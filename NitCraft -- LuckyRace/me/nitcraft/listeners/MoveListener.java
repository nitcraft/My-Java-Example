package me.nitcraft.listeners;

import org.bukkit.Bukkit;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import de.slimeplex.nitcore.utils.objects.NitUser;
import de.slimeplex.plexutils.system.base.objects.ItemBuilder;
import de.slimeplex.plexutils.system.world.objects.schematic.PasteAnimationType;
import de.slimeplex.plexutils.system.world.objects.schematic.SchematicBuilder;
import de.slimeplex.plexutils.system.world.objects.schematic.SchematicDirection;
import me.nitcraft.luckyrace.GameState;
import me.nitcraft.luckyrace.Global;
import me.nitcraft.luckyrace.LuckyRace;
import me.nitcraft.luckyrace.LuckyRaceSection;
import me.nitcraft.luckyrace.LuckyRaceSection.Achievement;
import me.nitcraft.schematic.SchematicManager;
import me.nitcraft.stats.Stats;
import me.nitcraft.utils.Utils;


public class MoveListener implements Listener {
	
	private LuckyRace plugin;
	private Utils u;
	int time = Global.timeAfterFinish;

	public MoveListener(LuckyRace plugin) {
		this.plugin = plugin;
		u = new Utils();
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}

