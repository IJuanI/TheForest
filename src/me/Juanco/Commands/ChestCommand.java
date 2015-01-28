package me.Juanco.Commands;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import me.Juanco.Configs.Config;
import me.Juanco.Configs.ConfigChests;
import me.Juanco.Configs.ConfigPlayer;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Chest;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class ChestCommand {

	static Config c = Config.getInstance();
	static ConfigChests cc = ConfigChests.getInstance();
	static ConfigPlayer cp = ConfigPlayer.getInstance();
	
	public static void chest(Player p, String[] args) {
		if (args.length == 1) {
			p.sendMessage(ChatColor.RED + "Uso: /tf chest (accion) (id)");
			return;
		}
		if (notequals(args[1], Arrays.asList("create", "delete", "save", "list", "setmessage", "setoption"))) {
			p.sendMessage(ChatColor.YELLOW + "Acciones validas: " + ChatColor.AQUA + "create, delete, save, list, setmessage, setoption");
			return;
		}
		if (args[1].equalsIgnoreCase("list")) {
			String list = "&2Cofres: ";
			int i = 1;
			for (File f : cc.folder().listFiles()) {
				if (i == 1) {
					list += "&b" + f.getName().substring(0, f.getName().length()-4) + "&6, ";
					i++;
				} else {
					list += "&f" + f.getName().substring(0, f.getName().length()-4) + "&6, ";
					i = 1;
				}
			}
			if (list.equals("&2Cofres: ")) list += "&cNo hay ningun cofre!    ";
			list = ChatColor.translateAlternateColorCodes('&', list.substring(0, list.length()-4));
			p.sendMessage(list);
			return;
		}
		if (args.length == 2) {
			p.sendMessage(ChatColor.RED + "Uso: /tf chest (accion) (id)");
			return;
		}
		cc.load(args[2]);
		if (args[1].equalsIgnoreCase("create")) {
			if (cc.getfile().exists()) {
				p.sendMessage(ChatColor.RED + "Ese cofre ya existe!");
				return;
			}
			cc.create(args[2]);
			Location loc = p.getLocation();
			loc.getBlock().setType(Material.CHEST);
			cc.get().set("Location.world", loc.getWorld().getName());
			cc.get().set("Location.x", loc.getBlockX());
			cc.get().set("Location.y", loc.getBlockY());
			cc.get().set("Location.z", loc.getBlockZ());
			cc.save();
			p.sendMessage(ChatColor.GREEN + "Cofre " + args[2] + " creado!");
		} else if (args[1].equalsIgnoreCase("delete")) {
			if (!cc.getfile().exists()) {
				p.sendMessage(ChatColor.RED + "Ese cofre no existe!");
				return;
			}
			World w = Bukkit.getWorld(cc.get().getString("Location.world"));
			double x = cc.get().getDouble("Location.x");
			double y = cc.get().getDouble("Location.y");
			double z = cc.get().getDouble("Location.z");
			Location loc = new Location(w, x, y, z);
			loc.getBlock().setType(Material.AIR);
			cc.getfile().delete();
			for (File f : cp.folder().listFiles()) {
				FileConfiguration fc = YamlConfiguration.loadConfiguration(f);
				if (fc.getStringList("Used").contains(args[2].toLowerCase())) {
					try {
						List<String> ls = fc.getStringList("Used");
						ls.remove(args[2].toLowerCase());
						fc.set("Used", ls);
						fc.save(f);
					} catch (IOException e) {
						p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cError al guardar &e" + f.getName() + "&c!"));
					}
				}
			}
			p.sendMessage(ChatColor.GREEN + "Cofre borrado exitosamente!");
		} else if (args[1].equalsIgnoreCase("save")) {
			if (!cc.getfile().exists()) {
				p.sendMessage(ChatColor.RED + "Ese cofre no existe!");
				return;
			}
			World w = Bukkit.getWorld(cc.get().getString("Location.world"));
			double x = cc.get().getDouble("Location.x");
			double y = cc.get().getDouble("Location.y");
			double z = cc.get().getDouble("Location.z");
			Location loc = new Location(w, x, y, z);
			if (loc.getBlock().getType().equals(Material.CHEST)) {
				Chest ch = (Chest) loc.getBlock().getState();
				cc.get().set("Inventory", null);
				cc.save();
				int n = -1;
				for (ItemStack i : ch.getInventory().getContents()) {
					n++;
					if (i != null) cc.get().set("Inventory." + n, i);
				}
				cc.save();
				p.sendMessage(ChatColor.GREEN + "Inventario guardado!");
			} else p.sendMessage(ChatColor.RED + "Error, El bloque no es un cofre!");
		} else if (args[1].equalsIgnoreCase("setmessage")) {
			if (!cc.getfile().exists()) {
				p.sendMessage(ChatColor.RED + "Ese cofre no existe!");
				return;
			}
			if (args.length == 3) p.sendMessage(ChatColor.RED + "Especifica un mensaje!");
			else {
				String msg = "";
				int n = 0;
				for (String arg : args) {
					n++;
					if (n > 3) msg += arg + " ";
				}
				cc.get().set("Message", msg.substring(0, msg.length()-1).split("::"));
				cc.save();
				p.sendMessage(ChatColor.GREEN + "Mensaje Establecido!");
				p.sendMessage(ChatColor.GREEN + "Mensaje:");
				p.sendMessage(ChatColor.translateAlternateColorCodes('&', msg).split("::"));
			}
		} else if (args[1].equalsIgnoreCase("setoption")) {
			if (!cc.getfile().exists()) {
				p.sendMessage(ChatColor.RED + "Ese cofre no existe!");
				return;
			}
			if (args.length == 3) p.sendMessage(ChatColor.RED + "Especifica una opcion!");
			else {
				List<String> list = Arrays.asList("single-use");
				if (notequals(args[3], list)) p.sendMessage(ChatColor.YELLOW + "Opciones validas: " + ChatColor.AQUA + list);
				else if (args.length == 4) p.sendMessage(ChatColor.RED + "Especifica true o false!");
					else if (notequals(args[4], Arrays.asList("true", "false"))) p.sendMessage(ChatColor.RED + "Especifica true o false!");
					else {
						cc.get().set(args[3].toLowerCase(), args[4].toLowerCase());
						cc.save();
						p.sendMessage(ChatColor.GREEN + "Establecida correctamente la opcion " + args[3].toLowerCase() + " a " + args[4].toLowerCase() + "!");
					}
			}
		}
		
	}
	
	public static boolean notequals(String original, List<String> list) {
		boolean b = true;
		for (String s : list) {
			if (s.equalsIgnoreCase(original)) b = false;
		}
		return b;
	}
}
