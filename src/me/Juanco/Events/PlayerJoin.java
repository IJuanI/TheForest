package me.Juanco.Events;

import me.Juanco.Configs.ConfigPlayer;
import me.Juanco.forest.TheForest;
import me.Juanco.helpers.GiveItems;
import me.Juanco.helpers.OpenBag;
import me.Juanco.helpers.Pedometer;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

public class PlayerJoin implements Listener {

	private PlayerJoin() { }
	static PlayerJoin instance = new PlayerJoin();
	public static PlayerJoin getInstance() {
		return instance;
	}
	
	public void register(Plugin pl) {
		Bukkit.getPluginManager().registerEvents(this, pl);
	}
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		ConfigPlayer.getInstance().create(p, TheForest.plugin);
		ItemStack book = GiveItems.book();
		ItemStack bag = GiveItems.bag();
		if (p.getInventory().getItem(1) == null) p.getInventory().setItem(1, book);
		else p.getInventory().addItem(book);
		if (p.getInventory().getItem(8) == null) p.getInventory().setItem(8, bag);
		else p.getInventory().addItem(bag);
		p.updateInventory();
		Pedometer.load(p);
		OpenBag.load(p);
	}
}
