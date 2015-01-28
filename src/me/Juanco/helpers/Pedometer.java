package me.Juanco.helpers;

import java.util.ArrayList;
import java.util.HashMap;

import me.Juanco.Configs.ConfigPlayer;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

public class Pedometer {

	static ConfigPlayer cp = ConfigPlayer.getInstance();
	public static HashMap<Player, Scoreboard> slist = new HashMap<Player, Scoreboard>();
	public static HashMap<Player, Integer> pasos = new HashMap<Player, Integer>();
	public static ArrayList<Player> displayed = new ArrayList<Player>();
	public static void display(Player p) {
		Scoreboard s;
		if (!slist.containsKey(p)) {
			s = Bukkit.getScoreboardManager().getNewScoreboard();
			slist.put(p, s);
		} else s = slist.get(p);

		Objective o;
		try {o = s.registerNewObjective("sc", "dummy");
		} catch(IllegalArgumentException e) {o = s.getObjective(DisplaySlot.SIDEBAR);}
		o.setDisplaySlot(DisplaySlot.SIDEBAR);
		String title = ChatColor.translateAlternateColorCodes('&', "&b&oPodometro");
		o.setDisplayName(title);
		String counter = ChatColor.translateAlternateColorCodes('&', "&a&lPasos: ");
		int b;
		if (!pasos.containsKey(p)) b = 0;
		else b = pasos.get(p);
		int d = b/3;
		s.getObjective(DisplaySlot.SIDEBAR).getScore(counter).setScore(d);
		p.setScoreboard(s);
		slist.put(p, s);
		displayed.add(p);
	}
	
	public static void hide(Player p) {
		if (p.getScoreboard() != null) {
			slist.put(p, p.getScoreboard());
			p.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
			displayed.remove(p);
		}
	}
	
	public static void update(Player p) {
		int b;
		if (pasos.containsKey(p)) b = pasos.get(p);
		else b = 0;
		b++;
		pasos.put(p, b);
		int d = b/3;
		if (p.getScoreboard().equals(slist.get(p))) {
			Scoreboard s = slist.get(p);
			if (d != s.getObjective(DisplaySlot.SIDEBAR).getScore(ChatColor.translateAlternateColorCodes('&', "&a&lPasos: ")).getScore()) {
				Objective o = s.getObjective(DisplaySlot.SIDEBAR);
				o.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&b&oPodometro"));
				s.getObjective(DisplaySlot.SIDEBAR).getScore(ChatColor.translateAlternateColorCodes('&', "&a&lPasos: ")).setScore(d);
				p.setScoreboard(s);
				slist.put(p, s);
			}
		}
	}
	
	public static void save(Player p) {
		cp.load(p);
		if (displayed.contains(p)) cp.get().set("Temp.Pedometer.Displayed", "true");
		if (pasos.containsKey(p)) cp.get().set("Temp.Pedometer.Steps", pasos.get(p));
		cp.save();
	}
	
	public static void load(Player p) {
		cp.load(p);
		if (cp.get().getString("Temp.Pedometer.Steps") != null) pasos.put(p, cp.get().getInt("Temp.Pedometer.Steps"));
		if (cp.get().getString("Temp.Pedometer.Displayed") != null) if (cp.get().getString("Temp.Pedometer.Displayed").equalsIgnoreCase("true")) {
			displayed.add(p);
			int b;
			if (pasos.containsKey(p)) b = pasos.get(p);
			else b = 0;
			int d = b/3;
			Scoreboard s = Bukkit.getScoreboardManager().getNewScoreboard();
			Objective o;
			try {o = s.registerNewObjective("sc", "dummy");
			} catch(IllegalArgumentException e) {o = s.getObjective(DisplaySlot.SIDEBAR);}
			o.setDisplaySlot(DisplaySlot.SIDEBAR);
			o.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&b&oPodometro"));
			s.getObjective(DisplaySlot.SIDEBAR).getScore(ChatColor.translateAlternateColorCodes('&', "&a&lPasos: ")).setScore(d);
			p.setScoreboard(s);
			slist.put(p, s);
		}
		cp.get().set("Temp.Pedometer", null);
		cp.save();
	}
}
