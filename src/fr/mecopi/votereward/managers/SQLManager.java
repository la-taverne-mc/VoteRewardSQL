package fr.mecopi.votereward.managers;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;

public class SQLManager 
{
	private static Connection dbConnection;
	public static String prefixTable;
	
	public static void Init(String queryEntry, String prefixEntry)
	{
		try 
		{
			prefixTable = prefixEntry;
			dbConnection = DriverManager.getConnection(queryEntry); //Database connection with builded query in BDDManager
			Bukkit.getConsoleSender().sendMessage(VoteRewardManager.sendSuccessMessage("-> [SQL] Initialized"));
			CheckTable(prefixTable.concat("effects"));
			CheckTable(prefixTable.concat("rewards"));
			CheckTable(prefixTable.concat("bags"));
		} catch (SQLException ex) { 
			ex.printStackTrace();
			Bukkit.getConsoleSender().sendMessage(VoteRewardManager.sendErrorMessage("-> [SQL] ".concat(ex.getMessage()))); //Notify server owner about SQL error
		}
	}
	public static List<String> SendQuery(String Query, String tableEntry)
	{
		ResultSet requestResult = null;
		List<String> dbDatas = new ArrayList<String>();
		try 
		{
			Statement queryBuild = dbConnection.createStatement(); //Create simple queryExecutor
			requestResult = queryBuild.executeQuery(Query); //Execute query
			ResultSetMetaData resultDatas = requestResult.getMetaData(); //Getting executor metas
			int returnColumnCount = resultDatas.getColumnCount();
			while (requestResult.next()) //Browse the query result
			{
				for(int i = 1; i <= returnColumnCount; i++)
					dbDatas.add(requestResult.getString(i)); //Add all data in a string table
			}
			requestResult.close(); //Close the query executor
		} catch (SQLException ex) {
			Bukkit.getConsoleSender().sendMessage(VoteRewardManager.sendErrorMessage("-> [SQL] ".concat(ex.getMessage()))); //Notify server owner about SQL error
		}
		return dbDatas.size() > 0 ? dbDatas : null; //Return query result (no results return null)
	}
	public static void CheckTable(String tableEntry)
	{
		DatabaseMetaData dataMeta;
		try 
		{
			dataMeta = dbConnection.getMetaData(); //Getting database datas
			ResultSet queryResult = dataMeta.getTables(null, null, tableEntry, null); //Trying to get table
			if(!queryResult.next())	//No metadata query result
				CreateTable(tableEntry);
			queryResult.close(); //Closing SQL query process
		} catch (SQLException ex) {
			Bukkit.getConsoleSender().sendMessage(VoteRewardManager.sendErrorMessage("-> [SQL] ".concat(ex.getMessage()))); //Notify server owner about SQL error
		}
	}
	public static void CreateTable(String tableEntry)
	{
		String createQuery = "";
		if(tableEntry.contains("effects"))
		{
			createQuery = "CREATE TABLE " + tableEntry + 
					"(id INTEGER not NULL," + //Set ID column
					"player_uuid TEXT," +  //Set Player UUID column
					"potion_type TEXT," +  //Set Flask Type column
					"remaining_time INTEGER," + //Set Remaining flask effect time column
					"PRIMARY KEY ( id ));"; //Define ID column on AI
		}
		else if(tableEntry.contains("rewards"))
		{
			createQuery = "CREATE TABLE " + tableEntry +
					"(id INTEGER not NULL," + //Set ID column
					"item_type TEXT," +  //Set item type column
					"item_amount TEXT," +  //Set Item amount column
					"item_percent TEXT," + //Set Drop percentage column
					"item_id TEXT," + //Setting custom ID column
					"PRIMARY KEY ( id ));"; //Define ID column on AI
		}
		else if(tableEntry.contains("bags"))
		{
			createQuery = "CREATE TABLE " + tableEntry +
					"(id INTEGER not NULL," + //Set ID column
					"player_uuid TEXT," +  //Set Owner column
					"item_type TEXT," +  //Set Item type column
					"item_amount TEXT," + //Set Item amount column
					"item_id TEXT," + //Setting custom ID column
					"expiration_date TEXT," + //Setting expiration stack column
					"PRIMARY KEY ( id ));"; //Define ID column on AI
		}
		try 
		{
			Statement queryBuild = dbConnection.createStatement(); //Create simple queryExecutor
			queryBuild.executeUpdate(createQuery);
			queryBuild.close();
			Bukkit.getConsoleSender().sendMessage(VoteRewardManager.sendSuccessMessage("-> [SQL] ".concat(tableEntry.concat(" just created."))));
		} 
		catch (SQLException ex) {
			Bukkit.getConsoleSender().sendMessage(VoteRewardManager.sendErrorMessage("-> [SQL] ".concat(ex.getMessage()))); //Notify server owner about SQL error
		}
	}
	public static void Close()
	{
		try {
			dbConnection.close(); //Trying closing connection from server to database (When VoteReward's disabling)
		} catch (SQLException ex) {
			Bukkit.getConsoleSender().sendMessage(VoteRewardManager.sendErrorMessage("-> [SQL] ".concat(ex.getMessage()))); //Notify server owner about SQL error
		}
	}
	
}
