package org.mmocore.gameserver.scripts.quests;

import org.mmocore.commons.utils.Rnd;
import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.model.quest.Quest;
import org.mmocore.gameserver.model.quest.QuestState;

public class _341_HuntingForWildBeasts extends Quest {
    //NPCs
    private static final int PANO = 30078;
    //Quest Items
    private static final int BEAR_SKIN = 4259;

    public _341_HuntingForWildBeasts() {
        super(false);
        addStartNpc(PANO);
        //Mobs
        int red_Bear = 20021;
        addKillId(red_Bear);
        int dion_Grizzly = 20203;
        addKillId(dion_Grizzly);
        int brown_Bear = 20310;
        addKillId(brown_Bear);
        int grizzly_Bear = 20335;
        addKillId(grizzly_Bear);
        addQuestItem(BEAR_SKIN);
        addLevelCheck(20, 24);
    }

    @Override
    public String onEvent(String event, QuestState st, NpcInstance npc) {
        String htmltext = event;
        if (event.equalsIgnoreCase("quest_accept") && st.getState() == CREATED) {
            htmltext = "pano_q0341_04.htm";
            st.setState(STARTED);
            st.setCond(1);
            st.soundEffect(SOUND_ACCEPT);
        }
        return htmltext;
    }

    @Override
    public String onTalk(NpcInstance npc, QuestState st) {
        String htmltext = "noquest";
        if (npc.getNpcId() != PANO) {
            return htmltext;
        }
        int _state = st.getState();
        if (_state == CREATED) {
            switch (isAvailableFor(st.getPlayer())) {
                case LEVEL:
                    htmltext = "pano_q0341_02.htm";
                    st.exitQuest(true);
                    break;
                default:
                    htmltext = "pano_q0341_01.htm";
                    st.setCond(0);
                    break;
            }
        } else if (_state == STARTED) {
            if (st.ownItemCount(BEAR_SKIN) >= 20) {
                htmltext = "pano_q0341_05.htm";
                st.takeItems(BEAR_SKIN, -1);
                st.giveItems(ADENA_ID, 3710);
                st.soundEffect(SOUND_FINISH);
                st.exitQuest(true);
            } else {
                htmltext = "pano_q0341_06.htm";
            }
        }

        return htmltext;
    }

    @Override
    public String onKill(NpcInstance npc, QuestState qs) {
        if (qs.getState() != STARTED) {
            return null;
        }

        long BEAR_SKIN_COUNT = qs.ownItemCount(BEAR_SKIN);
        //Chances
        int BEAR_SKIN_CHANCE = 40;
        if (BEAR_SKIN_COUNT < 20 && Rnd.chance(BEAR_SKIN_CHANCE)) {
            qs.giveItems(BEAR_SKIN, 1);
            if (BEAR_SKIN_COUNT == 19) {
                qs.setCond(2);
                qs.soundEffect(SOUND_MIDDLE);
            } else {
                qs.soundEffect(SOUND_ITEMGET);
            }
        }
        return null;
    }


}