package com.kamrantekkit.factory.setup;


import net.minecraft.core.Holder;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.placement.*;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static com.kamrantekkit.factory.setup.ModConfiguredFeatures.OREDEPOSIT;


@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class RegisterPlacements {

    public static Holder<PlacedFeature> OREDEPOSITHOLDER = null;
    @SubscribeEvent
    public static void onRegistryLoad(final RegistryEvent.Register<Feature<?>> event) {
        OREDEPOSITHOLDER = PlacementUtils.register("ore_deposit_surface", OREDEPOSIT, new PlacementModifier[]{RarityFilter.onAverageOnceEvery(50), BiomeFilter.biome()});
    }
}
