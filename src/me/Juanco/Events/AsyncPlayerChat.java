package me.Juanco.Events;

import java.util.ArrayList;

import me.Juanco.Commands.FlyareaCommand;
import me.Juanco.Configs.Config;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.Plugin;

public class AsyncPlayerChat implements Listener {

	private AsyncPlayerChat() { }
	static AsyncPlayerChat instance = new AsyncPlayerChat();
	public static AsyncPlayerChat getInstance() {
		return instance;
	}
	Config c = Config.getInstance();
	public ArrayList<Player> confirm = new ArrayList<Player>();
	
	public void register(Plugin pl) {
		Bukkit.getPluginManager().registerEvents(this, pl);
	}
	
	@EventHandler
	public void onAsyncPlayerChat(AsyncPlayerChatEvent e) {
		if (!e.isCancelled() && confirm.contains(e.getPlayer())) {
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
				confirm.remove(e.getPlayer());
				FlyareaCommand.cancel(e.getPlayer());
			} else e.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', "&cAccion Incorrecta!, usa &e+&c, &e- &co &esalir&c!"));
			if (finish) {
				Location loc1 = PlayerInteract.p1.get(e.getPlayer());
				Location loc2 = PlayerInteract.p2.get(e.getPlayer());
				PlayerInteract.saveloc("Fly Area.p1", loc1, c.get());
				PlayerInteract.saveloc("Fly Area.p2", loc2, c.get());
				c.save();
				FlyareaCommand.finish(e.getPlayer());
				confirm.remove(e.getPlayer());
			}
		}
	}
}
