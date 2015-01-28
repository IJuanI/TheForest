package me.Juanco.Events;

import me.Juanco.Commands.FlyareaCommand;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.plugin.Plugin;

public class PlayerItemHeld implements Listener {

	private PlayerItemHeld() { }
	static PlayerItemHeld instance = new PlayerItemHeld();
	public static PlayerItemHeld getInstance() {
		return instance;
	}
	
	public void register(Plugin pl) {
		Bukkit.getPluginManager().registerEvents(this, pl);
	}
	
	@EventHandler
	public void onPlayerItemHeld(PlayerItemHeldEvent e) {
		Player p = e.getPlayer();
		if (FlyareaCommand.selecting.contains(p)) FlyareaCommand.cancel(p);
	}
}
