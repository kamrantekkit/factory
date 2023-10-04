package com.kamrantekkit.factory.setup;

import com.kamrantekkit.factory.Factory;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.DataProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.BiomeFilter;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.RarityFilter;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.data.DatapackBuiltinEntriesProvider;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;
import java.util.Set;


@Mod.EventBusSubscriber(modid = Factory.MODID)
public class DatapackRegistries {
    private static final ResourceKey<ConfiguredFeature<?, ?>> ORE_IRON_DEPOSIT_CONFIGURE = ResourceKey.create(Registries.CONFIGURED_FEATURE, new ResourceLocation(Factory.MODID, "ore_deposit_iron_configured"));

    public static final ResourceKey<PlacedFeature> ORE_IRON_DEPOSIT_PLACEMENT  = ResourceKey.create(
            Registries.PLACED_FEATURE,
            new ResourceLocation(Factory.MODID, "ore_deposit_iron_placed")
    );
    private final static RegistrySetBuilder BUILDER = new RegistrySetBuilder()
            .add(Registries.CONFIGURED_FEATURE, context -> {
                context.register(ORE_IRON_DEPOSIT_CONFIGURE,ModConfiguredFeatures.OREIRONDEPOSIT);
            })
            .add(Registries.PLACED_FEATURE, context ->  {
                    HolderGetter<ConfiguredFeature<?, ?>> configured = context.lookup(Registries.CONFIGURED_FEATURE);
                    context.register(ORE_IRON_DEPOSIT_PLACEMENT, ModPlacedFeatures.createPlacement(configured.getOrThrow(ORE_IRON_DEPOSIT_CONFIGURE), 50, ORE_IRON_DEPOSIT_PLACEMENT));
            });

    @SubscribeEvent
    public static void onDatapackRegistyEvent(GatherDataEvent event) {
        Factory.getLogger().info("Factory: Creating Features");
        event.getGenerator().addProvider(
                // Tell generator to run only when server data are generating
                event.includeServer(),
                (DataProvider.Factory<DatapackBuiltinEntriesProvider>) output -> new DatapackBuiltinEntriesProvider(
                        output,
                        event.getLookupProvider(),
                        // The builder containing the datapack registry objects to generate
                        BUILDER,
                        // Set of mod ids to generate the datapack registry objects of
                        Set.of(Factory.MODID)
                )
        );
    }


}
