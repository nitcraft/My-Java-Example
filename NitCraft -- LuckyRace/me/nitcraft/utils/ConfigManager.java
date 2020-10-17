package me.nitcraft.utils;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class ConfigManager {
	
	public static File file = new File("plugins/LuckyRace", "config.yml");
	public static FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);
	
	public static void manageConfig() {
		
		if(!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		
		cfg.options().copyDefaults(true);
		cfg.addDefault("maxPlayers", 8);
		cfg.addDefault("minPlayers", 2);
		cfg.addDefault("prefix", "&8[&5LuckyRace&8] &b");
		cfg.addDefault("minJumpAndRunHeight", 20);
		cfg.addDefault("timeAfterFinish", 20);
		cfg.addDefault("defaultLobbyCountLength", 30);
		cfg.addDefault("defaultPvpCountLengthInSeconds", 600);
		cfg.addDefault("deathsUntilLose", 5);
		cfg.addDefault("timeAfterWin", 15);
		cfg.addDefault("deathMessage", "rasiert");
		cfg.addDefault("serverIPforScore", "NitCraft.de");
		cfg.addDefault("teamsAllowed", false);
		cfg.addDefault("deathmatchTimeInSeconds", 600);
		cfg.addDefault("respawnRange", 2);
		cfg.addDefault("hubServerName", "lobby");
		cfg.addDefault("databaseHost", "localhost");
		cfg.addDefault("databaseName", "LuckyRace");
		cfg.addDefault("databaseUser", "root");
		cfg.addDefault("databasePassword", "nikoali123");

		
		try {
			cfg.save(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
}
