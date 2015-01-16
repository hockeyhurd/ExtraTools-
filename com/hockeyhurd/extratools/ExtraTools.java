package com.hockeyhurd.extratools;

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

import com.hockeyhurd.block.BlockExtraSmoothStone;
import com.hockeyhurd.block.BlockGlowIngot;
import com.hockeyhurd.block.BlockGlowPressurePlate;
import com.hockeyhurd.block.BlockGlowPressurePlate.Sensitivity;
import com.hockeyhurd.block.BlockGlowRock;
import com.hockeyhurd.block.BlockGlowTorch;
import com.hockeyhurd.block.BlockSafeGlass;
import com.hockeyhurd.block.BlockStairsMaker;
import com.hockeyhurd.block.BlockStoneBricksBlue;
import com.hockeyhurd.block.BlockStoneBricksDefault;
import com.hockeyhurd.block.BlockStoneBricksGreen;
import com.hockeyhurd.block.BlockStoneBricksPurple;
import com.hockeyhurd.block.BlockStoneBricksRed;
import com.hockeyhurd.block.BlockStoneBricksWide;
import com.hockeyhurd.block.machines.BlockGlowChest;
import com.hockeyhurd.block.machines.BlockGlowFurnace;
import com.hockeyhurd.block.machines.BlockGlowPulverizer;
import com.hockeyhurd.block.machines.BlockTickTorch;
import com.hockeyhurd.block.ores.BlockFermiteOre;
import com.hockeyhurd.block.ores.BlockGlowOre;
import com.hockeyhurd.block.ores.BlockGlowOreNether;
import com.hockeyhurd.block.ores.BlockTanzaniteOre;
import com.hockeyhurd.block.ores.BlockXyniteOre;
import com.hockeyhurd.creativetab.MyCreativeTab;
import com.hockeyhurd.gui.GuiHandler;
import com.hockeyhurd.handler.ConfigHandler;
import com.hockeyhurd.handler.GuiIDHandler;
import com.hockeyhurd.handler.ModsLoadedHelper;
import com.hockeyhurd.item.ItemBottler;
import com.hockeyhurd.item.ItemDiamondFusedNetherStar;
import com.hockeyhurd.item.ItemDiamondSacrifice;
import com.hockeyhurd.item.ItemGlowCoal;
import com.hockeyhurd.item.ItemHockeyPuck;
import com.hockeyhurd.item.ItemNetherSoulCollector;
import com.hockeyhurd.item.ItemNetherStarFirery;
import com.hockeyhurd.item.ItemRubber;
import com.hockeyhurd.item.armor.ArmorSetGlow;
import com.hockeyhurd.item.armor.ArmorSetXynite;
import com.hockeyhurd.item.metalic.ItemFermiteDust;
import com.hockeyhurd.item.metalic.ItemFermiteIngot;
import com.hockeyhurd.item.metalic.ItemGlowDust;
import com.hockeyhurd.item.metalic.ItemGlowIngot;
import com.hockeyhurd.item.metalic.ItemTanzaniteDust;
import com.hockeyhurd.item.metalic.ItemTanzaniteIngot;
import com.hockeyhurd.item.metalic.ItemXyniteDust;
import com.hockeyhurd.item.metalic.ItemXyniteIngot;
import com.hockeyhurd.item.pulverized.ItemPulverizedGold;
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
import com.hockeyhurd.item.tool.ItemXyniteAxe;
import com.hockeyhurd.item.tool.ItemXyniteHammer;
import com.hockeyhurd.item.tool.ItemXyniteHoe;
import com.hockeyhurd.item.tool.ItemXynitePickaxe;
import com.hockeyhurd.item.tool.ItemXyniteShovel;
import com.hockeyhurd.item.tool.ItemXyniteSword;
import com.hockeyhurd.util.LogHelper;
import com.hockeyhurd.util.Reference;
import com.hockeyhurd.util.math.TimeLapse;
import com.hockeyhurd.worldgen.OreFermiteWorldgen;
import com.hockeyhurd.worldgen.OreGlowWorldgen;
import com.hockeyhurd.worldgen.OreTanzaniteWorldgen;
import com.hockeyhurd.worldgen.OreXyniteWorldgen;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = Reference.MOD_NAME, name = Reference.MOD_NAME, version = Reference.VERSION, dependencies = "required-after:HCoreLib")
public class ExtraTools {

	@SidedProxy(clientSide = "com.hockeyhurd.extratools.ClientProxy", serverSide = "com.hockeyhurd.extratools.CommonProxy")
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
	public static Block stoneBricksDefault, stoneBricksStairsDefault;
	public static Block stoneBricksWide, stoneBricksStairsWide;
	public static Block stoneBricksRed, stoneBricksStairsRed;
	public static Block stoneBricksBlue,stoneBricksStairsBlue ;
	public static Block stoneBricksGreen, stoneBricksStairsGreen;
	public static Block stoneBricksPurple, stoneBricksStairsPurple;
	public static Block safeGlass;
	public static Block glowPressurePlate;

