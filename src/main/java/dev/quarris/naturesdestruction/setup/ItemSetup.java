package dev.quarris.naturesdestruction.setup;

import dev.quarris.naturesdestruction.ModRef;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.*;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class ItemSetup {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, ModRef.ID);

    // Block Items
    static {
        registerDefaultBlockItem(BlockSetup.EXPLOSION_STABILISER);
        registerDefaultBlockItem(BlockSetup.CONVERSION_CATALYST);
        registerDefaultBlockItem(BlockSetup.FLUCTUATION_CAPACITOR);
    }

    public static void init(IEventBus modBus) {
        ITEMS.register(modBus);
    }

    private static RegistryObject<Item> registerDefaultBlockItem(RegistryObject<Block> block) {
        return ITEMS.register(block.getId().getPath(), () -> new BlockItem(block.get(), ModRef.DEFAULT_ITEM_PROPERTIES));
    }

}
