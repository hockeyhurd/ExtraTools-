package com.hockeyhurd.handler;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;

import com.hockeyhurd.item.tool.ItemGlowExcavator;
import com.hockeyhurd.item.tool.ItemGlowHammer;
import com.hockeyhurd.item.tool.ItemXyniteHammer;
import com.hockeyhurd.util.Keybindings;
import com.hockeyhurd.util.interfaces.IKeyBound;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class MessageTogglePressed implements IMessage, IMessageHandler<MessageTogglePressed, IMessage> {

	private byte keyPressed;

	public IMessage onMessage(MessageTogglePressed message, MessageContext ctx) {
		EntityPlayer player = ctx.getServerHandler().playerEntity;
		if (player != null && player.getCurrentEquippedItem() != null) {
			if (player.getCurrentEquippedItem().getItem() instanceof ItemGlowHammer) ((IKeyBound) player.getCurrentEquippedItem().getItem()).doKeyBindingAction(player, player.getCurrentEquippedItem(), Keybindings.keyValues[0]);
			else if (player.getCurrentEquippedItem().getItem() instanceof ItemXyniteHammer) ((IKeyBound) player.getCurrentEquippedItem().getItem()).doKeyBindingAction(player, player.getCurrentEquippedItem(), Keybindings.keyValues[0]);
			else if (player.getCurrentEquippedItem().getItem() instanceof ItemGlowExcavator) ((IKeyBound) player.getCurrentEquippedItem().getItem()).doKeyBindingAction(player, player.getCurrentEquippedItem(), Keybindings.keyValues[0]);
		}

		return null;
	}

	public void fromBytes(ByteBuf buf) {
		this.keyPressed = buf.readByte();
	}

	public void toBytes(ByteBuf buf) {
		buf.writeByte(keyPressed);
	}

}
