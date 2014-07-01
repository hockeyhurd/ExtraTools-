package com.hockeyhurd.util;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatMessageComponent;
import net.minecraft.util.EnumChatFormatting;

public class ChatHelper {

	private EntityPlayer player;
	private EnumChatFormatting color;
	
	// Constructor temporarily set to deprecated until player.sendChatMessage is implemented in this class.
	@Deprecated
	public ChatHelper(EntityPlayer player) {
		this.player = player;
		this.color = EnumChatFormatting.WHITE;
	}
	
	public ChatHelper() {
		this.color = EnumChatFormatting.WHITE;
	}
	
	public void setColor(EnumChatFormatting color) {
		this.color = color;
	}
	
	private EnumChatFormatting getColor() {
		return this.color;
	}
	
	public ChatMessageComponent comp(String message) {
		return comp(message, this.color);
	}
	
	public ChatMessageComponent comp(String message, EnumChatFormatting color) {
		setColor(color);
		ChatMessageComponent comp = new ChatMessageComponent();
		comp.addText(message);
		comp.setColor(color);
		return comp;
	}
	
}
