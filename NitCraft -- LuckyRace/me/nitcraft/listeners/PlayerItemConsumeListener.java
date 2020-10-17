package me.nitcraft.listeners;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import me.nitcraft.luckyrace.LuckyRace;

public class PlayerItemConsumeListener implements Listener {

	@SuppressWarnings("unused")
	private LuckyRace plugin;

	public PlayerItemConsumeListener(LuckyRace plugin) {
		this.plugin = plugin;
		Bukkit.getPluginManager().registerEvents(this, plugin);
	}

	@EventHandler
	public void onConsume(PlayerItemConsumeEvent e) {
		if (e.getItem().getItemMeta().getDisplayName() != null) {
			if (e.getItem().getItemMeta().getDisplayName().equalsIgnoreCase("§bLucky Potion")) {
				e.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 20 * 10, 2));
			} else if(e.getItem().getItemMeta().getDisplayName().equalsIgnoreCase("§6Heal Potion")) {
				e.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, 20 * 5, 1));
			}
		}
	}

	

}
