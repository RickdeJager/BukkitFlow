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
                            }
                        }else {
                            commandSender.sendMessage(prefix+"/bukkitflow levels help");
                        }

                        break;

                    default:    //args[0] was a number, or something else
                    Player player = (Player) commandSender;
                    int id = 0;
                    try {
                        id = Integer.parseInt(args[0]);
                    } catch (Exception e) {
                        player.sendMessage("Level ID should be a number");
                    }
                    GameInventory gi = new GameInventory(id, player);

                }
            }

        }else {
            commandSender.sendMessage("Sorry, you can't play BukkitFlow from the console.");
        }

        return true;
    }
}
