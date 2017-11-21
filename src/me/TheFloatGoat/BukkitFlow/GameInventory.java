package me.TheFloatGoat.BukkitFlow;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class GameInventory {

    int[] itemIDs;
    int levelID;
    Player player;

    public GameInventory(int lID, Player p) {
        player = p;
        levelID = lID;
        LevelLoader levelLoader = new LevelLoader();
        itemIDs = levelLoader.load(levelID);
        if(itemIDs == null) {
            player.sendMessage("Level not found.");
            return;
        }
        buildInventory();
    }

    public void buildInventory() {

        Inventory inventory = Bukkit.createInventory(null, itemIDs.length, "BukkitFlow: level "+levelID);

        for(int i = 0; i < itemIDs.length; i++) {

            if(itemIDs[i] > 0) {

                ItemStack item = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) itemIDs[i]);
                ItemMeta meta = item.getItemMeta();
                meta.setDisplayName("Anchor Point");
                ArrayList<String> loreList = new ArrayList<>();
                loreList.add("BukkitFlow");
                meta.setLore(loreList);
                item.setItemMeta(meta);
                inventory.setItem(i, item);

            }else if (itemIDs[i] == -2) {

                //Barrier block
                ItemStack item = new ItemStack(Material.IRON_FENCE, 1);
                ItemMeta meta = item.getItemMeta();
                meta.setDisplayName("Barrier block");
                item.setItemMeta(meta);
                inventory.setItem(i, item);

            }

            //If it's not positive and not -2, then we'll assume it's meant to be an open space. (for example, -1)

        }

        player.openInventory(inventory);

    }


}
