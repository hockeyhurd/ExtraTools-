/*
 * This class and all corresponding classes:
 * Special thanks to Pahimar for reference code made 
 * possible in his EE3 mod on his github
 * @https://github.com/pahimar/Equivalent-Exchange-3
 */

package com.hockeyhurd.handler;

import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;

public class PacketHandler {

	public static final SimpleNetworkWrapper instance = NetworkRegistry.INSTANCE.newSimpleChannel("ExtraTools+");
	
	public static void init() {
		instance.registerMessage(MessageTogglePressed.class, MessageTogglePressed.class, 0, Side.SERVER);
	}
	
}
