package net.fasilsmp.mods.jtmcraft.fabric.registration;

import net.fasilsmp.mods.jtmcraft.Jtmcraft;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

public class SoundsRegistration {
    public static final SoundEvent MUSIC_DISC_ODE_TO_JOY_SE = registerSoundEvent("music_disc_ode_to_joy");

    private static SoundEvent registerSoundEvent(String name) {
        Identifier soundEventIdentifier = Jtmcraft.id(name);
        return Registry.register(Registries.SOUND_EVENT, soundEventIdentifier, SoundEvent.of(soundEventIdentifier));
    }

    public static void register() {
        Jtmcraft.LOGGER.info("Registering jtmcraft sounds");
    }
}
