package drunkblood.netheriteelytra;

import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Items;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import top.theillusivec4.curios.api.client.ICurioRenderer;

@Mod(value = NetheriteElytra.MODID, dist = Dist.CLIENT)
public class NetheriteEyltraClient {
    public NetheriteEyltraClient(IEventBus bus) {
        bus.addListener(this::registerCreativeTab);
        bus.addListener(this::clientSetup);
        bus.addListener(this::addLayers);
    }

    private void addLayers(EntityRenderersEvent.AddLayers event){
        var renderer = (NetheriteEyltraCurioRenderer)ICurioRenderer.get(NetheriteElytra.NETHERITE_ELYTRA.get());
        renderer.createModel(event.getEntityModels());
    }

    private void clientSetup(FMLClientSetupEvent event){
        ICurioRenderer.register(NetheriteElytra.NETHERITE_ELYTRA.get(), NetheriteEyltraCurioRenderer::getInstance);
        ICurioRenderer.register(Items.ELYTRA, NetheriteEyltraCurioRenderer::getInstance);
    }

    private void registerCreativeTab(BuildCreativeModeTabContentsEvent event){
        if(event.getTabKey() == CreativeModeTabs.INGREDIENTS){
            event.accept(NetheriteElytra.NETHERITE_MEMBRANE.get());
        } else if (event.getTabKey() == CreativeModeTabs.TOOLS_AND_UTILITIES){
            event.accept(NetheriteElytra.NETHERITE_ELYTRA.get());
        }
    }
}
