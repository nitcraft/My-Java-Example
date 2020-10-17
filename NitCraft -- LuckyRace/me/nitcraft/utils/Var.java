package me.nitcraft.utils;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import me.nitcraft.luckyrace.Global;

public class Var {

	public static String pr = Global.prefix,
			noPerm = pr + "§cDu hast dazu keine rechte oder dir fehlt der nötige Premium Rang.";

	public static File cfgfile = new File("plugins//SkyWars//locations.yml");
	public static YamlConfiguration cfg = YamlConfiguration.loadConfiguration(cfgfile);
	public static File positions = new File("plugins//LuckyRace//Positions.yml");
	public static FileConfiguration positionscfg = YamlConfiguration.loadConfiguration(positions);
	public static HashMap<Player, Integer> breakedBlocks = new HashMap<>();

	public static File positions2 = new File("plugins//LuckyRace//Positions2.yml");
	public static FileConfiguration positionscfg2 = YamlConfiguration.loadConfiguration(positions2);
	
	public static File positions3 = new File("plugins//LuckyRace//Positions3.yml");
	public static FileConfiguration positionscfg3 = YamlConfiguration.loadConfiguration(positions3);

	public static void setSpawnLocation(Player p, int number) {
		Location loc = p.getLocation();
		if (p.hasPermission("skywars.setup.setspawn")) {
			if (number <= 8) {
				double x = loc.getX();
				double y = loc.getY();
				double z = loc.getZ();
				String world = loc.getWorld().getName();

				positionscfg.set(number + ".PosX", x);
				positionscfg.set(number + ".PosY", y);
				positionscfg.set(number + ".PosZ", z);
				positionscfg.set(number + ".worldname", world.toLowerCase());

				try {
					positionscfg.save(positions);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else {
				p.sendMessage(Var.pr + "§cDu kannst nur bis zu 8 Verschiedene Spawn-Punkte setzten!");
			}

		} else {
			p.sendMessage("§cDazu hast du keine Berechtigugn!");
		}

	}

	public static void setSpawnLocation1(Player p, int number) {
		Location loc = p.getLocation();
		if (p.hasPermission("skywars.setup.setspawn")) {
			if (number <= 8) {
				double x = loc.getX();
				double y = loc.getY();
				double z = loc.getZ();
				String world = loc.getWorld().getName();

				positionscfg2.set(number + ".PosX", x);
				positionscfg2.set(number + ".PosY", y);
				positionscfg2.set(number + ".PosZ", z);
				positionscfg2.set(number + ".worldname", world.toLowerCase());

				try {
					positionscfg2.save(positions2);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else {
				p.sendMessage(Var.pr + "§cDu kannst nur bis zu 8 Verschiedene Spawn-Punkte setzten!");
			}

		} else {
			p.sendMessage("§cDazu hast du keine Berechtigugn!");
		}

	}
	
	public static void setSpawnLocation3(Player p, int number) {
		Location loc = p.getLocation();
		if (p.hasPermission("skywars.setup.setspawn")) {
			if (number <= 8) {
				double x = loc.getX();
				double y = loc.getY();
				double z = loc.getZ();
				String world = loc.getWorld().getName();

				positionscfg3.set(number + ".PosX", x);
				positionscfg3.set(number + ".PosY", y);
				positionscfg3.set(number + ".PosZ", z);
				positionscfg3.set(number + ".worldname", world.toLowerCase());

				try {
					positionscfg3.save(positions3);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else {
				p.sendMessage(Var.pr + "§cDu kannst nur bis zu 8 Verschiedene Spawn-Punkte setzten!");
			}

		} else {
			p.sendMessage("§cDazu hast du keine Berechtigugn!");
		}

	}

}