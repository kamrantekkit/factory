package com.kamrantekkit.factory.core.grid;

import com.kamrantekkit.factory.Factory;
import com.kamrantekkit.factory.core.grid.energy.EnergyGrid;

import net.minecraft.nbt.ListTag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraftforge.common.util.INBTSerializable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class GridManager implements INBTSerializable<ListTag> {
    private final Set<UUID> usedIDs;
    private final Level level;
    private final ArrayList<EnergyGrid> energyGrids;
    public GridManager(Level level) {
        this.level = level;
        energyGrids = new ArrayList<>();
        usedIDs = new HashSet<>();
        Factory.getLogger().info("Grid Manager Instanced for " + level.toString());
    }

    public EnergyGrid createNewGrid() {
        EnergyGrid energyGrid = EnergyGrid.newGrid(newUUID(), level, this);
        energyGrids.add(energyGrid);
        return energyGrid;
    }

    public void removeGrid(EnergyGrid grid) {
        energyGrids.remove(grid);
    }

    @Override
    public ListTag serializeNBT() {
        ListTag gridState = new ListTag();
        return gridState;
    }

    @Override
    public void deserializeNBT(ListTag nbt) {

    }

    private UUID newUUID() {
        while (true) {
            UUID uuid = UUID.randomUUID();
            if (usedIDs.add(uuid)) {
                return uuid;
            }
        }
    }
}
