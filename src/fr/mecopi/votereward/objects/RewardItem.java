package fr.mecopi.votereward.objects;

import org.bukkit.inventory.ItemStack;

public class RewardItem 
{
	private ItemStack Item;
	private Integer itemID;
	private Double Percentage;
	
	public RewardItem(ItemStack item, Integer itemid, Double percentage)
	{
		//Setting RewardItem properties	
		Item = item;
		itemID = itemid;
		Percentage = percentage;
	}
	
	//Getters
	
	public ItemStack getItem() {
		return Item;
	}
	public Integer getID() {
		return itemID;
	}
	public Double getPercentage() {
		return Percentage;
	}
	
	//Setters
	
	public void setItem(ItemStack newItem) {
		Item = newItem;
	}
}
