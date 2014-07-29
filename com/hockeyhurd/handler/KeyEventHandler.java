package com.hockeyhurd.handler;

import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.util.EnumChatFormatting;

import org.lwjgl.input.Keyboard;

import com.hockeyhurd.item.tool.ItemGlowExcavator;
import com.hockeyhurd.item.tool.ItemGlowHammer;
import com.hockeyhurd.mod.ExtraTools;
import com.hockeyhurd.util.ChatHelper;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.InputEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class KeyEventHandler {

	private ChatHelper ch;
	private final EnumChatFormatting color = EnumChatFormatting.GOLD;
	private final boolean debug;
	
	private static final String[] desc = {
		"key.toggle.desc"
	};
	public static final int[] keyValues = {
		Keyboard.KEY_Y
	};
	private final KeyBinding[] keys;

	public KeyEventHandler() {
		keys = new KeyBinding[desc.length];
		for (int i = 0; i < desc.length; i++) {
			keys[i] = new KeyBinding(desc[i], keyValues[i], "key.extratools+.category");
			System.out.println("Registering key bind: " + keys[i]);
			ClientRegistry.registerKeyBinding(keys[i]);
		}
		
		ch = new ChatHelper();
		debug = ExtraTools.ch.debugMode;
	}

	@SubscribeEvent
	public void onKeyInput(InputEvent.KeyInputEvent event) {

		if (!FMLClientHandler.instance().getClient().inGameHasFocus) return;
		else {
			boolean flag = false;
			boolean yKeyFlag = false;
			for (int i = 0; i < keys.length; i++) {
				if (keys[i].isPressed()) {
					if (keys[i] == keys[0]) yKeyFlag = true;
					if (debug) System.out.println("Detected one of the set keybinds has been pressed!");
					flag = true;
					break;
				}
			}
			
			if (!flag) {
				if (debug) System.out.println("Not a keybind from this mod! Returning!");
				return;
			}
			
			EntityPlayer player = FMLClientHandler.instance().getClientPlayerEntity();

			Item item = null;
			if (player.getCurrentEquippedItem() != null && yKeyFlag) {
				item = player.getCurrentEquippedItem().getItem();
				
				boolean toggle = false;
				if (item instanceof ItemGlowHammer) {
					toggle = ((ItemGlowHammer) item).getToggle();
					((ItemGlowHammer) item).setToggle(toggle ? false : true);
					player.addChatComponentMessage(ch.comp("Mode: " + (((ItemGlowHammer) item).getToggle() ? "1x1 area." : "3x3 area."), color));
				}
				
				else if (item instanceof ItemGlowExcavator) {
					toggle = ((ItemGlowExcavator) item).getToggle();
					((ItemGlowExcavator) item).setToggle(toggle ? false : true);
					player.addChatComponentMessage(ch.comp("Mode: " + (((ItemGlowExcavator) item).getToggle() ? "1x1 area." : "3x3 area"), color));
				}
				
				else {
					if (debug) System.out.println("No effect!");
					return;
				}
				
			}
		}

	}
}
