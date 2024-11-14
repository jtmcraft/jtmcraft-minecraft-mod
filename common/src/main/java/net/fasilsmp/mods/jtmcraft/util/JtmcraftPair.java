package net.fasilsmp.mods.jtmcraft.util;

import org.jetbrains.annotations.NotNull;

public class JtmcraftPair<K, V> {
    private final K key;
    private final V value;

    private JtmcraftPair(K key, V value) {
        this.key = key;
        this.value = value;
    }

    public static <K, V> @NotNull JtmcraftPair<K, V> of(K key, V value) {
        return new JtmcraftPair<>(key, value);
    }

    public K getKey() {
        return key;
    }

    public V getValue() {
        return value;
    }
}
