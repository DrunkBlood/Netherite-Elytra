package drunkblood.netheriteelytra.datagen;

import drunkblood.netheriteelytra.NetheriteElytra;
import net.minecraft.advancements.Advancement;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.SmithingTransformRecipeBuilder;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;

public class ModRecipeProvider extends RecipeProvider {
    public ModRecipeProvider(BootstrapContext<Recipe<?>> recipeOutput, BootstrapContext<Advancement> advancementOutput) {
        super(recipeOutput, advancementOutput);
    }

    @Override
    protected void buildRecipes() {
        this.shapeless(
                        RecipeCategory.TOOLS,
                        NetheriteElytra.NETHERITE_MEMBRANE.get(),
                        16)
                .requires(Items.PHANTOM_MEMBRANE)
                .requires(Items.NETHERITE_INGOT)
                .unlockedBy("has_netherite_elytra", this.has(NetheriteElytra.NETHERITE_ELYTRA))
                .save(this.output);
        SmithingTransformRecipeBuilder.smithing(
                Ingredient.of(Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE),
                Ingredient.of(Items.ELYTRA),
                Ingredient.of(Items.NETHERITE_INGOT),
                RecipeCategory.TOOLS,
                NetheriteElytra.NETHERITE_ELYTRA.get()
        )
                .unlocks("has_elytra", this.has(Items.ELYTRA))
                .save(this.output, ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(NetheriteElytra.MODID, "netherite_elytra_smithing")));
    }
}
