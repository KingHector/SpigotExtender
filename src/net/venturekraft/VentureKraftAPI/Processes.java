package net.venturekraft.VentureKraftAPI;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class Processes
{

    /**
     * For use of Colorcodes in any String.
     * @param string String to add color to. Add the code at the start.
     * @return Returns color coded String.
     */
    public static String color (String string) { return ChatColor.translateAlternateColorCodes('&', string); }

    /**
     * To get a color from a String for spigot's Color method.
     * @param color String with the color name.
     * @return Returns the color.
     */
    public static Color colorFromString (String color)
    {
        switch (color)
        {
            case "AQUA":
                return Color.AQUA;

            case "BLACK":
                return Color.BLACK;

            case "BLUE":
                return Color.BLUE;

            case "FUCHSIA":
                return Color.FUCHSIA;

            case "GRAY":
                return Color.GRAY;

            case "GREEN":
                return Color.GREEN;

            case "LIME":
                return Color.LIME;

            case "MAROON":
                return Color.MAROON;

            case "NAVY":
                return Color.NAVY;

            case "OLIVE":
                return Color.OLIVE;

            case "ORANGE":
                return Color.ORANGE;

            case "PURPLE":
                return Color.PURPLE;

            case "RED":
                return Color.RED;

            case "SILVER":
                return Color.SILVER;

            case "TEAL":
                return Color.TEAL;

            case "YELLOW":
                return Color.YELLOW;

            case "WHITE":

            default: return Color.WHITE;
        }
    }

    /**
     * To create ItemStacks. You cannot use this to create Player Heads, use buildSkull instead.
     * @param name Item name.
     * @param lore Item lore.
     * @param material Item material.
     * @param quantity Item quantity.
     * @return Returns ItemStack.
     */
    public static ItemStack buildItem(String name, List<String> lore, Material material, int quantity)
    {
        ItemStack item = new ItemStack(material, quantity);
        ItemMeta itemMeta = item.getItemMeta();
        Objects.requireNonNull(itemMeta).setDisplayName(color(name));
        itemMeta.setLore(lore);
        item.setItemMeta(itemMeta);

        return item;
    }

    /**
     * To create Player Heads ItemStacks.
     * @param name Item name.
     * @param lore Item lore.
     * @param player Head's owner.
     * @param quantity Item quantity.
     * @return Returns ItemStack.
     */
    public static ItemStack buildSkull(String name, List<String> lore, UUID player, int quantity)
    {
        ItemStack playerSkull = new ItemStack(Material.PLAYER_HEAD, quantity);
        SkullMeta skullMeta = (SkullMeta) playerSkull.getItemMeta();
        Objects.requireNonNull(skullMeta).setDisplayName(name);
        Objects.requireNonNull(skullMeta).setOwningPlayer(Bukkit.getOfflinePlayer(player));
        skullMeta.setLore(lore);
        playerSkull.setItemMeta(skullMeta);

        return playerSkull;
    }

}
