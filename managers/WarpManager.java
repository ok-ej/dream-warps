package cc.dreamcode.dreamwarp.managers;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import cc.dreamcode.dreamwarp.Main;
import cc.dreamcode.dreamwarp.models.Warp;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WarpManager {

    private final Main plugin;
    private final Map<String, Warp> warps;

    public WarpManager(Main plugin) {
        this.plugin = plugin;
        this.warps = new HashMap<>();
        loadWarps();
    }

    private void loadWarps() {
        warps.clear();

        loadConfigWarps();
    }

    public void reloadWarps() {
        loadWarps();
    }

    private void loadConfigWarps() {
        FileConfiguration config = plugin.getConfig();

        if (!config.getBoolean("warpy.enabled", false)) {
            return;
        }

        ConfigurationSection warpsSection = config.getConfigurationSection("warpy.warps");
        if (warpsSection != null) {
            for (String warpName : warpsSection.getKeys(false)) {
                ConfigurationSection warpSection = warpsSection.getConfigurationSection(warpName);
                if (warpSection != null) {
                    String worldName = warpSection.getString("world");
                    World world = Bukkit.getWorld(worldName);

                    if (world == null) {
                        plugin.getLogger().warning("Świat " + worldName + " nie istnieje dla warpa " + warpName);
                        continue;
                    }

                    double x = warpSection.getDouble("x");
                    double y = warpSection.getDouble("y");
                    double z = warpSection.getDouble("z");
                    float yaw = (float) warpSection.getDouble("yaw", 0.0);
                    float pitch = (float) warpSection.getDouble("pitch", 0.0);

                    Location location = new Location(world, x, y, z, yaw, pitch);
                    int guiSlot = warpSection.getInt("gui-slot");
                    String permission = warpSection.getString("permission");

                    Material icon = Material.valueOf(warpSection.getString("icon", "ENDER_PEARL"));
                    String displayName = warpSection.getString("display-name", "§6" + warpName);

                    String description;
                    if (warpSection.isList("description")) {
                        List<String> descriptionList = warpSection.getStringList("description");
                        description = String.join("\n", descriptionList);
                    } else {
                        description = warpSection.getString("description", "§7klik aby teleportowac");
                    }

                    Warp warp = new Warp(warpName, location, guiSlot, permission, icon, displayName, description, true);
                    warps.put(warpName, warp);
                }
            }
        }
    }

    public void addWarp(String name, Location location, int guiSlot, String permission) {
        FileConfiguration config = plugin.getConfig();

        String path = "warpy.warps." + name;
        config.set(path + ".world", location.getWorld().getName());
        config.set(path + ".x", location.getX());
        config.set(path + ".y", location.getY());
        config.set(path + ".z", location.getZ());
        config.set(path + ".yaw", location.getYaw());
        config.set(path + ".pitch", location.getPitch());
        config.set(path + ".gui-slot", guiSlot);
        config.set(path + ".permission", permission);
        config.set(path + ".icon", "ENDER_PEARL");
        config.set(path + ".display-name", "&6nowy warp " + name);
        config.set(path + ".description", "&7tutaj sobie edytuj tekst kolezko");

        if (!config.getBoolean("warpy.enabled", false)) {
            config.set("warpy.enabled", true);
        }

        plugin.saveConfig();

        Warp warp = new Warp(name, location, guiSlot, permission);
        warps.put(name, warp);
    }

    public void removeWarp(String name) {
        Warp warp = warps.get(name);
        if (warp != null && warp.isManual()) {
            FileConfiguration config = plugin.getConfig();
            config.set("warpy.warps." + name, null);
            plugin.saveConfig();

            warps.remove(name);
        }
    }

    public Warp getWarp(String name) {
        return warps.get(name);
    }

    public Map<String, Warp> getAllWarps() {
        return new HashMap<>(warps);
    }

    public boolean warpExists(String name) {
        return warps.containsKey(name);
    }

    public boolean isManualWarp(String name) {
        Warp warp = warps.get(name);
        return warp != null && warp.isManual();
    }

    public void saveWarps() {
    }
}
