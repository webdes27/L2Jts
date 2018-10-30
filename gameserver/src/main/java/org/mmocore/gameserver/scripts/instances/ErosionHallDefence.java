package org.mmocore.gameserver.scripts.instances;

import org.mmocore.commons.lang.ArrayUtils;
import org.mmocore.commons.threading.RunnableImpl;
import org.mmocore.commons.utils.Rnd;
import org.mmocore.gameserver.ThreadPoolManager;
import org.mmocore.gameserver.ai.CtrlEvent;
import org.mmocore.gameserver.ai.CtrlIntention;
import org.mmocore.gameserver.listener.actor.OnDeathListener;
import org.mmocore.gameserver.listener.zone.OnZoneEnterLeaveListener;
import org.mmocore.gameserver.model.entity.Reflection;
import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.model.quest.QuestState;
import org.mmocore.gameserver.model.zone.Zone;
import org.mmocore.gameserver.model.zone.ZoneType;
import org.mmocore.gameserver.network.lineage.components.NpcString;
import org.mmocore.gameserver.network.lineage.serverpackets.EventTrigger;
import org.mmocore.gameserver.network.lineage.serverpackets.ExShowScreenMessage;
import org.mmocore.gameserver.object.Creature;
import org.mmocore.gameserver.object.Player;
import org.mmocore.gameserver.utils.ChatUtils;
import org.mmocore.gameserver.utils.Location;

import java.util.concurrent.ScheduledFuture;

/**
 * @author pchayka
 */

public class ErosionHallDefence extends Reflection {
    private static final int AliveTumor = 18708;
    private static final int DeadTumor = 32535;
    private static final int UnstableSeed = 32541;
    private static final int RegenerationCoffin = 18709;
    private static final int SoulWagon = 25636;
    private final int[] zoneEventTriggers = ArrayUtils.createAscendingArray(14240001, 14240012);
    private final ZoneListener startZoneListener = new ZoneListener();
    private boolean conquestBegun = false;
    private final DeathListener deathListener = new DeathListener();
    private ScheduledFuture<?> timerTask = null, agressionTask = null, coffinSpawnTask = null, aliveTumorSpawnTask = null, failureTask = null;
    private long startTime = 0;
    private long tumorRespawnTime = 0;
    private boolean conquestEnded = false;
    private int tumorKillCount = 0;
    private boolean soulwagonSpawned = false;

    @Override
    protected void onCreate() {
        super.onCreate();
        getZone("[soi_hoe_attack_pc_vicera_7]").addListener(startZoneListener);
        tumorRespawnTime = 3 * 60 * 1000L;
        tumorKillCount = 0;
    }

    private void conquestBegins() {
        for (Player p : getPlayers()) {
            p.sendPacket(new ExShowScreenMessage(NpcString.YOU_CAN_HEAR_THE_UNDEAD_OF_EKIMUS_RUSHING_TOWARD_YOU, 8000, ExShowScreenMessage.ScreenMessageAlign.MIDDLE_CENTER, false, 1, -1, false, "#" + NpcString.HALL_OF_EROSION.getId(), "#" + NpcString.DEFEND.getId()));
        }
        spawnByGroup("soi_hoe_defence_lifeseed");
        spawnByGroup("soi_hoe_defence_tumor");
        spawnByGroup("soi_hoe_defence_wards");
        invokeDeathListener();
        // Rooms
        spawnByGroup("soi_hoe_defence_mob_1");
        spawnByGroup("soi_hoe_defence_mob_2");
        spawnByGroup("soi_hoe_defence_mob_3");
        spawnByGroup("soi_hoe_defence_mob_4");
        spawnByGroup("soi_hoe_defence_mob_5");
        spawnByGroup("soi_hoe_defence_mob_6");
        spawnByGroup("soi_hoe_defence_mob_7");
        spawnByGroup("soi_hoe_defence_mob_8");
        agressionTask = ThreadPoolManager.getInstance().scheduleAtFixedRate(new RunnableImpl() {
            @Override
            public void runImpl() {
                if (!conquestEnded) {
                    notifyAttackSeed();
                }
            }
        }, 15000L, 25000L);
        coffinSpawnTask = ThreadPoolManager.getInstance().scheduleAtFixedRate(new RunnableImpl() {
            @Override
            public void runImpl() {
                if (!conquestEnded) {
                    for (NpcInstance npc : getAllByNpcId(DeadTumor, true)) {
                        spawnCoffin(npc);
                    }
                }
            }
        }, 1000L, 60000L);
        aliveTumorSpawnTask = ThreadPoolManager.getInstance().schedule(new RunnableImpl() {
            @Override
            public void runImpl() {
                if (!conquestEnded) {
                    despawnByGroup("soi_hoe_defence_tumor");
                    spawnByGroup("soi_hoe_defence_alivetumor");
                    handleTumorHp(0.5);
                    for (Player p : getPlayers()) {
                        p.sendPacket(new ExShowScreenMessage(NpcString.THE_TUMOR_INSIDE_S1_HAS_COMPLETELY_REVIVED_, 8000, ExShowScreenMessage.ScreenMessageAlign.MIDDLE_CENTER, false, 1, -1, false, "#" + NpcString.HALL_OF_EROSION.getId()));
                    }
                    invokeDeathListener();
                }
            }
        }, tumorRespawnTime);

        startTime = System.currentTimeMillis();
        timerTask = ThreadPoolManager.getInstance().scheduleAtFixedRate(new TimerTask(), 298 * 1000L, 5 * 60 * 1000L);
    }

