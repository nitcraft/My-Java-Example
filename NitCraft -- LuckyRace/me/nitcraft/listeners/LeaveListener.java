package me.nitcraft.listeners;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;

import de.slimeplex.nitcore.utils.objects.NitUser;
import me.nitcraft.luckyrace.GameState;
import me.nitcraft.luckyrace.Global;
import me.nitcraft.luckyrace.LuckyRace;
import me.nitcraft.luckyrace.LuckyRaceSection;
import me.nitcraft.luckyrace.LuckyRaceSection.Achievement;
import me.nitcraft.utils.Utils;

public class LeaveListener implements Listener {

	@SuppressWarnings("unused")
	private LuckyRace plugin;

	public LeaveListener(LuckyRace plugin) {
		this.plugin = plugin;
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}

	@EventHandler
	public void onLeave(PlayerQuitEvent e) {

		Player p = e.getPlayer();
		e.setQuitMessage(null);
		
//		StatsManager StatsManager = new StatsManager("LuckyRace");
//		StatsManager.addValue(p.getUniqueId().toString(), "BreakedBlocks", Var.breakedBlocks.get(p).intValue());

		
		if (JoinListener.VoteRace.contains(p)) {
			JoinListener.Race.remove(1);
			JoinListener.VoteRace.remove(p);
		}
	    if (JoinListener.VoteGLP.contains(p)) {
			JoinListener.GermanLetsPlay.remove(1);
			JoinListener.VoteGLP.remove(p);
			
	    }
		if (Global.activePlayers.contains(p)) {
			e.setQuitMessage("§4\u2770\u2770 §8" + p.getName());
			Global.activePlayers.remove(p);
		}

		if (Global.activePlayers.size() == 1) {
			Utils u = new Utils();
			u.setWinner(Global.activePlayers.get(0));
			Bukkit.getScheduler().cancelTask(Global.compassTask);
		}

		if (!GameState.isState(GameState.LOBBY)) {
			if (GameState.isState(GameState.PVP)) {
				if (!Global.spectators.contains(p)) {
					p.getLocation().getBlock().setType(Material.SPONGE);
					ArrayList<ItemStack> drops = new ArrayList<ItemStack>();

					for (int i = 0; i < p.getInventory().getContents().length; i++) {
						drops.add(p.getInventory().getContents()[i]);
					}

					for (int i = 0; i < p.getInventory().getArmorContents().length; i++) {
						drops.add(p.getInventory().getArmorContents()[i]);
					}

					Global.lootByKillMap.put(p.getLocation().getBlock(), drops);
					if (Global.activePlayers.size() == 1) {
						Utils u = new Utils();
						u.setWinner(Global.activePlayers.get(0));
						NitUser user = new NitUser(Global.activePlayers.get(0).getUniqueId().toString());
						LuckyRaceSection section = (LuckyRaceSection) user.getSection(new LuckyRaceSection(Global.activePlayers.get(0)));
						if (!section.hasAchievement(Achievement.FIRST_WIN)) {
							section.addAchievement(Achievement.FIRST_WIN);
						}
					}

					Utils.resetPvpScore();
					Utils.sendScoreboard();
				} else {
					Global.spectators.remove(p);
					e.setQuitMessage(null);
				}

			} else if(GameState.isState(GameState.INGAME)) {
				Utils.sendPvpScoreBoard(Global.deathMatchCountInt);
			}

		}
	}
}