package me.TheFloatGoat.BukkitFlow.ReadWrite;

import org.bukkit.plugin.Plugin;

import java.io.File;
import java.util.Scanner;

public class LevelLoader {

    Plugin plugin;

    public LevelLoader(Plugin plugin) {
        this.plugin = plugin;
    }

    /**
     * This method will load the level corresponding to the levelID
     * @param levelID   levelID of the level to be loaded.
     * @return  returns an array of ints, corresponding to color values to be set later. Does not check for negative numbers!
     */
    public int[] load(int levelID) {

        int[] itemIDs;

        System.out.println("Loading "+plugin.getDataFolder()+"/levels/"+levelID+".txt");
        try {

            Scanner sc = new Scanner(new File(plugin.getDataFolder()+"/levels/"+levelID+".txt"));
            sc.useDelimiter("/");
            int size = sc.nextInt();        //First int will be size
            if (size % 9 != 0) return null; //Inventory size must be a multiple of 9
            itemIDs = new int[size];

            for (int i = 0; i < size; i++) {

                itemIDs[i] = sc.nextInt();

            }

            return itemIDs;

        }catch (Exception e) {

            e.printStackTrace();
            return null;

        }
    }
}
