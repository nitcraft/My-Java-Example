package me.nitcraft.listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import me.nitcraft.luckyrace.Global;
import me.nitcraft.luckyrace.LuckyRace;

public class PlayerDamageListener implements Listener {
	
	@SuppressWarnings("unused")
	private LuckyRace plugin;
	
	public PlayerDamageListener(LuckyRace plugin) {
		this.plugin = plugin;
		Bukkit.getServer().getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler
	public void onDamageByPlayer(EntityDamageByEntityEvent e) {
		if(e.getDamager() instanceof Player && e.getEntity() instanceof Player) {
			Player d = (Player) e.getDamager();
			Player en = (Player) e.getEntity();
			if(d.getItemInHand().hasItemMeta() && d.getItemInHand().getItemMeta().getDisplayName() != null) {
				if(d.getItemInHand().getItemMeta().getDisplayName().equalsIgnoreCase("§2Giftdolch")) {
					if (Global.activePlayers.contains(d)) {
					en.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 100, 0));
				}
					}
			}
		}
	}

}