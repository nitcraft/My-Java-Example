package me.nitcraft.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import me.nitcraft.luckyrace.LuckyRace;

public class PlayerItemHeldListener implements Listener{
	
	@SuppressWarnings("unused")
	private LuckyRace plugin;
	
	public PlayerItemHeldListener(LuckyRace plugin) {
		this.plugin = plugin;
		Bukkit.getServer().getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler
	public void onItemHeld(PlayerItemHeldEvent e) {
		if (e.getPlayer().getInventory().getItem(e.getNewSlot()) != null && e.getPlayer().getInventory().getItem(e.getNewSlot()).getType().equals(Material.GOLD_AXE)) {
			if (e.getPlayer().getInventory().getItem(e.getNewSlot()).hasItemMeta()) {
				ItemMeta pItem = e.getPlayer().getInventory().getItem(e.getNewSlot()).getItemMeta();
				if (pItem.hasDisplayName() && pItem.getDisplayName().equalsIgnoreCase("§cStreitaxt der Stärke")) {
					e.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, Integer.MAX_VALUE, 0));
				}
			}
		} else {
			e.getPlayer().removePotionEffect(PotionEffectType.INCREASE_DAMAGE);
		}

	}
}
