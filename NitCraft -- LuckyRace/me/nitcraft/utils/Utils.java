package me.nitcraft.utils;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import org.apache.commons.io.FileUtils;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.DyeColor;
import org.bukkit.FireworkEffect;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.messaging.PluginMessageListener;
import org.bukkit.potion.PotionEffect;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.scoreboard.Team;
import org.bukkit.util.Vector;

import de.slimeplex.nitcore.utils.objects.NitUser;
import de.slimeplex.plexutils.system.base.objects.ItemBuilder;
import me.nitcraft.commands.BuildCommand;
import me.nitcraft.listeners.JoinListener;
import me.nitcraft.luckyrace.GameState;
import me.nitcraft.luckyrace.Global;
import me.nitcraft.luckyrace.LuckyRace;
import me.nitcraft.luckyrace.LuckyRaceSection;
import me.nitcraft.luckyrace.LuckyRaceSection.Achievement;
import me.nitcraft.schematic.SchematicManager;
import me.nitcraft.stats.Stats;
import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.PacketPlayOutChat;

public class Utils implements PluginMessageListener {

	public static ArrayList<Player> spec = new ArrayList<Player>();
	public static ArrayList<Player> activePlayers;
	public static ArrayList<Player> spectators;

	public static int protection = 6;
	public static int prostart;
	public static ArrayList<Player> move = new ArrayList<Player>();

	public static HashMap<UUID, Integer> deaths = new HashMap<>();
	public static HashMap<UUID, Integer> BoostUses = new HashMap<>();
	public static int high = 4;
	static int countdown;
	
	public static void fallDeath(Player p) {
		if (!BoostUses.containsKey(p.getUniqueId())) {

	if (!deaths.containsKey(p.getUniqueId())) {
		deaths.put(p.getUniqueId(), 1);
	} else {
	deaths.put(p.getUniqueId(), deaths.get(p.getUniqueId()) + 1);
	}
	Bukkit.broadcastMessage(deaths.get(p.getUniqueId()) + "Deaths") ;
	if (deaths.get(p.getUniqueId()) == 1) {
		p.sendTitle("", "§c✖§7✖✖");
	} else if (deaths.get(p.getUniqueId()) == 2) {
		p.sendTitle("", "§c✖✖§7✖");
	} else if (deaths.get(p.getUniqueId()) == 3 || deaths.get(p.getUniqueId()) > 3) {
		p.sendTitle("", "§c✖✖✖");
			BoostUses.put(p.getUniqueId(), 3);
			p.getInventory().addItem(new ItemBuilder(Material.FIREWORK).setDisplayName("§6>> §7Boost - §b" + BoostUses.get(p.getUniqueId()) + "§7 Nutzungen").setGlow().build());
			Bukkit.broadcastMessage("Add");
		}
	}

	}
	
	@Override
	public void onPluginMessageReceived(String arg0, Player arg1, byte[] arg2) {
	}

	public static void playFirework(Player p, Location loc, Color color1, Color color2, FireworkEffect.Type type) {
		loc.add(0.5, 1, 0.5);
		Firework fw = p.getWorld().spawn(loc, Firework.class);
		FireworkMeta fwmeta = fw.getFireworkMeta();
		FireworkEffect.Builder builder = FireworkEffect.builder();

		builder.withFlicker();
		builder.withFade(color2);
		builder.withColor(color1);
		builder.with(type);
		fwmeta.clearEffects();
		Field f;
		try {
			f = fwmeta.getClass().getDeclaredField("power");
			f.setAccessible(true);
			f.set(fwmeta, -1);
		} catch (Exception e) {
			return;
		}
		fwmeta.addEffect(builder.build());
		fw.setFireworkMeta(fwmeta);
	}

