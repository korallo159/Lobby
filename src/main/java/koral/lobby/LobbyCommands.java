package koral.lobby;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class LobbyCommands implements CommandExecutor {
    Lobby plugin;
    public LobbyCommands(Lobby plugin) {
        this.plugin = plugin;
    }
    public static boolean freeze = false;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player){
            if(command.getName().equals("lobby") && args.length > 0)
            switch(args[0]){
                case "reload":
                    sender.sendMessage(ChatColor.DARK_RED + "Config przeładowany");
                plugin.reloadConfig();
                break;
                case "freeze":
                    if(!freeze){
                        freeze = true;
                        sender.sendMessage(ChatColor.RED+ "Czat został zfreezowany");
                    }
                    else {
                        freeze = false;
                        sender.sendMessage(ChatColor.GREEN + "Czat został odfreezowany");
                    }

            }

        }
        else sender.sendMessage("Tylko grac moze uzyc tej komendy");
    return false;
    }

}
