package me.nitcraft.luckyrace;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Global {
	public static Player editor;
	public static File file;
	public static FileConfiguration cfg;
	public static List<Player> activePlayers;
	public static List<Player> spectators;
	public static List<Player> builds;
	public static World world;
	public static World world2;
	public static World lobby;
	public static World pvp;
	public static String prefix;
	public static String ip;
	public static LuckyRace instance;
	public static int compassTask;
	public static String deathMessage;
	public static int maxPlayers;
	public static HashMap<Block, ArrayList<ItemStack>> lootByKillMap;
	public static int minJumpAndRunHeight;
	public static HashMap<Player, Location> checkPoints;
	public static Material checkPointMaterial;
	public static HashMap<Player, Integer> checkPointNumber;
	public static Material jumpFinishMaterial;
	public static int timeAfterFinish;
	public static int defaultLobbyCountInt;
	public static int lobbyCount;
	public static int lobbyCountInt;
	public static boolean lobbyCountBoolean;
	public static int defaultPvpCountInt;
	public static int pvpCount;
	public static int pvpCountInt;
	public static HashMap<Integer, Integer> livesMap;
	public static int deathsUntilLose;
	public static int countAfterWinInt;
	public static int countAfterWin;
	public static ArrayList<Integer> countNumbers;
	public static ArrayList<Player> move;
	public static int minPlayers;
	public static boolean teamsAllowed;
	public static int deathMatchCount;
	public static int deathMatchCountInt;
	public static Location mitte;
	public static boolean feuer;
	public static int respawnRange;
	public static String hubServerName;

	static {
		Global.editor = null;
		Global.file = new File("plugins/LuckyRace", "config.yml");
		Global.cfg = YamlConfiguration.loadConfiguration(Global.file);
		Global.activePlayers = new ArrayList<Player>();
		Global.spectators = new ArrayList<Player>();
		Global.builds = new ArrayList<Player>();
		Global.world = Bukkit.createWorld(new WorldCreator("world"));
		Global.world2 = Bukkit.getWorld("world2");
		Global.lobby = Bukkit.createWorld(new WorldCreator("lobby"));
		Global.pvp = Bukkit.createWorld(new WorldCreator("pvp"));
		Global.prefix = Global.cfg.getString("prefix").replace("&", "§");
		Global.ip = Global.cfg.getString("serverIPforScore");
		Global.deathMessage = Global.cfg.getString("deathMessage");
		Global.maxPlayers = Global.cfg.getInt("maxPlayers");
		Global.lootByKillMap = new HashMap<Block, ArrayList<ItemStack>>();
		Global.minJumpAndRunHeight = Global.cfg.getInt("minJumpAndRunHeight");
		Global.checkPoints = new HashMap<Player, Location>();
		Global.checkPointMaterial = Material.IRON_BLOCK;
		Global.checkPointNumber = new HashMap<Player, Integer>();
		Global.jumpFinishMaterial = Material.GOLD_BLOCK;
		Global.timeAfterFinish = Global.cfg.getInt("timeAfterFinish");
		Global.defaultLobbyCountInt = Global.cfg.getInt("defaultLobbyCountLength");
		Global.lobbyCountInt = Global.defaultLobbyCountInt;
		Global.lobbyCountBoolean = false;
		Global.defaultPvpCountInt = Global.cfg.getInt("defaultPvpCountLengthInSeconds");
		Global.pvpCountInt = Global.defaultPvpCountInt;
		Global.livesMap = new HashMap<Integer, Integer>();
		Global.deathsUntilLose = Global.cfg.getInt("deathsUntilLose");
		Global.countAfterWinInt = Global.cfg.getInt("timeAfterWin");
		Global.countNumbers = new ArrayList<Integer>();
		Global.move = new ArrayList<Player>();
		Global.minPlayers = Global.cfg.getInt("minPlayers");
		Global.teamsAllowed = Global.cfg.getBoolean("teamsAllowed");
		Global.deathMatchCountInt = Global.cfg.getInt("deathmatchTimeInSeconds");
		Global.mitte = Global.pvp.getSpawnLocation();
		Global.feuer = false;
		Global.respawnRange = Global.cfg.getInt("respawnRange");
		Global.hubServerName = Global.cfg.getString("hubServerName");
	}

	public static void setCountNumbers() {
		Global.countNumbers.add(120);
		Global.countNumbers.add(60);
		Global.countNumbers.add(50);
		Global.countNumbers.add(40);
		Global.countNumbers.add(30);
		Global.countNumbers.add(20);
		Global.countNumbers.add(10);
		Global.countNumbers.add(5);
		Global.countNumbers.add(4);
		Global.countNumbers.add(3);
		Global.countNumbers.add(2);
		Global.countNumbers.add(1);
	}

	public static void setInstance(final LuckyRace race) {
		Global.instance = race;
	}
}
