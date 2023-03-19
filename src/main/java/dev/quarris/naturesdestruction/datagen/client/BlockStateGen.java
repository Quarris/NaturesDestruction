package dev.quarris.naturesdestruction.datagen.client;

import dev.quarris.naturesdestruction.setup.BlockSetup;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;

public class BlockStateGen extends BlockStateProvider {

    public BlockStateGen(DataGenerator gen, String modid, ExistingFileHelper exFileHelper) {
        super(gen, modid, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        String blockId = BlockSetup.EXPLOSION_STABILISER.getId().getPath();
        this.blockAndItem(BlockSetup.EXPLOSION_STABILISER.get(),
            this.models().cubeBottomTop(blockId,
                this.modifiedBlockTexture(blockId, "side"),
                this.modifiedBlockTexture(blockId, "bottom"),
                this.modifiedBlockTexture(blockId, "top")));


        blockId = BlockSetup.CONVERSION_CATALYST.getId().getPath();
        this.blockAndItem(BlockSetup.CONVERSION_CATALYST.get(),
            this.models().cubeColumn(blockId,
                this.modifiedBlockTexture(blockId, "side"),
                this.modifiedBlockTexture(blockId, "end")));

        blockId = BlockSetup.FLUCTUATION_CAPACITOR.getId().getPath();
        this.blockAndItem(BlockSetup.FLUCTUATION_CAPACITOR.get(),
            this.models().cubeColumn(blockId,
                this.modifiedBlockTexture(blockId, "side"),
                this.modifiedBlockTexture(blockId, "end")));
    }

    private ResourceLocation modifiedBlockTexture(String block, String modifier) {
        return this.modLoc("block/" + block + "_" + modifier);
    }

    private void blockAndItem(Block block, ModelFile model) {
        this.simpleBlock(block, model);
        this.simpleBlockItem(block, model);
    }

    @Override
    public String getName() {
        return "Nature's Destruction Block States";
    }
}
