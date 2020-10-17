package me.nitcraft.commands;

import org.apache.commons.lang.math.NumberUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import me.nitcraft.luckyrace.GameState;
import me.nitcraft.luckyrace.Global;
import me.nitcraft.luckyrace.LuckyRace;
import me.nitcraft.utils.Utils;

public class LRCommand implements CommandExecutor{


	@SuppressWarnings("unused")
	private LuckyRace plugin;

	public LRCommand(LuckyRace plugin) {
		this.plugin = plugin;
	}



	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(sender.isOp() || sender.hasPermission("lr.time")) {
			if(GameState.isState(GameState.INGAME)) {
				if(Global.pvpCountInt > Global.timeAfterFinish) {
					if(args.length == 1) {
						String arg = args[0];
						if(NumberUtils.isNumber(arg)) {
							int time = Integer.valueOf(arg);
							if(time >= 20) {
								Global.pvpCountInt = time;
								Utils u = new Utils();
								u.broadcast(Global.prefix + "Die Zeit wurde auf §6" + time + " Sekunden§b heruntergesetzt");
							} else {
								Global.pvpCountInt = 20;
								Utils u = new Utils();
								u.broadcast(Global.prefix + "Die Zeit wurde auf §6" + time + " Sekunden§b heruntergesetzt");
								sender.sendMessage(Global.prefix + "§cDa §6" + time + " Sekunden§c zu kurz waren, wurde die Zeit auf §620 Sekunden §cheruntergesetzt.");
							}
						} else {
							sender.sendMessage(Global.prefix + "§4FEHLER: §6\"" + arg + "\"§b ist keine Zeit!");
						}
					} else {
						sender.sendMessage(Global.prefix + "§b Zu wenige Argumente: §6/skip <Zeit in Sekunden>§b!");
					}
				} else {
					sender.sendMessage(Global.prefix + "§b Das Jump and Run endet bereits.");
				}
			} else {
				sender.sendMessage(Global.prefix + "§b Du kannst diesen Command nur im Jump and Run ausführen!");
			}
		} else {
			sender.sendMessage(Global.prefix + "§bKeine nötigen Berechtigungen!");
			return true;
		}
		return true;
	}
}
