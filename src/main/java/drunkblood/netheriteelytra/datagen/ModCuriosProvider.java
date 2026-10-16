package drunkblood.netheriteelytra.datagen;

import drunkblood.netheriteelytra.NetheriteElytra;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.references.ItemIds;
import top.theillusivec4.curios.api.CuriosDataProvider;
import top.theillusivec4.curios.api.CuriosSlotTypes;
import top.theillusivec4.curios.api.CuriosTags;

import java.util.concurrent.CompletableFuture;

public class ModCuriosProvider extends CuriosDataProvider {

    public ModCuriosProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(NetheriteElytra.MODID, output, registries);
    }

    @Override
    public void generate(HolderLookup.Provider registries) {
        this.createSlot(CuriosSlotTypes.Preset.BACK.id());
        this.createEntities("player")
                .addPlayer()
                .addSlots(CuriosSlotTypes.Preset.BACK.id());
        this.tag(CuriosTags.BACK)
                .add(NetheriteElytra.NETHERITE_ELYTRA_ID)
                .add(ItemIds.ELYTRA);
    }
}
