package me.TheFloatGoat.BukkitFlow.Handlers;

import me.TheFloatGoat.BukkitFlow.Helpers.InventoryHelpers;
import me.TheFloatGoat.BukkitFlow.Inventory.GameInventory;
import me.TheFloatGoat.BukkitFlow.LevelCreation.RandomLevelCreator;
import me.TheFloatGoat.BukkitFlow.ReadWrite.LevelSaver;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

import java.util.List;

public class LevelCreationHandlers implements Listener {


    Plugin plugin = Bukkit.getPluginManager().getPlugin("BukkitFlow");
    InventoryHelpers IH = new InventoryHelpers();


    @EventHandler
    void onInventoryClick(InventoryClickEvent e) {

        String title = e.getInventory().getTitle();

        if(title.equals("BukkitFlow: Level creation")) {

            ItemStack item = e.getCurrentItem();

            if(item == null) return;

            ItemMeta meta = item.getItemMeta();
            List lore = meta==null?null:meta.getLore();

            if(item.getType() != Material.AIR) {

                if(meta !=null && meta.hasDisplayName()) {

                    String command = meta.getDisplayName().toLowerCase();
                    Player player = (Player) e.getWhoClicked();

                    switch(command) {
                        case "test and save":
                            GameInventory gameInventory = new GameInventory(plugin);
                            Inventory inventory = player.getOpenInventory().getTopInventory();
                            IH.removePath(inventory, -1);
                            player.openInventory(gameInventory.testRun(inventory.getContents()));
                            e.setCancelled(true);
                            break;
                        case "save":
                            inventory = player.getOpenInventory().getTopInventory();
                            LevelSaver levelSaver = new LevelSaver(plugin);
                            int id = levelSaver.findNextID();
                            levelSaver.saveFile(id, e.getInventory().getContents());
                            e.getWhoClicked().sendMessage("Saving level as \""+id+".txt\"...");
                            e.setCancelled(true);
                            break;
                        case "randomized paths":
                            boolean on = item.getType() == Material.REDSTONE_TORCH_ON;
                            item.setType(on?Material.STICK:Material.REDSTONE_TORCH_ON);
                            RandomLevelCreator.randomizePaths = !on;
                            e.setCancelled(true);
                            break;
                        case "dismiss":
                            e.setCancelled(true);
                            player.getInventory().clear();
                            player.closeInventory();
                            break;
                        case "randomize":
                            GameInventory gi = new GameInventory(plugin);
                            RandomLevelCreator randomLevelCreator = new RandomLevelCreator();
                            player.getOpenInventory().getTopInventory().setContents(randomLevelCreator.createLevel());
                            e.setCancelled(true);
                            break;
                        case "add color...":
                            ItemStack glass = IH.genItem(true, e.getCurrentItem().getDurability());
                            e.setCancelled(true);
                            e.setCursor(glass);
                            break;
                        case "barrier block":
                            ItemStack barrier = IH.genItem(false, -2);
                            e.setCancelled(true);
                            e.setCursor(barrier);
                            break;
                        case "void":
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
