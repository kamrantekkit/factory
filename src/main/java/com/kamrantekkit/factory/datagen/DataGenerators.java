package com.kamrantekkit.factory.datagen;

import com.google.gson.JsonElement;
import com.kamrantekkit.factory.Factory;
import com.kamrantekkit.factory.setup.DatapackRegistries;
import com.kamrantekkit.factory.setup.ModPlacedFeatures;
import com.mojang.serialization.JsonOps;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.RegistryOps;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Map;

@Mod.EventBusSubscriber(modid = Factory.MODID)
public class DataGenerators {
    @SubscribeEvent
    public static void onGatherData(GatherDataEvent event) {
        Factory.getLogger().info("Factory Starting DataGen");


        DataGenerator dataGenerator = event.getGenerator();
        RegistryAccess registryAccess = RegistryAccess.EMPTY;
        RegistryOps<JsonElement> registryOps = RegistryOps.create(JsonOps.INSTANCE, registryAccess);

        dataGenerator.addProvider(event.includeClient(), new BlockStatesProvider(dataGenerator, event.getExistingFileHelper()));
        dataGenerator.addProvider(event.includeClient(), new ItemModelsProvider(dataGenerator, event.getExistingFileHelper()));

        dataGenerator.addProvider(event.includeServer(), new DatapackProvidor(dataGenerator, event.getExistingFileHelper(), registryOps, PackType.SERVER_DATA, "ore", ModPlacedFeatures.getPlacedFeatures()));

    }
}
