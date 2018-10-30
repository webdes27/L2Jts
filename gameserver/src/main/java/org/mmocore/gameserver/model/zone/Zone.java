package org.mmocore.gameserver.model.zone;

import org.jts.dataparser.data.holder.setting.common.PlayerRace;
import org.mmocore.commons.collections.MultiValueSet;
import org.mmocore.commons.listener.Listener;
import org.mmocore.commons.listener.ListenerList;
import org.mmocore.commons.threading.RunnableImpl;
import org.mmocore.commons.utils.Rnd;
import org.mmocore.gameserver.listener.zone.OnZoneEnterLeaveListener;
import org.mmocore.gameserver.model.Territory;
import org.mmocore.gameserver.model.entity.Reflection;
import org.mmocore.gameserver.model.entity.events.EventOwner;
import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.network.lineage.components.IBroadcastPacket;
import org.mmocore.gameserver.network.lineage.components.SystemMsg;
import org.mmocore.gameserver.network.lineage.serverpackets.EventTrigger;
import org.mmocore.gameserver.network.lineage.serverpackets.L2GameServerPacket;
import org.mmocore.gameserver.network.lineage.serverpackets.SystemMessage;
import org.mmocore.gameserver.object.Creature;
import org.mmocore.gameserver.object.GameObject;
import org.mmocore.gameserver.object.Playable;
import org.mmocore.gameserver.object.Player;
import org.mmocore.gameserver.skills.SkillEntry;
import org.mmocore.gameserver.stats.Stats;
import org.mmocore.gameserver.stats.funcs.FuncAdd;
import org.mmocore.gameserver.taskmanager.EffectTaskManager;
import org.mmocore.gameserver.templates.ZoneTemplate;
import org.mmocore.gameserver.utils.Location;
import org.mmocore.gameserver.utils.PositionUtils;
import org.mmocore.gameserver.world.World;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Future;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.stream.Collectors;

public class Zone extends EventOwner {
    public static final Zone[] EMPTY_ZONE_ARRAY = new Zone[0];

    public static final String BLOCKED_ACTION_PRIVATE_STORE = "open_private_store";
    public static final String BLOCKED_ACTION_PRIVATE_WORKSHOP = "open_private_workshop";
    public static final String BLOCKED_ACTION_DROP_MERCHANT_GUARD = "drop_merchant_guard";
    public static final String BLOCKED_ACTION_SAVE_BOOKMARK = "no_save_bookmark";
    public static final String BLOCKED_ACTION_USE_BOOKMARK = "no_use_bookmark";
    public static final String BLOCKED_ACTION_MINIMAP = "no_open_minimap";
    public static final String BLOCKED_ACTION_CALL_PC = "no_call_pc";

    public static final String ACTION_TRANSFORMABLE = "transformable";

    public static final String BLOCKED_ACTION_NOFLY = "no_fly";
    public static final String BLOCKED_ACTION_DROP_ITEM = "no_drop_item";
    /**
     * Ордер в зонах, с ним мы и добавляем/убираем статы.
     * TODO: сравнить ордер с оффом, пока от фонаря
     */
    public static final int ZONE_STATS_ORDER = 0x40;
    private final MultiValueSet<String> params;
    private final ZoneTemplate template;
    private final ZoneListenerList listeners = new ZoneListenerList();
    private final ReadWriteLock lock = new ReentrantReadWriteLock();
    private final Lock readLock = lock.readLock();
    private final Lock writeLock = lock.writeLock();
    private final List<Creature> objects = new ArrayList<>(32);
    private final Map<Creature, ZoneTimer> _zoneTimers = new ConcurrentHashMap<>();
    private ZoneType type;
    private boolean active;
    private Reflection reflection;
    public Zone(final ZoneTemplate template) {
        this(template.getType(), template);
    }

    public Zone(final ZoneType type, final ZoneTemplate template) {
        this.type = type;
        this.template = template;
        params = template.getParams();
    }

    public ZoneTemplate getTemplate() {
        return template;
    }

