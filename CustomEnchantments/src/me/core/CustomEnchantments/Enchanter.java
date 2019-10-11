package me.core.CustomEnchantments;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Enchanter 
{	
	// Returns a string of an enchantment which can be added
	public static ItemStack GetEnchantment(ItemStack item, Map<Enchantment, Integer> enchantments, int level) // ! Only returns 1 enchantment
	{
		ItemStack newItem = item.clone();
		
		for(Map.Entry<Enchantment, Integer> enchantment : enchantments.entrySet()) // Add all enchantments to item
		{
			newItem.addEnchantment(enchantment.getKey(), enchantment.getValue());
		}
				
		ItemMeta newItemMeta = null;
		
		ArrayList<CustomEnchantment> addedCustomEnchantments = new ArrayList<CustomEnchantment>();
		
		if(newItem.hasItemMeta()) 
		{
			newItemMeta = newItem.getItemMeta();
			
			for(CustomEnchantment customEnchantment : CustomEnchantment.customEnchantments) 
			{
				if(customEnchantment.minLevel <= level) 
				{
					if(customEnchantment.possibleItemsTypes.contains(item.getType())) 
					{
						double rndNum = (double)new Random().nextInt(1000000)/10000;
						Bukkit.broadcastMessage(rndNum + "");
						if(rndNum < customEnchantment.probability) 
						{
							boolean safeToAdd = true;
							
							for(BannedEnchantment bannedEnchantment : customEnchantment.bannedEnchantments) // Checks for any banned enchantments
							{
								for(CustomEnchantment addedCustomEnchantment : addedCustomEnchantments) // Checks for any banned custom enchantments
								{
									if(bannedEnchantment.bannedCustomEnchantment != null) 
									{
										if(bannedEnchantment.bannedCustomEnchantment.equals(addedCustomEnchantment)) 
										{ // Can no longer add enchantment
											safeToAdd = false;
											break;
										}
									}
								}
								
								for(Enchantment enchantment : enchantments.keySet()) // Checks for any banned enchantments
								{
									if(bannedEnchantment.bannedEnchantment != null) 
									{
										if(bannedEnchantment.bannedEnchantment.equals(enchantment)) 
										{ // Can no longer add enchantment
											safeToAdd = false;
											break;
										}
									}
								}
							}
							
							for(ReplaceableEnchantment replaceableEnchantment : customEnchantment.replaceableEnchantments) // Checks for any replaceable enchantments
							{
								ArrayList<CustomEnchantment> remove = new ArrayList<CustomEnchantment>();
										
								for(CustomEnchantment addedCustomEnchantment : addedCustomEnchantments) // Checks for any replaceable custom enchantments
								{
									if(replaceableEnchantment.replaceableCustomEnchantment != null) 
									{
										if(replaceableEnchantment.replaceableCustomEnchantment.equals(addedCustomEnchantment)) 
										{ // Removes replaceable enchantment
											List<String> lores = newItemMeta.getLore();
											
											lores.remove(addedCustomEnchantment.name);
											newItemMeta.setLore(lores);	
											
											remove.add(addedCustomEnchantment); // Do this as it's impossible to iterate and modify a list simultaneously
										}
									}
								}
								addedCustomEnchantments.removeAll(remove);
								
								for(Enchantment enchantment : enchantments.keySet()) // Checks for any replaceable enchantments
								{
									if(replaceableEnchantment.replaceableEnchantment != null) 
									{
										if(replaceableEnchantment.replaceableEnchantment.equals(enchantment)) 
										{ // Removes replaceable enchantment
											newItemMeta.removeEnchant(enchantment);
										}
									}
								}
							}
							
							if(safeToAdd) // Custom enchantment can be added
							{
								List<String> lores = null;
								if(newItemMeta.hasLore()) 
								{
									lores = newItemMeta.getLore();
								}
								else 
								{
									lores = new ArrayList<String>();
								}
								
								lores.add(customEnchantment.name);
								newItemMeta.setLore(lores);	
								
								addedCustomEnchantments.add(customEnchantment);
								
								newItem.setItemMeta(newItemMeta);
							}
						}
					}
				}
			}
		}
		return newItem;
	}
}

/*
 * Before adding lore to item
 * 
 * Run over all custom enchantments and try give enchantments which meet probability, enchant level and are intended for that item
 * 	if enchantment meets custom enchantment min level
 * 		if enchantment meets custom enchantment capable items
 * 			if possibility is above custom enchantment probability
 * 				if custom enchantment has an added enchantment or custom enchantment on item which is banned
 * 					don't add custom enchant
 * 				else
 * 					if custom enchantment has an added enchantment or custom enchantment on item which is replaceable
 * 						add custom enchantment
 * 						remove replaceable enchant/custom enchantment
 */

