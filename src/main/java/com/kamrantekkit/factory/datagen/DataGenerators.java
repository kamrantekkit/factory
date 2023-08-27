package com.kamrantekkit.factory.datagen;

import com.kamrantekkit.factory.Factory;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;


@Mod.EventBusSubscriber(modid = Factory.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGenerators {

    @SubscribeEvent
    public static void onGatherData(GatherDataEvent event) {
        DataGenerator dataGenerator = event.getGenerator();
        dataGenerator.addProvider(event.includeClient(), new BlockStatesProvider(dataGenerator, event.getExistingFileHelper()));
        dataGenerator.addProvider(event.includeClient(), new ItemModelsProvider(dataGenerator, event.getExistingFileHelper()));


    }
}
