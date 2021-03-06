package drunkblood.netheriteelytra;

import drunkblood.netheriteelytra.elytra.NetheriteElytraArmorStandLayer;
import drunkblood.netheriteelytra.elytra.NetheriteElytraLayer;
import drunkblood.netheriteelytra.item.NetheriteElytraItem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.client.renderer.entity.model.ArmorStandArmorModel;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.item.ArmorStandEntity;
import net.minecraft.item.*;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

@Mod(NetheriteElytra.MODID)
public class NetheriteElytra {
	public static final String MODID = "netherelytra";
	public static final String NAME = "Netherite Elytra";

	private static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MODID);
	public static final RegistryObject<Item> NETHERITE_ELYTRA = ITEMS.register("netherite_elytra",
			() -> new NetheriteElytraItem(new Item.Properties().maxDamage(540).group(ItemGroup.TRANSPORTATION)/* 540 */
					.rarity(Rarity.UNCOMMON).isImmuneToFire()));
	public static final RegistryObject<Item> NETHERITE_MEMBRANE = ITEMS.register("netherite_membrane",
			() -> new Item(new Item.Properties().group(ItemGroup.BREWING)));

	public NetheriteElytra() {
		final IEventBus modBus = FMLJavaModLoadingContext.get().getModEventBus();
		ITEMS.register(modBus);
		modBus.addListener(this::onClientSetup);
	}

	private void onClientSetup(FMLClientSetupEvent event) {
		registerElytraLayer();
		// broken Property
		ItemModelsProperties.registerProperty(NETHERITE_ELYTRA.get(), new ResourceLocation(MODID, "broken"),
				(stack, arg1, arg2) -> NetheriteElytraItem.isUseable(stack) ? 0 : 1);
	}

	@OnlyIn(Dist.CLIENT)
	private void registerElytraLayer() {
		Minecraft.getInstance().getRenderManager().getSkinMap().values()
				.forEach(player -> player.addLayer(new NetheriteElytraLayer(player)));
		ArmorStandEntity armorStandEntity = new ArmorStandEntity(EntityType.ARMOR_STAND, Minecraft.getInstance().world);
		LivingRenderer<ArmorStandEntity, ArmorStandArmorModel> armorStandRenderer =
				(LivingRenderer<ArmorStandEntity, ArmorStandArmorModel>)
						Minecraft.getInstance().getRenderManager().getRenderer(armorStandEntity);
		armorStandRenderer.addLayer(new NetheriteElytraArmorStandLayer(armorStandRenderer));
	}
}
