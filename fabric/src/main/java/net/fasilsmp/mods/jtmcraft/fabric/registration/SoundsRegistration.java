package net.fasilsmp.mods.jtmcraft.fabric.registration;

import net.fasilsmp.mods.jtmcraft.Jtmcraft;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

public class SoundsRegistration {
    public static final SoundEvent MUSIC_DISC_ODE_TO_JOY_SE = registerSoundEvent("music_disc_ode_to_joy");
    public static final SoundEvent MUSIC_DISC_SUNSET_SE = registerSoundEvent("music_disc_sunset");
    public static final SoundEvent MUSIC_DISC_GLAD_YOURE_HERE_SE = registerSoundEvent("music_disc_glad_youre_here");
    public static final SoundEvent MUSIC_DISC_BLOCK_PARTY_SE = registerSoundEvent("music_disc_block_party");
    public static final SoundEvent MUSIC_DISC_RUN_AND_JUMP_SE = registerSoundEvent("music_disc_run_and_jump");

    private static SoundEvent registerSoundEvent(String name) {
        Identifier soundEventIdentifier = Jtmcraft.id(name);
        return Registry.register(Registries.SOUND_EVENT, soundEventIdentifier, SoundEvent.of(soundEventIdentifier));
    }

    public static void register() {
        Jtmcraft.LOGGER.info("Registering jtmcraft sounds");
    }
}
