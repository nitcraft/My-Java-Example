package me.nitcraft.luckyrace;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import de.slimeplex.nitcore.NitCore;
import me.nitcraft.commands.BuildCommand;
import me.nitcraft.commands.CMDSetLocations;
import me.nitcraft.commands.CMDSetSpawn;
import me.nitcraft.commands.LRCommand;
import me.nitcraft.commands.StartCommand;
import me.nitcraft.drops.Drops;
import me.nitcraft.kits.KitInventory;
import me.nitcraft.listeners.BlockBreakListener;
import me.nitcraft.listeners.BlockPlaceListener;
import me.nitcraft.listeners.ChatListener;
import me.nitcraft.listeners.DamageListener;
import me.nitcraft.listeners.DeathListener;
import me.nitcraft.listeners.EntityDamageByEntityListener;
import me.nitcraft.listeners.EntityDeathListener;
import me.nitcraft.listeners.FoodListener;
import me.nitcraft.listeners.InteractListener;
import me.nitcraft.listeners.InventoryListener;
import me.nitcraft.listeners.JoinListener;
import me.nitcraft.listeners.LeaveListener;
import me.nitcraft.listeners.MoveListener;
import me.nitcraft.listeners.PlayerDamageListener;
import me.nitcraft.listeners.PlayerItemConsumeListener;
import me.nitcraft.listeners.PlayerItemHeldListener;
import me.nitcraft.listeners.PreJoinListener;
import me.nitcraft.listeners.RespawnListener;
import me.nitcraft.schematic.SchematicManager;
import me.nitcraft.utils.ConfigManager;
import me.nitcraft.utils.Factory;
import me.nitcraft.utils.Utils;
import me.nitcraft.utils.Var;

public class LuckyRace extends JavaPlugin
{
    private static LuckyRace instance;
    
    @Override
	public void onEnable() {
        LuckyRace.instance = this;

        GameState.setState(GameState.LOBBY);
        System.out.println("§cLuckyRace - NitCraft");
        Bukkit.getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
        final File f = new File("plugins/LuckyRace", "config.yml");
        if (!f.exists()) {
            try {
                f.createNewFile();
            }
            catch (IOException ex) {}
        }
        ConfigManager.manageConfig();
        this.registerEvents();
        this.registerCommands();
        SchematicManager.registerSchematics();
        Drops.registerDrops();
        for (final World world : Bukkit.getWorlds()) {
            world.setAutoSave(false);
        }
        NitCore.getServerManager().setMaxPlayers(8);
        Bukkit.getWorld("world").setGameRuleValue("doMobSpawning", "false");
        Bukkit.getWorld("world").setGameRuleValue("mobGriefing", "false");
        Bukkit.getWorld("pvp").setGameRuleValue("doMobSpawning", "false");
        Bukkit.getWorld("pvp").setGameRuleValue("mobGriefing", "false");
        Bukkit.getWorld("lobby").setGameRuleValue("doMobSpawning", "false");
        Bukkit.getWorld("world").setGameRuleValue("mobGriefing", "false");
        Bukkit.getWorld("world").setGameRuleValue("doFireTick", "false");
        Bukkit.getWorld("world").setGameRuleValue("keepInventory", "true");

        Bukkit.getWorld("lobby").setSpawnLocation(Factory.getConfigLocation("SkyWars.Lobby", Var.cfg).getBlockX(), Factory.getConfigLocation("SkyWars.Lobby", Var.cfg).getBlockY(), Factory.getConfigLocation("SkyWars.Lobby", Var.cfg).getBlockZ());
        
        Global.setCountNumbers();
        Global.setInstance(this);
        Global.livesMap.put(5, 0);
        Global.livesMap.put(4, 1);
        Global.livesMap.put(3, 2);
        Global.livesMap.put(2, 3);
        Global.livesMap.put(1, 4);
        Global.livesMap.put(0, 5);
        Global.activePlayers.clear();
        Global.spectators.clear();
        for (final Player all : Bukkit.getOnlinePlayers()) {
            for (final Player p : Bukkit.getOnlinePlayers()) {
                all.showPlayer(p);
                Utils.checkForNewMatch(p);

            }
        }
        this.saveDefaultConfig();
    }
    
    @Override
	public void onDisable() {
        for (final World world : Bukkit.getWorlds()) {
            Bukkit.getServer().unloadWorld(world.getName(), true);
        }
        System.out.println("[LuckyRace] Plugin wurde deaktiviert");
    }
    
//    private boolean checkForNitCore() {
//        try {
//            if (!Bukkit.getPluginManager().getPlugin("NitCore").isEnabled()) {
//                return false;
//            }
//        }
//        catch (NullPointerException e) {
//            return false;
//        }
//        return true;
//    }
    
    public void registerEvents() {
        new BlockPlaceListener(this);
        new JoinListener(this);
        new BlockBreakListener(this);
        new DeathListener(this);
        new LeaveListener(this);
        new DamageListener(this);
        new FoodListener(this);
        new MoveListener(this);
        new RespawnListener(this);
        new PreJoinListener(this);
        new KitInventory();
        new ChatListener(this);
        new PlayerItemConsumeListener(this);
        new EntityDeathListener(this);
        new PlayerItemHeldListener(this);
        new PlayerDamageListener(this);
        new InteractListener(this);
        new InventoryListener(this);
        new EntityDamageByEntityListener(this);
        new Utils();

    }
    
    public void registerCommands() {
        final StartCommand startCommand = new StartCommand(this);
        this.getCommand("start").setExecutor(startCommand);
        final BuildCommand buildCommand = new BuildCommand(this);
        this.getCommand("build").setExecutor(buildCommand);
        final LRCommand pvpcmd = new LRCommand(this);
        this.getCommand("skip").setExecutor(pvpcmd);
        final CMDSetSpawn setspawn = new CMDSetSpawn();
        this.getCommand("setspawn").setExecutor(setspawn);
        final CMDSetLocations setlocation = new CMDSetLocations();
        this.getCommand("setlocation").setExecutor(setlocation);
    }
    
    public static LuckyRace getInstance() {
        return LuckyRace.instance;
    }
}
