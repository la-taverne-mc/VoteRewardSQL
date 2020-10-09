package fr.mecopi.votereward;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import fr.mecopi.votereward.managers.BagClearManager;
import fr.mecopi.votereward.managers.CommandManager;
import fr.mecopi.votereward.managers.ConfigurationManager;
import fr.mecopi.votereward.managers.EventsManager;
import fr.mecopi.votereward.managers.FileManager;
import fr.mecopi.votereward.managers.VoteRewardManager;
import fr.mecopi.votereward.managers.VotifierManager;
import fr.mecopi.votereward.objects.GUI;

public class Main extends JavaPlugin
{
	
	@Override
	public void onEnable()
	{
		CommandManager.Init();
		ConfigurationManager.Init();
		BagClearManager.Init();
		Bukkit.getPluginManager().registerEvents(new EventsManager(), this);
		Bukkit.getPluginManager().registerEvents(new VotifierManager(), this);
		Bukkit.getConsoleSender().sendMessage(VoteRewardManager.sendSuccessMessage("Enabled successfully"));
	}
	@Override
	public void onDisable()
	{
		for(GUI gui : VoteRewardManager.GUIS)
		{
			gui.getOwner().closeInventory(); //Closing all guis
		}
		FileManager.SaveBags();
		FileManager.saveEffects();
		Bukkit.getConsoleSender().sendMessage(VoteRewardManager.sendSuccessMessage("Disabled successfully"));
	}
	public static Plugin getInstance()
	{
		return Bukkit.getPluginManager().getPlugin("VoteReward");
	}
}
