package me.nitcraft.kits;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;

import de.slimeplex.nitcore.utils.objects.NitUser;
import de.slimeplex.plexutils.system.base.objects.ItemBuilder;
import me.nitcraft.luckyrace.Global;
import me.nitcraft.luckyrace.LuckyRaceSection;
import me.nitcraft.luckyrace.LuckyRaceSection.Kits;
import me.nitcraft.utils.KitSelector;

public class KitInventory implements Listener {

	@SuppressWarnings({ "static-access", "deprecation" })
	@EventHandler
	public void Click(InventoryClickEvent e) {
		Player p = (Player) e.getWhoClicked();
		NitUser user = new NitUser(p.getUniqueId().toString());
		LuckyRaceSection section = (LuckyRaceSection) user.getSection(new LuckyRaceSection(p));
		String invName = e.getView().getTitle();


		
		for (Kits kit : Kits.values()) {
			if (invName.equalsIgnoreCase(kit.getName() + " Übersicht")
					|| invName.equalsIgnoreCase(kit.getName() + " Kaufen")) {
				e.setCancelled(true);
				Inventory inv = Bukkit.createInventory(null, 9, kit.getName() + " Übersicht");
				inv.setItem(0, new ItemBuilder(kit.getMaterial()).setDisplayName("§b" + kit.getName()).addLoreArray(kit.getDesc()).build());
				inv.setItem(8, new ItemBuilder(Material.SKULL_ITEM)
				.setSkullOwner("MHF_CHEST")
				.setDisplayName("§7" + kit.getName() + " Kaufen")
				.addLoreArray("§7Für §b" + kit.getCosts() + "§7 Kaufen?").build());

				p.openInventory(inv);
			
			//p.playSound(p.getLocation(), Sound.CLICK, 10.0f, 10.0f);
			}
		}
		

		for (Kits kit : Kits.values()) {
			if (invName.equalsIgnoreCase(kit.getName() + " Übersicht")
					|| invName.equalsIgnoreCase(kit.getName() + " Kaufen")) {
				e.setCancelled(true);
			}

			if (!section.hasKit(kit)) {
				if (e.getCurrentItem().getItemMeta().getDisplayName()
						.equalsIgnoreCase("§7" + kit.getName() + " Kaufen")) {
					buyKitInventory(p, kit);
				}
			}

			if (e.getCurrentItem().getItemMeta().getDisplayName()
					.equalsIgnoreCase("§aJa, " + kit.getName() + " Kaufen")) {
				buyKit(p, kit);
			}
		}
			}


	public static void buyKitInventory(Player p, Kits kit) {
		Inventory inv = Bukkit.createInventory(null, InventoryType.CHEST, kit.getName() + " Kaufen");

		inv.setItem(10, new ItemBuilder(Material.INK_SACK).setDisplayName("§aJa, " + kit.getName() + " Kaufen").build());
		inv.setItem(13, new ItemBuilder(Material.PAPER).setGlow().setDisplayName("§7Möchtest du das Kaufen?").build());
		inv.setItem(16, new ItemBuilder(Material.INK_SACK).setSubID(1).setDisplayName("§cNein").build());
		p.playSound(p.getLocation(), Sound.CLICK, 10.0f, 10.0f);

		p.openInventory(inv);
	}

	public static void buyKit(Player p, Kits kit) {
		NitUser user = new NitUser(p.getUniqueId().toString());
		LuckyRaceSection section = (LuckyRaceSection) user.getSection(new LuckyRaceSection(p));

		double getCoins = new NitUser(p.getUniqueId().toString()).getCoins();
		if (kit.getCosts().length() < getCoins || kit.getCosts().length() == getCoins) {
			section.addKit(kit);

			new NitUser(p.getUniqueId().toString()).removeCoins(kit.getCosts().length());
			p.playSound(p.getLocation(), Sound.BAT_DEATH, 10.0f, 5.0f);
			p.playSound(p.getLocation(), Sound.LEVEL_UP, 10.0f, 5.0f);
			user.save();
		} else {
			p.sendMessage(Global.prefix + "§cDu hast nicht genügend Coins");
			p.playSound(p.getLocation(), Sound.CHICKEN_EGG_POP, 10.0f, 10.0f);

		}

		p.closeInventory();

	}

	public static void getKitItems(Player p, Kits kit, String string) {
		Inventory inv = Bukkit.createInventory(null, 9, kit.getName() + " Übersicht");

		inv.setItem(0,
				new ItemBuilder(kit.getMaterial()).setDisplayName("§b" + kit.getName()).addLoreArray(string).build());
		inv.setItem(8,
				new ItemBuilder(Material.SKULL_ITEM).setSkullOwner("MHF_CHEST")
						.setDisplayName("§7" + kit.getName() + " Kaufen")
						.addLoreArray("§7Für §b" + kit.getCosts() + "§7 Kaufen?").build());

		p.openInventory(inv);

	}

	
	@SuppressWarnings({ "static-access", "deprecation" })
	@EventHandler
	public void click(InventoryClickEvent e) {
		Player p = (Player) e.getWhoClicked();
		NitUser user = new NitUser(p.getUniqueId().toString());
		LuckyRaceSection section = (LuckyRaceSection) user.getSection(new LuckyRaceSection(p));

		for (Kits kit : Kits.values()) {
		if (e.isLeftClick() && e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§a" + kit.getName() + " §7| " + "§a§nGekauft")) {
			if (section.hasKit(kit)) {
				if (section.hasKitSelect(kit)) {
					section.removeKitSelect(kit);
					p.sendMessage(Global.prefix + "§cDu hast nun " + kit.getName() + " nicht mehr ausgerüstet!");
					section.playerKit.remove(p, kit);
					user.save();
				} else if (!section.hasKitSelect(kit)) {
						if (section.getKitsSelect().size() == 1) {
						     p.closeInventory();
						     p.sendMessage(Global.prefix + "§cDu hast schon ein Kit ausgerüstet");
					    } else {
					section.addKitSelect(kit);
					KitSelector.pickKit(p);
					user.save();
					p.sendMessage(Global.prefix + "§aDu hast nun " + kit.getName() + " ausgerüstet!");
						}
					}
				p.closeInventory();
				}
			}
		}
	}
}
