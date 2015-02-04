package me.Juanco.Events;

import me.Juanco.Commands.SpawnareaCommand;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.plugin.Plugin;

public class PlayerChangedWorld implements Listener {

	private PlayerChangedWorld() { }
	static PlayerChangedWorld instance = new PlayerChangedWorld();
	public static PlayerChangedWorld getInstance() {
		return instance;
	}
	
	public void register(Plugin pl) {
		Bukkit.getPluginManager().registerEvents(this, pl);
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onPlayerChangedWorld(PlayerChangedWorldEvent e) {
		if (SpawnareaCommand.isOnSpawn(e.getPlayer())) for (Player pl : Bukkit.getOnlinePlayers()) pl.hidePlayer(e.getPlayer());
	}
}
