package me.Juanco.Events;

import me.Juanco.Commands.FlyareaCommand;
import me.Juanco.helpers.GiveItems;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

public class InventoryClick implements Listener {

	private InventoryClick() { }
	static InventoryClick instance = new InventoryClick();
	public static InventoryClick getInstance() {
		return instance;
	}
	
	public void register(Plugin pl) {
		Bukkit.getPluginManager().registerEvents(this, pl);
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onInventoryClick(InventoryClickEvent e) {
		if (e.getCurrentItem() != null) {
			if (e.getCurrentItem().equals(GiveItems.flyArea())) {
				e.setCancelled(true);
				e.setCursor(new ItemStack(Material.AIR));
				FlyareaCommand.cancel((Player)e.getWhoClicked());
			}
		}
	}
}
