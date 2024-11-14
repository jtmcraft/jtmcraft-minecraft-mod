package net.fasilsmp.mods.jtmcraft.fabric.registration;

import com.google.common.collect.ImmutableSet;
import net.fabricmc.fabric.api.object.builder.v1.trade.TradeOfferHelper;
import net.fabricmc.fabric.api.object.builder.v1.world.poi.PointOfInterestHelper;
import net.fasilsmp.mods.jtmcraft.Jtmcraft;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.sound.SoundEvents;
import net.minecraft.village.TradeOffer;
import net.minecraft.village.TradeOffers;
import net.minecraft.village.VillagerProfession;
import net.minecraft.world.poi.PointOfInterestType;
import org.jetbrains.annotations.NotNull;

public class VillagersRegistration {
    public static final RegistryKey<PointOfInterestType> RS_CLERIC_POI_KEY = registerPoiKey("redstoneclericpoi");
    public static final PointOfInterestType RS_CLERIC_POI = registerPoi("redstoneclericpoi", Blocks.REDSTONE_BLOCK);
    public static final VillagerProfession RS_CLERIC_PROFESSION = registerProfession("redstonecleric", RS_CLERIC_POI_KEY);

    private static VillagerProfession registerProfession(String name, RegistryKey<PointOfInterestType> type) {
        return Registry.register(Registries.VILLAGER_PROFESSION, Jtmcraft.id(name),
                new VillagerProfession(name, entry -> true, entry -> entry.matchesKey(type),
                        ImmutableSet.of(), ImmutableSet.of(), SoundEvents.ENTITY_VILLAGER_WORK_CLERIC));
    }

    private static @NotNull PointOfInterestType registerPoi(String name, Block block) {
        return PointOfInterestHelper.register(Jtmcraft.id(name), 1, 1, block);
    }

    private static RegistryKey<PointOfInterestType> registerPoiKey(String name) {
        return RegistryKey.of(RegistryKeys.POINT_OF_INTEREST_TYPE, Jtmcraft.id(name));
    }

    private static void registerRedstoneClericTrades() {
        ItemStack blank = new ItemStack(Items.QUARTZ, 2);

        TradeOfferHelper.registerVillagerOffers(RS_CLERIC_PROFESSION, 1,
                factories -> {
                    factories.add(createTradeOffer(blank, "logic_gate_and"));
                    factories.add(createTradeOffer(blank, "logic_gate_nand"));
                    factories.add(createTradeOffer(blank, "logic_gate_or"));
                    factories.add(createTradeOffer(blank, "logic_gate_nor"));
                    factories.add(createTradeOffer(blank, "logic_gate_xor"));
                    factories.add(createTradeOffer(blank, "logic_gate_nxor"));
                    factories.add(createTradeOffer(blank, "motion_detector"));
                    factories.add(createTradeOffer(blank, "sleep_time_detector"));
                    factories.add(createTradeOffer(blank, "redstone_vertical_emitter"));
                    factories.add(createTradeOffer(blank, "redstone_lantern"));
                });
    }

    private static TradeOffers.@NotNull Factory createTradeOffer(ItemStack buyItem, String sellItemName) {
        ItemStack sellItem = new ItemStack(BlocksRegistration.getFromName(sellItemName), 1);
        return (entity, random) -> new TradeOffer(buyItem, sellItem, 64, 1, 0.001f);
    }

    public static void register() {
        Jtmcraft.LOGGER.info("Registering jtmcraft villagers");
        registerRedstoneClericTrades();
    }
}
