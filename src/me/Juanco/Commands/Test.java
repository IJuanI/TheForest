package me.Juanco.Commands;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Test {
	
	public static void test(Player p, String[] args) {
		String contents = "";
		for (ItemStack i : p.getInventory().getContents()) if (i != null) contents += i.getType().toString().replace("_", " ").toLowerCase() + ", ";
		p.sendMessage(contents.substring(0, contents.length()-2));
	}
}
