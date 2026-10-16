package drunkblood.netheriteelytra;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.object.equipment.ElytraModel;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.state.AvatarRenderState;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.client.resources.model.EquipmentClientInfo;
import net.minecraft.core.component.DataComponents;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.equipment.Equippable;
import org.jspecify.annotations.Nullable;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.client.ICurioRenderer;

public class NetheriteEyltraCurioRenderer implements ICurioRenderer {
    private static NetheriteEyltraCurioRenderer INSTANCE;

    private ElytraModel elytraModel;

    public static NetheriteEyltraCurioRenderer getInstance(){
        if(INSTANCE == null){
            INSTANCE = new NetheriteEyltraCurioRenderer();
        }

        return INSTANCE;
    }

    public void createModel(EntityModelSet modelSet) {
        this.elytraModel = new ElytraModel(modelSet.bakeLayer(ModelLayers.ELYTRA));
    }

    // rendering code adapted from WingsLayer
    @Override
    public <S extends LivingEntityRenderState, M extends EntityModel<? super S>> void render
            (ItemStack stack, SlotContext slotContext, PoseStack poseStack, SubmitNodeCollector submitNodeCollector,
             int packedLight, S renderState, RenderLayerParent<S, M> renderLayerParent, EntityRendererProvider.Context context,
             float yRotation, float xRotation) {
        if(!(renderState instanceof AvatarRenderState avatarState)){
            return;
        }
        Equippable equippable = stack.get(DataComponents.EQUIPPABLE);
        Equippable chestEquippable = avatarState.chestEquipment.get(DataComponents.EQUIPPABLE);
        if(equippable == null
                || equippable.assetId().isEmpty()
                // do not render if Wings are already rendered
                || chestEquippable != null && chestEquippable.assetId().isPresent()) {
            return;
        }
        context.getEquipmentRenderer()
                .renderLayers(
                        EquipmentClientInfo.LayerType.WINGS,
                        equippable.assetId().get(),
                        elytraModel,
                        avatarState,
                        stack,
                        poseStack,
                        submitNodeCollector,
                        packedLight,
                        GetTextureOverride(avatarState),
                        avatarState.outlineColor,
                        0
                );
    }

    private @Nullable Identifier GetTextureOverride(AvatarRenderState avatarState) {
        var skin = avatarState.skin;
        if(skin.elytra() != null) {
            return skin.elytra().texturePath();
        }

        if(skin.cape() != null && avatarState.showCape){
            return skin.cape().texturePath();
        }
        // use data generated value of equipment i.e. defaults
        return null;
    }
}
