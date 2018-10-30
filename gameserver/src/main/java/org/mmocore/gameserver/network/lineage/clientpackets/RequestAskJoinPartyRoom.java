package org.mmocore.gameserver.network.lineage.clientpackets;

import org.mmocore.gameserver.model.Request;
import org.mmocore.gameserver.model.Request.L2RequestType;
import org.mmocore.gameserver.model.team.matching.MatchingRoom;
import org.mmocore.gameserver.network.lineage.components.SystemMsg;
import org.mmocore.gameserver.network.lineage.serverpackets.ExAskJoinPartyRoom;
import org.mmocore.gameserver.network.lineage.serverpackets.SystemMessage;
import org.mmocore.gameserver.object.Player;
import org.mmocore.gameserver.world.World;

/**
 * format: (ch)S
 */
public class RequestAskJoinPartyRoom extends L2GameClientPacket {
    private String _name; // not tested, just guessed

    @Override
    protected void readImpl() {
        _name = readS(16);
    }

    @Override
    protected void runImpl() {
        final Player player = getClient().getActiveChar();
        if (player == null) {
            return;
        }
        final Player targetPlayer = World.getPlayer(_name);

        if (targetPlayer == null || targetPlayer == player) {
            player.sendActionFailed();
            return;
        }

        if (targetPlayer == player) {
            player.sendPacket(SystemMsg.THE_PLAYER_DECLINED_TO_JOIN_YOUR_PARTY);
            player.sendPacket(SystemMsg.YOU_HAVE_INVITED_THE_WRONG_TARGET);
            player.sendActionFailed();
            return;
        }

        if (player.isProcessingRequest()) {
            player.sendPacket(SystemMsg.WAITING_FOR_ANOTHER_REPLY);
            return;
        }

        if (targetPlayer.isProcessingRequest()) {
            player.sendPacket(new SystemMessage(SystemMsg.C1_IS_ON_ANOTHER_TASK).addName(targetPlayer));
            return;
        }

        if (targetPlayer.getMatchingRoom() != null) {
            return;
        }

        final MatchingRoom room = player.getMatchingRoom();
        if (room == null || room.getType() != MatchingRoom.PARTY_MATCHING) {
            return;
        }

        if (room.getGroupLeader() != player) {
            player.sendPacket(SystemMsg.ONLY_A_ROOM_LEADER_MAY_INVITE_OTHERS_TO_A_PARTY_ROOM);
            return;
        }

        if (room.getPlayers().size() >= room.getMaxMembersSize()) {
            player.sendPacket(SystemMsg.THE_PARTY_ROOM_IS_FULL);
            return;
        }

        new Request(L2RequestType.PARTY_ROOM, player, targetPlayer).setTimeout(10000L);

        targetPlayer.sendPacket(new ExAskJoinPartyRoom(player.getName(), room.getTopic()));

        player.sendPacket(new SystemMessage(SystemMsg.S1_HAS_SENT_AN_INVITATION_TO_ROOM_S2).addName(player).addString(room.getTopic()));
        targetPlayer.sendPacket(new SystemMessage(SystemMsg.S1_HAS_SENT_AN_INVITATION_TO_ROOM_S2).addName(player).addString(room.getTopic()));
    }
}