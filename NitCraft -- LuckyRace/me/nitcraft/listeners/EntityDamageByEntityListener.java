package me.nitcraft.listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import me.nitcraft.luckyrace.GameState;
import me.nitcraft.luckyrace.LuckyRace;

public class EntityDamageByEntityListener implements Listener{

	@SuppressWarnings("unused")
	private LuckyRace plugin;
	
	public EntityDamageByEntityListener(LuckyRace plugin) {
		this.plugin = plugin;
		Bukkit.getServer().getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler
	public void onHit(EntityDamageByEntityEvent e) {
		if(GameState.isState(GameState.INGAME)) {
			if(e.getDamager() instanceof Player && e.getEntity() instanceof Player) {
				e.setCancelled(true);
			} else if(e.getEntity() instanceof Player && e.getDamager() instanceof Arrow) {
				Arrow a = (Arrow) e.getDamager();
				if (a.getShooter() instanceof Player) {
				e.setCancelled(true);
				} else {
					e.setCancelled(false);
				}
			}
		}
	}
	
}
