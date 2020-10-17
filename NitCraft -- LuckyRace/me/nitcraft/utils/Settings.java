package me.nitcraft.utils;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.Wool;

import me.nitcraft.luckyrace.Global;
import me.nitcraft.luckyrace.LuckyRace;

public class Settings {
	@SuppressWarnings("unused")
	private LuckyRace plugin;
	private static ItemStack teams;
	public static ItemStack teamsOff;
	public static ItemStack teamsOn;
	private static List<String> lore = new ArrayList<String>();
	
	public Settings(LuckyRace plugin, Player p) {
		this.plugin = plugin;
		
	}
	
	
	public static void openWelcomeInventory(Player p) {
		

		
		
		Wool w = new Wool();
		if(!Global.teamsAllowed) {
			w.setColor(DyeColor.RED);
			teamsOff = w.toItemStack(1);
			ItemMeta m = teamsOff.getItemMeta();
			lore.add("§7Klicken, um die Teams §aeinzuschalten.");
			m.setLore(lore);
			lore.clear();
			m.setDisplayName("§cTeams sind ausgeschaltet");
			teamsOff.setItemMeta(m);
			
			teams = teamsOff;
		} else if(Global.teamsAllowed){
			w.setColor(DyeColor.GREEN);
			teamsOn = w.toItemStack(1);
			ItemMeta m = teamsOn.getItemMeta();
			lore.add("§7Klicken, um die Teams§c auszuschalten.");
			m.setLore(lore);
			lore.clear();
			m.setDisplayName("§aTeams sind eingeschaltet");
			teamsOn.setItemMeta(m);
			
			teams = teamsOn;
		}
		
		
		
		
		
		ItemStack deathChange = new ItemStack(Material.DIAMOND_SWORD, Global.deathsUntilLose);
		ItemMeta deathChangeMeta = deathChange.getItemMeta();
		deathChangeMeta.setDisplayName("§7Leben: §a" + Global.deathsUntilLose);
		lore.add("§6Stelle die Leben eines Spielers bis er ausscheidet ein!");
		lore.add("§cLinksklick:  -1");
		lore.add("§cRechtsklick: +1");
		deathChangeMeta.setLore(lore);
		lore.clear();
		deathChange.setItemMeta(deathChangeMeta);
		
		
		
		
		
		ItemStack deathTime = new ItemStack(Material.WATCH, Global.deathMatchCountInt / 60);
		ItemMeta deathTimeMeta = deathTime.getItemMeta();
		deathTimeMeta.setDisplayName("§7Zeit im DM: §a" + Global.deathMatchCountInt / 60 +  " Min");
		lore.add("§6Stellt die Zeit im DeathMatch in Minuten ein!");
		lore.add("§cLinksklick:  -1 Min");
		lore.add("§cRechtsklick: +1 Min");
		deathTimeMeta.setLore(lore);
		lore.clear();
		deathTime.setItemMeta(deathTimeMeta);
		
		ItemStack resetDeath = new ItemStack(Material.BLAZE_POWDER);
		ItemMeta resetMeta = resetDeath.getItemMeta();
		resetMeta.setDisplayName("§4Setze die §6DM-Zeit§4 zurück");
		resetMeta.setLore(null);
		resetDeath.setItemMeta(resetMeta);
		
		
		
		ItemStack resetTeams = new ItemStack(Material.BLAZE_POWDER);
		ItemMeta resetTeamMeta = resetTeams.getItemMeta();
		resetTeamMeta.setDisplayName("§4Setze die §6Team-Einstellung§4 zurück");
		resetTeamMeta.setLore(null);
		resetTeams.setItemMeta(resetTeamMeta);
		
		
		
		ItemStack resetDeaths = new ItemStack(Material.BLAZE_POWDER);
		ItemMeta resetDeathsMeta = resetDeath.getItemMeta();
		resetDeathsMeta.setDisplayName("§4Setze die §6Tode zum Ausscheiden§4 zurück");
		resetDeathsMeta.setLore(null);
		resetDeaths.setItemMeta(resetDeathsMeta);
		
		
		
		
		
		Inventory inv = Bukkit.createInventory(null, 45, "§7Einstellungen");
		inv.setItem(22, teams);
		inv.setItem(20, deathChange);
		inv.setItem(24, deathTime);
		

		inv.setItem(29, resetDeaths);
		inv.setItem(31, resetTeams);
		inv.setItem(33, resetDeath);
		
		
		p.openInventory(inv);
	}


	public static void updateInv(Player p) {
		openWelcomeInventory(p);
	}
}
