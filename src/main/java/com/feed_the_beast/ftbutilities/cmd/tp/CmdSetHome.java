package com.feed_the_beast.ftbutilities.cmd.tp;

import com.feed_the_beast.ftblib.lib.cmd.CmdBase;
import com.feed_the_beast.ftblib.lib.config.RankConfigAPI;
import com.feed_the_beast.ftblib.lib.data.Universe;
import com.feed_the_beast.ftblib.lib.math.BlockDimPos;
import com.feed_the_beast.ftbutilities.FTBUtilitiesLang;
import com.feed_the_beast.ftbutilities.FTBUtilitiesPermissions;
import com.feed_the_beast.ftbutilities.data.FTBUPlayerData;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;

import javax.annotation.Nullable;
import java.util.List;

public class CmdSetHome extends CmdBase
{
	public CmdSetHome()
	{
		super("sethome", Level.ALL);
	}

	@Override
	public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos pos)
	{
		if (args.length == 1)
		{
			return getListOfStringsMatchingLastWord(args, FTBUPlayerData.get(Universe.get().getPlayer(sender)).homes.list());
		}

		return super.getTabCompletions(server, sender, args, pos);
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException
	{
		EntityPlayerMP player = getCommandSenderAsPlayer(sender);
		FTBUPlayerData data = FTBUPlayerData.get(getForgePlayer(player));

		if (args.length == 0)
		{
			args = new String[] {"home"};
		}

		args[0] = args[0].toLowerCase();

		int maxHomes = RankConfigAPI.get(player, FTBUtilitiesPermissions.HOMES_MAX).getInt();

		if (maxHomes <= 0 || data.homes.size() >= maxHomes)
		{
			if (maxHomes == 0 || data.homes.get(args[0]) == null)
			{
				throw FTBUtilitiesLang.HOME_LIMIT.commandError();
			}
		}

		data.homes.set(args[0], new BlockDimPos(player));
		FTBUtilitiesLang.HOME_SET.sendMessage(sender, args[0]);
		data.player.markDirty();
	}
}