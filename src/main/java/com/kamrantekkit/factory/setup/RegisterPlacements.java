package com.kamrantekkit.factory.setup;


import net.minecraft.core.Holder;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.placement.*;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.NewRegistryEvent;


@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class RegisterPlacements {

    public static Holder<PlacedFeature> OREDEPOSITHOLDER;
//    @SubscribeEvent
//    public static void onRegistryLoad(final ) {
//        event.
//        OREDEPOSITHOLDER = PlacementUtils.register("ore_deposit_surface", OREDEPOSIT, new PlacementModifier[]{RarityFilter.onAverageOnceEvery(50), BiomeFilter.biome()});
//    }
}
