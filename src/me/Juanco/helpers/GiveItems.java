package me.Juanco.helpers;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.server.v1_7_R4.NBTTagCompound;
import net.minecraft.server.v1_7_R4.NBTTagList;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_7_R4.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class GiveItems {

	public static ItemStack book() {
		ItemStack book = ItemStacker(Material.BOOK, 1, 0, "&a&oLibro de Supervivencia", "Manual de supervivencia\n \nContiene construcciones\ne informacion "
				+ "sobre\nalgunas plantas.\n \nIdeal para campistas\ndesafortunados.\n \n \n&6&lNo es arrojable", false);
		return book;
	}
	
	public static ItemStack bag() {
		ItemStack bag = ItemStacker(Material.LEATHER, 1, 0, "&b&oMochila", "Porta Equipaje\n \nTiene un seguro roto\nen la manija.\n\nSe puede leer:\nAirlines Protected Bag"
				+ "\nNº 245786\n \n \n&6&lNo es arrojable", false);
		return bag;
	}
	
	public static ItemStack pedometer() {
		ItemStack pedometer = ItemStacker(Material.COMPASS, 1, 0, "&9&oPodometro", "Podometro\n\nCuenta tus pasos!\n\nPoco Util\n\n\n&6&lNo es arrojable", false);
		return pedometer;
	}
	
	public static ItemStack flyArea() {
		ItemStack fly = ItemStacker(Material.BLAZE_ROD, 1, 0, "&a&oSelector", "Usalo como en world edit!\n\nAncho maximo: 1\n\n\n&6&lNo es arrojable", false);
		return fly;
	}
	
	
	
	
	
	
	
	
	

	public static ItemStack ItemStacker(Material Material, int Amount, int Data, boolean enchanted) {
		ItemStack i = new ItemStack(Material, Amount, (short) Data);
		if (enchanted == true) i = addGlow(i);
		return i;
		
	}
	
	public static ItemStack ItemStacker(Material Material, int Amount, int Data, String Name, boolean enchanted) {
		ItemStack i = new ItemStack(Material, Amount, (short) Data);
		if (enchanted == true) i = addGlow(i);
		ItemMeta im = i.getItemMeta();
		im.setDisplayName(ChatColor.translateAlternateColorCodes('&', Name));
		i.setItemMeta(im);
		return i;
	}
	
	public static ItemStack ItemStacker(Material Material, int Amount, int Data, String Name, String Lore, boolean enchanted) {
		ItemStack i = new ItemStack(Material, Amount, (short) Data);
		if (enchanted == true) i = addGlow(i);
		ItemMeta im = i.getItemMeta();
		im.setDisplayName(ChatColor.translateAlternateColorCodes('&', Name));
		List<String> ls = new ArrayList<String>();
		for (String id : ChatColor.translateAlternateColorCodes('&', Lore).split("\n")) ls.add(id);
		im.setLore(ls);
		i.setItemMeta(im);
		return i;
	}
	
	public static ItemStack ItemStacker(Material Material, int Amount, int Data, String Name, String[] Lore, boolean enchanted) {
		ItemStack i = new ItemStack(Material, Amount, (short) Data);
		if (enchanted == true) i = addGlow(i);
		ItemMeta im = i.getItemMeta();
		im.setDisplayName(ChatColor.translateAlternateColorCodes('&', Name));
		List<String> ls = new ArrayList<String>();
		for (String id : ChatColor.translateAlternateColorCodes('&', Lore.toString()).substring(1, Lore.toString().length()-1).split(", ")) ls.add(id);
		im.setLore(ls);
		i.setItemMeta(im);
		return i;
	}
	
	public static ItemStack ItemStacker(Material Material, int Amount, int Data, String Name, List<String> Lore, boolean enchanted) {
		ItemStack i = new ItemStack(Material, Amount, (short) Data);
		if (enchanted == true) i = addGlow(i);
		ItemMeta im = i.getItemMeta();
		im.setDisplayName(ChatColor.translateAlternateColorCodes('&', Name));
		List<String> ls = new ArrayList<String>();
		for (String id : ChatColor.translateAlternateColorCodes('&', Lore.toString()).substring(1, Lore.toString().length()-1).split(", ")) ls.add(id);
		im.setLore(ls);
		i.setItemMeta(im);
		return i;
	}
	
	public static ItemStack addGlow(ItemStack item) {
		net.minecraft.server.v1_7_R4.ItemStack nmsStack = CraftItemStack.asNMSCopy(item);
		NBTTagCompound tag = null;
		if (!nmsStack.hasTag()) {
			tag = new NBTTagCompound();
		    nmsStack.setTag(tag);
		}
		if (tag == null) tag = nmsStack.getTag();
		NBTTagList ench = new NBTTagList();
		tag.set("ench", ench);
		nmsStack.setTag(tag);
		return CraftItemStack.asCraftMirror(nmsStack);
	}
}
