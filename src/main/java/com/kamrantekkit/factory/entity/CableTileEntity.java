package com.kamrantekkit.factory.entity;

import com.kamrantekkit.factory.Factory;
import com.kamrantekkit.factory.blocks.property.ConnectProperty;
import com.kamrantekkit.factory.blocks.property.EnumConnectProperty;
import com.kamrantekkit.factory.core.grid.energy.EnergyGrid;
import com.kamrantekkit.factory.setup.ModEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.HashMap;

public class CableTileEntity extends BlockEntity {
    private static final int ENERGY_CAPACITY =  500;
    private EnergyGrid energyGrid;
    private HashMap<Direction, BlockEntity> energyEntities;
    public CableTileEntity(BlockPos p_155229_, BlockState p_155230_) {
        super(ModEntities.CABLE_ENERGY_ENTITY.get(),p_155229_, p_155230_);
    }
//    public Boolean checkForExistingGrid(CableTileEntity cableTile) {
//        if (cableTile.hasGrid()) {
//            Factory.getLogger().info("Cable at: " + worldPosition + "Found grid");
//            EnergyGrid grid = cableTile.getGrid();
//            grid.addNode(getBlockPos());
//            energyGrid = grid;
//            return true;
//        }
//        return false;
//    }

    public EnumConnectProperty getConnectState(Direction direction) {
        return getBlockState().getValue(ConnectProperty.FACING_TO_PROPERTY_MAP.get(direction));
    }
    public void updateConnectState(Direction direction, EnumConnectProperty connectProperty) {
       BlockState state = getBlockState().setValue(ConnectProperty.FACING_TO_PROPERTY_MAP.get(direction), connectProperty);
        if (level != null) {
            level.setBlockAndUpdate(worldPosition, state);
        }
    }

    @Override
    public void setRemoved() {
        super.setRemoved();
    }

    public void onNeighbourChange( @NotNull BlockPos pos, @NotNull Direction direction) {
        if (level == null) return;
        if (level.isEmptyBlock(pos)) {
            updateConnectState(direction, EnumConnectProperty.NONE);
            return;
        }
        BlockEntity entity = level.getBlockEntity(pos);
        if (entity != null) {
            if (entity instanceof CableTileEntity) {
                updateConnectState(direction, EnumConnectProperty.CONNECT);
                if (level.isClientSide) return;
                energyGrid.updateNodeNeighbours(worldPosition, pos);
            } else if (entity.getCapability(ForgeCapabilities.ENERGY, direction).isPresent()) {
                updateConnectState(direction, EnumConnectProperty.CONNECT);
            }
        }
    }

    private Direction getSide(BlockPos pos) {
        Factory.getLogger().info("Getting side from: " + pos);
        if (pos.getY() < 0) {
            return Direction.DOWN;
        }
        if (pos.getY() > 0) {
            return Direction.UP;
        }
        if (pos.getZ() < 0) {
            return Direction.NORTH;
        }
        if (pos.getZ() > 0) {
            return Direction.SOUTH;
        }
        if (pos.getX() < 0) {
            return Direction.WEST;
        }
        if (pos.getX() > 0) {
            return Direction.EAST;
        }

        return null;
    }

    public void serverTick() {

        //if (energyGrid.getCapability().)
    }

    public void extractPower() {

    }

    @NotNull
    @Override
    public <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (cap == ForgeCapabilities.ENERGY && side != null) {
            if (getConnectState(side) != EnumConnectProperty.CONNECT) updateConnectState(side.getOpposite(), EnumConnectProperty.CONNECT);
            return energyGrid.getCapability(cap);
        }
        return super.getCapability(cap, side);
    }

    public EnergyGrid getGrid() {
        return energyGrid;
    }

    public void setGrid(EnergyGrid energyGrid) {
        this.energyGrid = energyGrid;
    }

    public Boolean hasGrid() {
        return energyGrid != null;
    }
}
