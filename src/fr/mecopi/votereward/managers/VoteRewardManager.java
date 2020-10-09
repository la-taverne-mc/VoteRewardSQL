package fr.mecopi.votereward.managers;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import fr.mecopi.votereward.Main;
import fr.mecopi.votereward.objects.Bag;
import fr.mecopi.votereward.objects.CustomItems;
import fr.mecopi.votereward.objects.CustomPotion;
import fr.mecopi.votereward.objects.Effect;
import fr.mecopi.votereward.objects.GUI;
import fr.mecopi.votereward.objects.RewardItem;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;

public class VoteRewardManager 
{
	public static List<RewardItem> rewardItems = new ArrayList<RewardItem>(); //First degrees item list
	public static List<Bag> playerBags = new ArrayList<Bag>();
	public static List<GUI> GUIS = new ArrayList<GUI>();
	public static List<RewardItem> sendRewardItems = new ArrayList<RewardItem>(); //Second degrees item list (When vote or fakevote)
	public static List<Effect> potionEffects = new ArrayList<Effect>();
	public static List<CustomPotion> customPotionList = new ArrayList<CustomPotion>();
	
	public static void Init()
	{
		CustomItems.Init();
		FileManager.SecureIDS();
		droppableItemsSetting();
	}
	public static String convertTime(int Time) {
		String sTime = "";
		if (Time >= 3600) {
			sTime += String.valueOf(Time / 3600).concat("h");
			Time = Time % 3600;
		}
		if (Time >= 60) {
			sTime += String.valueOf(Time / 60).concat("min");
			Time = Time % 60;
		}
		if (Time >= 1) {
			sTime += String.valueOf(Time).concat("s");
		}
		return sTime;
	}
	public static String sendSuccessMessage(String Message) {
		return ChatColor.translateAlternateColorCodes('&', new String("[&aVoteReward&f] ").concat(Message)); //send success message (green color)
	}
	public static String sendErrorMessage(String Message) {
		return ChatColor.translateAlternateColorCodes('&', new String("[&cVoteReward&f] ").concat(Message)); //send error message (red color)
	}
	public static void sendBarMessage(Player player, String Message) {
		player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(Message));
	}
	public static void droppableItemsSetting()
	{
		int Multiplicator = 1;
		double totalPercent = 0;
		for(RewardItem Reward : rewardItems)
			totalPercent += Reward.getPercentage();
		if(totalPercent > 100)
		{
			Bukkit.getConsoleSender().sendMessage(sendErrorMessage("La configuration du taux des drops est erronee"));
			Bukkit.getPluginManager().disablePlugin(Main.getInstance());
			return;
		}
		for(RewardItem Reward : rewardItems)
		{
			int integerPart = Reward.getPercentage().intValue();
			String tempDecimal = String.valueOf(Reward.getPercentage() - integerPart);
			String Decimal = "";
			for(int i = tempDecimal.indexOf('.')+1; i < tempDecimal.length(); i++) { Decimal += tempDecimal.charAt(i); }
			if(!Decimal.equals("0"))
			{
				int tempMultiplicator = 1;
				for(int i = 0; i < Decimal.length(); i++)
					tempMultiplicator = (tempMultiplicator * 10);
				if(Multiplicator < tempMultiplicator)
					Multiplicator = tempMultiplicator;
			}
		}
		for(RewardItem Reward : rewardItems)
		{
			for(int i = 0; i < (Reward.getPercentage() * Multiplicator); i++)
				sendRewardItems.add(Reward);
		}
	}
	public static void sendItem(UUID PlayerUUID, int rewardsCount)
	{
		Bag playerBag = getBagByOwner(PlayerUUID) == null ? createBag(PlayerUUID) : getBagByOwner(PlayerUUID);
		for(int i = 0; i < rewardsCount; i++)
		{
			int Randomized = new Random().nextInt(sendRewardItems.size()-1);
			ItemStack pickedReward = sendRewardItems.get(Randomized).getItem().clone();
			ItemMeta pickedRewardMeta = pickedReward.getItemMeta();
			List<String> pickedRewardLore = pickedRewardMeta.hasLore() ? pickedRewardMeta.getLore() : new ArrayList<String>();
			LocalDate itemLocalDate = LocalDate.now();
			itemLocalDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
			itemLocalDate = itemLocalDate.plusDays(BagClearManager.Day);
			itemLocalDate = itemLocalDate.plusMonths(BagClearManager.Month);
			itemLocalDate = itemLocalDate.plusYears(BagClearManager.Year);
			String[] formatingDate = itemLocalDate.toString().split("-");
			pickedRewardMeta.setLocalizedName(formatingDate[2].concat("/".concat(formatingDate[1].concat("/".concat(formatingDate[0])))));
			pickedRewardLore.add(ChatColor.BLUE + "Expiration : ".concat(formatingDate[2].concat("/".concat(formatingDate[1].concat("/".concat(formatingDate[0]))))));
			pickedRewardMeta.setLore(pickedRewardLore);
			pickedReward.setItemMeta(pickedRewardMeta);
			playerBag.getBagContent().add(new RewardItem(pickedReward, sendRewardItems.get(Randomized).getID(), sendRewardItems.get(Randomized).getPercentage()));
		}
		if(Bukkit.getPlayer(PlayerUUID) != null)
		{
			if(rewardsCount > 1)
				Bukkit.getPlayer(PlayerUUID).sendMessage(sendSuccessMessage("Vous avez obtenu ".concat(rewardsCount + "").concat(" récompenses, consultez votre sac")));
			else
				Bukkit.getPlayer(PlayerUUID).sendMessage(sendSuccessMessage("Vous avez obtenu une récompense, consultez votre sac"));
		}
	}
	public static Bag createBag(UUID PlayerUUID)
	{
		Bag playerBag = new Bag(new ArrayList<RewardItem>(), PlayerUUID); 
		playerBags.add(playerBag);
		return playerBag;
	}	
	public static boolean isCustomPotion(ItemStack consummedItem)
	{
		if(consummedItem == null)
			return false;
		if(!consummedItem.getType().equals(Material.POTION))
			return false;
		if(consummedItem.getItemMeta().getDisplayName().equals(""))
			return false;
		for(RewardItem Reward :	rewardItems)
		{
			if(Reward.getItem().getItemMeta().getDisplayName().equals(consummedItem.getItemMeta().getDisplayName()))
				return true;
		}
		return false;
	}
	public static CustomPotion getCustomPotion(ItemStack consummedItem)
	{
		for(CustomPotion customPotion : customPotionList)
		{
			if(customPotion.getItemStack().getItemMeta().getDisplayName().equals(consummedItem.getItemMeta().getDisplayName()))
				return customPotion;
		}
		return null;
	}
	public static Effect getEffectByPlayer(UUID Player)
	{
		for(Effect effect : potionEffects)
		{
			if(effect.getOwner().equals(Player))
				return effect;
		}
		return null;
	}
	public static Bag getBagByOwner(UUID PlayerUUID)
	{
		for(Bag bag : playerBags)
		{
			if(bag.getOwner().equals(PlayerUUID))
			{
				return bag;
			}
		}
		return null;
	}
	public static Effect getEffectByOwner(UUID Player)
	{
		for(Effect effect : potionEffects)
		{
			if(effect.getOwner().equals(Player))
				return effect;
		}
		return null;
	}
	
}
