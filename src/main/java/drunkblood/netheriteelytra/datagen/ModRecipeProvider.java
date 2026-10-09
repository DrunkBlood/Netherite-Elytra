package drunkblood.netheriteelytra.datagen;

import drunkblood.netheriteelytra.NetheriteElytra;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends RecipeProvider {
    protected ModRecipeProvider(HolderLookup.Provider registries, RecipeOutput output) {
        super(registries, output);
    }

    @Override
    protected void buildRecipes() {
        ShapelessRecipeBuilder.shapeless(this.registries.lookupOrThrow(Registries.ITEM),
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
                .save(this.output, ResourceKey.create(Registries.RECIPE, ResourceLocation.fromNamespaceAndPath(NetheriteElytra.MODID, "netherite_elytra_smithing")));
    }

    public static class Runner extends RecipeProvider.Runner {
        public Runner(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
            super(output, lookupProvider);
        }

        @Override
        protected @NotNull RecipeProvider createRecipeProvider(HolderLookup.@NotNull Provider provider, @NotNull RecipeOutput output) {
            return new ModRecipeProvider(provider, output);
        }

        @Override
        public @NotNull String getName() {
            return "NetheriteElytraRecipeProvider";
        }
    }
}
