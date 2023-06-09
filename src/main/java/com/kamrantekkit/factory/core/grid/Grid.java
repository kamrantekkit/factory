package com.kamrantekkit.factory.core.grid;

import net.minecraft.nbt.Tag;
import net.minecraftforge.common.util.INBTSerializable;

import java.util.UUID;

public abstract class Grid implements INBTSerializable {
    private final UUID gridID;

    protected Grid(UUID gridID) {
        this.gridID = gridID;
    }

    @Override
    public Tag serializeNBT() {
        return null;
    }

    @Override
    public void deserializeNBT(Tag nbt) {

    }
}
