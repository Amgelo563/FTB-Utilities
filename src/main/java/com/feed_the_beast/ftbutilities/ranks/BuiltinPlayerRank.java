package com.feed_the_beast.ftbutilities.ranks;

import com.feed_the_beast.ftblib.FTBLibCommon;
import com.feed_the_beast.ftblib.lib.config.ConfigNull;
import com.feed_the_beast.ftblib.lib.config.ConfigValue;
import com.feed_the_beast.ftblib.lib.config.RankConfigValueInfo;
import com.feed_the_beast.ftblib.lib.util.misc.Node;

/**
 * @author LatvianModder
 */
public class BuiltinPlayerRank extends BuiltinRank
{
	BuiltinPlayerRank(Ranks r)
	{
		super(r, "builtin_player");
	}

	@Override
	public Rank getParent()
	{
		return this;
	}

	@Override
	public ConfigValue getConfig(Node node)
	{
		RankConfigValueInfo config = FTBLibCommon.RANK_CONFIGS_MIRROR.get(node);
		return config == null ? ConfigNull.INSTANCE : config.defaultValue;
	}
}