	// Machines/TileEntityBlocks
	public static Block glowFurnaceOff;
	public static Block glowFurnaceOn;
	public static Block glowPulverizerOn;
	public static Block glowPulverizerOff;
	public static Block glowChest;
	public static Block tickTorch;

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
	public static OreFermiteWorldgen worldgenFermiteOre;
	public static OreTanzaniteWorldgen worldgenTanzaniteOre;

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
	public static final ToolMaterial toolGlowUnbreakable = EnumHelper.addToolMaterial("GLOWUNBREAKING", 3, -1, 10.0f, 5.0f, 30);
	public static final ToolMaterial toolXynite = EnumHelper.addToolMaterial("XYNITE", 3, 2000, 10.0f, 5.0f, 30);
	public static final ToolMaterial toolHockey = EnumHelper.addToolMaterial("HOCKEY", 3, 500, 10.0f, 2.0f, 30);

	// Tool sets
	public static Item glowPickaxeUnbreakable;
	public static Item glowHoeUnbreakable;
	public static Item glowSwordUnbreakable;
	public static Item glowAxeUnbreakable;
	public static Item glowShovelUnbreakable;
	public static Item glowHammerUnbreakable;
	public static Item glowExcavatorUnbreakable;
	
	public static Item xynitePickaxe;
	public static Item xyniteHoe;
	public static Item xyniteSword;
	public static Item xyniteAxe;
	public static Item xyniteShovel;
	public static Item xyniteHammer;
	
	public static Item hockeyStick;
	public static Item diamondDetector;
	public static Item itemReplacer;
	public static Item wrench;
	public static Item debugger;

	// Armor materials.
	public static final ArmorMaterial glowArmorMat = EnumHelper.addArmorMaterial("GLOWARMOR", 100, new int[] {
			3, 8, 6, 3
	}, 25);
	
	public static final ArmorMaterial xyniteArmorMat = EnumHelper.addArmorMaterial("XYNITEARMOR", 80, new int[] {
			3, 8, 6, 3
	}, 20);

	// Armor sets.
	public static Item glowHelmet, glowChestplate, glowLegging, glowBoot;
	public static Item xyniteHelmet, xyniteChestplate, xyniteLegging, xyniteBoot;
	
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
		stoneBricksStairsDefault = new BlockStairsMaker(stoneBricksDefault, 0);
		stoneBricksWide = new BlockStoneBricksWide(Material.rock);
		stoneBricksStairsWide = new BlockStairsMaker(stoneBricksWide, 0);
		stoneBricksRed = new BlockStoneBricksRed(Material.rock);
		stoneBricksStairsRed = new BlockStairsMaker(stoneBricksRed, 0);
		stoneBricksBlue = new BlockStoneBricksBlue(Material.rock);
		stoneBricksStairsBlue = new BlockStairsMaker(stoneBricksBlue, 0);
		stoneBricksGreen = new BlockStoneBricksGreen(Material.rock);
		stoneBricksStairsGreen = new BlockStairsMaker(stoneBricksGreen, 0);
		stoneBricksPurple = new BlockStoneBricksPurple(Material.rock);
		stoneBricksStairsPurple = new BlockStairsMaker(stoneBricksPurple, 0);
		safeGlass = new BlockSafeGlass(Material.glass, false);
		glowPressurePlate = new BlockGlowPressurePlate("glow_pressure_plate", Material.rock, Sensitivity.players);

		// Machines/TileEntityBlocks
		glowFurnaceOff = new BlockGlowFurnace(Material.rock, false);
		glowFurnaceOn = new BlockGlowFurnace(Material.rock, true);
		glowPulverizerOff = new BlockGlowPulverizer(Material.rock, false);
		glowPulverizerOn = new BlockGlowPulverizer(Material.rock, true);
		glowChest = new BlockGlowChest(Material.rock);
		tickTorch = new BlockTickTorch();

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
		
		xynitePickaxe = new ItemXynitePickaxe(toolXynite);
		xyniteHoe= new ItemXyniteHoe(toolXynite);
		xyniteSword = new ItemXyniteSword(toolXynite);
		xyniteAxe = new ItemXyniteAxe(toolXynite);
		xyniteShovel = new ItemXyniteShovel(toolXynite);
		xyniteHammer = new ItemXyniteHammer(toolXynite);
		
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
		xyniteHelmet = new ArmorSetXynite(xyniteArmorMat, 0, 0, "Xynite", 0).setUnlocalizedName("XyniteHelm");
		xyniteChestplate = new ArmorSetXynite(xyniteArmorMat, 0, 1, "Xynite", 1).setUnlocalizedName("XyniteChestplate");
		xyniteLegging = new ArmorSetXynite(xyniteArmorMat, 0, 2, "Xynite", 2).setUnlocalizedName("XyniteLeggings");
		xyniteBoot = new ArmorSetXynite(xyniteArmorMat, 0, 3, "Xynite", 3).setUnlocalizedName("XyniteBoots");

		worldgenGlowOre = new OreGlowWorldgen();
		worldgenXyniteOre = new OreXyniteWorldgen(xyniteOre, null, 5, -1, 4, 7, 12, 24);
		worldgenFermiteOre = new OreFermiteWorldgen(fermiteOre, null, 7, -1, 4, 8, 24, 40);
		worldgenTanzaniteOre = new OreTanzaniteWorldgen(tanzaniteOre, null, 9, -1, 5, 9, 32, 56);
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
