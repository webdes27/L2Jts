package org.mmocore.gameserver.scripts.ai;

import org.mmocore.commons.utils.Rnd;
import org.mmocore.gameserver.ThreadPoolManager;
import org.mmocore.gameserver.ai.DefaultAI;
import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.network.lineage.serverpackets.MagicSkillUse;
import org.mmocore.gameserver.utils.Location;

/**
 * Master Toma, телепортируется раз в 30 минут по 3м разным точкам гномьего острова.
 *
 * @author SYS
 */
public class Toma extends DefaultAI {
    private final Location[] _points = {
            new Location(151680, -174891, -1807, 41400),
            new Location(154153, -220105, -3402),
            new Location(178834, -184336, -352)};
    private long _lastTeleport = System.currentTimeMillis();

    public Toma(NpcInstance actor) {
        super(actor);
    }

    @Override
    protected boolean thinkActive() {
        // 30 min
        long TELEPORT_PERIOD = 30 * 60 * 1000;
        if (System.currentTimeMillis() - _lastTeleport < TELEPORT_PERIOD) {
            return false;
        }

        NpcInstance _thisActor = getActor();

        Location loc = _points[Rnd.get(_points.length)];
        if (_thisActor.getLoc().equals(loc)) {
            return false;
        }

        _thisActor.broadcastPacketToOthers(new MagicSkillUse(_thisActor, _thisActor, 4671, 1, 1000, 0));
        ThreadPoolManager.getInstance().schedule(new Teleport(loc), 1000);
        _lastTeleport = System.currentTimeMillis();

        return true;
    }

    @Override
    public boolean isGlobalAI() {
        return true;
    }
}