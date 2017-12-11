package me.TheFloatGoat.BukkitFlow;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

public class LevelCreationInventory {

    Plugin plugin;
    Player player;
    String prefix = "[BukkitFlow] ";

    LevelCreationInventory(Plugin plugin, Player player) {
        this.plugin = plugin;
        this.player = player;
    }

    void createUI() {

        //This should be replaced by code to create a temp inventory and restore the old one after the user is done.
        ItemStack[] playerInv = player.getInventory().getContents();
        for(ItemStack cur: playerInv) {
            if(cur != null && cur.getType()!= Material.AIR) {
                player.sendMessage(prefix+"Please empty your inventory first.");
                return;
            }
        }


        //Open the gameInventory
        Inventory gameInventory = Bukkit.createInventory(null, 54, "BukkitFlow: Level creation");
        player.openInventory(gameInventory);
        //Open the UI
        Inventory controlInventory = Bukkit.createInventory(null, 36, "");
        controlInventory.setItem(9, createButton(5, "Accept"));
        controlInventory.setItem(13, createButton(1, "Randomize"));
        controlInventory.setItem(17, createButton(14, "Dismiss"));

        addColorPallet(27, controlInventory);


        player.getInventory().setContents(controlInventory.getContents());

    }




    private ItemStack createButton(int colorID, String label) {

        ItemStack button = new ItemStack(Material.WOOL, 1, (short) colorID);
        ItemMeta meta = button.getItemMeta();
        meta.setDisplayName(label);

        button.setItemMeta(meta);
        return button;
    }

    private void addColorPallet(int startingPoint, Inventory inventory) {

        if(startingPoint+9 > inventory.getSize()) return;

        ItemStack barrier = new ItemStack(Material.IRON_FENCE, 1);
        ItemMeta barrierMeta = barrier.getItemMeta();
        barrierMeta.setDisplayName("Barrier Block");
        barrier.setItemMeta(barrierMeta);
        inventory.setItem(startingPoint, barrier);

        ItemStack empty = new ItemStack(Material.BARRIER, 1);
        ItemMeta emptyMeta = empty.getItemMeta();
        emptyMeta.setDisplayName("Void");
        empty.setItemMeta(emptyMeta);
        inventory.setItem(startingPoint+1, empty);

        for(int i = 0; i < 16; i++) {

            ItemStack item = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) i);
            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName("Add color...");
            item.setItemMeta(meta);

            inventory.setItem((i+startingPoint+2>=inventory.getSize()?(i+startingPoint+2)%9:i+startingPoint+2), item);

        }
    }

}
