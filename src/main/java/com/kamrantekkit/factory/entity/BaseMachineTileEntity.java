package com.kamrantekkit.factory.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.EnergyStorage;
import net.minecraftforge.energy.IEnergyStorage;

public abstract class BaseMachineTileEntity extends BlockEntity {
    private static final int MACHINE_CAPACITY = 1000000;
    protected final EnergyStorage energyStorageHandler = createEnergy();
    protected final LazyOptional<IEnergyStorage> energyStorage = LazyOptional.of(() -> energyStorageHandler);
    private EnergyStorage createEnergy() {
        return new EnergyStorage(MACHINE_CAPACITY, 200) {
            private void onEnergyChanged() {
                setChanged();
            }
        };
    }

    @Override
    public void load(CompoundTag tag) {
        if (tag.contains("Energy")) {
            energyStorageHandler.deserializeNBT(tag.get("Energy"));
        }
        super.load(tag);
    }

    @Override
    public void saveAdditional(CompoundTag tag) {
        tag.put("Energy", energyStorageHandler.serializeNBT());
        super.saveAdditional(tag);
    }

    public BaseMachineTileEntity(BlockEntityType<?> p_155228_, BlockPos p_155229_, BlockState p_155230_) {
        super(p_155228_, p_155229_, p_155230_);
    }


    public abstract void serverTick();

}
