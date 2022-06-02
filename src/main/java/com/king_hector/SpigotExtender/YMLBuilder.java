package com.king_hector.spigotextender;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;

@Deprecated
public class YMLBuilder
{

    private final JavaPlugin core;
    private final File file;
    private final FileConfiguration config;

    @Deprecated
    public YMLBuilder(JavaPlugin core, String name)
    {
        this.core = core;
        file = new File(core.getDataFolder(), name + ".yml");
        config = YamlConfiguration.loadConfiguration(file);
        createFile();
    }

    @Deprecated
    public void save()
    {
        try
        {
            config.save(file);
        }
        catch (IOException ex)
        {
            throw new UncheckedIOException(ex);
        }
    }

    /**
     * The standard getter for the file variable.
     * @return Returns file.
     */
    @Deprecated
    public File getFile()
    {
        return file;
    }

    /**
     * The standard getter for the config variable.
     * @return Returns config.
     */
    @Deprecated
    public FileConfiguration getConfig()
    {
        return config;
    }

    /**
     * Creates the file including the folder in case they don't exist.
     * Rethrows the failure.
     */
    @Deprecated
    private void createFile()
    {
        if(!core.getDataFolder().exists())
        {
            core.getDataFolder().mkdirs();
        }

        if(!file.exists())
        {
            try
            {
                file.createNewFile();
            }
            catch (IOException ex) {

                throw new UncheckedIOException(ex);
            }
        }
    }

}