package drunkblood.netheriteelytra.client;

import com.mojang.blaze3d.vertex.PoseStack;

import drunkblood.netheriteelytra.NetheriteElytra;
import net.minecraft.client.model.ElytraModel;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.EquipmentLayerRenderer;
import net.minecraft.client.renderer.entity.layers.WingsLayer;
import net.minecraft.client.renderer.entity.state.AvatarRenderState;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import net.minecraft.client.resources.model.EquipmentClientInfo;
import net.minecraft.core.component.DataComponents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.equipment.Equippable;
import org.jetbrains.annotations.NotNull;

public class NetheriteElytraLayer<S extends HumanoidRenderState, M extends EntityModel<S>>
		extends WingsLayer<S, M> {

	private static final ResourceLocation TEXTURE_ELYTRA = ResourceLocation.fromNamespaceAndPath(NetheriteElytra.MODID,
			"textures/entity/equipment/wings/netherite_elytra.png");
	private final ElytraModel model;
	private final EquipmentLayerRenderer equipmentRenderer;

	public NetheriteElytraLayer(
			RenderLayerParent<S, M> renderer, EntityModelSet models, EquipmentLayerRenderer equipmentRenderer) {
		super(renderer, models, equipmentRenderer);
		this.model = new ElytraModel(models.bakeLayer(ModelLayers.ELYTRA));
		this.equipmentRenderer = equipmentRenderer;
	}

	@Override
	public void submit(@NotNull PoseStack poseStack, @NotNull SubmitNodeCollector node, int packedLight, S state, float p_433309_,
                       float p_432928_) {
		ItemStack chestStack = state.chestEquipment;
		Equippable equippable = chestStack.get(DataComponents.EQUIPPABLE);
		if(equippable != null && equippable.assetId().isPresent()) {
            poseStack.pushPose();
            poseStack.translate(0.0F, 0.0F, 0.125F);
            this.equipmentRenderer
                .renderLayers(
                    EquipmentClientInfo.LayerType.WINGS,
                    equippable.assetId().get(),
					model,
                    state,
                    chestStack,
                    poseStack,
                    node,
                    packedLight,
					getTexture(state),
                    state.outlineColor,
                    0
                );
            poseStack.popPose();
		}
	}

	private static @NotNull ResourceLocation getTexture(HumanoidRenderState state) {
		if(state instanceof AvatarRenderState avatarRenderState){
			var cape = avatarRenderState.skin.cape();
			if(cape != null && avatarRenderState.showCape){
				return cape.texturePath();
			}
		}
		return TEXTURE_ELYTRA;
	}

}
