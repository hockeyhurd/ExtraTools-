package com.hockeyhurd.util;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.event.ClickEvent;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;

public class ChatHelper {

	private EntityPlayer player;
	private EnumChatFormatting color;
	
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
	
	public IChatComponent comp(String message) {
		return comp(message, this.color);
	}
	
	public IChatComponent comp(String message, EnumChatFormatting color) {
		setColor(color);
		ChatComponentTranslation comp = new ChatComponentTranslation(message, new Object[0]);
		comp.getChatStyle().setColor(color);
		return comp;
	} 
	
	public IChatComponent compURL(String msg, String url, boolean maskUrl) {
		return compURL(msg, url, this.color, maskUrl);
	}
	
	public IChatComponent compURL(String msg, String url, EnumChatFormatting color, boolean maskUrl) {
		setColor(color);
		ChatComponentTranslation comp = new ChatComponentTranslation(msg + " " + (maskUrl ? "<here>" : url), new Object[0]);
		comp.getChatStyle().setChatClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, url));
		comp.getChatStyle().setColor(color);
		return comp;
	}
	
}
