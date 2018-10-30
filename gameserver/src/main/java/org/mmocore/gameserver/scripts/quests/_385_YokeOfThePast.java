package org.mmocore.gameserver.scripts.quests;

import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.model.quest.Quest;
import org.mmocore.gameserver.model.quest.QuestState;
import org.mmocore.gameserver.utils.PlayerUtils;

/**
 * Квест проверен и работает.
 * Рейты прописаны путем повышения шанса получения квестовых вещей.
 */
public class _385_YokeOfThePast extends Quest {
    final int ANCIENT_SCROLL = 5902;
    final int BLANK_SCROLL = 5965;


    public _385_YokeOfThePast() {
        super(true);

        for (int npcId = 31095; npcId <= 31126; npcId++) {
            if (npcId != 31111 && npcId != 31112 && npcId != 31113) {
                addStartNpc(npcId);
            }
        }

        for (int mobs = 21208; mobs < 21256; mobs++) {
            addKillId(mobs);
        }

        addQuestItem(ANCIENT_SCROLL);
        addLevelCheck(20, 75);
    }

    public boolean checkNPC(int npc) {
        if (npc >= 31095 && npc <= 31126) {
            if (npc != 31100 && npc != 31111 && npc != 31112 && npc != 31113) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String onEvent(String event, QuestState st, NpcInstance npc) {
        String htmltext = event;
        if (event.equalsIgnoreCase("enter_necropolis1_q0385_05.htm")) {
            st.setState(STARTED);
            st.soundEffect(SOUND_ACCEPT);
            st.setCond(1);
        } else if (event.equalsIgnoreCase("enter_necropolis1_q0385_09.htm")) {
            htmltext = "enter_necropolis1_q0385_10.htm";
            st.soundEffect(SOUND_FINISH);
            st.exitQuest(true);
        }
        return htmltext;
    }

    @Override
    public String onKill(NpcInstance npc, QuestState st) {
        double rand = 60 * PlayerUtils.penaltyModifier(st.calculateLevelDiffForDrop(npc.getLevel(), st.getPlayer().getLevel()), 9) * npc.getTemplate().rateHp / 4;

        st.rollAndGive(ANCIENT_SCROLL, 1, rand);
        return null;
    }

    @Override
    public String onTalk(NpcInstance npc, QuestState st) {
        int npcId = npc.getNpcId();
        String htmltext = "noquest";
        if (checkNPC(npcId) && st.getCond() == 0) {
            switch (isAvailableFor(st.getPlayer())) {
                case LEVEL:
                    htmltext = "enter_necropolis1_q0385_02.htm";
                    st.exitQuest(true);
                default:
                    htmltext = "enter_necropolis1_q0385_01.htm";
                    break;
            }
        } else if (st.getCond() == 1 && st.ownItemCount(ANCIENT_SCROLL) == 0) {
            htmltext = "enter_necropolis1_q0385_11.htm";
        } else if (st.getCond() == 1 && st.ownItemCount(ANCIENT_SCROLL) > 0) {
            htmltext = "enter_necropolis1_q0385_09.htm";
            st.giveItems(BLANK_SCROLL, st.ownItemCount(ANCIENT_SCROLL));
            st.takeItems(ANCIENT_SCROLL, -1);
        } else {
            st.exitQuest(true);
        }
        return htmltext;
    }
}