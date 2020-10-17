package me.nitcraft.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import de.slimeplex.nitcore.utils.objects.NitUser;
import de.slimeplex.plexutils.system.base.objects.ItemBuilder;
import me.nitcraft.luckyrace.Global;
import me.nitcraft.luckyrace.LuckyRace;
import me.nitcraft.luckyrace.LuckyRaceSection;
import me.nitcraft.luckyrace.LuckyRaceSection.Achievement;
import me.nitcraft.utils.Creator;
import me.nitcraft.utils.Settings;
import me.nitcraft.utils.Utils;
import me.nitcraft.utils.Var;

public class InteractListener implements Listener {


	private LuckyRace plugin;

	public InteractListener(LuckyRace plugin) {
		this.plugin = plugin;
		Bukkit.getServer().getPluginManager().registerEvents(this, plugin);
	}

	@EventHandler
	public void onInteract(PlayerInteractEvent e) {
		if(e.getPlayer().getItemInHand() == null) {
			return;
		}
		if (e.getPlayer().getItemInHand().getType().equals(Material.SLIME_BALL)) {
			if (e.getPlayer().getItemInHand().getItemMeta().getDisplayName().equalsIgnoreCase("verlassen")) {
				if (e.getAction().equals(Action.RIGHT_CLICK_AIR) || e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
					Utils.connect(e.getPlayer(), Global.hubServerName, plugin);
				}
			}
		} else if (e.getPlayer().getItemInHand().getType().equals(Material.REDSTONE_COMPARATOR)
				&& e.getAction().toString().contains("RIGHT") && e.getPlayer().getItemInHand().hasItemMeta()
				&& e.getPlayer().getItemInHand().getItemMeta().getDisplayName().contains("Einstellungen")) {
			if(Global.editor == null) {
				Global.editor = e.getPlayer();
				if(Global.lobbyCountInt >= 10) {
					Settings.openWelcomeInventory(e.getPlayer());
				} else {
					e.getPlayer().sendMessage(Global.prefix + "Das Spiel §6startet bereits§b.");
				}
			} else {
				e.getPlayer().sendMessage(Global.prefix + "§bEs bearbeitet bereits ein Spieler die §6Einstellungen§b.");
			}
		}
		if (e.getPlayer().getItemInHand().equals(new ItemBuilder(Material.FIREWORK).setDisplayName("§6>> §7Boost - §b" + Utils.BoostUses.get(e.getPlayer().getUniqueId()) + "§7 Nutzungen").setGlow().build())) { {
			Bukkit.broadcastMessage("Testi");
			if (e.getAction().equals(Action.RIGHT_CLICK_AIR) || e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
				e.setCancelled(true);
					Bukkit.broadcastMessage("Test1");
					if (Utils.BoostUses.get(e.getPlayer().getUniqueId()) > 1 || Utils.BoostUses.get(e.getPlayer().getUniqueId()) == 1) {
						if (e.getPlayer().getMaxHealth() > 3) {
						Vector v = e.getPlayer().getLocation().getDirection().normalize();
					     v = v.setY(Math.max(1.0D, v.getY())).multiply(1.9F);
					      e.getPlayer().setVelocity(v);
					      e.getPlayer().setMaxHealth(e.getPlayer().getMaxHealth() -1.0D);

					      
						Utils.BoostUses.put(e.getPlayer().getUniqueId(), Utils.BoostUses.get(e.getPlayer().getUniqueId() )-1);
					      e.getPlayer().getInventory().setItemInHand(new ItemBuilder(Material.FIREWORK).setDisplayName("§6>> §7Boost - §b" + Utils.BoostUses.get(e.getPlayer().getUniqueId()) + "§7 Nutzungen").setGlow().build());
					      e.getPlayer().updateInventory();	
					      e.getPlayer().sendMessage(Global.prefix + "§7Du zahlst mit einem §bhalben §c❤ §7um den Sprung zu tätigen");
					      e.getPlayer().playSound(e.getPlayer().getLocation(), Sound.BAT_LOOP, 10.0f, 10.0f);

						Bukkit.broadcastMessage(Utils.BoostUses.get(e.getPlayer().getUniqueId()) + "BoostUsesLeft");
						Bukkit.broadcastMessage("remove 1");
						if (Utils.BoostUses.get(e.getPlayer().getUniqueId()) == 0) {
							Bukkit.broadcastMessage("remove");
							e.getPlayer().getInventory().remove(e.getPlayer().getItemInHand());
						
						}
					} else {
						e.getPlayer().sendMessage(Var.pr + "§7Du hast zu wenig Herzen");
						e.getPlayer().getInventory().remove(e.getPlayer().getItemInHand());
						Utils.BoostUses.remove(e.getPlayer().getUniqueId());
					}
					}
				}
			}
		}
		
	}
	@EventHandler
	public void onInteract2(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		if (p.getItemInHand().getType() == Material.COMPASS) {
			if (((e.getAction() == Action.RIGHT_CLICK_BLOCK ? 1 : 0) | (e.getAction() == Action.RIGHT_CLICK_AIR ? 1 : 0)) != 0) {
				if (!Global.activePlayers.contains(p)) {
					Inventory inv = Bukkit.createInventory(null, 27, "§eIngame");
					int i = -1;
					LuckyRace.getInstance();
					for (Player all : Global.activePlayers) {
						i++;
						ItemStack Spieler = Creator.Skull("§e" + all.getName(), Material.SKULL_ITEM,
								"§8» §7Klicken zum teleportieren.", 1, (short) 3, all.getName());
						inv.setItem(i, Spieler);
						if (i == 10) {
							break;
						}
					}
					p.openInventory(inv);
				}
			}
		} else if (p.getItemInHand().getType() == Material.NETHER_STAR) {
			if (((e.getAction() == Action.RIGHT_CLICK_BLOCK ? 1 : 0) | (e.getAction() == Action.RIGHT_CLICK_AIR ? 1 : 0)) != 0) {
				if (p.getItemInHand().getItemMeta().getDisplayName().equalsIgnoreCase("§aErfolge")) {
				        final Inventory inv = Bukkit.createInventory((InventoryHolder)null, 9, "§eErfolge");
				        NitUser user = new NitUser(p.getUniqueId().toString());
						LuckyRaceSection section = (LuckyRaceSection) user.getSection(new LuckyRaceSection(p));
				        int current = 0;
				        for (final Achievement achievement : Achievement.values()) {
				            if (section.hasAchievement(achievement)) {
				                inv.setItem(current, new ItemBuilder(Material.INK_SACK).setSubID(10).setDisplayName("§a" + achievement.getName()).addLore("§7" + achievement.getDesc()).build());
				            }
				            else {
				                inv.setItem(current, new ItemBuilder(Material.INK_SACK).setSubID(8).setDisplayName("§c" + achievement.getName()).addLore("§7???").build());
				            }
				            ++current;
				        }
				        p.openInventory(inv);
				        return;
				    }
			}
		}
	}
}
