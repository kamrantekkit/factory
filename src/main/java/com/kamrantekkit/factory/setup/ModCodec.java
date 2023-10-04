package com.kamrantekkit.factory.setup;

import com.kamrantekkit.factory.Factory;
import com.kamrantekkit.factory.worldgen.OreBiomeModifor;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModCodec {
    public static DeferredRegister<Codec<? extends BiomeModifier>> BIOME_MODIFIER_SERIALIZERS =
            DeferredRegister.create(ForgeRegistries.Keys.BIOME_MODIFIER_SERIALIZERS, Factory.MODID);

    public static RegistryObject<Codec<OreBiomeModifor>> ORE_CODEC = BIOME_MODIFIER_SERIALIZERS.register("ore_codec", () ->
            RecordCodecBuilder.create(builder -> builder.group(
                    Biome.LIST_CODEC.fieldOf("biomes").forGetter(OreBiomeModifor::biomes),
                    PlacedFeature.CODEC.fieldOf("feature").forGetter(OreBiomeModifor::feature)
            ).apply(builder, OreBiomeModifor::new)));
}
