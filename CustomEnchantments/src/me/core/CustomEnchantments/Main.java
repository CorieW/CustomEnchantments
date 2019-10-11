package me.core.CustomEnchantments;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.enchantment.EnchantItemEvent;
import org.bukkit.event.enchantment.PrepareItemEnchantEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import me.core.CustomEnchantments.Enchantments.Poison;
import me.core.CustomEnchantments.Enchantments.Smelter;
import me.core.CustomEnchantments.Enchantments.Speed;
import me.core.CustomEnchantments.Enchantments.SuperSpeed;
import me.core.CustomEnchantments.Enchantments.Telekinesis;

public class Main extends JavaPlugin implements Listener { // ! Rename the plugin to CustomEnchantments
	
	public static Main plugin;
	
	@Override
	public void onEnable() 
	{
		Bukkit.getServer().getPluginManager().registerEvents(this, this);

		plugin = this;
		
		AnvilHandler task = new AnvilHandler();
		task.runTaskTimer(Main.plugin, 0, 1);
		
		new Smelter();
		new Speed();
		new SuperSpeed();
		new Telekinesis();
		new Poison();
	}
	
	@EventHandler
	public void onEnchantItemEvent(EnchantItemEvent event) 
	{
		ItemStack item = event.getItem();
		event.getInventory().clear();
		event.getInventory().setItem(0, Enchanter.GetEnchantment(item, event.getEnchantsToAdd(), event.getExpLevelCost()));
	}
	
	@EventHandler
	public void onPrepareItemEnchantEvent(PrepareItemEnchantEvent event) 
	{
		
	}
	
}
// Common - <= 100%
// Uncommon - <= 12.5%
// Rare - <= 2.5%
// Legendary <= 0.5%
// Mythical <= 0.01%

// If you're gonna make changes to the probabilities in the future when server is live, ensure that colour of the lore is not accounted for or everyone which previously had the 
// enchantment loses the ability to use it.

// TODO:
// Add more enchantments
// Add a command to give an item a custom enchantment