package me.Juanco.Configs;

import java.io.File;
import java.io.IOException;

import me.Juanco.forest.TheForest;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class ConfigPlayer {

	private ConfigPlayer() { }
	static ConfigPlayer instance = new ConfigPlayer();
	public static ConfigPlayer getInstance() {
		return instance;
	}
	
	File cfile;
	FileConfiguration config;
	
	File df = TheForest.plugin.getDataFolder();
	File folder = new File(df, "player data" + File.separator);
	
	public void create(Player p, Plugin pl) {
		File df = pl.getDataFolder();
		
		cfile = new File(df, "player data" + File.separator + p.getUniqueId().toString() + ".yml");
		if (!df.exists()) df.mkdir();
		if (!folder.exists()) folder.mkdirs();
		if (!cfile.exists()) {
			try {
				cfile.createNewFile();
			} catch (IOException e) {
				Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&cError al crear &e" + cfile.getName() + "&c!"));
			}
		}
		config = YamlConfiguration.loadConfiguration(cfile);
	}
	
	public void load(Player p) {
		cfile = new File(df, "player data" + File.separator + p.getUniqueId().toString() + ".yml");
		config = YamlConfiguration.loadConfiguration(cfile);
	}
	
	public File getfile() {
		return cfile;
	}
	
	public FileConfiguration get() {
		return config;
	}
	
	public File folder() {
		return folder;
	}
	
	public void save() {
		try {
			config.save(cfile);
		} catch (IOException e) {
			Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&cError al guardar &e" + cfile.getName() + "&c!"));
		}
	}
}
