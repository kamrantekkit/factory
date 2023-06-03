package com.kamrantekkit.factory.setup;


import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraftforge.common.world.BiomeGenerationSettingsBuilder;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.stream.Stream;

@Mod.EventBusSubscriber
public class WorldGenEventHandler {

    @SubscribeEvent
    public static void onBiomeLoad(final BiomeLoadingEvent event) {

        BiomeGenerationSettingsBuilder settings = event.getGeneration();
        if (Stream.of(Biome.BiomeCategory.NETHER, Biome.BiomeCategory.OCEAN, Biome.BiomeCategory.RIVER, Biome.BiomeCategory.NONE, Biome.BiomeCategory.THEEND)
                .noneMatch(category -> category == event.getCategory())) {
            settings.getFeatures(GenerationStep.Decoration.UNDERGROUND_ORES).add(RegisterPlacements.OREDEPOSITHOLDER);
        }
    }
}
