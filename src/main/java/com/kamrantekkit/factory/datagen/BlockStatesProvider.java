package com.kamrantekkit.factory.datagen;

import com.kamrantekkit.factory.Factory;
import com.kamrantekkit.factory.blocks.property.ConnectProperty;
import com.kamrantekkit.factory.blocks.property.EnumConnectProperty;
import com.kamrantekkit.factory.setup.ModBlocks;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraftforge.client.model.generators.*;
import net.minecraftforge.common.data.ExistingFileHelper;

public class BlockStatesProvider extends BlockStateProvider {
    private final ExistingFileHelper exFileHelper;

    public BlockStatesProvider(DataGenerator gen, ExistingFileHelper exFileHelper) {
        super(gen, Factory.MODID, exFileHelper);
        this.exFileHelper = exFileHelper;
    }

    @Override
    protected void registerStatesAndModels() {
        createSimpleMachine(ModBlocks.GENERATOR.get());
        createCable(ModBlocks.CABLE_ENERGY.get());
    }

    private void createSimpleMachine(Block block) {
        BlockModelBuilder model = models().cube(block.getRegistryName().getPath(),
                createResourcePath("generator_side"),
                createResourcePath("generator_side"),
                createResourcePath("generator_front"),
                createResourcePath("generator_side"),
                createResourcePath("generator_side"),
                createResourcePath("generator_side")
        );

        horizontalBlock(block, model);
    }

    private void createCable(Block block) {
        BlockModelBuilder cableBase = new BlockModelBuilder(createModelPath(block.getRegistryName().getPath() + "_base"), exFileHelper);
        BlockModelBuilder cableConnection = new BlockModelBuilder(createModelPath(block.getRegistryName().getPath() + "_connection"), exFileHelper);


        MultiPartBlockStateBuilder multipart = getMultipartBuilder(block)
                .part().modelFile(cableBase).addModel().end()
                .part().modelFile(cableConnection).addModel().condition(ConnectProperty.NORTH, EnumConnectProperty.CONNECT).end()
                .part().rotationX(90).modelFile(cableConnection).addModel().condition(ConnectProperty.UP, EnumConnectProperty.CONNECT).end()
                .part().rotationX(180).modelFile(cableConnection).addModel().condition(ConnectProperty.SOUTH, EnumConnectProperty.CONNECT).end()
                .part().rotationX(270).modelFile(cableConnection).addModel().condition(ConnectProperty.DOWN, EnumConnectProperty.CONNECT).end()
                .part().rotationY(90).modelFile(cableConnection).addModel().condition(ConnectProperty.EAST, EnumConnectProperty.CONNECT).end()
                .part().rotationY(270).modelFile(cableConnection).addModel().condition(ConnectProperty.WEST, EnumConnectProperty.CONNECT).end();
    }

    private ResourceLocation createResourcePath(String name) {
       return new ResourceLocation(Factory.MODID,name);
    }

    private ResourceLocation createModelPath(String name) {
        return new ResourceLocation(Factory.MODID, "/block/"+ name);
    }

}
