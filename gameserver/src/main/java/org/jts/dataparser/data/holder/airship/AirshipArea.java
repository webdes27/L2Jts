package org.jts.dataparser.data.holder.airship;

import org.jts.dataparser.data.annotations.array.IntArray;
import org.jts.dataparser.data.annotations.array.ObjectArray;
import org.jts.dataparser.data.annotations.value.EnumValue;
import org.jts.dataparser.data.annotations.value.IntValue;
import org.jts.dataparser.data.annotations.value.ObjectValue;
import org.jts.dataparser.data.common.Point4;

/**
 * @author : Camelion
 * @date : 24.08.12 20:31
 */
public class AirshipArea {
    @IntArray(name = "map_no")
    public int[] map;
    @ObjectValue
    public AreaType area_type; // Тип области. При type = AIRPORT param = ID
    // Аэропорта. При type = SPEED, значение param
    // неизвествно
    @ObjectArray(canBeNull = false)
    public Point4[] area_range;
    @IntValue
    private int id; // ID бласти

    public static enum Type {
        AIRPORT,
        SPEED
    }

    public static class AreaType {
        @EnumValue(withoutName = true)
        private Type type;
        @IntValue(withoutName = true)
        private int param;
    }
}
