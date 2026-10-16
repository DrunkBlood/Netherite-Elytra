package drunkblood.netheriteelytra;

import net.minecraft.core.component.DataComponents;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.common.NeoForgeMod;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurio;

public class CanElytraFlyCapability implements ICurio {
    private final Identifier enableFlight = Identifier.fromNamespaceAndPath(NetheriteElytra.MODID, "enable_glider_flight");
    private final ItemStack stack;

    public CanElytraFlyCapability(ItemStack stack){
        this.stack = stack;
    }

    @Override
    public void onEquip(SlotContext slotContext, ItemStack prevStack) {
        LivingEntity entity = slotContext.entity();
        AttributeInstance glidingFlight = entity.getAttribute(NeoForgeMod.GLIDING_FLIGHT);
        if(glidingFlight == null) return;
        AttributeModifier existing = glidingFlight.getModifier(this.enableFlight);
        if(existing == null){
            var newFlight = new AttributeModifier(this.enableFlight, 1.0, AttributeModifier.Operation.ADD_VALUE);
            glidingFlight.addPermanentModifier(newFlight);
        }
    }

    @Override
    public void onUnequip(SlotContext slotContext, ItemStack newStack) {
        LivingEntity entity = slotContext.entity();
        AttributeInstance glidingFlight = entity.getAttribute(NeoForgeMod.GLIDING_FLIGHT);
        if(glidingFlight == null) return;
        AttributeModifier existing = glidingFlight.getModifier(this.enableFlight);
        if(existing != null){
            glidingFlight.removeModifier(existing);
        }
    }

    @Override
    public boolean canEquip(SlotContext slotContext) {
        ItemStack chestStack = slotContext.entity().getItemBySlot(EquipmentSlot.CHEST);
        if(chestStack.getItem() == this.stack.getItem()) {
            return false;
        }
        return !chestStack.has(DataComponents.GLIDER);
    }

    @Override
    public boolean canEquipFromUse(SlotContext slotContext) {
        return true;
    }

    @Override
    public ItemStack getStack() {
        return this.stack;
    }
}
