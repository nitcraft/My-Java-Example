package me.nitcraft.listeners;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import de.slimeplex.plexutils.system.base.objects.ItemBuilder;
import me.nitcraft.luckyrace.GameState;
import me.nitcraft.luckyrace.Global;
import me.nitcraft.luckyrace.LuckyRace;
import me.nitcraft.utils.Creator;
import me.nitcraft.utils.Factory;
import me.nitcraft.utils.Utils;
import me.nitcraft.utils.Var;

public class JoinListener implements Listener {

	private LuckyRace plugin;
	
	public static ArrayList<Integer> Race = new ArrayList<>();
	public static ArrayList<Integer> GermanLetsPlay = new ArrayList<>();
	
	public static ArrayList<Player> VoteRace = new ArrayList<>();
	public static ArrayList<Player> VoteGLP = new ArrayList<>(); 


	public JoinListener(LuckyRace plugin) {
		this.plugin = plugin;
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}

	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		Player p = e.getPlayer();
	//	p.teleport(Factory.getConfigLocation("LR.LS", Var.cfg));

	//	String url = "https://download.nodecdn.net/containers/nodecraft/minepack/5f2657ce650634f452a06ad39fa0a511.zip";
		//e.getPlayer().setResourcePack(url);
		p.teleport(Factory.getConfigLocation("SkyWars.Lobby", Var.cfg));
		if (GameState.isState(GameState.LOBBY)) {
			e.setJoinMessage("§a\u2771\u2771 §8" + p.getName());
			p.teleport(Factory.getConfigLocation("SkyWars.Lobby", Var.cfg));
			Utils.reset(p);
			p.playSound(p.getLocation(), Sound.EAT, 10.0f, 10.0f);
	
	

			Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {

				@Override
				public void run() {
					ItemStack slime = new ItemStack(Material.SLIME_BALL, 1);
					ItemMeta meta = slime.getItemMeta();
					ArrayList<String> lore = new ArrayList<>();
					lore.add("§n§7Rechtsklick zum Benutzen");
					meta.setLore(lore);

					meta.setDisplayName("§aRunde verlassen!");
					slime.setItemMeta(meta);
					
					ItemStack vote = new ItemStack(Material.PAPER, 1);
					ItemMeta meta1 = vote.getItemMeta();
					ArrayList<String> lore1 = new ArrayList<>();
					lore1.add("§n§7Vote für eine Map");
					meta1.setLore(lore1);

					meta1.setDisplayName("§aMap-Voting");
					vote.setItemMeta(meta1);
					
				
					
					p.getInventory().setItem(8, slime);
					p.getInventory().setItem(1, vote);
					p.getInventory().setItem(0, Creator.itemcreator(Material.NETHER_STAR, "§aErfolge", 1, (short) 0, null));

					List<String> lore11 = new ArrayList<String>();
					ItemStack itemToOpen = new ItemStack(Material.REDSTONE_COMPARATOR);
					ItemMeta itemToOpenMeta = itemToOpen.getItemMeta();
					itemToOpenMeta.setDisplayName("§7Einstellungen");
					lore11.add("§aRechtsklicken, um die Einstellungen zu öffnen.");
					itemToOpenMeta.setLore(lore11);
					itemToOpen.setItemMeta(itemToOpenMeta);
					lore11.clear();
					if (p.hasPermission("lr.settings")) {
					p.getInventory().setItem(4, itemToOpen);
					}
				}

			}, 1L);
			p.teleport(Factory.getConfigLocation("SkyWars.Lobby", Var.cfg));

