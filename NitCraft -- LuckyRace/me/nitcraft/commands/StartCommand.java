package me.nitcraft.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;

import me.nitcraft.luckyrace.GameState;
import me.nitcraft.luckyrace.Global;
import me.nitcraft.luckyrace.LuckyRace;
import me.nitcraft.utils.Utils;

public class StartCommand implements CommandExecutor {
	@SuppressWarnings("unused")
	private LuckyRace plugin;

	public StartCommand(LuckyRace plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

			if ((sender.hasPermission("lr.start")) || (sender.isOp())) {
				if (Global.lobbyCountInt > 11) {
					if (Global.lobbyCountBoolean) {
						Global.lobbyCountInt = 11;
						Utils u = new Utils();
						u.broadcast(Global.prefix + "Das Spiel wurde gestartet!");
						if(sender instanceof ConsoleCommandSender) {
							sender.sendMessage(Global.prefix + "Du hast das Spiel gestartet!");
						}
					} else {
						if(Bukkit.getOnlinePlayers().size() <= Global.minPlayers && (GameState.isState(GameState.LOBBY))) {
							sender.sendMessage(Global.prefix + "§bEs sind zu wenige Spieler online! §7[§c" + Bukkit.getOnlinePlayers().size() + "§7/§a" + Global.minPlayers + "§7]");
						} else {
							sender.sendMessage(Global.prefix + "§bDas Spiel läuft bereits.");
						}
					}
				} else {
					sender.sendMessage(Global.prefix + "§bDas Spiel startet bereits in §6" + Global.lobbyCountInt
							+ " Sekunden§b.");
				}
			} else {
				sender.sendMessage(Global.prefix + "§bDu hast nicht die benötigten Berechtigung.");
			}
		return true;
	}
}
