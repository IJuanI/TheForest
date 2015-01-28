package me.Juanco.helpers;

import java.io.File;
import java.util.HashMap;

import me.Juanco.Configs.ConfigPlayer;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class OpenBag {

	static ConfigPlayer cp = ConfigPlayer.getInstance();
	static HashMap<Player, Inventory> invlist= new HashMap<Player, Inventory>();
	public static void open(Player p) {
		Inventory inv;
		if (invlist.containsKey(p)) inv = invlist.get(p);
		else {
			String name = ChatColor.translateAlternateColorCodes('&', "&a&lMochila personal");
			int size = 3*9;
			inv = Bukkit.createInventory(null, size, name);
			invlist.put(p, inv);
		}
		
		p.closeInventory();
		p.openInventory(inv);
	}
	
	public static void save() {
		for (Player p : invlist.keySet()) {
			cp.load(p);
			Inventory inv = invlist.get(p);
			int n = -1;
			for (ItemStack i : inv.getContents()) {
				n++;
				if (i != null) cp.get().set("Temp.Inv." + n, i);
			}
			cp.save();
		}
	}
	
	public static void save(Player p) {
		cp.load(p);
		Inventory inv = invlist.get(p);
		int n = -1;
		for (ItemStack i : inv.getContents()) {
			n++;
			if (i != null) cp.get().set("Temp.Inv." + n, i);
		}
		cp.save();
	}
	
	@SuppressWarnings("deprecation")
	public static void load() {
		for (File f : cp.folder().listFiles()) {
			for (Player p : Bukkit.getOnlinePlayers()) {
				cp.load(p);
				if (cp.getfile().equals(f)) {
					if (cp.get().contains("Temp.Inv")) {
						String name = ChatColor.translateAlternateColorCodes('&', "&a&lMochila personal");
						int size = 3*9;
						Inventory inv = Bukkit.createInventory(null, size, name);
						for (String s : cp.get().getConfigurationSection("Temp.Inv").getKeys(false)) {
							int slot = Integer.valueOf(Integer.parseInt(s));
							ItemStack i = cp.get().getItemStack("Temp.Inv." + s);
							inv.setItem(slot, i);
						}
						invlist.put(p, inv);
						cp.get().set("Temp.Inv", null);
						cp.save();
					}
				}
			}
		}
	}
	
	public static void load(Player p) {
		cp.load(p);
		if (cp.get().contains("Temp.Inv")) {
			String name = ChatColor.translateAlternateColorCodes('&', "&a&lMochila personal");
			int size = 3*9;
			Inventory inv = Bukkit.createInventory(null, size, name);
			for (String s : cp.get().getConfigurationSection("Temp.Inv").getKeys(false)) {
				int slot = Integer.valueOf(Integer.parseInt(s));
				ItemStack i = cp.get().getItemStack("Temp.Inv." + s);
				inv.setItem(slot, i);
			}
			invlist.put(p, inv);
			cp.get().set("Temp.Inv", null);
			cp.save();
		}
	}
}
