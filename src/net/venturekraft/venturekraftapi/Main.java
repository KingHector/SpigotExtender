package net.venturekraft.venturekraftapi;

import net.venturekraft.venturekraftapi.MenuBuilder.MenuListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin
{

    public void onEnable()
    {
        //Events
        Bukkit.getPluginManager().registerEvents(new MenuListener(), this);
    }

}
