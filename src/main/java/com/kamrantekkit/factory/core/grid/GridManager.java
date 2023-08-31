package com.kamrantekkit.factory.core.grid;

import com.google.common.graph.MutableGraph;
import com.kamrantekkit.factory.Factory;
import com.kamrantekkit.factory.core.grid.energy.EnergyGrid;

import com.kamrantekkit.factory.entity.CableTileEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.ListTag;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.util.INBTSerializable;
import org.jetbrains.annotations.NotNull;

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


    public void onNodePlace(@NotNull CableTileEntity cableTile, @NotNull ArrayList<CableTileEntity> neighbours, @NotNull HashSet<EnergyGrid> gridNeighbours) {
        //TODO Merge grids if there is multiple by adding grid check for duplicates in grid neighbours

        EnergyGrid grid = this.createNewGrid();;
//        if (gridNeighbours.isEmpty()) {
//            grid = this.createNewGrid();
//        }
//        else {
//            grid = mergeGrids();
//        }
        grid.addNode(cableTile.getBlockPos());
        cableTile.setGrid(grid);
        for (CableTileEntity neighbour : neighbours) {
            grid.updateNodeNeighbours(cableTile.getBlockPos(), neighbour.getBlockPos());
        }
    }

    public EnergyGrid createNewGrid() {
        EnergyGrid energyGrid = EnergyGrid.newGrid(newUUID(), level, this);
        energyGrids.add(energyGrid);
        return energyGrid;
    }

    public void createNewGrid(MutableGraph<BlockPos> nodeMap) {
        EnergyGrid energyGrid = EnergyGrid.newGrid(newUUID(), level, this);
        energyGrids.add(energyGrid);
        for (BlockPos node : nodeMap.nodes()) {
               energyGrid.addNode(node);
        }
        for (BlockPos node : nodeMap.nodes()) {
            for (BlockPos neighbour : nodeMap.adjacentNodes(node)) {
                energyGrid.updateNodeNeighbours(node, neighbour);
            };
        }
    }

//    public EnergyGrid mergeGrids() {
//
//    }

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
