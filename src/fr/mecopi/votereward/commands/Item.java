package fr.mecopi.votereward.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.mecopi.votereward.managers.VoteRewardManager;
import fr.mecopi.votereward.objects.GUI;

public class Item implements CommandExecutor
{

	@Override
	public boolean onCommand(CommandSender Sender, Command Command, String Useless, String[] Arguments) 
	{
		if(Sender instanceof Player)
		{
			new GUI((Player)Sender, VoteRewardManager.rewardItems, Bukkit.createInventory((Player)Sender, 4*9, "Objets droppables"), "Objets droppables");
			
			
		}
		return false;
	}
	
}
