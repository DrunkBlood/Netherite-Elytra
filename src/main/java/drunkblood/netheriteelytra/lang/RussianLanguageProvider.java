package drunkblood.netheriteelytra.lang;

import drunkblood.netheriteelytra.NetheriteElytra;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.LanguageProvider;

public class RussianLanguageProvider extends LanguageProvider {

    public RussianLanguageProvider(PackOutput output) {
        super(output, NetheriteElytra.MODID, "ru_ru");
    }

    @Override
    protected void addTranslations() {
        this.addItem(NetheriteElytra.NETHERITE_ELYTRA, "Незеритовые элитры");
        this.addItem(NetheriteElytra.NETHERITE_MEMBRANE, "Мембрана фантома с незеритом");
    }
}
