package org.mmocore.gameserver.model.entity.olympiad;

import org.mmocore.commons.threading.RunnableImpl;
import org.mmocore.gameserver.ThreadPoolManager;
import org.mmocore.gameserver.configuration.config.OlympiadConfig;
import org.mmocore.gameserver.model.entity.Hero;
import org.mmocore.gameserver.network.lineage.components.SystemMsg;
import org.mmocore.gameserver.network.lineage.serverpackets.SystemMessage;
import org.mmocore.gameserver.utils.AnnouncementUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OlympiadEndTask extends RunnableImpl {
    private static final Logger _log = LoggerFactory.getLogger(OlympiadEndTask.class);

    @Override
    public void runImpl() {
        if (Olympiad._inCompPeriod) // Если бои еще не закончились, откладываем окончание олимпиады на минуту
        {
            ThreadPoolManager.getInstance().schedule(new OlympiadEndTask(), 60000);
            return;
        }

        AnnouncementUtils.announceToAll(new SystemMessage(SystemMsg.ROUND_S1_OF_THE_GRAND_OLYMPIAD_GAMES_HAS_NOW_ENDED).addNumber(Olympiad._currentCycle));

        Olympiad._isOlympiadEnd = true;
        if (Olympiad._scheduledManagerTask != null) {
            Olympiad._scheduledManagerTask.cancel(false);
        }
        if (Olympiad._scheduledWeeklyTask != null) {
            Olympiad._scheduledWeeklyTask.cancel(false);
        }

        Olympiad._validationEnd = Olympiad._olympiadEnd + OlympiadConfig.ALT_OLY_VPERIOD;

        OlympiadDatabase.saveNobleData();
        Olympiad._period = 1;
        Hero.getInstance().clearHeroes();

        try {
            OlympiadDatabase.save();
        } catch (Exception e) {
            _log.error("Olympiad System: Failed to save Olympiad configuration!", e);
        }

        _log.info("Olympiad System: Starting Validation period. Time to end validation:" + Olympiad.getMillisToValidationEnd() / (60 * 1000));

        if (Olympiad._scheduledValdationTask != null) {
            Olympiad._scheduledValdationTask.cancel(false);
        }
        Olympiad._scheduledValdationTask = ThreadPoolManager.getInstance().schedule(new ValidationTask(), Olympiad.getMillisToValidationEnd());
    }
}