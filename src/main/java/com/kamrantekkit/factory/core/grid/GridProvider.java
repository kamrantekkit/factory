package com.kamrantekkit.factory.core.grid;

import com.kamrantekkit.factory.setup.ModCapabilities;
import net.minecraft.core.Direction;
import net.minecraft.nbt.ListTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class GridProvider implements ICapabilityProvider, INBTSerializable<ListTag> {

    private final GridManager instance;
    private LazyOptional<GridManager> instanceOpt;

    public GridProvider(GridManager instance) {
        this.instance = instance;
        this.instanceOpt = LazyOptional.of(() -> instance);

    }

    @Override
    public ListTag serializeNBT() {
        return instance.serializeNBT();
    }

    @Override
    public void deserializeNBT(ListTag nbt) {
        instance.deserializeNBT(nbt);
    }

    @NotNull
    @Override
    public <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (cap == ModCapabilities.GRID_MANAGER_CAPABILITY) {
            return instanceOpt.cast();
        }
        return LazyOptional.empty();
    }

}
