package com.king_hector.spigotextender.MenuBuilder;

import java.util.function.Consumer;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

@SuppressWarnings("unused")
public class MenuButton
{

    private final ItemStack itemStack;

    /**
     * Class constructor
     * @param itemStack ItemStack to use.
     */
    public MenuButton(ItemStack itemStack)
    {
        this.itemStack = itemStack;
    }

    /**
     * Returns the value of the whenClicked consumer.
     * @return whenClicked consumer.
     */
    public Consumer<Player> getWhenClicked()
    {
        return whenClicked;
    }

    private Consumer<Player> whenClicked;

    /**
     * Sets the value of the whenClicked consumer.
     * @param whenClicked The consumer to set.
     * @return Returns this object.
     */
    public MenuButton setWhenClicked(Consumer<Player> whenClicked)
    {
        this.whenClicked = whenClicked;
        return this;
    }

    /**
     * Returns the ItemStack for this button.
     * @return The itemStack supplied in the constructor.
     */
    public ItemStack getItemStack()
    {
        return itemStack;
    }

}