    public final String getName() {
        return getTemplate().getName();
    }

    public ZoneType getType() {
        return type;
    }

    public void setType(final ZoneType type) {
        this.type = type;
    }

    public Territory getTerritory() {
        return getTemplate().getTerritory();
    }

    public final int getEnteringMessageId() {
        return getTemplate().getEnteringMessageId();
    }

    public final int getLeavingMessageId() {
        return getTemplate().getLeavingMessageId();
    }

    public SkillEntry getZoneSkill() {
        return getTemplate().getZoneSkill();
    }

    public ZoneTarget getZoneTarget() {
        return getTemplate().getZoneTarget();
    }

    public PlayerRace getAffectRace() {
        return getTemplate().getAffectRace();
    }

    /**
     * Номер системного вообщения которое будет отослано игроку при нанесении урона зоной
     *
     * @return SystemMessage ID
     */
    public int getDamageMessageId() {
        return getTemplate().getDamageMessageId();
    }

    /**
     * Сколько урона зона нанесет по хп
     *
     * @return количество урона
     */
    public int getDamageOnHP() {
        return getTemplate().getDamageOnHP();
    }

    /**
     * Сколько урона зона нанесет по мп
     *
     * @return количество урона
     */
    public int getDamageOnMP() {
        return getTemplate().getDamageOnMP();
    }

    /**
     * @return Бонус к скорости движения в зоне
     */
    private double getMoveBonus() {
        return getTemplate().getMoveBonus();
    }

    /**
     * Возвращает бонус регенерации хп в этой зоне
     *
     * @return Бонус регенарации хп в этой зоне
     */
    public double getRegenBonusHP() {
        return getTemplate().getRegenBonusHP();
    }

    /**
     * Возвращает бонус регенерации мп в этой зоне
     *
     * @return Бонус регенарации мп в этой зоне
     */
    public double getRegenBonusMP() {
        return getTemplate().getRegenBonusMP();
    }

    public long getRestartTime() {
        return getTemplate().getRestartTime();
    }

    public List<Location> getRestartPoints() {
        return getTemplate().getRestartPoints();
    }

    public List<Location> getPKRestartPoints() {
        return getTemplate().getPKRestartPoints();
    }

    public Location getSpawn() {
        if (getRestartPoints() == null) {
            return null;
        }
        final Location loc = getRestartPoints().get(Rnd.get(getRestartPoints().size()));
        return loc.clone();
    }

    public Location getPKSpawn() {
        if (getPKRestartPoints() == null) {
            return getSpawn();
        }
        final Location loc = getPKRestartPoints().get(Rnd.get(getPKRestartPoints().size()));
        return loc.clone();
    }

    /**
     * Проверяет находятся ли даные координаты в зоне.
     * _loc - стандартная территория для зоны
     *
     * @param x координата
     * @param y координата
     * @return находятся ли координаты в локации
     */
    public boolean checkIfInZone(final int x, final int y) {
        return getTerritory().isInside(x, y);
    }

    public boolean checkIfInZone(final int x, final int y, final int z) {
        return checkIfInZone(x, y, z, getReflection());
    }

    public boolean checkIfInZone(final int x, final int y, final int z, final Reflection reflection) {
        return isActive() && this.reflection == reflection && getTerritory().isInside(x, y, z);
    }

    public boolean checkIfInZone(final Creature cha) {
        readLock.lock();
        try {
            return objects.contains(cha);
        } finally {
            readLock.unlock();
        }
    }

    public final double findDistanceToZone(final GameObject obj, final boolean includeZAxis) {
        return findDistanceToZone(obj.getX(), obj.getY(), obj.getZ(), includeZAxis);
    }

    public final double findDistanceToZone(final int x, final int y, final int z, final boolean includeZAxis) {
        return PositionUtils.calculateDistance(x, y, z, (getTerritory().getXmax() + getTerritory().getXmin()) / 2, (getTerritory().getYmax() + getTerritory().getYmin()) / 2, (getTerritory().getZmax() + getTerritory().getZmin()) / 2, includeZAxis);
    }

