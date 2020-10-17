package me.nitcraft.commands;

import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.nitcraft.utils.Factory;
import me.nitcraft.utils.Var;


public class CMDSetSpawn implements CommandExecutor {

	
	
	@Override
	public boolean onCommand(CommandSender s, Command cmd, String arg2, String[] args) {
		Player p = (Player) s;

		switch (args.length) {
		   case 1:
				if (args[0].equalsIgnoreCase("end")) {
					p.playSound(p.getLocation(), Sound.ITEM_PICKUP, 10.0f, 10.0f);
					Factory.createConfigLocation(p.getLocation(), "End.Spawn", Var.cfgfile, Var.cfg);

				}
		   case 2:
				if (args[0].equalsIgnoreCase("jump")) {
					p.playSound(p.getLocation(), Sound.ITEM_PICKUP, 10.0f, 10.0f);
					Factory.createConfigLocation(p.getLocation(), "Jump.Spawn", Var.cfgfile, Var.cfg);
				}
		   case 3:
			    if (args[0].equalsIgnoreCase("deathmatch")) {
					int number = Integer.valueOf(args[1]).intValue();

					Var.setSpawnLocation(p, number);
			    }
		   case 4:
			    if (args[0].equalsIgnoreCase("deathmatch2")) {
				    int number = Integer.valueOf(args[1]).intValue();
				    Var.setSpawnLocation1(p, number);
					return false;
			    }
				break;
			}
			
		return false;

		}
}