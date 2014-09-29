package com.hockeyhurd.handler;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;

import com.hockeyhurd.extratools.ExtraTools;
import com.hockeyhurd.item.tool.ItemGlowExcavator;
import com.hockeyhurd.item.tool.ItemGlowHammer;
import com.hockeyhurd.item.tool.ItemXyniteHammer;
import com.hockeyhurd.util.Keybindings;
import com.hockeyhurd.util.LogHelper;
import com.hockeyhurd.util.interfaces.IKeyBound;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.InputEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class KeyEventHandler {

	private final boolean debug = ExtraTools.ch.debugMode;;

	@SubscribeEvent
	public void onKeyInput(InputEvent.KeyInputEvent event) {

		if (!FMLClientHandler.instance().getClient().inGameHasFocus) return;
		else {
			boolean flag = false;
			boolean yKeyFlag = false;
			for (int i = 0; i < Keybindings.keys.length; i++) {
				if (Keybindings.keys[i].isPressed()) {
					if (Keybindings.keys[i] == Keybindings.keys[0]) yKeyFlag = true;
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

				if (item instanceof ItemGlowHammer) {
					if (player.worldObj.isRemote) PacketHandler.instance.sendToServer(new MessageTogglePressed());
					else ((IKeyBound) item).doKeyBindingAction(player, player.getCurrentEquippedItem(), Keybindings.keyValues[0]);
				}
				
				else if (item instanceof ItemXyniteHammer) {
					if (player.worldObj.isRemote) PacketHandler.instance.sendToServer(new MessageTogglePressed());
					else ((IKeyBound) item).doKeyBindingAction(player, player.getCurrentEquippedItem(), Keybindings.keyValues[0]);
				}

				else if (item instanceof ItemGlowExcavator) {
					if (player.worldObj.isRemote) PacketHandler.instance.sendToServer(new MessageTogglePressed());
					else ((IKeyBound) item).doKeyBindingAction(player, player.getCurrentEquippedItem(), Keybindings.keyValues[0]);
				}

				else {
					if (debug) LogHelper.info("No effect!");
					return;
				}

			}
		}

	}

}
