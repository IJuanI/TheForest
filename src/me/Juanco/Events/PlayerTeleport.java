package me.Juanco.Events;

import me.Juanco.Commands.SpawnareaCommand;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.plugin.Plugin;

public class PlayerTeleport implements Listener {

	private PlayerTeleport() { }
	static PlayerTeleport instance = new PlayerTeleport();
	public static PlayerTeleport getInstance() {
		return instance;
	}
	
	public void register(Plugin pl) {
		Bukkit.getPluginManager().registerEvents(this, pl);
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onPlayerTeleport(PlayerTeleportEvent e) {
		if (SpawnareaCommand.isOnSpawn(e.getPlayer())) for (Player pl : Bukkit.getOnlinePlayers()) pl.hidePlayer(e.getPlayer());
	}
}
