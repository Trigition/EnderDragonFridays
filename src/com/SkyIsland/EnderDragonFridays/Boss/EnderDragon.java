package com.SkyIsland.EnderDragonFridays.Boss;


import java.util.HashMap;
import java.util.UUID;

import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;

import com.SkyIsland.EnderDragonFridays.EnderDragonFridaysPlugin;
import com.SkyIsland.EnderDragonFridays.Boss.Cannon.FireballCannon;
import com.SkyIsland.EnderDragonFridays.Boss.Component.TargetType;

public class EnderDragon extends Dragon {

	
	
	/**
	 * Creates a default enderdragon
	 * @param plugin The EnderDragonFridays plugin this dragon is associated with
	 * @param level The level of the dragon
	 * @param loc
	 * @param name it's name
	 */
	public EnderDragon(World world, int level, String name) {
		
		this.chestAreaBL = world.getSpawnLocation();
		
		this.damageTaken = 0;
		
		//Ensure level is a positive integer
		if (level <= 0) {
			level = 1;
		}
		this.level = level;

		//Spawn an ender dragon
		dragon = (LivingEntity) world.spawnEntity(world.getSpawnLocation().add(0, 50, 0), EntityType.ENDER_DRAGON);
		
		//Set the dragon's name
		if (name != null && name.length() > 0) {
			dragon.setCustomName(name + " (Lvl " + level + ")");
			dragon.setCustomNameVisible(true);
		}
		
		//Set the dragon's health
		dragon.setMaxHealth(dragon.getMaxHealth() * (1 + (Math.log(level)/Math.log(2))));
		dragon.setHealth(dragon.getMaxHealth());

		//Initialize the map of damage each player does to the dragon
		damageMap = new HashMap<UUID, Double>();
		
		//Start firing the dragon's fireballs
		//Bukkit.getScheduler().scheduleSyncRepeatingTask(EnderDragonFridaysPlugin.plugin, new FireballCannon(this, 500, 2000), 20, (long) (20 / (1 + (Math.log(level)/Math.log(2)))));
		//Removed ^^ and handle this in FireballCannon instead
		
		new FireballCannon(this, TargetType.MOSTDAMAGE, (20 / (1 + (Math.log(level)/Math.log(2)))), (20 / (1 + (Math.log(level)/Math.log(2)))) + 5);
		//least delay is what it was before. Max is the same + 5 ticks
		
		EnderDragonFridaysPlugin.plugin.getServer().getPluginManager().registerEvents(this, EnderDragonFridaysPlugin.plugin);

	}
	
}
