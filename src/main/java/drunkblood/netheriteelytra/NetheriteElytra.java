package drunkblood.netheriteelytra;

import drunkblood.netheriteelytra.datagen.ModEquipmentAssetProvider;
import drunkblood.netheriteelytra.datagen.ModModelProvider;
import drunkblood.netheriteelytra.datagen.ModRecipeProvider;
import drunkblood.netheriteelytra.lang.*;
import net.minecraft.core.component.DataComponents;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Unit;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.equipment.EquipmentAsset;
import net.minecraft.world.item.equipment.EquipmentAssets;
import net.minecraft.world.item.equipment.Equippable;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

@Mod(NetheriteElytra.MODID)
public class NetheriteElytra {
	public static final String MODID = "netherelytra";

	public static ResourceKey<EquipmentAsset> NETHERITE_ELYTRA_ASSET = ResourceKey.create(EquipmentAssets.ROOT_ID, ResourceLocation.fromNamespaceAndPath(MODID, "netherite_eyltra"));

	public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(MODID);
	public static final DeferredItem<Item> NETHERITE_MEMBRANE = ITEMS.registerSimpleItem("netherite_membrane");
	public static final DeferredItem<Item> NETHERITE_ELYTRA = ITEMS.registerItem("netherite_elytra",
			(properties) -> new Item(properties
				.durability(720)
				.rarity(Rarity.EPIC)
				.fireResistant()
				.component(DataComponents.GLIDER, Unit.INSTANCE)
				.component(
						DataComponents.EQUIPPABLE,
						Equippable.builder(EquipmentSlot.CHEST)
							.setEquipSound(SoundEvents.ARMOR_EQUIP_ELYTRA)
							.setAsset(NETHERITE_ELYTRA_ASSET)
							.setDamageOnHurt(false)
							.build()
						)
				.repairable(NETHERITE_MEMBRANE.get())
				));

	public NetheriteElytra(IEventBus modEventBus, ModContainer modContainer) {
		ITEMS.register(modEventBus);
		modEventBus.addListener(this::gatherData);
	}

	private void gatherData(GatherDataEvent.Client event){
		event.createProvider(ModModelProvider::new);
		event.createProvider(ModRecipeProvider.Runner::new);
		event.createProvider(GermanLanguageProvider::new);
		event.createProvider(ChineseLanguageProvider::new);
		event.createProvider(EnglishLanguageProvider::new);
		event.createProvider(PolishLanguageProvider::new);
		event.createProvider(RussianLanguageProvider::new);
		event.createProvider(ModEquipmentAssetProvider::new);
	}
}
