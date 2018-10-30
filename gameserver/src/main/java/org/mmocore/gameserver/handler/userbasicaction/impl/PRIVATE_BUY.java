package org.mmocore.gameserver.handler.userbasicaction.impl;

import org.mmocore.gameserver.handler.userbasicaction.IUserBasicActionHandler;
import org.mmocore.gameserver.network.lineage.serverpackets.PrivateStoreManageListBuy;
import org.mmocore.gameserver.object.GameObject;
import org.mmocore.gameserver.object.Player;
import org.mmocore.gameserver.utils.TradeHelper;

import java.util.Optional;
import java.util.OptionalInt;

/**
 * Create by Mangol on 21.10.2015.
 */
public class PRIVATE_BUY implements IUserBasicActionHandler {
    @Override
    public void useAction(final Player player, final int id, final Optional<String> option, final OptionalInt useSkill, final Optional<GameObject> target, final boolean ctrlPressed, final boolean shiftPressed) {
        if (player.isOutOfControl() || player.isActionsDisabled()) {
            player.sendActionFailed();
            return;
        }
        if (player.getSittingTask() || (player.isSitting() && !player.isInStoreMode())) {
            player.sendActionFailed();
            return;
        }
        if (player.isInStoreMode()) {
            player.setPrivateStoreType(Player.STORE_PRIVATE_NONE);
            player.standUp();
            player.broadcastCharInfo();
        } else if (!TradeHelper.checksIfCanOpenStore(player, Player.STORE_PRIVATE_BUY)) {
            player.sendActionFailed();
            return;
        }
        player.sendPacket(new PrivateStoreManageListBuy(player));
    }
}
