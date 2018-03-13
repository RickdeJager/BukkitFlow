package me.TheFloatGoat.BukkitFlow.Helpers;

import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class InventoryHelpers {

    /**
     * This function removes a path with either a specific color, or just all panes if ID = -1
     * @param inventory the inventory to clear the path from
     * @param ID the ID of the color whose path should be removed, or -1 for all paths
     */
    public void removePath(Inventory inventory, int ID) {

        ItemStack[] contents = inventory.getContents();

        for(int i = 0; i < contents.length; i++) {

            if(contents[i] != null && contents[i].getType() == Material.STAINED_GLASS &&  (contents[i].getDurability() == ID || ID == -1)) {
                contents[i] = new ItemStack(Material.AIR, 1);
            }
        }

        inventory.setContents(contents);
    }

    /**
     * This function is used to quickly convert a glass pane to a full stack of glass block items of the same color
     * @param input an Itemstack
     * @return Returns an itemstack of 64 stained glass of the same color/durability as the input
     */
    public ItemStack convertToGlassPanes(ItemStack input) {
        ItemStack output = new ItemStack(Material.STAINED_GLASS, 64);

        Short colorID = input.getDurability();
        output.setDurability(colorID);

        ItemMeta meta = output.getItemMeta();
        ArrayList<String> loreList = new ArrayList<>();
        loreList.add("BukkitFlow");
        meta.setLore(loreList);
        output.setItemMeta(meta);

        return output;
    }

    public ItemStack genItem(boolean anchor, int ID) {

        Material material = anchor?Material.STAINED_GLASS_PANE:Material.STAINED_GLASS;
        String label = anchor?"Anchor Point":"Path";

        if(ID >= 0) {

            ItemStack item = new ItemStack(material, 1, (short) ID);
            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName(label);
            ArrayList<String> loreList = new ArrayList<>();
            loreList.add("BukkitFlow");
            meta.setLore(loreList);
            item.setItemMeta(meta);
            return item;

        }else if (ID == -2) {

            //Barrier block
            ItemStack item = new ItemStack(Material.IRON_FENCE, 1);
            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName("Barrier block");
            item.setItemMeta(meta);
            return item;

        }

        return null;
    }


}
