package me.Juanco.Events;

import me.Juanco.Commands.FlyareaCommand;
import me.Juanco.Configs.Config;
import me.Juanco.helpers.BookGUI;
import me.Juanco.helpers.BookItems;
import me.Juanco.helpers.GiveItems;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

public class InventoryClick implements Listener {

	static Config c = Config.getInstance();
	static FileConfiguration fc = c.get();
	static ItemStack i(String path) {
		return BookItems.item(path, fc);
	}
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
			if (e.getCurrentItem().equals(GiveItems.selector())) {
				e.setCancelled(true);
				e.setCursor(new ItemStack(Material.AIR));
				FlyareaCommand.cancel((Player)e.getWhoClicked());
				Player p = (Player) e.getWhoClicked();
				p.updateInventory();
				p.closeInventory();
			}
			Player p = (Player) e.getWhoClicked();
			String invn = e.getInventory().getName();
			ItemStack c = e.getCurrentItem();
			if (invn.equals(BookGUI.Name("Main"))) {
				e.setCancelled(true);
				if (c.equals(i("Basics"))) {
					p.sendMessage("Basics");
				}
			}
		}
	}
}
