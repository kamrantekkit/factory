package com.kamrantekkit.factory.setup;

import com.kamrantekkit.factory.blocks.BaseGenerator;
import com.kamrantekkit.factory.blocks.SurfaceOre;
import com.kamrantekkit.factory.Factory;
import com.kamrantekkit.factory.blocks.Cable;
import com.kamrantekkit.factory.material.Material;

import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Factory.MODID);

    public static final RegistryObject<Block> ORE_STONE = register("ore_stone", () -> new SurfaceOre(Block.Properties.of(Material.SURFACESTONE)));
    public static final RegistryObject<Block> ORE_IRON = register("ore_iron", () -> new SurfaceOre(Block.Properties.of(Material.SURFACESTONE)));
    public static final RegistryObject<Block> GENERATOR = register("generator", () -> new BaseGenerator(Block.Properties.of(net.minecraft.world.level.material.Material.STONE)));
    public static final RegistryObject<Block> CABLE_ENERGY = register("cable_energy", () -> new Cable(Block.Properties.of(net.minecraft.world.level.material.Material.METAL)));
    private static RegistryObject<Block> register(String name, Supplier<Block> block)
    {
        RegistryObject<Block> registryObject = BLOCKS.register(name, block);
        return registryObject;
    }
}
