package com.kamrantekkit.factory.setup;

import com.kamrantekkit.factory.worldgen.SurfaceOreConfig;
import net.minecraft.core.Holder;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;

public class ModConfiguredFeatures {

    public static final Holder<ConfiguredFeature<SurfaceOreConfig, ?>> OREDEPOSIT = FeatureUtils.register("ore_deposit",  ModFeatures.SURFACEORE.get(), new SurfaceOreConfig(ModBlocks.ORE_STONE.get().defaultBlockState(),ModBlocks.ORE_IRON.get().defaultBlockState(), 10, 10));

}