	@EventHandler
	public void onMove(PlayerMoveEvent e) {
		if (GameState.isState(GameState.INGAME)) {
			Player p = e.getPlayer();
			if (!Global.spectators.contains(p)) {
				
				Location check = Global.checkPoints.get(p);
				Location loc = p.getLocation();
				int x1 = check.getBlockX();
				int y1 = check.getBlockY();
				int z1 = check.getBlockZ();
				
				int x2 = loc.getBlockX();
				int y2 = loc.getBlockY();
				int z2 = loc.getBlockZ();
				
				if (loc.getX() > x1 + 15 || loc.getX() < x1 - 15) {
					p.teleport(Global.checkPoints.get(p));
					Utils.fallDeath(p);
					p.playSound(p.getLocation(), Sound.BAT_DEATH, 10.0f, 5.0f);
				}
				
				if (y2 < Global.minJumpAndRunHeight) {
					p.teleport(Global.checkPoints.get(p));
					Utils.fallDeath(p);
					p.playSound(p.getLocation(), Sound.BAT_DEATH, 10.0f, 5.0f);
					
				} else if (p.getLocation().subtract(0.0D, 1.0D, 0.0D).getBlock().getType().equals(Global.checkPointMaterial)) {

				//	if ((x1 != x2) || (y1 != y2) || (z1 != z2)) {
						Global.checkPoints.put(p, p.getLocation());
						p.playSound(p.getLocation(), Sound.ITEM_PICKUP, 10.0F, 10.0F);
						p.playSound(p.getLocation(), Sound.LEVEL_UP, 3.0F, 3.0F);

						int i = Global.checkPointNumber.get(p);
						i++;
						Global.checkPointNumber.put(p, i);
						p.getInventory().remove(new ItemBuilder(Material.FIREWORK).setDisplayName("§6>> §7Boost - §b" + Utils.BoostUses.get(e.getPlayer().getUniqueId()) + "§7 Nutzungen").setGlow().build());
						Utils.BoostUses.remove(p.getUniqueId());
						Utils.deaths.remove(p.getUniqueId());
						Utils.addCheckPointForPvpScoreboard(p);
						p.sendMessage(Global.prefix + "§aDu hast Checkpoint §e" + i + "§a erreicht!");
						p.sendMessage(Global.prefix + "§7Du hast §b50 §eMoneten §7erhalten");
						new NitUser(p.getUniqueId().toString()).addCoins(50);
					//	p.sendTitle("§6Checkpoint", "§a"+i);
						SchematicBuilder schematicBuilder = SchematicManager.schematicList.get(i-1);
						schematicBuilder.pasteWithAnimation(PasteAnimationType.LAYER, SchematicDirection.SOUTH, p.getLocation(), true, 100);
						SchematicManager.sendActionBar(p, schematicBuilder);
						p.getLocation().subtract(0.0D, 1.0D, 0.0D).getBlock().setType(Material.COAL_BLOCK);
					    Utils.playFirework(p, loc, org.bukkit.Color.AQUA, org.bukkit.Color.AQUA, FireworkEffect.Type.BALL);
				} else if ((p.getLocation().subtract(0.0D, 1.0D, 0.0D).getBlock().getType().equals(Global.jumpFinishMaterial)) && (!Stats.finishedJumpRun.containsKey(p))) {

					Stats.finishedJumpRun.put(p, true);
					p.playSound(p.getLocation(), Sound.NOTE_PLING, 3.0F, 3.0F);
					p.playSound(p.getLocation(), Sound.NOTE_PLING, 1.0F, 2.0F);

					for (Player all : Bukkit.getOnlinePlayers()) {
						all.playSound(p.getLocation(), Sound.ENDERDRAGON_GROWL, 5.0f, 5.0f);
					}
					int i = Global.checkPointNumber.get(p);
					i++;

					Global.checkPoints.put(p, p.getLocation());

					Global.checkPointNumber.put(p, i);

					Utils.addCheckPointForPvpScoreboard(p);
					
					u.broadcast(Global.prefix + "§k§8||||||||||||||||||||||||||||||||||||||||||||");
					u.broadcast(Global.prefix + "§6" + p.getName() + "§b hat das Ziel erreicht!");
					u.broadcast(Global.prefix + "§k§8||||||||||||||||||||||||||||||||||||||||||||");
					p.sendMessage(Global.prefix + "§7Du hast §b400 §eMoneten §7erhalten");
					new NitUser(p.getUniqueId().toString()).addCoins(400);
					NitUser user = new NitUser(p.getUniqueId().toString());
					LuckyRaceSection section = (LuckyRaceSection) user.getSection(new LuckyRaceSection(p));
					if (!section.hasAchievement(Achievement.FIRST_JUMPANDRUN)) {
						section.addAchievement(Achievement.FIRST_JUMPANDRUN);
						user.save();
					}
//					StatsManager StatsManager = new StatsManager("LuckyRace");
//					StatsManager.addValue(p.getUniqueId().toString(), "Finished", 1);
//					StatsManager.addValue(p.getUniqueId().toString(), "Points", 10);


					Bukkit.getScheduler().cancelTask(Global.pvpCount);
					Global.pvpCount = Bukkit.getScheduler().scheduleSyncRepeatingTask(this.plugin, new Runnable() {
						@Override
						public void run() {
							time--;
							Utils.sendPvpScoreBoard(time);
							if ((time == 15) || (time == 10) || (time == 5) || (time ==3) || (time == 2) || (time == 1)) {
								u.broadcast(Global.prefix + "Das Deathmatch beginnt in §6" + time + "§b Sekunden!");
								for (Player all : Bukkit.getOnlinePlayers()) {
									all.playSound(all.getLocation(), Sound.NOTE_PLING, 10.0f, 5.0f);
								}
							} else if (time == 0) {
								GameState.setState(GameState.PVP);
								
								Utils.MapVoting();
								for (Player all : Bukkit.getOnlinePlayers()) {
									all.getInventory().remove(new ItemBuilder(Material.FIREWORK).setDisplayName("§6>> §7Boost - §b" + Utils.BoostUses.get(all.getUniqueId()) + "§7 Nutzungen").setGlow().build());
									Utils.BoostUses.remove(all.getUniqueId());
									Utils.deaths.remove(all.getUniqueId());
									SchematicManager.playerActionBar.remove(all);

								}
								Bukkit.getScheduler().cancelTask(Global.pvpCount);
								Utils.sendScoreboard();
								Bukkit.getScheduler().scheduleSyncDelayedTask(Global.instance, new Runnable() {

									@SuppressWarnings("deprecation")
									@Override
									public void run() {
										String s = "§cTeams verboten!";
										if(Global.teamsAllowed) s = "§aTeams erlaubt!";
										u.broadcast("");
										u.broadcast("");
										u.broadcast("");
										u.broadcast(s);
										u.broadcast("");
										u.broadcast("");
										u.broadcast("");
										Utils.startDeathMatch();
										for(Player all : Bukkit.getOnlinePlayers()) {
											all.sendTitle(s, "");
										}
										}
										
									}, 1L);
								}
	
							}
						}, 0L, 20L);
					}
				}
	
			}
		}
}
