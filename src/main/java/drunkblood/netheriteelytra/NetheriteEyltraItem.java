package drunkblood.netheriteelytra;

import net.minecraft.core.component.DataComponents;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.type.capability.ICurioItem;
import top.theillusivec4.curios.api.type.capability.ICuriosItemHandler;

public class NetheriteEyltraItem extends Item implements ICurioItem {
    public NetheriteEyltraItem(Properties properties) {
        super(properties);
    }

    @Override
    public boolean canEquip(@NotNull ItemStack stack, @NotNull EquipmentSlot armorType, @NotNull LivingEntity entity) {
        ICuriosItemHandler curiosInventory = CuriosApi.getCuriosInventoryOrNull(entity);
        if(curiosInventory != null
            &&
            curiosInventory.findFirstCurio(itemStack -> itemStack.has(DataComponents.GLIDER)).isPresent()){
            return false;
        }
        return super.canEquip(stack, armorType, entity);
    }
}
