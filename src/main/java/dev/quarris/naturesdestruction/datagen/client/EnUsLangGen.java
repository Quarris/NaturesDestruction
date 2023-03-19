package dev.quarris.naturesdestruction.datagen.client;

import dev.quarris.naturesdestruction.setup.BlockSetup;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.LanguageProvider;

public class EnUsLangGen extends LanguageProvider {

    public EnUsLangGen(DataGenerator gen, String modid) {
        super(gen, modid, "en_us");
    }

    @Override
    protected void addTranslations() {
        this.addBlockTranslations();
        this.addItemTranslations();
        this.addMiscTranslations();
    }

    public void addBlockTranslations() {
        this.addBlock(BlockSetup.EXPLOSION_STABILISER, "Explosion Stabilizer");
        this.addBlock(BlockSetup.CONVERSION_CATALYST, "Conversion Catalyst");
        this.addBlock(BlockSetup.FLUCTUATION_CAPACITOR, "Fluctuation Capacitor");
    }

    public void addItemTranslations() {

    }

    public void addMiscTranslations() {

    }

    @Override
    public String getName() {
        return "Nature's Destruction EnUs Languages";
    }
}
