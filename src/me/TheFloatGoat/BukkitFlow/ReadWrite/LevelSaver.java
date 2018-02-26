package me.TheFloatGoat.BukkitFlow.ReadWrite;

import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.FileWriter;

public class LevelSaver {

    Plugin plugin;

    public LevelSaver(Plugin plugin) {
        this.plugin = plugin;
    }

    public boolean saveFile(int id, ItemStack[] contents) {

        int size = contents.length;

        String levelString = size+"/";

        for(ItemStack itemStack : contents) {

            if(itemStack == null) {
                levelString += -1;
            } else {

                switch (itemStack.getType()) {
                    case STAINED_GLASS_PANE:
                        levelString += itemStack.getDurability();
                        break;
                    case IRON_FENCE:
                        levelString += -2;
                        break;
                    default:
                        levelString += -1;
                        break;
                }
            }

            levelString+="/";
        }

        System.out.println(levelString);

        try {
            FileWriter fileWriter = new FileWriter(plugin.getDataFolder().getPath() + "/levels/" + id + ".txt");
            fileWriter.write(levelString);
            fileWriter.close();
            return true;
        }catch (Exception e) {
            System.out.println("Error while saving file");
            return false;
        }

    }


    /**
     * Finds the first available id in the level folder
     * @return the first available id in the level folder
     */
    public int findNextID() {

        File levelFolder = new File(plugin.getDataFolder()+"/levels/");
        File[] files = levelFolder.listFiles();

        for(int i = 0; i > files.length; i++) {

            String idString = files[i].getName().replaceAll("^[0-9]", "");

            if(! idString.equals(i)) {

                return i;
            }
        }

        return files.length;

    }
}
