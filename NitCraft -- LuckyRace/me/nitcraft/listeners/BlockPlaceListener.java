package me.nitcraft.listeners;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBurnEvent;
import org.bukkit.event.block.BlockPlaceEvent;

import me.nitcraft.luckyrace.LuckyRace;

public class BlockPlaceListener implements Listener{
	@SuppressWarnings("unused")
	private LuckyRace plugin;

	public BlockPlaceListener(LuckyRace plugin) {
		this.plugin = plugin;
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler
	public void onPlace(BlockPlaceEvent e) {
		if(e.getPlayer().getGameMode().equals(GameMode.CREATIVE)) {
			e.setCancelled(false);
			
			
	} else if(e.getBlockPlaced().getType() == Material.SPONGE) {
		e.setCancelled(false);
} else {
	e.setCancelled(true);
}
	}

	

	

	@EventHandler
	public void onPlace(BlockBurnEvent e) {
			e.setCancelled(true);
			
}
	}