    /**
     * Обработка входа в территорию
     * Персонаж всегда добавляется в список вне зависимости от активности территории.
     * Если зона акивная, то обработается вход в зону
     *
     * @param cha кто входит
     */
    public void doEnter(final Creature cha) {
        boolean added = false;

        writeLock.lock();
        try {
            if (!objects.contains(cha)) {
                added = objects.add(cha);
            }
        } finally {
            writeLock.unlock();
        }

        if (added) {
            onZoneEnter(cha);
        }
    }

    /**
     * Обработка входа в зону
     *
     * @param actor кто входит
     */
    protected void onZoneEnter(final Creature actor) {
        checkEffects(actor, true);
        addZoneStats(actor);

        if (actor.isPlayer()) {
            if (getEnteringMessageId() != 0) {
                actor.sendPacket(new SystemMessage(SystemMsg.valueOf(getEnteringMessageId())));
            }
            if (getTemplate().getEventId() != 0) {
                actor.sendPacket(new EventTrigger(getTemplate().getEventId(), true));
            }
            if (getTemplate().getBlockedActions() != null) {
                ((Player) actor).blockActions(getTemplate().getBlockedActions());
            }
        }

        listeners.onEnter(actor);
    }

    /**
     * Обработка выхода из зоны
     * Object всегда убирается со списка вне зависимости от зоны
     * Если зона активная, то обработается выход из зоны
     *
     * @param cha кто выходит
     */
    public void doLeave(final Creature cha) {
        boolean removed = false;

        writeLock.lock();
        try {
            removed = objects.remove(cha);
        } finally {
            writeLock.unlock();
        }

        if (removed) {
            onZoneLeave(cha);
        }
    }

    /**
     * Обработка выхода из зоны
     *
     * @param actor кто выходит
     */
    protected void onZoneLeave(final Creature actor) {
        checkEffects(actor, false);
        removeZoneStats(actor);

        if (actor.isPlayer()) {
            if (getLeavingMessageId() != 0 && actor.isPlayer()) {
                actor.sendPacket(new SystemMessage(SystemMsg.valueOf(getLeavingMessageId())));
            }
            if (getTemplate().getEventId() != 0 && actor.isPlayer()) {
                actor.sendPacket(new EventTrigger(getTemplate().getEventId(), false));
            }
            if (getTemplate().getBlockedActions() != null) {
                ((Player) actor).unblockActions(getTemplate().getBlockedActions());
            }
        }

        listeners.onLeave(actor);
    }

    /**
     * Добавляет статы зоне
     *
     * @param cha персонаж которому добавляется
     */
    private void addZoneStats(final Creature cha) {
        // Проверка цели
        if (!checkTarget(cha)) {
            return;
        }

        // Скорость движения накладывается только на L2Playable
        // affectRace в базе не указан, если надо будет влияние, то поправим
        if (getMoveBonus() != 0) {
            if (cha.isPlayable()) {
                cha.addStatFunc(new FuncAdd(Stats.RUN_SPEED, ZONE_STATS_ORDER, this, getMoveBonus()));
                cha.sendChanges();
            }
        }

        // Если у нас есть что регенить
        if (getRegenBonusHP() != 0) {
            cha.addStatFunc(new FuncAdd(Stats.REGENERATE_HP_RATE, ZONE_STATS_ORDER, this, getRegenBonusHP()));
        }

        // Если у нас есть что регенить
        if (getRegenBonusMP() != 0) {
            cha.addStatFunc(new FuncAdd(Stats.REGENERATE_MP_RATE, ZONE_STATS_ORDER, this, getRegenBonusMP()));
        }
    }

    /**
     * Убирает добавленые зоной статы
     *
     * @param cha персонаж у которого убирается
     */
    private void removeZoneStats(final Creature cha) {
        if (getRegenBonusHP() == 0 && getRegenBonusMP() == 0 && getMoveBonus() == 0) {
            return;
        }

        cha.removeStatsByOwner(this);

        cha.sendChanges();
    }

