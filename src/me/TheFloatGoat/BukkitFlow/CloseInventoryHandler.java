package me.TheFloatGoat.BukkitFlow;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;

public class CloseInventoryHandler implements Listener {

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent e) {

        if(e.getInventory().getTitle().equals("BukkitFlow: Level creation")) {

            e.getPlayer().getInventory().setContents(new ItemStack[36]);    //Clear the inventory
        }
    }


}
