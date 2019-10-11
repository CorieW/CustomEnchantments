package me.core.CustomEnchantments;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.Material;

public class CustomEnchantment {
	
	public static ArrayList<CustomEnchantment> customEnchantments = new ArrayList<CustomEnchantment>();
	
	public String name;
	public int minLevel;
	public ArrayList<Material> possibleItemsTypes;
	public double probability;
	public ArrayList<BannedEnchantment> bannedEnchantments;
	public ArrayList<ReplaceableEnchantment> replaceableEnchantments;
	
	public CustomEnchantment(String name, int minLevel, ArrayList<Material> possibleItemsTypes, double probability, ArrayList<BannedEnchantment> bannedEnchantments, ArrayList<ReplaceableEnchantment> replaceableEnchantments) 
	{
		name = RarityColorizer(name, probability);
		
		this.name = name;
		this.minLevel = minLevel;
		this.possibleItemsTypes = possibleItemsTypes;
		this.probability = probability;
		
		if(bannedEnchantments != null) 
		{
			this.bannedEnchantments = bannedEnchantments;
		}
		else 
		{
			this.bannedEnchantments = new ArrayList<BannedEnchantment>();
		}
		
		if(replaceableEnchantments != null) 
		{
			this.replaceableEnchantments = replaceableEnchantments;
		}
		else 
		{
			this.replaceableEnchantments = new ArrayList<ReplaceableEnchantment>();
		}
		
		customEnchantments.add(this);
	}
	
	// Colorize the enchantment based on their rarity
	public static String RarityColorizer(String name, double probability) 
	{
		if(probability > 12.5) { // Common
			name = ChatColor.GRAY + name;
		}
		else if(probability > 2.5) { // Uncommon
			name = ChatColor.WHITE + name;
		}
		else if(probability > 0.5) { // Rare
			name = ChatColor.GREEN + name;
		}
		else if(probability > 0.01) { // Legendary
			name = ChatColor.YELLOW + name;
		}
		else { // Mythical
			name = ChatColor.RED + name;
		}
		return name;
	}
	
	// Attempts to find a custom enchantment by input enchantment name
	public static CustomEnchantment FindCustomEnchantment(String enchantmentName) 
	{
		for(CustomEnchantment customEnchantment : customEnchantments) 
		{
			if(customEnchantment.name.equals(enchantmentName)) 
			{
				return customEnchantment;
			}
		}
		
		return null;
	}
	
}
