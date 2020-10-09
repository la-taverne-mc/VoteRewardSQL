package fr.mecopi.votereward.managers;

import org.bukkit.configuration.file.FileConfiguration;

public class BDDManager 
{
	public static void Init()
	{
		FileConfiguration tempConfig = ConfigurationManager.configurationFile;
		SQLManager.Init(new String("jdbc:").concat("mysql://").concat(tempConfig.getString("sql-host")).concat(":").concat(tempConfig.getString("sql-port")).concat("/").concat(tempConfig.getString("sql-database")).concat("?user=").concat(tempConfig.getString("sql-username").concat("&password=").concat(tempConfig.getString("sql-password")).concat("&useSSL=false")), tempConfig.getString("table-prefix")); //Build connection request
	}
	public static void Update()
	{
		
	}
}
