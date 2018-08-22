package me.TheFloatGoat.BukkitFlow.Handlers;

import me.TheFloatGoat.BukkitFlow.LevelCreation.RandomLevelCreator;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class CounterButtonHandler implements Listener{

    @EventHandler
    void onInventoryClick(InventoryClickEvent e) {

        if(! e.getInventory().getTitle().equals("BukkitFlow: Level creation")) return;
        ItemStack itemStack = e.getCurrentItem();
        if(itemStack==null) return;
        ItemMeta meta = itemStack.getItemMeta();
        if(meta==null) return;
        List lore = meta.getLore();
        if(lore == null) return;

        if (lore.contains("CounterButton")) {
            int cur =  itemStack.getAmount();
            if(e.getClick() == ClickType.LEFT) {
                cur = cur==16?16:cur+1;
                itemStack.setAmount(cur);
                RandomLevelCreator.colors = cur;
            }else if (e.getClick() == ClickType.RIGHT) {
                cur = cur==1?1:cur-1;
                itemStack.setAmount(cur);
                RandomLevelCreator.colors = cur;
            }
            e.setCancelled(true);
        }
    }
}
