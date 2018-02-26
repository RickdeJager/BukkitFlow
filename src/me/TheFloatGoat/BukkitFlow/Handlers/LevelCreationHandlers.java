package me.TheFloatGoat.BukkitFlow.Handlers;

import me.TheFloatGoat.BukkitFlow.Inventory.GameInventory;
import me.TheFloatGoat.BukkitFlow.LevelCreation.RandomLevelCreator;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
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

            ItemStack item = e.getCurrentItem();

            if(item == null) return;

            if(item.getType() != Material.AIR) {

                if(item.hasItemMeta()) {

                    String command = item.getItemMeta().getDisplayName().toLowerCase();
                    Player player = (Player) e.getWhoClicked();

                    switch(command) {
                        case "accept":
                            System.out.println("levelCreation.accept");
                            GameInventory gameInventory = new GameInventory(plugin);
                            player.openInventory(gameInventory.testRun(player.getOpenInventory().getTopInventory().getContents()));
                            e.setCancelled(true);
                            break;
                        case "dismiss":
                            System.out.println("levelCreation.dismiss");
                            e.setCancelled(true);
                            player.getInventory().clear();
                            player.closeInventory();
                            break;
                        case "randomize":
                            System.out.println("levelCreation.randomize");
                            GameInventory gi = new GameInventory(plugin);
                            RandomLevelCreator randomLevelCreator = new RandomLevelCreator(plugin);
                            //player.openInventory(gi.buildInventory(randomLevelCreator.createLevel(),-99));
                            player.getOpenInventory().getTopInventory().setContents(gi.getItemstack(randomLevelCreator.createLevel()));
                            e.setCancelled(true);
                            break;
                        case "add color...":
                            System.out.println("levelCreation.addColor");
                            ItemStack glass = e.getCurrentItem();
                            e.setCancelled(true);
                            e.setCursor(glass);
                            break;
                        case "barrier block":
                            System.out.println("levelCreation.barrier");
                            ItemStack barrier = e.getCurrentItem();
                            e.setCancelled(true);
                            e.setCursor(barrier);
                            break;
                        case "void":
                            System.out.println("levelCreation.void");
                            ItemStack empty = e.getCurrentItem();
                            empty.setAmount(9);
                            e.setCancelled(true);
                            e.setCursor(empty);
                            break;
                    }
                }
            } else if(item.hasItemMeta()){
                if(item.getItemMeta().hasLore()) {
                    if (item.getItemMeta().getLore().contains("BukkitFlow")) { // might crash on nullPointers
                        ItemStack cursorItem = e.getCursor();
                        if (e.getClickedInventory() != null && e.getClickedInventory().getTitle().equals(title)) {
                            e.getClickedInventory().setItem(e.getSlot(), cursorItem);
                            e.setCancelled(true);
                        }
                    }
                }
            }
        }
    }
}
