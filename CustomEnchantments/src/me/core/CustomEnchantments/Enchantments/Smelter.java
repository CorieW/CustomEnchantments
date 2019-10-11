package me.core.CustomEnchantments.Enchantments;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.core.CustomEnchantments.BannedEnchantment;
import me.core.CustomEnchantments.CustomEnchantment;
import me.core.CustomEnchantments.Main;

public class Smelter implements Listener
{
	
	public static CustomEnchantment enchantment;
	
	public Smelter() 
	{
		Bukkit.getServer().getPluginManager().registerEvents(this, Main.plugin);
		
		// Setting up it's custom enchantment ...
		String name = "Smelter";
		int minLevel = 3;
		
		ArrayList<Material> possibleItemTypes = new ArrayList<Material>();
		possibleItemTypes.add(Material.DIAMOND_PICKAXE);
		possibleItemTypes.add(Material.GOLD_PICKAXE);
		possibleItemTypes.add(Material.IRON_PICKAXE);
		possibleItemTypes.add(Material.STONE_PICKAXE);
		possibleItemTypes.add(Material.WOOD_PICKAXE);
		
		double probability = 15;
		
		ArrayList<BannedEnchantment> bannedEnchantments = new ArrayList<BannedEnchantment>();
		bannedEnchantments.add(new BannedEnchantment(Enchantment.SILK_TOUCH));
		
		enchantment = new CustomEnchantment(name, minLevel, possibleItemTypes, probability, bannedEnchantments, null);
		// ...
	}
	
	// Returns a material which will be refined, if it can be refined else returns material entered
	static Material refined(Material material) 
	{
		if(material == Material.STONE) {
			return Material.STONE;
		}
		else if(material == Material.IRON_ORE) {
			return Material.IRON_INGOT;
		}
		else if(material == Material.GOLD_ORE) {
			return Material.GOLD_INGOT;
		}
		else {
			return material;
		}
	}
	
	@EventHandler
	public void onBlockBreakEvent(BlockBreakEvent event) 
	{
		if(event.getPlayer().getGameMode().equals(GameMode.SURVIVAL)) 
		{
			if(event.getPlayer().getItemInHand().hasItemMeta()) 
			{
				ItemMeta itemMeta = event.getPlayer().getItemInHand().getItemMeta();
				for(String lore : itemMeta.getLore()) 
				{
					if(lore.equals(enchantment.name)) 
					{
						Material material = event.getBlock().getType();
						event.getBlock().setType(Material.AIR); // Prevents normal drop
						
						Location blockLoc = event.getBlock().getLocation();
						event.getBlock().getWorld().dropItem(new Location(event.getBlock().getWorld(), blockLoc.getX() + 0.5f, blockLoc.getY() + 0.5f, blockLoc.getZ() + 0.5f), new ItemStack(refined(material), 1)); // Makes itemstack drop naturally					
					}
				}
			}
		}
	}
}
