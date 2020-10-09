package fr.mecopi.votereward.managers;

import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;

import fr.mecopi.votereward.Main;
import fr.mecopi.votereward.objects.Bag;

public class BagClearManager
{
	public static int Day;
	public static int Month;
	public static int Year;
	private static boolean Enabled;
	private static File configFile = new File(Main.getInstance().getDataFolder().getPath().replace("\\", "/").concat("/config.yml"));
	
	public static void Init()
	{
		Setting();
		if(Enabled)
		{
			Task();
			Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getInstance(), new Runnable() {
			    @Override
			    public void run() {
			        Task();
			    }
			}, 0, (((60 * 20) * 60) * 24)); //Every 24 hours
		}
	}
	private static void Setting()
	{
		YamlConfiguration fileConfig = new YamlConfiguration();
		if(!configFile.exists())
			Main.getInstance().saveResource("config.yml", false);
		try { fileConfig.load(configFile); } catch (Exception ex) { ex.printStackTrace(); }
		Day = fileConfig.getInt("expire_bag.d");
		Month = fileConfig.getInt("expire_bag.m");
		Year = fileConfig.getInt("expire_bag.y");
		if(Day == 0 && Month == 0 && Year == 0)
			Enabled = false;
		else
			Enabled = true;
	}
	
	
	public static void Task() 
	{
		LocalDate NOW = LocalDate.now(); 
		NOW.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		for(Bag bag : VoteRewardManager.playerBags)
		{
			for(int i = 0; i < bag.getBagContent().size(); i++)
			{
				ItemStack rewardItem = bag.getBagContent().get(i).getItem();
				String expirationDate = rewardItem.getItemMeta().getLocalizedName();
				String[] IDS = expirationDate.replace(" ", "").split("/");
				LocalDate itemLocalDate = LocalDate.parse(IDS[2].concat("-").concat(IDS[1].concat("-").concat(IDS[0])));
				itemLocalDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
				if(NOW.isAfter(itemLocalDate))
				{
					bag.getBagContent().remove(i);
				}
			}
		}
	}
	public static void Task(Bag bag) 
	{
		LocalDate NOW = LocalDate.now(); 
		NOW.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		for(int i = 0; i < bag.getBagContent().size(); i++)
		{
			ItemStack rewardItem = bag.getBagContent().get(i).getItem();
			String expirationDate = rewardItem.getItemMeta().getLocalizedName();
			String[] IDS = expirationDate.replace(" ", "").split("/");
			LocalDate itemLocalDate = LocalDate.parse(IDS[2].concat("-").concat(IDS[1].concat("-").concat(IDS[0])));
			itemLocalDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
			if(NOW.isAfter(itemLocalDate))
			{
				bag.getBagContent().remove(i);
			}
		}
	}
}
