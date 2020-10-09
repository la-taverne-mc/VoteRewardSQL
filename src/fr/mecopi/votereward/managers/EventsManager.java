package fr.mecopi.votereward.managers;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockCookEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import fr.mecopi.votereward.Main;
import fr.mecopi.votereward.objects.CustomItems;
import fr.mecopi.votereward.objects.CustomPotion;
import fr.mecopi.votereward.objects.Effect;
import fr.mecopi.votereward.objects.GUI;

public class EventsManager implements Listener
{
	@EventHandler
	public void onInventoryClick(InventoryClickEvent e)
	{
		CustomItems.CantBake(e);
		for(GUI gui : VoteRewardManager.GUIS)
		{
			gui.onClickGUI(e);
			break;
		}
	}
	@EventHandler
	public void onInventoryClose(InventoryCloseEvent e)
	{
		for(GUI gui : new ArrayList<GUI>(VoteRewardManager.GUIS))
		{
			gui.onGUIClose(e);
			break;
		}
	}
	@EventHandler
	public void onBlockBreak(BlockBreakEvent e)
	{
		CustomItems.GoblinPickaxeEvent(e);
	}
	@EventHandler
	public void onEntityDeath(EntityDeathEvent e)
	{
		CustomItems.ULUEvent(e);
		CustomItems.IndianSpearEvent(e);
	}
	@EventHandler
	public void onBlockCook(BlockCookEvent e)
	{
		CustomItems.BakeCustomFood(e);
	}
	@EventHandler
	public void onConsume(PlayerItemConsumeEvent e)
	{
		CustomItems.EatCustomFood(e);
		if(VoteRewardManager.isCustomPotion(e.getItem()))
		{
			CustomPotion customPotion = VoteRewardManager.getCustomPotion(e.getItem());
			if(VoteRewardManager.getEffectByPlayer(e.getPlayer().getUniqueId()) == null && customPotion != null)
			{
				VoteRewardManager.potionEffects.add(new Effect(e.getPlayer().getUniqueId(), customPotion.getType().toLowerCase(), customPotion.getLevel()));
				Bukkit.getScheduler().runTaskLater(Main.getInstance(), () -> Modif(customPotion, e.getPlayer()), 1);
			}
			else
			{
				e.setCancelled(true);
				Effect playerEffect = VoteRewardManager.getEffectByPlayer(e.getPlayer().getUniqueId());
				Bukkit.getScheduler().cancelTask(playerEffect.getTask().taskID);
				VoteRewardManager.sendBarMessage(e.getPlayer(), ChatColor.translateAlternateColorCodes('&', "&cVous êtes déjà sous l'effet d'une potion."));
				playerEffect.setRemainingTime(playerEffect.getRemainingTime() - 2);
				Bukkit.getScheduler().runTaskLater(Main.getInstance(), () -> playerEffect.getTask().addTask(playerEffect), (20 * 2));
			}
		}
		else
		{
			if(e.getItem().getType().equals(Material.MILK_BUCKET))
			{
				Effect playerEffect = VoteRewardManager.getEffectByPlayer(e.getPlayer().getUniqueId());
				if(playerEffect != null)
					playerEffect.getTask().disableEffect();
			}
		}
		CustomItems.EatCustomFood(e);
	}
	public void Modif(CustomPotion customPotion, Player player)
	{
		ItemStack Cloned = player.getInventory().getItemInMainHand();
		ItemMeta stackMeta = Cloned.getItemMeta();
		if(customPotion.getType().equals("fly"))
		{
			switch(customPotion.getLevel())
			{
				case 150:
					stackMeta.setCustomModelData(1);
					break;
				case 300:
					stackMeta.setCustomModelData(2);
					break;
				case 600:
					stackMeta.setCustomModelData(3);
					break;
				case 1200:
					stackMeta.setCustomModelData(4);
					break;
				default:
					stackMeta.setCustomModelData(1);
					break;
			}	
		}
		else if(customPotion.getType().equals("phantom"))
		{
			stackMeta.setCustomModelData(5);
		}
		else if(customPotion.getType().equals("creeper"))
		{
			stackMeta.setCustomModelData(6);
		}
		else if(customPotion.getType().equals("mining"))
		{
			stackMeta.setCustomModelData(7);
		}
		else if(customPotion.getType().equals("swimming"))
		{
			stackMeta.setCustomModelData(8);
		}
		Cloned.setItemMeta(stackMeta);
		player.getInventory().setItemInMainHand(Cloned);
	}
}