	public static void connect(Player p, String server, Plugin plugin) {
		try {
			ByteArrayOutputStream b = new ByteArrayOutputStream();
			DataOutputStream out = new DataOutputStream(b);

			out.writeUTF("Connect");
			out.writeUTF(server);
			p.sendPluginMessage(plugin, "BungeeCord", b.toByteArray());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void addSpec(Player p) {
		p.setGameMode(GameMode.ADVENTURE);
		
		Global.spectators.add(p);
		p.getInventory().clear();
		p.spigot().setCollidesWithEntities(false);
		p.getInventory().setItem(0, Creator.itemcreator(Material.COMPASS, "§6Teleporter", 1, (short) 0, null));
		p.getInventory().setItem(1, Creator.itemcreator(Material.NETHER_STAR, "§aAchievements", 1, (short) 0, null));
		p.getInventory().setItem(8, Creator.itemcreator(Material.SLIME_BALL, "§4§lVerlassen", 1, (short) 0, null));
		Vector v = p.getVelocity().setX(3);
		p.setVelocity(v);
		p.setAllowFlight(true);
		p.setFlying(true);

		for (Player all : Global.activePlayers) {
			all.hidePlayer(p);
		}
		Bukkit.getScheduler().runTaskLater(Global.instance, new  Runnable() {
			
			@Override
			public void run() {

				if (GameState.isState(GameState.INGAME)) {
					p.teleport(Factory.getConfigLocation("Jump.Spawn", Var.cfg));

				} else if (GameState.isState(GameState.PVP)) {
					p.teleport(Factory.getConfigLocation("End.Spawn", Var.cfg));

				}	
				p.setAllowFlight(true);
				p.setFlying(true);

			}
		}, 10);
	}
	
//	public static void addSpec2(Player p) {
//		p.setGameMode(GameMode.ADVENTURE);
//		Global.spectators.add(p);
//		p.spigot().setCollidesWithEntities(false);
//		
//		
//		Vector v = p.getLocation().getDirection().setY(20.0);
//		p.setVelocity(v);
//		p.setAllowFlight(true);
//		p.setFlying(true);
//
//		for (Player all : Global.activePlayers) {
//			all.hidePlayer(p);
//		}
//		
//
//		p.playSound(p.getLocation(), Sound.WITHER_SPAWN, 3.0f, 2.0f);
//		
//		
//		
//		high = 4;
//		countdown = Bukkit.getScheduler().scheduleSyncRepeatingTask(Global.instance, new Runnable() {
//			@Override
//			public void run() {
//					if (high != 0) {
//						high--;
//
//						if (high == 3 || (high == 2) || (high == 1)) {
//								p.sendTitle("§cWieder eintritt in", "§a" + high);
//
//								p.playSound(p.getLocation(), Sound.CLICK, 10.0F, 10.0F);
//
//							}
//					} else {
//							Utils.MapVoting2(p);
//							Bukkit.getScheduler().cancelTask(countdown);
//							p.setAllowFlight(false);
//							p.setFlying(false);
//							Global.spectators.remove(p);
//							for (Player all : Global.activePlayers) {
//								all.showPlayer(p);
//							}
//							p.playSound(p.getLocation(), Sound.WITHER_SHOOT, 10.0F, 5.0F);
//							Utils.MapVoting2(p);
//
//						} 
//
//			}
//		}, 0L, 20L);
//
//
//	}


	public static void reset(Player p) {
		p.getInventory().clear();
		p.setHealth(p.getMaxHealth());
		p.setFoodLevel(20);
		p.setSaturation(20.0F);
		p.setGameMode(GameMode.SURVIVAL);
		p.setExp(0.0F);
		p.setLevel(0);
		p.getInventory().setArmorContents(null);
		p.getScoreboard().clearSlot(DisplaySlot.SIDEBAR);
		p.setMaxHealth(20);
		for (PotionEffect pot : p.getActivePotionEffects()) {
			p.removePotionEffect(pot.getType());
		}
	}

	public void broadcast(String message) {
		for (Player all : Bukkit.getOnlinePlayers()) {
			all.sendMessage(message);
		}
	}

	public static void moveAllToWorld(World world) {
		for (Player all : Bukkit.getOnlinePlayers()) {
			all.teleport(world.getSpawnLocation());
		}
	}

	public static void resetScoreBoard(Player p) {
		p.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
	}

	public void setWinner(Player p) {

		Global.spectators.clear();
		for (Player all : Bukkit.getOnlinePlayers()) {
			if (!Global.activePlayers.contains(all)) {
				Global.activePlayers.add(all);
			}
		}
		Utils.showAllForAll();
		Bukkit.getScheduler().cancelTask(Global.compassTask);
		Bukkit.getScheduler().cancelTask(Global.deathMatchCount);
		Bukkit.getScheduler().scheduleSyncDelayedTask(Global.instance, new Runnable() {

			@Override
			public void run() {

				for (int i = 0; i < 60; i++)
					broadcast("");
				broadcast(Global.prefix + "§k2243522095239762023723693262967230967239693207633");
				broadcast(Global.prefix + "§6                                                 ");
				broadcast(Global.prefix + "§6Der Spieler §e" + p.getName() + "§a Gewinnt!     ");
				broadcast(Global.prefix + "§a           ⒷⒺⓁⓄⒽⓃⓊⓃⒼ§7: §6200 Coins!            ");
				broadcast(Global.prefix + "§6                                                 ");
				broadcast(Global.prefix + "§k2243522095239762023723693262967230967239693207633");
				// new CoinsManager(p).addCoins(200);

				broadcast("");
				broadcast("§a======§6STATS§a======");
				for (Player all : Bukkit.getOnlinePlayers()) {
					reset(all);
					int Deaths = 0;
					int Kills = 0;
					if (Stats.deaths.get(all) != null) {
						Deaths = Stats.deaths.get(all).intValue();
					}
					if (Stats.kills.get(all) != null) {
						Kills = Stats.kills.get(all).intValue();
					}

					all.sendMessage("§cDeaths:     §7" + Deaths);
					all.sendMessage("§cKills:     §7" + Kills);

					// StatsManager.addValue(all.getUniqueId().toString(), "PlayedGames", 1);
					// StatsManager.addValue(all.getUniqueId().toString(), "Kills", Kills);
					// StatsManager.addValue(all.getUniqueId().toString(), "Deaths", Deaths);

					// if (Var.breakedBlocks.containsKey(all)) {
					// StatsManager.addValue(all.getUniqueId().toString(), "BreakedBlocks",
					// Var.breakedBlocks.get(all).intValue());
					//
					// }

					String finished = "§4Nein";
					if ((Stats.finishedJumpRun.containsKey(all)) && (Stats.finishedJumpRun.get(all).booleanValue())) {
						finished = "§2Ja";

					}

					all.sendMessage("§cJump and Run beendet: " + finished);
					all.teleport(Factory.getConfigLocation("SkyWars.Lobby", Var.cfg));
				}

				broadcast("");

				GameState.setState(GameState.RESTART);
				Global.countAfterWin = Bukkit.getScheduler().scheduleSyncRepeatingTask(Global.instance, new Runnable() {

					@Override
					public void run() {
						if (Global.countNumbers.contains(Global.countAfterWinInt)) {
							broadcast(Global.prefix + "Server startet in §b" + Global.countAfterWinInt
									+ "§7 Sekunden neu!");
						}
						Global.countAfterWinInt--;
						if (Global.countAfterWinInt == 0) {
							Bukkit.getScheduler().cancelTask(Global.countAfterWin);
							for (Player all : Bukkit.getOnlinePlayers()) {
								all.kickPlayer("");
							}
							Bukkit.getScheduler().scheduleSyncDelayedTask(LuckyRace.getInstance(),
									() -> Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "restart"), 5 * 20L);
						}
					}

				}, 0L, 20L);
			}

		}, 20L);

	}

	public static World resetWorld(File backup, File old, String worldname) {
		for (Player all : Bukkit.getOnlinePlayers()) {
			all.teleport(Factory.getConfigLocation("SkyWars.Lobby", Var.cfg));
		}
		Bukkit.getServer().unloadWorld(worldname, true);
		try {
			FileUtils.deleteDirectory(old);
		} catch (IOException e) {
			e.printStackTrace();
		}

		if (!old.exists()) {
			try {
				FileUtils.copyDirectory(backup, old);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		World w = Bukkit.getServer().createWorld(new WorldCreator(worldname));
		return w;
	}

	@SuppressWarnings("deprecation")
	public static void sendScoreboard() {
		ScoreboardManager sm = Bukkit.getScoreboardManager();
		Scoreboard board = sm.getNewScoreboard();
		Objective score = board.registerNewObjective("aaa", "bbb");

		score.setDisplayName("§5LuckyRace");
		score.setDisplaySlot(DisplaySlot.SIDEBAR);
		score.getScore("  ").setScore(97);

		Team op = board.registerNewTeam("ops");
		Team players = board.registerNewTeam("players");

		Team team = score.getScoreboard().registerNewTeam("team");
		int time = Global.deathMatchCountInt;
		int minutes = time / 60;
		int seconds = time % 60;
		String disMinu = (minutes < 10 ? "0" : "") + minutes;
		String disSec = (seconds < 10 ? "0" : "") + seconds;
		String formattedTime = disMinu + ":" + disSec;

		team.setPrefix("§6" + formattedTime);
		for (Player all : Bukkit.getOnlinePlayers()) {
			if (Global.activePlayers.contains(all)) {

				players.addPlayer(all);
				score.getScore("§7➤ §bEndet in:").setScore(99);

				team.addEntry("§4");
				score.getScore("§4").setScore(98);

				if (!all.hasPermission("lr.name")) {
					players.addPlayer(Bukkit.getOfflinePlayer(all.getName()));
					players.setPrefix("§a");
				} else {
					op.addPlayer(Bukkit.getOfflinePlayer(all.getName()));
					op.setPrefix("§c");
				}
				if (Stats.deaths.get(all) == null)
					Stats.deaths.put(all, 0);
				int lives = Global.deathsUntilLose - Stats.deaths.get(all);

				score.getScore(all.getName()).setScore(lives);
				all.setScoreboard(board);
			}
		}

	}

	public static void updateScoreboard() {
		int time = Global.deathMatchCountInt;
		int minutes = time / 60;
		int seconds = time % 60;
		String disMinu = (minutes < 10 ? "0" : "") + minutes;
		String disSec = (seconds < 10 ? "0" : "") + seconds;
		String formattedTime = disMinu + ":" + disSec;

		for (Player all : Global.activePlayers) {
			all.getScoreboard().getTeam("team").setPrefix("§6" + formattedTime);
		}
	}

	public static void checkForNewMatch(Player p) {

		Global.activePlayers.clear();
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

		for (Player all : Bukkit.getOnlinePlayers()) {
				all.showPlayer(p);
		}

		Utils u = new Utils();

		if (Bukkit.getOnlinePlayers().size() >= Global.minPlayers) {

			if (!Global.lobbyCountBoolean) {
				for (Player all : Bukkit.getOnlinePlayers()) {
					Utils.reset(all);
					all.getInventory().setItem(8, slime);
					all.getInventory().setItem(1, vote);
					all.getInventory().setItem(0, Creator.itemcreator(Material.NETHER_STAR, "§aErfolge", 1, (short) 0, null));

						ItemStack itemToOpen = new ItemStack(Material.REDSTONE_COMPARATOR);
						ItemMeta itemToOpenMeta = itemToOpen.getItemMeta();
						itemToOpenMeta.setDisplayName("§7Einstellungen");
						itemToOpenMeta.setLore(lore1);
						itemToOpen.setItemMeta(itemToOpenMeta);

						if (p.hasPermission("lr.settings")) {
						p.getInventory().setItem(4, itemToOpen);
						}
				}
				Global.lobbyCount = Bukkit.getScheduler().scheduleSyncRepeatingTask(Global.instance, new Runnable() {
					@Override
					@SuppressWarnings("deprecation")
					public void run() {
						if (Bukkit.getOnlinePlayers().size() >= Global.minPlayers) {
							Global.lobbyCountInt -= 1;
							if (Global.countNumbers.contains(Integer.valueOf(Global.lobbyCountInt))) {
								u.broadcast(
										Global.prefix + "Spiel startet in §6" + Global.lobbyCountInt + " §bSekunden");
								for (Player all : Bukkit.getOnlinePlayers()) {
									all.playSound(all.getLocation(), Sound.NOTE_BASS_GUITAR, 2, 2);
								}
								if (Global.lobbyCountInt == 5) {
									String s = "§cTeams verboten!";
									if (Global.teamsAllowed)
										s = "§aTeams erlaubt!";
									u.broadcast("");
									u.broadcast("");
									u.broadcast("");
									u.broadcast(s);
									u.broadcast("");
									u.broadcast("");
									u.broadcast("");
									for (Player all : Bukkit.getOnlinePlayers()) {
										all.sendTitle("§eLuckyRace", "§6ＮｉｔＣｒａｆｔ．ｄｅ");
									}
								}
							}
							for (Player all : Bukkit.getOnlinePlayers()) {

								all.setLevel(Global.lobbyCountInt);
								float i = Global.lobbyCountInt;
								float a = Global.defaultLobbyCountInt;
								all.setExp(i / a);

							}
							if (Global.lobbyCountInt == 0) {

								for (Player all : Bukkit.getOnlinePlayers()) {
									all.playSound(all.getLocation(), Sound.LEVEL_UP, 2, 2);
								}

								if (Bukkit.getOnlinePlayers().size() >= Global.minPlayers) {
									if (BuildCommand.cfg.getConfigurationSection("spawns") != null) {

										if (!(BuildCommand.cfg.getConfigurationSection("spawns").getKeys(false)
												.toArray().length < Bukkit.getOnlinePlayers().size())) {
											Integer x = Global.cfg.getInt("mitteLocation.x");
											Integer y = Global.cfg.getInt("mitteLocation.y");
											Integer z = Global.cfg.getInt("mitteLocation.z");
											if (x != null && y != null && z != null) {

												Global.compassTask = Bukkit.getScheduler()
														.scheduleSyncRepeatingTask(Global.instance, new Runnable() {

															@Override
															public void run() {
																updateCompases(Global.activePlayers);
															}

														}, 0L, 20L);
												GameState.setState(GameState.INGAME);
										      //  NitCore.getServerManager().setMaxPlayers(12);

												Utils.moveAllToWorld(Global.world);
												for (Player all : Bukkit.getOnlinePlayers()) {
													Utils.reset(all);
													Global.activePlayers.add(all);
													Global.checkPointNumber.put(all, 0);
													SchematicManager.playerActionBar.remove(all);
													// StatsManager.addPlayed(all.getUniqueId().toString());
												}
												
												Utils.setStartItems();
												Utils.moveAllToSpawn();
												NitUser user = new NitUser(p.getUniqueId().toString());
												LuckyRaceSection section = (LuckyRaceSection) user.getSection(new LuckyRaceSection(p));
												if (!section.hasAchievement(Achievement.FIRST_ROUND)) {
													section.addAchievement(Achievement.FIRST_ROUND);
													user.save();

												}
												Utils.sendPvpScoreBoard(Global.pvpCountInt);
												Global.pvpCount = Bukkit.getScheduler()
														.scheduleSyncRepeatingTask(Global.instance, new Runnable() {

															@Override
															public void run() {
																Global.pvpCountInt--;
																Utils.updatePvpScoreBoard();
																if (Global.pvpCountInt <= 15) {
																	int time = Global.pvpCountInt;
																	if ((time == 15) || (time == 10) || (time == 5) || (time ==3) || (time == 2) || (time == 1)) {
																		u.broadcast(Global.prefix + "Das Deathmatch beginnt in §b" + time + "§7 Sekunden!");
																		for (Player all : Bukkit.getOnlinePlayers()) {
																			all.playSound(all.getLocation(), Sound.NOTE_PLING, 10.0f, 5.0f);
																		}
																	} else if (time <= 0) {
																		GameState.setState(GameState.PVP);
																		for (Player all : Bukkit.getOnlinePlayers()) {
																			all.getInventory().remove(new ItemBuilder(Material.FIREWORK).setDisplayName("§6>> §7Boost - §b" + Utils.BoostUses.get(all.getUniqueId()) + "§7 Nutzungen").setGlow().build());
																			Utils.BoostUses.remove(all.getUniqueId());
																			Utils.deaths.remove(all.getUniqueId());
																		}
																		MapVoting();
																		for (Player all : Bukkit.getOnlinePlayers()) {
																			all.sendTitle("§a§lLos!", "");

																		}

																		Bukkit.getScheduler().scheduleSyncDelayedTask(
																				Global.instance, new Runnable() {

																					@Override
																					public void run() {
																						String s = "§cTeams verboten!";
																						if (Global.teamsAllowed)
																							s = "§aTeams erlaubt!";
																						u.broadcast("");
																						u.broadcast("");
																						u.broadcast("");
																						u.broadcast(s);
																						u.broadcast("");
																						u.broadcast("");
																						u.broadcast("");
																						startDeathMatch();
																						for (Player all : Bukkit
																								.getOnlinePlayers()) {
																							all.sendTitle(s, "");
																						}
																					}

																				}, 1L);

																		for (Player all : Global.activePlayers) {
																			Utils.resetScoreBoard(all);
																		}

																		Bukkit.getScheduler()
																				.cancelTask(Global.pvpCount);
																	}
																}
															}

														}, 0L, 20L);
											} else {
												Bukkit.getScheduler().cancelTask(Global.lobbyCount);
												Global.lobbyCountInt = Global.defaultLobbyCountInt;
												Global.lobbyCountBoolean = false;

												u.broadcast(Global.prefix
														+ "§4FEHLER: §bKeine Mitte für das Ende vom DeathMatch festgesetzt. §7-> /editpvp");
											}
										} else {

											Bukkit.getScheduler().cancelTask(Global.lobbyCount);
											Global.lobbyCountInt = Global.defaultLobbyCountInt;
											Global.lobbyCountBoolean = false;

											u.broadcast(Global.prefix
													+ "§4FEHLER: §bEs gibt nicht genügend Spawns. §c-> Bitte den Admin kontaktieren!");
										}
									} else {
										u.broadcast(Global.prefix
												+ "§4FEHLER: §bEs gibt nicht genügend Spawns. §c-> Bitte den Admin kontaktieren!");
									}
								} else {

									u.broadcast(Global.prefix + "Es sind nicht genug Spieler online. §7["
											+ Bukkit.getOnlinePlayers().size() + "/" + Global.minPlayers + "]");
									for (Player all : Bukkit.getOnlinePlayers()) {
										Utils.reset(all);
									}

								}
								Bukkit.getScheduler().cancelTask(Global.lobbyCount);
								Global.lobbyCountInt = Global.defaultLobbyCountInt;
								Global.lobbyCountBoolean = false;
							}
						} else {
							Bukkit.getScheduler().cancelTask(Global.lobbyCount);
							u.broadcast(Global.prefix + "Es sind nicht genug Spieler online. §7["
									+ Bukkit.getOnlinePlayers().size() + "/" + Global.minPlayers + "]");
							Global.lobbyCountInt = Global.defaultLobbyCountInt;
							Global.lobbyCountBoolean = false;
							for (Player all : Bukkit.getOnlinePlayers()) {
								Utils.reset(all);
							}

						}
					}
				}, 0L, 20L);
				Global.lobbyCountBoolean = true;
			}
		}
	}
	// Global.lobbyCount =
	// Bukkit.getScheduler().scheduleSyncRepeatingTask(Global.instance, new
	// Runnable() {
	// @Override
	// @SuppressWarnings("deprecation")
	// public void run() {
	// if (Bukkit.getOnlinePlayers().size() >= Global.minPlayers) {
	// Global.lobbyCountInt -= 1;
	// if (Global.countNumbers.contains(Integer.valueOf(Global.lobbyCountInt))) {
	// u.broadcast(
	// Global.prefix + "Spiel startet in §b" + Global.lobbyCountInt + "
	// §7Sekunden");
	// for (Player all : Bukkit.getOnlinePlayers()) {
	// all.playSound(all.getLocation(), Sound.NOTE_STICKS, 1F, 0F);
	// }
	//
	// if (Global.lobbyCountInt == 5) {
	// String s = "§cTeams verboten!";
	// if (Global.teamsAllowed)
	// s = "§aTeams erlaubt!";
	// u.broadcast("");
	// u.broadcast("");
	// u.broadcast("");
	// u.broadcast(s);
	// u.broadcast("");
	// u.broadcast("");
	// u.broadcast("");
	// for (Player all : Bukkit.getOnlinePlayers()) {
	// all.sendTitle("§6§lLucky§5§lRace", "§6ＮｉｔＣｒａｆｔ．ｄｅ");
	// }
	// }
	// }
	// for (Player all : Bukkit.getOnlinePlayers()) {
	//
	// all.setLevel(Global.lobbyCountInt);
	// float i = Global.lobbyCountInt;
	// float a = Global.defaultLobbyCountInt;
	// all.setExp(i / a);
	//
	// }
	// if (Global.lobbyCountInt == 0) {
	// for (Player all : Bukkit.getOnlinePlayers()) {
	// all.playSound(all.getLocation(), Sound.LEVEL_UP, 2, 2);
	// }
	//
	// if (Bukkit.getOnlinePlayers().size() >= Global.minPlayers) {
	// if (JoinListener.GermanLetsPlay.size() < JoinListener.Race.size()) {
	// if (BuildCommand.cfg.getConfigurationSection("spawns") != null) {
	//
	// if (!(BuildCommand.cfg.getConfigurationSection("spawns").getKeys(false)
	// .toArray().length < Bukkit.getOnlinePlayers().size())) {
	// Integer x = Global.cfg.getInt("mitteLocation.x");
	// Integer y = Global.cfg.getInt("mitteLocation.y");
	// Integer z = Global.cfg.getInt("mitteLocation.z");
	// if (x != null && y != null && z != null) {
	//
	// Global.compassTask = Bukkit.getScheduler()
	// .scheduleSyncRepeatingTask(Global.instance, new Runnable() {
	//
	// @Override
	// public void run() {
	// updateCompases(Global.activePlayers);
	// }
	//
	// }, 0L, 20L);
	// GameState.setState(GameState.INGAME);
	// for (Player all : Bukkit.getOnlinePlayers()) {
	// Utils.reset(all);
	// Global.activePlayers.add(all);
	// Global.checkPointNumber.put(all, 0);
	//
	// }
	// Utils.setStartItems();
	// Utils.moveAllToWorld(Global.world);
	// Utils.moveAllToSpawn();
	//
	// for (Player all : Bukkit.getOnlinePlayers()) {
	// all.sendTitle("§a§lGO!", "");
	//
	// }
	//
	// Utils.sendPvpScoreBoard(Global.pvpCountInt);
	// Global.pvpCount = Bukkit.getScheduler()
	// .scheduleSyncRepeatingTask(Global.instance, new Runnable() {
	//
	// @Override
	// public void run() {
	// Global.pvpCountInt--;
	// Utils.updatePvpScoreBoard();
	// if (Global.pvpCountInt <= 15) {
	// int time = Global.pvpCountInt;
	// if ((time == 60 || (time == 30) || (time == 15)
	// || (time == 10) || (time == 5))) {
	// u.broadcast(Global.prefix
	// + "Das Deathmatch beginnt in §b"
	// + time + "§7 Sekunden!");
	//
	// } else if (time <= 0) {
	// GameState.setState(GameState.PVP);
	// Utils.MapVoting();
	// }
	//
	// Bukkit.getScheduler().scheduleSyncDelayedTask(
	// Global.instance, new Runnable() {
	//
	// @Override
	// public void run() {
	// String s = "§cTeams verboten!";
	// if (Global.teamsAllowed)
	// s = "§aTeams erlaubt!";
	// u.broadcast("");
	// u.broadcast("");
	// u.broadcast("");
	// u.broadcast(s);
	// u.broadcast("");
	// u.broadcast("");
	// u.broadcast("");
	// startDeathMatch();
	// for (Player all : Bukkit
	// .getOnlinePlayers()) {
	// all.sendTitle(s, "");
	// }
	// }
	//
	// }, 1L);
	//
	// for (Player all : Global.activePlayers) {
	// Utils.resetScoreBoard(all);
	// }
	//
	// Bukkit.getScheduler()
	// .cancelTask(Global.pvpCount);
	// }
	// }
	//
	// }, 0L, 20L);
	// }
	// } else {
	// Bukkit.getScheduler().cancelTask(Global.lobbyCount);
	// Global.lobbyCountInt = Global.defaultLobbyCountInt;
	// Global.lobbyCountBoolean = false;
	//
	// u.broadcast(Global.prefix
	// + "§4FEHLER: §bKeine Mitte für das Ende vom DeathMatch festgesetzt. §7->
	// /editpvp");
	// }
	// } else {
	//
	// Bukkit.getScheduler().cancelTask(Global.lobbyCount);
	// Global.lobbyCountInt = Global.defaultLobbyCountInt;
	// Global.lobbyCountBoolean = false;
	//
	// u.broadcast(Global.prefix
	// + "§4FEHLER: §bEs gibt nicht genügend Spawns. §c-> Bitte den Admin
	// kontaktieren!");
	// }
	// } else {
	// u.broadcast(Global.prefix
	// + "§4FEHLER: §bEs gibt nicht genügend Spawns. §c-> Bitte den Admin
	// kontaktieren!");
	// }
	//
	// } else {
	//
	// u.broadcast(Global.prefix + "Es sind nicht genug Spieler online. §7["
	// + Bukkit.getOnlinePlayers().size() + "/" + Global.minPlayers + "]");
	// for (Player all : Bukkit.getOnlinePlayers()) {
	// Utils.reset(all);
	// }
	//
	// }
	// Bukkit.getScheduler().cancelTask(Global.lobbyCount);
	// Global.lobbyCountInt = Global.defaultLobbyCountInt;
	// Global.lobbyCountBoolean = false;
	// }
	//
	// } else {
	// Bukkit.getScheduler().cancelTask(Global.lobbyCount);
	// u.broadcast(Global.prefix + "Es sind nicht genug Spieler online. §7["
	// + Bukkit.getOnlinePlayers().size() + "/" + Global.minPlayers + "]");
	// Global.lobbyCountInt = Global.defaultLobbyCountInt;
	// Global.lobbyCountBoolean = false;
	// for (Player all : Bukkit.getOnlinePlayers()) {
	// Utils.reset(all);
	// }
	//
	// }
	// }
	//
	// }, 0L, 20L);
	// Global.lobbyCountBoolean = true;
	// }
	// }
	//
	// }

	@SuppressWarnings("deprecation")
	public static void sendPvpScoreBoard(int time) {
		ScoreboardManager sm = Bukkit.getScoreboardManager();
		Scoreboard board = sm.getNewScoreboard();
		Objective score = board.registerNewObjective("aaa", "bbb");

		int minutes = time / 60;
		int seconds = time % 60;
		String disMinu = (minutes < 10 ? "0" : "") + minutes;
		String disSec = (seconds < 10 ? "0" : "") + seconds;
		String formattedTime = disMinu + ":" + disSec;

		Team players = board.registerNewTeam("players");
		players.setPrefix("§a");
		Team timeEntry = board.registerNewTeam("time");
		timeEntry.setPrefix("§6" + formattedTime);

		for (Player all : Global.activePlayers) {

			score.setDisplayName("§7»§6LuckyRace§7«");
			score.setDisplaySlot(DisplaySlot.SIDEBAR);

			players.addPlayer(all);
			score.getScore("§7➤ §bZeit bis PVP:").setScore(99);

			timeEntry.addEntry("§4");
			score.getScore("§4").setScore(98);

			int i = 0;
			if (Global.checkPointNumber.containsKey(all))
				i = Global.checkPointNumber.get(all);
			score.getScore(all.getName()).setScore(i);
			score.getScore("      ").setScore(-1);
			score.getScore(Global.ip.replace("&", "§")).setScore(-2);
			all.setScoreboard(board);
		}
	}

	public static void updatePvpScoreBoard() {
		int time = Global.pvpCountInt;
		int minutes = time / 60;
		int seconds = time % 60;
		String disMinu = (minutes < 10 ? "0" : "") + minutes;
		String disSec = (seconds < 10 ? "0" : "") + seconds;
		String formattedTime = disMinu + ":" + disSec;
		for (Player all : Global.activePlayers) {
			if (all.getScoreboard() == null)
				continue;
			if (all.getScoreboard().getTeam("time") == null)
				continue;
			all.getScoreboard().getTeam("time").setPrefix("§6" + formattedTime);
		}
	}

	public static void addCheckPointForPvpScoreboard(Player p) {
		for (Player all : Global.activePlayers) {
			all.getScoreboard().getObjective("aaa").getScore(p.getName()).setScore(Global.checkPointNumber.get(p));
		}
	}

	public static void moveAllToSpawn() {
		for (int ia = 0; ia < Bukkit.getOnlinePlayers().size(); ia++) {
			String stra = "spawns." + (ia + "") + ".";
			double x = BuildCommand.cfg.getDouble(stra + "x");
			double y = BuildCommand.cfg.getDouble(stra + "y");
			double z = BuildCommand.cfg.getDouble(stra + "z");
			double yaw = BuildCommand.cfg.getDouble(stra + "yaw");
			double pitch = BuildCommand.cfg.getDouble(stra + "pitch");

			Location loca = new Location(Global.world, x, y, z);
			loca.setYaw((float) yaw);
			loca.setPitch((float) pitch);

			Player pa = (Player) Bukkit.getOnlinePlayers().toArray()[ia];
			pa.teleport(loca);
			Global.checkPoints.put(pa, loca);
		}
	}

	public static void MapVoting() {
		if (JoinListener.GermanLetsPlay.size() > JoinListener.Race.size()) {
			int i = 1;
			for (Player players : Bukkit.getOnlinePlayers()) {
				Location loc = getSpawnLocation(i);

				players.teleport(loc);
				i++;
			}
		} else if (JoinListener.GermanLetsPlay.size() < JoinListener.Race.size()) {
			int i = 1;
			for (Player players : Bukkit.getOnlinePlayers()) {
				Location loc = getSpawnLocation1(i);

				players.teleport(loc);
				i++;
			}
		} else {
			int i = 1;
			for (Player players : Bukkit.getOnlinePlayers()) {
				Location loc = getSpawnLocation(i);

				players.teleport(loc);
				i++;
			}
		}
	}
	
	public static void MapVoting2(Player p) {
		if (JoinListener.GermanLetsPlay.size() > JoinListener.Race.size()) {
			int min = 1;
			int max = 6;
			Random r = new Random();
			int randomNum = r.nextInt(max - min + 1) + min;
			Location loc = getSpawnLocation(randomNum);

			p.teleport(loc);

		} else if (JoinListener.GermanLetsPlay.size() < JoinListener.Race.size()) {
			int min = 1;
			int max = 6;
			Random r = new Random();
			int randomNum = r.nextInt(max - min + 1) + min;
			Location loc = getSpawnLocation(randomNum);

			p.teleport(loc);

		} else {
			int min = 1;
			int max = 6;
			Random r = new Random();
			int randomNum = r.nextInt(max - min + 1) + min;
			Location loc = getSpawnLocation(randomNum);

			p.teleport(loc);

		}
	}

//	public static void changeMap(Player p, int zahl) {
//		if (zahl == 0) {
//		}
//		if (zahl == 1) {
//
//		}
//		if (zahl == 2) {
//
//			}
//		}
//	}

	public static void setStartItems() {
		ItemStack schwert = new ItemStack(Material.STONE_SWORD);
		ItemMeta meta1 = schwert.getItemMeta();
		meta1.addEnchant(Enchantment.DAMAGE_ALL, 1, true);
		schwert.setItemMeta(meta1);
		meta1 = null;

		ItemStack jacke = new ItemStack(Material.LEATHER_CHESTPLATE);
		meta1 = jacke.getItemMeta();
		meta1.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1, true);
		jacke.setItemMeta(meta1);

		ItemStack compass = new ItemStack(Material.COMPASS);

		for (Player p : Global.activePlayers) {
			p.getInventory().setItem(0, schwert);
			p.getInventory().setItem(8, compass);
			p.getInventory().setChestplate(jacke);

		}
	}

	public static void startDeathMatch() {
		Utils.sendScoreboard();

		Global.deathMatchCount = Bukkit.getScheduler().scheduleSyncRepeatingTask(Global.instance, new Runnable() {

			@SuppressWarnings("deprecation")
			@Override
			public void run() {
				Global.deathMatchCountInt--;

				Utils.updateScoreboard();
				Utils u = new Utils();

				if (Global.countNumbers.contains(Global.deathMatchCountInt) && Global.deathMatchCountInt <= 60) {
					u.broadcast(Global.prefix + "Das Spiel endet in§b " + Global.deathMatchCountInt + " Sekunden§7.");
					u.broadcast(Global.prefix + "Der Spieler, der sich am nähesten an der Mitte befindet, gewinnt.");
					for (Player all : Bukkit.getOnlinePlayers()) {
						all.playSound(all.getLocation(), Sound.NOTE_PLING, 2, 2);
					}
				} else if (Global.deathMatchCountInt == 0) {

					Player winner = null;
					int i = 9999999;
					for (Player all : Global.activePlayers) {
						if (all.getLocation().distance(Global.mitte) < i) {
							i = (int) all.getLocation().distance(Global.mitte);
							winner = all;
						}
					}
					u.setWinner(winner);

					Bukkit.getScheduler().cancelTask(Global.deathMatchCount);
				}
				if (Global.deathMatchCountInt < 50) {
					if (Global.feuer) {

						Location l = Factory.getConfigLocation("End.Spawn", Var.cfg).add(0, 1, 0);
						l.getBlock().setType(Material.STAINED_GLASS);
						l.getBlock().setData(DyeColor.RED.getData());

						Location loc = Factory.getConfigLocation("End.Spawn", Var.cfg);
						for (int x = loc.getBlockX() - 1; x < loc.getBlockX() + 2; x++) {

							for (int y = loc.getBlockY() - 1; y < loc.getBlockY(); y++) {

								for (int z = loc.getBlockZ() - 1; z < loc.getBlockZ() + 2; z++) {

									Global.pvp.getBlockAt(x, y, z).setType(Material.GOLD_BLOCK);
								}

							}

						}

						Factory.getConfigLocation("End.Spawn", Var.cfg).getBlock().setType(Material.BEACON);
					} else {
						Factory.getConfigLocation("End.Spawn", Var.cfg).getBlock().setType(Material.BEACON);
						Location l = Factory.getConfigLocation("End.Spawn", Var.cfg).add(0, 1, 0);
						l.getBlock().setType(Material.STAINED_GLASS);
						l.getBlock().setData(DyeColor.WHITE.getData());
					}
					Global.feuer = !Global.feuer;
				}

			}

		}, 0L, 20L);
	}

	public static void updateCompases(List<Player> players) {
		for (int i = 0; i < players.size(); i++) {
			if (!Global.spectators.contains(players.get(i))) {
				Location l = players.get(i).getLocation();
				double shortestDistance = 99999;
				int nearestPlayer = i;
				for (int j = 0; j < players.size(); j++) {
					double distanceToPlayer = l.distance(players.get(j).getLocation());
					if (distanceToPlayer < shortestDistance && i != j) {
						nearestPlayer = j;
						shortestDistance = distanceToPlayer;
					}

				}

				players.get(i).setCompassTarget(players.get(nearestPlayer).getLocation());
			}

		}
	}

	public static Location randomSpawn() {
		Random r = new Random();

		int x = (int) (Global.pvp.getSpawnLocation().getX()
				+ (r.nextInt(Global.respawnRange) - (Global.respawnRange / 2)));
		int z = Global.pvp.getSpawnLocation().getBlockZ()
				+ (r.nextInt(Global.respawnRange) - (Global.respawnRange / 2));
		int y = 40;
		while (Global.pvp.getBlockAt(x, y, z).getType() != Material.AIR) {
			y++;
		}
		y += 2;
		Location loc = new Location(Global.pvp, x, y, z);
		return loc;
	}

	public static void resetPvpScore() {
		for (Player all : Global.activePlayers) {
			if (all.getScoreboard().getObjective("aaa") != null) {
				Objective score = all.getScoreboard().getObjective("aaa");
				score.getScoreboard().getEntries().clear();
			}
		}
	}

	public static void showAllForAll() {
		for (Player all : Bukkit.getOnlinePlayers()) {
			for (Player other : Bukkit.getOnlinePlayers()) {
				if (all != other)
					all.showPlayer(other);
			}
		}
	}

	public static boolean checkForItemStackTitle(ItemStack i, String name) {
		if (i != null) {
			if (i.hasItemMeta()) {
				if (i.getItemMeta().hasDisplayName()) {
					if (i.getItemMeta().getDisplayName().contains(name)) {
						return true;
					}
					return false;
				}
				return false;
			}
			return false;
		}

		return false;
	}

	@EventHandler
	public void onMove(PlayerMoveEvent e) {
		if (Utils.move.contains(e.getPlayer())) {
			Player p = e.getPlayer();
			Location to = e.getTo();
			Location from = e.getFrom();

			if (from.getX() != to.getX() || from.getZ() != to.getZ()) {
				p.teleport(from);

			}
		}
	}

	public static Location getSpawnLocation(int number) {
		double x = Var.positionscfg.getDouble(number + ".PosX");
		double y = Var.positionscfg.getDouble(number + ".PosY");
		double z = Var.positionscfg.getDouble(number + ".PosZ");
		String worlds = Var.positionscfg.getString(number + ".worldname");

		Location loc = new Location(Bukkit.getWorld(worlds.toLowerCase()), x, y, z);
		return loc;

	}

	public static Location getSpawnLocation1(int number) {
		double x = Var.positionscfg2.getDouble(number + ".PosX");
		double y = Var.positionscfg2.getDouble(number + ".PosY");
		double z = Var.positionscfg2.getDouble(number + ".PosZ");
		String worlds = Var.positionscfg2.getString(number + ".worldname");

		Location loc = new Location(Bukkit.getWorld(worlds.toLowerCase()), x, y, z);
		return loc;

	}

	public static void setActionBar(Player player, String message) {
		CraftPlayer p = (CraftPlayer) player;
		IChatBaseComponent cbc = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + message + "\"}");
		PacketPlayOutChat ppoc = new PacketPlayOutChat(cbc, (byte) 2);
		p.getHandle().playerConnection.sendPacket(ppoc);
	}
}