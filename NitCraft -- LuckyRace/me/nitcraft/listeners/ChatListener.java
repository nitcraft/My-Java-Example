package me.nitcraft.listeners;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import de.slimeplex.nitcore.utils.objects.NitUser;
import me.nitcraft.luckyrace.GameState;
import me.nitcraft.luckyrace.LuckyRace;
import me.nitcraft.luckyrace.LuckyRaceSection;
import me.nitcraft.luckyrace.LuckyRaceSection.Achievement;

public class ChatListener implements Listener {

	@SuppressWarnings("unused")
	private LuckyRace plugin;

	public ChatListener(LuckyRace plugin) {
		this.plugin = plugin;
		Bukkit.getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler
	public void chat(AsyncPlayerChatEvent e) {
		if (GameState.isState(GameState.RESTART)) {
			if (e.getMessage().contentEquals("gg")) {
			NitUser user = new NitUser(e.getPlayer().getUniqueId().toString());
			LuckyRaceSection section = (LuckyRaceSection) user.getSection(new LuckyRaceSection(e.getPlayer()));
			if (!section.hasAchievement(Achievement.FAIRPLAY)) {
				section.addAchievement(Achievement.FAIRPLAY);
				user.save();

			}
		}
		}

	}
}
