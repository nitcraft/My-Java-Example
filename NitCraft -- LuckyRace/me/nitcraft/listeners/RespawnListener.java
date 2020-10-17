package me.nitcraft.listeners;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;

import me.nitcraft.luckyrace.GameState;
import me.nitcraft.luckyrace.Global;
import me.nitcraft.luckyrace.LuckyRace;
import me.nitcraft.utils.Utils;

public class RespawnListener implements Listener {

	@SuppressWarnings("unused")
	private LuckyRace plugin;

	public RespawnListener(LuckyRace plugin) {
		this.plugin = plugin;
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}

	@EventHandler
	public void onRespawn(PlayerRespawnEvent e) {
		Player p = e.getPlayer();
		if (GameState.isState(GameState.PVP)) {

			e.setRespawnLocation(Utils.randomSpawn());
			if (!Global.activePlayers.contains(p)) {
				Global.spectators.add(p);
				
				for(Player all : Bukkit.getOnlinePlayers()) {
					if(!Global.spectators.contains(all)) {
						all.hidePlayer(p);
					}
				}
				p.setGameMode(GameMode.SPECTATOR);
			}
			return;
		}
		
		if(GameState.isState(GameState.INGAME)) {
			p.teleport(Global.checkPoints.get(p));
		}
	}

}
