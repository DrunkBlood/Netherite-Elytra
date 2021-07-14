package drunkblood.netheriteelytra.item;

import drunkblood.netheriteelytra.NetheriteElytra;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.block.DispenserBlock;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ElytraItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
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
		DispenserBlock.registerDispenseBehavior(this, ArmorItem.DISPENSER_BEHAVIOR);
	}

	public static boolean isUsable(ItemStack stack) {
		return stack.getDamage() < stack.getMaxDamage() - 1;
	}
	/**
	 * Return whether this item is repairable in an anvil.
	 */
	@Override
	@ParametersAreNonnullByDefault
	public boolean getIsRepairable(ItemStack toRepair, ItemStack repair) {
		return repair.getItem() == NetheriteElytra.NETHERITE_MEMBRANE.get();
	}

	@Override
	@ParametersAreNonnullByDefault
	@MethodsReturnNonnullByDefault
	public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
		ItemStack itemstack = playerIn.getHeldItem(handIn);
		EquipmentSlotType equipmentslottype = MobEntity.getSlotForItemStack(itemstack);
		ItemStack armorStack = playerIn.getItemStackFromSlot(equipmentslottype);
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
			playerIn.world.playSound(null, new BlockPos(playerIn.getPositionVec()),
					SoundEvents.ITEM_ARMOR_EQUIP_ELYTRA, SoundCategory.NEUTRAL, 1.0F, 1.0F);
			return ActionResult.resultSuccess(itemstack);
		}
		else if (armorStack.isEmpty()) {
			if(CuriosApi.getCuriosHelper().findEquippedCurio(itemstack.getItem(), playerIn).isPresent()){
				return ActionResult.resultFail(itemstack);
			}
			playerIn.setItemStackToSlot(equipmentslottype, itemstack.copy());
			itemstack.setCount(0);
			playerIn.world.playSound(null, new BlockPos(playerIn.getPositionVec()),
					SoundEvents.ITEM_ARMOR_EQUIP_ELYTRA, SoundCategory.NEUTRAL, 1.0F, 1.0F);
			return ActionResult.resultSuccess(itemstack);
		} else {
			return ActionResult.resultFail(itemstack);
		}
	}

	@Nullable
	@Override
	public EquipmentSlotType getEquipmentSlot(ItemStack stack) {
		return EquipmentSlotType.CHEST;
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
		if (!entity.world.isRemote && (flightTicks + 1) % 25 == 0) {
			stack.damageItem(1, entity, e -> e.sendBreakAnimation(EquipmentSlotType.CHEST));
		}
		return true;
	}

	@Override
	public void curioTick(String identifier, int index, LivingEntity livingEntity) {
		ICurio.super.curioTick(identifier, index, livingEntity);
		Integer ticksFlying = ObfuscationReflectionHelper
				.getPrivateValue(LivingEntity.class, livingEntity, "field_184629_bo");
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
