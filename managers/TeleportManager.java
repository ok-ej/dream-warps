package cc.dreamcode.dreamwarp.managers;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import cc.dreamcode.dreamwarp.Main;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class TeleportManager {

    private final Main plugin;
    private final Map<UUID, TeleportData> teleportingPlayers;
    private final int teleportDelay;

    public TeleportManager(Main plugin) {
        this.plugin = plugin;
        this.teleportingPlayers = new HashMap<>();
        this.teleportDelay = plugin.getConfigManager().getTeleportDelay();
    }

    public void startTeleport(Player player, Location destination, String warpName) {
        if (teleportingPlayers.containsKey(player.getUniqueId())) {
            plugin.getMessageManager().sendMessage(player, "warp-already-teleporting");
            return;
        }

        Location startLocation = player.getLocation().clone();

        BukkitTask task = new BukkitRunnable() {
            int countdown = teleportDelay;

            @Override
            public void run() {
                if (countdown <= 0) {
                    player.teleport(destination);
                    plugin.getMessageManager().sendMessage(player, "warp-teleported", "name", warpName);
                    teleportingPlayers.remove(player.getUniqueId());
                    cancel();
                    return;
                }

                plugin.getMessageManager().sendMessage(player, "warp-teleporting", "time", String.valueOf(countdown));
                countdown--;
            }
        }.runTaskTimer(plugin, 0L, 20L);

        teleportingPlayers.put(player.getUniqueId(), new TeleportData(startLocation, task, warpName));
    }

    public void cancelTeleport(Player player) {
        TeleportData data = teleportingPlayers.remove(player.getUniqueId());
        if (data != null) {
            data.getTask().cancel();
            plugin.getMessageManager().sendMessage(player, "warp-teleport-cancelled");
        }
    }

    public boolean isTeleporting(Player player) {
        return teleportingPlayers.containsKey(player.getUniqueId());
    }

    public Location getTeleportStartLocation(Player player) {
        TeleportData data = teleportingPlayers.get(player.getUniqueId());
        return data != null ? data.getStartLocation() : null;
    }

    private static class TeleportData {
        private final Location startLocation;
        private final BukkitTask task;
        private final String warpName;

        public TeleportData(Location startLocation, BukkitTask task, String warpName) {
            this.startLocation = startLocation;
            this.task = task;
            this.warpName = warpName;
        }

        public Location getStartLocation() {
            return startLocation;
        }

        public BukkitTask getTask() {
            return task;
        }

        public String getWarpName() {
            return warpName;
        }
    }
}
