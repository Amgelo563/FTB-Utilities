package latmod.ftbu.core.net;
import io.netty.buffer.ByteBuf;
import latmod.ftbu.core.client.LatCoreMCClient;
import latmod.ftbu.core.event.LMPlayerClientEvent;
import latmod.ftbu.core.gui.IClientActionGui;
import latmod.ftbu.core.world.*;
import latmod.ftbu.mod.FTBU;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.nbt.NBTTagCompound;
import cpw.mods.fml.common.network.simpleimpl.*;
import cpw.mods.fml.relauncher.*;

public class MessageLMPlayerUpdate extends MessageLM<MessageLMPlayerUpdate> implements IClientMessageLM<MessageLMPlayerUpdate>
{
	public int playerID;
	public String action;
	public NBTTagCompound data;
	
	public MessageLMPlayerUpdate() { }
	
	public MessageLMPlayerUpdate(LMPlayerServer p, String a)
	{
		playerID = p.playerID;
		action = a;
		
		data = new NBTTagCompound();
		p.writeToNet(data);
	}
	
	public void fromBytes(ByteBuf bb)
	{
		playerID = bb.readInt();
		action = readString(bb);
		data = readTagCompound(bb);
	}
	
	public void toBytes(ByteBuf bb)
	{
		bb.writeInt(playerID);
		writeString(bb, action);
		writeTagCompound(bb, data);
	}
	
	public IMessage onMessage(MessageLMPlayerUpdate m, MessageContext ctx)
	{ FTBU.proxy.handleClientMessage(m, ctx); return null; }
	
	@SideOnly(Side.CLIENT)
	public void onMessageClient(MessageLMPlayerUpdate m, MessageContext ctx)
	{
		LMPlayerClient p = LMWorld.client.getPlayer(m.playerID);
		p.readFromNet(m.data);
		new LMPlayerClientEvent.DataChanged(p, action).post();
		
		GuiScreen g = LatCoreMCClient.getMinecraft().currentScreen;
		if(g != null && g instanceof IClientActionGui)
			((IClientActionGui)g).onClientAction(action);
	}
}