    /**
     * Применяет эффекты при входе/выходе из(в) зону
     *
     * @param cha   обьект
     * @param enter вошел или вышел
     */
    private void checkEffects(final Creature cha, final boolean enter) {
        if (checkTarget(cha)) {
            if (enter) {
                if (getZoneSkill() != null) {
                    final ZoneTimer timer = new SkillTimer(cha);
                    _zoneTimers.put(cha, timer);
                    timer.start();
                } else if (getDamageOnHP() > 0 || getDamageOnHP() > 0) {
                    final ZoneTimer timer = new DamageTimer(cha);
                    _zoneTimers.put(cha, timer);
                    timer.start();
                }
            } else {
                final ZoneTimer timer = _zoneTimers.remove(cha);
                if (timer != null) {
                    timer.stop();
                }

                if (getZoneSkill() != null) {
                    cha.getEffectList().stopEffect(getZoneSkill());
                }
            }
        }
    }

    /**
     * Проверяет подходит ли персонаж для вызвавшего действия
     *
     * @param cha персонаж
     * @return подошел ли
     */
    private boolean checkTarget(final Creature cha) {
        switch (getZoneTarget()) {
            case pc:
                if (!cha.isPlayable()) {
                    return false;
                }
                break;
            case only_pc:
                if (!cha.isPlayer()) {
                    return false;
                }
                break;
            case npc:
                if (!cha.isNpc()) {
                    return false;
                }
                break;
        }

        // Если у нас раса не "all"
        if (getAffectRace() != null) {
            final Player player = cha.getPlayer();
            //если не игровой персонаж
            if (player == null) {
                return false;
            }
            // если раса не подходит
            if (player.getPlayerTemplateComponent().getPlayerRace() != getAffectRace()) {
                return false;
            }
        }

        return true;
    }

    public Creature[] getObjects() {
        readLock.lock();
        try {
            return objects.toArray(new Creature[objects.size()]);
        } finally {
            readLock.unlock();
        }
    }

    public List<Player> getInsidePlayers() {
        final List<Player> result = new ArrayList<>();
        readLock.lock();
        try {
            result.addAll(objects.stream().filter(creature -> creature != null && creature.isPlayer()).map(creature -> (Player) creature).collect(Collectors.toList()));
        } finally {
            readLock.unlock();
        }
        return result;
    }

    public List<Playable> getInsidePlayables() {
        final List<Playable> result = new ArrayList<>();
        readLock.lock();
        try {
            result.addAll(objects.stream().filter(creature -> creature != null && creature.isPlayable()).map(creature -> (Playable) creature).collect(Collectors.toList()));
        } finally {
            readLock.unlock();
        }
        return result;
    }

