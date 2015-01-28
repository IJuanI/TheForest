package me.Juanco.Events;

import java.io.File;
import java.util.List;

import me.Juanco.Configs.ConfigChests;
import me.Juanco.Configs.ConfigPlayer;
import me.Juanco.helpers.GiveItems;
import me.Juanco.helpers.OpenBag;
import me.Juanco.helpers.Pedometer;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

public class PlayerInteract implements Listener {

	ConfigChests cc = ConfigChests.getInstance();
	ConfigPlayer cp = ConfigPlayer.getInstance();
	private PlayerInteract() { }
	static PlayerInteract instance = new PlayerInteract();
	public static PlayerInteract getInstance() {
		return instance;
	}
	
	public void register(Plugin pl) {
		Bukkit.getPluginManager().registerEvents(this, pl);
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent e) {
		if (e.getItem() != null) {
			ItemStack c = e.getItem();
			Player p = e.getPlayer();
			if (c.equals(GiveItems.bag())) OpenBag.open(p);
			else if (c.equals(GiveItems.pedometer())) {
				if (Pedometer.displayed.contains(p)) Pedometer.hide(p);
				else Pedometer.display(p);
			}
		}
		if (e.getClickedBlock() != null) {
			if (e.getClickedBlock().getType().equals(Material.CHEST)) {
				for (File f : cc.folder().listFiles()) {
					FileConfiguration fc = YamlConfiguration.loadConfiguration(f);
					World w = Bukkit.getWorld(fc.getString("Location.world"));
					double x = fc.getDouble("Location.x");
					double y = fc.getDouble("Location.y");
					double z = fc.getDouble("Location.z");
					Location loc = new Location(w, x, y, z);
					if (e.getClickedBlock().getLocation().getBlock().getLocation().equals(loc)) {
						e.setCancelled(true);
						Player p = e.getPlayer();
						if (p.hasPermission("theforest.openchests") && p.isSneaking()) {
							if (e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
								e.setCancelled(false);
								String name = ChatColor.translateAlternateColorCodes('&', "&b&l" + f.getName().substring(0, 1).toUpperCase() 
										+ f.getName().substring(1, f.getName().length()-4));
								p.sendMessage(ChatColor.GREEN + "Abriendo cofre " + name + ChatColor.GREEN + "!");
							} else {
								byte b = (byte) (e.getClickedBlock().getData());
								if (b == 0) b = 4;
								else if (b == 4) b = 2;
								else if (b == 2) b = 5;
								else  b = 0;
								e.getClickedBlock().setData(b);
							}
						} else {
							cp.load(p);
							if (cp.get().getStringList("Used").contains(f.getName().toLowerCase().substring(0, f.getName().length()-4))) {
								p.sendMessage(ChatColor.RED + "Ya has usado ese cofre!");
							} else {
								if (fc.contains("Inventory")) {
									for (String s : fc.getConfigurationSection("Inventory").getKeys(false)) {
										ItemStack i = fc.getItemStack("Inventory." + s);
										p.getInventory().addItem(i);
									}
								}
								p.updateInventory();
								if (fc.getString("Message") != null) {
									for (String s : fc.getStringList("Message")) {
										p.sendMessage(ChatColor.translateAlternateColorCodes('&', s));
									}
								}
								if (fc.getString("single-use") != null) {
									if (fc.getString("single-use").equals("true")) {
										List<String> ls = cp.get().getStringList("Used");
										ls.add(f.getName().toLowerCase().substring(0, f.getName().length()-4));
										cp.get().set("Used", ls);
										cp.save();
									}
								}
							}
						}
					}
				}
			}
		}
	}
}
