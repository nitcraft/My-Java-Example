package me.nitcraft.utils;

import org.bukkit.Sound;
import org.bukkit.entity.Player;

import de.slimeplex.nitcore.utils.objects.NitUser;
import me.nitcraft.luckyrace.LuckyRaceSection;
import me.nitcraft.luckyrace.LuckyRaceSection.Kits;

public class KitSelector {

	public static void setKit(Kits name, String name2, Player p) {
		NitUser user = new NitUser(p.getUniqueId().toString());
		LuckyRaceSection section = (LuckyRaceSection) user.getSection(new LuckyRaceSection(p));

		section.addKitSelect(name);
	}

	@SuppressWarnings("static-access")
	public static void pickKit(Player p) {

		NitUser user = new NitUser(p.getUniqueId().toString());
		LuckyRaceSection section = (LuckyRaceSection) user.getSection(new LuckyRaceSection(p));

		if (section.hasKitSelect(Kits.DESCENDIO)) {
			section.playerKit.put(p, Kits.DESCENDIO);
			
			
			
		}
		user.save();

	}
	
	@SuppressWarnings("static-access")
	public static void removeKit(Player p) {

		NitUser user = new NitUser(p.getUniqueId().toString());
		LuckyRaceSection section = (LuckyRaceSection) user.getSection(new LuckyRaceSection(p));

		if (section.hasKitSelect(Kits.DESCENDIO)) {
			section.playerKit.remove(p, Kits.DESCENDIO);
				
			
			
			
			
		}
		
		user.save();
	}
	

	@SuppressWarnings("static-access")
	public static void sendConfirmation(final Player p, final Kits fairplay) {
	        
		NitUser user = new NitUser(p.getUniqueId().toString());
		LuckyRaceSection section = (LuckyRaceSection) user.getSection(new LuckyRaceSection(p));

		section.playerKit.put(p, fairplay);
		p.sendMessage(Var.pr + "§7Du hast das §aKit §7" + fairplay.getName() + " ausgewählt!");

		p.playSound(p.getLocation(), Sound.PISTON_EXTEND, 1.0f, 1.0f);
		
		user.save();
	}
}
