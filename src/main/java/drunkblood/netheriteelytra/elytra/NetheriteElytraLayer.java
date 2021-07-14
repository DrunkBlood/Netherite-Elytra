package drunkblood.netheriteelytra.elytra;

import drunkblood.netheriteelytra.NetheriteElytra;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.layers.ElytraLayer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.apache.commons.lang3.tuple.ImmutableTriple;
import top.theillusivec4.curios.api.CuriosApi;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Optional;

@OnlyIn(Dist.CLIENT)
@MethodsReturnNonnullByDefault
public class NetheriteElytraLayer
		extends ElytraLayer{

	private static final ResourceLocation TEXTURE_ELYTRA = new ResourceLocation(NetheriteElytra.MODID,
			"textures/entity/netherite_elytra.png");

	public NetheriteElytraLayer(IEntityRenderer<?, ?> livingRenderer) {
		super(livingRenderer);
	}

	@Override
	@ParametersAreNonnullByDefault
	public boolean shouldRender(ItemStack stack, LivingEntity entity) {
		Optional<ImmutableTriple<String, Integer, ItemStack>> equippedCurio =
				CuriosApi.getCuriosHelper().findEquippedCurio(NetheriteElytra.NETHERITE_ELYTRA.get(), entity);
		return equippedCurio.isPresent() || stack.getItem() == NetheriteElytra.NETHERITE_ELYTRA.get();
	}

	@Override
	@ParametersAreNonnullByDefault
	public ResourceLocation getElytraTexture(ItemStack stack, LivingEntity entity) {
		return TEXTURE_ELYTRA;
	}

}
