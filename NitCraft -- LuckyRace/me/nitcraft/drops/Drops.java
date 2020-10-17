package me.nitcraft.drops;

import java.util.function.BiConsumer;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.BrewingStand;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Bat;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.ExperienceOrb;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Giant;
import org.bukkit.entity.MagmaCube;
import org.bukkit.entity.PigZombie;
import org.bukkit.entity.Player;
import org.bukkit.entity.Sheep;
import org.bukkit.entity.Skeleton;
import org.bukkit.entity.Spider;
import org.bukkit.entity.Witch;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;

import de.slimeplex.plexutils.system.base.objects.ItemBuilder;
import de.slimeplex.plexutils.system.entity.objects.EntityBuilder;
import me.nitcraft.luckyrace.Global;
import me.nitcraft.utils.Var;

@SuppressWarnings("deprecation")
public class Drops {

	public static void registerDrops() {

		new LuckyBlockDrop(10)

				/* entity spawnen */
				.addEntity(new BiConsumer<Player, Block>() {
					@Override
					public void accept(Player p, Block b) {
						new EntityBuilder(Giant.class, b.getLocation()).setDisplayName("§eT-REX").build();
					}
				});

		new LuckyBlockDrop(12)

				.addBlock(Material.ANVIL);

		new LuckyBlockDrop(10)

				.addBlock(Material.ENCHANTMENT_TABLE);
		
		new LuckyBlockDrop(15)

		.addBlock(Material.WORKBENCH);

		new LuckyBlockDrop(15)

				.addEntity(new BiConsumer<Player, Block>() {
					@Override
					public void accept(Player p, Block b) {
						new EntityBuilder(ExperienceOrb.class, b.getLocation()).build();
						new EntityBuilder(ExperienceOrb.class, b.getLocation()).build();
						new EntityBuilder(ExperienceOrb.class, b.getLocation()).build();

					}
				});

//		new LuckyBlockDrop(10)
//
//				.addEntity(new BiConsumer<Player, Block>() {
//					@Override
//					public void accept(Player p, Block b) {
//						new EntityBuilder(MushroomCow.class, b.getLocation()).build();
//						new EntityBuilder(MushroomCow.class, b.getLocation()).build();
//						new EntityBuilder(MushroomCow.class, b.getLocation()).build();
//
//						new Action
//					}
//					
//				});

		new LuckyBlockDrop(30)

				/* item spawnen */
				.addItemDrop(new ItemBuilder(Material.DIAMOND_SWORD).setDisplayName("§eLucky Sword")
						.addEnchantment(Enchantment.DAMAGE_ALL, 1).addEnchantment(Enchantment.DURABILITY, 1).build());

		new LuckyBlockDrop(15)

				.addItemDrop(new ItemBuilder(Material.DIAMOND_CHESTPLATE).setDisplayName("§eLucky Chestplate")
						.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1)
						.addEnchantment(Enchantment.DURABILITY, 1).build());

		new LuckyBlockDrop(20)

