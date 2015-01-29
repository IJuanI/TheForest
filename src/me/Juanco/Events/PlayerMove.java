package me.Juanco.Events;

import me.Juanco.Configs.Config;
import me.Juanco.helpers.GiveItems;
import me.Juanco.helpers.Pedometer;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.Plugin;

public class PlayerMove implements Listener {

	Config c = Config.getInstance();
	private PlayerMove() { }
	static PlayerMove instance = new PlayerMove();
	public static PlayerMove getInstance() {
		return instance;
	}
	
	public void register(Plugin pl) {
		Bukkit.getPluginManager().registerEvents(this, pl);
	}
	
	public Location loadloc(String path, FileConfiguration fc) {
		if (fc.contains(path)) {
			World w = Bukkit.getWorld(fc.getString(path + ".world"));
			double x = fc.getDouble(path + ".x");
			double y = fc.getDouble(path + ".y");
			double z = fc.getDouble(path + ".z");
			float yaw = (float) fc.getDouble(path + ".yaw");
			float pitch = (float) fc.getDouble(path + ".pitch");
			return new Location(w, x, y, z, yaw, pitch);
		}
		return null;
	}
	
	@EventHandler
	public void onPlayerMove(PlayerMoveEvent e) {
		Location loc1 = e.getFrom();
		Location loc2 = e.getTo();
		if (loc1.getBlockX() != loc2.getBlockX() || loc1.getBlockZ() != loc2.getBlockZ()) {
			Player p = e.getPlayer();
			if (!p.isFlying() && p.getInventory().contains(GiveItems.pedometer())) Pedometer.update(p);
			Location fly1 = loadloc("Fly Area.p1", c.get());
			Location fly2 = loadloc("Fly Area.p2", c.get());
			if (fly1 != null && fly2 != null) {
				boolean x = false;
				boolean z = false;
				boolean y = false;
				boolean area = true;
				if (fly1.getBlockX() == fly2.getBlockX()) x = true;
				if (fly1.getBlockZ() == fly2.getBlockZ()) z = true;
				if (fly1.getBlockY() < fly2.getBlockY()) y = true;
				if (x != z) {
					if (y) {
						if (loc2.getBlockY() > fly2.getBlockY() || loc2.getBlockY() < fly1.getBlockY()) area = false;
					} else if (loc2.getBlockY() < fly2.getBlockY() || loc2.getBlockY() > fly1.getBlockY()) area = false;
					if (x) {
						boolean z1 = false;
						if (fly1.getBlockZ() < fly2.getBlockZ()) z1 = true;
						if (z1) {
							if (loc2.getBlockZ() > fly2.getBlockZ() || loc2.getBlockZ() < fly1.getBlockZ()) area = false;
						} else if (loc2.getBlockZ() < fly2.getBlockZ() || loc2.getBlockZ() > fly1.getBlockZ()) area = false;
					} else {
						boolean x1 = false;
						if (fly1.getBlockX() < fly2.getBlockX()) x1 = true;
						if (x1) {
							if (loc2.getBlockX() > fly2.getBlockX() || loc2.getBlockX() < fly1.getBlockX()) area = false;
						} else if (loc2.getBlockX() < fly2.getBlockX() || loc2.getBlockX() > fly1.getBlockX()) area = false;
					}
					if (area) {
						if (x) {
							if (c.get().getString("Fly Area.Direction").equalsIgnoreCase("+")) {
								if (loc2.getBlockX() == fly1.getBlockX()+1) p.setAllowFlight(true);
								if (loc2.getBlockX() == fly1.getBlockX()) p.setAllowFlight(false);
							} else {
								if (loc2.getBlockX() == fly1.getBlockX()-1) p.setAllowFlight(true);
								if (loc2.getBlockX() == fly1.getBlockX()) p.setAllowFlight(false);
							}
						} else {
							if (c.get().getString("Fly Area.Direction").equalsIgnoreCase("+")) {
								if (loc2.getBlockZ() == fly1.getBlockZ()+1) p.setAllowFlight(true);
								if (loc2.getBlockZ() == fly1.getBlockZ()) p.setAllowFlight(false);
							} else {
								if (loc2.getBlockZ() == fly1.getBlockZ()-1) p.setAllowFlight(true);
								if (loc2.getBlockZ() == fly1.getBlockZ()) p.setAllowFlight(false);
							}
						}
					}
				}
			}
		}
	}
}
