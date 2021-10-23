package drunkblood.netheriteelytra;

import drunkblood.netheriteelytra.elytra.NetheriteElytraArmorStandLayer;
import drunkblood.netheriteelytra.elytra.NetheriteElytraLayer;
import drunkblood.netheriteelytra.item.NetheriteElytraItem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ArmorStandArmorModel;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.entity.ArmorStandRenderer;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLEnvironment;
import net.minecraftforge.fmllegacy.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import org.apache.logging.log4j.LogManager;

@Mod(NetheriteElytra.MODID)
public class NetheriteElytra {
	public static final String MODID = "netherelytra";
	public static final String NAME = "Netherite Elytra";

	private static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MODID);
	public static final RegistryObject<Item> NETHERITE_ELYTRA = ITEMS.register("netherite_elytra",
			() -> new NetheriteElytraItem(new Item.Properties().durability(540).tab(CreativeModeTab.TAB_TRANSPORTATION)/* 540 */
					.rarity(Rarity.UNCOMMON).fireResistant()));
	public static final RegistryObject<Item> NETHERITE_MEMBRANE = ITEMS.register("netherite_membrane",
			() -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_BREWING)));

	public NetheriteElytra() {
		final IEventBus modBus = FMLJavaModLoadingContext.get().getModEventBus();
		ITEMS.register(modBus);
		modBus.addListener(this::onClientSetup);
		if(FMLEnvironment.dist.isClient()) modBus.addListener(this::registerElytraLayer);
	}

	private void onClientSetup(FMLClientSetupEvent event) {
		// broken Property
		ItemProperties.register(NETHERITE_ELYTRA.get(), new ResourceLocation(MODID, "broken"),
				(stack, arg1, arg2, arg3) -> NetheriteElytraItem.isUseable(stack) ? 0 : 1);
	}

	@OnlyIn(Dist.CLIENT)
	private void registerElytraLayer(EntityRenderersEvent event) {
		if(event instanceof EntityRenderersEvent.AddLayers addLayersEvent){
			EntityModelSet entityModels = addLayersEvent.getEntityModels();
			addLayersEvent.getSkins().forEach(s -> {
				LivingEntityRenderer<? extends Player, ? extends EntityModel<? extends Player>> livingEntityRenderer = addLayersEvent.getSkin(s);
				if(livingEntityRenderer instanceof PlayerRenderer playerRenderer){
					playerRenderer.addLayer(new NetheriteElytraLayer(playerRenderer, entityModels));
				}
			});
			LivingEntityRenderer<ArmorStand, ? extends EntityModel<ArmorStand>> livingEntityRenderer = addLayersEvent.getRenderer(EntityType.ARMOR_STAND);
			if(livingEntityRenderer instanceof ArmorStandRenderer armorStandRenderer){
				armorStandRenderer.addLayer(new NetheriteElytraArmorStandLayer(armorStandRenderer, entityModels));
			}

		}
	}
}
