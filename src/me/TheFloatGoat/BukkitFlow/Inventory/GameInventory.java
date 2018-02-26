package me.TheFloatGoat.BukkitFlow.Inventory;

import me.TheFloatGoat.BukkitFlow.ReadWrite.LevelLoader;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;


import java.util.ArrayList;

public class GameInventory {

    Plugin plugin;

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

    public ItemStack[] getItemstack(int array[]) {

        ItemStack[] itemStacks = new ItemStack[array.length];

        for(int i = 0; i < itemStacks.length; i++) {

            if(array[i] > 0) {

                ItemStack item = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) array[i]);
                ItemMeta meta = item.getItemMeta();
                meta.setDisplayName("Anchor Point");
                ArrayList<String> loreList = new ArrayList<>();
                loreList.add("BukkitFlow");
                meta.setLore(loreList);
                item.setItemMeta(meta);
                itemStacks[i] = item;

            }else if (array[i] == -2) {

                //Barrier block
                ItemStack item = new ItemStack(Material.IRON_FENCE, 1);
                ItemMeta meta = item.getItemMeta();
                meta.setDisplayName("Barrier block");
                item.setItemMeta(meta);
                itemStacks[i] = item;

            }

            //If it's not positive and not -2, then we'll assume it's meant to be an open space. (for example, -1)

        }


        return itemStacks;
    }

    public Inventory buildInventory(int[] array, int levelID) {

        Inventory inventory = Bukkit.createInventory(null, array.length, "BukkitFlow: level "+levelID);
        inventory.setContents(getItemstack(array));
        return inventory;

    }


}
