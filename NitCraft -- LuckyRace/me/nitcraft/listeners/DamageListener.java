package me.nitcraft.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

import me.nitcraft.luckyrace.GameState;
import me.nitcraft.luckyrace.LuckyRace;

public class DamageListener implements Listener {
	
	
	@SuppressWarnings("unused")
	private LuckyRace plugin;

	public DamageListener(LuckyRace plugin) {
		this.plugin = plugin;
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}

	@EventHandler
	public void onDamage(EntityDamageEvent e) {
		if(GameState.isState(GameState.LOBBY) || GameState.isState(GameState.RESTART)) {
			e.setCancelled(true);
			return;
		}
		
		if (!GameState.isState(GameState.PVP) && !e.getCause().equals(DamageCause.ENTITY_ATTACK)) {
			if(e.getEntity() instanceof Player) {
				e.setCancelled(true);
			}
		} else {
			if(e.getCause().equals(DamageCause.FALL)) {
				e.setCancelled(true);
				return;
			}
			if(GameState.isState(GameState.INGAME)) {
				e.setCancelled(false);
			}
		}
	}
}