    private void notifyAttackSeed() {
        for (final NpcInstance npc : getNpcs()) {
            NpcInstance seed = getNearestSeed(npc);
            if (seed != null) {
                if (npc.getAI().getIntention() == CtrlIntention.AI_INTENTION_ACTIVE) {
                    npc.getAI().notifyEvent(CtrlEvent.EVT_AGGRESSION, seed, 100);
                    ThreadPoolManager.getInstance().schedule(new RunnableImpl() {
                        @Override
                        public void runImpl() {

                            npc.getAggroList().clear(true);
                            npc.getAI().setIntention(CtrlIntention.AI_INTENTION_ACTIVE);
                            npc.moveToLocation(Location.findAroundPosition(npc, 400), 0, false);
                        }
                    }, 7000L);
                }
            }
        }
    }

    public void notifyCoffinDeath() {
        tumorRespawnTime -= 5 * 1000L;
    }

    private void spawnCoffin(NpcInstance tumor) {
        addSpawnWithoutRespawn(RegenerationCoffin, new Location(tumor.getLoc().x, tumor.getLoc().y, tumor.getLoc().z, Location.getRandomHeading()), 250);
    }

    private NpcInstance getNearestSeed(NpcInstance mob) {
        for (NpcInstance npc : mob.getAroundNpc(900, 300)) {
            if (npc.getNpcId() == UnstableSeed && mob.getZone(ZoneType.poison) == npc.getZone(ZoneType.poison)) {
                return npc;
            }
        }
        return null;
    }

    private void invokeDeathListener() {
        for (NpcInstance npc : getNpcs()) {
            npc.addListener(deathListener);
        }
    }

    private void conquestConclusion(boolean win) {
        if (conquestEnded) {
            return;
        }
        cancelTimers();
        conquestEnded = true;
        clearReflection(15, true);
        if (win) {
            setReenterTime(System.currentTimeMillis());
        }
        for (Player p : getPlayers()) {
            if (win) {
                QuestState qs = p.getQuestState(697);
                if (qs != null && qs.getCond() == 1) {
                    qs.setMemoState("defenceDone", 1);
                }
            }
            p.sendPacket(new ExShowScreenMessage(win ? NpcString.CONGRATULATIONS_YOU_HAVE_SUCCEEDED_AT_S1_S2_THE_INSTANCE_WILL_SHORTLY_EXPIRE : NpcString.YOU_HAVE_FAILED_AT_S1_S2, 8000, ExShowScreenMessage.ScreenMessageAlign.MIDDLE_CENTER, false, 1, -1, false, "#" + NpcString.HALL_OF_EROSION.getId(), "#" + NpcString.DEFEND.getId()));
        }
    }

    private void handleTumorHp(double percent) {
        for (NpcInstance npc : getAllByNpcId(AliveTumor, true)) {
            npc.setCurrentHp(npc.getMaxHp() * percent, false);
        }
    }

    private void notifyTumorDeath() {
        tumorKillCount++;
        if (tumorKillCount > 4 && !soulwagonSpawned) // 16
        {
            soulwagonSpawned = true;
            spawnByGroup("soi_hoe_defence_soulwagon");
            for (NpcInstance npc : getAllByNpcId(SoulWagon, true)) {
                ChatUtils.shout(npc, NpcString.HA_HA_HA);
                NpcInstance seed = getNearestSeed(npc);
                if (seed != null) {
                    npc.getAI().notifyEvent(CtrlEvent.EVT_AGGRESSION, seed, 100);
                }
                rescheduleFailureTask(180000L);
            }
            invokeDeathListener();
        }
    }

