package me.nitcraft.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.nitcraft.utils.Factory;
import me.nitcraft.utils.Var;


public class CMDSetLocations implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String arg2, String[] args) {
		Player p = (Player) sender;

		switch (args.length) {
		case 1:
			if (args[0].equalsIgnoreCase("spawn")) {
				Factory.createConfigLocation(p.getLocation(), "LR.LS", Var.cfgfile, Var.cfg);
			}
		
			break;
		}
		return false;
	}
}
