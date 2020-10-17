package me.nitcraft.commands;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import me.nitcraft.luckyrace.GameState;
import me.nitcraft.luckyrace.Global;
import me.nitcraft.luckyrace.LuckyRace;
import me.nitcraft.utils.Factory;
import me.nitcraft.utils.Utils;
import me.nitcraft.utils.Var;

public class BuildCommand implements CommandExecutor {

	private LuckyRace plugin;

	public BuildCommand(LuckyRace plugin) {
		this.plugin = plugin;
	}

	public static File file = new File("plugins/LuckyRace", "spawns.yml");
	public static FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);

	
	public static File file2 = new File("plugins/LuckyRace", "spawns2.yml");
	public static FileConfiguration cfg2 = YamlConfiguration.loadConfiguration(file2);
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if ((sender instanceof Player)) {
			Player p = (Player) sender;
			if ((p.hasPermission("lr.build")) || (p.isOp())) {
				if (args.length != 1) {
					p.sendMessage(Global.prefix
							+ "§4FEHLER: §bZu wenig Argumente:§c /build <on , off , setspawn , delspawns>");
					return true;
				} else {
					if (GameState.isState(GameState.LOBBY)) {

						String s = args[0];

						if (Global.lobbyCountBoolean) {
							Bukkit.getScheduler().cancelTask(Global.lobbyCount);
							Global.lobbyCountInt = Global.defaultLobbyCountInt;
							Global.lobbyCountBoolean = false;
							Utils u = new Utils();
							u.broadcast(
									Global.prefix + "Das Spiel wurde §4abgebrochen§b, da die Spawns geändert werden.");
						}

						if (s.equalsIgnoreCase("on")) {
							if (!Global.builds.contains(p)) {
								p.sendMessage(Global.prefix + "Der Build-Modus ist nun §2an!");
								p.setGameMode(GameMode.CREATIVE);

								String stra = "spawns." + (0 + "") + ".";

								Double x = BuildCommand.cfg.getDouble(stra + "x");
								Double y = BuildCommand.cfg.getDouble(stra + "y");
								Double z = BuildCommand.cfg.getDouble(stra + "z");

								if (x != null && y != null && z != null) {
									p.teleport(new Location(Global.world, x, y, z));
								} else {
									p.teleport(Global.world.getSpawnLocation());
								}

								Global.builds.add(p);
							} else {
								p.sendMessage(Global.prefix + "§4FEHLER:§b Der Build-Modus ist bereits an!");
							}
							return true;

						} else if (s.equalsIgnoreCase("off")) {

							if (!Global.spectators.contains(p)) {
								p.setGameMode(GameMode.SURVIVAL);
							} else {
								p.setGameMode(GameMode.SPECTATOR);
							}
							if (GameState.isState(GameState.LOBBY))
								p.teleport(Factory.getConfigLocation("SkyWars.Lobby", Var.cfg));

							Global.world.save();
							Utils.resetWorld(new File("world"), new File("backup_world"), "world");
							p.teleport(Global.lobby.getSpawnLocation());

							Global.builds.remove(p);
							p.sendMessage(Global.prefix + "Der Build-Modus ist nun §4aus!");

							Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {

								@Override
								public void run() {
									Utils.checkForNewMatch(p);
								}

							}, 1);

							return true;

						} else if (s.equalsIgnoreCase("setspawn")) {
							if (Global.builds.contains(p)) {
								Location loc = p.getLocation();
								int i = 0;
								if (BuildCommand.cfg.getConfigurationSection("spawns") != null) {
									i = BuildCommand.cfg.getConfigurationSection("spawns").getKeys(false)
											.toArray().length;

								}
								String str = "spawns." + i + "" + ".";
								BuildCommand.cfg.set(str + "x", loc.getX());
								BuildCommand.cfg.set(str + "y", loc.getY());
								BuildCommand.cfg.set(str + "z", loc.getZ());
								BuildCommand.cfg.set(str + "yaw", loc.getYaw());
								BuildCommand.cfg.set(str + "pitch", loc.getPitch());

								try {
									BuildCommand.cfg.save(file);
									p.sendMessage(Global.prefix + "Spawn Nummer §6" + (i) + "§b wurde gespeichert!");
								} catch (IOException e) {
									e.printStackTrace();
								}

								return true;
							} else {
								p.sendMessage(Global.prefix
										+ "§4FEHLER:§b Du musst den Build-Modus erst mit §6/build on §beinschalten!!");
								return true;
							}
							
						} else if (s.equalsIgnoreCase("setsdeathmatch")) {
							if (Global.builds.contains(p)) {
							    int number = Integer.valueOf(args[1]).intValue();
								 
							    Var.setSpawnLocation(p, number);

								
							} else {
								p.sendMessage(Global.prefix
										+ "§4FEHLER:§b Du musst den Build-Modus erst mit §6/build on §beinschalten!!");
								return true;
							}
						} else if (s.equalsIgnoreCase("delspawns")) {
							if (Global.builds.contains(p)) {
								cfg.set("spawns", null);
								try {
									cfg.save(file);
								} catch (IOException e) {
									e.printStackTrace();
								}
								p.sendMessage(Global.prefix + "Spawns erfolgreich §6gelöscht.");
							} else {
								p.sendMessage(Global.prefix
										+ "§4FEHLER:§b Du musst den Build-Modus erst mit §6/build on §beinschalten!!");
							}
							return true;
						} else {
							p.sendMessage(Global.prefix
									+ "§4FEHLER:§b Falsche Argumente: §c /build <on , off , setspawn , delspawns>");
						}
					} else {
						p.sendMessage("§4FEHLER:§b Du kannst die Spawns nicht während eines Spiels setzen.");
						return true;
					}

				}

			} else {
				p.sendMessage(Global.prefix + "§4FEHLER: §bDu hast nicht die benötigten Berechtigung.");
				return true;
			}
		} else {
			sender.sendMessage(Global.prefix + "§4FEHLER: §bDu musst ein Spieler sein!");
			return true;
		}
		return true;
	}
}
