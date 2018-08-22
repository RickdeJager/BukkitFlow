package me.TheFloatGoat.BukkitFlow.LevelCreation;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class ToggleButton extends ItemStack{

    public ToggleButton(boolean defaultVal, String label) {
        this.setType(defaultVal?Material.REDSTONE_TORCH_ON:Material.STICK);
        RandomLevelCreator.randomizePaths = defaultVal;
        setup(label);
    }

    void setup(String label) {
        this.setAmount(1);
        ItemMeta meta = this.getItemMeta();
        meta.setDisplayName(label);
        List lore = new ArrayList();
        lore.add("ToggleButton");
        meta.setLore(lore);
        this.setItemMeta(meta);
    }
}
