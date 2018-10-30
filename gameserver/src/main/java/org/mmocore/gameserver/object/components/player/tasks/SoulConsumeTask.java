package org.mmocore.gameserver.object.components.player.tasks;

import org.mmocore.commons.lang.reference.HardReference;
import org.mmocore.commons.threading.RunnableImpl;
import org.mmocore.gameserver.object.Player;

public class SoulConsumeTask extends RunnableImpl {
    private final HardReference<Player> _playerRef;

    public SoulConsumeTask(Player player) {
        _playerRef = player.getRef();
    }

    @Override
    public void runImpl() {
        Player player = _playerRef.get();
        if (player == null) {
            return;
        }
        player.setConsumedSouls(player.getConsumedSouls() + 1, null);
    }
}