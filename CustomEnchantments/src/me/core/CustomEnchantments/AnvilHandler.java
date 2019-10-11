package me.core.CustomEnchantments;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.AnvilInventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

public class AnvilHandler extends BukkitRunnable // Might need optimized in the future, currently has many for loops and updates constantly when anvil is open
{ // Could be joint with a oninventoryclick listener as well as a list, so when a player clicks that's when the requires an updating
	
    @Override
    public void run(){
	    // check whether the event has been cancelled by another plugin
		for(Player onlinePlayer : Bukkit.getOnlinePlayers())
		{
			if(onlinePlayer.getOpenInventory().getTopInventory() instanceof AnvilInventory){
				AnvilInventory anvil = (AnvilInventory)onlinePlayer.getOpenInventory().getTopInventory();
								 
				ItemStack[] items = anvil.getContents();
				 
				// item in the left slot
				ItemStack item1 = items[0];

				// item in the right slot
				ItemStack item2 = items[1];

				// I do not know if this is necessary
				if(item1 != null && item2 != null){
					Material id1 = item1.getType();
					Material id2 = item2.getType();
					 
					// if the player is repairing something the ids will be the same
					if(id1 != null && id2 != null){

					  // item in the result slot
						ItemStack item3 = null;
						if(anvil.getItem(3) != null) 
						{
							item3 = anvil.getItem(3);
							TransferEnchantments(item1, item2, item3);
						}
					}
				}
			}
		}
		
		/*if(!e.isCancelled()){
			// not really necessary
			// see if we are talking about an anvil here
			if(e.getDestination() instanceof AnvilInventory){
				AnvilInventory anvil = (AnvilInventory)e.getDestination();
								 
				ItemStack[] items = anvil.getContents();
				 
				// item in the left slot
				ItemStack item1 = items[0];
				Bukkit.broadcastMessage((item1 == null) + "");

				// item in the right slot
				ItemStack item2 = items[1];
				Bukkit.broadcastMessage((item2 == null) + "");

				// I do not know if this is necessary
				if(item1 != null && item2 != null){
					Material id1 = item1.getType();
					Material id2 = item2.getType();
					 
					// if the player is repairing something the ids will be the same
					if(id1 != null && id2 != null){

					  // item in the result slot
						ItemStack item3 = null;
						if(anvil.getItem(3) != null) 
						{
							item3 = anvil.getItem(3);
							TransferEnchantments(item1, item2, item3);
						}
						 
						// check if there is an item in the result slot
						if(item3 != null){
							ItemMeta meta = item3.getItemMeta();
							 										
							// meta data could be null
							if(meta != null){
									
								// get the repairable interface to obtain the repair cost
								if(meta instanceof Repairable){
									Repairable repairable = (Repairable)meta;
									int repairCost = repairable.getRepairCost();
								 
									// can the player afford to repair the item
									if(player.getGameMode().equals(GameMode.SURVIVAL)) 
									{
										if(player.getLevel() >= repairCost){
											player.sendMessage(ChatColor.GREEN + "Items have been successfully merged");
										}else{
											player.sendMessage(ChatColor.RED + "You need more exp levels to do this");
										}
									}
									else 
									{ // in creative the player will always have sufficient experience
										player.sendMessage(ChatColor.GREEN + "Items have been successfully merged");
									}
								}
							}
						}
					}
				}
			}
		}*/
	}
	
	
		
