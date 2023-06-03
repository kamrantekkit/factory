package com.kamrantekkit.factory.blocks.property;

import net.minecraft.util.StringRepresentable;

public enum EnumConnectProperty implements StringRepresentable {
    NONE("none"),
    CONNECT("connect");

    private final String name;
    EnumConnectProperty(String name) {
        this.name = name;
    }

    @Override
    public String getSerializedName() {
        return this.name;
    }
}
