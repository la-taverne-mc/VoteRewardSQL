package fr.mecopi.votereward.managers;

import org.bukkit.configuration.file.FileConfiguration;

import fr.mecopi.votereward.Main;

public class ConfigurationManager 
{
	private static boolean useMySQL;
	public static FileConfiguration configurationFile = Main.getInstance().getConfig();
	
	public static void Init()
	{
		if(!FileManager.dataFolder.exists())
			FileManager.dataFolder.mkdir();
		Main.getInstance().saveDefaultConfig();
		useMySQL = configurationFile.getBoolean("use-sql"); //getting if sql is using
		InitLobby();
	}
	public static void InitLobby()
	{
		if(useMySQL) //If SQL is using
			BDDManager.Init(); //Init SQL Service
		else
			FileManager.Init(); //Init File Service
	}
}
