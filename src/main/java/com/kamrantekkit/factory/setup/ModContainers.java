package com.kamrantekkit.factory.setup;

import com.kamrantekkit.factory.containers.BaseGeneratorContainer;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static com.kamrantekkit.factory.Factory.MODID;

public class ModContainers {
    public static final DeferredRegister<MenuType<?>> CONTAINERS = DeferredRegister.create(ForgeRegistries.CONTAINERS, MODID);

    public static final RegistryObject<MenuType<BaseGeneratorContainer>> GENERATOR = CONTAINERS.register("generator", () -> IForgeMenuType.create((windowId, inv, data) -> new BaseGeneratorContainer(windowId, data.readBlockPos(), inv, inv.player)));



}
