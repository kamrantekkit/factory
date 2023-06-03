package com.kamrantekkit.factory;

import com.kamrantekkit.factory.datagen.BlockStatesProvider;
import com.kamrantekkit.factory.datagen.DataGenerators;
import com.kamrantekkit.factory.datagen.ItemModelsProvider;
import com.kamrantekkit.factory.setup.*;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.*;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(Factory.MODID)
public class Factory {

    public static final String MODID = "factory";

    // Directly reference a log4j logger.
    private static final Logger LOGGER = LogManager.getLogger();

    public Factory() {

        final IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);


        ModBlocks.BLOCKS.register(modEventBus);
        ModItems.ITEMS.register(modEventBus);
        ModFeatures.FEATURES.register(modEventBus);
        ModEntities.BLOCK_ENTITIES.register(modEventBus);
        ModContainers.CONTAINERS.register(modEventBus);

    }

    public static Logger getLogger() {
        return LOGGER;
    }

    private void setup(final FMLCommonSetupEvent event) {

    }



    private void enqueueIMC(final InterModEnqueueEvent event) {
        // some example code to dispatch IMC to another mod
    }

    private void processIMC(final InterModProcessEvent event) {
        // some example code to receive and process InterModComms from other mods


    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(FMLDedicatedServerSetupEvent event) {
        // do something when the server starts
        LOGGER.info("HELLO from server starting");
    }


}
