package me.TheFloatGoat.BukkitFlow;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

public class LevelCreationHandlers implements Listener {


    Plugin plugin = Bukkit.getPluginManager().getPlugin("BukkitFlow");


    @EventHandler
    void onInventoryClick(InventoryClickEvent e) {

        String title = e.getInventory().getTitle();

        if(title.equals("BukkitFlow: Level creation")) {

            if(e.getCurrentItem().getType() != Material.AIR) {

                if(e.getCurrentItem().hasItemMeta()) {

                    String command = e.getCurrentItem().getItemMeta().getDisplayName().toLowerCase();

                    switch(command) {
                        case "accept":
                            int id = 4;
                            LevelSaver levelSaver = new LevelSaver(plugin);
                            levelSaver.saveFile(id, e.getWhoClicked().getOpenInventory().getTopInventory().getContents());
                            e.getWhoClicked().sendMessage("Saved level to \""+id+"\".txt");
                            e.setCancelled(true);
                            break;
                        case "dismiss":


                            e.setCancelled(true);
                            break;
                        case "randomize":

                            e.setCancelled(true);
                            break;
                        case "add color...":

                            ItemStack glass = e.getCurrentItem();
                            e.setCancelled(true);
                            e.setCursor(glass);
                            break;
                        case "barrier block":
                            ItemStack barrier = e.getCurrentItem();
                            e.setCancelled(true);
                            e.setCursor(barrier);
                            break;

                    }
                }
            } else {
                if(e.getCursor().getItemMeta().getLore().contains("BukkitFlow")) { // might crash on nullPointers
                    ItemStack cursorItem = e.getCursor();
                    if(e.getClickedInventory() != null && e.getClickedInventory().getTitle().equals(title)) {
                        e.getClickedInventory().setItem(e.getSlot(), cursorItem);
                        e.setCancelled(true);
                    }
                }
            }
        }
    }
}
