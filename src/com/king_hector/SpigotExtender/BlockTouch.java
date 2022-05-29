package com.king_hector.SpigotExtender;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.List;
import java.util.Map;

@SuppressWarnings("unused")
public class BlockTouch
{

    private static BossBar progressBar = Bukkit.createBossBar("null", BarColor.WHITE, BarStyle.SOLID);

    /**
     * Enables the Progress Bar.
     * If this is enabled `convertTouchedBlocks()` will update the progress bar as the Player converts new blocks.
     * This method is designed to be called from a PlayerJoinEvent method but can be called from other places as well.
     * If this is Enabled, run on the server, and then Disabled, you need to delete `blockTouch.yml` from your plugin's folder inside the "Plugins" folder.
     * @param player The Player to assign the bar to.
     * @param title
     * @param barColor
     * @param barStyle
     * @param finalTitle
     * @param finalBarColor
     * @param finalBarStyle
     */
    public static void blockTouchProgressBar(Player player, String title, BarColor barColor, BarStyle barStyle, String finalTitle, BarColor finalBarColor, BarStyle finalBarStyle)
    {
        //Progress Bar Setup
        progressBar.setTitle(title);
        progressBar.setColor(barColor);
        progressBar.setStyle(barStyle);
        progressBar.addPlayer(player);



        //progressBar.setProgress(modifyProgressBarFile.getDouble("Progress"));



        //Changes the Progress Bar to the finished style if the Player reconnects.
        if (progressBar.getProgress() == 1)
        {
            progressBar.setTitle(finalTitle);
            progressBar.setColor(finalBarColor);
            progressBar.setStyle(finalBarStyle);
        }
    }

    /**
     * Converts blocks the Player walks on to a specified block.
     * This method is designed to be called from a PlayerMoveEvent method.
     * @param player The affected Player.
     * @param block The block to convert to.
     * @param previousBlock `event.getFrom().getBlock()` should be assigned here.
     * @param nextBlock `event.getTo().getBlock()` should be assigned here.
     * @param blocksToExclude A list of Blocks to exclude from converting.
     */
    public static void convertTouchedBlocks(Player player, Material block, Block previousBlock, Block nextBlock, List<Material> blocksToExclude)
    {
        int blockLocation = player.getLocation().getBlockY() - 1;
        Block blockUnderPlayer = player.getWorld().getBlockAt(player.getLocation().getBlockX(), blockLocation, player.getLocation().getBlockZ());

        //If nextBlock is null then previousBlock is assigned
        if (nextBlock == null)
            nextBlock = previousBlock;

        //Blocks To Exclude
        int nextBlockY = nextBlock.getLocation().getBlockY() - 1;
        if (player.getWorld().getBlockAt(nextBlock.getX(), nextBlockY, nextBlock.getZ()).getType().equals(block)) return; //Excludes already set blocks

        if (blocksToExclude != null)
        {
            for (Material b : blocksToExclude)
                if (blockUnderPlayer.getType().equals(b)) return;
        }

        //Set block under player to specified block
        blockUnderPlayer.setType(block);
    }

    /**
     * Converts items the Player holds to a specified item.
     * This method is designed to be called from a PlayerItemHeldEvent method twice as explained in the slot parameter.
     * @param player The affected Player.
     * @param item The Item to convert to.
     * @param slot `event.getPreviousSlot()` or `event.getNewSlot()` should be assigned here. In order for the method to work as intended it should be called twice, each time using each specified argument.
     * @param itemsToExclude A list of Items to exclude from converting.
     * @param convertibleItems A map of Items that can be converted to other Items when Player holds them. The Key of the map is the Item that will be converted, the Value of the map is the Item that the first Item will be converted to.
     */
    public static void convertTouchedItems(Player player, Material item, int slot, List<Material> itemsToExclude, List<ItemStack> customItemsToExclude, Map<ItemStack, ItemStack> convertibleItems)
    {
        PlayerInventory playerInventory = player.getInventory();
        ItemStack slotItem = playerInventory.getItem(slot);

        if (slotItem == null) return;
        if (slotItem.getType().equals(item)) return;

        //Items to exclude
        if (itemsToExclude != null)
        {
            for (Material i : itemsToExclude)
                if (slotItem.getType().equals(i)) return;
        }

        //Custom items to exclude
        if (customItemsToExclude != null)
        {
            for (ItemStack i : customItemsToExclude)
                if (slotItem.isSimilar(i)) return;
        }

        //Set holding convertable items to their specified item
        for (ItemStack c : convertibleItems.keySet())
        {
            ItemStack newItem = convertibleItems.get(c);
            if (slotItem.equals(newItem)) return;

            if (slotItem.getType().equals(c.getType()))
            {
                slotItem.setType(newItem.getType());
                slotItem.setItemMeta(newItem.getItemMeta());
                return;
            }
        }

        //Set holding item to specified item
        slotItem.setType(item);
    }

}
