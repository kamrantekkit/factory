package com.kamrantekkit.factory.setup;

import com.kamrantekkit.factory.Factory;
import com.kamrantekkit.factory.entity.BaseGeneratorTileEntity;
import com.kamrantekkit.factory.entity.CableTileEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, Factory.MODID);

    public static final RegistryObject<BlockEntityType<BaseGeneratorTileEntity>> GENERATORENTITY = BLOCK_ENTITIES.register("generator", () -> BlockEntityType.Builder.of(BaseGeneratorTileEntity::new, ModBlocks.GENERATOR.get()).build(null));
    public static final RegistryObject<BlockEntityType<CableTileEntity>> CABLE_ENERGY_ENTITY = BLOCK_ENTITIES.register("cable_energy", () -> BlockEntityType.Builder.of(CableTileEntity::new, ModBlocks.CABLE_ENERGY.get()).build(null));

}
