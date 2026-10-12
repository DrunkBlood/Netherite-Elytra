package drunkblood.netheriteelytra;

import net.minecraft.world.item.CreativeModeTabs;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;

@Mod(value = NetheriteElytra.MODID, dist = Dist.CLIENT)
public class NetheriteEyltraClient {
    public NetheriteEyltraClient(IEventBus bus) {
        bus.addListener(this::registerCreativeTab);
    }

    private void registerCreativeTab(BuildCreativeModeTabContentsEvent event){
        if(event.getTabKey() == CreativeModeTabs.INGREDIENTS){
            event.accept(NetheriteElytra.NETHERITE_MEMBRANE.get());
        } else if (event.getTabKey() == CreativeModeTabs.TOOLS_AND_UTILITIES){
            event.accept(NetheriteElytra.NETHERITE_ELYTRA.get());
        }
    }
}
