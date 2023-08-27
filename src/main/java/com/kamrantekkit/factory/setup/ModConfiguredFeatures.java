package com.kamrantekkit.factory.setup;

import com.kamrantekkit.factory.Factory;
import com.kamrantekkit.factory.worldgen.SurfaceOre;
import com.kamrantekkit.factory.worldgen.SurfaceOreConfig;
import net.minecraft.core.Holder;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModConfiguredFeatures {

    public static final ConfiguredFeature<SurfaceOreConfig, ?> OREIRONDEPOSIT = createOreFeature(ModFeatures.SURFACEORE.get(), new SurfaceOreConfig(ModBlocks.ORE_STONE.get().defaultBlockState(),ModBlocks.ORE_IRON.get().defaultBlockState(), 10, 10));


    private static ConfiguredFeature<SurfaceOreConfig, ?> createOreFeature(SurfaceOre feature, SurfaceOreConfig config) {
        return new ConfiguredFeature<>(feature, config);
    }
}
