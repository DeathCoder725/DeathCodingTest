package net.deathcoder.coding;

import org.bukkit.plugin.java.JavaPlugin;

public class DeathCodingPlugin extends JavaPlugin {
    private static DeathCodingPlugin INSTANCE;

    public static DeathCodingPlugin getINSTANCE() {
        return INSTANCE;
    }

    @Override
    public void onEnable() {
        INSTANCE = this;
    }
}
