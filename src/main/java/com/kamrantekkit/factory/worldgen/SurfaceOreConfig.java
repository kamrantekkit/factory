package com.kamrantekkit.factory.worldgen;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;

public class SurfaceOreConfig implements FeatureConfiguration {
    public static final Codec<SurfaceOreConfig> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            BlockState.CODEC.fieldOf("state").forGetter((config) -> {
                return config.state;
            }),
            BlockState.CODEC.fieldOf("decorator_state").forGetter((config) -> {
                return config.decoratorState;
            }),
            Codec.INT.fieldOf("patch_radius").forGetter((config) -> {
                return config.patchRadius;
            }),
            Codec.INT.fieldOf("patch_density").forGetter((config) -> {
                return config.patchDensity;
            })).apply(instance, SurfaceOreConfig::new));


    public BlockState state;
    public BlockState decoratorState;
    public Integer patchRadius;
    public Integer patchDensity;


    public SurfaceOreConfig(BlockState state, BlockState decoratorState, Integer patchRadius, Integer patchDensity) {
        this.state = state;
        this.decoratorState = decoratorState;
        this.patchRadius = patchRadius;
        this.patchDensity = patchDensity;
    }
}
