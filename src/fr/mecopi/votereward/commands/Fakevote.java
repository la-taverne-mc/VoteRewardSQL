package fr.mecopi.votereward.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.mecopi.votereward.managers.Permissions;
import fr.mecopi.votereward.managers.VoteRewardManager;

public class Fakevote implements CommandExecutor
{
	@Override
	public boolean onCommand(CommandSender Sender, Command Command, String arg2, String[] Arguments) 
	{
		if(Sender instanceof Player)
		{
			if(Sender.hasPermission(Permissions.fakevote))
			{
				if(Arguments.length > 0)
				{
					try
					{
						VoteRewardManager.sendItem(((Player)Sender).getUniqueId(), Integer.parseInt(Arguments[0]));
					}
					catch (Exception ex)
					{
						((Player)Sender).sendMessage(VoteRewardManager.sendErrorMessage("Entrer le nombre de récompenses voulu."));
					}
				}
				else
				{
					VoteRewardManager.sendItem(((Player)Sender).getUniqueId(), 1);
				}
			}
			else
				Bukkit.getConsoleSender().sendMessage(VoteRewardManager.sendErrorMessage("Vous n'avez pas le droit d'utiliser cette commande."));
		}
		return false;
	}
	
}
