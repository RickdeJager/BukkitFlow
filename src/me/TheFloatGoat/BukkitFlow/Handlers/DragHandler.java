package me.TheFloatGoat.BukkitFlow.Handlers;

import me.TheFloatGoat.BukkitFlow.Helpers.InventoryHelpers;
import me.TheFloatGoat.BukkitFlow.ReadWrite.LevelSaver;
import me.TheFloatGoat.BukkitFlow.Checkers.PathChecker;
import me.TheFloatGoat.BukkitFlow.Inventory.ScoreKeeper;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.Set;

public class DragHandler implements Listener {

    Plugin plugin = Bukkit.getPluginManager().getPlugin("BukkitFlow");
    String prefix = "[BukkitFlow] ";
    InventoryHelpers IH = new InventoryHelpers();

    @SuppressWarnings("depreciation")
    @EventHandler
    public void onInventoryDrag(InventoryDragEvent e) {


        if(e.getInventory().getName().contains("BukkitFlow")) {

            PathChecker pathChecker = new PathChecker();
            boolean valid = false;
            boolean testRun = e.getInventory().getName().toLowerCase().contains("test");

            Set<Integer> slots = e.getRawSlots();

            if(e.getInventorySlots().equals(slots)) {

                ItemStack pathItem = e.getOldCursor();
                Short colorID = pathItem.getDurability();
                valid = pathChecker.CheckInventory(e.getInventory(), slots, colorID);

            }else {

                valid = false;
            }

            if(valid) {

                if(pathChecker.win(e.getInventory(), e.getRawSlots())) {
                    if(testRun){

                     LevelSaver levelSaver = new LevelSaver(plugin);
                     int id = levelSaver.findNextID();
                     levelSaver.saveFile(id, e.getInventory().getContents());
                     e.getWhoClicked().sendMessage("Saving level as \""+id+".txt\"...");
                    } else {

                        HumanEntity humanEntity = e.getWhoClicked();
                        humanEntity.closeInventory();
                        humanEntity.sendMessage(prefix + "Level completed!");

                        ScoreKeeper scoreKeeper = new ScoreKeeper(plugin);
                        String scoreString = e.getInventory().getTitle().replaceAll("[^0-9]", "");
                        try {
                            int score = Integer.parseInt(scoreString);
                            scoreKeeper.newScore((Player) humanEntity, score);
                        } catch (NumberFormatException exception) {
                            System.out.println(prefix + "Failed while trying to save level progress: " + scoreString + " for " + humanEntity.getName() + " ( " + humanEntity.getUniqueId() + ")");
                        }
                    }

                }

            }else {
                e.setCancelled(true);
            }

                e.setCancelled(!valid);
                Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
                    @Override
                    public void run() {
                        e.getWhoClicked().setItemOnCursor(null);
                    }
                },1);
        }
    }


    @EventHandler
    public void onInventoryClickEvent(InventoryClickEvent e) {

        HumanEntity humanEntity = e.getWhoClicked();

        if(humanEntity.getOpenInventory().getTopInventory().getName().matches("BukkitFlow: level (\\d|testing)")) {

            //Disable the player inventory if the game inventory is open
            if (!humanEntity.getOpenInventory().getTopInventory().equals(e.getClickedInventory())) {
                e.setCancelled(true);
                e.setCursor(null);  //The cursor shouldn't contain player items, since interaction with their own inventory is disabled
                return;
            }

            if (e.getClickedInventory().equals(humanEntity.getOpenInventory().getTopInventory())) {

                if (!e.isLeftClick()) {

                    e.setCancelled(true);   //We're having none of that
                    e.setCursor(null);

                } else if (e.getCursor().getType() == Material.AIR && e.getCurrentItem().getType() == Material.STAINED_GLASS_PANE) {

                    IH.removePath(e.getClickedInventory(), e.getCurrentItem().getDurability());
                    e.setCursor(IH.convertToGlassPanes(e.getCurrentItem()));
                }

                e.setCancelled(true);   //Cancels the old event, but will set the cursor up with the new item stack if needed
            }
        }
    }
}
