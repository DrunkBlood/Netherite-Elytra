package drunkblood.netheriteelytra.lang;

import drunkblood.netheriteelytra.NetheriteElytra;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.LanguageProvider;

public class EnglishLanguageProvider extends LanguageProvider {
    public EnglishLanguageProvider(PackOutput output) {
        super(output, NetheriteElytra.MODID, "en_us");
    }

    @Override
    protected void addTranslations() {
        this.addItem(NetheriteElytra.NETHERITE_ELYTRA, "Netherite Elytra");
        this.addItem(NetheriteElytra.NETHERITE_MEMBRANE, "Phantom Membrane with Netherite");
    }
}
