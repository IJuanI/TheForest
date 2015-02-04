package me.Juanco.forest;

import me.Juanco.Commands.ChestCommand;
import me.Juanco.Commands.FlyareaCommand;
import me.Juanco.Commands.ModifyCommand;
import me.Juanco.Commands.SeeareaCommand;
import me.Juanco.Commands.SpawnareaCommand;
import me.Juanco.Commands.Test;
import me.Juanco.helpers.GiveItems;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandsHandler {

	public static void onCommand(CommandSender sender, Command cmd, String CommandLabel, String[] args) {
		if (!(sender instanceof Player)) sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cSolo Players."));
		else {
			Player p = (Player) sender;
			if (cmd.getName().equalsIgnoreCase("tf")) {
				if (args.length == 0) p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&aAun no hice la ayuda :("));
				else {
					if (args[0].equalsIgnoreCase("chest")) {
						ChestCommand.chest(p, args);
						return;
					} else if (args[0].equalsIgnoreCase("pedometer")) {
						p.getInventory().addItem(GiveItems.pedometer());
						return;
					} else if (args[0].equalsIgnoreCase("flyarea")) {
						FlyareaCommand.flyarea(p, args);
						return;
					} else if (args[0].equalsIgnoreCase("seearea")) {
						SeeareaCommand.SeeArea(p, args);
						return;
					} else if (args[0].equalsIgnoreCase("spawnarea")) {
						SpawnareaCommand.SpawnArea(p, args);
						return;
					} else if (args[0].equalsIgnoreCase("modify")) {
						ModifyCommand.modify(p, args);
						return;
					} else if (args[0].equalsIgnoreCase("test")) {
						Test.test(p, args);
						return;
					}
					p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cComando desconocido"));
				}
				return;
			}
			p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cComando desconocido"));
		}
	}
}
