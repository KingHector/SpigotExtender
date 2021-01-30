# VentureKraftAPI

A collection of useful methods for better stability, writability and readability of your code.
Special thanks to Thorbie and the entire Stephen Kings Course Discord Community.

## Processes

Processes contains methods that will make your code easier to write.

### Color Method

Using `ChatColor.COLOR` in your code is not the best way to add color to your Strings as it can get really obnoxious, 
especially if you want a multicolored one.

With the Color Method you can use Minecraft's ColorCodes which is more efficient and easier. The ColorCode should be inside the String. You can check the ColorCodes [here](https://minecraft.gamepedia.com/Formatting_codes)

`Processes.color(<String string>);`

### BuildItem & BuildSkull Methods

Normally when you need to create ItemStacks with either an item or a skull you need to do something like this:

```ItemStack item = new ItemStack(<material>, <quantity>);
ItemMeta itemMeta = item.getItemMeta();
itemMeta.setDisplayName(color(<name>));
itemMeta.setLore(<lore>);
item.setItemMeta(itemMeta); 
```     
        
With BuildItem & BuildSkull Methods you can do this in just one line!

```Processes.buildItem(String name, List<String> lore, Material material, int quantity);```

```Processes.buildSkull(String name, List<String> lore, UUID player, int quantity)```

NOTE: In order to add lores you need to use ```Arrays.asList()```

## MenuBuilder

MenuBuilder is an easier way of creating GUI. Normally you would need a GUI class and a GUIListener class. Using thing you can now have a complete GUI in just ONE class, which is fast to write, easy to read and more performant.

NOTE: If you intent to make GUI's using MenuBuilder you need to register the following event in your Main class, in your onEnable Method.
`Bukkit.getPluginManager().registerEvents(new MenuListener(), this);`

### Menu Creation

You need to create a class which extends Menu.
The `inventoryOpened`, `inventoryClosed` and `whenClicked` consumers do NOT have to be set.

```
public class TestMenu extends Menu 
{

    public TestMenu() 
    {
        // Initialise our menu.
        super("Test Menu", 1);

        // Set our inventory consumers.
        setInventoryOpened(opened -> opened.sendMessage(processes.color("&aYou opened the inventory!")));
        setInventoryClosed(closed -> closed.sendMessage(processes.color("&cYou closed the inventory!")));

        // Register a do-nothing button.
        registerButton(new MenuButton(new ItemStack(Material.WOODEN_PICKAXE)), 0);

        // Register an action button.
        registerButton(new MenuButton(new ItemStack(Material.DIAMOND_PICKAXE)).setWhenClicked(clicked -> clicked.sendMessage("You clicked a button!")), 1);
    }
    
}
```

Finally in order to use this you need to do the following.
```
Menu testMenu = new TestMenu();
testMenu.open(player);
```

## YMLBuilder

YMLBuilder is an easier way of creating .yml files from within your code.

### .yml Creation

In your Main class you need to do the following.

```
public class YourClass extends JavaPlugin
{

    private YMLBuilder yourFile;          

    private void initiateFiles()
    {
        yourFile = new YMLBuilder(this, "yourFileName")    
    }

    @Override
    public void onEnable()
    {
        initiateFiles();    
    }

    public YMLBuilder getYourFile() { return yourFile; }

    public void setYourFile(YMLBuilder yourFile)
    {
        this.yourFile = yourFile;    
    }
    
}    
```

### Accessing & Saving The .yml File

Accessing your .yml file from within your code is basically the same as it was before.
Previously you would do this.

```main.getYourFile.getStringList()...```

Now you need to do this.

```main.getYourFile.getConfig().getStringList()...```

So basically after you need to add the `getConfig()` method after you get your file.

Finally in order to save the .yml file you need to do the following.

```main.getYourFile.save();```
