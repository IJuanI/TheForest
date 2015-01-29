package me.Juanco.forest;

import me.Juanco.Events.AsyncPlayerChat;
import me.Juanco.Events.InventoryClick;
import me.Juanco.Events.PlayerDropItem;
import me.Juanco.Events.PlayerInteract;
import me.Juanco.Events.PlayerItemHeld;
import me.Juanco.Events.PlayerJoin;
import me.Juanco.Events.PlayerMove;
import me.Juanco.Events.PlayerQuit;

import org.bukkit.plugin.Plugin;

public class EventsHandler {

	public static void register(Plugin pl) {
		PlayerJoin.getInstance().register(pl);
		PlayerDropItem.getInstance().register(pl);
		PlayerInteract.getInstance().register(pl);
		PlayerMove.getInstance().register(pl);
		PlayerItemHeld.getInstance().register(pl);
		InventoryClick.getInstance().register(pl);
		PlayerQuit.getInstance().register(pl);
		AsyncPlayerChat.getInstance().register(pl);
	}
}
