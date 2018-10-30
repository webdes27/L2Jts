package org.mmocore.gameserver.scripts.ai;

import org.mmocore.commons.threading.RunnableImpl;
import org.mmocore.commons.utils.Rnd;
import org.mmocore.gameserver.ThreadPoolManager;
import org.mmocore.gameserver.ai.CtrlEvent;
import org.mmocore.gameserver.ai.Fighter;
import org.mmocore.gameserver.model.SimpleSpawner;
import org.mmocore.gameserver.model.entity.Reflection;
import org.mmocore.gameserver.model.instances.MonsterInstance;
import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.object.Creature;
import org.mmocore.gameserver.object.Player;
import org.mmocore.gameserver.scripts.instances.KamalokaNightmare;
import org.mmocore.gameserver.utils.PositionUtils;
import org.mmocore.gameserver.world.World;

public class Kanabion extends Fighter {
    public Kanabion(NpcInstance actor) {
        super(actor);
    }

    @Override
    protected void onEvtDead(Creature killer) {
        NpcInstance actor = getActor();
        boolean isOverhit = false;
        if (actor instanceof MonsterInstance) {
            isOverhit = ((MonsterInstance) actor).getOverhitDamage() > 0;
        }
        int npcId = actor.getNpcId();
        int nextId = 0;
        int type = 0;
        //TODO: [Bonux] пересмотреть шансы спавна доплеров и воидеров. Реализовать шанс при ударе 2\3 ХП моба.
        if (npcId != getNextDoppler(npcId) && npcId != getNextVoid(npcId)) {
            type = 1;
            if (isOverhit) {
                if (Rnd.chance(70)) {
                    nextId = getNextDoppler(npcId);
                } else if (Rnd.chance(80)) {
                    nextId = getNextVoid(npcId);
                }
            } else if (Rnd.chance(65)) {
                nextId = getNextDoppler(npcId);
            }
        } else if (npcId == getNextDoppler(npcId)) {
            type = 2;
            if (isOverhit) {
                if (Rnd.chance(60)) {
                    nextId = getNextDoppler(npcId);
                } else if (Rnd.chance(90)) {
                    nextId = getNextVoid(npcId);
                }
            } else if (Rnd.chance(40)) {
                nextId = getNextDoppler(npcId);
            } else if (Rnd.chance(50)) {
                nextId = getNextVoid(npcId);
            }
        } else if (npcId == getNextVoid(npcId)) {
            type = 3;
            if (isOverhit) {
                if (Rnd.chance(80)) {
                    nextId = getNextVoid(npcId);
                }
            } else if (Rnd.chance(50)) {
                nextId = getNextVoid(npcId);
            }
        }

        Reflection r = actor.getReflection();
        boolean spawnPossible = true;
        if (r instanceof KamalokaNightmare) {
            KamalokaNightmare kama = (KamalokaNightmare) r;
            kama.addKilledKanabion(type);
            spawnPossible = kama.isSpawnPossible();
        }

        if (spawnPossible && nextId > 0) {
            Creature player = null;
            if (!killer.isPlayer()) // На оффе если убить саммоном или петом, то следующий канабион агрится не на пета, а на хозяина.
            {
                for (Player pl : World.getAroundPlayers(actor)) {
                    player = pl;
                    break;
                }
            }
            if (player == null) {
                player = killer;
            }
            ThreadPoolManager.getInstance().schedule(new SpawnNext(actor, player, nextId), 5000);
        }

        super.onEvtDead(killer);
    }

    private int getNextDoppler(int npcId) {
        switch (npcId) {
            case 22452: // White Skull Kanabion
            case 22453:
            case 22454:
                return 22453; // Doppler

            case 22455: // Begrudged Kanabion
            case 22456:
            case 22457:
                return 22456; // Doppler

            case 22458: // Rotten Kanabion
            case 22459:
            case 22460:
                return 22459; // Doppler

            case 22461: // Gluttonous Kanabion
            case 22462:
            case 22463:
                return 22462; // Doppler

            case 22464: // Callous Kanabion
            case 22465:
            case 22466:
                return 22465; // Doppler

            case 22467: // Savage Kanabion
            case 22468:
            case 22469:
                return 22468; // Doppler

            case 22470: // Peerless Kanabion
            case 22471:
            case 22472:
                return 22471; // Doppler

            case 22473: // Massive Kanabion
            case 22474:
            case 22475:
                return 22474; // Doppler

            case 22476: // Fervent Kanabion
            case 22477:
            case 22478:
                return 22477; // Doppler

            case 22479: // Ruptured Kanabion
            case 22480:
            case 22481:
                return 22480; // Doppler

            case 22482: // Sword Kanabion
            case 22483:
            case 22484:
                return 22483; // Doppler

            default:
                return 0; // такого быть не должно
        }
    }

    private int getNextVoid(int npcId) {
        switch (npcId) {
            case 22452: // White Skull Kanabion
            case 22453:
            case 22454:
                return 22454; // Void

            case 22455: // Begrudged Kanabion
            case 22456:
            case 22457:
                return 22457; // Void

            case 22458: // Rotten Kanabion
            case 22459:
            case 22460:
                return 22460; // Void

            case 22461: // Gluttonous Kanabion
            case 22462:
            case 22463:
                return 22463; // Void

            case 22464: // Callous Kanabion
            case 22465:
            case 22466:
                return 22466; // Void

            case 22467: // Savage Kanabion
            case 22468:
            case 22469:
                return 22469; // Void

            case 22470: // Peerless Kanabion
            case 22471:
            case 22472:
                return 22472; // Void

            case 22473: // Massive Kanabion
            case 22474:
            case 22475:
                return 22475; // Void

            case 22476: // Fervent Kanabion
            case 22477:
            case 22478:
                return 22478; // Void

            case 22479: // Ruptured Kanabion
            case 22480:
            case 22481:
                return 22481; // Void

            case 22482: // Sword Kanabion
            case 22483:
            case 22484:
                return 22484; // Void

            default:
                return 0; // такого быть не должно
        }
    }

    public static class SpawnNext extends RunnableImpl {
        private final NpcInstance _actor;
        private final Creature _player;
        private final int _nextId;

        public SpawnNext(NpcInstance actor, Creature player, int nextId) {
            _actor = actor;
            _player = player;
            _nextId = nextId;
        }

        @Override
        public void runImpl() {
            SimpleSpawner sp = new SimpleSpawner(_nextId);
            sp.setLocx(_actor.getX());
            sp.setLocy(_actor.getY());
            sp.setLocz(_actor.getZ());
            sp.setReflection(_actor.getReflection());
            NpcInstance npc = sp.doSpawn(true);
            npc.setHeading(PositionUtils.calculateHeadingFrom(npc, _player));
            npc.getAI().notifyEvent(CtrlEvent.EVT_AGGRESSION, _player, 1000);
        }
    }
}