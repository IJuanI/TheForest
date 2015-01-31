package me.Juanco.Commands;

import java.util.Arrays;

import me.Juanco.Events.AsyncPlayerChat;
import net.minecraft.server.v1_7_R4.ChatSerializer;
import net.minecraft.server.v1_7_R4.IChatBaseComponent;
import net.minecraft.server.v1_7_R4.PacketPlayOutChat;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_7_R4.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.ItemMeta;

public class ModifyCommand {

	public static void modify(Player p, String[] args) {
		if (args.length == 1) p.sendMessage(ChatColor.RED + "Especifica! (Name, Lore)");
		else if (ChestCommand.notequals(args[1], Arrays.asList("Name", "Lore", "add", "remove", "set", "clear"))) p.sendMessage(ChatColor.RED + "Uso incorrecto, especifica Name o Lore!");
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
		} else if (args[1].equalsIgnoreCase("Lore")) {
			msg1(p);
		} else if (args[1].equalsIgnoreCase("add")) {
			p.sendMessage(ChatColor.GREEN + "Especifica un lore!");
			p.sendMessage(ChatColor.RED + "\".salir\" para cancelar!");///tellraw @a {text:"OLAKASE.", color:"blue", bold:true, italic:true ,hoverEvent:{action:show_item,value:"{id:1, tag:{display:{Name:Test, Lore:[\"Quien lo lea es gay.\", \"Y mas quien lea este ultimo.\"]}}}"}}
			AsyncPlayerChat.getInstance().Add.add(p);
		}
	}
	
	public static void msg1(Player p) {
		p.sendMessage("");
		p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&3&l&o*****************************************************"));
		p.sendMessage(ChatColor.translateAlternateColorCodes('&', "                                     &6&l>> &a&lEspecifica una accion! &6&l<<"));
		IChatBaseComponent comp = ChatSerializer.a(ChatColor.translateAlternateColorCodes('&', "{text:\" >> \", color:dark_gray, bold:true, extra:[{text:\"* \", color:blue, "
				+ "italic:true, bold:true}, {text:Agregar, color:yellow, bold:true, hoverEvent:{action:show_item,value:\"{id:1, tag:{display:{Name:&b&oAgregar, "
				+ "Lore:[\\\"&a\\\", \\\"&aAgrega un &6Lore&a!\\\", \\\"&a\\\", \\\"&7&oThe Forest\\\"]}}}\"}, clickEvent:{action:run_command,value:\"/tf modify add\"}}, "
				+ "{text:\" <<\", color:dark_gray, bold:true}]}"));
		PacketPlayOutChat packet = new PacketPlayOutChat(comp, true);
		((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);
		p.sendMessage("");
		
		IChatBaseComponent comp2 = ChatSerializer.a(ChatColor.translateAlternateColorCodes('&', "{text:\" >> \", color:dark_gray, bold:true, extra:[{text:\"* \", color:red, "
				+ "italic:true, bold:true}, {text:Borrar, color:yellow, bold:true, hoverEvent:{action:show_item,value:\"{id:1, tag:{display:{Name:&d&oBorrar, "
				+ "Lore:[\\\"&a\\\", \\\"&aBorra un &6Lore&a!\\\", \\\"&a\\\", \\\"&7&oThe Forest\\\"]}}}\"}}, {text:\" <<\", color:dark_gray, bold:true}]}"));
		PacketPlayOutChat packet2 = new PacketPlayOutChat(comp2, true);
		((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet2);
		p.sendMessage("");
		
		IChatBaseComponent comp3 = ChatSerializer.a(ChatColor.translateAlternateColorCodes('&', "{text:\" >> \", color:dark_gray, bold:true, extra:[{text:\"* \", color:white, "
				+ "italic:true, bold:true}, {text:Modificar, color:yellow, bold:true, hoverEvent:{action:show_item,value:\"{id:1, tag:{display:{Name:&6&oModificar, "
				+ "Lore:[\\\"&a\\\", \\\"&aCambia un &6Lore&a!\\\", \\\"&a\\\", \\\"&7&oThe Forest\\\"]}}}\"}}, {text:\" <<\", color:dark_gray, bold:true}]}"));
		PacketPlayOutChat packet3 = new PacketPlayOutChat(comp3, true);
		((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet3);
		p.sendMessage("");

		IChatBaseComponent comp4 = ChatSerializer.a(ChatColor.translateAlternateColorCodes('&', "{text:\" >> \", color:dark_gray, bold:true, extra:[{text:\"* \", color:dark_green, "
				+ "italic:true, bold:true}, {text:\"Borrado Total\", color:yellow, bold:true, hoverEvent:{action:show_item,value:\"{id:1, tag:{display:{Name:&3&o&lAniquilacion!, "
				+ "Lore:[\\\"&a\\\", \\\"&aBorra todos los &6Lores&a!\\\", \\\"&a\\\", \\\"&7&oThe Forest\\\"]}}}\"}}, {text:\" <<\", color:dark_gray, bold:true}]}"));
		PacketPlayOutChat packet4 = new PacketPlayOutChat(comp4, true);
		((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet4);
	}
}
