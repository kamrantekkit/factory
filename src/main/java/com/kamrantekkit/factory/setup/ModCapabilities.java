package com.kamrantekkit.factory.setup;

import com.kamrantekkit.factory.Factory;
import com.kamrantekkit.factory.core.grid.GridManager;
import com.kamrantekkit.factory.core.grid.GridProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.event.AttachCapabilitiesEvent;

public class ModCapabilities {
    public static final Capability<GridManager> GRID_MANAGER_CAPABILITY = CapabilityManager.get(new CapabilityToken<>() {});
    public static void register() {
        MinecraftForge.EVENT_BUS.addGenericListener(Level.class, ModCapabilities::attachCapabilities);

    }

    private static void attachCapabilities(AttachCapabilitiesEvent<Level> event) {
        event.addCapability(new ResourceLocation(Factory.MODID, "grid_manager"), new GridProvider(new GridManager(event.getObject())));
    }
}