	static void TransferEnchantments(ItemStack item1, ItemStack item2, ItemStack item3) 
	{	
		item2 = item2.clone();
		
		@SuppressWarnings("unused")
		ItemMeta item1Meta = item1.getItemMeta();
		ArrayList<CustomEnchantment> item1CustomEnchantments = new ArrayList<CustomEnchantment>();
		if(item1.getItemMeta().hasLore()) 
		{
			for(String customEnchantmentName : item1.getItemMeta().getLore())
			{
				CustomEnchantment customEnchantment = CustomEnchantment.FindCustomEnchantment(customEnchantmentName);
				if(customEnchantment != null) 
				{
					item1CustomEnchantments.add(customEnchantment);
				}
			}
		}
		
		ItemMeta item2Meta = item2.getItemMeta();
		ArrayList<CustomEnchantment> item2CustomEnchantments = new ArrayList<CustomEnchantment>();
		if(item2.getItemMeta().hasLore()) 
		{
			for(String customEnchantmentName : item2.getItemMeta().getLore())
			{
				CustomEnchantment customEnchantment = CustomEnchantment.FindCustomEnchantment(customEnchantmentName);
				if(customEnchantment != null) 
				{
					item2CustomEnchantments.add(customEnchantment);
				}
			}
		}
		
		ItemMeta item3Meta = item3.getItemMeta();
		ArrayList<CustomEnchantment> item3CustomEnchantments = new ArrayList<CustomEnchantment>();
		if(item3.getItemMeta().hasLore()) 
		{
			for(String customEnchantmentName : item3.getItemMeta().getLore())
			{
				CustomEnchantment customEnchantment = CustomEnchantment.FindCustomEnchantment(customEnchantmentName);
				if(customEnchantment != null) 
				{
					item3CustomEnchantments.add(customEnchantment);
				}
			}
		}
		
		// Run through item3 enchantments
			// Check if item1 has it
				// if no, remove it
		
		for(Enchantment item3Enchantment : item3.getEnchantments().keySet()) 
		{
			boolean itemHasItem3Enchant = false;
			for(Enchantment item1Enchantment : item1.getEnchantments().keySet()) 
			{
				if(item1Enchantment.getName().equals(item3Enchantment.getName())) 
				{
					itemHasItem3Enchant = true;
					break;
				}
			}
			if(!itemHasItem3Enchant) 
			{
				for(CustomEnchantment item1CustomEnchantment : item1CustomEnchantments) 
				{
					for(BannedEnchantment bannedEnchantment : item1CustomEnchantment.bannedEnchantments) 
					{
						if(bannedEnchantment.bannedEnchantment != null) 
						{
							if(bannedEnchantment.bannedEnchantment.equals(item3Enchantment)) 
							{
								item3Meta.removeEnchant(item3Enchantment);
							}
						}
					}
				}
			}
		}
		
		for(CustomEnchantment item2CustomEnchantment : item2CustomEnchantments) 
		{
			item3CustomEnchantments.add(item2CustomEnchantment);
		}
	
		for(CustomEnchantment item3CustomEnchantment : item3CustomEnchantments) 
		{			
			for(CustomEnchantment item2CustomEnchantment : item2CustomEnchantments) // Only custom enchants
			{
				boolean _break = false;
				boolean _continue = false;
				
				for(BannedEnchantment bannedEnchantment : item2CustomEnchantment.bannedEnchantments) // Each of the adding item's banned enchantments
				{
					if(bannedEnchantment.bannedCustomEnchantment != null) 
					{
						if(bannedEnchantment.bannedCustomEnchantment.equals(item3CustomEnchantment)) // Adding item enchantment has banned an enchantment on the end result item
						{
							if(item3Meta.hasLore()) 
							{
								List<String> lores = item2Meta.getLore();
								lores.remove(item2CustomEnchantment.name);
								item2Meta.setLore(lores);	
								
								_continue = true;
							}
						}
					}
				}
				
				if(_continue)
					continue;
								
				for(ReplaceableEnchantment replaceableEnchantment : item2CustomEnchantment.replaceableEnchantments)
				{
					if(replaceableEnchantment.replaceableCustomEnchantment != null) 
					{
						if(replaceableEnchantment.replaceableCustomEnchantment.equals(item3CustomEnchantment))
						{
							if(item3Meta.hasLore()) 
							{
								List<String> lores = item3Meta.getLore();
								lores.remove(replaceableEnchantment.replaceableCustomEnchantment.name);
								item3Meta.setLore(lores);
								
								_break = true;
							}
						}
					}
				}
				
				if(_break)
					break;
				
				for(BannedEnchantment bannedEnchantment : item3CustomEnchantment.bannedEnchantments) // Each of the end item's banned enchantments
				{
					if(bannedEnchantment.bannedCustomEnchantment != null) 
					{
						if(bannedEnchantment.bannedCustomEnchantment.equals(item2CustomEnchantment)) // End item enchantment has banned an enchantment on the adding item
						{
							if(item2Meta.hasLore()) 
							{
								List<String> lores = item2Meta.getLore();
								lores.remove(bannedEnchantment.bannedCustomEnchantment.name);
								item2Meta.setLore(lores);		
								
								_continue = true;
							}
						}
					}
				}
				
				if(_continue)
					continue;
				
				for(ReplaceableEnchantment replaceableEnchantment : item3CustomEnchantment.replaceableEnchantments)
				{
					if(replaceableEnchantment.replaceableCustomEnchantment != null) 
					{
						if(replaceableEnchantment.replaceableCustomEnchantment.equals(item2CustomEnchantment))
						{
							List<String> lores = item2Meta.getLore();
							lores.remove(replaceableEnchantment.replaceableCustomEnchantment.name);
							item2Meta.setLore(lores);	
							
							_continue = true;
						}
					}
				}
				
				if(_continue)
					continue;
				
				
				
				// Normal Enchantments
				for(Enchantment item2Enchantment : item2.getEnchantments().keySet()) 
				{
					for(Enchantment item3Enchantment : item3.getEnchantments().keySet()) // Only custom enchants
					{
						for(BannedEnchantment bannedEnchantment : item2CustomEnchantment.bannedEnchantments) // Each of the adding item's banned enchantments
						{
							if(bannedEnchantment.bannedEnchantment != null) 
							{
								if(bannedEnchantment.bannedEnchantment.equals(item3Enchantment)) // Adding item enchantment has banned an enchantment on the end result item
								{
									if(item2Meta.hasLore()) 
									{
										List<String> lores = item2Meta.getLore();
										lores.remove(bannedEnchantment.bannedCustomEnchantment.name);
										item2Meta.setLore(lores);	
										
										_continue = true;
									}
								}
							}
						}
						
						if(_continue)
							continue;
						
						for(ReplaceableEnchantment replaceableEnchantment : item2CustomEnchantment.replaceableEnchantments)
						{
							if(replaceableEnchantment.replaceableEnchantment != null) 
							{
								if(replaceableEnchantment.replaceableEnchantment.equals(item3Enchantment))
								{
									item3Meta.removeEnchant(item3Enchantment);
									
									_break = true;
								}
							}
						}
						
						if(_continue)
							continue;
						
						for(BannedEnchantment bannedEnchantment : item3CustomEnchantment.bannedEnchantments) // Each of the end item's banned enchantments
						{
							if(bannedEnchantment.bannedEnchantment != null) 
							{
								if(bannedEnchantment.bannedEnchantment.equals(item2Enchantment)) // End item enchantment has banned an enchantment on the adding item
								{
									if(item2Meta.hasLore()) 
									{
										List<String> lores = item2Meta.getLore();
										lores.remove(bannedEnchantment.bannedCustomEnchantment.name);
										item2Meta.setLore(lores);	
										
										_continue = true;
									}
								}
							}
						}
						
						if(_break)
							break;
						
						for(ReplaceableEnchantment replaceableEnchantment : item3CustomEnchantment.replaceableEnchantments)
						{
							if(replaceableEnchantment.replaceableEnchantment != null) 
							{
								if(replaceableEnchantment.replaceableEnchantment.equals(item2Enchantment))
								{
									if(item2Meta.hasLore()) 
									{
										List<String> lores = item2Meta.getLore();
										lores.remove(replaceableEnchantment.replaceableCustomEnchantment.name);
										item2Meta.setLore(lores);	
										
										_continue = true;
									}
								}
							}
						}
						
						if(_continue)
							continue;
					}
				}
			}
		}
	
		
		item2.setItemMeta(item2Meta);
		item3.setItemMeta(item3Meta);
		
		TransferCustomEnchants(item2, item3);
	}
	

