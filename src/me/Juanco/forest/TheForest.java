package me.Juanco.forest;

import me.Juanco.Configs.Config;
import me.Juanco.Configs.ConfigPlayer;
import me.Juanco.helpers.OpenBag;
import me.Juanco.helpers.Pedometer;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
@SuppressWarnings("deprecation")
public class TheForest extends JavaPlugin {

	public static TheForest plugin;
	
	public void onEnable() {
		plugin = this;
		Config.getInstance().setup(this);
		Defaults.config();
		EventsHandler.register(this);
		for (Player p : Bukkit.getOnlinePlayers()) {
			ConfigPlayer.getInstance().load(p);
			if (!ConfigPlayer.getInstance().getfile().exists()) {
				ConfigPlayer.getInstance().create(p, this);
			}
		}
		OpenBag.load();
		for (Player p : Bukkit.getOnlinePlayers()) Pedometer.load(p);
		for (Player p : Pedometer.displayed) Pedometer.display(p);
	}
	
	public void onDisable() {
		plugin = null;
		OpenBag.save();
		for (Player p : Bukkit.getOnlinePlayers()) Pedometer.save(p);
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String CommandLabel, String[] args) {
		CommandsHandler.onCommand(sender, cmd, CommandLabel, args);
		return true;
	}
}
