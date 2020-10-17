package me.nitcraft.listeners;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;

import me.nitcraft.luckyrace.GameState;
import me.nitcraft.luckyrace.LuckyRace;

public class PreJoinListener implements Listener{
	
	@SuppressWarnings("unused")
	private LuckyRace plugin;

	public static File file = new File("plugins/LuckyRace", "spawns.yml");
	public static FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);
	
	
	public PreJoinListener(LuckyRace plugin) {
		this.plugin = plugin;
		Bukkit.getPluginManager().registerEvents(this, plugin);
	}
	
 	 @EventHandler
	 public void onLogin(AsyncPlayerPreLoginEvent e){
 		 if(GameState.isState(GameState.LOBBY)){
			 e.allow();
		 }
	 }
  	
}
