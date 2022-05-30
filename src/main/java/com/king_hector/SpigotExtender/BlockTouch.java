package com.king_hector.SpigotExtender;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@SuppressWarnings("unused")
public class BlockTouch
{

    //The first Progress Bar (ProgressBar) is the standard one that changes while the Player plays the game.
    //The second Progress Bar (ProgressBarFinished) is the final one that appears once the standard one has been filled.
    //In general one is the Gameplay bar while the other one is the Cosmetic bar. Only one bar is visible each time.
    //When one is hidden the other one is visible.
    private static final BossBar progressBar = Bukkit.createBossBar("null", BarColor.WHITE, BarStyle.SOLID);
    private static final BossBar progressBarFinished = Bukkit.createBossBar("null", BarColor.WHITE, BarStyle.SOLID);

    //A list of Players that have one of the two Progress Bars visible.
    //Used to reassign Progress Bars.
    private static final List<Player> playerList = new ArrayList<>();

    /**
     * Enables the Block Touch Progress Bar.
     * If this is enabled `convertTouchedBlocks()` will update the progress bar as the Player converts new blocks.
     * This method is designed to be called from a PlayerJoinEvent method but can be called from other places as well.
     * If this is Enabled, run on the server, and then Disabled, you need to delete `blockTouch.yml` from your plugin folder or slight problems may arise.
     * @param player The Player to assign the bar to.
     * @param plugin Your main plugin instance.
     * @param title The Progress Bar Title.
     * @param barColor The Progress Bar Color.
     * @param barStyle The Progress Bar Style.
     * @param finalTitle The Progress Bar Title after completion.
     * @param finalBarColor The Progress Bar Color after completion.
     * @param finalBarStyle The Progress Bar Style after completion.
     */
    public static void blockTouchProgressBarEnable(Player player, Plugin plugin, String title, BarColor barColor, BarStyle barStyle, String finalTitle, BarColor finalBarColor, BarStyle finalBarStyle)
    {
        //File Initialization
        File blockTouch = new File(plugin.getDataFolder(), "blockTouch.yml");
        YamlConfiguration modifyBlockTouch = YamlConfiguration.loadConfiguration(blockTouch);

        if (!blockTouch.exists())
        {
            modifyBlockTouch.set("Progress", 0);

            //Save Changes
            Processes.saveFileChanges(blockTouch, modifyBlockTouch);
        }

        //Standard Progress Bar Setup
        progressBar.setTitle(title);
        progressBar.setColor(barColor);
        progressBar.setStyle(barStyle);
        progressBar.addPlayer(player);
        progressBar.setProgress(modifyBlockTouch.getDouble("Progress"));

        //Finished Progress Bar Setup
        progressBarFinished.setTitle(finalTitle);
        progressBarFinished.setColor(finalBarColor);
        progressBarFinished.setStyle(finalBarStyle);
        progressBarFinished.setProgress(1);

        //Changes the Progress Bar to the Finished bar if a Player connects/reconnects when the Progress Bar has reached 100%
        if (progressBar.getProgress() == 1)
        {
            progressBar.removePlayer(player);
            progressBarFinished.addPlayer(player);
        }

        //Adds Player to the playerList
        playerList.add(player);
    }

    /**
     * Disables the Block Touch Progress Bar.
     * * This method is designed to be called from a PlayerQuitEvent method but can be called from other places as well.
     */
    public static void blockTouchProgressBarDisable()
    {
        progressBar.removeAll();
        progressBarFinished.removeAll();
    }

    /**
     * Converts blocks the Player walks on to a specified block.
     * This method is designed to be called from a PlayerMoveEvent method.
     * @param player The affected Player.
     * @param block The block to convert to.
     * @param previousBlock `event.getFrom().getBlock()` should be assigned here.
     * @param nextBlock `event.getTo().getBlock()` should be assigned here.
     * @param blocksToExclude A list of Blocks to exclude from converting.
     * @param plugin Your main plugin instance.
     */
    public static void convertTouchedBlocks(Player player, Material block, Block previousBlock, Block nextBlock, List<Material> blocksToExclude, Plugin plugin)
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

        //Increase Progress Bar if enabled
        addBarProgress(0.001, true, plugin);
    }

    /**
     * Converts items the Player holds to a specified item.
     * This method is designed to be called from a PlayerItemHeldEvent method twice as explained in the slot parameter.
     * @param player The affected Player.
     * @param item The Item to convert to.
     * @param slot `event.getPreviousSlot()` or `event.getNewSlot()` should be assigned here. In order for the method to work as intended it should be called twice, each time using each specified argument.
     * @param itemsToExclude A list of Items to exclude from converting.
     * @param convertibleItems A map of Items that can be converted to other Items when Player holds them. The Key of the map is the Item that will be converted, the Value of the map is the Item that the first Item will be converted to.
     * @param plugin Your main plugin instance.
     */
    public static void convertTouchedItems(Player player, Material item, int slot, List<Material> itemsToExclude, List<ItemStack> customItemsToExclude, Map<ItemStack, ItemStack> convertibleItems, Plugin plugin)
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
        if (convertibleItems != null)
        {
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
        }

        //Set holding item to specified item
        slotItem.setType(item);

        //Increase Progress Bar if enabled
        addBarProgress(0.001, true, plugin);
    }

    /**
     * Adds a specified amount of Progress to the Progress Bar.
     * @param amount The amount to add. Assign something small as when the bar reaches 1 it is filled.
     * @param playEffects Set whether the completion particles and sounds should be played.
     * @param plugin Your main plugin instance.
     */
    public static void addBarProgress(double amount, boolean playEffects, Plugin plugin)
    {
        //File Initialization
        File blockTouch = new File(plugin.getDataFolder(), "blockTouch.yml");
        YamlConfiguration modifyBlockTouch = YamlConfiguration.loadConfiguration(blockTouch);

        //Stop if Progress Bar is disabled or if Progress Bar is complete
        if (!blockTouch.exists()) return;
        if (modifyBlockTouch.getDouble("Progress") >= 1) return;

        //Apply Changes
        double oldProgress = modifyBlockTouch.getDouble("Progress");
        modifyBlockTouch.set("Progress", oldProgress + amount);
        double newProgress = modifyBlockTouch.getDouble("Progress");
        Processes.saveFileChanges(blockTouch, modifyBlockTouch);

        if (newProgress >= 1) //Completed Progress Bar
        {
            modifyBlockTouch.set("Progress", 1);
            Processes.saveFileChanges(blockTouch, modifyBlockTouch);
            progressBar.setProgress(1);
            progressBar.removeAll();

            //Effects
            for (Player p : playerList)
            {
                if (playEffects)
                {
                    for (int i = 0; i < 10; i++)
                    {
                        Firework firework = p.getWorld().spawn(p.getLocation(), Firework.class);
                        FireworkMeta fireworkMeta = firework.getFireworkMeta();
                        fireworkMeta.addEffect(FireworkEffect.builder().withColor(Color.AQUA).withColor(Color.BLUE).with(FireworkEffect.Type.STAR).build());
                        fireworkMeta.setPower(0);
                        firework.setFireworkMeta(fireworkMeta);
                    }
                }

                //Assign to all the Players the Finished Progress Bar
                progressBarFinished.addPlayer(p);
            }
        }
        else
            progressBar.setProgress(newProgress);
    }

}
