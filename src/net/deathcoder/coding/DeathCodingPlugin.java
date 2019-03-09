package net.deathcoder.coding;

import net.deathcoder.coding.listener.PlayerListener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class DeathCodingPlugin extends JavaPlugin {
    private static DeathCodingPlugin INSTANCE;

    public static DeathCodingPlugin getINSTANCE() {
        return INSTANCE;
    }

    @Override
    public void onEnable() {
        INSTANCE = this;
        final PluginManager pm = this.getServer().getPluginManager();
        pm.registerEvents(new PlayerListener(this), this);
    }
}
