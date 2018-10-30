package org.mmocore.gameserver.scripts.instances;

import org.mmocore.commons.lang.ArrayUtils;
import org.mmocore.commons.threading.RunnableImpl;
import org.mmocore.commons.utils.Rnd;
import org.mmocore.gameserver.ThreadPoolManager;
import org.mmocore.gameserver.listener.actor.OnDeathListener;
import org.mmocore.gameserver.listener.zone.OnZoneEnterLeaveListener;
import org.mmocore.gameserver.manager.SoIManager;
import org.mmocore.gameserver.model.entity.Reflection;
import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.model.zone.Zone;
import org.mmocore.gameserver.network.lineage.components.NpcString;
import org.mmocore.gameserver.network.lineage.components.SystemMsg;
import org.mmocore.gameserver.network.lineage.serverpackets.EventTrigger;
import org.mmocore.gameserver.network.lineage.serverpackets.ExShowScreenMessage;
import org.mmocore.gameserver.network.lineage.serverpackets.SystemMessage;
import org.mmocore.gameserver.object.Creature;
import org.mmocore.gameserver.object.Player;
import org.mmocore.gameserver.utils.ChatUtils;
import org.mmocore.gameserver.utils.Location;

import java.util.List;
import java.util.concurrent.ScheduledFuture;

/**
 * @author pchayka
 */
public class ErosionHallAttack extends Reflection {
    private static final int AliveTumor = 18708;
    private static final int DeadTumor = 32535;
    private static final int Cohemenes = 25634;
    private static final int RegenerationCoffin = 18710;

    private final Zone[] viceraZones = new Zone[12];
    private final int[] zoneEventTriggers = ArrayUtils.createAscendingArray(14240001, 14240012);
    private final ZoneListener startZoneListener = new ZoneListener();
    private final DeathListener deathListener = new DeathListener();
    private boolean conquestBegun = false;
    private boolean conquestEnded = false;
    private long tumorRespawnTime = 0;

    private NpcInstance cohemenes = null;
    private long startTime = 0;
    private ScheduledFuture<?> timerTask = null;
    // 25516 25520 25524 25526 25522 25532 25517 25519 25521 25534 25518

    @Override
    protected void onCreate() {
        super.onCreate();
        viceraZones[0] = getZone("[soi_hoe_attack_pc_vicera_1]");
        viceraZones[1] = getZone("[soi_hoe_attack_pc_vicera_2]");
        viceraZones[2] = getZone("[soi_hoe_attack_pc_vicera_3]");
        viceraZones[3] = getZone("[soi_hoe_attack_pc_vicera_4]");
        viceraZones[4] = getZone("[soi_hoe_attack_pc_vicera_5]");
        viceraZones[5] = getZone("[soi_hoe_attack_pc_vicera_6]");
        viceraZones[6] = getZone("[soi_hoe_attack_pc_vicera_7]");
        viceraZones[6].addListener(startZoneListener);
        viceraZones[7] = getZone("[soi_hoe_attack_pc_vicera_8]");
        viceraZones[8] = getZone("[soi_hoe_attack_pc_vicera_9]");
        viceraZones[9] = getZone("[soi_hoe_attack_pc_vicera_10]");
        viceraZones[10] = getZone("[soi_hoe_attack_pc_vicera_11]");
        viceraZones[11] = getZone("[soi_hoe_attack_pc_vicera_12]");
        for (Zone z : viceraZones) {
            z.setActive(true);
        }
        tumorRespawnTime = 3 * 60 * 1000L;
    }

    private void conquestBegins() {
        for (Player p : getPlayers()) {
            p.sendPacket(new ExShowScreenMessage(NpcString.YOU_CAN_HEAR_THE_UNDEAD_OF_EKIMUS_RUSHING_TOWARD_YOU, 8000, ExShowScreenMessage.ScreenMessageAlign.MIDDLE_CENTER, false, 1, -1, false, "#" + NpcString.HALL_OF_EROSION.getId(), "#" + NpcString.ATTACK.getId()));
        }
        spawnByGroup("soi_hoe_attack_tumors");
        for (NpcInstance n : getAllByNpcId(AliveTumor, true)) {
            n.setCurrentHp(n.getMaxHp() * .5, false);
        }
        spawnByGroup("soi_hoe_attack_symbols");
        spawnByGroup("soi_hoe_attack_wards");
        invokeDeathListener();
        // Rooms
        spawnByGroup("soi_hoe_attack_mob_1");
        spawnByGroup("soi_hoe_attack_mob_2");
        spawnByGroup("soi_hoe_attack_mob_3");
        spawnByGroup("soi_hoe_attack_mob_4");
        spawnByGroup("soi_hoe_attack_mob_5");
        spawnByGroup("soi_hoe_attack_mob_6");
        spawnByGroup("soi_hoe_attack_mob_7");
        spawnByGroup("soi_hoe_attack_mob_8");
        startTime = System.currentTimeMillis();
        timerTask = ThreadPoolManager.getInstance().scheduleAtFixedRate(new TimerTask(), 298 * 1000L, 5 * 60 * 1000L);
    }

