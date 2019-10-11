package me.core.CustomEnchantments.Enchantments;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Animals;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import me.core.CustomEnchantments.BannedEnchantment;
import me.core.CustomEnchantments.CustomEnchantment;
import me.core.CustomEnchantments.Main;

public class Poison implements Listener {

	public static CustomEnchantment enchantment;
	
	public Poison() 
	{
		Bukkit.getServer().getPluginManager().registerEvents(this, Main.plugin);
		
		// Setting up it's custom enchantment ...
		String name = "Poisoned Blade";
		int minLevel = 3;
		
		ArrayList<Material> possibleItemTypes = new ArrayList<Material>();
		possibleItemTypes.add(Material.DIAMOND_SWORD);
		possibleItemTypes.add(Material.GOLD_SWORD);
		possibleItemTypes.add(Material.IRON_SWORD);
		possibleItemTypes.add(Material.STONE_SWORD);
		possibleItemTypes.add(Material.WOOD_SWORD);
		
		double probability = 2.5;
		
		ArrayList<BannedEnchantment> bannedEnchantments = new ArrayList<BannedEnchantment>();
		bannedEnchantments.add(new BannedEnchantment(Enchantment.SILK_TOUCH));
		
		enchantment = new CustomEnchantment(name, minLevel, possibleItemTypes, probability, bannedEnchantments, null);
		// ...
	}
	
	@EventHandler
	public void EntityDamageByEntity(EntityDamageByEntityEvent event) 
	{
		if(event.getDamager() instanceof Player) 
		{
			Player player = (Player) event.getDamager();
			
			if(player.getItemInHand().hasItemMeta()) 
			{
				ItemMeta itemMeta = player.getItemInHand().getItemMeta();
				for(String lore : itemMeta.getLore()) 
				{
					if(lore.equals(enchantment.name)) 
					{
						if(event.getEntity() instanceof Animals) 
						{
							Animals hitAnimal = (Animals) event.getEntity();
							hitAnimal.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 140, 10));
						}
						else if(event.getEntity() instanceof Monster) 
						{
							Monster hitMonster = (Monster) event.getEntity();
							hitMonster.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 140, 1));
						}
						else if(event.getEntity() instanceof Player) 
						{
							Player hitPlayer = (Player) event.getEntity();
							hitPlayer.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 140, 1));
						}
					}
				}
					
			}
		}
	}
	
}
