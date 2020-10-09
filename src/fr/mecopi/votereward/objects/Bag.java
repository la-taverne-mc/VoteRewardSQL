package fr.mecopi.votereward.objects;

import java.util.List;
import java.util.UUID;

public class Bag 
{
	private List<RewardItem> inventoryContent;
	private UUID Owner;
	
	public Bag(List<RewardItem> inventoryContentEntry, UUID ownerEntry)
	{
		inventoryContent = inventoryContentEntry;
		Owner = ownerEntry;
	}
	
	//Getters
	
	public List<RewardItem> getBagContent()
	{
		return inventoryContent;
	}
	public UUID getOwner()
	{
		return Owner;
	}	
}
