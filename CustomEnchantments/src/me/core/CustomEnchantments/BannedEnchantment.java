package me.core.CustomEnchantments;

import org.bukkit.enchantments.Enchantment;

public class BannedEnchantment 
{ // A banned enchantment prevents the enchantment from being added if a banned enchantment is already on the item
	public Enchantment bannedEnchantment;
	public CustomEnchantment bannedCustomEnchantment;
	
	public BannedEnchantment(Enchantment bannedEnchantment) 
	{
		this.bannedEnchantment = bannedEnchantment;
	}
	
	public BannedEnchantment(CustomEnchantment bannedCustomEnchantment) 
	{
		this.bannedCustomEnchantment = bannedCustomEnchantment;
	}

	public static boolean PreventAddingBannedCustomEnchantment() {
		// TODO Auto-generated method stub
		return false;
	}
}
 /*
  * Silk Touch to Smelter - Smelter banned Silk Touch, deny
  * Smelter to Silk Touch - Smelter banned Silk Touch, deny
  * 
  * Speed to Super Speed - Speed banned Super Speed, deny
  * Super Speed to Speed - Super Speed replaced Speed so -Speed +Super Speed
  * 
  * More scenarios to be added
  */


/*
 * Replaced runthrough
 * 	Get rid of replaced
 * Banned runthrough
 * 	Get rid of banned
 */
/*
 * Opposite item replacing should take priority
 * Item banning should take priority
 */