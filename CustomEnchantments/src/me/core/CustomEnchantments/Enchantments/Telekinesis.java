package me.core.CustomEnchantments.Enchantments;

import java.util.ArrayList;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.core.CustomEnchantments.CustomEnchantment;
import me.core.CustomEnchantments.Main;

public class Telekinesis implements Listener {

	public static CustomEnchantment enchantment;
	
	public Telekinesis() 
	{
		Bukkit.getServer().getPluginManager().registerEvents(this, Main.plugin);
		
		// Setting up it's custom enchantment ...
		String name = "Telekinesis";
		int minLevel = 22;
		
		ArrayList<Material> possibleItemTypes = new ArrayList<Material>();
		possibleItemTypes.add(Material.DIAMOND_PICKAXE);
		possibleItemTypes.add(Material.GOLD_PICKAXE);
		possibleItemTypes.add(Material.IRON_PICKAXE);
		possibleItemTypes.add(Material.STONE_PICKAXE);
		possibleItemTypes.add(Material.WOOD_PICKAXE);
		possibleItemTypes.add(Material.DIAMOND_AXE);
		possibleItemTypes.add(Material.GOLD_AXE);
		possibleItemTypes.add(Material.IRON_AXE);
		possibleItemTypes.add(Material.STONE_AXE);
		possibleItemTypes.add(Material.WOOD_AXE);
		possibleItemTypes.add(Material.DIAMOND_SPADE);
		possibleItemTypes.add(Material.GOLD_SPADE);
		possibleItemTypes.add(Material.IRON_SPADE);
		possibleItemTypes.add(Material.STONE_SPADE);
		possibleItemTypes.add(Material.WOOD_SPADE);
		
		double probability = 10;
		
		enchantment = new CustomEnchantment(name, minLevel, possibleItemTypes, probability, null, null);
		// ...
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
						Block block = event.getBlock();
						
						Inventory inv = event.getPlayer().getInventory();
						
						ItemStack itemUsed = event.getPlayer().getItemInHand();
						
						boolean given = false;
						for(Enchantment enchantment : itemUsed.getEnchantments().keySet()) 
						{
							if(enchantment.equals(Enchantment.SILK_TOUCH)) // When item used to mine block has silk touch enchantment
							{
								inv.addItem(new ItemStack(block.getType(), 1));
								given = true;
								break;
							}
							else  if(enchantment.equals(Enchantment.LOOT_BONUS_BLOCKS)) // When item used to mine block has fortune enchantment
							{
								for(ItemStack itemStack : block.getDrops()) // Adds all the blocks drops
								{
									inv.addItem(FortuneMine(itemStack));
								}
								given = true;
								break;
							}
						}
						
						if(given == false) 
						{
							for(ItemStack itemStack : block.getDrops()) // Adds all the blocks drops
							{
								inv.addItem(itemStack);
							}
						}
						
						event.getBlock().setType(Material.AIR); // Prevents normal drop
					}
				}
			}
		}
	}
	
	// Simulates mining with the fortune enchantment
	public ItemStack FortuneMine(ItemStack droppedItemStack) 
	{
		Material material = droppedItemStack.getType();
		
		int amount = 1;
		switch(material) { // ! MIGHT NEED TO ADD LAPIS IF SOMEONE NOTICES
			case COAL:
				amount += new Random().nextInt(3);
				break;
			case REDSTONE:
				amount += new Random().nextInt(3);
				break;
			case DIAMOND:
				amount += new Random().nextInt(3);
				break;
			case EMERALD:
				amount += new Random().nextInt(3);
				break;
			case QUARTZ:
				amount += new Random().nextInt(3);
				break;
			default:
				break;
		}
		
		return new ItemStack(material, droppedItemStack.getAmount() * amount);
	}
}
