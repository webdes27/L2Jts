package org.mmocore.gameserver.scripts.npc.model.pts.hellbound;

import org.mmocore.gameserver.manager.HellboundManager;
import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.object.Player;
import org.mmocore.gameserver.templates.npc.NpcTemplate;

/**
 * @author KilRoy
 */
public class SolomonInstance extends NpcInstance {
    public SolomonInstance(int objectId, NpcTemplate template) {
        super(objectId, template);
    }

    @Override
    public void showChatWindow(Player talker, int val, Object... arg) {
        final int i0 = HellboundManager.getHellboundLevel();

        if (i0 == 5) {
            showChatWindow(talker, "pts/hellbound/solmon001.htm");
        } else if (i0 > 5) {
            showChatWindow(talker, "pts/hellbound/solmon001a.htm");
        }
    }
}