package dev.quarris.naturesdestruction;

import dev.quarris.naturesdestruction.setup.BlockSetup;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ModRef {
    public static final String ID = "naturesdestruction";

    private static final Logger LOGGER = LoggerFactory.getLogger(ID);

    public static final CreativeModeTab TAB = new CreativeModeTab(ID) {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(BlockSetup.EXPLOSION_STABILISER.get());
        }
    };

    public static final Item.Properties DEFAULT_ITEM_PROPERTIES = new Item.Properties().tab(TAB);

    public static ResourceLocation res(String name) {
        return new ResourceLocation(ID, name);
    }
}
