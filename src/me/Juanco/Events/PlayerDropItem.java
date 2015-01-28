package me.Juanco.Events;

import java.util.Arrays;
import java.util.List;

import me.Juanco.helpers.GiveItems;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

public class PlayerDropItem implements Listener {

	private PlayerDropItem() { }
	static PlayerDropItem instance = new PlayerDropItem();
	public static PlayerDropItem getInstance() {
		return instance;
	}
	
	public void register(Plugin pl) {
		Bukkit.getPluginManager().registerEvents(this, pl);
	}
	
	@EventHandler
	public void onPlayerDropItem(PlayerDropItemEvent e) {
		ItemStack c = e.getItemDrop().getItemStack();
		List<ItemStack> list = Arrays.asList(GiveItems.book(), GiveItems.bag(), GiveItems.pedometer(), GiveItems.flyArea());
		for (ItemStack i : list) if (c.equals(i)) e.setCancelled(true);
	}
}
