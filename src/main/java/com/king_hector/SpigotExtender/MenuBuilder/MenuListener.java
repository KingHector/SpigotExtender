package com.king_hector.spigotextender.MenuBuilder;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerQuitEvent;

@SuppressWarnings("unused")
public class MenuListener implements Listener
{

    @EventHandler
    public void InventoryClick(InventoryClickEvent e)
    {
        Menu matchedMenu = MenuManager.getInstance().matchMenu(e.getWhoClicked().getUniqueId());

        if (matchedMenu != null)
        {
            matchedMenu.handleClick(e);
        }
    }

    @EventHandler
    public void InventoryClose(InventoryCloseEvent e)
    {
        Menu matchedMenu = MenuManager.getInstance().matchMenu(e.getPlayer().getUniqueId());

        if (matchedMenu != null)
        {
            matchedMenu.handleClose((Player) e.getPlayer());
        }

        // Unregister menu - it has been closed.
        MenuManager.getInstance().unregisterMenu(e.getPlayer().getUniqueId());
    }

    @EventHandler
    public void PlayerQuit(PlayerQuitEvent e)
    {
        Menu matchedMenu = MenuManager.getInstance().matchMenu(e.getPlayer().getUniqueId());

        if (matchedMenu != null)
        {
            matchedMenu.handleClose(e.getPlayer());
        }

        // Unregister menu - the player has quit.
        MenuManager.getInstance().unregisterMenu(e.getPlayer().getUniqueId());
    }

}