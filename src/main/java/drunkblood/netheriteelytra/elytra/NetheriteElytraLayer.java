package drunkblood.netheriteelytra.elytra;

import drunkblood.netheriteelytra.NetheriteElytra;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.ElytraLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.apache.commons.lang3.tuple.ImmutableTriple;
import top.theillusivec4.curios.api.CuriosApi;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Optional;


@OnlyIn(Dist.CLIENT)
public class NetheriteElytraLayer
		extends ElytraLayer<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>> {

	private static final ResourceLocation TEXTURE_ELYTRA = new ResourceLocation(NetheriteElytra.MODID,
			"textures/entity/netherite_elytra.png");

	public NetheriteElytraLayer(
			RenderLayerParent<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>> rendererIn,
			EntityModelSet modelSet) {
		super(rendererIn, modelSet);
	}

	@Override
	public boolean shouldRender(ItemStack stack, AbstractClientPlayer entity) {
		Optional<ImmutableTriple<String, Integer, ItemStack>> equippedCurio =
				CuriosApi.getCuriosHelper().findEquippedCurio(NetheriteElytra.NETHERITE_ELYTRA.get(), entity);
		return equippedCurio.isPresent() || stack.getItem() == NetheriteElytra.NETHERITE_ELYTRA.get();
	}

	@Override
	public ResourceLocation getElytraTexture(ItemStack stack, AbstractClientPlayer entity) {
		return TEXTURE_ELYTRA;
	}

}