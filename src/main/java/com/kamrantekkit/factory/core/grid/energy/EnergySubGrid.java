package com.kamrantekkit.factory.core.grid.energy;

import it.unimi.dsi.fastutil.objects.ObjectRBTreeSet;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.ChunkPos;
import org.jetbrains.annotations.NotNull;


public class EnergySubGrid {
    private final ObjectRBTreeSet<BlockPos> cables;
    private final EnergyGrid parentGrid;
    private final ChunkPos chunkPos;

    public EnergySubGrid(ChunkPos chunkPos, EnergyGrid parentGrid) {
        cables = new ObjectRBTreeSet<>();
        this.parentGrid = parentGrid;
        this.chunkPos = chunkPos;
    }

    public void addNode(BlockPos pos) {
        cables.add(pos);
    }


    public void removeNode(@NotNull BlockPos pos) {
        cables.remove(pos);
    }
    public ChunkPos getChunkPos() {
        return chunkPos;
    }

    public Boolean isEmpty() {
        return cables.isEmpty();
    }

}