    @Override
    public void onPlayerEnter(Player player) {
        super.onPlayerEnter(player);
        for (int i : zoneEventTriggers) {
            player.sendPacket(new EventTrigger(i, true));
        }
    }

    private void invokeDeathListener() {
        for (NpcInstance npc : getNpcs()) {
            npc.addListener(deathListener);
        }
    }

    public void notifyCoffinDeath() {
        tumorRespawnTime += 10 * 1000L;
    }

    private void notifyTumorDeath(NpcInstance tumor) {
        if (getAliveTumorCount() == 0) {
            cohemenesApperance();
        } else {
            for (Player p : getPlayers()) {
                p.sendPacket(new ExShowScreenMessage(NpcString.THE_TUMOR_INSIDE_S1_HAS_BEEN_DESTROYED_NIN_ORDER_TO_DRAW_OUT_THE_COWARDLY_COHEMENES_YOU_MUST_DESTROY_ALL_THE_TUMORS, 8000, ExShowScreenMessage.ScreenMessageAlign.MIDDLE_CENTER, false, 1, -1, false, "#" + NpcString.HALL_OF_EROSION.getId()));
            }
        }
        manageRegenZone(getRoomId(tumor), true);
    }

    private void notifyTumorRevival(NpcInstance tumor) {
        if (getAliveTumorCount() > 0 && cohemenes != null && !cohemenes.isDead()) {
            if (cohemenes.hasOnePrivateEx()) {
                cohemenes.getPrivatesList().deleteOnePrivateEx();
            }
            cohemenes.deleteMe();
        }
        for (Player p : getPlayers()) {
            p.sendPacket(new ExShowScreenMessage(NpcString.THE_TUMOR_INSIDE_S1_HAS_COMPLETELY_REVIVED, 8000, ExShowScreenMessage.ScreenMessageAlign.MIDDLE_CENTER, false, 1, -1, false, "#" + NpcString.HALL_OF_EROSION.getId()));
        }
        manageRegenZone(getRoomId(tumor), false);
    }

    private void cohemenesApperance() {
        for (Player p : getPlayers()) {
            p.sendPacket(new ExShowScreenMessage(NpcString.ALL_THE_TUMORS_INSIDE_S1_HAVE_BEEN_DESTROYED_DRIVEN_INTO_A_CORNER_COHEMENES_APPEARS_CLOSE_BY, 8000, ExShowScreenMessage.ScreenMessageAlign.MIDDLE_CENTER, false, 1, -1, false, "#" + NpcString.HALL_OF_EROSION.getId()));
        }

        cohemenes = addSpawnWithoutRespawn(Cohemenes, getRandomSymbolLocation(), 0);
        cohemenes.getPrivatesList().createOnePrivateEx(25635, cohemenes.getX() + Rnd.get(50) - Rnd.get(50), cohemenes.getY() + Rnd.get(50) - Rnd.get(50), cohemenes.getZ(), Rnd.get(61440), null, 0, 0, true);
        invokeDeathListener();
        ChatUtils.shout(cohemenes, NpcString.CMON_CMON_SHOW_YOUR_FACE_YOU_LITTLE_RATS_LET_ME_SEE_WHAT_THE_DOOMED_WEAKLINGS_ARE_SCHEMING);
    }

    private void conquestConclusion(boolean win) {
        if (timerTask != null) {
            timerTask.cancel(false);
        }
        conquestEnded = true;
        despawnByGroup("soi_hoe_attack_symbols");
        despawnByGroup("soi_hoe_attack_wards");
        if (cohemenes != null && !cohemenes.isDead()) {
            if (cohemenes.hasOnePrivateEx()) {
                cohemenes.getPrivatesList().deleteOnePrivateEx();
            }
            cohemenes.deleteMe();
        }
        startCollapseTimer(15 * 60 * 1000L);
        if (win) {
            setReenterTime(System.currentTimeMillis());
        }
        for (Player p : getPlayers()) {
            p.sendPacket(new SystemMessage(SystemMsg.THIS_DUNGEON_WILL_EXPIRE_IN_S1_MINUTES).addNumber(15));
            p.sendPacket(new ExShowScreenMessage(win ? NpcString.CONGRATULATIONS_YOU_HAVE_SUCCEEDED_AT_S1_S2_THE_INSTANCE_WILL_SHORTLY_EXPIRE : NpcString.YOU_HAVE_FAILED_AT_S1_S2, 8000, ExShowScreenMessage.ScreenMessageAlign.MIDDLE_CENTER, false, 1, -1, false, "#" + NpcString.HALL_OF_EROSION.getId(), "#" + NpcString.ATTACK.getId()));
        }
        for (NpcInstance npc : getNpcs()) {
            if (npc.getNpcId() == AliveTumor || npc.getNpcId() == DeadTumor) {
                npc.deleteMe();
            }
        }
    }

