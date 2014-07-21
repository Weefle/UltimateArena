package net.dmulloy2.ultimatearena.types;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import lombok.Getter;
import lombok.NonNull;
import net.dmulloy2.util.ItemUtil;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;

/**
 * @author dmulloy2
 */

@Getter
public final class KillStreak
{
	public static enum Type
	{
		ITEM, MOB;
	}

	private final int kills;
	private final String message;
	private final Type type;

	// Mob Stuff
	private EntityType mobType;
	private int mobAmount;

	// Item
	private ItemStack item;

	// Base constructor
	private KillStreak(int kills, @NonNull String message, @NonNull Type type)
	{
		this.kills = kills;
		this.message = message;
		this.type = type;
	}

	// Mob Constructor
	public KillStreak(int kills, @NonNull String message, @NonNull EntityType mobType, int mobAmount)
	{
		this(kills, message, Type.MOB);
		this.mobType = mobType;
		this.mobAmount = mobAmount;
	}

	// Item Constructor
	public KillStreak(int kills, @NonNull String message, @NonNull ItemStack item)
	{
		this(kills, message, Type.ITEM);
		this.item = item;
	}

	/**
	 * Performs this kill streak
	 */
	public final void perform(ArenaPlayer ap)
	{
		switch (type)
		{
			case ITEM:
				ap.giveItem(item.clone());
				break;
			case MOB:
				for (int i = 0; i < mobAmount; i++)
					ap.getPlayer().getWorld().spawnEntity(ap.getPlayer().getLocation(), mobType);
				break;
		}

		ap.sendMessage(message);
	}

	public static Map<Integer, List<KillStreak>> defaultKillStreak(FieldType type)
	{
		Map<Integer, List<KillStreak>> ret = new LinkedHashMap<>();

		switch (type)
		{
			case CONQUEST:
				// Omit zombies
				ret.put(2, Arrays.asList(new KillStreak[] {
						new KillStreak(2, "&e2 &3kills! Unlocked strength potion!", ItemUtil.readPotion("strength, 1, 1, false"))
				}));

				ret.put(4, Arrays.asList(new KillStreak[] {
						new KillStreak(4, "&e4 &3kills! Unlocked health potion!", ItemUtil.readPotion("heal, 1, 1, false")),
						new KillStreak(4, "&e4 &3kills! Unlocked food!", new ItemStack(Material.GRILLED_PORK, 2))
				}));

				ret.put(8, Arrays.asList(new KillStreak[] {
						new KillStreak(8, "&e8 &3kills! Unlocked attack dogs!", EntityType.WOLF, 2)
				}));

				ret.put(12, Arrays.asList(new KillStreak[] {
						new KillStreak(12, "&e12 &3kills! Unlocked regen potion!", ItemUtil.readPotion("regen, 1, 1, false")),
						new KillStreak(12, "&e12 &3kills! Unlocked food!", new ItemStack(Material.GRILLED_PORK, 2))
				}));

				break;
			case HUNGER:
				// Do nothing
				break;
			case KOTH:
				ret.put(2, Arrays.asList(new KillStreak[] {
						new KillStreak(2, "&e2 &3kills! Unlocked strength potion!", ItemUtil.readPotion("strength, 1, 1, false"))
				}));

				ret.put(4, Arrays.asList(new KillStreak[] {
						new KillStreak(4, "&e4 &3kills! Unlocked health potion!", ItemUtil.readPotion("heal, 1, 1, false")),
						new KillStreak(4, "&e4 &3kills! Unlocked food!", new ItemStack(Material.GRILLED_PORK, 2))
				}));

				ret.put(12, Arrays.asList(new KillStreak[] {
						new KillStreak(12, "&e12 &3kills! Unlocked regen potion!", ItemUtil.readPotion("regen, 1, 1, false")),
						new KillStreak(12, "&e12 &3kills! Unlocked food!", new ItemStack(Material.GRILLED_PORK, 2))
				}));

				break;
			case MOB:
				ret.put(8, Arrays.asList(new KillStreak[] {
						new KillStreak(8, "&e8 &3kills! Unlocked strength potion!", ItemUtil.readPotion("strength, 1, 1, false"))
				}));

				ret.put(12, Arrays.asList(new KillStreak[] {
						new KillStreak(12, "&e12 &3kills! Unlocked swiftness potion!", ItemUtil.readPotion("strength, 1, 1, false"))
				}));

				ret.put(16, Arrays.asList(new KillStreak[] {
						new KillStreak(16, "&e16 &3kills! Unlocked Anti-Fire!", ItemUtil.readPotion("fireres, 1, 1, false"))
				}));

				ret.put(24, Arrays.asList(new KillStreak[] {
						new KillStreak(24, "&e24 &3kills! Unlocked health potion!", ItemUtil.readPotion("heal, 1, 1, false")),
						new KillStreak(24, "&e24 &3kills! Unlocked food!", new ItemStack(Material.GRILLED_PORK, 2))
				}));

				ret.put(32, Arrays.asList(new KillStreak[] {
						new KillStreak(24, "&e32 &3kills! Unlocked attack dogs!", EntityType.WOLF, 3)
				}));

				ret.put(40, Arrays.asList(new KillStreak[] {
						new KillStreak(40, "&e40 &3kills! Unlocked regen potion!", ItemUtil.readPotion("regen, 1, 1, false")),
						new KillStreak(40, "&e40 &3kills! Unlocked food!", new ItemStack(Material.GRILLED_PORK, 2))
				}));

				ret.put(72, Arrays.asList(new KillStreak[] {
						new KillStreak(72, "&e72 &3kills! Unlocked Golden Apples!", new ItemStack(Material.GOLDEN_APPLE, 2))
				}));

				ret.put(112, Arrays.asList(new KillStreak[] {
						new KillStreak(112, "&e112 &3kills! Unlocked Golden Apples!", new ItemStack(Material.GOLDEN_APPLE, 2))
				}));

				break;
			default:
				ret.put(2, Arrays.asList(new KillStreak[] {
						new KillStreak(2, "&e2 &3kills! Unlocked strength potion!", ItemUtil.readPotion("strength, 1, 1, false"))
				}));

				ret.put(4, Arrays.asList(new KillStreak[] {
						new KillStreak(4, "&e4 &3kills! Unlocked health potion!", ItemUtil.readPotion("heal, 1, 1, false")),
						new KillStreak(4, "&e4 &3kills! Unlocked food!", new ItemStack(Material.GRILLED_PORK, 2))
				}));

				ret.put(5, Arrays.asList(new KillStreak[] {
						new KillStreak(5, "&e5 &3kills! Unlocked Zombies!", EntityType.ZOMBIE, 4)
				}));

				ret.put(8, Arrays.asList(new KillStreak[] {
						new KillStreak(8, "&e8 &3kills! Unlocked attack dogs!", EntityType.WOLF, 2)
				}));

				ret.put(12, Arrays.asList(new KillStreak[] {
						new KillStreak(12, "&e12 &3kills! Unlocked regen potion!", ItemUtil.readPotion("regen, 1, 1, false")),
						new KillStreak(12, "&e12 &3kills! Unlocked food!", new ItemStack(Material.GRILLED_PORK, 2))
				}));

				break;
		}

		return ret;
	}
}