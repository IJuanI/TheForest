package me.Juanco.Commands;

import java.util.ArrayList;

import me.Juanco.Configs.Config;
import me.Juanco.Configs.ConfigPlayer;
import me.Juanco.Events.AsyncPlayerChat;
import me.Juanco.Events.PlayerInteract;
import me.Juanco.Events.PlayerMove;
import me.Juanco.helpers.GiveItems;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class SpawnareaCommand {

	static Config c = Config.getInstance();
	static ConfigPlayer cp = ConfigPlayer.getInstance();
	public static ArrayList<Player> selecting = new ArrayList<Player>();
	public static void SpawnArea(Player p, String[] args) {
		if (!selecting.contains(p)) {
			p.sendMessage(ChatColor.translateAlternateColorCodes('&', "\n"
					+ "                     &9&l>> &aSelector activado &9&l<<\n"
					+ "                  &2&l>> &3Usalo como world edit! &2&l<<\n"
					+ "          &e&l>> &bAparta el selector para cancelar! &e&l<<\n ").split("\n"));
			cp.load(p);
			cp.get().set("Temp.Hand", p.getItemInHand());
			cp.save();
			p.setItemInHand(GiveItems.selector());
			p.updateInventory();
			selecting.add(p);
			AsyncPlayerChat.getInstance().SpawnArea.add(p);
		} else cancel(p);
	}
	
	public static void cancel(Player p) {
		if (selecting.contains(p)) {
			cp.load(p);
			selecting.remove(p);
			AsyncPlayerChat.getInstance().SpawnArea.remove(p);
			int n = -1;
			for (ItemStack i : p.getInventory().getContents()) {
				n++;
				if (i != null) if (i.equals(GiveItems.selector())) {
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
			AsyncPlayerChat.getInstance().SeeArea.remove(p);
			int n = -1;
			for (ItemStack i : p.getInventory().getContents()) {
				n++;
				if (i != null) if (i.equals(GiveItems.selector())) {
					ItemStack i2 = cp.get().getItemStack("Temp.Hand");
					p.getInventory().setItem(n, i2);
				}
			}
			cp.get().set("Temp.Hand", null);
			cp.save();
			p.updateInventory();
			if (PlayerInteract.p1.containsKey(p)) PlayerInteract.p1.remove(p);
			if (PlayerInteract.p2.containsKey(p)) PlayerInteract.p2.remove(p);
			p.sendMessage(ChatColor.GREEN + "Spawn Area establecida!");
		}
	}
	
	public static boolean isOnSpawn(Player p) {
		Location p1 = PlayerMove.getInstance().loadloc("Spawn Area.p1", c.get());
		Location p2 = PlayerMove.getInstance().loadloc("Spawn Area.p2", c.get());
		Location loc = p.getLocation();
		if (p1 == null || p2 == null) return false;
		double x = loc.getBlockX();
		double y = loc.getBlockY();
		double z = loc.getBlockZ();
		double xp1 = p1.getX();
		double xp2 = p2.getX();
		double yp1 = p1.getY();
		double yp2 = p2.getY();
		double zp1 = p1.getZ();
		double zp2 = p2.getZ();
		double xM;
		double yM;
		double zM;
		double xm;
		double ym;
		double zm;
		
		if (xp1 > xp2) xM = xp1;
		else xM = xp2;
		if (yp1 > yp2) yM = yp1;
		else yM = yp2;
		if (zp1 > zp2) zM = zp1;
		else zM = zp2;

		if (xM == xp1) xm = xp2;
		else xm = xp1;
		if (yM == yp1) ym = yp2;
		else ym = yp1;
		if (zM == zp1) zm = zp2;
		else zm = zp1;
		
		if (x > xM || y > yM || z > zM || x < xm || y < ym || z < zm) return false;
		return true;
	}
}