    public List<NpcInstance> getInsideNpcs() {
        final List<NpcInstance> result = new ArrayList<>();
        readLock.lock();
        try {
            result.addAll(objects.stream().filter(object -> object != null && object.isNpc()).map(object -> (NpcInstance) object).collect(Collectors.toList()));
        } finally {
            readLock.unlock();
        }
        return result;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(final boolean value) {
        writeLock.lock();
        try {
            if (active == value) {
                return;
            }
            active = value;
        } finally {
            writeLock.unlock();
        }

        if (isActive()) {
            World.addZone(Zone.this);
        } else {
            World.removeZone(Zone.this);
        }
    }

    public Reflection getReflection() {
        return reflection;
    }

    public void setReflection(final Reflection reflection) {
        this.reflection = reflection;
    }

    public void setParam(final String name, final String value) {
        params.put(name, value);
    }

    public void setParam(final String name, final Object value) {
        params.put(name, value);
    }

    public MultiValueSet<String> getParams() {
        return params;
    }

    public <T extends Listener<Zone>> boolean addListener(final T listener) {
        return listeners.add(listener);
    }

    public <T extends Listener<Zone>> boolean removeListener(final T listener) {
        return listeners.remove(listener);
    }

    @Override
    public final String toString() {
        return "[Zone " + getType() + " name: " + getName() + ']';
    }

    public void broadcastPacket(final L2GameServerPacket packet, final boolean toAliveOnly) {
        final List<Player> insideZoners = getInsidePlayers();

        if (insideZoners != null && !insideZoners.isEmpty()) {
            for (final Player player : insideZoners) {
                if (toAliveOnly) {
                    if (!player.isDead()) {
                        player.broadcastPacket(packet);
                    }
                } else {
                    player.broadcastPacket(packet);
                }
            }
        }
    }

    public void broadcastPacket(final IBroadcastPacket packet, final boolean toAliveOnly) {
        final List<Player> insideZoners = getInsidePlayers();

        if (insideZoners != null && !insideZoners.isEmpty()) {
            for (final Player player : insideZoners) {
                if (toAliveOnly) {
                    if (!player.isDead()) {
                        player.broadcastPacket(packet);
                    }
                } else {
                    player.broadcastPacket(packet);
                }
            }
        }
    }

    /**
     * Таймер зоны
     */
    private abstract class ZoneTimer extends RunnableImpl {
        protected final Creature cha;
        protected Future<?> future;
        protected boolean active;

        ZoneTimer(final Creature cha) {
            this.cha = cha;
        }

        public void start() {
            active = true;
            future = EffectTaskManager.getInstance().schedule(this, getTemplate().getInitialDelay() * 1000L);
        }

        public void stop() {
            active = false;
            if (future != null) {
                future.cancel(false);
                future = null;
            }
        }

        public void next() {
            if (!active) {
                return;
            }
            if (getTemplate().getUnitTick() == 0 && getTemplate().getRandomTick() == 0) {
                return;
            }
            future = EffectTaskManager.getInstance().schedule(this, (getTemplate().getUnitTick() + Rnd.get(0, getTemplate().getRandomTick())) * 1000L);
        }

        @Override
        public abstract void runImpl();
    }

    /**
     * Таймер для наложения эффектов зоны
     */
    private class SkillTimer extends ZoneTimer {
        public SkillTimer(final Creature cha) {
            super(cha);
        }

        @Override
        public void runImpl() {
            if (!isActive()) {
                return;
            }

            if (!checkTarget(cha)) {
                return;
            }

            final SkillEntry skill = getZoneSkill();
            if (skill == null) {
                return;
            }

            if (Rnd.chance(getTemplate().getSkillProb()) && !cha.isDead()) {
                skill.getEffects(cha, cha, false, false);
            }

            next();
        }
    }

    /**
     * Таймер для нанесения урона
     */
    private class DamageTimer extends ZoneTimer {
        public DamageTimer(final Creature cha) {
            super(cha);
        }

        @Override
        public void runImpl() {
            if (!isActive()) {
                return;
            }

            if (!checkTarget(cha)) {
                return;
            }

            final int hp = getDamageOnHP();
            final int mp = getDamageOnMP();
            final int message = getDamageMessageId();

            if (hp == 0 && mp == 0) {
                return;
            }

            if (hp > 0) {
                cha.reduceCurrentHp(hp, cha, null, false, false, true, false, false, false, true);
                if (message > 0) {
                    cha.sendPacket(new SystemMessage(SystemMsg.valueOf(message)).addNumber(hp));
                }
            }

            if (mp > 0) {
                cha.reduceCurrentMp(mp, null);
                if (message > 0) {
                    cha.sendPacket(new SystemMessage(SystemMsg.valueOf(message)).addNumber(mp));
                }
            }

            next();
        }
    }

    public class ZoneListenerList extends ListenerList<Zone> {
        public void onEnter(final Creature actor) {
            for (final OnZoneEnterLeaveListener listener : getListeners(OnZoneEnterLeaveListener.class))
                listener.onZoneEnter(Zone.this, actor);
        }

        public void onLeave(final Creature actor) {
            for (final OnZoneEnterLeaveListener listener : getListeners(OnZoneEnterLeaveListener.class))
                listener.onZoneLeave(Zone.this, actor);
        }
    }
}