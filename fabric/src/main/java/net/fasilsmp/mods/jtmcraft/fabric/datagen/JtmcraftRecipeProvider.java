package net.fasilsmp.mods.jtmcraft.fabric.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.fasilsmp.mods.jtmcraft.fabric.registration.BlocksRegistration;
import net.fasilsmp.mods.jtmcraft.fabric.registration.ItemsRegistration;
import net.minecraft.data.server.recipe.RecipeJsonProvider;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.data.server.recipe.ShapelessRecipeJsonBuilder;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.Items;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class JtmcraftRecipeProvider extends FabricRecipeProvider {
    public JtmcraftRecipeProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generate(Consumer<RecipeJsonProvider> exporter) {
        Map<String, ItemConvertible> nameToItem = new HashMap<>();
        List<String> jtmcraftBlocks = List.of(
                "logic_gate_blank",
                "logic_gate_and",
                "logic_gate_nand",
                "logic_gate_or",
                "logic_gate_nor",
                "logic_gate_xor",
                "logic_gate_nxor"
        );

        jtmcraftBlocks.forEach(jtmcraftBlock -> nameToItem.put(jtmcraftBlock, BlocksRegistration.getFromName(jtmcraftBlock)));

        createRedstoneRecipes(exporter);
        createToolRecipes(exporter);
        createLogicGateRecipes(exporter, nameToItem);
    }

    private void createLogicGateRecipes(Consumer<RecipeJsonProvider> exporter, @NotNull Map<String, ItemConvertible> nameToItem) {
        ItemConvertible logicGateBlank = BlocksRegistration.getFromName("logic_gate_blank");
        ItemConvertible logicGateAnd = BlocksRegistration.getFromName("logic_gate_and");
        ItemConvertible logicGateNand = BlocksRegistration.getFromName("logic_gate_nand");
        ItemConvertible logicGateOr = BlocksRegistration.getFromName("logic_gate_or");
        ItemConvertible logicGateNor = BlocksRegistration.getFromName("logic_gate_nor");
        ItemConvertible logicGateXor = BlocksRegistration.getFromName("logic_gate_xor");
        ItemConvertible logicGateNxor = BlocksRegistration.getFromName("logic_gate_nxor");

        ShapedRecipeJsonBuilder.create(RecipeCategory.REDSTONE, logicGateBlank, 1)
                .pattern("QRQ")
                .pattern("SSS")
                .input('Q', Items.QUARTZ)
                .input('R', Items.REDSTONE)
                .input('S', Items.STONE)
                .criterion(hasItem(Items.QUARTZ), conditionsFromItem(Items.QUARTZ))
                .criterion(hasItem(Items.REDSTONE), conditionsFromItem(Items.REDSTONE))
                .criterion(hasItem(Items.STONE), conditionsFromItem(Items.STONE))
                .offerTo(exporter, new Identifier(getRecipeName(logicGateBlank)));

        ShapelessRecipeJsonBuilder.create(RecipeCategory.REDSTONE, logicGateAnd, 1)
                .input(logicGateBlank)
                .criterion(hasItem(logicGateBlank), conditionsFromItem(logicGateBlank))
                .offerTo(exporter, new Identifier(getRecipeName(logicGateAnd)));

        ShapelessRecipeJsonBuilder.create(RecipeCategory.REDSTONE, logicGateNand, 1)
                .input(logicGateBlank)
                .criterion(hasItem(logicGateBlank), conditionsFromItem(logicGateBlank))
                .offerTo(exporter, new Identifier(getRecipeName(logicGateNand)));

        ShapelessRecipeJsonBuilder.create(RecipeCategory.REDSTONE, logicGateOr, 1)
                .input(logicGateBlank)
                .criterion(hasItem(logicGateBlank), conditionsFromItem(logicGateBlank))
                .offerTo(exporter, new Identifier(getRecipeName(logicGateOr)));

        ShapelessRecipeJsonBuilder.create(RecipeCategory.REDSTONE, logicGateNor, 1)
                .input(logicGateBlank)
                .criterion(hasItem(logicGateBlank), conditionsFromItem(logicGateBlank))
                .offerTo(exporter, new Identifier(getRecipeName(logicGateNor)));

        ShapelessRecipeJsonBuilder.create(RecipeCategory.REDSTONE, logicGateXor, 1)
                .input(logicGateBlank)
                .criterion(hasItem(logicGateBlank), conditionsFromItem(logicGateBlank))
                .offerTo(exporter, new Identifier(getRecipeName(logicGateXor)));

        ShapelessRecipeJsonBuilder.create(RecipeCategory.REDSTONE, logicGateNxor, 1)
                .input(logicGateBlank)
                .criterion(hasItem(logicGateBlank), conditionsFromItem(logicGateBlank))
                .offerTo(exporter, new Identifier(getRecipeName(logicGateNxor)));
    }

    private void createRedstoneRecipes(Consumer<RecipeJsonProvider> exporter) {
        createShapedRedstoneRecipe(BlocksRegistration.getFromName("motion_detector"), List.of(" R ", "SSS"), exporter);
        createShapedRedstoneRecipe(BlocksRegistration.getFromName("redstone_vertical_emitter"), List.of("R", "S", "R"), exporter);
        createShapelessRedstoneRecipe(BlocksRegistration.getFromName("sleep_time_detector"), exporter, List.of(Items.CLOCK, Items.DAYLIGHT_DETECTOR));
        createShapelessRedstoneRecipe(BlocksRegistration.getFromName("redstone_lantern"), exporter, List.of(Items.REDSTONE_LAMP));
    }

    private void createShapedRedstoneRecipe(ItemConvertible item, @NotNull List<String> pattern, Consumer<RecipeJsonProvider> exporter) {
        ShapedRecipeJsonBuilder recipeBuilder = ShapedRecipeJsonBuilder.create(RecipeCategory.REDSTONE, item);
        pattern.forEach(recipeBuilder::pattern);
        recipeBuilder
                .input('S', Items.STONE).input('R', Items.REDSTONE)
                .criterion(hasItem(Items.STONE), conditionsFromItem(Items.STONE))
                .criterion(hasItem(Items.REDSTONE), conditionsFromItem(Items.REDSTONE))
                .offerTo(exporter, new Identifier(getRecipeName(item)));
    }

    private void createShapelessRedstoneRecipe(ItemConvertible item, Consumer<RecipeJsonProvider> exporter, @NotNull List<ItemConvertible> items) {
        ShapelessRecipeJsonBuilder recipeBuilder = ShapelessRecipeJsonBuilder.create(RecipeCategory.REDSTONE, item);

        items.forEach(ingredient -> {
            recipeBuilder.input(ingredient);
            recipeBuilder.criterion(hasItem(ingredient), conditionsFromItem(ingredient));
        });

        recipeBuilder.offerTo(exporter, new Identifier(getRecipeName(item)));
    }

    private void createToolRecipes(Consumer<RecipeJsonProvider> exporter) {
        createRecipeForTool(exporter, Items.DIAMOND_AXE, Items.DIAMOND_PICKAXE, Items.DIAMOND_SHOVEL, ItemsRegistration.DIAMOND_MINING_TOOL);
        createRecipeForTool(exporter, Items.STONE_AXE, Items.STONE_PICKAXE, Items.STONE_SHOVEL, ItemsRegistration.STONE_MINING_TOOL);
        createRecipeForTool(exporter, Items.WOODEN_AXE, Items.WOODEN_PICKAXE, Items.WOODEN_SHOVEL, ItemsRegistration.WOODEN_MINING_TOOL);
        createRecipeForTool(exporter, Items.GOLDEN_AXE, Items.GOLDEN_PICKAXE, Items.GOLDEN_SHOVEL, ItemsRegistration.GOLDEN_MINING_TOOL);
        createRecipeForTool(exporter, Items.IRON_AXE, Items.IRON_PICKAXE, Items.IRON_SHOVEL, ItemsRegistration.IRON_MINING_TOOL);
        createRecipeForTool(exporter, Items.NETHERITE_AXE, Items.NETHERITE_PICKAXE, Items.NETHERITE_SHOVEL, ItemsRegistration.NETHERITE_MINING_TOOL);
    }

    private void createRecipeForTool(Consumer<RecipeJsonProvider> exporter,
                                     ItemConvertible axe,
                                     ItemConvertible pickaxe,
                                     ItemConvertible shovel,
                                     ItemConvertible miningTool) {
        ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, miningTool)
                .pattern("APS")
                .input('A', axe)
                .input('P', pickaxe)
                .input('S', shovel)
                .criterion(hasItem(axe), conditionsFromItem(axe))
                .criterion(hasItem(pickaxe), conditionsFromItem(pickaxe))
                .criterion(hasItem(shovel), conditionsFromItem(shovel))
                .offerTo(exporter, new Identifier(getRecipeName(miningTool) + "_"));
    }
}
