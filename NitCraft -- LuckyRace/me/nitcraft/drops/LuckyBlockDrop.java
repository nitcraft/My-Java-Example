package me.nitcraft.drops;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.entity.ThrownPotion;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;

import de.slimeplex.plexutils.system.world.objects.schematic.SchematicBuilder;
import de.slimeplex.plexutils.system.world.objects.schematic.SchematicDirection;

public class LuckyBlockDrop {
	
	
	// .addPlayerAction
	
	
	private List<BiConsumer<Player, Block>> entityList;
	private List<Material> blockList;
	private Sound sound;
	private float pitch, volume;
	private List<BiConsumer<Player, Block>> customActionList;
	private List<LuckyBlockSchematic> schematicList;
	private List<PotionEffect> throwPotionList;
	private List<PotionEffect> potionEffectList;
	private List<ItemStack> itemList;
	private int chance;
	
	
	/* constructor */ 
	public LuckyBlockDrop(int chance) {
		this.entityList = new ArrayList<>();
		this.itemList = new ArrayList<>();
		this.customActionList = new ArrayList<>();
		this.throwPotionList = new ArrayList<>();
		this.blockList = new ArrayList<>();
		this.schematicList = new ArrayList<>();
		this.potionEffectList = new ArrayList<>();
		this.chance = chance;
		LuckyBlockDropManager.dropList.add(this);
	}
	
	/* constructor */ 
	public LuckyBlockDrop(int chance, Sound sound, float pitch, float volume) {
		this.entityList = new ArrayList<>();
		this.sound = sound;
		this.pitch = pitch;
		this.volume = volume;
		this.itemList = new ArrayList<>();
		this.throwPotionList = new ArrayList<>();
		this.customActionList = new ArrayList<>();
		this.blockList = new ArrayList<>();
		this.schematicList = new ArrayList<>();
		this.potionEffectList = new ArrayList<>();
		this.chance = chance;
		LuckyBlockDropManager.dropList.add(this);
	}
	
	
	
	/* modules */
	public LuckyBlockDrop addEntity(BiConsumer<Player, Block> action) {
		entityList.add(action);
		return this;
	}
	
	public LuckyBlockDrop addItemDrop(ItemStack item) {
		itemList.add(item);
		return this;
	}
	
	public LuckyBlockDrop addBlock(Material material) {
		blockList.add(material);
		return this;
	}
	
	public LuckyBlockDrop addEffect(PotionEffect potionEffect) {
		potionEffectList.add(potionEffect);
		return this;
	}
	
	public LuckyBlockDrop addSchematic(LuckyBlockSchematic schematic) {
		schematicList.add(schematic);
		return this;
	}
	
	public LuckyBlockDrop addThrowPotion(PotionEffect potionEffect) {
		throwPotionList.add(potionEffect);
		return this;
	}
	
	public LuckyBlockDrop addCustomAction(BiConsumer<Player, Block> action) {
		customActionList.add(action);
		return this;
	}
	
	
	
	/* getter */
	public int getChance() {
		return chance;
	}
	
	public Sound getSound() {
		return sound;
	}
	
	public float getPitch() {
		return pitch;
	}
	
	
	public float getVolume() {
		return volume;
	}
	
	
	
	@SuppressWarnings("deprecation")
	public void spawn(Player p, Block b) {
		for(BiConsumer<Player, Block> action : entityList) {
			action.accept(p, b);
		}
		
		for(ItemStack item : itemList) {
			Item dropItem = b.getLocation().getWorld().dropItem(b.getLocation().add(0.5, 2, 0.5), item);
			if(item.getItemMeta() != null && item.getItemMeta().getDisplayName() != null) {
				dropItem.setCustomName(item.getItemMeta().getDisplayName());
				dropItem.setCustomNameVisible(true);
			}
		}
		
		for(Material material : blockList) {
			b.getLocation().getWorld().spawnFallingBlock(b.getLocation().clone().add(0, 10, 0), material, (byte) 0);
		}
		
		for(PotionEffect potionEffect : potionEffectList) {
			p.addPotionEffect(potionEffect);
		}
		
		for(LuckyBlockSchematic schematic : schematicList) {
			SchematicBuilder builder = new SchematicBuilder(schematic.getFile());
			if(schematic.isSpawnByPlayer()) {
				builder.paste(SchematicDirection.getSchematicDirectionFromPlayer(p), p.getLocation(), true);
			} else {
				builder.paste(SchematicDirection.SOUTH, b.getLocation(), true);
			}
		}
		
		for(PotionEffect effect : throwPotionList) {
			ItemStack item = new ItemStack(Material.POTION);
			PotionMeta meta = (PotionMeta) item.getItemMeta();
			meta.addCustomEffect(effect, true);
			item.setItemMeta(meta);
			ThrownPotion thrownPotion = (ThrownPotion) p.getLocation().getWorld().spawnEntity(b.getLocation(), EntityType.SPLASH_POTION);
			thrownPotion.setItem(item);
			thrownPotion.setBounce(true);
		}
		
		for(BiConsumer<Player, Block> action : customActionList) {
			action.accept(p, b);
		}
	}

}
