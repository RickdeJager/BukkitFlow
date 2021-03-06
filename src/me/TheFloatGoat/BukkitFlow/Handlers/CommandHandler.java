package me.TheFloatGoat.BukkitFlow.Handlers;

import me.TheFloatGoat.BukkitFlow.Inventory.GameInventory;
import me.TheFloatGoat.BukkitFlow.Inventory.LevelCreationInventory;
import me.TheFloatGoat.BukkitFlow.ReadWrite.LevelImporter;
import me.TheFloatGoat.BukkitFlow.Inventory.ScoreKeeper;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class CommandHandler implements CommandExecutor {

    String prefix = "[BukkitFlow] ";
    Plugin plugin;

    public CommandHandler(Plugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if(commandSender instanceof Player) {
            Player player = (Player) commandSender;
            if(args.length > 0) {
                switch (args[0]) {
                    case "levels":
                        if(player.hasPermission("bukkitflow.create")) {
                            if (args.length > 1) {
                                switch (args[1]) {
                                    case "help":
                                        commandSender.sendMessage(prefix + "use '/bukkitflow levels import' to import or update from the included levels. (This overwrites the first levels)");
                                        commandSender.sendMessage(prefix + "use '/bukkitflow levels create' to automatic create new random levels");
                                        break;
                                    case "import":
                                        LevelImporter levelImporter = new LevelImporter(plugin);
                                        int imported = levelImporter.ImportLevels();
                                        commandSender.sendMessage(prefix + (imported == 0 ? "No levels we're found" : "Imported " + imported + " levels!"));
                                        break;
                                    case "create":
                                        LevelCreationInventory levelCreationInventory = new LevelCreationInventory(plugin, player);
                                        levelCreationInventory.createUI();
                                        break;
                                }
                            } else {
                                commandSender.sendMessage(prefix + "/bukkitflow levels help");
                            }
                        }else {
                            player.sendMessage(prefix+"You don't have permission to use this command!");
                        }

                        break;

                    case "score":
                        if(player.hasPermission("bukkitflow.score")) {
                            ScoreKeeper scoreKeeper = new ScoreKeeper(plugin);
                            int score = scoreKeeper.getScore(player);

                            player.sendMessage(prefix + (score >= 0 ? "Current level: " + score : "You haven't completed a level yet."));

                            break;
                        }else {
                            player.sendMessage(prefix+"You don't have permission to use this command!");
                        }

                    default:    //args[0] was a number, or something else
                        if(player.hasPermission("bukkitflow.play")) {
                            ScoreKeeper scoreKeeper = new ScoreKeeper(plugin);
                            int id = 0;
                            int score = scoreKeeper.getScore(player);
                            try {
                                id = Integer.parseInt(args[0]);

                                if (score + 1 >= id) {
                                    GameInventory gi = new GameInventory(plugin);
                                    player.openInventory(gi.fromID(id));
                                } else {
                                    player.sendMessage(prefix + "You haven't unlocked that level yet.");
                                }
                            } catch (Exception e) {
                                player.sendMessage(prefix + "Level ID should be a number.");
                            }
                        }else {
                            player.sendMessage(prefix + "You don't have permission to use this command!");
                        }
                }
            }else {
                player.sendMessage(prefix+"To play a level, simply type '/bukkitflow [ID]'");
                player.sendMessage(prefix+"To see your current level, type '/bukkitflow score'");
                player.sendMessage(prefix+"To create new levels, see '/bukkitflow levels help'");
            }

        }else {
            commandSender.sendMessage("Sorry, you can't play BukkitFlow from the console.");
        }

        return true;
    }
}
