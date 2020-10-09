package fr.mecopi.votereward.objects;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.attribute.AttributeModifier.Operation;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockCookEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import fr.mecopi.votereward.managers.VoteRewardManager;
import net.md_5.bungee.api.ChatColor;

public class CustomItems 
{
	
	public static ItemStack goblinPickaxe; //ID : 0
	public static ItemStack giantBoots; //ID : 1
	public static ItemStack unbreakingHoe; //ID : 2
	public static ItemStack mendingBook; //ID : 3
	public static ItemStack fortuneBook; //ID : 4
	public static ItemStack silkTouchBook; //ID : 5
	public static ItemStack fireworkRocket; //ID : 6
	public static ItemStack rawBear; //ID : none
	public static ItemStack rawHorse; //ID : none
	public static ItemStack cookedBear; //ID : none
	public static ItemStack cookedHorse; //ID : none
	public static ItemStack ULU; //ID : 7
	public static ItemStack indianSpears; //ID : 8
	
	private static ItemMeta itemMeta;
	
	public static void Init()
	{
		setGoblinPickaxe();
		setGiantBoots();
		setUnbreakingHoe();
		setMendingBook();
		setFortuneBook();
		setSilkTouchBook();
		setFireworkRocket();
		setRawBear();
		setRawHorse();
		setCookedBear();
		setCookedHorse();
		setULU();
		setIndianSpears();
	}
	
