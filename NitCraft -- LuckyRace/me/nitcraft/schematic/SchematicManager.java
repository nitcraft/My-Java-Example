package me.nitcraft.schematic;

import java.io.File;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import de.slimeplex.plexutils.system.player.objects.ActionBar;
import de.slimeplex.plexutils.system.world.objects.schematic.SchematicBuilder;
import me.nitcraft.luckyrace.Global;
import me.nitcraft.luckyrace.LuckyRace;

public class SchematicManager {
	
	public enum SchematicDifficulty {EASY, NORMAL, HARD, HARDCORE};

	private static int maxEasyModules = 3;
	private static int maxNormalModules = 3;
	private static int maxHardModules = 3;
	private static int maxHardcoreModules = 1;

	public static List<SchematicBuilder> schematicList = new ArrayList<>();
	public static HashMap<Player, ActionBar> playerActionBar = new HashMap<>();
	
	
	
	public static void registerSchematics() {
		File easyModules = new File(LuckyRace.getInstance().getDataFolder().getPath() + "/Easy/");
		File normalModules = new File(LuckyRace.getInstance().getDataFolder().getPath() + "/Normal/");
		File hardModules = new File(LuckyRace.getInstance().getDataFolder().getPath() + "/Hard/");
		File hardcoreModules = new File(LuckyRace.getInstance().getDataFolder().getPath() + "/Hardcore/");
		
		for(File schematic : getSchematics(easyModules, maxEasyModules)) {
			SchematicBuilder schematicBuilder = new SchematicBuilder(schematic);
			schematicList.add(schematicBuilder);
		}
		
//		for(int i = 1; i <= maxNormalModules; i++) {
//			File schematic = normalModules.listFiles()[new SecureRandom().nextInt(normalModules.listFiles().length)];
//			SchematicBuilder schematicBuilder = new SchematicBuilder(schematic);
//			if(schematicNameList.contains(schematicBuilder.getSchematicName())) {
//				i--;
//				continue;
//			}
//			schematicList.add(schematicBuilder);
//			schematicNameList.add(schematicBuilder.getSchematicName());
//
//		}
		for(File schematic : getSchematics(normalModules, maxNormalModules)) {
			SchematicBuilder schematicBuilder = new SchematicBuilder(schematic);
			schematicList.add(schematicBuilder);
		}
		
		for(File schematic : getSchematics(hardModules, maxHardModules)) {
			SchematicBuilder schematicBuilder = new SchematicBuilder(schematic);
			schematicList.add(schematicBuilder);
		}
		
		for(File schematic : getSchematics(hardcoreModules, maxHardcoreModules)) {
			SchematicBuilder schematicBuilder = new SchematicBuilder(schematic);
			schematicList.add(schematicBuilder);
		}
	}
	
	
	public static void sendActionBar(Player p, SchematicBuilder schematicBuilder) {
		int level = Global.checkPointNumber.get(p);
		ActionBar actionBar;
		if(!playerActionBar.containsKey(p)) {
			actionBar = new ActionBar(p, "§6§l>> " + "§e" + schematicBuilder.getSchematicName() + "§6§l | " + getDifficulty(level) + " §6§l<<").setInfinity(true).send();
			playerActionBar.put(p, actionBar);
		} else {
			actionBar = playerActionBar.get(p);
		}
		actionBar.setMessage("§6§l>> " + "§e" + schematicBuilder.getSchematicName() + "§6§l | " + getDifficulty(level) + " §6§l<< ");
	}
	
	
	private static String getDifficulty(int level) {
		if(level <= maxEasyModules) {
			return "§aLeicht";
		} else if(level <= (maxEasyModules + maxNormalModules)) {
			return "§eNormal";
		} else if(level <= (maxEasyModules + maxNormalModules + maxHardModules)) {
			return "§cSchwer";
		} else if(level <= (maxEasyModules + maxNormalModules + maxHardModules + maxHardcoreModules)) {
			return "§4Hardcore";
		} else {
			return "error";
		}
		
	}
	
	private static List<File> getSchematics(File schematicFolder, int schematicAmount) {
		List<File> list = new ArrayList<>();
		while(list.size() < schematicAmount) {
			File schematic = schematicFolder.listFiles()[new SecureRandom().nextInt(schematicFolder.listFiles().length)];
			if(!list.contains(schematic)) {
				list.add(schematic);
			} else {
				Bukkit.getConsoleSender().sendMessage("skip: " + schematicFolder.getPath());
			}
		}
		return list;
	}
}
