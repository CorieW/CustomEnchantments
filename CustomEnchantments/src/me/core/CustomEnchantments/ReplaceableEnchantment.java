package me.core.CustomEnchantments;

import org.bukkit.enchantments.Enchantment;

public class ReplaceableEnchantment 
{ // A replaceable enchantment is an enchantment which can be replaced with another enchantment
	public Enchantment replaceableEnchantment;
	public CustomEnchantment replaceableCustomEnchantment;
	
	public ReplaceableEnchantment(Enchantment replaceableEnchantment) 
	{
		this.replaceableEnchantment = replaceableEnchantment;
	}
	
	public ReplaceableEnchantment(CustomEnchantment replaceableCustomEnchantment) 
	{
		this.replaceableCustomEnchantment = replaceableCustomEnchantment;
	}
}