	private static void setGoblinPickaxe()
	{
		goblinPickaxe = new ItemStack(Material.GOLDEN_PICKAXE);
		itemMeta = goblinPickaxe.getItemMeta();
		itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&bPioche de Gobelin"));
		itemMeta.setLore(Arrays.asList(ChatColor.YELLOW + "Cette pioche vous confère", ChatColor.YELLOW + "une précision accrue et", ChatColor.YELLOW + "vous permet d'extraire", ChatColor.YELLOW + "des minéraux précieux", ChatColor.YELLOW + "des roches"));
		itemMeta.setCustomModelData(1);
		goblinPickaxe.setItemMeta(itemMeta);
		VoteRewardManager.rewardItems.add(new RewardItem(goblinPickaxe, 0, 0.15));
	}
	private static void setGiantBoots()
	{
		giantBoots = new ItemStack(Material.LEATHER_BOOTS);
		itemMeta = giantBoots.getItemMeta();
		itemMeta.addAttributeModifier(Attribute.GENERIC_ARMOR, new AttributeModifier(UUID.fromString("ec3474c3-b57c-4fbf-ba1f-a7c5ac9292c5"), "generic.armor", 1, Operation.ADD_NUMBER, EquipmentSlot.FEET));
		itemMeta.addAttributeModifier(Attribute.GENERIC_MOVEMENT_SPEED, new AttributeModifier(UUID.fromString("dcc66b53-a22c-48cc-afbd-274a02967392"), "generic.movementSpeed", 0.75, Operation.MULTIPLY_SCALAR_1, EquipmentSlot.FEET));
		itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		itemMeta.setDisplayName("§bBottes de Géant");
		itemMeta.setLore(Arrays.asList("§eCes bottes semblent émettre", "§eune certaine forme de magie", "§eet vous permettraient", "§ed'aller plus vite", "", "§cAttention la durabilité", "§cbaisse avec la distance", "§cparcourue"));
		itemMeta.setCustomModelData(1);
		giantBoots.setItemMeta(itemMeta);
		VoteRewardManager.rewardItems.add(new RewardItem(giantBoots, 1, 0.05));
	}
	private static void setUnbreakingHoe()
	{
		unbreakingHoe = new ItemStack(Material.STONE_HOE);
		itemMeta = unbreakingHoe.getItemMeta();
		itemMeta.addEnchant(Enchantment.DURABILITY, 20, true);
		itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		itemMeta.setLore(Arrays.asList(ChatColor.GRAY + "Solidité XX"));
		unbreakingHoe.setItemMeta(itemMeta);
		VoteRewardManager.rewardItems.add(new RewardItem(unbreakingHoe, 2, 0.01));
	}
	private static void setMendingBook()
	{
		mendingBook = new ItemStack(Material.ENCHANTED_BOOK);
		EnchantmentStorageMeta bookMeta = (EnchantmentStorageMeta)mendingBook.getItemMeta();
		bookMeta.addStoredEnchant(Enchantment.MENDING, 1, true);
		bookMeta.setLore(Arrays.asList(ChatColor.YELLOW + "Enchanté avec Raccommodage"));
		mendingBook.setItemMeta(bookMeta);
		VoteRewardManager.rewardItems.add(new RewardItem(mendingBook, 3, 0.04));
	}
	private static void setSilkTouchBook()
	{
		silkTouchBook = new ItemStack(Material.ENCHANTED_BOOK);
        EnchantmentStorageMeta bookMeta =  (EnchantmentStorageMeta)silkTouchBook.getItemMeta();
        bookMeta.addStoredEnchant(Enchantment.SILK_TOUCH, 1, false);
        bookMeta.setLore(Arrays.asList(ChatColor.YELLOW + "Enchanté avec Toucher de Soie"));
        silkTouchBook.setItemMeta(bookMeta);
        VoteRewardManager.rewardItems.add(new RewardItem(silkTouchBook, 4, 0.09));
	}
	private static void setFortuneBook()
	{
		fortuneBook = new ItemStack(Material.ENCHANTED_BOOK);
        EnchantmentStorageMeta bookMeta =  (EnchantmentStorageMeta)fortuneBook.getItemMeta();
        bookMeta.addStoredEnchant(Enchantment.LOOT_BONUS_BLOCKS, 3, false);
        bookMeta.setLore(Arrays.asList(ChatColor.YELLOW + "Enchanté avec Fortune"));
		fortuneBook.setItemMeta(bookMeta);
		VoteRewardManager.rewardItems.add(new RewardItem(fortuneBook, 5, 0.07));
	}
	private static void setFireworkRocket()
	{
		fireworkRocket = new ItemStack(Material.FIREWORK_ROCKET, 64);
        FireworkMeta itemMeta = (FireworkMeta)fireworkRocket.getItemMeta();
        itemMeta.setPower(3);
        fireworkRocket.setItemMeta(itemMeta);
        VoteRewardManager.rewardItems.add(new RewardItem(fireworkRocket, 6, 0.15));
	}
	private static void setULU()
	{
		ULU = new ItemStack(Material.STONE_AXE);
		itemMeta = ULU.getItemMeta();

        itemMeta.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE, new AttributeModifier(UUID.fromString("1de4ca9e-fe66-4222-966d-73f226e8fecd"), "generic.attackDamage", 4, Operation.ADD_NUMBER, EquipmentSlot.HAND));
		itemMeta.addAttributeModifier(Attribute.GENERIC_ATTACK_SPEED, new AttributeModifier(UUID.fromString("c26bf64f-1a1a-4425-bc4e-77dc56845f8b"), "generic.attackSpeed", -0.65, Operation.MULTIPLY_SCALAR_1, EquipmentSlot.HAND));
		itemMeta.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE, new AttributeModifier(UUID.fromString("10cea9ed-2627-4a6e-b904-52228bbff57b"), "generic.attackDamage", 4, Operation.ADD_NUMBER, EquipmentSlot.OFF_HAND));
		itemMeta.addAttributeModifier(Attribute.GENERIC_ATTACK_SPEED, new AttributeModifier(UUID.fromString("48c680ff-6d3a-4bff-b62c-2badd65cf045"), "generic.attackSpeed", -0.65, Operation.MULTIPLY_SCALAR_1, EquipmentSlot.OFF_HAND));
		itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		itemMeta.setDisplayName("§bUlu");
		itemMeta.setLore(Arrays.asList("§eUn couteau inuit", "§epermettant de", "§edépecer un ours"));
		itemMeta.setCustomModelData(1);
		ULU.setItemMeta(itemMeta);
		VoteRewardManager.rewardItems.add(new RewardItem(ULU, 7, 0.18));
	}
	private static void setIndianSpears()
	{
		indianSpears = new ItemStack(Material.GOLDEN_SWORD);
        itemMeta = indianSpears.getItemMeta();

		itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		itemMeta.setDisplayName("§bLance Indienne");
		itemMeta.setLore(Arrays.asList("§ePermet d'obtenir du", "§esteak de cheval"));
		itemMeta.setCustomModelData(1);
		indianSpears.setItemMeta(itemMeta);
		VoteRewardManager.rewardItems.add(new RewardItem(indianSpears, 8, 0.21));
	}
	private static void setRawBear()
	{
		rawBear = new ItemStack(Material.BEEF);
        itemMeta = rawBear.getItemMeta();
        itemMeta.setDisplayName("§fOurs Cru");
        itemMeta.setLocalizedName("rawBear");
        itemMeta.setLore(Arrays.asList(ChatColor.YELLOW + "Cette viande semble se cuire au feu de camp"));
        itemMeta.setCustomModelData(2);
        rawBear.setItemMeta(itemMeta);
	}
	private static void setRawHorse()
	{
		rawHorse = new ItemStack(Material.BEEF);
        ItemMeta itemMeta = rawHorse.getItemMeta();
        itemMeta.setDisplayName("§fCheval Cru");
        itemMeta.setLore(Arrays.asList(ChatColor.YELLOW + "Cette viande semble se cuire au feu de camp"));
        itemMeta.setCustomModelData(1);
        rawHorse.setItemMeta(itemMeta);
	}
	private static void setCookedBear()
	{
		cookedBear = new ItemStack(Material.COOKED_BEEF);
        ItemMeta itemMeta = cookedBear.getItemMeta();
        itemMeta.setDisplayName("§fOurs Cuit");
        itemMeta.setLocalizedName("cookedBear");
        itemMeta.setCustomModelData(2);
        cookedBear.setItemMeta(itemMeta);
	}
	private static void setCookedHorse()
	{
		cookedHorse = new ItemStack(Material.COOKED_BEEF);
        ItemMeta itemMeta = cookedHorse.getItemMeta();
        itemMeta.setDisplayName("§fCheval Cuit");
        itemMeta.setCustomModelData(1);
        cookedHorse.setItemMeta(itemMeta);
	}

	//Events
	
	public static void GoblinPickaxeEvent(BlockBreakEvent e)
	{
		if(e.getBlock() != null && !e.isCancelled())
		{
			if(!e.getPlayer().getInventory().getItemInMainHand().getType().isAir() && e.getPlayer().getInventory().getItemInMainHand().getItemMeta().getDisplayName().equals(ChatColor.translateAlternateColorCodes('&', "&bPioche de Gobelin"))) //Cast error & check if it's goblin pickaxe
			{
				if(e.getBlock().getType().equals(Material.STONE))
				{
					e.setDropItems(false);
					int mineralRand = new Random().nextInt(65);
					if (mineralRand >= 0 && mineralRand <= 14) {
						e.getBlock().getLocation().getWorld().dropItemNaturally(e.getBlock().getLocation(), new ItemStack(Material.IRON_NUGGET, getRandomNumberInRange(7, 11)));
					}
					else if (mineralRand >= 15 && mineralRand <= 29) {
						e.getBlock().getLocation().getWorld().dropItemNaturally(e.getBlock().getLocation(), new ItemStack(Material.LAPIS_LAZULI, getRandomNumberInRange(6, 10)));
					}
					else if (mineralRand >= 30 && mineralRand <= 39) {
						e.getBlock().getLocation().getWorld().dropItemNaturally(e.getBlock().getLocation(), new ItemStack(Material.REDSTONE, getRandomNumberInRange(6, 10)));
					}
					else if (mineralRand >= 40 && mineralRand <= 49) {
						e.getBlock().getLocation().getWorld().dropItemNaturally(e.getBlock().getLocation(), new ItemStack(Material.GOLD_NUGGET, getRandomNumberInRange(6, 10)));
					}
					else if (mineralRand >= 50 && mineralRand <= 57) {
						e.getBlock().getLocation().getWorld().dropItemNaturally(e.getBlock().getLocation(), new ItemStack(Material.DIAMOND, getRandomNumberInRange(1, 2)));
					}
					else if (mineralRand >= 58 && mineralRand <= 64) {
						e.getBlock().getLocation().getWorld().dropItemNaturally(e.getBlock().getLocation(), new ItemStack(Material.EMERALD, getRandomNumberInRange(1, 2)));
					}
				}
				else if(e.getBlock().getType().equals(Material.NETHERRACK))
				{
					e.setDropItems(false);
					e.getBlock().getLocation().getWorld().dropItemNaturally(e.getBlock().getLocation(), new ItemStack(Material.QUARTZ, getRandomNumberInRange(1, 5)));
				}
			}
		}
	}
	public static void ULUEvent(EntityDeathEvent e)
	{
		if(e.getEntity().getType().equals(EntityType.POLAR_BEAR) && e.getEntity().getKiller() instanceof Player)
		{
			Player Killer = e.getEntity().getKiller();
			if(Killer.getInventory().getItemInMainHand().getItemMeta().getDisplayName().equals(ULU.getItemMeta().getDisplayName()))
			{
				Bukkit.getWorld(Killer.getLocation().getWorld().getName()).dropItemNaturally(e.getEntity().getLocation(), rawBear);
			}
		}
	}
	public static void IndianSpearEvent(EntityDeathEvent e)
	{
		if(e.getEntity().getType().equals(EntityType.HORSE) && e.getEntity().getKiller() instanceof Player)
		{
			Player Killer = e.getEntity().getKiller();
			if(Killer.getInventory().getItemInMainHand().getItemMeta().getDisplayName().equals(indianSpears.getItemMeta().getDisplayName()))
			{
				Bukkit.getWorld(Killer.getLocation().getWorld().getName()).dropItemNaturally(e.getEntity().getLocation(), rawHorse);
			}
		}
	}
	public static void BakeCustomFood(BlockCookEvent e)
	{
		if(e.getSource().getItemMeta().getDisplayName().equals(rawBear.getItemMeta().getDisplayName()))
		{
			e.getResult().setItemMeta(cookedBear.getItemMeta().clone());
		}
		else if(e.getSource().getItemMeta().getDisplayName().equals(rawHorse.getItemMeta().getDisplayName()))
		{
			e.getResult().setItemMeta(cookedHorse.getItemMeta().clone());
		}
	}
	public static void CantBake(InventoryClickEvent e)
	{
		if(e.getInventory().getType().equals(InventoryType.PLAYER))
		{
			if(e.getInventory().getItem(1) != null)
			{
				e.setCancelled(isCustom(e.getInventory().getItem(1)));
				return;
			}
			if(e.getInventory().getItem(2) != null)
			{
				e.setCancelled(isCustom(e.getInventory().getItem(2)));
				return;
			}
			if(e.getInventory().getItem(3) != null)
			{
				e.setCancelled(isCustom(e.getInventory().getItem(3)));
				return;
			}
			if(e.getInventory().getItem(4) != null)
			{
				e.setCancelled(isCustom(e.getInventory().getItem(4)));
				return;
			}
		}
		if(e.getCurrentItem() != null && !e.getCurrentItem().getType().equals(Material.AIR))
		{
			if((e.getInventory().getType().equals(InventoryType.SMOKER) || e.getInventory().getType().equals(InventoryType.FURNACE) || e.getInventory().getType().equals(InventoryType.ANVIL) || e.getInventory().getType().equals(InventoryType.ENCHANTING) || e.getInventory().getType().equals(InventoryType.WORKBENCH)) && isCustom(e.getCurrentItem()) && !e.getCurrentItem().getType().equals(Material.ENCHANTED_BOOK))
			{
				e.setCancelled(true);
			}
		}
	}
	public static boolean isCustom(ItemStack toCompare)
	{
		List<ItemStack> customItems = new ArrayList<ItemStack>();
		for(RewardItem Reward : VoteRewardManager.rewardItems)
		{
			if(Reward.getID() != -1)
				customItems.add(Reward.getItem());
		}
		customItems.add(rawBear);
		customItems.add(rawHorse);
		customItems.add(cookedBear);
		customItems.add(cookedHorse);
		for(ItemStack itemStack : customItems)
		{
			if(itemStack.isSimilar(toCompare))
				return true;
		}
		return false;
	}
	public static void EatCustomFood(PlayerItemConsumeEvent e)
	{
		Player player = e.getPlayer();
		if(e.getItem().getItemMeta().getDisplayName().equals(cookedBear.getItemMeta().getDisplayName()))
		{
			player.setFoodLevel(20);
		}
		else if(e.getItem().getItemMeta().getDisplayName().equals(rawBear.getItemMeta().getDisplayName()))
		{
			if((player.getFoodLevel() + 3) <= 20)
				player.setFoodLevel(player.getFoodLevel() + 3);
			else
				player.setFoodLevel(20);
			player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, (20 * 15), 3));
		}
		else if(e.getItem().getItemMeta().getDisplayName().equals(cookedHorse.getItemMeta().getDisplayName()))
		{
			if((player.getFoodLevel() + 5) <= 20)
				player.setFoodLevel(player.getFoodLevel() + 5);
			else
				player.setFoodLevel(20);
		}
		else if(e.getItem().getItemMeta().getDisplayName().equals(rawHorse.getItemMeta().getDisplayName()))
		{
			if((player.getFoodLevel() + 3) <= 20)
				player.setFoodLevel(player.getFoodLevel() + 3);
			else
				player.setFoodLevel(20);
			player.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, (20 * 15), 3));
		}
	}
	
	//Functions

	
	private static int getRandomNumberInRange(int min, int max) {
		return new Random().nextInt((max - min) + 1) + min;
	}
	
}
