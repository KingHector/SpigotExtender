# DeveloperAPI

A collection of usefull methods for better stability and readability of your code.

## Processes

Processes contains methods that will make your code easier to write.

### Color Method

Using `ChatColor.COLOR` in your code is not the best way to add color to your Strings as it can get really obnoxious, 
especially if you want a multicolored one.

With the Color Dethod you can use Minecraft's ColorCodes which is more efficient and easier.

`processes.color(<String string>);`

Output: PhileasFogg3 is bad! (In red)

### BuildItem & BuildSkull Methods

Normally when you need to create ItemStacks with either an item or a skull you need to do something like this:

```ItemStack item = new ItemStack(material, quantity);
ItemMeta itemMeta = item.getItemMeta();
Objects.requireNonNull(itemMeta).setDisplayName(color(name));
itemMeta.setLore(lore);
item.setItemMeta(itemMeta); 
```     
        
With BuildItem & BuildSkull Methods you can do this in just one line!

```processes.buildItem(String name, List<String> lore, Material material, int quantity);```

```processes.buildSkull(String name, List<String> lore, UUID player, int quantity)```

NOTE: In order to add lores you need to use ```Arrays.asList()```

## MenuBuilder

MenuBuilder is an easier way of creating GUI. Normally you would need a GUI class and a GUIListener class. Using thing you can now have a complete GUI in just ONE class, which is fast to write, easy to read and more performant.

###Menu Creation

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
        setInventoryOpened(opened -> opened.sendMessage("§aYou opened the inventory!"));
        setInventoryClosed(closed -> closed.sendMessage("§cYou closed the inventory!"));

        // Register a do-nothing button.
        registerButton(new MenuButton(new ItemStack(Material.WOODEN_PICKAXE)), 0);

        // Register an action button.
        registerButton(new MenuButton(new ItemStack(Material.DIAMOND_PICKAXE)).setWhenClicked(clicked -> clicked.sendMessage("You clicked a button!")), 1);
    }
}
```
