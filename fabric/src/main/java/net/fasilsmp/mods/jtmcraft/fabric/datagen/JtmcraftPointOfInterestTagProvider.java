package net.fasilsmp.mods.jtmcraft.fabric.datagen;

import net.fasilsmp.mods.jtmcraft.Jtmcraft;
import net.minecraft.data.DataOutput;
import net.minecraft.data.server.tag.TagProvider;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.PointOfInterestTypeTags;
import net.minecraft.world.poi.PointOfInterestType;

import java.util.concurrent.CompletableFuture;

public class JtmcraftPointOfInterestTagProvider extends TagProvider<PointOfInterestType> {
    public JtmcraftPointOfInterestTagProvider(DataOutput output,
                                              CompletableFuture<RegistryWrapper.WrapperLookup> registryLookupFuture) {
        super(output, RegistryKeys.POINT_OF_INTEREST_TYPE, registryLookupFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup lookup) {
        this.getOrCreateTagBuilder(PointOfInterestTypeTags.ACQUIRABLE_JOB_SITE)
                .addOptional(Jtmcraft.id("redstoneclericpoi"));
    }
}
