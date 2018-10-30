package org.mmocore.gameserver.scripts.npc.model;

import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.network.lineage.components.SystemMsg;
import org.mmocore.gameserver.object.Player;
import org.mmocore.gameserver.tables.SkillTable;
import org.mmocore.gameserver.templates.npc.NpcTemplate;

/**
 * @author VISTALL
 * @date 10:50/24.06.2011
 */
public class BlackJudeInstance extends NpcInstance {
    public BlackJudeInstance(int objectId, NpcTemplate template) {
        super(objectId, template);
    }

    @Override
    public void onBypassFeedback(Player player, String command) {
        if (!canBypassCheck(player, this)) {
            return;
        }

        switch (command) {
            case "tryRemovePenalty":
                if (player.getDeathPenalty().getLevel() > 0) {
                    showChatWindow(player, 2, "%price%", getPrice(player));
                } else {
                    showChatWindow(player, 1);
                }
                break;
            case "removePenalty":
                if (player.getDeathPenalty().getLevel() > 0) {
                    if (player.getAdena() >= getPrice(player)) {
                        player.reduceAdena(getPrice(player), true);
                        doCast(SkillTable.getInstance().getSkillEntry(5077, 1), player, false);
                    } else {
                        player.sendPacket(SystemMsg.YOU_DO_NOT_HAVE_ENOUGH_ADENA);
                    }
                } else {
                    showChatWindow(player, 1);
                }
                break;
            default:
                super.onBypassFeedback(player, command);
                break;
        }
    }

    public int getPrice(Player player) {
        int playerLvl = player.getLevel();
        if (playerLvl <= 19) {
            return 3600; // Non-grade (confirmed)
        } else if (playerLvl >= 20 && playerLvl <= 39) {
            return 16400; // D-grade
        } else if (playerLvl >= 40 && playerLvl <= 51) {
            return 36200; // C-grade
        } else if (playerLvl >= 52 && playerLvl <= 60) {
            return 50400; // B-grade (confirmed)
        } else if (playerLvl >= 61 && playerLvl <= 75) {
            return 78200; // A-grade
        } else {
            return 102800; // S-grade
        }
    }
}
