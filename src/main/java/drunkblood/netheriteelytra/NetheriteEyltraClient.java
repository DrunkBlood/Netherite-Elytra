package drunkblood.netheriteelytra;

import drunkblood.netheriteelytra.client.NetheriteElytraLayer;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.entity.ArmorStandRenderer;
import net.minecraft.client.renderer.entity.layers.EquipmentLayerRenderer;
import net.minecraft.client.renderer.entity.player.AvatarRenderer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.CreativeModeTabs;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;

@Mod(value = NetheriteElytra.MODID, dist = Dist.CLIENT)
public class NetheriteEyltraClient {
    public NetheriteEyltraClient(IEventBus bus) {
        bus.addListener(this::registerElytraLayer);
        bus.addListener(this::registerCreativeTab);
    }

    private void registerCreativeTab(BuildCreativeModeTabContentsEvent event){
        if(event.getTabKey() == CreativeModeTabs.INGREDIENTS){
            event.accept(NetheriteElytra.NETHERITE_MEMBRANE.get());
        } else if (event.getTabKey() == CreativeModeTabs.TOOLS_AND_UTILITIES){
            event.accept(NetheriteElytra.NETHERITE_ELYTRA.get());
        }
    }

    private void registerElytraLayer(EntityRenderersEvent.AddLayers event) {
        EntityModelSet entityModels = event.getEntityModels();
        EquipmentLayerRenderer equipmentRenderer = event.getContext().getEquipmentRenderer();
        var skins = event.getSkins();
        for (var skin : skins) {
            AvatarRenderer<AbstractClientPlayer> playerRenderer = event.getPlayerRenderer(skin);
            if (playerRenderer != null) {
                playerRenderer.addLayer(new NetheriteElytraLayer<>(playerRenderer, entityModels, equipmentRenderer));
            }
        }
        var livingEntityRenderer = event.getRenderer(EntityType.ARMOR_STAND);
        if (livingEntityRenderer instanceof ArmorStandRenderer armorStandRenderer) {
            armorStandRenderer.addLayer(new NetheriteElytraLayer<>(armorStandRenderer, entityModels, equipmentRenderer));
        }

    }
}
