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
        getCommand("bukkitflow").setExecutor(new CommandHandler(this));


        //Check for first setup
        File dataFolder = getDataFolder();

        if(!dataFolder.exists()) {
            dataFolder.mkdir();
            System.out.println(prefix+"To setup the levels, type /bukkitflow levels help");


            //TODO create a scoreconfig.yml file
            File configFile = new File(getDataFolder(), "scorefile.yml");
            try {
                configFile.createNewFile();
            } catch (IOException e) {
            }

        }

    }

    @Override
    public void onDisable() {

    }
}
