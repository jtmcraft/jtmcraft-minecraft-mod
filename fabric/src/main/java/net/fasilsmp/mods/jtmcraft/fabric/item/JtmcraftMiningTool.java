package net.fasilsmp.mods.jtmcraft.fabric.item;

import com.google.common.collect.BiMap;
import net.fasilsmp.mods.jtmcraft.Jtmcraft;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.CampfireBlock;
import net.minecraft.block.Oxidizable;
import net.minecraft.block.PillarBlock;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.AxeItem;
import net.minecraft.item.HoneycombItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.item.MiningToolItem;
import net.minecraft.item.ShovelItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.registry.Registries;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class JtmcraftMiningTool extends MiningToolItem {
    public static final TagKey<Block> MINING_TOOL_BLOCKS = TagKey.of(Registries.BLOCK.getKey(), Jtmcraft.id("mineable/mining_tool"));

    public JtmcraftMiningTool(float attackDamage, float attackSpeed, ToolMaterial material, TagKey<Block> effectiveBlocks, Settings settings) {
        super(attackDamage, attackSpeed, material, effectiveBlocks, settings);
    }

    @Override
    public ActionResult useOnBlock(@NotNull ItemUsageContext context) {
        World world = context.getWorld();
        BlockPos blockPos = context.getBlockPos();
        BlockState blockState = world.getBlockState(blockPos);
        Block targetBlock = blockState.getBlock();
        boolean isCampfire = blockState.streamTags().anyMatch(blockTagKey -> blockTagKey.id().equals(new Identifier("minecraft:campfires")));
        boolean isCampfireLit = isCampfire ? blockState.get(CampfireBlock.LIT) : false;
        Optional<BlockState> newBlockState = handleBlocks(context, blockState, targetBlock, isCampfireLit);

        return processBlockState(context, newBlockState);
    }

    private Optional<BlockState> handleBlocks(@NotNull ItemUsageContext context, BlockState blockState, Block targetBlock, boolean isCampfireLit) {
        Optional<BlockState> strippedBlockState = getStrippedState(blockState);
        Optional<BlockState> scrapedBlockState = Oxidizable.getDecreasedOxidationState(blockState);
        Optional<BlockState> unwaxedBlockState = getUnwaxedBlockState(blockState);
        Optional<BlockState> pathedBlockState = Shovel.getPathableBlockState(targetBlock);
        World world = context.getWorld();
        BlockPos blockPos = context.getBlockPos();
        PlayerEntity playerEntity = context.getPlayer();

        if (strippedBlockState.isPresent()) {
            playBlockSound(world, playerEntity, blockPos, SoundEvents.ITEM_AXE_STRIP);
            return strippedBlockState;
        }
        if (scrapedBlockState.isPresent()) {
            playBlockSound(world, playerEntity, blockPos, SoundEvents.ITEM_AXE_SCRAPE);
            syncWorld(world, playerEntity, blockPos, 3005);
            return scrapedBlockState;
        }
        if (unwaxedBlockState.isPresent()) {
            playBlockSound(world, playerEntity, blockPos, SoundEvents.ITEM_AXE_WAX_OFF);
            syncWorld(world, playerEntity, blockPos, 3004);
            return unwaxedBlockState;
        }
        if (pathedBlockState.isPresent() && world.getBlockState(blockPos.up()).isAir()) {
            playBlockSound(world, playerEntity, blockPos, SoundEvents.ITEM_SHOVEL_FLATTEN);
            return pathedBlockState;
        }
        if (isCampfireLit) {
            return handleLitCampFire(context, blockState);
        }

        return Optional.empty();
    }

    private void syncWorld(@NotNull World world, PlayerEntity playerEntity, BlockPos blockPos, int event) {
        world.syncWorldEvent(playerEntity, event, blockPos, 0);
    }

    private void playBlockSound(@NotNull World world, PlayerEntity playerEntity, BlockPos blockPos, SoundEvent soundEvent) {
        world.playSound(playerEntity, blockPos, soundEvent, SoundCategory.BLOCKS, 1.0F, 1.0F);
    }

    private @NotNull Optional<BlockState> handleLitCampFire(@NotNull ItemUsageContext context, BlockState blockState) {
        World world = context.getWorld();
        BlockPos blockPos = context.getBlockPos();

        if (!world.isClient()) {
            syncWorld(world, null, blockPos, 1009);
        }

        CampfireBlock.extinguish(context.getPlayer(), world, blockPos, blockState);

        return Optional.of(blockState.with(CampfireBlock.LIT, false));
    }

    private Optional<BlockState> getUnwaxedBlockState(@NotNull BlockState blockState) {
        return Optional.ofNullable((Block)((BiMap<?, ?>) HoneycombItem.WAXED_TO_UNWAXED_BLOCKS.get()).get(blockState.getBlock())).map((block) -> block.getStateWithProperties(blockState));
    }

    private ActionResult processBlockState(@NotNull ItemUsageContext context, @NotNull Optional<BlockState> newBlockState) {
        World world = context.getWorld();
        BlockPos blockPos = context.getBlockPos();
        PlayerEntity playerEntity = context.getPlayer();
        ItemStack itemStack = context.getStack();

        if (newBlockState.isPresent()) {
            if (playerEntity instanceof ServerPlayerEntity serverPlayerEntity) {
                Criteria.ITEM_USED_ON_BLOCK.trigger(serverPlayerEntity, blockPos, itemStack);
            }
            world.setBlockState(blockPos, newBlockState.get(), 11);
            world.emitGameEvent(GameEvent.BLOCK_CHANGE, blockPos, GameEvent.Emitter.of(playerEntity, newBlockState.get()));
            if (playerEntity != null) {
                itemStack.damage(1, playerEntity, (p) -> p.sendToolBreakStatus(context.getHand()));
            }
            return ActionResult.success(world.isClient);
        }
        return ActionResult.PASS;
    }

    private Optional<BlockState> getStrippedState(@NotNull BlockState state) {
        return Optional.ofNullable(Axe.getStrippableBlocks().get(state.getBlock()))
                .map((block) -> block.getDefaultState().with(PillarBlock.AXIS, state.get(PillarBlock.AXIS)));
    }

    private static final class Axe extends AxeItem {
        private Axe(ToolMaterial material, float attackDamage, float attackSpeed, Settings settings) {
            super(material, attackDamage, attackSpeed, settings);
        }

        public static Map<Block, Block> getStrippableBlocks() {
            return AxeItem.STRIPPED_BLOCKS;
        }
    }

    private static final class Shovel extends ShovelItem {
        private Shovel(ToolMaterial material, float attackDamage, float attackSpeed, Settings settings) {
            super(material, attackDamage, attackSpeed, settings);
        }

        public static Optional<BlockState> getPathableBlockState(Block block) {
            BlockState blockState = ShovelItem.PATH_STATES.getOrDefault(block, null);
            return Optional.ofNullable(blockState);
        }
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        if (Screen.hasShiftDown()) {
            tooltip.add(Text.translatable("item.jtmcraft.mining_tool.tooltip.shift"));
        } else {
            tooltip.add(Text.translatable("item.jtmcraft.mining_tool.tooltip"));
        }
    }
}
