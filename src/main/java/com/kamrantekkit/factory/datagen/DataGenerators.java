package com.kamrantekkit.factory.datagen;

import com.kamrantekkit.factory.Factory;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent;


@Mod.EventBusSubscriber(modid = Factory.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGenerators {

    @SubscribeEvent
    public static void onGatherData(GatherDataEvent event) {
        DataGenerator dataGenerator = event.getGenerator();
        dataGenerator.addProvider(new BlockStatesProvider(dataGenerator, event.getExistingFileHelper()));
        dataGenerator.addProvider(new ItemModelsProvider(dataGenerator, event.getExistingFileHelper()));

    }
}
