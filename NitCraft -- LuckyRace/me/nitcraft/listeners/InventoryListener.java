package me.nitcraft.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.ItemStack;

import me.nitcraft.luckyrace.GameState;
import me.nitcraft.luckyrace.Global;
import me.nitcraft.luckyrace.LuckyRace;
import me.nitcraft.utils.ConfigManager;
import me.nitcraft.utils.Settings;
import me.nitcraft.utils.Utils;

public class InventoryListener implements Listener{



	private LuckyRace plugin;
	
	public InventoryListener(LuckyRace plugin) {
		this.plugin = plugin;
		Bukkit.getServer().getPluginManager().registerEvents(this, plugin);
	}
	

	
	@EventHandler
	public void onDrop21(PlayerDropItemEvent e) {
		if(e.getItemDrop() == null) {
			return;
		}
		if(e.getItemDrop().getItemStack().getType() == Material.PAPER) {
			if(e.getItemDrop().getItemStack().hasItemMeta()) {
				if(e.getItemDrop().getItemStack().getItemMeta().getDisplayName().contains("Map-Voting")) {
					e.setCancelled(true);
				}
			}
		}
			}
	
	
	
	
	
	@EventHandler
	public void onClick1(InventoryClickEvent e) {
		if(GameState.isState(GameState.LOBBY) || GameState.isState(GameState.RESTART)) {
			e.setCancelled(true);
			} else {
				e.setCancelled(false);
			}
	}
	
	@EventHandler
	public void onDrop(PlayerDropItemEvent e) {
		if(e.getItemDrop() == null) {
			return;
		}
		if(e.getItemDrop().getItemStack().getType() == Material.SLIME_BALL) {
			if(e.getItemDrop().getItemStack().hasItemMeta()) {
				if(e.getItemDrop().getItemStack().getItemMeta().getDisplayName().contains("Runde verlassen")) {
					e.setCancelled(true);
				}
			}
		} else if(Utils.checkForItemStackTitle(e.getItemDrop().getItemStack(), "Einstellungen")) {
			e.setCancelled(true);
			return;
		}
	}
	
	@EventHandler
	public void onClick(InventoryClickEvent e) {
		if(e.getCurrentItem() != null && e.getCurrentItem().getType().equals(Material.SLIME_BALL)) {
			if(e.getCurrentItem().hasItemMeta()) {
				if(e.getCurrentItem().getItemMeta().getDisplayName().contains("Runde verlassen")) {
					e.setCancelled(true);
					return;
				}
			}
		}
		
		if(e.getInventory().getName().contains("Einstellungen")) {
			
			e.setCancelled(true);
			ItemStack i = e.getCurrentItem();
			if(i!=null) {
				if(Utils.checkForItemStackTitle(i, "eingeschaltet")) {
					Global.teamsAllowed = !Global.teamsAllowed;
					Settings.openWelcomeInventory((Player)e.getWhoClicked());
					
					return;
				}
				if(Utils.checkForItemStackTitle(i, "ausgeschaltet")) {

					Global.teamsAllowed = !Global.teamsAllowed;
					Settings.openWelcomeInventory((Player) e.getWhoClicked());
					return;
				}
				if(Utils.checkForItemStackTitle(i, "Leben")) {
					if(e.getClick().equals(ClickType.LEFT)) {
						if((Global.deathsUntilLose - 1) < 1) {
							e.getWhoClicked().closeInventory();
							e.getWhoClicked().sendMessage(Global.prefix + "§4FEHLER:§b Zahl zu niedrig.");
							return;
						} else {
							Global.deathsUntilLose--;
						}
					} else if(e.getClick().equals(ClickType.RIGHT)) {
						Global.deathsUntilLose ++;
					}
					Settings.openWelcomeInventory((Player) e.getWhoClicked());
					return;
				}
				if(i.getType().equals(Material.WATCH)) {
					if(e.getClick().equals(ClickType.LEFT)) {
						if((Global.deathMatchCountInt - 60) < 60) {
							e.getWhoClicked().closeInventory();
							e.getWhoClicked().sendMessage(Global.prefix + "§4FEHLER:§b Zahl zu niedrig.");
							return;
						} else {
							Global.deathMatchCountInt -= 60;
						}
					} else if(e.getClick().equals(ClickType.RIGHT)) {
						Global.deathMatchCountInt += 60;
					}
					Settings.openWelcomeInventory((Player) e.getWhoClicked());
					return;
				}
				if(i.getType().equals(Material.BLAZE_POWDER)) {
					if(Utils.checkForItemStackTitle(i, "DM")) {
						Global.deathMatchCountInt = ConfigManager.cfg.getInt("deathmatchTimeInSeconds");
						Settings.openWelcomeInventory((Player) e.getWhoClicked());
						return;
					}
					
					if(Utils.checkForItemStackTitle(i, "Team")) {
						Global.teamsAllowed = Global.cfg.getBoolean("teamsAllowed");
						
						Settings.openWelcomeInventory((Player) e.getWhoClicked());
						return;
					}
					
					if(Utils.checkForItemStackTitle(i, "Tode")) {
						Global.deathsUntilLose = ConfigManager.cfg.getInt("deathsUntilLose");
						
						Settings.openWelcomeInventory((Player) e.getWhoClicked());
						return;
					}
					
					
				}
			}
			
		} else if (e.getInventory().getName().equalsIgnoreCase("§eIngame")) {
			e.setCancelled(true);
			Player p = (Player) e.getWhoClicked();
	    	if (e.getClickedInventory().getName().equals("§eIngame")) {
	    	      e.setCancelled(true);
	    	      if (e.getCurrentItem().getType() == Material.SKULL_ITEM)
	    	      {
	    	        String playername = e.getCurrentItem().getItemMeta().getDisplayName().replace("§e", "");
	    	        Player t = Bukkit.getPlayer(playername);
	    	        p.teleport(t.getLocation());
	    	        p.playSound(p.getLocation(), Sound.ENDERMAN_TELEPORT, 5.0f, 5.0f);
	    	      }
			}
		} else if (e.getInventory().getName().equalsIgnoreCase("§eErfolge")) {
			e.setCancelled(true);
		}

	}
	
	@EventHandler
	public void onClose(InventoryCloseEvent e) {
		if(((Player) e.getPlayer()) == Global.editor) {
			Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
				@Override
				public void run() {
					if(!e.getPlayer().getOpenInventory().getTitle().contains("Einstellungen")) {
						Global.editor = null;
					}
				}
			}, 20L);
		}
	}
	
}
