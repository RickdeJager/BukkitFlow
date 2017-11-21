package me.TheFloatGoat.BukkitFlow;

import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.io.FileWriter;

public class LevelSaver {

    Plugin plugin;

    LevelSaver(Plugin plugin) {
        this.plugin = plugin;
    }

    void saveFile(int id, ItemStack[] contents) {

        String levelString = ""+contents.length;

        for(ItemStack itemStack : contents) {

            switch (itemStack.getType()) {
                case STAINED_GLASS_PANE:
                    levelString+= itemStack.getDurability();
                    break;
                case IRON_FENCE:
                    levelString+=-2;
                    break;
                default:
                    levelString+=-1;
                    break;
            }

            levelString+="/";
        }

        try {
            FileWriter fileWriter = new FileWriter(plugin.getDataFolder().getPath() + "/levels/" + id + ".txt");
            fileWriter.write(levelString);
        }catch (Exception e) {
            System.out.println("Error while saving file");
        }

    }
}
