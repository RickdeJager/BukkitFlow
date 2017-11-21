package me.TheFloatGoat.BukkitFlow;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class CommandHandler implements CommandExecutor {

    String prefix = "[BukkitFlow] ";
    Plugin plugin;

    CommandHandler(Plugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if(commandSender instanceof Player) {
            Player player = (Player) commandSender;
            if(args.length > 0) {
                switch (args[0]) {
                    case "levels":

                        if(args.length > 1) {
                            switch (args[1]) {
                                case "help":
                                    commandSender.sendMessage(prefix+"use '/bukkitflow levels import' to import or update from the included levels.");
                                    commandSender.sendMessage(prefix+"use '/bukkitflow levels create' to automatic create new random levels");
                                    break;
                                case "import":
                                    LevelImporter levelImporter = new LevelImporter(plugin);
                                    int imported = levelImporter.ImportLevels();
                                    commandSender.sendMessage(prefix + (imported==0?"No levels we're found":"Imported "+imported+" levels!"));
                                    break;
                                case "create":
                                    //TODO init level creation ui
                                    LevelCreationInventory levelCreationInventory = new LevelCreationInventory(plugin, player);
                                    levelCreationInventory.createUI();
                                    break;
                            }
                        }else {
                            commandSender.sendMessage(prefix+"/bukkitflow levels help");
                        }

                        break;

                    case "score":

                        ScoreHandler scoreHandler = new ScoreHandler(plugin);
                        int score = scoreHandler.getScore(player);

                        player.sendMessage(prefix+ (score>=0?"Current level: "+score:"You haven't completed a level yet."));

                        break;

                    default:    //args[0] was a number, or something else
                        scoreHandler = new ScoreHandler(plugin);
                        int id = 0;
                        score = scoreHandler.getScore(player);
                        try {
                            id = Integer.parseInt(args[0]);

                            if(score + 1 >= id) {
                                GameInventory gi = new GameInventory(id, player);
                            }else {
                                player.sendMessage(prefix+"You haven't unlocked that level yet.");
                            }
                        } catch (Exception e) {
                            player.sendMessage(prefix+"Level ID should be a number.");
                        }

                }
            }

        }else {
            commandSender.sendMessage("Sorry, you can't play BukkitFlow from the console.");
        }

        return true;
    }
}
