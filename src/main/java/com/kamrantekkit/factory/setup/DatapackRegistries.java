package com.kamrantekkit.factory.setup;

import com.kamrantekkit.factory.Factory;
import net.minecraft.core.Holder;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.placement.BiomeFilter;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;
import net.minecraft.world.level.levelgen.placement.RarityFilter;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.NewRegistryEvent;
import net.minecraftforge.registries.RegistryBuilder;

import java.util.List;

@Mod.EventBusSubscriber
public class DatapackRegistries {
//    private final static RegistrySetBuilder BUILDER = new RegistrySetBuilder()
//            .add(Registries.CONFIGURED_FEATURE, context -> {
//                context.register(ResourceKey.create(Registries.CONFIGURED_FEATURE, new ResourceLocation(Factory.MODID, "ore_deposit_iron")), ModConfiguredFeatures.OREIRONDEPOSIT);
//            });
//            .add(Registries.PLACED_FEATURE, context ->  {
//                    context.register(new PlacedFeature(Holder.direct(ModConfiguredFeatures.OREIRONDEPOSIT), List.of(RarityFilter.onAverageOnceEvery(50), BiomeFilter.biome())));
//            });
    @SubscribeEvent
    public void onDatapackRegistyEvent(NewRegistryEvent event) {


    }


}
