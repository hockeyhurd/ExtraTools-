package com.hockeyhurd.mod;

import java.util.Iterator;
import java.util.Map.Entry;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraftforge.common.util.EnumHelper;

import com.hockeyhurd.block.*;
import com.hockeyhurd.block.BlockGlowPressurePlate.Sensitivity;
import com.hockeyhurd.block.machines.BlockGlowChest;
import com.hockeyhurd.block.machines.BlockGlowFurnace;
import com.hockeyhurd.block.machines.BlockGlowPulverizer;
import com.hockeyhurd.block.ores.BlockFermiteOre;
import com.hockeyhurd.block.ores.BlockGlowOre;
import com.hockeyhurd.block.ores.BlockTanzaniteOre;
import com.hockeyhurd.creativetab.MyCreativeTab;
import com.hockeyhurd.gui.GuiHandler;
import com.hockeyhurd.handler.ConfigHandler;
import com.hockeyhurd.handler.GuiIDHandler;
import com.hockeyhurd.handler.ModsLoadedHelper;
import com.hockeyhurd.item.*;
import com.hockeyhurd.item.armor.ArmorSetGlow;
import com.hockeyhurd.item.metalic.*;
import com.hockeyhurd.item.pulverized.ItemPulverizedGold;
import com.hockeyhurd.item.pulverized.ItemPulverizedIron;
import com.hockeyhurd.item.tool.*;
import com.hockeyhurd.util.LogHelper;
import com.hockeyhurd.util.Reference;
import com.hockeyhurd.util.math.TimeLapse;
import com.hockeyhurd.worldgen.OreGlowWorldgen;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = Reference.MOD_NAME, name = Reference.MOD_NAME, version = Reference.VERSION)
public class ExtraTools {

	@SidedProxy(clientSide = "com.hockeyhurd.mod.ClientProxy", serverSide = "com.hockeyhurd.mod.CommonProxy")
	public static CommonProxy proxy;

	// GuiHandler object(s)
	public static GuiHandler guiHandler;

	@Instance(Reference.MOD_NAME)
	public static ExtraTools instance;

	public static final String assetsDir = "extratools:";
	
	// Config object(s).
	public static ConfigHandler ch;
	public static final String modID = Reference.MOD_NAME;

	// Blocks
	public static Block glowRock;
	public static Block glowTorch;
	public static Block glowIngotBlock;
	public static Block extraSmoothStone;
	public static Block stoneBricksDefault;
	public static Block stoneBricksWide;
	public static Block stoneBricksRed;
	public static Block stoneBricksBlue;
	public static Block stoneBricksGreen;
	public static Block stoneBricksPurple;
	public static Block safeGlass;
	public static Block glowPressurePlate;

	// Machines/TileEntityBlocks
	public static Block glowFurnaceOff;
	public static Block glowFurnaceOn;
	public static Block glowPulverizerOn;
	public static Block glowPulverizerOff;
	public static Block glowChest;

	// Gui stuff
	public static final int guiIDGlowFurnace = GuiIDHandler.getNextAvailableID();
	public static final int guiIDGlowChest = GuiIDHandler.getNextAvailableID();
	public static final int guiIDGlowPulverizer = GuiIDHandler.getNextAvailableID();

	// Ores
	public static Block glowOre;
	public static Block glowOreNether;
	public static Block fermiteOre;
	public static Block tanzaniteOre;
	public static Block xyniteOre;

	// Ores pulverized.
	public static Item pulverizedIron;
	public static Item pulverizedGold;

	// World generation.
	public static OreGlowWorldgen worldgenGlowOre;
	public static OreXyniteWorldgen worldgenXyniteOre;

	// Metals and dusts.
	public static Item glowDust;
	public static Item fermiteDust;
	public static Item tanzaniteDust;
	public static Item xyniteDust;
	public static Item glowIngot;
	public static Item fermiteIngot;
	public static Item tanzaniteIngot;
	public static Item xyniteIngot;
	
	// Items
	public static Item diamondFusedNetherStar;
	public static Item netherSoulCollector;
	public static Item fireryNetherStar;
	public static Item diamondSacrifice;
	public static Item glowCoal;
	public static Item hockeyPuck;
	public static Item rubber;
	public static Item bottler;

