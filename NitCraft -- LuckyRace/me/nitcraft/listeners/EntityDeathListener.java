package me.nitcraft.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.ExplosionPrimeEvent;
import org.bukkit.inventory.ItemStack;

import me.nitcraft.luckyrace.LuckyRace;
import net.minecraft.server.v1_8_R3.Explosion;

public class EntityDeathListener implements Listener{

	@SuppressWarnings("unused")
	private LuckyRace plugin;
	
	public EntityDeathListener(LuckyRace plugin) {
		this.plugin = plugin;
		Bukkit.getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler
	public void onDeath(EntityDeathEvent e){
		e.getDrops().clear();
		if(e.getEntity().getCustomName()!= null) {
			if(e.getEntity().getCustomName().contains("Lucky Cow")) {
				e.getDrops().clear();
				e.setDroppedExp(0);
				e.getDrops().add(0, new ItemStack(Material.GOLDEN_APPLE));
			}
		}
	}
		
	
		@EventHandler
		public void onDeath2(EntityDeathEvent e){
			e.getDrops().clear();
			if(e.getEntity().getCustomName()!= null) {
				if(e.getEntity().getCustomName().contains("Lucky Pig")) {
					e.getDrops().clear();
					e.setDroppedExp(0);
					e.getDrops().add(0, new ItemStack(Material.DIAMOND));
				}
			}
	}
	
}
