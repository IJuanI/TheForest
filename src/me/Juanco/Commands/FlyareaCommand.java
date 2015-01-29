package me.Juanco.Commands;

import java.util.ArrayList;

import me.Juanco.Configs.Config;
import me.Juanco.Configs.ConfigPlayer;
import me.Juanco.Events.PlayerInteract;
import me.Juanco.helpers.GiveItems;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class FlyareaCommand {

	static Config c = Config.getInstance();
	static ConfigPlayer cp = ConfigPlayer.getInstance();
	public static ArrayList<Player> selecting = new ArrayList<Player>();
	public static void flyarea(Player p, String[] args) {
		if (!selecting.contains(p)) {
			p.sendMessage(ChatColor.translateAlternateColorCodes('&', "\n"
					+ "                     &9&l>> &aSelector activado &9&l<<\n"
					+ "                  &2&l>> &3Usalo como world edit! &2&l<<\n"
					+ "          &e&l>> &bAparta el selector para cancelar! &e&l<<\n ").split("\n"));
			cp.load(p);
			cp.get().set("Temp.Hand", p.getItemInHand());
			cp.save();
			p.setItemInHand(GiveItems.flyArea());
			p.updateInventory();
			selecting.add(p);
		} else cancel(p);
	}
	
	public static void cancel(Player p) {
		if (selecting.contains(p)) {
			cp.load(p);
			selecting.remove(p);
			int n = -1;
			for (ItemStack i : p.getInventory().getContents()) {
				n++;
				if (i != null) if (i.equals(GiveItems.flyArea())) {
					ItemStack i2 = cp.get().getItemStack("Temp.Hand");
					p.getInventory().setItem(n, i2);
				}
			}
			cp.get().set("Temp.Hand", null);
			cp.save();
			p.updateInventory();
			if (PlayerInteract.p1.containsKey(p)) PlayerInteract.p1.remove(p);
			if (PlayerInteract.p2.containsKey(p)) PlayerInteract.p2.remove(p);
			p.sendMessage(ChatColor.RED + "Selector cancelado!");
		}
	}
	
	public static void finish(Player p) {
		if (selecting.contains(p)) {
			cp.load(p);
			selecting.remove(p);
			int n = -1;
			for (ItemStack i : p.getInventory().getContents()) {
				n++;
				if (i != null) if (i.equals(GiveItems.flyArea())) {
					ItemStack i2 = cp.get().getItemStack("Temp.Hand");
					p.getInventory().setItem(n, i2);
				}
			}
			cp.get().set("Temp.Hand", null);
			cp.save();
			p.updateInventory();
			if (PlayerInteract.p1.containsKey(p)) PlayerInteract.p1.remove(p);
			if (PlayerInteract.p2.containsKey(p)) PlayerInteract.p2.remove(p);
			p.sendMessage(ChatColor.GREEN + "Fly Area establecida!");
		}
	}
}