			Utils.checkForNewMatch(p);
			p.teleport(Factory.getConfigLocation("SkyWars.Lobby", Var.cfg));
		} else if (GameState.isState(GameState.INGAME)) {
			p.teleport(Factory.getConfigLocation("Jump.Spawn", Var.cfg));
			p.setAllowFlight(true);
			p.sendMessage(Var.pr + "§7Du bist nun im Zuschauer-Modus");
			Utils.addSpec(p);
			e.setJoinMessage(null);


		} else if (GameState.isState(GameState.PVP)) {
			p.teleport(Factory.getConfigLocation("End.Spawn", Var.cfg));
			p.setAllowFlight(true);
			p.sendMessage(Var.pr + "§7Du bist nun im Zuschauer-Modus");
			Utils.addSpec(p);
			e.setJoinMessage(null);

		}

	
	}
	
	
			
			
		
	@EventHandler
	public void Interact2(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		if(e.getItem() == null) return;
		if(e.getItem().hasItemMeta()){
		    if(e.getItem().getItemMeta().hasDisplayName()){
		if (e.getItem().getItemMeta().getDisplayName().equalsIgnoreCase("§aMap-Voting")) { 
			if (Global.lobbyCountInt < 11) {
				p.sendMessage(Global.prefix + "§cDie Map-Voting Phase ist beendet!");
				
			}  else {
			
			Inventory inv = Bukkit.createInventory(null, InventoryType.CHEST, "§eMap-Voting");
			
		    inv.setItem(0, Creator.itemcreator(Material.STAINED_GLASS_PANE, "", 1, (short) 0, null));
     	    inv.setItem(1, Creator.itemcreator(Material.STAINED_GLASS_PANE, "", 1, (short) 0, null));
			inv.setItem(2, Creator.itemcreator(Material.STAINED_GLASS_PANE, "", 1, (short) 0, null));
			inv.setItem(3, Creator.itemcreator(Material.STAINED_GLASS_PANE, "", 1, (short) 0, null));
			inv.setItem(4, Creator.itemcreator(Material.STAINED_GLASS_PANE, "", 1, (short) 0, null));
			inv.setItem(5, Creator.itemcreator(Material.STAINED_GLASS_PANE, "", 1, (short) 0, null));
			inv.setItem(6, Creator.itemcreator(Material.STAINED_GLASS_PANE, "", 1, (short) 0, null));
			inv.setItem(7, Creator.itemcreator(Material.STAINED_GLASS_PANE, "", 1, (short) 0, null));
			inv.setItem(8, Creator.itemcreator(Material.STAINED_GLASS_PANE, "", 1, (short) 0, null));
			inv.setItem(9, Creator.itemcreator(Material.STAINED_GLASS_PANE, "", 1, (short) 0, null));
			inv.setItem(17, Creator.itemcreator(Material.STAINED_GLASS_PANE, "", 1, (short) 0, null));
			inv.setItem(18, Creator.itemcreator(Material.STAINED_GLASS_PANE, "", 1, (short) 0, null));
			inv.setItem(19, Creator.itemcreator(Material.STAINED_GLASS_PANE, "", 1, (short) 0, null));
			inv.setItem(20, Creator.itemcreator(Material.STAINED_GLASS_PANE, "", 1, (short) 0, null));
			inv.setItem(21, Creator.itemcreator(Material.STAINED_GLASS_PANE, "", 1, (short) 0, null));
			inv.setItem(22, Creator.itemcreator(Material.STAINED_GLASS_PANE, "", 1, (short) 0, null));
			inv.setItem(23, Creator.itemcreator(Material.STAINED_GLASS_PANE, "", 1, (short) 0, null));
			inv.setItem(24, Creator.itemcreator(Material.STAINED_GLASS_PANE, "", 1, (short) 0, null));
			inv.setItem(25, Creator.itemcreator(Material.STAINED_GLASS_PANE, "", 1, (short) 0, null));
     		inv.setItem(26, Creator.itemcreator(Material.STAINED_GLASS_PANE, "", 1, (short) 0, null));
	
		    inv.setItem(10, new ItemBuilder(Material.PAPER).setDisplayName("§6Map: §bFrühling").addLore("§7Stimmen: " + Race.size()).build());
		    inv.setItem(11, new ItemBuilder(Material.PAPER).setDisplayName("§6Map: §bTest").addLore("§7Stimmen: " + GermanLetsPlay.size()).build());

			p.openInventory(inv);
	
			}
		}
		    }
	}
	
}

		
		@EventHandler
		public void Click(InventoryClickEvent e) {
			Player p = (Player) e.getWhoClicked();
			if (GameState.isState(GameState.LOBBY)) {
				if(e.getCurrentItem() == null) {
					return;
				}
				
				if(e.getCurrentItem().hasItemMeta()){
				    if(e.getCurrentItem().getItemMeta().hasDisplayName()){
			if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§6Map: §bFrühling")) {
			if (VoteRace.contains(p) || VoteGLP.contains(p)) {
				p.closeInventory();
				p.sendMessage(Global.prefix + "§cDu hast bereits für eine Map gevotet!");
				p.playSound(p.getLocation(), Sound.ANVIL_BREAK, 10.0f, 10.0f);
			} else {
				Race.add(1);
				VoteRace.add(p);
				p.closeInventory();
				p.sendMessage(Global.prefix + "§7Du hast für die Deathmatch Map §bFrühling §agevotet!");
				p.playSound(p.getLocation(), Sound.CLICK, 10.0f, 10.0f);
			}
				
			} else if(e.getCurrentItem().hasItemMeta()){
			    if(e.getCurrentItem().getItemMeta().hasDisplayName()){ 
			    	if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§6Map: §bTest")) {
				if (VoteGLP.contains(p) || VoteRace.contains(p)) {
					p.closeInventory();
					p.sendMessage(Global.prefix + "§cDu hast bereits für eine Map gevotet!");
					p.playSound(p.getLocation(), Sound.ANVIL_BREAK, 10.0f, 10.0f);
				} else {
					GermanLetsPlay.add(1);
					VoteGLP.add(p);
					p.closeInventory();
					p.sendMessage(Global.prefix + "§7Du hast für die Deathmatch Map §bTest §agevotet!");
					p.playSound(p.getLocation(), Sound.CLICK, 10.0f, 10.0f);
				}
			    	}
			    }
			}
				    }
			}
		}	
		}
		
}
