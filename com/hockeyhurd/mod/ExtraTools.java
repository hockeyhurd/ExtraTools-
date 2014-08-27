package com.hockeyhurd.mod;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraftforge.common.util.EnumHelper;

import com.hockeyhurd.block.BlockExtraSmoothStone;
import com.hockeyhurd.block.BlockGlowIngot;
import com.hockeyhurd.block.BlockGlowPressurePlate;
import com.hockeyhurd.block.BlockGlowPressurePlate.Sensitivity;
import com.hockeyhurd.block.BlockGlowRock;
import com.hockeyhurd.block.BlockGlowTorch;
import com.hockeyhurd.block.BlockSafeGlass;
import com.hockeyhurd.block.machines.BlockGlowChest;
import com.hockeyhurd.block.machines.BlockGlowFurnace;
import com.hockeyhurd.block.machines.BlockGlowPulverizer;
import com.hockeyhurd.block.ores.BlockGlowOre;
import com.hockeyhurd.creativetab.MyCreativeTab;
import com.hockeyhurd.gui.GuiHandler;
import com.hockeyhurd.handler.ConfigHandler;
import com.hockeyhurd.handler.GuiIDHandler;
import com.hockeyhurd.handler.ModsLoadedHelper;
import com.hockeyhurd.item.ItemBottler;
import com.hockeyhurd.item.ItemDiamondFusedNetherStar;
import com.hockeyhurd.item.ItemDiamondSacrifice;
import com.hockeyhurd.item.ItemGlowCoal;
import com.hockeyhurd.item.ItemGlowDust;
import com.hockeyhurd.item.ItemGlowIngot;
import com.hockeyhurd.item.ItemHockeyPuck;
import com.hockeyhurd.item.ItemNetherSoulCollector;
import com.hockeyhurd.item.ItemNetherStarFirery;
import com.hockeyhurd.item.ItemRubber;
import com.hockeyhurd.item.armor.ArmorSetGlow;
import com.hockeyhurd.item.pulverized.ItemPulverizedIron;
import com.hockeyhurd.item.tool.ItemDebugger;
import com.hockeyhurd.item.tool.ItemDiamondDetector;
import com.hockeyhurd.item.tool.ItemGlowAxe;
import com.hockeyhurd.item.tool.ItemGlowExcavator;
import com.hockeyhurd.item.tool.ItemGlowHammer;
import com.hockeyhurd.item.tool.ItemGlowHoe;
import com.hockeyhurd.item.tool.ItemGlowPickaxe;
import com.hockeyhurd.item.tool.ItemGlowShovel;
import com.hockeyhurd.item.tool.ItemGlowSword;
import com.hockeyhurd.item.tool.ItemHockeyStick;
import com.hockeyhurd.item.tool.ItemItemReplacer;
import com.hockeyhurd.item.tool.ItemWrench;
import com.hockeyhurd.item.tool.ItemWrenchIC2;
import com.hockeyhurd.util.LogHelper;
import com.hockeyhurd.util.LogicHelper;
import com.hockeyhurd.util.PulverizeRecipes;
import com.hockeyhurd.worldgen.OreGlowWorldgen;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = "ExtraTools+", name = "ExtraTools+", version = "v1.1.24")
public class ExtraTools {

	@SidedProxy(clientSide = "com.hockeyhurd.mod.ClientProxy", serverSide = "com.hockeyhurd.mod.CommonProxy")
	public static CommonProxy proxy;

	// GuiHandler object(s)
	public static GuiHandler guiHandler;

	@Instance("ExtraTools+")
	public static ExtraTools instance;

	public static String assetsDir = "extratools:";

	// Config object(s).
	public static ConfigHandler ch;
	public static LogicHelper lh;
	public static String modID = "ExtraTools+";

	// Blocks
	public static Block glowRock;
	public static Block glowTorch;
	public static Block glowIngotBlock;
	public static Block extraSmoothStone;
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
	
	// Ores pulverized.
	public static Item pulverizedIron;

	// World generation.
	public static OreGlowWorldgen worldgenGlowOre = new OreGlowWorldgen();

	// Items
	public static Item glowDust;
	public static Item glowIngot;
	public static Item diamondFusedNetherStar;
	public static Item netherSoulCollector;
	public static Item fireryNetherStar;
	public static Item diamondSacrifice;
	public static Item glowCoal;
	public static Item hockeyPuck;
	public static Item rubber;
	public static Item bottler;

	// Tool materials.
	public static ToolMaterial toolGlow = EnumHelper.addToolMaterial("GLOW", 3, 2000, 10.0f, 5.0f, 30);
	public static ToolMaterial toolGlowUnbreakable = EnumHelper.addToolMaterial("GLOWUNBREAKING", 3, -1, 10.0f, 5.0f, 30);
	public static ToolMaterial toolHockey = EnumHelper.addToolMaterial("HOCKEY", 3, 500, 10.0f, 2.0f, 30);

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
	public static ArmorMaterial glowArmorMat = EnumHelper.addArmorMaterial("GLOWARMOR", 100, new int[] {
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
		lh = new LogicHelper();
		ch = new ConfigHandler(event);
		ch.handleConfiguration();
		ch.handleWrenchablesConfiguration();
		LogHelper.info("Config loaded successfully!");

		LogHelper.info("Detecting other soft-dependent mods.");
		ModsLoadedHelper.init();
		PulverizeRecipes.init();
		
		for (int i = 0; i < ModsLoadedHelper.staticArray.length; i++) {
			if (ModsLoadedHelper.staticArray[i]) LogHelper.info(ModsLoadedHelper.staticArray[i] + " detected! Wrapping into mod!");
			else LogHelper.info(ModsLoadedHelper.staticArray[i] + " not detected!");
		}
		
		LogHelper.info("Pre-init finished succesfully!");
	}

	@EventHandler
	public void load(FMLInitializationEvent event) {
		LogHelper.info("Init started");
		loadObj();
		
		proxy.init();
		proxy.registerRenderInformation();
		
		LogHelper.info("Init finished successfully!");
	}

	private void loadObj() {
		// Blocks
		glowRock = new BlockGlowRock(Material.glass);
		glowTorch = new BlockGlowTorch();
		glowIngotBlock = new BlockGlowIngot(Material.rock);
		extraSmoothStone = new BlockExtraSmoothStone(Material.rock);
		safeGlass = new BlockSafeGlass(Material.glass, false);
		glowPressurePlate = new BlockGlowPressurePlate("glow_pressure_plate", Material.rock, Sensitivity.everything);

		// Machines/TileEntityBlocks
		glowFurnaceOff = new BlockGlowFurnace(Material.rock, false);
		glowFurnaceOn = new BlockGlowFurnace(Material.rock, true);
		glowPulverizerOn = new BlockGlowPulverizer(Material.rock, true);
		glowPulverizerOff = new BlockGlowPulverizer(Material.rock, false);
		glowChest = new BlockGlowChest(Material.rock);

		// Ores
		glowOre = new BlockGlowOre(Material.rock);
		glowOreNether = new BlockGlowOreNether(Material.rock);
		
		// Ores pulverized.
		pulverizedIron = new ItemPulverizedIron();

		// Items
		glowDust = new ItemGlowDust();
		glowIngot = new ItemGlowIngot();
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
	}

	public ExtraTools() {
	}

}
