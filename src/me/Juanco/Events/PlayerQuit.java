package me.Juanco.Events;

import me.Juanco.helpers.OpenBag;
import me.Juanco.helpers.Pedometer;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.Plugin;

public class PlayerQuit implements Listener {

	private PlayerQuit() { }
	static PlayerQuit instance = new PlayerQuit();
	public static PlayerQuit getInstance() {
		return instance;
	}
	
	public void register(Plugin pl) {
		Bukkit.getPluginManager().registerEvents(this, pl);
	}
	
	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent e) {
		Player p = e.getPlayer();
		Pedometer.save(p);
		OpenBag.save(p);
	}
}
