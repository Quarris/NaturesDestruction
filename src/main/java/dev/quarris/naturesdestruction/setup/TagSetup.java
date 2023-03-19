package dev.quarris.naturesdestruction.setup;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

public class TagSetup {

    public static void init() {
        Blocks.init();
    }

    public static class Blocks {
        public static final TagKey<Block> GLOWSTONE = BlockTags.create(new ResourceLocation("forge", "glowstone"));

        public static void init() {}
    }
}
