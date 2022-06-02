package com.king_hector.spigotextender.MenuBuilder;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class MenuManager
{

    private static MenuManager instance;

    public static MenuManager getInstance()
    {
        if (instance == null)
        {
            instance = new MenuManager();
        }
        return instance;
    }

    private final Map<UUID, Menu> openMenus;

    /**
     * Class constructor
     */
    public MenuManager()
    {
        this.openMenus = new HashMap<>();
    }

    /**
     * Register a menu to a user.
     * @param toRegister The user.
     * @param menu The menu.
     */
    public void registerMenu(UUID toRegister, Menu menu)
    {
        openMenus.put(toRegister, menu);
    }

    /**
     * Unregister a menu.
     * @param toUnRegister User's menu to unregister.
     */
    public void unregisterMenu(UUID toUnRegister)
    {
        openMenus.remove(toUnRegister);
    }

    /**
     * Find a menu.
     * @param user User to search for.
     * @return The Menu found, or null if it does not exist.
     */
    public Menu matchMenu(UUID user) { return openMenus.get(user); }

}