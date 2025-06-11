package cc.dreamcode.dreamwarp.managers;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import cc.dreamcode.dreamwarp.Main;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class MessageManager {

    private final Main plugin;
    private File messagesFile;
    private FileConfiguration messagesConfig;

    public MessageManager(Main plugin) {
        this.plugin = plugin;
        loadMessages();
    }

    private void loadMessages() {
        messagesFile = new File(plugin.getDataFolder(), "messages.yml");
        if (!messagesFile.exists()) {
            plugin.saveResource("messages.yml", false);
        }
        messagesConfig = YamlConfiguration.loadConfiguration(messagesFile);
    }

    public void reloadMessages() {
        messagesConfig = YamlConfiguration.loadConfiguration(messagesFile);
    }

    public void sendMessage(Player player, String key, Map<String, String> placeholders) {
        String type = messagesConfig.getString(key + ".type", "CHAT");
        String text = messagesConfig.getString(key + ".text", "");

        if (type.equals("DO_NOT_SEND") || text.isEmpty()) {
            return;
        }

        if (placeholders != null) {
            for (Map.Entry<String, String> entry : placeholders.entrySet()) {
                text = text.replace("{" + entry.getKey() + "}", entry.getValue());
            }
        }

        text = ChatColor.translateAlternateColorCodes('&', text);

        switch (type.toUpperCase()) {
            case "CHAT":
                player.sendMessage(text);
                break;
            case "ACTION_BAR":
                player.sendActionBar(text);
                break;
            case "TITLE":
                player.sendTitle(text, "", 0, 70, 0);
                break;
            case "SUBTITLE":
                player.sendTitle("", text, 0, 70, 0);
                break;
            case "TITLE_SUBTITLE":
                String[] parts = text.split("\\|");
                String title = parts.length > 0 ? parts[0] : "";
                String subtitle = parts.length > 1 ? parts[1] : "";
                player.sendTitle(title, subtitle, 0, 70, 0);
                break;
        }
    }

    public void sendMessage(Player player, String key) {
        sendMessage(player, key, null);
    }

    public void sendMessage(Player player, String key, String placeholder, String value) {
        Map<String, String> placeholders = new HashMap<>();
        placeholders.put(placeholder, value);
        sendMessage(player, key, placeholders);
    }
}