				.addItemDrop(new ItemBuilder(Material.DIAMOND_LEGGINGS).setDisplayName("§eLucky Leggings")
						.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1)
						.addEnchantment(Enchantment.DURABILITY, 1).build());

		new LuckyBlockDrop(25)

				.addItemDrop(new ItemBuilder(Material.DIAMOND_BOOTS).setDisplayName("§eLucky Boots")
						.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1)
						.addEnchantment(Enchantment.DURABILITY, 1).build());

		new LuckyBlockDrop(25)

				.addItemDrop(new ItemBuilder(Material.DIAMOND_HELMET).setDisplayName("§eLucky Helmet")
						.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1)
						.addEnchantment(Enchantment.DURABILITY, 1).build());

		new LuckyBlockDrop(25)

				.addItemDrop(new ItemBuilder(Material.GOLDEN_APPLE).setAmount(2).build());

		new LuckyBlockDrop(45)

				.addItemDrop(new ItemBuilder(Material.IRON_PICKAXE).addEnchantment(Enchantment.DIG_SPEED, 1).build());

		new LuckyBlockDrop(1)

				.addItemDrop(new ItemBuilder(Material.DIAMOND_CHESTPLATE)
						.setDisplayName("§4§k::: §4Illuminati Chestplate §4§k:::")
						.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 5)
						.addEnchantment(Enchantment.DAMAGE_ALL, 7).build());

		new LuckyBlockDrop(5)

				.addItemDrop(new ItemBuilder(Material.DIAMOND_LEGGINGS)
						.setDisplayName("§4§k::: §4Illuminati Leggings §4§k:::")
						.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4)
						.addEnchantment(Enchantment.PROTECTION_FIRE, 2).build());

		new LuckyBlockDrop(6)

				.addItemDrop(new ItemBuilder(Material.DIAMOND_SWORD)
						.setDisplayName("§4§k::: §4Illuminati Sword §4§k:::").addEnchantment(Enchantment.DAMAGE_ALL, 3)
						.addEnchantment(Enchantment.FIRE_ASPECT, 2).build());

		new LuckyBlockDrop(25)

				.addItemDrop(new ItemBuilder(Material.LEATHER_CHESTPLATE)
						.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1)
						.addEnchantment(Enchantment.DURABILITY, 1).build())
				.addItemDrop(new ItemBuilder(Material.LEATHER_LEGGINGS)
						.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1)
						.addEnchantment(Enchantment.DURABILITY, 1).build())
				.addItemDrop(
						new ItemBuilder(Material.LEATHER_BOOTS).addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1)
								.addEnchantment(Enchantment.DURABILITY, 1).build())
				.addItemDrop(
						new ItemBuilder(Material.LEATHER_HELMET).addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1)
								.addEnchantment(Enchantment.DURABILITY, 1).build());

		new LuckyBlockDrop(20)

				.addItemDrop(new ItemBuilder(Material.CHAINMAIL_CHESTPLATE).build())
				.addItemDrop(new ItemBuilder(Material.CHAINMAIL_LEGGINGS).build())
				.addItemDrop(new ItemBuilder(Material.CHAINMAIL_BOOTS).build())
				.addItemDrop(new ItemBuilder(Material.CHAINMAIL_HELMET).build());

		new LuckyBlockDrop(15)

				.addItemDrop(new ItemBuilder(Material.GOLD_CHESTPLATE).build())
				.addItemDrop(new ItemBuilder(Material.GOLD_LEGGINGS).build())
				.addItemDrop(new ItemBuilder(Material.GOLD_BOOTS).build())
				.addItemDrop(new ItemBuilder(Material.GOLD_HELMET).build());

		new LuckyBlockDrop(10)

				.addItemDrop(new ItemBuilder(Material.IRON_CHESTPLATE).build())
				.addItemDrop(new ItemBuilder(Material.IRON_LEGGINGS).build())
				.addItemDrop(new ItemBuilder(Material.IRON_BOOTS).build())
				.addItemDrop(new ItemBuilder(Material.IRON_HELMET).build());

		new LuckyBlockDrop(20)

				.addItemDrop(
						new ItemBuilder(Material.GOLD_AXE).setDisplayName("§cStreitaxt der Stärke").setUnbreakable(true)
								.addLore("§eAuf den ersten Blick wirkt diese Waffe sehr gewöhnlich.").build());

		new LuckyBlockDrop(29)

				.addItemDrop(new ItemBuilder(Material.POTION).setSubID(2).setDisplayName("§bLucky Potion")
						.addLore("§cEin mysteriöser Trank...").build());

		new LuckyBlockDrop(40)

				.addItemDrop(new ItemBuilder(Material.POTION).setSubID(3).setDisplayName("§6Heal Potion")
						.addLore("§eDen würde ich lieber nicht weg werfen...").build());

		new LuckyBlockDrop(15)

				.addItemDrop(new ItemBuilder(Material.GOLD_SWORD).setDisplayName("§2Giftdolch").setUnbreakable(true)
						.addLore("§eEin scharfer Dolch, bestrichen mit starkem Gift.").build());

		new LuckyBlockDrop(8)

				.addItemDrop(new ItemBuilder(Material.FISHING_ROD).build());
		
		
		new LuckyBlockDrop(10, Sound.ITEM_PICKUP, 10F, 10F)

		/* schematic */

		.addEntity(new BiConsumer<Player, Block>() {
			@Override
			public void accept(Player p, Block b) {
				p.setMaxHealth(p.getMaxHealth() + 2D);
				p.sendMessage(Var.pr + "§7Du hast ein §c❤ §7erhalten");
				p.playSound(p.getLocation(), Sound.LEVEL_UP, 10.0f, 5.0f);

			}
		});

		new LuckyBlockDrop(18, Sound.ITEM_PICKUP, 10F, 10F)

				/* schematic */

				.addSchematic(new LuckyBlockSchematic("Brunnen", false))
				.addItemDrop(new ItemBuilder(Material.DIAMOND).setAmount(5).build())
				.addEntity(new BiConsumer<Player, Block>() {
					@Override
					public void accept(Player p, Block b) {
						new EntityBuilder(Firework.class, b.getLocation()).build();
						new EntityBuilder(Firework.class, b.getLocation()).build();
						new EntityBuilder(Firework.class, b.getLocation()).build();

					}
				});
		
		new LuckyBlockDrop(32, Sound.SHEEP_IDLE, 10F, 10F)

		/* schematic */

		.addItemDrop(new ItemBuilder(Material.DIAMOND).setAmount(5).build())
		.addEntity(new BiConsumer<Player, Block>() {
			
			@Override
			public void accept(Player p, Block b) {
				new EntityBuilder(Sheep.class, b.getLocation()).build();
				new EntityBuilder(Sheep.class, b.getLocation()).build();
				new EntityBuilder(Sheep.class, b.getLocation()).build();

			}
		});
		
		new LuckyBlockDrop(39, Sound.SHEEP_IDLE, 10F, 10F)

		/* schematic */

		.addEntity(new BiConsumer<Player, Block>() {
			
			@Override
			public void accept(Player p, Block b) {
				new EntityBuilder(Creeper.class, b.getLocation()).build();

			}
		});
		
		new LuckyBlockDrop(30, Sound.SPIDER_DEATH, 10F, 10F)

		/* schematic */

		.addEntity(new BiConsumer<Player, Block>() {
			
			@Override
			public void accept(Player p, Block b) {
				spawnSpiderArmy(b.getLocation(),p);
				
			}
		});
		
		new LuckyBlockDrop(35, Sound.SPIDER_DEATH, 10F, 10F)

		/* schematic */

		.addEntity(new BiConsumer<Player, Block>() {
			
			@Override
			public void accept(Player p, Block b) {
				spawnPigmans(b.getLocation(),p);
				
			}
		});
		
		new LuckyBlockDrop(25, Sound.SPIDER_DEATH, 10F, 10F)

		/* schematic */

		.addEntity(new BiConsumer<Player, Block>() {
			
			@Override
			public void accept(Player p, Block b) {
				spawnSkeletonOnSpider(b.getLocation(),p);
			}
		});
		
		new LuckyBlockDrop(28, Sound.SPIDER_DEATH, 10F, 10F)

		/* schematic */

		.addEntity(new BiConsumer<Player, Block>() {
			
			@Override
			public void accept(Player p, Block b) {
				spawnHexe(b.getLocation());
			}
		});
		
		
		new LuckyBlockDrop(6, Sound.SPIDER_DEATH, 10F, 10F)

		/* schematic */

		.addEntity(new BiConsumer<Player, Block>() {
			
			@Override
			public void accept(Player p, Block b) {
				spawnBrewingStand2(b.getLocation());
			}
		});
		
		new LuckyBlockDrop(5, Sound.SPIDER_DEATH, 10F, 10F)

		/* schematic */

		.addEntity(new BiConsumer<Player, Block>() {
			
			@Override
			public void accept(Player p, Block b) {
				spawnBrewingStand(b.getLocation());
			}
		});
		
		
		
		
		
		
		
		

		new LuckyBlockDrop(10, Sound.PORTAL_TRAVEL, 2F, 2F)

				/* effect */
				.addEffect(new PotionEffect(PotionEffectType.CONFUSION, 200, 50000));

		new LuckyBlockDrop(10, Sound.WITHER_IDLE, 2F, 2F)

				/* throw effect */
				.addThrowPotion(new PotionEffect(PotionEffectType.SLOW, 200, 50000));
		        

	}

	// Schuhe
	// Op-Potions
	// OP-Potions-Evil
	// Kühe
	// Edgar
	// Giftsterne
	// Eisterne
	// Rauchbombe
	// Essen
	// Lagerfeuer
	// Käfig
	// Käfig + Lava
	// Potions
	// Mystery Potion
	// Nuke
	// TNT Bogen
	// Lucky Pig


	public static void spawnSkeletonOnSpider(Location location, Player p) {
		Bukkit.getScheduler().scheduleSyncDelayedTask(Global.instance, new Runnable() {

			@Override
			public void run() {
				Spider spider = (Spider) location.getWorld().spawnEntity(location, EntityType.SPIDER);
				Skeleton rider = (Skeleton) location.getWorld().spawnEntity(location, EntityType.SKELETON);
				rider.getEquipment().setChestplate(new ItemStack(Material.IRON_CHESTPLATE));
				rider.getEquipment().setBoots(new ItemStack(Material.IRON_BOOTS));
				rider.getEquipment().setLeggings(new ItemStack(Material.IRON_LEGGINGS));
				rider.getEquipment().setHelmet(new ItemStack(Material.IRON_HELMET));
				rider.getEquipment().setItemInHandDropChance(1);
				rider.setCustomName("§6Spinnenreiter");
				spider.setPassenger(rider);
				spider.setTarget(p);
				rider.setTarget(p);
			}

		}, 1L);
	}

	public static void spawnSpiderArmy(Location location, Player p) {
		Bukkit.getScheduler().scheduleSyncDelayedTask(Global.instance, new Runnable() {

			@Override
			public void run() {
				Spider spider = (Spider) location.getWorld().spawnEntity(location, EntityType.SPIDER);
				Spider spider1 = (Spider) location.getWorld().spawnEntity(location, EntityType.SPIDER);
				Spider spider2 = (Spider) location.getWorld().spawnEntity(location, EntityType.SPIDER);
				Spider spider3 = (Spider) location.getWorld().spawnEntity(location, EntityType.SPIDER);
				Spider spider4 = (Spider) location.getWorld().spawnEntity(location, EntityType.SPIDER);
				Spider spider5 = (Spider) location.getWorld().spawnEntity(location, EntityType.SPIDER);
				Spider spider6 = (Spider) location.getWorld().spawnEntity(location, EntityType.SPIDER);
				Spider spider7 = (Spider) location.getWorld().spawnEntity(location, EntityType.SPIDER);
				Spider spider8 = (Spider) location.getWorld().spawnEntity(location, EntityType.SPIDER);
				Spider spider9 = (Spider) location.getWorld().spawnEntity(location, EntityType.SPIDER);

				Skeleton rider = (Skeleton) location.getWorld().spawnEntity(location, EntityType.SKELETON);
				rider.getEquipment().setChestplate(new ItemStack(Material.DIAMOND_CHESTPLATE));
				rider.getEquipment().setBoots(new ItemStack(Material.IRON_BOOTS));
				rider.getEquipment().setLeggings(new ItemStack(Material.DIAMOND_LEGGINGS));
				rider.getEquipment().setHelmet(new ItemStack(Material.IRON_HELMET));
				rider.getEquipment().setItemInHandDropChance(0);
				spider.setPassenger(rider);
				spider.setTarget(p);
				spider2.setTarget(p);
				spider3.setTarget(p);
				spider4.setTarget(p);
				spider5.setTarget(p);
				spider6.setTarget(p);
				spider7.setTarget(p);
				spider8.setTarget(p);
				spider9.setTarget(p);


				rider.setCustomName("§6Spider §cArmy §7- §4Anführer");
				spider1.setCustomName("§6Spider §cArmy");
				spider2.setCustomName("§6Spider §cArmy");
				spider3.setCustomName("§6Spider §cArmy");
				spider4.setCustomName("§6Spider §cArmy");
				spider5.setCustomName("§6Spider §cArmy");
				spider6.setCustomName("§6Spider §cArmy");
				spider7.setCustomName("§6Spider §cArmy");
				spider8.setCustomName("§6Spider §cArmy");
				spider9.setCustomName("§6Spider §cArmy");
			}

		}, 1L);
	}

	public static void spawnHexe(Location location) {
		Bukkit.getScheduler().scheduleSyncDelayedTask(Global.instance, new Runnable() {

			@Override
			public void run() {
				Witch hexe = (Witch) location.getWorld().spawnEntity(location, EntityType.WITCH);
				location.getWorld().spawnEntity(location, EntityType.LIGHTNING);
				Bat spider3 = (Bat) location.getWorld().spawnEntity(location, EntityType.BAT);
				Bat spider4 = (Bat) location.getWorld().spawnEntity(location, EntityType.BAT);
				Bat spider5 = (Bat) location.getWorld().spawnEntity(location, EntityType.BAT);
				Bat spider6 = (Bat) location.getWorld().spawnEntity(location, EntityType.BAT);
				Bat spider7 = (Bat) location.getWorld().spawnEntity(location, EntityType.BAT);
				Bat spider8 = (Bat) location.getWorld().spawnEntity(location, EntityType.BAT);

				MagmaCube rider = (MagmaCube) location.getWorld().spawnEntity(location, EntityType.MAGMA_CUBE);
				rider.getEquipment().setItemInHandDropChance(0);
				hexe.setPassenger(rider);

				rider.setCustomName("§6Begleiter");
				spider3.setCustomName("§6Fledermaus");
				spider4.setCustomName("§6Fledermaus");
				spider5.setCustomName("§6Fledermaus");
				spider6.setCustomName("§6Fledermaus");
				spider7.setCustomName("§6Fledermaus");
				spider8.setCustomName("§6Fledermaus");
			}

		}, 1L);
	}
	public static void spawnPigmans(Location location, Player p) {
		Bukkit.getScheduler().scheduleSyncDelayedTask(Global.instance, new Runnable() {

			@Override
			public void run() {
				PigZombie hexe = (PigZombie) location.getWorld().spawnEntity(location, EntityType.PIG_ZOMBIE);
				PigZombie hexe2 = (PigZombie) location.getWorld().spawnEntity(location, EntityType.PIG_ZOMBIE);
				PigZombie hexe3 = (PigZombie) location.getWorld().spawnEntity(location, EntityType.PIG_ZOMBIE);
				hexe.setTarget(p);
				hexe2.setTarget(p);
				hexe3.setTarget(p);

			}

		}, 1L);
	}






	public static void spawnBrewingStand(Location loc) {
		loc.getBlock().setType(Material.BREWING_STAND);
		BrewingStand stand = (BrewingStand) loc.getBlock().getState();
		Potion pot = new Potion(PotionType.INSTANT_HEAL, 2, true);
		stand.getInventory().setItem(0, pot.toItemStack(1));
		stand.getInventory().setItem(1, pot.toItemStack(1));
		stand.getInventory().setItem(2, pot.toItemStack(1));
	}


	public static void spawnBrewingStand2(Location loc) {
		loc.getBlock().setType(Material.BREWING_STAND);
		BrewingStand stand = (BrewingStand) loc.getBlock().getState();
		Potion pot = new Potion(PotionType.INSTANT_DAMAGE, 2, true);
		stand.getInventory().setItem(0, pot.toItemStack(1));
		stand.getInventory().setItem(1, pot.toItemStack(1));
		stand.getInventory().setItem(2, pot.toItemStack(1));
	}




	/*
	 * @EventHandler public void playerBowEvent23(PlayerInteractEvent e) { Player p
	 * = e.getPlayer();
	 * 
	 * if ((e.getAction() == Action.RIGHT_CLICK_AIR) || (e.getAction() ==
	 * Action.RIGHT_CLICK_BLOCK)) { if (e.getItem().getType() ==
	 * Material.DIAMOND_AXE) { Location location = p.getLocation(); World world =
	 * p.getWorld(); world.strikeLightning(location); e.getItem().getItemMeta(). }
	 * else if (e.getAction() == Action.LEFT_CLICK_AIR) {
	 * 
	 * Block block = e.getPlayer().getTargetBlock((HashSet<Byte>) null, 50);
	 * Location blockLocation = block.getLocation(); World world = p.getWorld();
	 * world.strikeLightning(blockLocation); } else {
	 * 
	 * } } }
	 */

}
