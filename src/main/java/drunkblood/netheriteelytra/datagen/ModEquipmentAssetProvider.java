package drunkblood.netheriteelytra.datagen;

import drunkblood.netheriteelytra.NetheriteElytra;
import net.minecraft.client.data.models.EquipmentAssetProvider;
import net.minecraft.client.resources.model.EquipmentClientInfo;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.equipment.EquipmentAsset;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;
import java.util.function.BiConsumer;

public class ModEquipmentAssetProvider extends EquipmentAssetProvider {

    public ModEquipmentAssetProvider(PackOutput output) {
        super(output);
    }

    @Override
    protected void registerModels(@NotNull BiConsumer<ResourceKey<EquipmentAsset>, EquipmentClientInfo> output) {
        output.accept(
                NetheriteElytra.NETHERITE_ELYTRA_ASSET,
                EquipmentClientInfo.builder()
                        .addLayers(
                                EquipmentClientInfo.LayerType.WINGS, new EquipmentClientInfo.Layer(Identifier.fromNamespaceAndPath(NetheriteElytra.MODID, "netherite_elytra"), Optional.empty(), true)
                        )
                        .build()
        );
    }
}
