package dev.quarris.naturesdestruction.datagen;

import dev.quarris.naturesdestruction.ModRef;
import dev.quarris.naturesdestruction.datagen.client.BlockStateGen;
import dev.quarris.naturesdestruction.datagen.client.EnUsLangGen;
import dev.quarris.naturesdestruction.datagen.server.BlockTagGen;
import dev.quarris.naturesdestruction.datagen.server.LootTableGen;
import dev.quarris.naturesdestruction.datagen.server.RecipeGen;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGen {

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator gen = event.getGenerator();
        ExistingFileHelper existingFiles = event.getExistingFileHelper();
        String id = ModRef.ID;
        boolean isClient = event.includeClient();
        boolean isServer = event.includeServer();


        // Client
        gen.addProvider(isClient, new EnUsLangGen(gen, id));
        gen.addProvider(isClient, new BlockStateGen(gen, id, existingFiles));

        // Server
        gen.addProvider(isServer, new RecipeGen(gen));
        gen.addProvider(isServer, new LootTableGen(gen));
        gen.addProvider(isServer, new BlockTagGen(gen, id, existingFiles));
    }
}
