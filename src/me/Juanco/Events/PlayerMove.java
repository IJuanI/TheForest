package me.Juanco.Events;

import me.Juanco.helpers.GiveItems;
import me.Juanco.helpers.Pedometer;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.Plugin;

public class PlayerMove implements Listener {

	private PlayerMove() { }
	static PlayerMove instance = new PlayerMove();
	public static PlayerMove getInstance() {
		return instance;
	}
	
	public void register(Plugin pl) {
		Bukkit.getPluginManager().registerEvents(this, pl);
	}
	
	@EventHandler
	public void onPlayerMove(PlayerMoveEvent e) {
		Location loc1 = e.getFrom();
		Location loc2 = e.getTo();
		if (loc1.getBlockX() != loc2.getBlockX() || loc1.getBlockZ() != loc2.getBlockZ()) {
			Player p = e.getPlayer();
			if (!p.isFlying() && p.getInventory().contains(GiveItems.pedometer())) Pedometer.update(p);
		}
	}
}
