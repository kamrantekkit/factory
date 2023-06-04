package com.kamrantekkit.factory.core.grid.energy;

import com.kamrantekkit.factory.entity.CableTileEntity;
import net.minecraft.core.Direction;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

public class EnergySubGrid {
    private final HashMap<CableTileEntity, HashMap<Direction, CableTileEntity>> cablesMap;
    private final ChunkPos chunkPos;

    public EnergySubGrid(ChunkPos chunkPos) {
        cablesMap = new HashMap<>();
        this.chunkPos = chunkPos;
    }

    public void addNode(CableTileEntity cableTile) {
        cablesMap.put(cableTile, new HashMap<>());
        updateMap(cableTile);
    }

    private void updateMap(@NotNull CableTileEntity cableTile) {
        Level level = cableTile.getLevel();
        for (Direction direction : Direction.values()) {
            BlockEntity entity = level.getBlockEntity(cableTile.getBlockPos().relative(direction));
            if (entity == null) continue;
            if (!(entity instanceof CableTileEntity)) return;
            CableTileEntity neighbourCable = (CableTileEntity) entity;
            if (!cablesMap.containsKey(neighbourCable)) continue;
            cablesMap.get(cableTile).put(direction, neighbourCable);
            cablesMap.get(neighbourCable).put(direction.getOpposite(), neighbourCable);
        }
    }


    public ChunkPos getChunkPos() {
        return chunkPos;
    }
}
