package me.nitcraft.listeners;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.FireworkEffect;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

import de.slimeplex.nitcore.utils.objects.NitUser;
import me.nitcraft.constructor.Respawn;
import me.nitcraft.luckyrace.GameState;
import me.nitcraft.luckyrace.Global;
import me.nitcraft.luckyrace.LuckyRace;
import me.nitcraft.luckyrace.LuckyRaceSection;
import me.nitcraft.luckyrace.LuckyRaceSection.Achievement;
import me.nitcraft.stats.Stats;
import me.nitcraft.utils.Utils;
import me.nitcraft.utils.Var;
import net.minecraft.server.v1_8_R3.PacketPlayOutNamedSoundEffect;


public class DeathListener implements Listener {

	
	private LuckyRace plugin;
	Utils u = new Utils();

	public DeathListener(LuckyRace plugin) {
		this.plugin = plugin;
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}

	int i;
	int cd;
	int cd2;
	int cd7;
	int cdc;

	public static HashMap<Player, Inventory> Inventory;

	public static int count = 6;
	public int countdown;

	public static int high = 61;
	public int countdown2;

	static String Winner;

	@SuppressWarnings("deprecation")
	@EventHandler
	public void onDeath(EntityDamageEvent e) {
		if (e.getEntity() instanceof Player) {
			if (!Global.activePlayers.contains(e.getEntity())) {
				e.setCancelled(true);
			}
		Player pa = (Player) e.getEntity();
		Player k = pa.getKiller();
		//Bukkit.broadcastMessage(Global.activePlayers + "");
		if ((GameState.isState(GameState.PVP)) && (pa.getLocation().getWorld().equals(Global.pvp))) {
			if (!Global.activePlayers.contains(pa)) {
				e.setCancelled(true);
				e.setDamage(0);
			}
			if (pa.getHealth() - e.getDamage() < 0D) {
//				if (k.getHealth() == 0.5D) {
//					NitUser user = new NitUser(k.getUniqueId().toString());
//					LuckyRaceSection section = (LuckyRaceSection) user.getSection(new LuckyRaceSection(k));
//					if (!section.hasAchievement(Achievement.FIRST_HEART)) {
//						section.addAchievement(Achievement.FIRST_HEART);
//						user.save();
//
//					}
//				}
				Global.activePlayers.remove(pa);

				e.setCancelled(true);
				pa.teleport(pa.getLocation().add(0, 5, 0));
				pa.setAllowFlight(true);
				pa.setFlying(true);
				PacketPlayOutNamedSoundEffect packet = new PacketPlayOutNamedSoundEffect("game.player.hurt", pa.getLocation().getX(), pa.getLocation().getY(), pa.getLocation().getZ(), 1F, 1F);
	     		Bukkit.getOnlinePlayers().forEach(all -> {
	     		((CraftPlayer) all).getHandle().playerConnection.sendPacket(packet);
	     		});
//	     		PacketPlayOutEntityDestroy packet11 = new PacketPlayOutEntityDestroy(10);
//	     		Bukkit.getOnlinePlayers().forEach(all -> {
//	     		((CraftPlayer) all).getHandle().playerConnection.sendPacket(packet11);
//	     		});
//	     		
	     		
				Bukkit.broadcastMessage(pa.getHealth() - e.getDamage() + "SHerzen - Schaden");
				pa.getWorld().strikeLightningEffect(pa.getLocation());
				pa.setHealth(pa.getMaxHealth());
				for (Player all : Bukkit.getOnlinePlayers()) {
					all.hidePlayer(pa);
				}
				if (Stats.deaths.get(pa) != Global.deathsUntilLose -1) {
				Bukkit.broadcastMessage(" ");
						if (k == null) {
							Bukkit.broadcastMessage(
									Var.pr + "§7Der Spieler §b" + e.getEntity().getName() + "§7 ist gestorben!");
						} else {
							if (Stats.kills.get(k) == null)
								Stats.kills.put(k, 0);
							Stats.kills.put(k, Integer.valueOf(Stats.kills.get(k).intValue() + 1));

							k.playSound(k.getLocation(), Sound.LEVEL_UP, 1, 1);
							k.sendMessage(Var.pr + "§7+ §b50 §7Coins");
							k.addPotionEffect(
									new PotionEffect(org.bukkit.potion.PotionEffectType.REGENERATION, 100, 3));

							// new CoinsManager(e.getEntity().getKiller()).addCoins(50);

							Bukkit.broadcastMessage(Var.pr + "§7Der Spieler §b" + e.getEntity().getName()
									+ "§7 wurde von §b" + k.getName() + "§7 getötet!");

						}

						if (Stats.deaths.get(pa) == null)
							Stats.deaths.put(pa, Integer.valueOf(0));
						else {
							Stats.deaths.put(pa, Integer.valueOf(Stats.deaths.get(pa).intValue() + 1));
						}
						int lives = Global.deathsUntilLose - Stats.deaths.get(pa);
						u.broadcast(Global.prefix + "§6" + pa.getName() + "§b hat nur noch §6" + lives + "§b Leben.");
						for (Player all : Bukkit.getOnlinePlayers()) {
							all.hidePlayer(pa);
						}
						new Respawn(pa);
						Utils.resetScoreBoard(pa);

						Utils.playFirework(pa, pa.getLocation(), org.bukkit.Color.AQUA, org.bukkit.Color.AQUA,
								FireworkEffect.Type.CREEPER);

				} else if (Stats.deaths.get(pa) == 2) {
					Global.activePlayers.remove(pa);
					Global.spectators.add(pa);
					Utils.resetScoreBoard(pa);
					for (Player all : Bukkit.getOnlinePlayers()) {
						all.hidePlayer(pa);
						Global.activePlayers.remove(pa);
//						if (Global.activePlayers.size() == 1) {
//							// ScoreboardManager.updateIndividual(all, "#players", "§b0");
//						} else {
//							// ScoreboardManager.updateIndividual(all, "#players", "§b" +
//							// Global.activePlayers.size());
//						}
						all.updateInventory();
					}

					// ScoreboardManager.setSpecBoard(pa);
					u.broadcast(Global.prefix + "§6" + pa.getName() + "§b ist mit " + Global.deathsUntilLose + " Toden ausgeschieden.");
					if (Global.activePlayers.size() == 1) {
						GameState.setState(GameState.LOBBY);
						u.setWinner(Global.activePlayers.get(0));
						NitUser user = new NitUser(Global.activePlayers.get(0).getUniqueId().toString());
						LuckyRaceSection section = (LuckyRaceSection) user.getSection(new LuckyRaceSection(Global.activePlayers.get(0)));
						if (!section.hasAchievement(Achievement.FIRST_WIN)) {
							section.addAchievement(Achievement.FIRST_WIN);
							user.save();

						}
					} else {
						pa.getLocation().getBlock().setType(Material.SPONGE);
						ArrayList<ItemStack> drops = new ArrayList<ItemStack>();

						for (int i = 0; i < pa.getInventory().getContents().length; i++) {
							drops.add(pa.getInventory().getContents()[i]);
						}

						for (int i = 0; i < pa.getInventory().getArmorContents().length; i++) {
							drops.add(pa.getInventory().getArmorContents()[i]);
						}
						Global.lootByKillMap.put(pa.getLocation().getBlock(), drops);
					}
					Utils.addSpec(pa);


				}

				Utils.sendScoreboard();
			}
		}
		}
	}

