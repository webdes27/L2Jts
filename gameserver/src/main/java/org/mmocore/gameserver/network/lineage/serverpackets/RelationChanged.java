package org.mmocore.gameserver.network.lineage.serverpackets;


import org.mmocore.gameserver.network.lineage.components.GameServerPacket;
import org.mmocore.gameserver.object.Playable;
import org.mmocore.gameserver.object.Player;

import java.util.ArrayList;
import java.util.List;


public class RelationChanged extends GameServerPacket {
    public static final int RELATION_PARTY1 = 0x00001; // party member
    public static final int RELATION_PARTY2 = 0x00002; // party member
    public static final int RELATION_PARTY3 = 0x00004; // party member
    public static final int RELATION_PARTY4 = 0x00008; // party member (for information, see L2PcInstance.getRelation())
    public static final int RELATION_PARTYLEADER = 0x00010; // true if is party leader
    public static final int RELATION_HAS_PARTY = 0x00020; // true if is in party
    public static final int RELATION_CLAN_MEMBER = 0x00040; // true if is in clan
    public static final int RELATION_LEADER = 0x00080; // true if is clan leader
    public static final int RELATION_CLAN_MATE = 0x00100; // true if is in same clan
    public static final int RELATION_IN_SIEGE = 0x00200; // true if in siege
    public static final int RELATION_ATTACKER = 0x00400; // true when attacker
    public static final int RELATION_ALLY = 0x00800; // blue siege icon, cannot have if red
    public static final int RELATION_ENEMY = 0x01000; // true when red icon, doesn't matter with blue
    public static final int RELATION_MUTUAL_WAR = 0x04000; // double fist
    public static final int RELATION_1SIDED_WAR = 0x08000; // single fist
    public static final int RELATION_ALLY_MEMBER = 0x10000; // clan is in alliance
    public static final int RELATION_IN_DOMINION_WAR = 0x80000; // Territory Wars

    public static final int USER_RELATION_CLAN_MEMBER = 0x20;
    public static final int USER_RELATION_CLAN_LEADER = 0x40;
    public static final int USER_RELATION_IN_SIEGE = 0x80;
    public static final int USER_RELATION_ATTACKER = 0x100;
    public static final int USER_RELATION_IN_DOMINION_WAR = 0x1000;

    protected final List<RelationChangedData> data;

    protected RelationChanged() {
        data = new ArrayList<>(1);
    }

    /**
     * @param targetPlayable игрок, отношение к которому изменилось
     * @param activeChar     игрок, которому будет отослан пакет с результатом
     */
    public static GameServerPacket update(final Player sendTo, final Playable targetPlayable, final Player activeChar) {
        //FIXME [G1ta0] идиотизм
        if (sendTo == null || targetPlayable == null || activeChar == null) {
            return null;
        }

        final Player targetPlayer = targetPlayable.getPlayer();

        final int relation = targetPlayer == null ? 0 : targetPlayer.getRelation(activeChar);

        final RelationChanged pkt = new RelationChanged();

        pkt.add(new RelationChangedData(targetPlayable, targetPlayable.isAutoAttackable(activeChar), relation));
        //if(pet != null)
        //	pkt.add(new RelationChangedData(pet, pet.isAutoAttackable(activeChar), relation));

        return pkt;
    }

    protected void add(final RelationChangedData data) {
        this.data.add(data);
    }

    @Override
    protected void writeData() {
        writeD(data.size());
        for (final RelationChangedData d : data) {
            writeD(d.charObjId);
            writeD(d.relation);
            writeD(d.isAutoAttackable ? 1 : 0);
            writeD(d.karma);
            writeD(d.pvpFlag);
        }
    }

    static class RelationChangedData {
        public final int charObjId;
        public final boolean isAutoAttackable;
        public final int relation, karma, pvpFlag;

        public RelationChangedData(final Playable cha, final boolean _isAutoAttackable, final int _relation) {
            isAutoAttackable = _isAutoAttackable;
            relation = _relation;
            charObjId = cha.getObjectId();
            karma = cha.getKarma();
            pvpFlag = cha.getPvpFlag();
        }
    }
}