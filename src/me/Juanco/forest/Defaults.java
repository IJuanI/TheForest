package me.Juanco.forest;

import java.util.Arrays;
import java.util.List;

import me.Juanco.Configs.Config;

import org.bukkit.configuration.file.FileConfiguration;

public class Defaults {

	static Config c = Config.getInstance();
	public static void config() {
		FileConfiguration fc = c.get();
		check("GUIs.Main.Name", fc, "&4&oContenidos");
		check("GUIs.Main.Size", fc, 54);
		
		item("Default", fc, "&a", "ENDER_PORTAL", 1, 0);
		item("Red", fc, "&a", "STAINED_GLASS_PANE", 1, 14);
		item("Green", fc, "&a", "STAINED_GLASS_PANE", 1, 5);
		item("Gold", fc, "&a", "STAINED_GLASS_PANE", 1, 1);
		
		item("Basics", fc, "&cGuia basica de supervivencia", "BOOK", 1, 0, 14, Arrays.asList("&fSimple guia para &cComenzar"));
		item("Fire", fc, "&2Fuego", "FIRE", 1, 0, 28, Arrays.asList("&aEsquemas &fde construcciones"));
		item("Shelters", fc, "&2Casas", "WOOD", 1, 0, 29, Arrays.asList("&aEsquemas &fde construcciones"));
		item("Storage", fc, "&2Almacenamiento", "CHEST", 1, 0, 30, Arrays.asList("&aEsquemas &fde construcciones"));
		item("Custom Building", fc, "&2Construcciones personalizadas", "THIN_GLASS", 1, 0, 31, Arrays.asList("&aEsquemas &fde construcciones"));
		item("Furniture", fc, "&2Decoraciones", "FLOWER_POT_ITEM", 1, 0, 32, Arrays.asList("&aEsquemas &fde construcciones"));
		item("Traps", fc, "&2Trampas", "WEB", 1, 0, 33, Arrays.asList("&aEsquemas &fde construcciones"));
		item("Food", fc, "&2Comida", "COOKED_BEEF", 1, 0, 34, Arrays.asList("&aEsquemas &fde construcciones"));
		item("Boats", fc, "&2Botes", "INK_SACK", 1, 7, 35, Arrays.asList("&c&oError: Estas paginas", "&c&ono parecen legibles!"));
		item("Effigies", fc, "&2Esfinges", "SKULL", 1, 3, 36, Arrays.asList("&aEsquemas &fde construcciones"));
		item("Herbal Medicines", fc, "&6Medicinas", "YELLOW_FLOWER", 1, 1, 47, Arrays.asList("&c&oError: Estas paginas", "&c&ono parecen legibles!"));
		item("Food2", fc, "&6Comida", "RAW_BEEF", 1, 0, 48, Arrays.asList("&c&oError: Estas paginas", "&c&ono parecen legibles!"));
		item("Caving", fc, "&6Cuevas", "STONE_PICKAXE", 1, 0, 49, Arrays.asList("&c&oError: Estas paginas", "&c&ono parecen legibles!"));
		item("DIY Crafting Guide", fc, "&6Guia de crafteo", "WORKBENCH", 1, 0, 51, Arrays.asList("&eInformacion &fsobre los crafteos"));
		item("Myths and Dealing With Fear", fc, "&6Mitos y miedos", "GHAST_TEAR", 1, 0, 52, Arrays.asList("&c&oError: Estas paginas", 
				"&c&ono parecen legibles!"));
		item("Notes", fc, "&6Notas", "BOOK_AND_QUILL", 1, 0, 53, Arrays.asList("&eEscribir &fnotas personales"));
		
		c.save();
	}
	
	public static void item(String path, FileConfiguration fc, String Name, String Material, int Amount, int Data, int Slot, List<String> lore) {
		check(path + ".Name", fc, Name);
		check(path + ".ID", fc, Material);
		check(path + ".Amount", fc, Amount);
		check(path + ".Data", fc, Data);
		check(path + ".Slot", fc, Slot);
		check(path + ".Lore", fc, lore);
	}
	
	public static void item(String path, FileConfiguration fc, String Name, String Material, int Amount, int Data) {
		check(path + ".Name", fc, Name);
		check(path + ".ID", fc, Material);
		check(path + ".Amount", fc, Amount);
		check(path + ".Data", fc, Data);
	}
	
	public static void check(String path, FileConfiguration fc, String result) {
		if (fc.getString(path) == null) fc.set(path, result);
	}
	
	public static void check(String path, FileConfiguration fc, int result) {
		if (fc.getString(path) == null) fc.set(path, result);
	}
	
	public static void check(String path, FileConfiguration fc, List<String> result) {
		if (fc.getString(path) == null) fc.set(path, result);
	}
}
