package drunkblood.netheriteelytra.lang;

import drunkblood.netheriteelytra.NetheriteElytra;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.LanguageProvider;

public class PolishLanguageProvider extends LanguageProvider {

    public PolishLanguageProvider(PackOutput output) {
        super(output, NetheriteElytra.MODID, "pl_pl");
    }

    @Override
    protected void addTranslations() {
        this.addItem(NetheriteElytra.NETHERITE_ELYTRA, "Netherytowa Elytra");
        this.addItem(NetheriteElytra.NETHERITE_MEMBRANE, "Błona Fantoma z Netherytem");
    }
}