    private int getAliveTumorCount() {
        return getAllByNpcId(AliveTumor, true).size();
    }

    private int getRoomId(NpcInstance npc) {
        int i = 0;
        if (npc.isInZone("[soi_hoe_attack_attackup1_1]")) {
            i = 1;
        } else if (npc.isInZone("[soi_hoe_attack_attackup1_2]")) {
            i = 2;
        } else if (npc.isInZone("[soi_hoe_attack_attackup1_3]")) {
            i = 3;
        } else if (npc.isInZone("[soi_hoe_attack_attackup1_4]")) {
            i = 4;
        } else if (npc.isInZone("[soi_hoe_attack_attackup1_5]")) {
            i = 5;
        } else if (npc.isInZone("[soi_hoe_attack_attackup1_6]")) {
            i = 6;
        } else if (npc.isInZone("[soi_hoe_attack_attackup1_7]")) {
            i = 7;
        } else if (npc.isInZone("[soi_hoe_attack_attackup1_8]")) {
            i = 8;
        }
        return i;
    }

    private void manageRegenZone(int roomId, boolean doActivate) {
        switch (roomId) {
            case 1:
                getZone("[soi_hoe_attack_pc_regen_1]").setActive(doActivate);
                break;
            case 2:
                getZone("[soi_hoe_attack_pc_regen_2]").setActive(doActivate);
                break;
            case 3:
                getZone("[soi_hoe_attack_pc_regen_3]").setActive(doActivate);
                break;
            case 4:
                getZone("[soi_hoe_attack_pc_regen_4]").setActive(doActivate);
                break;
            case 5:
                getZone("[soi_hoe_attack_pc_regen_5]").setActive(doActivate);
                break;
            case 6:
                getZone("[soi_hoe_attack_pc_regen_6]").setActive(doActivate);
                break;
            case 7:
                getZone("[soi_hoe_attack_pc_regen_7]").setActive(doActivate);
                break;
            case 8:
                getZone("[soi_hoe_attack_pc_regen_8]").setActive(doActivate);
                break;

        }
    }

    private Location getRandomSymbolLocation() {
        List<NpcInstance> npclocations = getAllByNpcId(18780, true);
        if (!npclocations.isEmpty()) {
            return Location.findPointToStay(npclocations.get(Rnd.get(npclocations.size())), 100, 250);
        } else {
            return new Location(-178418, 211653, -12029);
        }
    }

    @Override
    protected void onCollapse() {
        if (timerTask != null) {
            timerTask.cancel(false);
        }
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
                NpcInstance deadTumor = addSpawnWithoutRespawn(DeadTumor, self.getLoc(), 0);
                self.deleteMe();
                notifyTumorDeath(deadTumor);
                //Schedule tumor revival
                ThreadPoolManager.getInstance().schedule(new TumorRevival(deadTumor), tumorRespawnTime);
                // Schedule regeneration coffins spawn
                ThreadPoolManager.getInstance().schedule(new RegenerationCoffinSpawn(deadTumor), 20000L);
            } else if (self.getNpcId() == Cohemenes) {
                ChatUtils.shout(cohemenes, NpcString.KEU);
                conquestConclusion(true);
                SoIManager.notifyCohemenesKill();
            }
        }
    }

    private class TumorRevival extends RunnableImpl {
        final NpcInstance _deadTumor;

        public TumorRevival(NpcInstance deadTumor) {
            _deadTumor = deadTumor;
        }

        @Override
        public void runImpl() {
            if (conquestEnded) {
                return;
            }
            NpcInstance tumor = addSpawnWithoutRespawn(AliveTumor, _deadTumor.getLoc(), 0);
            tumor.setCurrentHp(tumor.getMaxHp() * .25, false);
            notifyTumorRevival(_deadTumor);
            _deadTumor.deleteMe();
            invokeDeathListener();
        }
    }

    private class RegenerationCoffinSpawn extends RunnableImpl {
        final NpcInstance _deadTumor;

        public RegenerationCoffinSpawn(NpcInstance deadTumor) {
            _deadTumor = deadTumor;
        }

        @Override
        public void runImpl() {
            if (conquestEnded) {
                return;
            }
            for (int i = 0; i < 4; i++) {
                addSpawnWithoutRespawn(RegenerationCoffin, new Location(_deadTumor.getLoc().x, _deadTumor.getLoc().y, _deadTumor.getLoc().z, Location.getRandomHeading()), 250);
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