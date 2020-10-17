package me.nitcraft.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.weather.WeatherChangeEvent;

import me.nitcraft.luckyrace.LuckyRace;

public class FoodListener implements Listener {
	@SuppressWarnings("unused")
	private LuckyRace plugin;

	public FoodListener(LuckyRace plugin) {
		this.plugin = plugin;
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}

	@EventHandler
	public void onFoodChange(FoodLevelChangeEvent e) {
		e.setCancelled(true);
	}
	
	@EventHandler
	public void WatherChange(WeatherChangeEvent e) {
		e.setCancelled(true);
	}
}
