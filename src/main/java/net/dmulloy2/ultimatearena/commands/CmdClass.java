package net.dmulloy2.ultimatearena.commands;

import net.dmulloy2.gui.GUIHandler;
import net.dmulloy2.ultimatearena.UltimateArena;
import net.dmulloy2.ultimatearena.gui.ClassSelectionGUI;
import net.dmulloy2.ultimatearena.types.ArenaClass;
import net.dmulloy2.ultimatearena.types.ArenaPlayer;
import net.dmulloy2.ultimatearena.types.Permission;
import net.dmulloy2.util.FormatUtil;

/**
 * @author dmulloy2
 */

public class CmdClass extends UltimateArenaCommand
{
	public CmdClass(UltimateArena plugin)
	{
		super(plugin);
		this.name = "class";
		this.aliases.add("cl");
		this.optionalArgs.add("class");
		this.description = "Switch UltimateArena classes";
		this.permission = Permission.CLASS;
		this.mustBePlayer = true;
	}

	@Override
	public void perform()
	{
		ArenaPlayer ap = plugin.getArenaPlayer(player);
		if (ap == null)
		{
			err("You must be in an arena to do this!");
			return;
		}

		if (args.length == 0)
		{
			ClassSelectionGUI csGUI = new ClassSelectionGUI(plugin, player);
			GUIHandler.openGUI(player, csGUI);
			return;
		}

		ArenaClass cl = plugin.getArenaClass(args[0]);
		if (cl == null)
		{
			err("Could not find a class by the name of \"&c{0}&4\"!", args[0]);
			return;
		}

		if (! cl.checkPermission(player))
		{
			err("You do not have permissions for this class.");
			return;
		}

		if (! ap.setClass(cl))
		{
			err("You cannot use this class in this arena.");
			return;
		}

		String name = cl.getName();
		String article = FormatUtil.getArticle(name);

		if (ap.getArena().isInLobby())
		{
			sendpMessage("&3You will spawn as {0}: &e{1}", article, name);
		}
		else
		{
			sendpMessage("&3You will respawn as {0}: &e{1}", article, name);
		}
	}
}