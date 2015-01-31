package me.Juanco.Events;

import java.util.ArrayList;
import java.util.List;

import me.Juanco.Commands.FlyareaCommand;
import me.Juanco.Configs.Config;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

public class AsyncPlayerChat implements Listener {

	private AsyncPlayerChat() { }
	static AsyncPlayerChat instance = new AsyncPlayerChat();
	public static AsyncPlayerChat getInstance() {
		return instance;
	}
	Config c = Config.getInstance();
	public ArrayList<Player> confirm = new ArrayList<Player>();
	public ArrayList<Player> Add = new ArrayList<Player>();
	
	public void register(Plugin pl) {
		Bukkit.getPluginManager().registerEvents(this, pl);
	}
	
	@EventHandler
	public void onAsyncPlayerChat(AsyncPlayerChatEvent e) {
		if (!e.isCancelled()) {
			Player p = e.getPlayer();
			if (confirm.contains(p)) {
				e.setCancelled(true);
				String msg = e.getMessage();
				boolean finish = false;
				if (msg.equalsIgnoreCase("+")) {
					c.get().set("Fly Area.Direction", "+");
					finish = true;
				} else if (msg.equalsIgnoreCase("-")) {
					c.get().set("Fly Area.Direction", "-");
					finish = true;
				} else if (msg.equalsIgnoreCase("salir")) {
					confirm.remove(p);
					FlyareaCommand.cancel(p);
				} else p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cAccion Incorrecta!, usa &e+&c, &e- &co &esalir&c!"));
				if (finish) {
					Location loc1 = PlayerInteract.p1.get(p);
					Location loc2 = PlayerInteract.p2.get(p);
					PlayerInteract.saveloc("Fly Area.p1", loc1, c.get());
					PlayerInteract.saveloc("Fly Area.p2", loc2, c.get());
					c.save();
					FlyareaCommand.finish(p);
					confirm.remove(p);
				}
			} else if (Add.contains(p)) {
				e.setCancelled(true);
				String msg = e.getMessage();
				if (msg.equalsIgnoreCase(".salir")) {
					p.sendMessage(ChatColor.GREEN + "Has salido exitosamente!");
				} else {
					ItemStack i = p.getItemInHand();
					List<String> lore;
					if (i.getItemMeta().hasLore()) lore = i.getItemMeta().getLore();
					else lore = new ArrayList<String>();
					lore.add(ChatColor.translateAlternateColorCodes('&', msg));
					ItemMeta im = i.getItemMeta();
					im.setLore(lore);
					p.getItemInHand().setItemMeta(im);
					p.sendMessage(ChatColor.GREEN + "Accion exitosa!");
					p.updateInventory();
				}
				Add.remove(p);
			}
		}
	}
}