    private void rescheduleFailureTask(long time) {
        if (failureTask != null) {
            failureTask.cancel(false);
            failureTask = null;
        }
        failureTask = ThreadPoolManager.getInstance().schedule(new RunnableImpl() {
            @Override
            public void runImpl() {
                conquestConclusion(false);
            }
        }, time);
    }

    private void cancelTimers() {
        if (timerTask != null) {
            timerTask.cancel(false);
        }
        if (agressionTask != null) {
            agressionTask.cancel(false);
        }
        if (coffinSpawnTask != null) {
            coffinSpawnTask.cancel(false);
        }
        if (aliveTumorSpawnTask != null) {
            aliveTumorSpawnTask.cancel(false);
        }
        if (failureTask != null) {
            failureTask.cancel(false);
        }
    }

    @Override
    public void onPlayerEnter(Player player) {
        super.onPlayerEnter(player);
        for (int i : zoneEventTriggers) {
            player.sendPacket(new EventTrigger(i, true));
        }
    }

    @Override
    protected void onCollapse() {
        cancelTimers();
        super.onCollapse();
    }

    public class ZoneListener implements OnZoneEnterLeaveListener {
        @Override
        public void onZoneEnter(Zone zone, Creature cha) {
            if (!conquestBegun) {
                conquestBegun = true;
                conquestBegins();
            }
        }

        @Override
        public void onZoneLeave(Zone zone, Creature cha) {
        }
    }

    private class DeathListener implements OnDeathListener {
        @Override
        public void onDeath(Creature self, Creature killer) {
            if (!self.isNpc()) {
                return;
            }
            if (self.getNpcId() == AliveTumor) {
                ((NpcInstance) self).dropItem(killer.getPlayer(), 13797, Rnd.get(2, 5));
                final NpcInstance deadTumor = addSpawnWithoutRespawn(DeadTumor, self.getLoc(), 0);
                notifyTumorDeath();
                self.deleteMe();
                for (Player p : getPlayers()) {
                    p.sendPacket(new ExShowScreenMessage(NpcString.THE_TUMOR_INSIDE_S1_HAS_BEEN_DESTROYED_NTHE_NEARBY_UNDEAD_THAT_WERE_ATTACKING_SEED_OF_LIFE_START_LOSING_THEIR_ENERGY_AND_RUN_AWAY, 8000, ExShowScreenMessage.ScreenMessageAlign.MIDDLE_CENTER, false, 1, -1, false, "#" + NpcString.HALL_OF_EROSION.getId()));
                }
                ThreadPoolManager.getInstance().schedule(new RunnableImpl() {
                    @Override
                    public void runImpl() {
                        deadTumor.deleteMe();
                        addSpawnWithoutRespawn(AliveTumor, deadTumor.getLoc(), 0);
                        handleTumorHp(0.25);
                        invokeDeathListener();
                        for (Player p : getPlayers()) {
                            p.sendPacket(new ExShowScreenMessage(NpcString.THE_TUMOR_INSIDE_S1_HAS_COMPLETELY_REVIVED_, 8000, ExShowScreenMessage.ScreenMessageAlign.MIDDLE_CENTER, false, 1, -1, false, "#" + NpcString.HALL_OF_EROSION.getId()));
                        }
                    }
                }, tumorRespawnTime);
            } else if (self.getNpcId() == SoulWagon) {
                if (getAllByNpcId(SoulWagon, true).size() > 0) {
                    rescheduleFailureTask(60000L);
                } else {
                    conquestConclusion(true);
                }
            }
        }
    }

    private class TimerTask extends RunnableImpl {
        @Override
        public void runImpl() {
            long time = (startTime + 25 * 60 * 1000L - System.currentTimeMillis()) / 60000;
            if (time == 0) {
                conquestConclusion(false);
            } else {
                for (Player p : getPlayers()) {
                    p.sendPacket(new ExShowScreenMessage(NpcString.S1_MINUTES_ARE_REMAINING, 8000, ExShowScreenMessage.ScreenMessageAlign.MIDDLE_CENTER, false, 1, -1, false, String.valueOf((startTime + 25 * 60 * 1000L - System.currentTimeMillis()) / 60000)));
                }
            }
        }
    }
}
