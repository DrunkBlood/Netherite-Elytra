package drunkblood.netheriteelytra;

import drunkblood.netheriteelytra.elytra.NetheriteElytraLayer;
import drunkblood.netheriteelytra.item.NetheriteElytraItem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.ModifiableAttributeInstance;
import net.minecraft.entity.item.ArmorStandEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import top.theillusivec4.caelus.api.CaelusApi;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.CuriosCapability;
import top.theillusivec4.curios.api.SlotTypeMessage;
import top.theillusivec4.curios.api.SlotTypePreset;
import top.theillusivec4.curios.api.type.capability.ICurio;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.UUID;


@Mod(NetheriteElytra.MODID)
public class NetheriteElytra {
	public static final String MODID = "netherelytra";
	public static final String NAME = "Netherite Elytra";

	public static final Logger LOGGER = LogManager.getLogger(MODID);

	private static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MODID);
	public static final RegistryObject<Item> NETHERITE_ELYTRA = ITEMS.register("netherite_elytra",
			() -> new NetheriteElytraItem(new Item.Properties().maxDamage(540).group(ItemGroup.TRANSPORTATION)/* 540 */
					.rarity(Rarity.UNCOMMON).isImmuneToFire()));
	public static final RegistryObject<Item> NETHERITE_MEMBRANE = ITEMS.register("netherite_membrane",
			() -> new Item(new Item.Properties().group(ItemGroup.BREWING)));
	private static final AttributeModifier NETHERITE_ELYTRA_MODIFIER = new AttributeModifier(
			UUID.fromString("92b506f3-0a87-4989-a203-2ed4c7b4c1fd"), "Netherite Elytra modifier", 1.0D,
			AttributeModifier.Operation.ADDITION);

	public NetheriteElytra() {
		final IEventBus modBus = FMLJavaModLoadingContext.get().getModEventBus();
		ITEMS.register(modBus);
		modBus.addListener(this::onClientSetup);
		modBus.addListener(this::onCommonSetup);
		modBus.addListener(this::enqueue);
	}

	private void onClientSetup(FMLClientSetupEvent event) {
		registerElytraLayer();
		// broken Property
		ItemModelsProperties.registerProperty(NETHERITE_ELYTRA.get(), new ResourceLocation(MODID, "broken"),
				(stack, arg1, arg2) -> NetheriteElytraItem.isUsable(stack) ? 0 : 1);
	}

	private void onCommonSetup(FMLCommonSetupEvent event){
		MinecraftForge.EVENT_BUS.addListener(this::playerTick);
		MinecraftForge.EVENT_BUS.addGenericListener(ItemStack.class, this::attachCapabilities);
	}

	private void enqueue(final InterModEnqueueEvent evt) {
		InterModComms.sendTo(CuriosApi.MODID, SlotTypeMessage.REGISTER_TYPE,
				() -> SlotTypePreset.BACK.getMessageBuilder().build());
	}

	@OnlyIn(Dist.CLIENT)
	private void registerElytraLayer() {
		Minecraft.getInstance().getRenderManager().getSkinMap().values()
				.forEach(player -> player.addLayer(new NetheriteElytraLayer(player)));
	}

	private void playerTick(final TickEvent.PlayerTickEvent evt) {
		PlayerEntity player = evt.player;
		ModifiableAttributeInstance attributeInstance =
				player.getAttribute(CaelusApi.ELYTRA_FLIGHT.get());

		if (attributeInstance != null) {
			attributeInstance.removeModifier(NETHERITE_ELYTRA_MODIFIER);

			if (!attributeInstance.hasModifier(NETHERITE_ELYTRA_MODIFIER)) {
				CuriosApi.getCuriosHelper()
						.findEquippedCurio((stack) -> CaelusApi.canElytraFly(player, stack), player)
						.ifPresent(triple -> attributeInstance
								.applyNonPersistentModifier(NETHERITE_ELYTRA_MODIFIER));
			}
		}
	}

	private void attachCapabilities(final AttachCapabilitiesEvent<ItemStack> evt) {
		ItemStack stack = evt.getObject();

		if (stack.getItem() instanceof NetheriteElytraItem) {
			final LazyOptional<ICurio> elytraCurio = LazyOptional.of(() -> (NetheriteElytraItem) stack.getItem());
			evt.addCapability(CuriosCapability.ID_ITEM, new ICapabilityProvider() {

				@Nonnull
				@Override
				public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap,
														 @Nullable Direction side) {
					return CuriosCapability.ITEM.orEmpty(cap, elytraCurio);
				}
			});
			evt.addListener(elytraCurio::invalidate);
		}
	}
}
