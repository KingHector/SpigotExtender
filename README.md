# DeveloperAPI

A collection of usefull methods for better stability and readability of your code.

## Processes

Processes contains methods that will make your code easier to write.

### Color Method

Using ```ChatColor.COLOR``` in your code is not the best way to add color to your Strings as it can get really obnoxious, 
especially if you want a multicolored one.

With the Color Dethod you can use Minecraft's ColorCodes which is more efficient and easier.

```processes.color(<String string>);```

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

