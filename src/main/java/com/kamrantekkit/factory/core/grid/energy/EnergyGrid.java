package com.kamrantekkit.factory.core.grid.energy;

import com.google.common.graph.ElementOrder;
import com.google.common.graph.GraphBuilder;
import com.google.common.graph.MutableGraph;
import com.kamrantekkit.factory.Factory;
import com.kamrantekkit.factory.core.grid.Grid;
import com.kamrantekkit.factory.core.grid.GridManager;
import it.unimi.dsi.fastutil.objects.Object2ObjectSortedMap;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.Tag;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.EnergyStorage;
import net.minecraftforge.energy.IEnergyStorage;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.*;

public class EnergyGrid extends Grid {
    private static final int BASE_CAPACITY = 1000000;
    private static final int TRANSFERRATE = 1500;
    private static final int PER_NODE_CAPACITY = 1500;
    private final EnergyStorage energyStorageHandler = createEnergy();
    private final LazyOptional<IEnergyStorage> energyStorage = LazyOptional.of(() -> energyStorageHandler);
    private final MutableGraph<BlockPos> nodes;
    private final HashMap <BlockPos, EnergySubGrid> nodesSubGrids;
    private final HashMap<ChunkPos, EnergySubGrid> subGrids;
    private final Level level;
    private final GridManager gridManager;


    private EnergyGrid(UUID gridID, Level level, GridManager gridManager) {
        super(gridID);
        this.level = level;
        this.gridManager = gridManager;

        subGrids = new HashMap<>();
        nodesSubGrids = new HashMap<>();
        nodes = GraphBuilder.undirected().nodeOrder(ElementOrder.unordered()).build();
    }

    public static EnergyGrid newGrid(UUID uuid, Level level, GridManager gridManager) {
        return new EnergyGrid(uuid, level, gridManager);
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

    public void addNode(@NotNull BlockPos pos) {
        ChunkPos chunk = level.getChunk(pos).getPos();

        EnergySubGrid energySubGrid = null;
        if (subGrids.containsKey(chunk)) {
            energySubGrid = subGrids.get(chunk);
        } else {
            energySubGrid = new EnergySubGrid(chunk, this);
            subGrids.put(chunk, energySubGrid);
        }
        nodes.addNode(pos);
        energySubGrid.addNode(pos);
        nodesSubGrids.put(pos, energySubGrid);
    }

    public LevelAccessor getLevel() {
        return level;
    }

    public void removeNode(@NotNull BlockPos pos) {
        if (!nodesSubGrids.containsKey(pos)) return;
        EnergySubGrid energySubGrid = nodesSubGrids.get(pos);
        Set<BlockPos> neighbourNodes = nodes.adjacentNodes(pos);
        nodes.removeNode(pos);
        energySubGrid.removeNode(pos);
        if (neighbourNodes.size() > 1) {
            seperateGrid(neighbourNodes);
        }
        if (energySubGrid.isEmpty()) {
            subGrids.remove(energySubGrid.getChunkPos());
        }
        if (nodesSubGrids.isEmpty()) {
            gridManager.removeGrid(this);
        }
    }

    private void seperateGrid(Set<BlockPos> disconnectedNodes) {
        ArrayList<ArrayList<BlockPos>> foundBranches = new ArrayList<>();
        for (BlockPos pos : disconnectedNodes) {
            Boolean duplicateBranch = false;
            //Finds if a cable loops back for whatever reason
            for (ArrayList<BlockPos> branch : foundBranches) {
                if (branch.contains(pos)) {
                    duplicateBranch = true;
                    break;
                }
            }
            if (duplicateBranch) continue;
            ArrayList<BlockPos> foundNodes = searchBranch(pos);
            foundBranches.add(foundNodes);
            Factory.getLogger().info("Starting Branch Search at " + pos);
            for (BlockPos node : foundNodes) {
                Factory.getLogger().info(node.toShortString());
            }
        }
    }

    private ArrayList<BlockPos> searchBranch(BlockPos pos) {
        return searchBranch(pos, null);
    }

    /**
     * @param startNode Start Point to searching
     * @param branchNode everytime the branch split, the node where it split from is added so it does go back on itself
     * @return
     */
    private ArrayList<BlockPos> searchBranch(BlockPos startNode, @Nullable BlockPos branchNode) {
        ArrayList<BlockPos> foundNodes = new ArrayList<>();
        foundNodes.add(startNode);
        while (true) {
            Set<BlockPos> adjacentNodes =  new HashSet<>(nodes.adjacentNodes(startNode));
            if (branchNode != null) {
                adjacentNodes.remove(branchNode);
            }
            if (foundNodes.stream().anyMatch(adjacentNodes::contains)) foundNodes.forEach(adjacentNodes::remove);
            if (adjacentNodes.isEmpty()) {
                return foundNodes;
            } else {
                for (BlockPos blockPos : adjacentNodes) {
                    if (adjacentNodes.size() == 1) {
                        foundNodes.add(blockPos);
                        startNode = blockPos;
                    } else {
                        foundNodes.addAll(searchBranch(blockPos, startNode));
                    }
                }
            }
        }
    }

    public void updateNodeNeighbours(BlockPos pos, BlockPos neighbourPos) {
        nodes.putEdge(pos, neighbourPos);
    }

    @Override
    public Tag serializeNBT() {
        return null;
    }

    @Override
    public void deserializeNBT(Tag nbt) {

    }
}
