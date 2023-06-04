package com.kamrantekkit.factory.core.grid.energy;

import com.kamrantekkit.factory.entity.CableTileEntity;
import net.minecraft.core.Direction;
import net.minecraft.nbt.Tag;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.EnergyStorage;
import net.minecraftforge.energy.IEnergyStorage;

import javax.annotation.Nonnull;
import java.util.HashMap;

public class EnergyGrid implements INBTSerializable {
    private static final int BASE_CAPACITY = 1000000;
    private static final int TRANSFERRATE = 1500;
    private static final int PER_NODE_CAPACITY = 1500;

    private final EnergyStorage energyStorageHandler = createEnergy();
    private final LazyOptional<IEnergyStorage> energyStorage = LazyOptional.of(() -> energyStorageHandler);
    private HashMap<EnergySubGrid, HashMap<Direction, EnergySubGrid>> subGrids;

    private EnergyGrid() {
        subGrids = new HashMap<>();
    }

    public static EnergyGrid newGrid() {
        return new EnergyGrid();
    }

    private EnergyStorage createEnergy() {
        return new EnergyGridStorage(BASE_CAPACITY, TRANSFERRATE);
    }
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap) {
        if (cap == CapabilityEnergy.ENERGY) {
            return energyStorage.cast();
        }

        return LazyOptional.empty();
    }

    public void addNode(CableTileEntity node) {
        if (node == null) return;
        ChunkPos chunk = node.getLevel().getChunkAt(node.getBlockPos()).getPos();
        EnergySubGrid energySubGrid;
        for (EnergySubGrid subGrid : subGrids.keySet()) {
            if (subGrid.getChunkPos().equals(chunk)) {

                return;
            }
        }
    }
    @Override
    public Tag serializeNBT() {
        return null;
    }

    @Override
    public void deserializeNBT(Tag nbt) {

    }
}
