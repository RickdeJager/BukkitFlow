package me.TheFloatGoat.BukkitFlow.LevelCreation;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class CounterButton extends ItemStack{

    public CounterButton(Material item, int start, String label) {
        this.setType(item);
        setup(start, label);
    }

    public CounterButton(int colorID, int start, String label) {
        this.setType(Material.WOOL);
        this.setDurability((short) colorID);
        setup(start, label);
    }

    void setup(int start,  String label) {
        this.setAmount(start);
        RandomLevelCreator.colors = start;
        ItemMeta meta = this.getItemMeta();
        meta.setDisplayName(label);
        List lore = new ArrayList();
        lore.add("CounterButton");
        meta.setLore(lore);
        this.setItemMeta(meta);
    }
}