	@EventHandler
	public void damageByPlayer(EntityDamageByEntityEvent e) {
		if (e.getDamager() instanceof Player) {
		Player p = (Player) e.getDamager();
		if (!Global.activePlayers.contains(p)) {
			e.setCancelled(true);
			e.setDamage(0);

			
		}
		if (!Global.activePlayers.contains(e.getEntity())) {
			e.setCancelled(true);
			e.setDamage(0);
		}
		}
	}
	@EventHandler
	public void death(PlayerDeathEvent e) {
		e.setDeathMessage(null);
		if (GameState.isState(GameState.INGAME)) {
			if (e.getEntity() instanceof Player) {
			Bukkit.getScheduler().scheduleSyncDelayedTask(LuckyRace.getInstance(), new Runnable() {
				@Override
				public void run() {
				e.getEntity().spigot().respawn();
				}
			}, 5);
			}
		}
	}
	@EventHandler
	public void onRe(PlayerRespawnEvent e) {
		if (!GameState.isState(GameState.INGAME)) {
		Bukkit.getScheduler().scheduleSyncDelayedTask(LuckyRace.getInstance(), new Runnable() {
			@Override
			public void run() {
				Utils.addSpec(e.getPlayer());
				
			}
		}, 5);
		} else if (GameState.isState(GameState.INGAME)) {
			Bukkit.getScheduler().scheduleSyncDelayedTask(LuckyRace.getInstance(), new Runnable() {
				@Override
				public void run() {
					e.getPlayer().teleport(Global.checkPoints.get(e.getPlayer()));
					e.setRespawnLocation(Global.checkPoints.get(e.getPlayer()));
				}
			}, 5);

		}
	}

	@EventHandler
	public void onItem(PlayerPickupItemEvent e) {
		if (!(GameState.isState(GameState.LOBBY))) {
			if (Global.activePlayers.contains(e.getPlayer())) {
				e.setCancelled(false);
			}
		} else {
			e.setCancelled(true);
		}
	}

	@EventHandler
	public void onDr(PlayerDropItemEvent e) {
		if (!(GameState.isState(GameState.LOBBY))) {
			if (Global.activePlayers.contains(e.getPlayer())) {
				e.setCancelled(false);
			}
		} else {
			e.setCancelled(true);
		}
	}
}
