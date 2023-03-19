package dev.quarris.naturesdestruction.datagen.server;

import dev.quarris.naturesdestruction.setup.BlockSetup;
import dev.quarris.naturesdestruction.setup.TagSetup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

public class BlockTagGen extends BlockTagsProvider {
    public BlockTagGen(DataGenerator gen, String id, @Nullable ExistingFileHelper existingFiles) {
        super(gen, id, existingFiles);
    }

    @Override
    protected void addTags() {
        this.tag(BlockTags.MINEABLE_WITH_PICKAXE)
            .add(BlockSetup.EXPLOSION_STABILISER.getKey())
            .add(BlockSetup.FLUCTUATION_CAPACITOR.getKey())
            .add(BlockSetup.CONVERSION_CATALYST.getKey());

        this.tag(BlockTags.NEEDS_STONE_TOOL)
            .add(BlockSetup.EXPLOSION_STABILISER.getKey())
            .add(BlockSetup.FLUCTUATION_CAPACITOR.getKey())
            .add(BlockSetup.CONVERSION_CATALYST.getKey());

        this.tag(TagSetup.Blocks.GLOWSTONE).add(Blocks.GLOWSTONE);
    }
}
