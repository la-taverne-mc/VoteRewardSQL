package fr.mecopi.votereward.managers;

import org.bukkit.Bukkit;

import fr.mecopi.votereward.commands.*;

public class CommandManager 
{
	public static void Init()
	{
		Bukkit.getPluginCommand("rvitem").setExecutor(new Item());
		Bukkit.getPluginCommand("fakevote").setExecutor(new Fakevote());
		Bukkit.getPluginCommand("rvbag").setExecutor(new RvBag());
	}
}
