package net.dmulloy2.ultimatearena.types;

import lombok.Getter;
import net.dmulloy2.ultimatearena.UltimateArena;
import net.dmulloy2.ultimatearena.arenas.Arena;
import net.dmulloy2.ultimatearena.util.FormatUtil;
import net.dmulloy2.ultimatearena.util.Util;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;

/**
 * Represents an ArenaSign, whether it be join or not
 * 
 * @author dmulloy2
 */

@Getter
public class ArenaSign
{
	private Location location;
	private ArenaZone arena;
	private int id;
	private Sign sign;
	
	private final UltimateArena plugin;

	/**
	 * Creates a new ArenaSign
	 * 
	 * @param plugin
	 *            - {@link UltimateArena} plugin instance
	 * @param loc
	 *            - {@link Location} of the spawn
	 * @param zone
	 *            - {@link ArenaZone} that the sign is for
	 * @param id
	 *            - The sign's ID
	 */
	public ArenaSign(UltimateArena plugin, Location loc, ArenaZone zone, int id)
	{
		this.plugin = plugin;
		this.location = loc;
		this.arena = zone;
		this.id = id;
		this.sign = getSign();
	}

	/**
	 * Gets the {@link Sign} instance
	 * 
	 * @return {@link Sign} instance
	 */
	public Sign getSign()
	{
		Block block = location.getWorld().getBlockAt(location);
		if (block.getState() instanceof Sign)
		{
			return (Sign) block.getState();
		}

		return null;
	}

	/**
	 * Updates the Sign
	 */
	public void update()
	{
		if (getSign() == null)
		{
			plugin.getSignHandler().deleteSign(this);
			return;
		}

		plugin.debug("Updating sign: {0}", id);
		
		sign.setLine(0, "[UltimateArena]");
		sign.setLine(1, arena.getArenaName());
		
		// Line 2
		StringBuilder line = new StringBuilder();
		if (isActive())
		{
			Arena ar = getArena();
			if (ar.isInLobby())
			{
				line.append(FormatUtil.format("&aJoin ({0} sec)", ar.getStartTimer()));
			}
			else
			{
				line.append(FormatUtil.format("&cIn-Game"));
			}
		}
		else
		{
			line.append(FormatUtil.format("&aJoin"));
		}
		
		sign.setLine(2, line.toString());
		
		// Line 3
		line = new StringBuilder();
		if (isActive())
		{
			Arena ar = getArena();

			switch (ar.getGameMode())
			{
				case DISABLED:
					line.append("STOPPING (0/");
					line.append(arena.getMaxPlayers());
					line.append(")");
					break;
				case IDLE:
					line.append("IDLE (0/");
					line.append(arena.getMaxPlayers());
					line.append(")");
					break;
				case INGAME:
					line.append("INGAME (");
					line.append(ar.getActivePlayers());
					line.append("/");
					line.append(arena.getMaxPlayers());
					line.append(")");
					break;
				case LOBBY:
					line.append("LOBBY (");
					line.append(ar.getActivePlayers());
					line.append("/");
					line.append(arena.getMaxPlayers());
					line.append(")");
					break;
				case STOPPING:
					line.append("STOPPING (0/");
					line.append(arena.getMaxPlayers());
					line.append(")");
					break;
				default:
					break;
				
			}
		}
		else
		{
			if (arena.isDisabled())
			{
				line.append("DISABLED");
			}
			else
			{
				line.append("IDLE");
			}
			
			line.append("(0/");
			line.append(arena.getMaxPlayers());
			line.append(")");
		}

		sign.setLine(3, line.toString());

		sign.update();
	}
	
	private final boolean isActive()
	{
		return plugin.getArena(arena.getArenaName()) != null;
	}
	
	private final Arena getArena()
	{
		return plugin.getArena(arena.getArenaName());
	}
	
	public final String getName()
	{
		return arena.getArenaName();
	}

	@Override
	public String toString()
	{
		StringBuilder ret = new StringBuilder();
		ret.append("ArenaSign {");
		ret.append("id=" + id + ", ");
		ret.append("loc=" + Util.locationToString(location));
		ret.append("}");

		return ret.toString();
	}
}