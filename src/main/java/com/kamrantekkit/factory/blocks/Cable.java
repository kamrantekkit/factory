package com.kamrantekkit.factory.blocks;

import com.kamrantekkit.factory.Factory;
import com.kamrantekkit.factory.blocks.property.ConnectProperty;
import com.kamrantekkit.factory.blocks.property.EnumConnectProperty;
import com.kamrantekkit.factory.entity.CableTileEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class Cable extends BaseEntityBlock {
    public static final EnumProperty<EnumConnectProperty> UP = ConnectProperty.UP;
    public static final EnumProperty<EnumConnectProperty> DOWN = ConnectProperty.DOWN;
    public static final EnumProperty<EnumConnectProperty> NORTH = ConnectProperty.NORTH;
    public static final EnumProperty<EnumConnectProperty> EAST = ConnectProperty.EAST;
    public static final EnumProperty<EnumConnectProperty> SOUTH = ConnectProperty.SOUTH;
    public static final EnumProperty<EnumConnectProperty> WEST = ConnectProperty.WEST;
    public Cable(Properties p_49224_) {
        super(p_49224_.noOcclusion().dynamicShape());
    }

    @Override
    public @NotNull RenderShape getRenderShape(@NotNull BlockState p_49232_) {
        return RenderShape.MODEL;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(@NotNull BlockPos pos, @NotNull BlockState state) {
        return new CableTileEntity(pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type)  {
        if (!level.isClientSide()) {
            return (lvl, pos, stt, te) -> {
                if (te instanceof CableTileEntity cableTile) cableTile.serverTick();
            };
        }
        return null;
    }



//    @Override
//    public void onNeighborChange(BlockState state, LevelReader level, BlockPos pos, BlockPos neighbor) {
//a        if (level.isClientSide()) return;
//        BlockEntity entity = level.getBlockEntity(pos);
//        Factory.getLogger().info("Block Updating at: " + pos);
//        if (entity instanceof CableTileEntity) {
//            ((CableTileEntity) entity).onNeighbourChange(pos, neighbor);
//        }
//        super.onNeighborChange(state,level,pos,neighbor);
//    }

    @Override
    public void setPlacedBy(@NotNull Level world, @NotNull BlockPos pos, @NotNull BlockState state, LivingEntity placer, @NotNull ItemStack stack) {
        CableTileEntity cable = (CableTileEntity) world.getBlockEntity(pos);
        boolean foundGrid = false;
        for (Direction d : Direction.values()) {
            BlockEntity facingTile = world.getBlockEntity(pos.relative(d));
            if (facingTile != null) {
                if (facingTile.getCapability(CapabilityEnergy.ENERGY).isPresent()) {
                    state = state.setValue(ConnectProperty.FACING_TO_PROPERTY_MAP.get(d), EnumConnectProperty.CONNECT);
                    world.setBlockAndUpdate(pos, state);
                } else {
                    state = state.setValue(ConnectProperty.FACING_TO_PROPERTY_MAP.get(d), EnumConnectProperty.NONE);
                    world.setBlockAndUpdate(pos, state);
                }
            }
        }
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.@NotNull Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(UP, DOWN, NORTH, EAST, SOUTH, WEST);
    }


    @Override
    public @NotNull BlockState updateShape(@NotNull BlockState state, @NotNull Direction direction, @NotNull BlockState neighbourState, @NotNull LevelAccessor level, @NotNull BlockPos pos, @NotNull BlockPos neighbourPos) {
        CableTileEntity cableTile = (CableTileEntity) level.getBlockEntity(pos);
        if (cableTile != null) {
            cableTile.onNeighbourChange(neighbourPos,direction);
        }
        return super.updateShape(state, direction, neighbourState, level, pos, neighbourPos);
    }
}
