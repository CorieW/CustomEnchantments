package me.core.CustomEnchantments.Enchantments;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import me.core.CustomEnchantments.CustomEnchantment;
import me.core.CustomEnchantments.Main;
import me.core.CustomEnchantments.ReplaceableEnchantment;

public class SuperSpeed
{
	
	public static CustomEnchantment enchantment;
	
	public SuperSpeed() 
	{		
		// Setting up it's custom enchantment ...
		String name = "Super Speed";
		int minLevel = 30;
		
		ArrayList<Material> possibleItemTypes = new ArrayList<Material>();
		possibleItemTypes.add(Material.DIAMOND_BOOTS);
		possibleItemTypes.add(Material.GOLD_BOOTS);
		possibleItemTypes.add(Material.IRON_BOOTS);
		possibleItemTypes.add(Material.CHAINMAIL_BOOTS);
		possibleItemTypes.add(Material.LEATHER_BOOTS);
		
		double probability = 2;
		
		ArrayList<ReplaceableEnchantment> replaceableEnchantment = new ArrayList<ReplaceableEnchantment>();
		replaceableEnchantment.add(new ReplaceableEnchantment(Speed.enchantment));
		
		enchantment = new CustomEnchantment(name, minLevel, possibleItemTypes, probability, null, replaceableEnchantment);
		// ...
		
		// Begin task
		SuperSpeedRunnable task = new SuperSpeedRunnable();
		task.runTaskTimer(Main.plugin, 0, 1);
	}
	
	class SuperSpeedRunnable extends BukkitRunnable
	{
	    @Override
	    public void run(){
	        for(Player player : Bukkit.getOnlinePlayers()) 
	        {
	    		if(player.getGameMode().equals(GameMode.SURVIVAL)) 
	    		{
	    			if(player.getInventory().getBoots() != null) 
	    			{
		    			if(player.getInventory().getBoots().hasItemMeta()) 
		    			{
		    				ItemMeta itemMeta = player.getInventory().getBoots().getItemMeta();
		    				for(String lore : itemMeta.getLore()) 
		    				{
		    					if(lore.equals(enchantment.name)) 
		    					{
		    						player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 60, 1));
		    					}
		    				}
		    			}
	    			}
	    		}
	        }
	    }
	}
}