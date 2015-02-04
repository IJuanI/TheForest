package me.Juanco.Events;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import me.Juanco.Commands.ChestCommand;
import me.Juanco.Commands.FlyareaCommand;
import me.Juanco.Commands.SeeareaCommand;
import me.Juanco.Commands.SpawnareaCommand;
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
	public ArrayList<Player> Remove = new ArrayList<Player>();
	public ArrayList<Player> Set = new ArrayList<Player>();
	public ArrayList<Player> Clear = new ArrayList<Player>();
	public ArrayList<Player> SeeArea = new ArrayList<Player>();
	public ArrayList<Player> SpawnArea = new ArrayList<Player>();
	public static HashMap<Player, ItemStack> item = new HashMap<Player, ItemStack>();
	public static HashMap<Player, Integer> slot = new HashMap<Player, Integer>();
	
	public void register(Plugin pl) {
		Bukkit.getPluginManager().registerEvents(this, pl);
	}
	
	@EventHandler
	public void onAsyncPlayerChat(AsyncPlayerChatEvent e) {
		String msg = e.getMessage();
		if (!e.isCancelled()) {
			Player p = e.getPlayer();
			if (confirm.contains(p)) {
				e.setCancelled(true);
				if (others(p, confirm)) {
					boolean finish = false;
					String s;
					if (SeeArea.contains(p)) s = "See Area";
					else if (SpawnArea.contains(p)) s = "Spawn Area";
					else s = "Fly Area";
					if (msg.equalsIgnoreCase("+")) {
						c.get().set(s +".Direction", "+");
						finish = true;
					} else if (msg.equalsIgnoreCase("-")) {
						c.get().set(s +".Direction", "-");
						finish = true;
					} else if (msg.equalsIgnoreCase("salir")) {
						confirm.remove(p);
						FlyareaCommand.cancel(p);
					} else p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cAccion Incorrecta!, usa &e+&c, &e- &co &esalir&c!"));
					if (finish) {
						Location loc1 = PlayerInteract.p1.get(p);
						Location loc2 = PlayerInteract.p2.get(p);
						PlayerInteract.saveloc(s + ".p1", loc1, c.get());
						PlayerInteract.saveloc(s + ".p2", loc2, c.get());
						if (SeeArea.contains(p)) SeeareaCommand.finish(p);
						else if (SpawnArea.contains(p)) SpawnareaCommand.finish(p);
						else FlyareaCommand.finish(p);
						c.save();
						confirm.remove(p);
					}
				} else p.sendMessage(ChatColor.RED + "No puedes hacer eso ahora!");
			} else if (Add.contains(p)) {
				e.setCancelled(true);
				if (others(p, Add)) {
					if (msg.equalsIgnoreCase(".salir")) {
						p.sendMessage(ChatColor.GREEN + "Has salido exitosamente!");
					} else {
						ItemStack i = item.get(p);
						List<String> lore;
						if (i.getItemMeta().hasLore()) lore = i.getItemMeta().getLore();
						else lore = new ArrayList<String>();
						lore.add(ChatColor.translateAlternateColorCodes('&', msg));
						ItemMeta im = i.getItemMeta();
						im.setLore(lore);
						i.setItemMeta(im);
						p.getInventory().setItem(slot.get(p), i);
						p.sendMessage(ChatColor.GREEN + "Accion exitosa!");
						p.updateInventory();
					}
					Add.remove(p);
					item.remove(p);
					slot.remove(p);
				} else p.sendMessage(ChatColor.RED + "No puedes hacer eso ahora!");
			} else if (Remove.contains(p) && others(p, Remove)) {
				e.setCancelled(true);
				if (others(p, Remove)) {
					boolean bo = true;
					if (msg.equalsIgnoreCase(".salir")) p.sendMessage(ChatColor.GREEN + "Has salido exitosamente!");
					else try {
						int n = Integer.parseInt(msg)-1;
						ItemStack i = item.get(p);
						List<String> lore;
						ItemMeta im = i.getItemMeta();
						if (im.hasLore()) lore = im.getLore();
						else lore = new ArrayList<String>();
						if (lore.isEmpty()) {
							bo = false;
							String m = "\n    &4&l>> &cError: El item &d" + i.getType().toString().toLowerCase().replace("_", " ") + 
									" &cno tiene lore &4&l<<\n ";
							p.sendMessage(ChatColor.translateAlternateColorCodes('&', m).split("\n"));
						} else if (lore.size() < n) {
							bo = false;
							String m = "\n    &4&l>> &cError: El item &d" + i.getType().toString().toLowerCase().replace("_", " ") + 
									" &cno tiene tantas lineas en el lore &4&l<<\n";
							p.sendMessage(ChatColor.translateAlternateColorCodes('&', m).split("\n"));
						} else {
							lore.remove(n);
							im.setLore(lore);
							i.setItemMeta(im);
							p.getInventory().setItem(slot.get(p), i);
							p.sendMessage(ChatColor.GREEN + "Accion exitosa!");
							p.updateInventory();
						}
					} catch (IllegalArgumentException e1) {
						String m = "\n    &4&l>> &cError: &d%msg% &cno es un numero &4&l<<\n ";
						p.sendMessage(ChatColor.translateAlternateColorCodes('&', m).replace("%msg%", msg).split("\n"));
						bo = false;
					}
					if (bo) {
						Remove.remove(p);
						item.remove(p);
						slot.remove(p);
					}
				} else p.sendMessage(ChatColor.RED + "No puedes hacer eso ahora!");
			} else if (Set.contains(p)) {
				e.setCancelled(true);
				if (others(p, Set)) {
					boolean bo = true;
					String[] args = msg.split(" ");
					if (args.length != 0) {
						if (!msg.equalsIgnoreCase(".salir")) {
							try {
								int n = Integer.parseInt(args[0])-1;
								ItemStack i = item.get(p);
								List<String> lore;
								ItemMeta im = i.getItemMeta();
								if (im.hasLore()) lore = im.getLore();
								else lore = new ArrayList<String>();
								if (args.length < 2) {
									bo = false;
									String m = "\n    &4&l>> &cError: Especifica un mensaje &4&l<<\n ";
									p.sendMessage(ChatColor.translateAlternateColorCodes('&', m).split("\n"));
								} else if (lore.size() < n) {
									bo = false;
									String m = "\n    &4&l>> &cError: El item &d" + i.getType().toString().toLowerCase().replace("_", " ") + 
											" &cno tiene tantas lineas en el lore &4&l<<\n ";
									p.sendMessage(ChatColor.translateAlternateColorCodes('&', m).split("\n"));
								} else {
									String m = "";
									int Int = 0;
									for (String arg : args) {
										Int++;
										if (Int > 1) m += arg + " ";
									}
									m = ChatColor.translateAlternateColorCodes('&', m.substring(0, m.length()-1));
									lore.set(n, m);
									im.setLore(lore);
									i.setItemMeta(im);
									p.getInventory().setItem(slot.get(p), i);
									p.updateInventory();
									p.sendMessage(ChatColor.GREEN + "Accion Exitosa");
								}
							} catch(IllegalArgumentException e1) {
								bo = false;
								String m = "\n    &4&l>> &cError: &d%msg% &cno es un numero &4&l<<\n ";
								p.sendMessage(ChatColor.translateAlternateColorCodes('&', m).replace("%msg%", args[0]).split("\n"));
							}
						} else p.sendMessage(ChatColor.GREEN + "Has salido exitosamente!");
					}
					if (bo) {
						Set.remove(p);
						item.remove(p);
						slot.remove(p);
					}
				} else p.sendMessage(ChatColor.RED + "No puedes hacer eso ahora!");
			} else if (Clear.contains(p)) {
				e.setCancelled(true);
				if (others(p, Clear)) {
					boolean bo = true;
					if (ChestCommand.notequals(msg, Arrays.asList("no", "si", "yes"))) {
						bo = false;
						String m = "\n    &4&l>> &cError: El mensaje debe ser si o no &4&l<<\n ";
						p.sendMessage(ChatColor.translateAlternateColorCodes('&', m).split("\n"));
					} else if (msg.equalsIgnoreCase("no")) p.sendMessage(ChatColor.GREEN + "Has salido exitosamente!");
					else {
						ItemStack i = item.get(p);
						ItemMeta im = i.getItemMeta();
						im.setLore(null);
						i.setItemMeta(im);
						p.getInventory().setItem(slot.get(p), i);
						p.updateInventory();
						p.sendMessage(ChatColor.GREEN + "Accion Exitosa");
					}
					if (bo) {
						Clear.remove(p);
						item.remove(p);
						slot.remove(p);
					}
				} else p.sendMessage(ChatColor.RED + "No puedes hacer eso ahora!");
			}
		}
	}
	
	public boolean others(Player p, ArrayList<Player> list) {
		List<ArrayList<Player>> l = Arrays.asList(confirm, Add, Remove, Set, Clear);
		boolean b = true;
		for (ArrayList<Player> al: l) if (al != list && al.contains(p)) {
			b = false;
			break;
		}
		return b;
	}
	
	public boolean other(Player p) {
		List<ArrayList<Player>> l = Arrays.asList(confirm, Add, Remove, Set, Clear);
		boolean b = true;
		for (ArrayList<Player> al: l) if (al.contains(p)) {
			b = false;
			break;
		}
		return b;
	}
}
