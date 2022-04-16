package drunkblood.netheriteelytra.item;

import drunkblood.netheriteelytra.NetheriteElytra;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ElytraItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fml.util.ObfuscationReflectionHelper;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.type.capability.ICurio;
import top.theillusivec4.curios.api.type.capability.ICuriosItemHandler;
import top.theillusivec4.curios.api.type.inventory.ICurioStacksHandler;
import top.theillusivec4.curios.api.type.inventory.IDynamicStackHandler;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

public class NetheriteElytraItem extends ElytraItem implements ICurio {

	public NetheriteElytraItem(Properties properties) {
		super(properties);
		DispenserBlock.registerBehavior(this, ArmorItem.DISPENSE_ITEM_BEHAVIOR);
	}

	public static boolean isUsable(ItemStack stack) {
		return stack.getDamageValue() < stack.getMaxDamage() - 1;
	}
	/**
	 * Return whether this item is repairable in an anvil.
	 */
	@Override
	@ParametersAreNonnullByDefault
	public boolean isValidRepairItem(ItemStack toRepair, ItemStack repair) {
		return repair.getItem() == NetheriteElytra.NETHERITE_MEMBRANE.get();
	}

	@Override
	@ParametersAreNonnullByDefault
	@MethodsReturnNonnullByDefault
	public InteractionResultHolder<ItemStack> use(Level worldIn, Player playerIn, InteractionHand handIn) {
		ItemStack itemstack = playerIn.getItemInHand(handIn);
		EquipmentSlot equipmentSlot = Mob.getEquipmentSlotForItem(itemstack);
		ItemStack armorStack = playerIn.getItemBySlot(equipmentSlot);
		LazyOptional<ICuriosItemHandler> curiosHandler = CuriosApi.getCuriosHelper().getCuriosHandler(playerIn);
		AtomicBoolean replacedCurio = new AtomicBoolean(false);
		curiosHandler.ifPresent(handler ->{
			Map<String, ICurioStacksHandler> curios = handler.getCurios();
			if(curios.containsKey("back")){
				ICurioStacksHandler backHandler = curios.get("back");
				IDynamicStackHandler stackHandler = backHandler.getStacks();
				for(int i = 0; i < stackHandler.getSlots(); ++i){
					ItemStack stackInSlot = stackHandler.getStackInSlot(i);
					if(stackInSlot.isEmpty() && armorStack.isEmpty()){
						stackHandler.setStackInSlot(i, itemstack.copy());
						itemstack.setCount(0);
						replacedCurio.set(true);
						return;
					}
				}
			}
		});
		if(replacedCurio.get()){
			playerIn.level.playSound(null, new BlockPos(playerIn.position()),
					SoundEvents.ARMOR_EQUIP_ELYTRA, SoundSource.NEUTRAL, 1.0F, 1.0F);
			return InteractionResultHolder.success(itemstack);
		}
		else if (armorStack.isEmpty()) {
			if(CuriosApi.getCuriosHelper().findEquippedCurio(itemstack.getItem(), playerIn).isPresent()){
				return InteractionResultHolder.fail(itemstack);
			}
			playerIn.setItemSlot(equipmentSlot, itemstack.copy());
			itemstack.setCount(0);
			playerIn.level.playSound(null, new BlockPos(playerIn.position()),
					SoundEvents.ARMOR_EQUIP_ELYTRA, SoundSource.NEUTRAL, 1.0F, 1.0F);
			return InteractionResultHolder.success(itemstack);
		} else {
			return InteractionResultHolder.fail(itemstack);
		}
	}

	@Nullable
	@Override
	public EquipmentSlot getEquipmentSlot(ItemStack stack) {
		return EquipmentSlot.CHEST;
	}

	@Override
	@ParametersAreNonnullByDefault
	public boolean canElytraFly(ItemStack stack, LivingEntity entity) {
		return NetheriteElytraItem.isUsable(stack);
	}

	@Override
	@ParametersAreNonnullByDefault
	public boolean elytraFlightTick(ItemStack stack, LivingEntity entity, int flightTicks) {
		// Adding 1 to ticksElytraFlying prevents damage on the very first tick.
		if (!entity.level.isClientSide() && (flightTicks + 1) % 25 == 0) {
			stack.hurtAndBreak(1, entity, e -> e.broadcastBreakEvent(EquipmentSlot.CHEST));
		}
		return true;
	}

	@Override
	public ItemStack getStack() {
		// TODO
		return new ItemStack(this);
	}

	@Override
	public void curioTick(String identifier, int index, LivingEntity livingEntity) {
		ICurio.super.curioTick(identifier, index, livingEntity);
		Integer ticksFlying = livingEntity.getFallFlyingTicks();
		LazyOptional<ICuriosItemHandler> curiosHandler = CuriosApi.getCuriosHelper().getCuriosHandler(livingEntity);
		curiosHandler.ifPresent(handler ->{
			Map<String, ICurioStacksHandler> curios = handler.getCurios();
			ICurioStacksHandler stacksHandler = curios.get(identifier);
			IDynamicStackHandler stacks = stacksHandler.getStacks();
			ItemStack stackInSlot = stacks.getStackInSlot(index);
			stackInSlot.elytraFlightTick(livingEntity, ticksFlying);
		});
	}
}
