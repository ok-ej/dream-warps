package cc.dreamcode.dreamwarp.managers;

import org.bukkit.configuration.file.FileConfiguration;
import cc.dreamcode.dreamwarp.Main;

public class ConfigManager {

    private final Main plugin;
    private FileConfiguration config;

    public ConfigManager(Main plugin) {
        this.plugin = plugin;
        loadConfig();
    }

    private void loadConfig() {
        plugin.saveDefaultConfig();
        config = plugin.getConfig();
    }

    public void reloadConfig() {
        plugin.reloadConfig();
        config = plugin.getConfig();
    }

    public int getTeleportDelay() {
        return config.getInt("teleport-delay", 3);
    }

    public double getMovementThreshold() {
        return config.getDouble("movement-threshold", 0.5);
    }

    public String getGuiTitle() {
        return config.getString("gui.title", "§6§lWarpy");
    }

    public int getGuiSize() {
        return config.getInt("gui.size", 54);
    }
}
