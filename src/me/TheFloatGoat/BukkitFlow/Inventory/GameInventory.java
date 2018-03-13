package me.TheFloatGoat.BukkitFlow.Inventory;

import me.TheFloatGoat.BukkitFlow.Helpers.InventoryHelpers;
import me.TheFloatGoat.BukkitFlow.ReadWrite.LevelLoader;
import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

public class GameInventory {

    Plugin plugin;
    InventoryHelpers IH = new InventoryHelpers();

    public GameInventory(Plugin plugin) {
        this.plugin = plugin;
    }

    public Inventory testRun(ItemStack[] contents) {

        Inventory inventory = Bukkit.createInventory(null, contents.length, "BukkitFlow: level testing");
        inventory.setContents(contents);
        return inventory;
    }

    public Inventory fromID(int id) {
        LevelLoader levelLoader = new LevelLoader(plugin);
        return buildInventory(levelLoader.load(id), id);
    }

    public ItemStack[] getItemStack(int array[]) {

        ItemStack[] itemStacks = new ItemStack[array.length];

        for(int i = 0; i < itemStacks.length; i++) {

            itemStacks[i] = IH.genItem(true, array[i]);

        }


        return itemStacks;
    }

    public Inventory buildInventory(int[] array, int levelID) {

        Inventory inventory = Bukkit.createInventory(null, array.length, "BukkitFlow: level "+levelID);
        inventory.setContents(getItemStack(array));
        return inventory;

    }


}