	// Transfers all of item1's enchants to item2
	static void TransferCustomEnchants(ItemStack item2, ItemStack item3) 
	{
		if(item2.hasItemMeta() && item3.hasItemMeta()); 
		{
			ItemMeta itemMeta = item3.getItemMeta();
			
			List<String> lores = null;
			if(itemMeta.hasLore())
				lores = itemMeta.getLore();
			else 
				lores = new ArrayList<String>();
			
			if(item2.getItemMeta().hasLore()) 
			{
				for(String lore : item2.getItemMeta().getLore()) 
				{
					if(!lores.contains(lore)) // Avoid adding same custom enchantments twice
					{
						lores.add(lore);
					}
				}
			}
			itemMeta.setLore(lores);
			
			item3.setItemMeta(itemMeta);
		}
		
		
	}
	
}
/*
 *  for each enchant on end item
 *  	for each enchant on adding item
 *  		if addingItemEnchant has endItemEnchantment on banned list
 *  			remove addingItemEnchant
 *  		else if addingItemEnchant has endItemEnchantment on replace list
 *  			remove endItemEnchantment
 *  		else if endItemEnchantment has addingItemEnchant on banned list
 *  			remove addingItemEnchantment
 *  		else if endItemEnchantment has addingItemEnchant on replace list
 *  			remove addingItemEnchantment
 *  add all enchantments to endItem
 *  
 *  ///
 *  When running through properly to decide the end result item, ensure you don't change the result of the 2nd item.
 *  However, do use the original end result item and the 2nd item to decide upon the end result item, with the same method.
 *  Just return the third item normally, the method does not have to be ran again.
*/
