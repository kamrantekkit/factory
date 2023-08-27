package com.kamrantekkit.factory.worldgen;

import com.kamrantekkit.factory.Factory;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;

import java.util.ArrayList;
import java.util.Random;

public class SurfaceOre extends Feature<SurfaceOreConfig>   {
    private final int MAXRADIUS = 2;

    public SurfaceOre(Codec<SurfaceOreConfig> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<SurfaceOreConfig> context) {
        ChunkGenerator chunkGenerator = context.chunkGenerator();
        RandomSource random = context.random();
        WorldGenLevel level = context.level();
        BlockPos pos = context.origin();
        ChunkAccess chunk = level.getChunk(pos);
        SurfaceOreConfig config = context.config();


        BlockPos.MutableBlockPos centerPos = new BlockPos.MutableBlockPos();

        int x = pos.getX() + random.nextInt(16);
        int z = pos.getZ() + random.nextInt(16);
        int y = chunk.getHeight(Heightmap.Types.WORLD_SURFACE, x, z);

        if (y <= 0) {
            return false;
        }

        centerPos.set(x, y, z);
        Factory.getLogger().info("Custom feature placed at " + centerPos.toShortString());

        ArrayList<BlockPos> innerShape = new ArrayList<>();
        ArrayList<BlockPos> outerShape = new ArrayList<>();

//        for (int offsetX = MAXRADIUS*-1; offsetX < MAXRADIUS; offsetX++) {
//            for (int offsetZ = MAXRADIUS * -1; offsetZ < MAXRADIUS; offsetZ++) {
//                for (int yOffset = 0; yOffset < 2; yOffset++) {
//                    BlockPos blockPos = centerPos.offset(offsetX, yOffset * -1, offsetZ);
//                    if (!level.isEmptyBlock(blockPos)) {
//                        depositShape.add(blockPos);
//                        break;
//                    }
//                }
//            }
//        }
        for (int offsetX = MAXRADIUS*-1; offsetX <= MAXRADIUS; offsetX++) {
            for (int offsetZ = MAXRADIUS*-1; offsetZ <= MAXRADIUS; offsetZ++) {
                for (int offsetY = 0; offsetY > -2; offsetY--) {
                    BlockPos blockPos = centerPos.offset(offsetX, offsetY, offsetZ);
                    if (!level.isEmptyBlock(blockPos)) {
                        if (offsetX == MAXRADIUS*-1 || offsetZ == MAXRADIUS*-1 || offsetX == MAXRADIUS || offsetZ == MAXRADIUS) {
                            outerShape.add(blockPos);
                        } else {
                            innerShape.add(blockPos);
                        }
                        break;
                    }
                }
            }
        }

        for (BlockPos blockPos : innerShape) {
            level.setBlock(blockPos, config.decoratorState, 0);
        }

        for (BlockPos blockPos : outerShape) {
            level.setBlock(blockPos, config.state, 0);
        }



        return true;
    }

}
