package me.nitcraft.stats;

import java.util.HashMap;
import org.bukkit.entity.Player;


public class Stats {
	
  public static HashMap<Player, Integer> deaths = new HashMap<Player, Integer>();
  public static HashMap<Player, Integer> kills = new HashMap<Player, Integer>();
  public static HashMap<Player, Boolean> finishedJumpRun = new HashMap<Player, Boolean>();
  
}
