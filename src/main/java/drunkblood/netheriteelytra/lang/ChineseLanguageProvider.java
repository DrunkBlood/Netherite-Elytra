package drunkblood.netheriteelytra.lang;

import drunkblood.netheriteelytra.NetheriteElytra;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.LanguageProvider;

public class ChineseLanguageProvider extends LanguageProvider {
    public ChineseLanguageProvider(PackOutput output) {
        super(output, NetheriteElytra.MODID, "zh_cn");
    }

    @Override
    protected void addTranslations() {
        this.addItem(NetheriteElytra.NETHERITE_ELYTRA, "下界合金鞘翅");
        this.addItem(NetheriteElytra.NETHERITE_MEMBRANE, "下界合金幻翼膜");
    }
}
