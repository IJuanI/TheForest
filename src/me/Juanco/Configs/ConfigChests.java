package me.Juanco.Configs;

import java.io.File;
import java.io.IOException;

import me.Juanco.forest.TheForest;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class ConfigChests {

	private ConfigChests() { }
	static ConfigChests instance = new ConfigChests();
	public static ConfigChests getInstance() {
		return instance;
	}
	
	File cfile;
	FileConfiguration chest;
	
	File df = TheForest.plugin.getDataFolder();
	File folder = new File(df, "chests" + File.separator);
	
	public void create(String id) {
		
		cfile = new File(df, "chests" + File.separator + id.toLowerCase() + ".yml");
		if (!df.exists()) df.mkdir();
		if (!folder.exists()) folder.mkdirs();
		if (!cfile.exists()) {
			try {
				cfile.createNewFile();
			} catch (IOException e) {
				Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&cError al crear &e" + cfile.getName() + "&c!"));
			}
		}
		chest = YamlConfiguration.loadConfiguration(cfile);
	}
	
	public void load(String id) {
		cfile = new File(df, "chests" + File.separator + id.toLowerCase() + ".yml");
		chest = YamlConfiguration.loadConfiguration(cfile);
	}
	
	public File getfile() {
		return cfile;
	}
	
	public FileConfiguration get() {
		return chest;
	}
	
	public File folder() {
		return folder;
	}
	
	public void save() {
		try {
			chest.save(cfile);
		} catch (IOException e) {
			Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&cError al guardar &e" + cfile.getName() + "&c!"));
		}
	}
}
