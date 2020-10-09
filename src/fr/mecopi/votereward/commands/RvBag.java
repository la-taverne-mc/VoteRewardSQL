package fr.mecopi.votereward.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.mecopi.votereward.managers.VoteRewardManager;
import fr.mecopi.votereward.objects.Bag;
import fr.mecopi.votereward.objects.GUI;

public class RvBag implements CommandExecutor
{

	@Override
	public boolean onCommand(CommandSender Sender, Command Command, String arg2, String[] Arguments) 
	{
		if(Sender instanceof Player)
		{
			Player player = (Player)Sender;
			Bag playerBag = VoteRewardManager.getBagByOwner(player.getUniqueId()) == null ? VoteRewardManager.createBag(player.getUniqueId()) : VoteRewardManager.getBagByOwner(player.getUniqueId());
			new GUI(player, playerBag.getBagContent(), Bukkit.createInventory(player, 5*9, "Sac de récompenses"), "Sac de récompenses");
		}
		return false;
	}

}
