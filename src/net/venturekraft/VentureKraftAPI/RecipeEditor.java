package net.venturekraft.VentureKraftAPI;

import net.minecraft.server.v1_16_R3.IRecipe;
import net.minecraft.server.v1_16_R3.MinecraftKey;
import net.minecraft.server.v1_16_R3.Recipes;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.libs.it.unimi.dsi.fastutil.objects.Object2ObjectLinkedOpenHashMap;
import org.bukkit.craftbukkit.v1_16_R3.CraftServer;

public class RecipeEditor
{

    /**
     * Change hte amount of recipe results.
     * @param recipeKey the key of the recipes.
     * @param count the amount of items the recipe will yield.
     * @return false if no recipe by that key could be found, true if the change has been made.
     */
    public static boolean setRecipeResultCount(String recipeKey, int count) {
        // Convert key to minecraft:... key.
        MinecraftKey key = new MinecraftKey(recipeKey);

        // Get all crafting recipes.
        Object2ObjectLinkedOpenHashMap<MinecraftKey, IRecipe<?>> craftingRecipes = ((CraftServer) Bukkit.getServer()).getServer().getCraftingManager().recipes.get(Recipes.CRAFTING);

        // Get the recipe associated with the given key.
        IRecipe<?> iRecipe = craftingRecipes.get(key);
        if (iRecipe == null) { return false; }

        // Change the amount.
        iRecipe.getResult().setCount(count);
        return true;
    }

}
