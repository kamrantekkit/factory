package com.kamrantekkit.factory.datagen;

import com.kamrantekkit.factory.Factory;
import com.kamrantekkit.factory.setup.ModBlocks;
import com.kamrantekkit.factory.setup.ModItems;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ItemModelsProvider extends ItemModelProvider {


    public ItemModelsProvider(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator, Factory.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        withExistingParent(ModItems.GENERATOR.get().getRegistryName().getPath(), createResourcePath("block/generator"));
        withExistingParent(ModItems.CABLE_ENERGY.get().getRegistryName().getPath(), createResourcePath("block/cable_energy_base"));
    }


    private ResourceLocation createResourcePath(String name) {
        return  new ResourceLocation(Factory.MODID,name);
    }
}
