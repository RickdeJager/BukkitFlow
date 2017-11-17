package me.TheFloatGoat.BukkitFlow;

import org.apache.commons.io.FileUtils;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.InputStream;

public class LevelImporter {

    Plugin plugin;

    LevelImporter(Plugin plugin) {
        this.plugin = plugin;
    }

    /**
     * Imports all levels from the .jar file
     * @return  Returns the number of imported levels
     */
    int ImportLevels() {

        int i = 0;

        while (true) {
            InputStream source = plugin.getResource("levels/"+i+".txt");
            if(source == null)break;

            try {
                File destination = new File(plugin.getDataFolder().getPath() + "/levels/"+i+".txt");
                FileUtils.copyInputStreamToFile(source, destination);
            } catch (Exception e) {
               break;
            }
            i++;
        }

        return i;
    }
}
