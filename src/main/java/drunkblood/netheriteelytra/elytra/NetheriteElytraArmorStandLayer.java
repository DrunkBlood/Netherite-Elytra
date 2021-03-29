package drunkblood.netheriteelytra.elytra;

import drunkblood.netheriteelytra.NetheriteElytra;
import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.layers.ElytraLayer;
import net.minecraft.client.renderer.entity.model.ArmorStandArmorModel;
import net.minecraft.client.renderer.entity.model.ArmorStandModel;
import net.minecraft.entity.item.ArmorStandEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class NetheriteElytraArmorStandLayer extends ElytraLayer<ArmorStandEntity, ArmorStandArmorModel> {
    private static final ResourceLocation TEXTURE_ELYTRA = new ResourceLocation(NetheriteElytra.MODID,
            "textures/entity/netherite_elytra.png");

    public NetheriteElytraArmorStandLayer(IEntityRenderer<ArmorStandEntity, ArmorStandArmorModel> rendererIn) {
        super(rendererIn);
    }

    @Override
    public boolean shouldRender(ItemStack stack, ArmorStandEntity entity) {
        return stack.getItem() == NetheriteElytra.NETHERITE_ELYTRA.get();
    }

    @Override
    public ResourceLocation getElytraTexture(ItemStack stack, ArmorStandEntity entity) {
        return TEXTURE_ELYTRA;
    }
}
