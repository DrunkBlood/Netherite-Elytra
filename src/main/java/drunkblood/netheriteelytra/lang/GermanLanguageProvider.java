package drunkblood.netheriteelytra.lang;

import drunkblood.netheriteelytra.NetheriteElytra;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.LanguageProvider;

public class GermanLanguageProvider extends LanguageProvider {
    public GermanLanguageProvider(PackOutput output) {
        super(output, NetheriteElytra.MODID, "de_de");
    }

    @Override
    protected void addTranslations() {
        this.addItem(NetheriteElytra.NETHERITE_ELYTRA, "Netheritelytren");
        this.addItem(NetheriteElytra.NETHERITE_MEMBRANE, "Phantomhaut mit Netherit");
    }
}
