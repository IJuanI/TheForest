package me.Juanco.helpers;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class BookItems {

	@SuppressWarnings("deprecation")
	public static ItemStack item(String path, FileConfiguration fc) {
		Material mat;
		String m = fc.getString(path + ".ID");
		try {
			mat = Material.getMaterial(Integer.parseInt(fc.getString(path + ".ID")));
		} catch(Exception e) {
			mat = Material.getMaterial(m);
		}
		int Amount = fc.getInt(path + ".Amount");
		int Data = fc.getInt(path + ".Data");
		String Name = fc.getString(path + ".Name");
		Name = ChatColor.translateAlternateColorCodes('&', Name);
		boolean b = true;
		if (fc.getString(path + ".Lore") == null) b = false;
		ItemStack item;
		if (m != "SKULL") item = new ItemStack(mat, Amount, (short) Data);
		else item = new ItemStack(397, Amount, (short) Data);
		ItemMeta im = item.getItemMeta();
		im.setDisplayName(Name);
		if (b) {
			List<String> original = fc.getStringList(path + ".Lore");
			List<String> lores = new ArrayList<String>();
			for (String lore : original) {
				lore = ChatColor.translateAlternateColorCodes('&', lore);
				lores.add(lore);
			}
			im.setLore(lores);
		}
		item.setItemMeta(im);
		return item;
	}
	
	public static int slot(String path, FileConfiguration fc) {
		return fc.getInt(path + ".Slot")-1;
	}
}
