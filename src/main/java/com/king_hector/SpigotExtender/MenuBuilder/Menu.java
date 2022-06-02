package com.king_hector.spigotextender.MenuBuilder;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

@SuppressWarnings("unused")
public class Menu
{

    private final Inventory inventory;

    /**
     * Menu constructor.
     * @param title Inventory name.
     * @param rows Inventory rows.
     */
    public Menu(String title, int rows)
    {
        if (rows > 6 || rows < 1 || title.length() > 32)
        {
            // Invalid rows / title length requested.
            throw new IllegalArgumentException("Invalid arguments passed to menu constructor.");
        }

        // Initialise variables
        this.inventory = Bukkit.createInventory(null, rows * 9, title);
        this.buttonMap = new HashMap<>();
    }

    private final Map<Integer, MenuButton> buttonMap;

    /**
     * Registers a button in a specified slot.
     * @param button Button to register.
     * @param slot The slot to associate with the button.
     */
    public void registerButton(MenuButton button, int slot)
    {
        buttonMap.put(slot, button);
    }

    private Consumer<Player> inventoryOpened;

    /**
     * Sets the value of the inventoryOpened consumer.
     * @param inventoryOpened Consumer.
     */
    public void setInventoryOpened(Consumer<Player> inventoryOpened)
    {
        this.inventoryOpened = inventoryOpened;
    }

    /**
     * Handles a player opening the inventory. <br>
     * Executes the inventoryOpen consumer if it is not null.
     * @param player Player who opened the Inventory.
     */
    public void handleOpen(Player player)
    {
        // Is there an action associated with opening the inventory?
        if (inventoryOpened != null)
        {
            inventoryOpened.accept(player);
        }
    }

    private Consumer<Player> inventoryClosed;

    /**
     * Sets the value of the inventoryClosed consumer.
     * @param inventoryClosed Consumer.
     */
    public void setInventoryClosed(Consumer<Player> inventoryClosed)
    {
        this.inventoryClosed = inventoryClosed;
    }

    /**
     * Handles a player closing the inventory. <br>
     * Executes the inventoryClosed consumer if it is not null.
     * @param player Player who closed the Inventory.
     */
    public void handleClose(Player player)
    {
        if (inventoryClosed != null)
        {
            inventoryClosed.accept(player);
        }
    }

    /**
     * Handles an InventoryClickEvent inside this menu.
     * @param event The InventoryClickEvent.
     */
    public void handleClick(InventoryClickEvent event)
    {
        ItemStack clicked = event.getCurrentItem();

        if (clicked == null)
        {
            return;
        }

        if (buttonMap.containsKey(event.getRawSlot()))
        {
            // Clicked on a valid button.

            event.setCancelled(true);
            Consumer<Player> consumer = buttonMap.get(event.getRawSlot()).getWhenClicked();

            // Does the button clicked have an action associated with it?
            if (consumer != null)
            {
                consumer.accept((Player) event.getWhoClicked());
            }
        }
    }

    /**
     * Opens the inventory to a specified player.
     * @param player Player to open the inventory to.
     */
    public void open(Player player)
    {
        MenuManager manager = MenuManager.getInstance();

        buttonMap.forEach((slot, button) -> inventory.setItem(slot, button.getItemStack()));

        // Open the inventory and handle the open event.
        player.openInventory(inventory);
        manager.registerMenu(player.getUniqueId(), this);
        handleOpen(player);
    }

}