package com.orange451.UltimateArena.Arenas.Objects;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;

import com.orange451.UltimateArena.UltimateArena;
import com.orange451.UltimateArena.Arenas.Arena;

public class ArenaSign 
{
	private Location loc;
	private boolean join = false;
	private ArenaZone zone;
	private Sign sign = null;
	private int id;
	
	private UltimateArena plugin;
	public ArenaSign(UltimateArena plugin, Location loc, ArenaZone zone, boolean join, int id)
	{
		this.plugin = plugin;
		this.loc = loc;
		this.zone = zone;
		this.join = join;
		this.id = id;
		this.sign = getSign();
	}
	
	public Sign getSign()
	{
		Block block = loc.getWorld().getBlockAt(loc);
		if (block.getState() instanceof Sign)
		{
			return (Sign)block.getState();
		}
		
		return null;
	}
	
	public void update()
	{
		if (getSign() == null)
		{
			plugin.deleteSign(this);
			return;
		}
		
		if (join)
		{
			sign.setLine(0, "[UltimateArena]");
			sign.setLine(1, "Click to join");
			sign.setLine(2, zone.arenaName);
			sign.setLine(3, "");
		}
		else
		{
			sign.setLine(0, "[UA Stats]");
			sign.setLine(1, "Arena: " + zone.arenaName);
			sign.setLine(2, "Type: " + zone.arenaType);
			sign.setLine(3, getStatus());
		}
		
		sign.update();
	}
	
	public String getStatus()
	{
		StringBuilder line = new StringBuilder();
		if (plugin.getArena(zone.arenaName) != null)
		{
			Arena a = plugin.getArena(zone.arenaName);
			if (a.starttimer > 1)
			{
				line.append("LOBBY (");
			}
			else
			{
				line.append("INGAME (");
			}
			line.append(a.amtPlayersInArena + "/" + zone.maxPlayers + ")");
		}
		else
		{
			if (zone.disabled)
			{
				line.append("DISABLED (0/0)");
			}
			else
			{
				line.append("IDLE (0/");
				line.append(zone.maxPlayers);
				line.append(")");
			}
		}
		
		return line.toString();
	}
	
	public void save()
	{
		plugin.getFileHelper().saveSign(this);
	}
	
	public Location getLocation()
	{
		return loc;
	}
	
	public String getArena()
	{
		return zone.arenaName;
	}
	
	public String getArenaType()
	{
		return zone.arenaType;
	}
	
	public boolean isJoinSign()
	{
		return join;
	}
	
	public boolean isStatusSign()
	{
		return ! join;
	}
	
	public int getId()
	{
		return id;
	}
}