	// Tool materials.
	public static final ToolMaterial toolGlow = EnumHelper.addToolMaterial("GLOW", 3, 2000, 10.0f, 5.0f, 30);
	public static final ToolMaterial toolGlowUnbreakable = EnumHelper.addToolMaterial("GLOWUNBREAKING", 3, -1, 10.0f, 5.0f, 30);
	public static final ToolMaterial toolHockey = EnumHelper.addToolMaterial("HOCKEY", 3, 500, 10.0f, 2.0f, 30);

	// Tool sets
	public static Item glowPickaxeUnbreakable;
	public static Item glowHoeUnbreakable;
	public static Item glowSwordUnbreakable;
	public static Item glowAxeUnbreakable;
	public static Item glowShovelUnbreakable;
	public static Item glowHammerUnbreakable;
	public static Item glowExcavatorUnbreakable;
	public static Item hockeyStick;
	public static Item diamondDetector;
	public static Item itemReplacer;
	public static Item wrench;
	public static Item debugger;

	// Armor materials.
	public static final ArmorMaterial glowArmorMat = EnumHelper.addArmorMaterial("GLOWARMOR", 100, new int[] {
			3, 8, 6, 3
	}, 25);

	// Armor sets.
	public static Item glowHelmet;
	public static Item glowChestplate;
	public static Item glowLegging;
	public static Item glowBoot;

	// Creative Tabs
	public static CreativeTabs myCreativeTab = new MyCreativeTab(CreativeTabs.getNextID(), "ExtraTools+");

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		LogHelper.info("Pre-init started, looking for config info!");

		TimeLapse tl = new TimeLapse();

		ch = new ConfigHandler(event);
		ch.handleConfiguration();
		ch.handleWrenchablesConfiguration();
		LogHelper.info("Config loaded successfully!");

		LogHelper.info("Detecting other soft-dependent mods.");
		ModsLoadedHelper.init();

		Iterator iter = ModsLoadedHelper.getEntries().iterator();
		do {
			Entry<String, Boolean> current = (Entry<String, Boolean>) iter.next();
			if (current.getValue()) LogHelper.info(current.getKey() + " detected! Wrapping into mod!");
			else LogHelper.warn(current.getKey() + " not detected!");
		}
		while (iter.hasNext());

