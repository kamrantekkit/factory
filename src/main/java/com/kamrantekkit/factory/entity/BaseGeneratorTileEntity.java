package com.kamrantekkit.factory.entity;

import com.kamrantekkit.factory.setup.ModEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.EnergyStorage;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class BaseGeneratorTileEntity extends  BaseMachineTileEntity  {

    private static final int POWERGEN_GENERATIONRATE = 20;
    private final ItemStackHandler itemHandler = createHandler();
    private final LazyOptional<IItemHandler> handler = LazyOptional.of(() -> itemHandler);
    private int remainingBurningTicks = 0;

    public BaseGeneratorTileEntity(BlockPos p_155229_, BlockState p_155230_) {
        super(ModEntities.GENERATORENTITY.get(), p_155229_, p_155230_);
    }

    @Override
    public void setRemoved() {
        super.setRemoved();
        handler.invalidate();
        energyStorage.invalidate();
    }

    @Override
    public void serverTick() {
        if (remainingBurningTicks > 0) {
            energyStorageHandler.receiveEnergy(POWERGEN_GENERATIONRATE,false);
            remainingBurningTicks--;
            setChanged();
        }

        if (remainingBurningTicks <= 0) {
            ItemStack stack = itemHandler.getStackInSlot(0);
            int burnTime = ForgeHooks.getBurnTime(stack, RecipeType.SMELTING);
            if (burnTime > 0) {
                itemHandler.extractItem(0, 1, false);
                remainingBurningTicks = burnTime;
                setChanged();
            }
        }
    }


    @Override
    public void load(CompoundTag tag) {
        if (tag.contains("Inventory")) {
            itemHandler.deserializeNBT(tag.getCompound("Inventory"));
        }
        if (tag.contains("Info")) {
            remainingBurningTicks = tag.getCompound("Info").getInt("RemainingBurningTicks");
        }
        super.load(tag);
    }

    @Override
    public void saveAdditional(CompoundTag tag) {
        tag.put("Inventory", itemHandler.serializeNBT());


        CompoundTag infoTag = new CompoundTag();
        infoTag.putInt("RemainingBurningTicks", remainingBurningTicks);
        tag.put("Info", infoTag);
        super.saveAdditional(tag);
    }

    private ItemStackHandler createHandler() {
        return new ItemStackHandler(1) {

            @Override
            protected void onContentsChanged(int slot) {
                // To make sure the TE persists when the chunk is saved later we need to
                // mark it dirty every time the item handler changes
                setChanged();
            }

            @Override
            public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
                return ForgeHooks.getBurnTime(stack, RecipeType.SMELTING) > 0;
            }

            @Nonnull
            @Override
            public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {
                if (ForgeHooks.getBurnTime(stack, RecipeType.SMELTING) <= 0) {
                    return stack;
                }
                return super.insertItem(slot, stack, simulate);
            }
        };
    }



    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if (cap == ForgeCapabilities.ITEM_HANDLER) {
            return handler.cast();
        }

        if (cap == ForgeCapabilities.ENERGY) {
            return energyStorage.cast();
        }
        return super.getCapability(cap, side);
    }
}
