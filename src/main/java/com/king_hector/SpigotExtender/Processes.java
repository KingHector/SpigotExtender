package com.king_hector.SpigotExtender;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.potion.PotionData;

@SuppressWarnings("unused")
public class Processes
{
    
    /**
     * Allows usage of Color Codes in any String.
     * @param string String to add color to. Add the Color Code at the start.
     * @return Returns the Color Coded String.
     */
    public static String color (String string) { return ChatColor.translateAlternateColorCodes('&', string); }

    /**
     * Returns a color from a String for Spigot's Color method.
     * @param color String with the color name.
     * @return Returns the color.
     */
    public static Color colorFromString (String color)
    {
        return switch (color)
        {
            case "AQUA" -> Color.AQUA;
            case "BLACK" -> Color.BLACK;
            case "BLUE" -> Color.BLUE;
            case "FUCHSIA" -> Color.FUCHSIA;
            case "GRAY" -> Color.GRAY;
            case "GREEN" -> Color.GREEN;
            case "LIME" -> Color.LIME;
            case "MAROON" -> Color.MAROON;
            case "NAVY" -> Color.NAVY;
            case "OLIVE" -> Color.OLIVE;
            case "ORANGE" -> Color.ORANGE;
            case "PURPLE" -> Color.PURPLE;
            case "RED" -> Color.RED;
            case "SILVER" -> Color.SILVER;
            case "TEAL" -> Color.TEAL;
            case "YELLOW" -> Color.YELLOW;
            default -> Color.WHITE;
        };
    }

    /**
     * Creates ItemStacks.
     * @param name Item name.
     * @param lore Item lore.
     * @param material Item material.
     * @param quantity Item quantity.
     * @param customModelData Custom Model Data number.
     * @return Returns ItemStack.
     */
    public static ItemStack buildItem(String name, List<String> lore, Material material, int quantity, int customModelData)
    {
        ItemStack item = new ItemStack(material, quantity);
        ItemMeta itemMeta = item.getItemMeta();
        if (itemMeta != null)
        {
            itemMeta.setDisplayName(color(name));
            lore = (lore != null) ? lore.stream().map(Processes::color).collect(Collectors.toList()) : null;
            itemMeta.setLore(lore);
            itemMeta.setCustomModelData(customModelData);
            item.setItemMeta(itemMeta);
        }
        return item;
    }

    /**
     * Creates Player Heads ItemStacks.
     * @param name Item name.
     * @param lore Item lore.
     * @param player Head's owner.
     * @param quantity Item quantity.
     * @param customModelData Custom Model Data number.
     * @return Returns ItemStack.
     */
    public static ItemStack buildSkull(String name, List<String> lore, UUID player, int quantity, int customModelData)
    {
        ItemStack playerSkull = new ItemStack(Material.PLAYER_HEAD, quantity);
        SkullMeta skullMeta = (SkullMeta) playerSkull.getItemMeta();
        if (skullMeta != null)
        {
            skullMeta.setDisplayName(name);
            skullMeta.setOwningPlayer(Bukkit.getOfflinePlayer(player));
            lore = (lore != null) ? lore.stream().map(Processes::color).collect(Collectors.toList()) : null;
            skullMeta.setLore(lore);
            skullMeta.setCustomModelData(customModelData);
            playerSkull.setItemMeta(skullMeta);
        }

        return playerSkull;
    }

    /**
     * Creates Potion ItemStacks.
     * @param name Item name.
     * @param lore Item lore.
     * @param effect Potion effect.
     * @param color Potion color.
     * @param quantity Item quantity.
     * @param customModelData Custom Model Data number.
     * @return Returns ItemStack.
     */
    public static ItemStack buildPotion(String name, List<String> lore, PotionData effect, Color color, int quantity, int customModelData)
    {
        ItemStack potion = new ItemStack(Material.POTION, quantity);
        ItemMeta itemMeta = potion.getItemMeta();
        if (itemMeta != null)
        {
            itemMeta.setDisplayName(color(name));
            PotionMeta potionMeta = (PotionMeta) itemMeta;
            potionMeta.setBasePotionData(effect);
            if (color != null) potionMeta.setColor(color);
            lore = (lore != null) ? lore.stream().map(Processes::color).collect(Collectors.toList()) : null;
            itemMeta.setLore(lore);
            itemMeta.setCustomModelData(customModelData);
            potion.setItemMeta(itemMeta);
        }

        return potion;
    }

    /**
     * Checks if given String can be parsed to Integer.
     * @param string String To Check.
     * @return Returns True/False.
     */
    public static boolean isInteger(String string)
    {
        try
        {
            Integer.parseInt(string);
            return true;
        }
        catch (NumberFormatException e)
        {
            return false;
        }
    }

}
