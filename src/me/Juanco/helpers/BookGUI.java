package me.Juanco.helpers;

import me.Juanco.Configs.Config;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class BookGUI {

	static Config c = Config.getInstance();
	static FileConfiguration fc = c.get();
	
	public static void main(Player p) {
		int Size = fc.getInt("GUIs.Main.Size");
		Inventory inv = Bukkit.createInventory(null, Size, Name("Main"));
		for (int n = 0; n <= inv.getSize()-1; n++) {
			if (n <= 17) inv.setItem(n, BookItems.item("Red", fc));
			else if (n > 17 && n <= 35) inv.setItem(n, BookItems.item("Green", fc));
			else inv.setItem(n, BookItems.item("Gold", fc));
		}
		
		String items = "Basics, Fire, Shelters, Storage, Custom Building, Furniture, Traps, Food, Boats, Effigies, Herbal Medicines, Food2, Caving, "
				+ "DIY Crafting Guide, Myths and Dealing With Fear, Notes";
		for (String iname : items.split(", ")) {
			ItemStack i = BookItems.item(iname, fc);
			int n = BookItems.slot(iname, fc);
			inv.setItem(n, i);
		}
		
		p.closeInventory();
		p.openInventory(inv);
	}
	
	public static String Name(String Gui) {
		return ChatColor.translateAlternateColorCodes('&', fc.getString("GUIs." + Gui + ".Name"));
	}
}
