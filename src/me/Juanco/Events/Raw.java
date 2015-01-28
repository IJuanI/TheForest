package me.Juanco.Events;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

public class Raw implements Listener {

	private Raw() { }
	static Raw instance = new Raw();
	public static Raw getInstance() {
		return instance;
	}
	
	public void register(Plugin pl) {
		Bukkit.getPluginManager().registerEvents(this, pl);
	}
}
