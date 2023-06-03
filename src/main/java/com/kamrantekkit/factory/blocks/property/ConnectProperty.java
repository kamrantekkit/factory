package com.kamrantekkit.factory.blocks.property;

import com.google.common.collect.Maps;
import net.minecraft.Util;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.properties.EnumProperty;

import java.util.Map;

public class ConnectProperty {
    public static final EnumProperty<EnumConnectProperty> UP  = EnumProperty.create("up",  EnumConnectProperty.class);
    public static final EnumProperty<EnumConnectProperty> DOWN  = EnumProperty.create("down",  EnumConnectProperty.class);
    public static final EnumProperty<EnumConnectProperty> NORTH  = EnumProperty.create("north",  EnumConnectProperty.class);
    public static final EnumProperty<EnumConnectProperty> EAST  = EnumProperty.create("east",  EnumConnectProperty.class);
    public static final EnumProperty<EnumConnectProperty> SOUTH  = EnumProperty.create("south",  EnumConnectProperty.class);
    public static final EnumProperty<EnumConnectProperty> WEST = EnumProperty.create("west",  EnumConnectProperty.class);

    public static final Map<Direction, EnumProperty<EnumConnectProperty>> FACING_TO_PROPERTY_MAP =  Util.make(Maps.newEnumMap(Direction.class), (p) -> {
        p.put(Direction.NORTH, NORTH);
        p.put(Direction.EAST, EAST);
        p.put(Direction.SOUTH, SOUTH);
        p.put(Direction.WEST, WEST);
        p.put(Direction.UP, UP);
        p.put(Direction.DOWN, DOWN);
    });


}
