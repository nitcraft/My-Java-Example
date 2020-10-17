package me.nitcraft.constructor;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import me.nitcraft.luckyrace.Global;
import me.nitcraft.utils.Utils;

public class Respawn {

	private Player p;
	private int task;
	private int high;

	public Respawn(Player p) {
		this.p = p;
		start();
	}

	@SuppressWarnings("deprecation")
	public void start() {
		high = 4;
		task = Bukkit.getScheduler().scheduleSyncRepeatingTask(Global.instance, new Runnable() {
			@Override
			public void run() {
				if (high != 0) {
					high--;

					if (high == 3 || (high == 2) || (high == 1)) {
						p.setHealth(20);
						p.sendTitle("§cWiedereintritt in", "§a" + high);
						p.playSound(p.getLocation(), Sound.CLICK, 10.0F, 10.0F);

					}
				} else {
					p.spigot().setCollidesWithEntities(true);
					p.playSound(p.getLocation(), Sound.WITHER_SHOOT, 10.0F, 5.0F);
					p.setHealth(p.getMaxHealth());
					p.setFoodLevel(20);
					p.getActivePotionEffects().clear();
					p.setGameMode(GameMode.SURVIVAL);
					p.setAllowFlight(false);
					p.setFlying(false);
					//Utils.activePlayers.add(p);
					Global.activePlayers.add(p);

					Global.spectators.remove(p);
					
					for (Player all : Global.activePlayers) {
						all.showPlayer(p);
					}
					for (Player all : Bukkit.getOnlinePlayers()) {
						all.showPlayer(p);
					}
					p.playSound(p.getLocation(), Sound.WITHER_SHOOT, 10.0F, 5.0F);
					Utils.sendScoreboard();

					Utils.MapVoting2(p);

					stop();

				}


			}
		}, 0L, 20L);
	}

	public void stop() {
		Bukkit.getScheduler().cancelTask(task);
	}

}
