package me.Juanco.Configs;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

public class Config {

	private Config() { }
	static Config instance = new Config();
	public static Config getInstance() {
		return instance;
	}
	
	File cfile;
	FileConfiguration config;
	
	public void setup(Plugin pl) {
		File df = pl.getDataFolder();
		cfile = new File(df, "config.yml");
		if (!df.exists()) df.mkdir();
		if (!cfile.exists()) {
			try {
				cfile.createNewFile();
			} catch (IOException e) {
				Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&cError al crear &e" + cfile.getName() + "&c!"));
			}
		}
		config = YamlConfiguration.loadConfiguration(cfile);
	}
	
	public FileConfiguration get() {
		return config;
	}
	
	public void save() {
		try {
			config.save(cfile);
		} catch (IOException e) {
			Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&cError al guardar &e" + cfile.getName() + "&c!"));
		}
	}
}
