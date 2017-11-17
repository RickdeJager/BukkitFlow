package me.TheFloatGoat.BukkitFlow;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;

public class ScoreHandler {

    Plugin plugin;

    ScoreHandler(Plugin plugin) {
        this.plugin = plugin;
    }

    int getScore(Player player) {
        int score = -1;

        File scoreFile = new File(plugin.getDataFolder(), "scorefile.yml");
        FileConfiguration scoreConfig = YamlConfiguration.loadConfiguration(scoreFile);

        String uuid = player.getUniqueId().toString();

        if (scoreConfig.contains(uuid)) {
            try {
                score = scoreConfig.getInt(uuid);
            }catch (Exception e) {
                //Failed to load score, let's keep it at -1 for now
            }
        }

        return score;
    }


    void newScore(Player player, int score) {

        //Load a yml file

        File scoreFile = new File(plugin.getDataFolder(), "scorefile.yml");
        FileConfiguration scoreConfig = YamlConfiguration.loadConfiguration(scoreFile);


        //Find player by UUID, if they're not in the yml yet, add them with the new score
        //Otherwise, check if the stored score is lower then the new score, if so: store the new score

        String uuid = player.getUniqueId().toString();

        if(! scoreConfig.contains(uuid)) {

            scoreConfig.set(uuid, score);

            try {
                scoreConfig.save(scoreFile);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }else {

            int oldScore = scoreConfig.getInt(uuid);

            if(oldScore < score) {

                scoreConfig.set(uuid, score);

                try {
                    scoreConfig.save(scoreFile);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
