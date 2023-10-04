package com.kamrantekkit.factory.datagen;

import com.google.gson.JsonElement;
import com.kamrantekkit.factory.setup.ModCodec;
import com.kamrantekkit.factory.worldgen.OreBiomeModifor;
import com.mojang.serialization.DynamicOps;
import net.minecraft.data.DataGenerator;
import net.minecraft.server.packs.PackType;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.JsonCodecProvider;

import java.util.Map;

public class DatapackProvidor extends JsonCodecProvider<OreBiomeModifor> {
    public DatapackProvidor(DataGenerator gen, ExistingFileHelper existingFileHelper, DynamicOps<JsonElement> dynamicOps, PackType packType, String directory, Map entries ) {
        super(gen.getPackOutput(), existingFileHelper, com.kamrantekkit.factory.Factory.MODID, dynamicOps, packType, directory, ModCodec.ORE_CODEC.get(), entries);
    }


}
