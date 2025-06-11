package cc.dreamcode.dreamwarp;

import org.bukkit.plugin.java.JavaPlugin;
import cc.dreamcode.dreamwarp.commands.DelWarpCommand;
import cc.dreamcode.dreamwarp.commands.SetWarpCommand;
import cc.dreamcode.dreamwarp.commands.WarpCommand;
import cc.dreamcode.dreamwarp.commands.WarpsCommand;
import cc.dreamcode.dreamwarp.listeners.InventoryClickListener;
import cc.dreamcode.dreamwarp.listeners.PlayerMoveListener;
import cc.dreamcode.dreamwarp.managers.ConfigManager;
import cc.dreamcode.dreamwarp.managers.MessageManager;
import cc.dreamcode.dreamwarp.managers.TeleportManager;
import cc.dreamcode.dreamwarp.managers.WarpManager;

public final class Main extends JavaPlugin {

    private static Main instance;
    private WarpManager warpManager;
    private TeleportManager teleportManager;
    private ConfigManager configManager;
    private MessageManager messageManager;

    @Override
    public void onEnable() {
        instance = this;

        this.configManager = new ConfigManager(this);
        this.messageManager = new MessageManager(this);
        this.warpManager = new WarpManager(this);
        this.teleportManager = new TeleportManager(this);
        registerCommands();
        registerListeners();
        getLogger().info("Plugin Dream-Warps został włączony!");
    }

    @Override
    public void onDisable() {
        if (warpManager != null) {
            warpManager.saveWarps();
        }
        getLogger().info("Plugin Dream-Warps został wyłączony!");
    }

    private void registerCommands() {
        getCommand("setwarp").setExecutor(new SetWarpCommand(this));
        getCommand("delwarp").setExecutor(new DelWarpCommand(this));
        getCommand("warp").setExecutor(new WarpCommand(this));
        getCommand("warps").setExecutor(new WarpsCommand(this));
    }

    private void registerListeners() {
        getServer().getPluginManager().registerEvents(new InventoryClickListener(this), this);
        getServer().getPluginManager().registerEvents(new PlayerMoveListener(this), this);
    }

    public static Main getInstance() {
        return instance;
    }

    public WarpManager getWarpManager() {
        return warpManager;
    }

    public TeleportManager getTeleportManager() {
        return teleportManager;
    }

    public ConfigManager getConfigManager() {
        return configManager;
    }

    public MessageManager getMessageManager() {
        return messageManager;
    }
}
