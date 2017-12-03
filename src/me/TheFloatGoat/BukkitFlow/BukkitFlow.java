package me.TheFloatGoat.BukkitFlow;

import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

public class BukkitFlow extends JavaPlugin {

    String prefix = "[BukkitFlow] ";

    @Override
    public void onEnable() {
        //init the listeners
        this.getServer().getPluginManager().registerEvents(new DragHandler(),this);
        this.getServer().getPluginManager().registerEvents(new DropBlocker(),this);
        this.getServer().getPluginManager().registerEvents(new CloseInventoryHandler(), this);
        getCommand("bukkitflow").setExecutor(new CommandHandler(this));


        //Check for first setup
        File dataFolder = getDataFolder();

        if(!dataFolder.exists()) {
            dataFolder.mkdir();

            //create a scoreconfig.yml file
            File configFile = new File(getDataFolder(), "scorefile.yml");

            try {

                configFile.createNewFile();

                //Import the default levels
                LevelImporter levelImporter = new LevelImporter(this);
                int imported = levelImporter.ImportLevels();
                System.out.println(prefix + (imported==0?"No levels we're found":"Imported "+imported+" levels!"));

            } catch (IOException e) {

                System.out.println(prefix+"An error occurred while trying to write to disk");
            }

        }

    }

    @Override
    public void onDisable() {

    }
}
