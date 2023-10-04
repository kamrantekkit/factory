package com.kamrantekkit.factory.setup;

import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.BiomeFilter;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.RarityFilter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ModPlacedFeatures {

    private final static HashMap<ResourceKey<PlacedFeature>, PlacedFeature> PLACED_FEATURES = new HashMap<>();

    public static HashMap<ResourceKey<PlacedFeature>, PlacedFeature> getPlacedFeatures() {
        return PLACED_FEATURES;
    }
    public static PlacedFeature createPlacement(Holder<ConfiguredFeature<?, ?>> configFeature, int rarity, ResourceKey<PlacedFeature> key ) {
        PlacedFeature feature = new PlacedFeature(configFeature, List.of(RarityFilter.onAverageOnceEvery(rarity), BiomeFilter.biome()));
        PLACED_FEATURES.put(key, feature);
        return feature;
    }
}