		LogHelper.info("Pre-init finished succesfully after", tl.getEffectiveTimeSince(), "ms!");
	}

	@EventHandler
	public void init(FMLInitializationEvent event) {
		LogHelper.info("Init started");

		TimeLapse tl = new TimeLapse();

		loadObj();

		proxy.init();
		proxy.registerRenderInformation();

		LogHelper.info("Init finished successfully after", tl.getEffectiveTimeSince(), "ms!");
	}

	private void loadObj() {
		// Blocks
		glowRock = new BlockGlowRock(Material.glass);
		glowTorch = new BlockGlowTorch();
		glowIngotBlock = new BlockGlowIngot(Material.rock);
		extraSmoothStone = new BlockExtraSmoothStone(Material.rock);
		stoneBricksDefault = new BlockStoneBricksDefault(Material.rock);
		stoneBricksWide = new BlockStoneBricksWide(Material.rock);
		stoneBricksRed = new BlockStoneBricksRed(Material.rock);
		stoneBricksBlue = new BlockStoneBricksBlue(Material.rock);
		stoneBricksGreen = new BlockStoneBricksGreen(Material.rock);
		stoneBricksPurple = new BlockStoneBricksPurple(Material.rock);
		safeGlass = new BlockSafeGlass(Material.glass, false);
		glowPressurePlate = new BlockGlowPressurePlate("glow_pressure_plate", Material.rock, Sensitivity.players);

		// Machines/TileEntityBlocks
		glowFurnaceOff = new BlockGlowFurnace(Material.rock, false);
		glowFurnaceOn = new BlockGlowFurnace(Material.rock, true);
		glowPulverizerOff = new BlockGlowPulverizer(Material.rock, false);
		glowPulverizerOn = new BlockGlowPulverizer(Material.rock, true);
		glowChest = new BlockGlowChest(Material.rock);

		// Ores
		glowOre = new BlockGlowOre(Material.rock, "GlowOre");
		glowOreNether = new BlockGlowOreNether(Material.rock, "GlowOreNether");
		fermiteOre = new BlockFermiteOre(Material.rock, "FermiteOre");
		tanzaniteOre = new BlockTanzaniteOre(Material.rock, "TanzaniteOre");
		xyniteOre = new BlockXyniteOre(Material.rock, "XyniteOre");

		// Ores pulverized.
		pulverizedIron = new ItemPulverizedIron();
		pulverizedGold = new ItemPulverizedGold();

		// Metals and Dusts.
		glowDust = new ItemGlowDust("GlowDust");
		fermiteDust = new ItemFermiteDust("FermiteDust");
		tanzaniteDust = new ItemTanzaniteDust("TanzaniteDust");
		xyniteDust = new ItemXyniteDust("XyniteDust");
		glowIngot = new ItemGlowIngot("GlowIngot");
		fermiteIngot = new ItemFermiteIngot("FermiteIngot");
		tanzaniteIngot = new ItemTanzaniteIngot("TanzaniteIngot");
		xyniteIngot = new ItemXyniteIngot("XyniteIngot");
		
		// Items
		diamondFusedNetherStar = new ItemDiamondFusedNetherStar();
		netherSoulCollector = new ItemNetherSoulCollector();
		fireryNetherStar = new ItemNetherStarFirery();
		diamondSacrifice = new ItemDiamondSacrifice();
		glowCoal = new ItemGlowCoal();
		rubber = new ItemRubber();
		hockeyPuck = new ItemHockeyPuck();
		bottler = new ItemBottler();
		debugger = new ItemDebugger();

		// Tool sets
		glowPickaxeUnbreakable = new ItemGlowPickaxe(toolGlowUnbreakable);
		glowHoeUnbreakable = new ItemGlowHoe(toolGlowUnbreakable);
		glowSwordUnbreakable = new ItemGlowSword(toolGlowUnbreakable);
		glowAxeUnbreakable = new ItemGlowAxe(toolGlowUnbreakable);
		glowShovelUnbreakable = new ItemGlowShovel(toolGlowUnbreakable);
		glowHammerUnbreakable = new ItemGlowHammer(toolGlowUnbreakable);
		glowExcavatorUnbreakable = new ItemGlowExcavator(toolGlowUnbreakable);
		hockeyStick = new ItemHockeyStick(toolHockey);
		diamondDetector = new ItemDiamondDetector(Blocks.diamond_ore);
		itemReplacer = new ItemItemReplacer();
		if (ModsLoadedHelper.ic2Loaded) wrench = new ItemWrenchIC2();
		else wrench = new ItemWrench();

		// Armor sets.
		glowHelmet = new ArmorSetGlow(glowArmorMat, 0, 0, "Glow", 0).setUnlocalizedName("GlowHelm");
		glowChestplate = new ArmorSetGlow(glowArmorMat, 0, 1, "Glow", 1).setUnlocalizedName("GlowChestplate");
		glowLegging = new ArmorSetGlow(glowArmorMat, 0, 2, "Glow", 2).setUnlocalizedName("GlowLeggings");
		glowBoot = new ArmorSetGlow(glowArmorMat, 0, 3, "Glow", 3).setUnlocalizedName("GlowBoots");

		worldgenGlowOre = new OreGlowWorldgen();
		worldgenXyniteOre = new OreXyniteWorldgen(xyniteOre, null, 5, -1, 4, 7, 12, 24);
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		LogHelper.info("Post-Init started");
		TimeLapse tl = new TimeLapse();

		proxy.updateChecker();
		if (!proxy.updateFlag) LogHelper.warn("Found an update!");
		else LogHelper.info("Everything is up to date!");
		
		LogHelper.info("Post-Init finished successfully after", tl.getEffectiveTimeSince(), "ms!");
	}

	public ExtraTools() {
	}

}
