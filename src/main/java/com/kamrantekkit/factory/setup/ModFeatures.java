package com.kamrantekkit.factory.setup;

import com.kamrantekkit.factory.Factory;
import com.kamrantekkit.factory.worldgen.SurfaceOre;
import com.kamrantekkit.factory.worldgen.SurfaceOreConfig;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.treedecorators.BeehiveDecorator;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class ModFeatures {
    public static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(ForgeRegistries.FEATURES, Factory.MODID);
    public static final RegistryObject<SurfaceOre> SURFACEORE = register("ore_deposit", () ->  new SurfaceOre(SurfaceOreConfig.CODEC));

     private static RegistryObject register(String name, Supplier<Feature> feature) {
         RegistryObject<Feature> featureRegistryObject = FEATURES.register(name, feature);
         return featureRegistryObject;
     }
}
