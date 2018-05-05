package com.feed_the_beast.ftbutilities.cmd.tp;

import com.feed_the_beast.ftblib.lib.cmd.CmdBase;
import com.feed_the_beast.ftblib.lib.data.Universe;
import com.feed_the_beast.ftblib.lib.math.BlockDimPos;
import com.feed_the_beast.ftbutilities.data.FTBUtilitiesUniverseData;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.server.command.TextComponentHelper;

public class CmdSetWarp extends CmdBase
{
	public CmdSetWarp()
	{
		super("setwarp", Level.OP);
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException
	{
		checkArgs(sender, args, 1);
		EntityPlayerMP player = getCommandSenderAsPlayer(sender);
		BlockPos c;

		args[0] = args[0].toLowerCase();

		if (args.length >= 4)
		{
			int x = parseInt(args[1]);
			int y = parseInt(args[2]);
			int z = parseInt(args[3]);
			c = new BlockPos(x, y, z);
		}
		else
		{
			c = player.getPosition();
		}

		FTBUtilitiesUniverseData.WARPS.set(args[0], new BlockDimPos(c, player.dimension));
		sender.sendMessage(TextComponentHelper.createComponentTranslation(sender, "ftbutilities.lang.warps.set", args[0]));
		Universe.get().markDirty();
	}
}