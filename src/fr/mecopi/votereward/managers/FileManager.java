package fr.mecopi.votereward.managers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import fr.mecopi.votereward.Main;
import fr.mecopi.votereward.objects.Bag;
import fr.mecopi.votereward.objects.CustomPotion;
import fr.mecopi.votereward.objects.Effect;
import fr.mecopi.votereward.objects.RewardItem;
import net.md_5.bungee.api.ChatColor;

public class FileManager 
{
	public static File dataFolder = new File(Main.getInstance().getDataFolder().getPath().replace("\\", "/")); //Casting '\' chars for enable all architectures reading files paths
	public static File rewardsFile = new File(dataFolder.getPath().concat("/rewards.yml"));
	public static File effectFile = new File(dataFolder.getPath().concat("/effects.yml"));
	public static File potionFile = new File(dataFolder.getPath().concat("/potions.yml"));
	public static File bagsFolder = new File(dataFolder.getPath().concat("/Bags"));
	
	public static void Init()
	{
		
		//BagsFolder init
		try
		{
			if(!bagsFolder.exists())
				bagsFolder.mkdir();
			if(!effectFile.exists())
				effectFile.createNewFile();
			if(!potionFile.exists())
				Main.getInstance().saveResource("potions.yml", false);
		}
		catch (Exception ex) { }
		//Init all basic rewards
		
		YamlConfiguration defaultRewards = new YamlConfiguration(); //Creating YamlConfig object
		if(!rewardsFile.exists()) //If rewards.yml doesn't exist
			Main.getInstance().saveResource("rewards.yml", true); //Saving basic rewards file
		try
		{
			defaultRewards.load(rewardsFile); //Loading rewardsFile
			for(String Key : defaultRewards.getKeys(false)) //Not deep search in file
			{
				if(Material.getMaterial(defaultRewards.getString(Key.concat(".type"))) != null) //Catching type error
					VoteRewardManager.rewardItems.add(new RewardItem(new ItemStack(Material.getMaterial(defaultRewards.getString(Key.concat(".type"))), defaultRewards.getInt(Key.concat(".amount"))), (-1), defaultRewards.getDouble(Key.concat(".percent")))); //Create new RewardItem (ItemStack, ItemID, Drop Percent) with properties saved in file
				else
					Bukkit.getConsoleSender().sendMessage(VoteRewardManager.sendErrorMessage("La récompense '".concat(Key).concat("' n'a pas été initialisé.")));
			}
		}
		catch (Exception Ex) { Ex.printStackTrace(); }
		PotionInit();
		VoteRewardManager.Init();
		EffectsInit();
		BagsInit();
	}
	public static void SecureIDS()
	{
		List<Integer> IDS = new ArrayList<Integer>();
		for(RewardItem Reward : VoteRewardManager.rewardItems)
		{
			if(IDS.contains(Reward.getID()) && Reward.getID() > -1)
			{
				Bukkit.getConsoleSender().sendMessage(VoteRewardManager.sendErrorMessage("Check all potions IDS"));
				Bukkit.getPluginManager().disablePlugin(Main.getInstance());
				break;
			}
			else
				IDS.add(Reward.getID());
		}
	}
	public static void EffectsInit()
	{
		BufferedReader fileReader;
		String Line = "";
		try 
		{ 
			fileReader = new BufferedReader(new FileReader(effectFile)); 
			while((Line = fileReader.readLine()) != null)
			{
				Line = Line.replace(" ", "");
				VoteRewardManager.potionEffects.add(new Effect(UUID.fromString(Line.split(":")[0]), Line.split(":")[1].split(",")[0], Integer.parseInt(Line.split(",")[1])));
			}
			fileReader.close();
		} catch (IOException e) { e.printStackTrace(); }
	}
	public static void PotionInit()
	{
		YamlConfiguration potionConfig = new YamlConfiguration();
		try { potionConfig.load(potionFile); } catch (Exception ex) { ex.printStackTrace(); }
		for(String Key : potionConfig.getKeys(false))
		{
			VoteRewardManager.customPotionList.add(new CustomPotion(potionConfig.getString(new String(Key.concat(".name"))), potionConfig.getString(new String(Key.concat(".type"))), potionConfig.getInt(new String(Key.concat(".time"))), Arrays.asList(potionConfig.getString(new String(Key.concat(".lore"))).split("@"))));
			VoteRewardManager.rewardItems.add(new RewardItem(new CustomPotion(potionConfig.getString(new String(Key.concat(".name"))), potionConfig.getString(new String(Key.concat(".type"))), potionConfig.getInt(new String(Key.concat(".time"))), Arrays.asList(potionConfig.getString(new String(Key.concat(".lore"))).split("@"))).getItemStack(), potionConfig.getInt(Key.concat(".ID")) ,potionConfig.getDouble(new String(Key.concat(".percent")))));
		}
	}
	public static void saveEffects() //Save file for a reload issue
	{
		try
		{
			PrintWriter fileWriter = new PrintWriter(effectFile, "UTF-8");
			for(Effect effect : VoteRewardManager.potionEffects)
				fileWriter.write(effect.getOwner().toString() + " : " + effect.getType() + ", " + effect.getRemainingTime());
			fileWriter.close();
		} 
		catch (IOException e) { e.printStackTrace(); }	
	}
	private static void BagsInit()
	{
		File[] bagFiles = bagsFolder.listFiles();
		for(File bagFile : bagFiles)
		{
			YamlConfiguration bagContent = new YamlConfiguration();
			try
			{
				bagContent.load(bagFile); //Loading yml interpretor for bag file
				List<RewardItem> inventoryContent = new ArrayList<RewardItem>();
				for(String Key : bagContent.getKeys(false)) //Not deep search in file
				{
					if(bagContent.getInt(Key.concat(".ID")) != -1) //Checking if saved item ID isn't -1
					{
						for(RewardItem rewardItem : VoteRewardManager.rewardItems) //For each rewards
						{
							if(rewardItem.getID() == bagContent.getInt(Key.concat(".ID"))) //If actual reward if readed ID in file equals ID reward
							{
								ItemStack rewardItemClone = rewardItem.getItem().clone();
								ItemMeta rewardItemCloneMeta = rewardItemClone.getItemMeta();
								List<String> rewardItemCloneLore = rewardItemCloneMeta.hasLore() ? rewardItemCloneMeta.getLore() : new ArrayList<String>();
								rewardItemCloneLore.add(ChatColor.BLUE + "Expiration : ".concat(bagContent.getString(Key.concat(".expiration"))));
								rewardItemCloneMeta.setLocalizedName(bagContent.getString(Key.concat(".expiration")));
								rewardItemCloneMeta.setLore(rewardItemCloneLore);
								rewardItemClone.setItemMeta(rewardItemCloneMeta);
								inventoryContent.add(new RewardItem(rewardItemClone, rewardItem.getID(), 100D)); //Add this custom item in bagContent
								break;
							}
						}
					}
					else //If ID's -1
					{
						if(Material.getMaterial(bagContent.getString(Key.concat(".type"))) != null)
						{
							ItemStack rewardItem = new ItemStack(Material.getMaterial(bagContent.getString(Key.concat(".type"))), bagContent.getInt(Key.concat(".amount")));
							ItemMeta rewardItemMeta = rewardItem.getItemMeta();
							List<String> rewardItemLore = rewardItemMeta.hasLore() ? rewardItemMeta.getLore() : new ArrayList<String>();
							rewardItemLore.add(ChatColor.BLUE + "Expiration : ".concat(bagContent.getString(Key.concat(".expiration"))));
							rewardItemMeta.setLocalizedName(bagContent.getString(Key.concat(".expiration")));
							rewardItemMeta.setLore(rewardItemLore);
							rewardItem.setItemMeta(rewardItemMeta);
							inventoryContent.add(new RewardItem(rewardItem, -1, 100D)); 
						}
						else
							Bukkit.getConsoleSender().sendMessage(VoteRewardManager.sendErrorMessage("La récompense '".concat(Key).concat("' n'a pas été initialisé.")));
					}
				}
				VoteRewardManager.playerBags.add(new Bag(inventoryContent, UUID.fromString(bagFile.getName().replace(".yml", "")))); //Creating Bag Object
			}
			catch (Exception Ex) { Ex.printStackTrace(); }
		}
	}
	public static void SaveBags()
	{
		//Clear all bag files
		bagsFolder.delete();
		bagsFolder.mkdir();
		for(Bag bag : VoteRewardManager.playerBags)
		{
			try 
			{
				PrintWriter fileWriter = new PrintWriter(new File(bagsFolder.getPath().concat("/").concat(bag.getOwner().toString()).concat(".yml")));
				int itemIndex = 0;
				for(RewardItem Reward : bag.getBagContent())
				{
					String Expiration = Reward.getItem().getItemMeta().getLocalizedName();
					fileWriter.write("reward_".concat(String.valueOf(itemIndex).concat(":\n")));
					fileWriter.write("  type: ".concat(Reward.getItem().getType().name().toUpperCase()).concat("\n"));
					fileWriter.write("  amount: ".concat(String.valueOf(Reward.getItem().getAmount())).concat("\n"));
					fileWriter.write("  ID: ".concat(String.valueOf(Reward.getID()).concat("\n")));
					fileWriter.write("  expiration: ".concat(Expiration).concat("\n"));
					itemIndex++;
				}
				fileWriter.close();
				
			} catch (FileNotFoundException e) { }
			
		}
	}
}
