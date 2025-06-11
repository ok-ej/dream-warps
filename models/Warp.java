package cc.dreamcode.dreamwarp.models;

import org.bukkit.Location;
import org.bukkit.Material;

public class Warp {

    private final String name;
    private final Location location;
    private final int guiSlot;
    private final String permission;
    private final Material icon;
    private final String displayName;
    private String description = "";
    private final boolean isManual;

    public Warp(String name, Location location, int guiSlot, String permission) {
        this.name = name;
        this.location = location;
        this.guiSlot = guiSlot;
        this.permission = permission;
        this.icon = Material.DIRT;
        this.displayName = "§6" + name;
        this.description = "kliknij aby tp";
        this.isManual = false;
    }

    public Warp(String name, Location location, int guiSlot, String permission, Material icon, String displayName, String description) {
        this.name = name;
        this.location = location;
        this.guiSlot = guiSlot;
        this.permission = permission;
        this.icon = icon;
        this.displayName = displayName;
        this.description = description;
        this.isManual = false;
    }

    public Warp(String name, Location location, int guiSlot, String permission, Material icon, String displayName, String description, boolean isManual) {
        this.name = name;
        this.location = location;
        this.guiSlot = guiSlot;
        this.permission = permission;
        this.icon = icon;
        this.displayName = displayName;
        this.description = description;
        this.isManual = isManual;
    }

    public String getName() {
        return name;
    }

    public Location getLocation() {
        return location;
    }

    public int getGuiSlot() {
        return guiSlot;
    }

    public String getPermission() {
        return permission;
    }

    public Material getIcon() {
        return icon;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getDescription() {
        return description;
    }

    public boolean isManual() {
        return isManual;
    }
}
