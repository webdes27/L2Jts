package org.mmocore.gameserver.network.authcomm;

/**
 * @author VISTALL
 * @date 21:12/28.06.2011
 */
public enum ServerType {
    NORMAL,
    RELAX,
    TEST,
    NO_LABEL,
    RESTRICTED,
    EVENT,
    FREE;

    private final int _mask;

    ServerType() {
        _mask = 1 << ordinal();
    }

    public int getMask() {
        return _mask;
    }
}
