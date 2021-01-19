package net.venturekraft.VentureKraftAPI;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;

public class YMLBuilder
{

    private final VentureKraftAPI core;
    private final File file;
    private final FileConfiguration config;

    public YMLBuilder(VentureKraftAPI core, String name)
    {
        this.core = core;
        file = new File(core.getDataFolder(), name + ".yml");
        config = new YamlConfiguration().loadConfiguration(file);
        createFile();
    }

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
     *
     * @return file
     */
    public File getFile()
    {
        return file;
    }

    /**
     * The standard getter for the config variable.
     *
     * @return config
     */
    public FileConfiguration getConfig()
    {
        return config;
    }

    /**
     * Creates the file including the folder in case they don't exist.
     *
     * Rethrows the failure.
     */
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