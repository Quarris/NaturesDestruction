package dev.quarris.naturesdestruction.datagen.server.loottables;

import dev.quarris.naturesdestruction.setup.BlockSetup;
import net.minecraft.data.loot.BlockLoot;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.RegistryObject;

public class BlockLootProvider extends BlockLoot {

    @Override
    protected void addTables() {
        this.dropSelf(BlockSetup.EXPLOSION_STABILISER.get());
        this.dropSelf(BlockSetup.FLUCTUATION_CAPACITOR.get());
        this.dropSelf(BlockSetup.CONVERSION_CATALYST.get());
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return BlockSetup.BLOCKS.getEntries().stream().flatMap(RegistryObject::stream)::iterator;
    }
}
