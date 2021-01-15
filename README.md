# DeveloperAPI

A collection of usefull methods for better stability and readability of your code.

## Processes

### Color Method

Using ```ChatColor.COLOR ``` in your code is not the best way to add color to your Strings as it can get really obnoxious, 
especially if you want a multicolored one.

With the Color Dethod you can use Minecraft's ColorCodes which is more efficient and easier.

Example: ```player.sendMessage(processes.color("&cPhileasFogg3 is bad!"));```

Output: PhileasFogg3 is bad! (In red)

### BuildItem & BuildSkull Methods

Normally when you need to create ItemStacks with either an item or a skull you need to do something like this:

```
ItemStack item = new ItemStack(material, quantity);
ItemMeta itemMeta = item.getItemMeta();
Objects.requireNonNull(itemMeta).setDisplayName(color(name));
itemMeta.setLore(lore);
item.setItemMeta(itemMeta);
```        
        
With BuildItem & BuildSkull Methods you can do this in just one line!

Example One: ```processes.buildItem(processes.color("&eItem"), processes.color("&cLore"), Material.IRON_INGOT, 1);```

Output: This will create 1 Iron Ingot named "Item" (in yellow), with the lore "Lore" (in red).

Example Two: ```processes.buildItem(processes.color("&eSkull"), processes.color("&cPretty"), 77ebefb3-2bf1-485d-b5e3-d74464526993, 1);```

Output: This will create 1 skull of King_Hector named "Skull" (in yellow), with the lore "Pretty" (in red).
