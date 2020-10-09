package fr.mecopi.votereward.objects;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import fr.mecopi.votereward.managers.Permissions;
import fr.mecopi.votereward.managers.VoteRewardManager;
import net.md_5.bungee.api.ChatColor;

public class GUI 
{
	private Player Owner;
	private List<RewardItem> Repertory;
	private String GUIName;
	private Inventory GUI;
	private ItemStack prevButton;
	private ItemStack nextButton;
	private int Index = 0;
	
	public GUI(Player ownerEntry, List<RewardItem> repertoryEntry, Inventory guiEntry, String GUINameEntry)
	{
		Owner = ownerEntry;
		Repertory = repertoryEntry;
		GUI = guiEntry;
		GUIName = GUINameEntry;
		
		//Setting previous button properties
		
		prevButton = new ItemStack(Material.BOOK);
		ItemMeta prevButtonMeta = prevButton.getItemMeta();
		prevButtonMeta.setDisplayName("Page précédente");
		prevButton.setItemMeta(prevButtonMeta);
		
		//Setting next button properties
		
		nextButton = new ItemStack(Material.BOOK);
		ItemMeta nextButtonMeta = nextButton.getItemMeta();
		nextButtonMeta.setDisplayName("Page suivante");
		nextButton.setItemMeta(nextButtonMeta);
		
		VoteRewardManager.GUIS.add(this);
		Owner.openInventory(GUI);
		ShowGUI();
	}
	
	//Function
	
	public void ShowGUI()
	{
		GUI.clear();
		if(Index > 0)
		{
			GUI.setItem(GUI.getSize()-9, prevButton);
		}
		if((Index + (GUI.getSize()-9)) < Repertory.size()) //If (index + (GUI Size - Nav slot)) < Total size of items to show
		{
			GUI.setItem(GUI.getSize()-1, nextButton);
		}
		for(int i = 0; i < GUI.getSize()-9; i++)
		{
			int actualIndex = i + Index;
			if(actualIndex < Repertory.size() && actualIndex >= 0)
			{
				if(GUIName.equals("Objets droppables"))
				{
					ItemStack toShow = Repertory.get(actualIndex).getItem().clone();
					ItemMeta toShowMeta = toShow.getItemMeta();
					List<String> toShowLore = toShowMeta.hasLore() ? toShowMeta.getLore() : new ArrayList<String>();
					toShowLore.add(ChatColor.translateAlternateColorCodes('&', "&aTaux d'obtention : ".concat(String.valueOf(Repertory.get(actualIndex).getPercentage()))).concat("%"));
					toShowMeta.setLore(toShowLore);
					toShow.setItemMeta(toShowMeta);
					GUI.addItem(toShow);
				}
				else if(GUIName.equals("Sac de récompenses"))
					GUI.addItem(Repertory.get(actualIndex).getItem().clone());
			}
		}
		
	}
	public void StaffGUI(InventoryClickEvent e)
	{
		if(e.getWhoClicked().hasPermission(Permissions.getItem) && e.getCurrentItem() != nextButton && e.getCurrentItem() != prevButton)
		{
			if(!isFull(e.getWhoClicked().getInventory(), (4*9)))
			{
				ItemStack clickedItem = e.getCurrentItem().clone();
				ItemMeta clickedItemMeta = clickedItem.getItemMeta();
				List<String> clickedItemLore = clickedItemMeta.getLore();
				clickedItemLore.remove(clickedItemLore.size()-1);
				clickedItemMeta.setLore(clickedItemLore);
				clickedItem.setItemMeta(clickedItemMeta);
				e.getWhoClicked().getInventory().addItem(clickedItem);
			}
			else
				e.getWhoClicked().sendMessage(VoteRewardManager.sendErrorMessage("Votre inventaire est plein, videz le d'abord."));
			e.setCancelled(true);
		}
		else
			e.setCancelled(true);
	}
	public void BagGUI(InventoryClickEvent e)
	{
		if(e.getCurrentItem() != nextButton && e.getCurrentItem() != prevButton)
		{
			if(!isFull(e.getWhoClicked().getInventory(), (4*9)))
			{
				ItemStack clickedItem = e.getCurrentItem();
				ItemMeta clickedItemMeta = clickedItem.getItemMeta();
				List<String> clickedItemLore = clickedItemMeta.getLore();
				clickedItemLore.remove(clickedItemLore.size()-1); //Delete expiration time from item lore
				clickedItemMeta.setLocalizedName("");
				clickedItemMeta.setLore(clickedItemLore);
				clickedItem.setItemMeta(clickedItemMeta);
				Repertory.get(e.getSlot() + Index).getItem().setType(Material.AIR);
				e.getInventory().setItem(e.getSlot(), new ItemStack(Material.AIR));
				e.getWhoClicked().getInventory().addItem(clickedItem);
			}
			else
				e.getWhoClicked().sendMessage(VoteRewardManager.sendErrorMessage("Votre inventaire est plein, videz le d'abord."));
			e.setCancelled(true);
		}
		else
			e.setCancelled(true);
	}
	public boolean isFull(Inventory invToCheck, int maxSize)
	{
		List<ItemStack> inventoryContent = new ArrayList<ItemStack>();
		for(ItemStack itemStack : invToCheck)
		{
			if(itemStack != null)
				inventoryContent.add(itemStack);
		}
		return inventoryContent.size() < maxSize ? false : true;
	}
	private void Deleting()
	{
		if(GUIName.equals("Sac de récompenses"))
		{
			for(RewardItem Reward : new ArrayList<RewardItem>(Repertory))
			{
				if(Reward.getItem().getType().equals(Material.AIR))
					Repertory.remove(Reward);
			}
		}
	}
	//Events
	
	public void onNavClick(InventoryClickEvent e)
	{
		if(e.getClickedInventory() != null && !e.getClickedInventory().getType().equals(InventoryType.PLAYER) && e.getCurrentItem() != null)
		{
			if(e.getCurrentItem().equals(prevButton))
			{
				e.setCancelled(true);
				Index -= (GUI.getSize()-9);
				Deleting();
				ShowGUI();
			}
			else if(e.getCurrentItem().equals(nextButton))
			{
				e.setCancelled(true);
				Index += (GUI.getSize()-9);
				Deleting();
				ShowGUI();
			}
			else
			{
				onClickItem(e);
			}
		}
		else if(e.getClickedInventory() != null && e.getClickedInventory().getType().equals(InventoryType.PLAYER))
			e.setCancelled(true);
	}
	public void onClickItem(InventoryClickEvent e)
	{
		if(e.getClickedInventory() != null && !e.getClickedInventory().getType().equals(InventoryType.PLAYER) && e.getCurrentItem() != null)
		{
			if(GUIName.equals("Objets droppables"))
			{
				StaffGUI(e);
			}
			else if(GUIName.equals("Sac de récompenses"))
			{
				BagGUI(e);
			}
		}
	}
	public void onGUIClose(InventoryCloseEvent e)
	{
		if(e.getPlayer().equals(Owner) && e.getView().getTitle().equals(GUIName))
		{
			Deleting();
			VoteRewardManager.GUIS.remove(this);
		}
	}
	public void onClickGUI(InventoryClickEvent e)
	{
		if(e.getWhoClicked().equals(Owner) && e.getView().getTitle().equals(GUIName))
			onNavClick(e);
	}
	//Getters
	
	public Player getOwner()
	{
		return Owner;
	}
}
