package com.kamrantekkit.factory.entity;

import com.kamrantekkit.factory.Factory;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class DummyMachineTileEntity extends BaseMachineTileEntity {
    private static final int MACHINE_CAPACITY = 99999999;
    private int counter = 0;
    public DummyMachineTileEntity(BlockEntityType<?> p_155228_, BlockPos p_155229_, BlockState p_155230_) {
        super(p_155228_, p_155229_, p_155230_);
    }

    @Override
    public void serverTick() {
        if (counter >= 20) {
            counter = 0;
            Factory.getLogger().info("Current Energy: " + energyStorageHandler.getEnergyStored());
        }
        counter++;
    }


    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if (cap == ForgeCapabilities.ENERGY) {
            return energyStorage.cast();
        }

        return super.getCapability(cap, side);
    }
}
