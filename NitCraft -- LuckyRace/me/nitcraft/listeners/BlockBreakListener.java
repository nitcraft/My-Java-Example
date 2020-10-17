package me.nitcraft.listeners;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import me.nitcraft.drops.LuckyBlockDrop;
import me.nitcraft.drops.LuckyBlockDropManager;
import me.nitcraft.luckyrace.GameState;
import me.nitcraft.luckyrace.Global;
import me.nitcraft.luckyrace.LuckyRace;
import me.nitcraft.utils.Var;

public class BlockBreakListener implements Listener {
	@SuppressWarnings("unused")
	private LuckyRace plugin;

	public BlockBreakListener(LuckyRace plugin) {
		this.plugin = plugin;
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}

	@EventHandler
	public void onBreak(BlockBreakEvent e) {
		if (!Global.activePlayers.contains(e.getPlayer())) {
			e.setCancelled(true);
		}
		if (Var.breakedBlocks.containsKey(e.getPlayer()))
			Var.breakedBlocks.put(e.getPlayer(), Integer.valueOf(Var.breakedBlocks.get(e.getPlayer()).intValue() + 1));
		else {
			Var.breakedBlocks.put(e.getPlayer(), Integer.valueOf(1));
		}
		
		if (GameState.isState(GameState.LOBBY)) {
			if(!Global.builds.contains(e.getPlayer())) {
				e.setCancelled(true);
			} else {
				e.setCancelled(false);
			}
		} else {

			if (!GameState.isState(GameState.PVP)) {

				
				
				/* if (e.getBlock().getType().equals(Material.SPONGE)) {
					e.setCancelled(true);
				     
//					StatsManager StatsManager = new StatsManager("LuckyRace");
//					StatsManager.addValue(e.getPlayer().getUniqueId().toString(), "BreakedBlocks", 1);
					e.getBlock().setType(Material.AIR);
					while(!Drops.dropNewDrop(e.getBlock().getLocation(), e.getPlayer())) {
						Drops.dropNewDrop(e.getBlock().getLocation(), e.getPlayer());
					} */
				
				if(e.getBlock().getType().equals(Material.SPONGE)) {
					
					e.setCancelled(true);
					LuckyBlockDrop luckyBlockDrop = LuckyBlockDropManager.getDrop();
					e.getPlayer().playSound(e.getPlayer().getLocation(), luckyBlockDrop.getSound(), luckyBlockDrop.getPitch(), luckyBlockDrop.getVolume());
					luckyBlockDrop.spawn(e.getPlayer(), e.getBlock());
					e.getBlock().setType(Material.AIR);
					
					
				} else if (!e.getPlayer().getGameMode().equals(GameMode.CREATIVE)) {
					if(!Global.builds.contains(e.getPlayer())) {
						e.setCancelled(true);
					} else {
						e.setCancelled(false);
					}
				}
			} else if (GameState.isState(GameState.PVP)) {
				if (Global.lootByKillMap.containsKey(e.getBlock())) {
					e.setCancelled(true);
					e.getBlock().setType(Material.AIR);
					if(Global.lootByKillMap.containsKey(e.getBlock())) {
						for(ItemStack a : Global.lootByKillMap.get(e.getBlock())) {
							if(a!= null && a.getType() != Material.AIR) {
								e.getBlock().getWorld().dropItem(e.getBlock().getLocation(), a);
							}
						}
						Global.lootByKillMap.remove(e.getBlock());
					}
				} else if(e.getPlayer().getGameMode().equals(GameMode.CREATIVE)) {
					e.setCancelled(false);
				} else {
					e.setCancelled(true);
				}
			}
		}
	}
}
