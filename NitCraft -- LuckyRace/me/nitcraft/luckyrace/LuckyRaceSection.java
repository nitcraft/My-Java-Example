package me.nitcraft.luckyrace;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bson.Document;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import de.slimeplex.nitcore.utils.sections.UserSection;

public class LuckyRaceSection implements UserSection {

	private Document document;
	private Document achievements;
	private Document kits;
	private Document kitselect;

	private Player p;
	private String p1;

	public static HashMap<Player, Kits> playerKit = new HashMap<>();


	public LuckyRaceSection(Player p) {
		this.p = p;
	}
	public LuckyRaceSection(String p1) {
		this.p1 = p1;
	}

	@Override
	public void loadDefaultDocument() {
		achievements = new Document();
		document = new Document("Achievements", new Document()).append("Kits", new Document()).append("KitSelect", new Document());
       
	}

	@Override
	public void loadDocument(Document document) {
		this.document = document;
		this.achievements = (Document) document.get("Achievements");
		this.kits = (Document) document.get("Kits");
		this.kitselect = (Document) document.get("KitSelect");

	}

	@Override
	public Document getDocument() {
		return document;
	}

	
	
	public void addAchievement(Achievement firstRound) {
		achievements.append(String.valueOf(firstRound.getId()), true);
		document.replace("Achievements", achievements);

		p.sendMessage(Global.prefix + "§f§koskdosdkopdskspdkdpskdpks");
		p.sendMessage(Global.prefix + "");
		p.sendMessage(Global.prefix + "§7Du hast ein §aAchievement §7erhalten§8: ");
		p.sendMessage(Global.prefix + "§a" + firstRound.getName());
		p.sendMessage(Global.prefix + "");
		p.sendMessage(Global.prefix + "§a" + firstRound.getDesc());
		p.sendMessage(Global.prefix + "§a  + 50 Moneten!              ");
		p.sendMessage(Global.prefix + "");
		p.sendMessage(Global.prefix + "§f§koskdosdkopdskspdkdpskdpks");

		p.playSound(p.getLocation(), Sound.CHEST_OPEN, 1.0f, 1.0f);
	}

	public boolean hasAchievement(Achievement achievement) {
		return achievements.containsKey(achievement.getId() + "");


	}

	public List<Achievement> getAchievements() {
		List<Achievement> list = new ArrayList<>();
		for (String s : achievements.keySet()) {
			list.add(Achievement.fromId(Integer.valueOf(s)));
		}
		return list;
	}

	public enum Achievement {
		FIRST_ROUND("Spiele deine erste Runde", 0, "Erste Runde"), 
		FAIRPLAY("Schreibe ,,GG`` in den Chat", 1, "Fairplay"),
		FIRST_WIN("Gewinne eine LuckyRace Runde", 2, "Bester Mann!"),
		FIRST_JUMPANDRUN("Erreiche das Ende von einem Race", 3, "Profi"),
		FIRST_HEART("Töte einen Spieler mit einem halben Herz", 4, "Das war Knapp");
		
		private String desc;
		private int id;
		private String name;

		Achievement(final String desc, final int id, final String name) {
			this.desc = desc;
			this.id = id;
			this.name = name;
		}

		public static Achievement fromString(String s) {
			for (final Achievement achievement : values()) {
				if (achievement.getName().equalsIgnoreCase(s)) {
					return achievement;
				}
			}
			return null;
		}

		public static Achievement fromId(final int i) {
			for (final Achievement achievement : values()) {
				if (achievement.getId() == i) {
					return achievement;
				}
			}
			return null;
		}

		public int getId() {
			return this.id;
		}

		public String getDesc() {
			return this.desc;
		}

		public String getName() {
			return this.name;
		}
	}
	
	

	public void addKit(Kits Starter) {
		kits.append(String.valueOf(Starter.getId()), true);
		p.sendMessage(Global.prefix + "§kokskdosdkopdskspdkdpskdpks");
		p.sendMessage(Global.prefix + "");
		p.sendMessage(Global.prefix + "§7Du hast ein §aKit §7gekauft§8: ");
		p.sendMessage(Global.prefix + "§a" + Starter.getName());
		p.sendMessage(Global.prefix + "");
		p.sendMessage(Global.prefix + "§a" + Starter.getDesc());
		p.sendMessage(Global.prefix + "§a  + 50 Coins!              ");
		p.sendMessage(Global.prefix + "");
		p.sendMessage(Global.prefix + "§koskdosdkopdskspdkdpskdpks");

		p.playSound(p.getLocation(), Sound.ANVIL_LAND, 1.0f, 1.0f);
	}

	public boolean hasKit(Kits kit) {
		return kits.containsKey(kit.getId() + "");
	}

	public List<Kits> getKits() {
		List<Kits> list = new ArrayList<>();
		for (String s : kits.keySet()) {
			list.add(Kits.fromId(Integer.valueOf(s)));
		}
		return list;
	}
	
	public void addKitSelect(Kits Starter) {
		kitselect.append(String.valueOf(Starter.getId()), true);
		document.replace("KitSelect", kitselect);
		
	}
	
	public void removeKit(Kits Starter) {
		kits.remove(String.valueOf(Starter.getId()));
		document.replace("Kits", kits);
		
	}
	
	public void removeKitSelect(Kits Starter) {
		kitselect.remove(String.valueOf(Starter.getId()));
		document.replace("KitSelect", kitselect);
		
	}

	public boolean hasKitSelect(Kits kit) {
		return kitselect.containsKey(kit.getId() + "");
	}
	

	public List<Kits> getKitsSelect() {
		List<Kits> list = new ArrayList<>();
		for (String s : kitselect.keySet()) {
			list.add(Kits.fromId(Integer.valueOf(s)));
		}
		return list;
	}
	
	public enum Kits {
		DESCENDIO("§7- Steckt Gegner in Flammen", 0, "Descendio", Material.ENCHANTED_BOOK, "[Kostenlos]", 0);
		
		private String desc;
		private int id;
		private String name;
		private Material material;
		private String costs;
		private int lvl;


		private Kits(final String desc, final int id, final String name, final Material material, final String costs, final int lvl) {
			this.desc = desc;
			this.id = id;
			this.name = name;
			this.material = material;
			this.costs = costs;
			this.lvl = lvl;
		}

		public static Kits fromString(String s) {
			for (final Kits kits : values()) {
				if (kits.getName().equalsIgnoreCase(s)) {
					return kits;
				}
			}
			return null;
		}

		public static Kits fromId(final int i) {
			for (final Kits kits : values()) {
				if (kits.getId() == i) {
					return kits;
				}
			}
			return null;
		}

		public int getId() {
			return this.id;
		}

		public int getLvl() {
			return this.lvl;
		}
		
		public String getDesc() {
			return this.desc;
		}

		public String getCosts() {
			return this.costs;
		}

		public String getName() {
			return this.name;
		}

		public Material getMaterial() {
			return this.material;
		}
	}

}
