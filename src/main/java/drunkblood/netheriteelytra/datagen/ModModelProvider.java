package drunkblood.netheriteelytra.datagen;

import drunkblood.netheriteelytra.NetheriteElytra;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ModelProvider;
import net.minecraft.client.data.models.model.ItemModelUtils;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.client.renderer.item.properties.conditional.Broken;
import net.minecraft.data.PackOutput;
import org.jetbrains.annotations.NotNull;

public class ModModelProvider extends ModelProvider {
    public ModModelProvider(PackOutput output) {
        super(output, NetheriteElytra.MODID);
    }

    @Override
    protected void registerModels(@NotNull BlockModelGenerators blockModels, @NotNull ItemModelGenerators itemModels) {
        itemModels.generateFlatItem(NetheriteElytra.NETHERITE_MEMBRANE.get(), ModelTemplates.FLAT_ITEM);
        var elytra = ItemModelUtils.plainModel(itemModels.createFlatItemModel(NetheriteElytra.NETHERITE_ELYTRA.get(), ModelTemplates.FLAT_ITEM));
        var brokenElytra = ItemModelUtils.plainModel(itemModels.createFlatItemModel(NetheriteElytra.NETHERITE_ELYTRA.get(), "_broken", ModelTemplates.FLAT_ITEM));
//        itemodels.generateFlatItem(NetheriteElytra.NETHERITE_ELYTRA.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateBooleanDispatch(NetheriteElytra.NETHERITE_ELYTRA.get(), new Broken(), brokenElytra, elytra);
    }
}
