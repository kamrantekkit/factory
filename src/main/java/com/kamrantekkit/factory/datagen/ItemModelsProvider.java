package com.kamrantekkit.factory.datagen;

import com.kamrantekkit.factory.setup.ModItems;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ItemModelsProvider extends ItemModelProvider {


    public ItemModelsProvider(DataGenerator gen, ExistingFileHelper existingFileHelper) {
        super(gen.getPackOutput(), com.kamrantekkit.factory.Factory.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        System.out.println(createResourcePath("cable_energy_base"));
        withExistingParent(ModItems.GENERATOR.getId().toString(), createResourcePath("generator"));
        withExistingParent(ModItems.CABLE_ENERGY.getId().toString(), createResourcePath("cable_energy_base"));
    }


    private ResourceLocation createResourcePath(String name) {
        return  new ResourceLocation(com.kamrantekkit.factory.Factory.MODID,"block/" + name);
    }
}
