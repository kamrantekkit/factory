package com.kamrantekkit.factory.core.energy;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.EnergyStorage;
import net.minecraftforge.energy.IEnergyStorage;

import javax.annotation.Nonnull;

public class EnergyGrid {

    private static final int BASE_CAPACITY = 50000000;
    private static final int TRANSFERRATE = 1500;

    private final EnergyStorage energyStorageHandler = createEnergy();
    private final LazyOptional<IEnergyStorage> energyStorage = LazyOptional.of(() -> energyStorageHandler);
    public static EnergyGrid newGrid() {
        return new EnergyGrid();
    }

    private EnergyStorage createEnergy() {
        return new EnergyGridStorage(BASE_CAPACITY, TRANSFERRATE);
    }
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap) {

        if (cap == CapabilityEnergy.ENERGY) {
            return energyStorage.cast();
        }
        return LazyOptional.empty();
    }

}
