package com.kamrantekkit.factory.blocks;

import com.kamrantekkit.factory.blocks.property.ConnectProperty;
import com.kamrantekkit.factory.blocks.property.EnumConnectProperty;
import com.kamrantekkit.factory.core.grid.GridManager;
import com.kamrantekkit.factory.core.grid.energy.EnergyGrid;
import com.kamrantekkit.factory.entity.CableTileEntity;
import com.kamrantekkit.factory.setup.ModCapabilities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashSet;


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

    @Override
    public void onRemove(BlockState state, Level level, BlockPos blockPos, BlockState newState, boolean p_60519_) {
        if(level.isClientSide()) return;
        if (defaultBlockState().getBlock() == newState.getBlock()) return;
        BlockEntity entity = level.getBlockEntity(blockPos);
        if (!(entity instanceof CableTileEntity)) return;
        ((CableTileEntity) entity).getGrid().removeNode(blockPos);
        super.onRemove(state,level, blockPos, newState, p_60519_);
    }

    @Override
    public void setPlacedBy(@NotNull Level world, @NotNull BlockPos pos, @NotNull BlockState state, LivingEntity placer, @NotNull ItemStack stack) {
        if (world.isClientSide) return;
        CableTileEntity cable = (CableTileEntity) world.getBlockEntity(pos);
        if (cable == null) return;
        GridManager gridManager = world.getCapability(ModCapabilities.GRID_MANAGER_CAPABILITY).resolve().get();
        ArrayList<CableTileEntity> neighbours = new ArrayList<>();
        HashSet<EnergyGrid> gridNeighbours = new HashSet<>();
        for (Direction d : Direction.values()) {
            BlockEntity facingTile = world.getBlockEntity(pos.relative(d));
            if (facingTile != null) {
                if (facingTile.getCapability(ForgeCapabilities.ENERGY, d).isPresent()) {
                    if (facingTile instanceof CableTileEntity) {
                        EnergyGrid grid = ((CableTileEntity) facingTile).getGrid();;
                        neighbours.add((CableTileEntity) facingTile);
                        gridNeighbours.add(grid);
                    }
                    state = state.setValue(ConnectProperty.FACING_TO_PROPERTY_MAP.get(d), EnumConnectProperty.CONNECT);
                    world.setBlockAndUpdate(pos, state);
                } else {
                    state = state.setValue(ConnectProperty.FACING_TO_PROPERTY_MAP.get(d), EnumConnectProperty.NONE);
                    world.setBlockAndUpdate(pos, state);
                }
            }
        }
        gridManager.onNodePlace(cable, neighbours, gridNeighbours);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.@NotNull Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(UP, DOWN, NORTH, EAST, SOUTH, WEST);
    }


    @Override
    @Deprecated
    public @NotNull BlockState updateShape(@NotNull BlockState state, @NotNull Direction direction, @NotNull BlockState neighbourState, @NotNull LevelAccessor level, @NotNull BlockPos pos, @NotNull BlockPos neighbourPos) {
        CableTileEntity cableTile = (CableTileEntity) level.getBlockEntity(pos);
        if (cableTile != null) {
            cableTile.onNeighbourChange(neighbourPos,direction);
        }
        return super.updateShape(state, direction, neighbourState, level, pos, neighbourPos);
    }
}
