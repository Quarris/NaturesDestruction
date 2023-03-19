package dev.quarris.naturesdestruction;

import dev.quarris.naturesdestruction.setup.BlockSetup;
import dev.quarris.naturesdestruction.setup.ItemSetup;
import dev.quarris.naturesdestruction.setup.MultiblockSetup;
import dev.quarris.naturesdestruction.setup.TagSetup;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(ModRef.ID)
public class ModRoot {

    public ModRoot() {
        IEventBus modBus = FMLJavaModLoadingContext.get().getModEventBus();
        BlockSetup.init(modBus);
        ItemSetup.init(modBus);
        TagSetup.init();

        modBus.addListener(this::commonSetup);
    }

    private void commonSetup(FMLCommonSetupEvent event) {
        MultiblockSetup.init();
    }
}
