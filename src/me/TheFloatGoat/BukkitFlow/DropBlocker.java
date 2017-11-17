package me.TheFloatGoat.BukkitFlow;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class DropBlocker implements Listener {

    @EventHandler
    public void onPlayerDropItem(PlayerDropItemEvent e) {

        ItemStack item = e.getItemDrop().getItemStack();

        if(item.hasItemMeta()) {

            ItemMeta meta = item.getItemMeta();

            if(meta.hasLore()) {

                if(meta.getLore().get(0).contains("BukkitFlow")) {

                    e.getItemDrop().setItemStack(null);
                }
            }
        }
    }
}
