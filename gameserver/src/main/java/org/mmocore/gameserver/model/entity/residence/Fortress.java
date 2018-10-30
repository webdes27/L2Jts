package org.mmocore.gameserver.model.entity.residence;

import org.mmocore.commons.database.dao.JdbcEntityState;
import org.mmocore.commons.database.dbutils.DbUtils;
import org.mmocore.gameserver.data.xml.holder.ResidenceHolder;
import org.mmocore.gameserver.database.DatabaseFactory;
import org.mmocore.gameserver.database.dao.impl.ClanDataDAO;
import org.mmocore.gameserver.database.dao.impl.FortressDAO;
import org.mmocore.gameserver.model.pledge.Clan;
import org.mmocore.gameserver.network.lineage.components.SystemMsg;
import org.mmocore.gameserver.templates.StatsSet;
import org.mmocore.gameserver.templates.item.ItemTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

public class Fortress extends Residence {
    public static final long CASTLE_FEE = 25000;
    // type
    public static final int DOMAIN = 0;
    public static final int BOUNDARY = 1;
    // state
    public static final int NOT_DECIDED = 0;
    public static final int INDEPENDENT = 1;
    public static final int CONTRACT_WITH_CASTLE = 2;
    // facility
    public static final int REINFORCE = 0;
    public static final int GUARD_BUFF = 1;
    public static final int DOOR_UPGRADE = 2;
    public static final int DWARVENS = 3;
    public static final int SCOUT = 4;
    public static final int FACILITY_MAX = 5;
    private static final Logger _log = LoggerFactory.getLogger(Fortress.class);
    private static final long REMOVE_CYCLE = 7 * 24; // 7 дней форт может пренадлежать овнеру
    private static final long REWARD_CYCLE = 6; // каждых 6 часов
    private final int[] _facilities = new int[FACILITY_MAX];
    private final List<Castle> _relatedCastles = new ArrayList<>(5);
    // envoy
    private int _state;
    private int _castleId;
    private int _supplyCount;

    public Fortress(final StatsSet set) {
        super(set);
    }

    @Override
    public void changeOwner(final Clan clan) {
        // Если клан уже владел каким-либо замком/крепостью, отбираем его.
        if (clan != null) {
            if (clan.getHasFortress() != 0) {
                final Fortress oldFortress = ResidenceHolder.getInstance().getResidence(Fortress.class, clan.getHasFortress());
                if (oldFortress != null) {
                    oldFortress.changeOwner(null);
                }
            }
            if (clan.getCastle() != 0) {
                final Castle oldCastle = ResidenceHolder.getInstance().getResidence(Castle.class, clan.getCastle());
                if (oldCastle != null) {
                    oldCastle.changeOwner(null);
                }
            }
        }

        // Если этой крепостью уже кто-то владел, отбираем у него крепость
        if (getOwnerId() > 0 && (clan == null || clan.getClanId() != getOwnerId())) {
            // Удаляем фортовые скилы у старого владельца
            removeSkills();
            final Clan oldOwner = getOwner();
            if (oldOwner != null) {
                oldOwner.setHasFortress(0);
            }

            cancelCycleTask();
            clearFacility();
        }

        // Выдаем крепость новому владельцу
        if (clan != null) {
            clan.setHasFortress(getId());
        }

        // Сохраняем в базу
        updateOwnerInDB(clan);

        // Выдаем фортовые скилы новому владельцу
        rewardSkills();

        setFortState(NOT_DECIDED, 0);
        setJdbcState(JdbcEntityState.UPDATED);

        update();
    }

    @Override
    protected void loadData() {
        owner = ClanDataDAO.getInstance().getOwner(this);
        FortressDAO.getInstance().select(this);
    }

    private void updateOwnerInDB(final Clan clan) {
        owner = clan;

        Connection con = null;
        PreparedStatement statement = null;
        try {
            con = DatabaseFactory.getInstance().getConnection();
            statement = con.prepareStatement("UPDATE clan_data SET hasFortress=0 WHERE hasFortress=? LIMIT 1");
            statement.setInt(1, getId());
            statement.execute();
            DbUtils.close(statement);

            if (clan != null) {
                statement = con.prepareStatement("UPDATE clan_data SET hasFortress=? WHERE clan_id=? LIMIT 1");
                statement.setInt(1, getId());
                statement.setInt(2, getOwnerId());
                statement.execute();

                clan.broadcastClanStatus(true, false, false);
            }
        } catch (Exception e) {
            _log.error("", e);
        } finally {
            DbUtils.closeQuietly(con, statement);
        }
    }

    public void setFortState(final int state, final int castleId) {
        _state = state;
        _castleId = castleId;
    }

    public int getCastleId() {
        return _castleId;
    }

    public int getContractState() {
        return _state;
    }

    @Override
    public void chanceCycle() {
        super.chanceCycle();
        if (getCycle() >= REMOVE_CYCLE) {
            getOwner().broadcastToOnlineMembers(SystemMsg.ENEMY_BLOOD_PLEDGES_HAVE_INTRUDED_INTO_THE_FORTRESS);
            changeOwner(null);
            return;
        }

        setPaidCycle(getPaidCycle() + 1);
        // если кратно REWARD_CYCLE то добавляем ревард
        if (getPaidCycle() >= REWARD_CYCLE) {
            setPaidCycle(0);
            setRewardCount(getRewardCount() + 1);

            if (getContractState() == CONTRACT_WITH_CASTLE) {
                final Castle castle = ResidenceHolder.getInstance().getResidence(Castle.class, _castleId);
                if (castle.getOwner() == null || castle.getOwner().getReputationScore() < 2 || owner.getWarehouse().getCountOf(ItemTemplate.ITEM_ID_ADENA) > CASTLE_FEE) {
                    setSupplyCount(0);
                    setFortState(INDEPENDENT, 0);
                    clearFacility();
                } else {
                    if (_supplyCount < 6) {
                        _supplyCount++;
                    }

                    castle.getOwner().incReputation(-2, false, "Fortress:chanceCycle():" + getId());
                    owner.getWarehouse().destroyItemByItemId(ItemTemplate.ITEM_ID_ADENA, CASTLE_FEE);
                }
            }
        }
    }

    @Override
    public void update() {
        FortressDAO.getInstance().update(this);
    }

    public int getSupplyCount() {
        return _supplyCount;
    }

    public void setSupplyCount(final int c) {
        _supplyCount = c;
    }

    public int getFacilityLevel(final int type) {
        return _facilities[type];
    }

    public void setFacilityLevel(final int type, final int val) {
        _facilities[type] = val;
    }

    public void clearFacility() {
        for (int i = 0; i < _facilities.length; i++) {
            _facilities[i] = 0;
        }
    }

    public int[] getFacilities() {
        return _facilities;
    }

    public void addRelatedCastle(final Castle castle) {
        _relatedCastles.add(castle);
    }

    public List<Castle> getRelatedCastles() {
        return _relatedCastles;
    }
}