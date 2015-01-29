package me.Juanco.Commands;

import java.util.Arrays;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.ItemMeta;

import com.bobacadodl.JSONChatLib.JSONChatClickEventType;
import com.bobacadodl.JSONChatLib.JSONChatColor;
import com.bobacadodl.JSONChatLib.JSONChatExtra;
import com.bobacadodl.JSONChatLib.JSONChatFormat;
import com.bobacadodl.JSONChatLib.JSONChatHoverEventType;
import com.bobacadodl.JSONChatLib.JSONChatMessage;

public class ModifyCommand {

	public static void modify(Player p, String[] args) {
		if (args.length == 1) p.sendMessage(ChatColor.RED + "Especifica! (Name, Lore)");
		else if (ChestCommand.notequals(args[1], Arrays.asList("Name", "Lore"))) p.sendMessage(ChatColor.RED + "Uso incorrecto, especifica Name o Lore!");
		else if (p.getItemInHand().getType() == Material.AIR) p.sendMessage(ChatColor.RED + "No puedes modificar aire!");
		else if (args[1].equalsIgnoreCase("Name")) {
			if (args.length == 2) p.sendMessage(ChatColor.RED + "Especifica un nombre!");
			else {
				String msg = "";
				int n = 0;
				for (String arg : args) {
					n++;
					if (n > 2) msg += arg + " ";
				}
				msg = ChatColor.translateAlternateColorCodes('&', msg).substring(0, msg.length()-1);
				ItemMeta im = p.getItemInHand().getItemMeta();
				im.setDisplayName(msg);
				p.getItemInHand().setItemMeta(im);
				p.updateInventory();
				p.sendMessage(ChatColor.GREEN + "Accion exitosa!");
			}
		} else {
			msg1(p);
		}
	}
	
	public static void msg1(Player p) {
		p.sendMessage("");
		p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&3&l&o*****************************************************"));
		p.sendMessage(ChatColor.translateAlternateColorCodes('&', "                                     &6&l>> &a&lEspecifica una accion! &6&l<<"));
		
		JSONChatMessage m1 = new JSONChatMessage(" >> ", JSONChatColor.DARK_GRAY, Arrays.asList(JSONChatFormat.BOLD));
		JSONChatExtra msg1 = new JSONChatExtra("Agregar", JSONChatColor.YELLOW, Arrays.asList(JSONChatFormat.BOLD));
		msg1.setHoverEvent(JSONChatHoverEventType.SHOW_TEXT, "Agrega un Lore!");
		msg1.setClickEvent(JSONChatClickEventType.SUGGEST_COMMAND, "test");
		JSONChatExtra msg2 = new JSONChatExtra("* ", JSONChatColor.BLUE, Arrays.asList(JSONChatFormat.BOLD, JSONChatFormat.ITALIC));
		JSONChatExtra msg3 = new JSONChatExtra(" <<", JSONChatColor.DARK_GRAY, Arrays.asList(JSONChatFormat.BOLD));
		m1.addExtra(msg2);
		m1.addExtra(msg1);
		m1.addExtra(msg3);
		m1.sendToPlayer(p);
		p.sendMessage("");
		
		JSONChatMessage m2 = new JSONChatMessage(" >> ", JSONChatColor.DARK_GRAY, Arrays.asList(JSONChatFormat.BOLD));
		JSONChatExtra msg12 = new JSONChatExtra("Borrar", JSONChatColor.YELLOW, Arrays.asList(JSONChatFormat.BOLD));
		msg12.setHoverEvent(JSONChatHoverEventType.SHOW_TEXT, "Borra un Lore!");
		msg12.setClickEvent(JSONChatClickEventType.SUGGEST_COMMAND, "test");
		JSONChatExtra msg22 = new JSONChatExtra("* ", JSONChatColor.RED, Arrays.asList(JSONChatFormat.BOLD, JSONChatFormat.ITALIC));
		JSONChatExtra msg32 = new JSONChatExtra(" <<", JSONChatColor.DARK_GRAY, Arrays.asList(JSONChatFormat.BOLD));
		m2.addExtra(msg22);
		m2.addExtra(msg12);
		m2.addExtra(msg32);
		m2.sendToPlayer(p);
		p.sendMessage("");
		
		JSONChatMessage m3 = new JSONChatMessage(" >> ", JSONChatColor.DARK_GRAY, Arrays.asList(JSONChatFormat.BOLD));
		JSONChatExtra msg13 = new JSONChatExtra("Modificar", JSONChatColor.YELLOW, Arrays.asList(JSONChatFormat.BOLD));
		msg13.setHoverEvent(JSONChatHoverEventType.SHOW_TEXT, "Modifica un Lore!");
		msg13.setClickEvent(JSONChatClickEventType.SUGGEST_COMMAND, "test");
		JSONChatExtra msg23 = new JSONChatExtra("* ", JSONChatColor.WHITE, Arrays.asList(JSONChatFormat.BOLD, JSONChatFormat.ITALIC));
		JSONChatExtra msg33 = new JSONChatExtra(" <<", JSONChatColor.DARK_GRAY, Arrays.asList(JSONChatFormat.BOLD));
		m3.addExtra(msg23);
		m3.addExtra(msg13);
		m3.addExtra(msg33);
		m3.sendToPlayer(p);
		p.sendMessage("");
		
		JSONChatMessage m4 = new JSONChatMessage(" >> ", JSONChatColor.DARK_GRAY, Arrays.asList(JSONChatFormat.BOLD));
		JSONChatExtra msg14 = new JSONChatExtra("Borrado Total", JSONChatColor.YELLOW, Arrays.asList(JSONChatFormat.BOLD));
		msg14.setHoverEvent(JSONChatHoverEventType.SHOW_TEXT, "Borra todos los lores!");
		msg14.setClickEvent(JSONChatClickEventType.SUGGEST_COMMAND, "test");
		JSONChatExtra msg24 = new JSONChatExtra("* ", JSONChatColor.DARK_GREEN, Arrays.asList(JSONChatFormat.BOLD, JSONChatFormat.ITALIC));
		JSONChatExtra msg34 = new JSONChatExtra(" <<", JSONChatColor.DARK_GRAY, Arrays.asList(JSONChatFormat.BOLD));
		m4.addExtra(msg24);
		m4.addExtra(msg14);
		m4.addExtra(msg34);
		m4.sendToPlayer(p);
	}
}
