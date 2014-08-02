package com.hockeyhurd.mod;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;

import com.hockeyhurd.block.BlockExtraSmoothStone;
import com.hockeyhurd.block.BlockGlowIngot;
import com.hockeyhurd.block.BlockGlowRock;
import com.hockeyhurd.block.BlockGlowTorch;
import com.hockeyhurd.block.machines.BlockGlowFurnace;
import com.hockeyhurd.block.ores.BlockGlowOre;
import com.hockeyhurd.creativetab.MyCreativeTab;
import com.hockeyhurd.entity.tileentity.TileEntityGlowFurnace;
import com.hockeyhurd.gui.GuiHandlerGlowFurnace;
import com.hockeyhurd.handler.ConfigHandler;
import com.hockeyhurd.handler.EventHookContainer;
import com.hockeyhurd.handler.FuelHandler;
import com.hockeyhurd.handler.KeyEventHandler;
import com.hockeyhurd.handler.ModsLoadedHelper;
import com.hockeyhurd.handler.PacketHandler;
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
import com.hockeyhurd.util.Keybindings;
import com.hockeyhurd.worldgen.OreGlowWorldgen;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;

@Mod(modid = "ExtraTools+", name = "ExtraTools+", version = "v1.1.6")
public class ExtraTools {

	@SidedProxy(clientSide = "com.hockeyhurd.mod.ClientProxy", serverSide = "com.hockeyhurd.mod.CommonProxy")
	public static CommonProxy proxy;

	public static GuiHandlerGlowFurnace guiHandler;

	@Instance("ExtraTools+")
	public static ExtraTools instance;

	public static String modPrefix = "extratools:";

	// Config object(s).
	public static ConfigHandler ch;
	public static String modID = "ExtraTools+";

	// Blocks
	public static Block glowRock;
	public static Block glowTorch;
	public static Block glowIngotBlock;
	public static Block extraSmoothStone;

	// Machines
	public static Block glowFurnaceOff;
	public static Block glowFurnaceOn;

	// Gui stuff
	public static final int guiIDGlowFurnace = 0;

	// Ores
	public static Block glowOre;
	public static Block glowOreNether;

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
		ch = new ConfigHandler(event);
		ch.handleConfiguration();
		ch.handleWrenchablesConfiguration();
		
		ModsLoadedHelper.init();
	}

	@EventHandler
	public void load(FMLInitializationEvent event) {
		proxy.registerRenderInformation();

		loadObj();
		init();
	}

	private void loadObj() {
		// Blocks
		glowRock = new BlockGlowRock(Material.glass);
		glowTorch = new BlockGlowTorch();
		glowIngotBlock = new BlockGlowIngot(Material.rock);
		extraSmoothStone = new BlockExtraSmoothStone(Material.rock);

		// Machines
		glowFurnaceOff = new BlockGlowFurnace(Material.rock, false);
		glowFurnaceOn = new BlockGlowFurnace(Material.rock, true);

		// Ores
		glowOre = new BlockGlowOre(Material.rock);
		glowOreNether = new BlockGlowOreNether(Material.rock);

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

	// Handlers all init of blocks, items, etc.
	private void init() {
		registerEventHandlers();
		registerWorldgen();
		registerBlocks();
		registerItems();
		addOreDict();
		addFuelRegister();
		addCraftingRecipes();
		addFurnaceRecipes();
		// if (Loader.isModLoaded("ThermalExpansion")) pulverizeRecipes();
		registerTileEntities();
		registerGuiHandler();
	}

	private void registerEventHandlers() {
		MinecraftForge.EVENT_BUS.register(new EventHookContainer());
		PacketHandler.init();
		if (FMLCommonHandler.instance().getEffectiveSide().isClient()) {
			Keybindings.init();
			FMLCommonHandler.instance().bus().register(new KeyEventHandler());
		}
	}

	private void registerWorldgen() {
		GameRegistry.registerWorldGenerator(worldgenGlowOre, 1);
	}

	private void registerBlocks() {
		GameRegistry.registerBlock(glowRock, "GlowRock");
		GameRegistry.registerBlock(glowOre, "GlowOre");
		GameRegistry.registerBlock(glowOreNether, "GlowOreNether");
		GameRegistry.registerBlock(glowTorch, "GlowTorchOn");
		GameRegistry.registerBlock(glowIngotBlock, "GlowIngotBlock");
		GameRegistry.registerBlock(glowFurnaceOff, "GlowFurnaceOff");
		GameRegistry.registerBlock(glowFurnaceOn, "GlowFurnaceOn");
		GameRegistry.registerBlock(extraSmoothStone, "ExtraSmoothStone");
	}

	private void registerItems() {
		GameRegistry.registerItem(glowIngot, "GlowIngot");
		GameRegistry.registerItem(glowDust, "Glow Dust");
		GameRegistry.registerItem(diamondFusedNetherStar, "DiamondFusedNetherStar");
		GameRegistry.registerItem(netherSoulCollector, "NetherSoulCollector");
		GameRegistry.registerItem(fireryNetherStar, "FireryNetherStar");
		GameRegistry.registerItem(diamondSacrifice, "Sacriment to The Nether");
		GameRegistry.registerItem(glowCoal, "GlowCoal");
		GameRegistry.registerItem(hockeyPuck, "HockeyPuck");
		GameRegistry.registerItem(rubber, "Rubber");
		GameRegistry.registerItem(bottler, "Bottler");

		GameRegistry.registerItem(hockeyStick, "HockeyStick");
		GameRegistry.registerItem(diamondDetector, "DiamondDetector");
		GameRegistry.registerItem(itemReplacer, "ItemReplacer");
		GameRegistry.registerItem(debugger, "ItemDebugger");
		GameRegistry.registerItem(wrench, "GlowWrench");
		
		GameRegistry.registerItem(glowPickaxeUnbreakable, "GlowPickaxeUnbreakable");
		GameRegistry.registerItem(glowHoeUnbreakable, "GlowHoeUnbreakable");
		GameRegistry.registerItem(glowSwordUnbreakable, "GlowSwordUnbreakable");
		GameRegistry.registerItem(glowAxeUnbreakable, "GlowAxeUnbreakable");
		GameRegistry.registerItem(glowShovelUnbreakable, "GlowShovelUnbreakable");
		GameRegistry.registerItem(glowHammerUnbreakable, "GlowHammerUnbreakable");
		GameRegistry.registerItem(glowExcavatorUnbreakable, "GlowExcavatorUnbreakable");

		GameRegistry.registerItem(glowHelmet, "GlowHelmet");
		GameRegistry.registerItem(glowChestplate, "GlowChestplate");
		GameRegistry.registerItem(glowLegging, "GlowLegging");
		GameRegistry.registerItem(glowBoot, "GlowBoot");
	}

	private void addOreDict() {
		OreDictionary.registerOre("oreGlow", glowOre);
		OreDictionary.registerOre("oreGlow", glowOreNether);
		OreDictionary.registerOre("dustGlow", glowDust);
		OreDictionary.registerOre("ingotGlow", glowIngot);
		OreDictionary.registerOre("oreGlowCoal", glowCoal);
		OreDictionary.registerOre("itemRubber", rubber);
		OreDictionary.registerOre("stone", extraSmoothStone);
	}

	private void addFuelRegister() {
		GameRegistry.registerFuelHandler(new FuelHandler());
	}

	private void addCraftingRecipes() {
		// General purpose items.
		final String STICK = "stickWood";

		// Glow rock recipe, similar to that of glowstone.
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(glowRock, 1), "xx", "xx", 'x', "dustGlow"));

		// Glow ingot recipe
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(glowIngot, 1), "xy", 'x', "ingotGold", 'y', glowDust));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(glowIngot, 1), new Object[] {
				"xyy", "yyy", "yyy", 'x', glowDust, 'y', "ingotIron"
		}));
		GameRegistry.addRecipe(new ItemStack(glowIngot, 9), "x", 'x', glowIngotBlock);

		// Crafting the GlowIngotBlock
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(glowIngotBlock, 1), new Object[] {
				"xxx", "xxx", "xxx", 'x', "ingotGlow"
		}));

		// Crafting the glow furnace
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(glowFurnaceOff, 1), new Object[] {
				" x ", "x x", "xyx", 'x', "ingotGlow", 'y', Blocks.furnace
		}));

		// Crafting the ExtraSmoothStone
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(extraSmoothStone, 8), new Object[] {
				"xxx", "xyx", "xxx", 'x', "stone", 'y', "dustGlow"
		}));

		// Nether Star Firery
		GameRegistry.addRecipe(new ItemStack(fireryNetherStar, 1), new Object[] {
				"xyx", "yzy", "xyx", 'x', Blocks.nether_brick, 'y', glowIngot, 'z', !ch.easyRecipes ? Items.nether_star : Items.diamond
		});

		if (ch.altFireStarRecipe) {
			GameRegistry.addRecipe(new ItemStack(fireryNetherStar, 1), new Object[] {
					"xxx", "xyx", "xxx", 'x', diamondSacrifice, 'y', Blocks.redstone_block
			});
		}

		// DiamondNetherStarIngot recipe
		GameRegistry.addRecipe(new ItemStack(diamondFusedNetherStar, 1), new Object[] {
				"xyx", "yzy", "xyx", 'x', Items.diamond, 'y', glowIngot, 'z', fireryNetherStar
		});

		// Crafting the NetherSoulCollector
		GameRegistry.addRecipe(new ItemStack(netherSoulCollector, 1), new Object[] {
				"xyx", "yzy", "xyx", 'x', glowIngot, 'y', Items.gold_ingot, 'z', diamondFusedNetherStar
		});

		// Crafting the 'Black Diamond'
		GameRegistry.addRecipe(new ItemStack(diamondSacrifice, 1), new Object[] {
				"xyx", "yzy", "xyx", 'x', Blocks.nether_brick, 'y', Items.diamond, 'z', Items.blaze_rod
		});

		// Crafting the 'Item Bottler'
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(bottler, 1), new Object[] {
				"xy", 'x', "dustGlow", 'y', Items.glass_bottle
		}));
		
		// Crafting the GlowWrench
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(wrench, 1), new Object[] {
			"x x", "xxx", " x ", 'x', "ingotGlow"
		}));

		// Crafting the ItemReplacer Tool
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(itemReplacer, 1), new Object[] {
				" xy", " zx", "z  ", 'x', "dyeBlue", 'y', fireryNetherStar, 'z', STICK
		}));

		// Crafting the glow coal
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(glowCoal, 1), new Object[] {
				" x ", "xyx", " x ", 'x', glowDust, 'y', "coal"
		}));

		// Crafting the hockey stick
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(hockeyStick, 1), new Object[] {
				" wx", " wx", "yxw", 'w', "itemRubber", 'x', STICK, 'y', Items.string
		}));

		// Crafting the hockey puck.
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(hockeyPuck, 4), new Object[] {
				"xy", 'x', "itemRubber", 'y', "coal"
		}));

		// Crafting the DiamondDetector
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(diamondDetector, 1), new Object[] {
				" x ", "xyx", " x ", 'x', "ingotGlow", 'y', Items.diamond
		}));

		// Craft the pick
		ItemStack pick = new ItemStack(glowPickaxeUnbreakable, 1);
		pick.addEnchantment(Enchantment.efficiency, 5);
		pick.addEnchantment(Enchantment.fortune, 4);

		GameRegistry.addRecipe(new ShapedOreRecipe(pick, new Object[] {
				"yxy", " z ", " z ", 'x', diamondFusedNetherStar, 'y', glowIngot, 'z', Items.blaze_rod
		}));

		// Crafting the sword
		ItemStack SWORD = new ItemStack(glowSwordUnbreakable, 1);
		SWORD.addEnchantment(Enchantment.fireAspect, 1);
		SWORD.addEnchantment(Enchantment.sharpness, 5);
		SWORD.addEnchantment(Enchantment.looting, 4);
		GameRegistry.addRecipe(new ShapedOreRecipe(SWORD, new Object[] {
				" w ", " x ", " z ", 'w', glowIngot, 'x', diamondFusedNetherStar, 'z', Items.blaze_rod
		}));

		// Crafting the axe
		ItemStack AXE = new ItemStack(glowAxeUnbreakable, 1);
		AXE.addEnchantment(Enchantment.efficiency, 5);
		GameRegistry.addRecipe(new ShapedOreRecipe(AXE, new Object[] {
				"wx ", "xy ", " y ", 'w', diamondFusedNetherStar, 'x', glowIngot, 'y', Items.blaze_rod,
		}));

		// Crafting the glowHoe
		ItemStack HOE = new ItemStack(glowHoeUnbreakable, 1);
		GameRegistry.addRecipe(new ShapedOreRecipe(HOE, new Object[] {
				"wx ", "yz ", " z ", 'w', glowIngot, 'x', diamondFusedNetherStar, 'y', Items.diamond, 'z', Items.blaze_rod
		}));

		// Crafting the glow Shovel
		ItemStack SHOVEL = new ItemStack(glowShovelUnbreakable, 1);
		SHOVEL.addEnchantment(Enchantment.efficiency, 5);
		GameRegistry.addRecipe(new ShapedOreRecipe(SHOVEL, new Object[] {
				" x ", " y ", " y ", 'x', diamondFusedNetherStar, 'y', Items.blaze_rod
		}));

		// Crafting the glow hammer
		ItemStack HAMMER = new ItemStack(glowHammerUnbreakable, 1);
		// HAMMER.addEnchantment(Enchantment.efficiency, 5); // TODO: Adjust this!
		HAMMER.addEnchantment(Enchantment.fortune, 4);
		GameRegistry.addRecipe(new ShapedOreRecipe(HAMMER, new Object[] {
				"yxy", "wzw", " z ", 'x', diamondFusedNetherStar, 'y', glowIngot, 'w', Items.diamond, 'z', Items.blaze_rod
		}));

		// Crafting the glow excavator
		ItemStack EXCAVATOR = new ItemStack(glowExcavatorUnbreakable, 1);
		GameRegistry.addRecipe(new ShapedOreRecipe(EXCAVATOR, new Object[] {
				" x ", "yzy", " z ", 'x', diamondFusedNetherStar, 'y', Items.diamond, 'z', Items.blaze_rod
		}));

		// Crafting the glow boots
		ItemStack BOOT = new ItemStack(glowBoot, 1);
		GameRegistry.addRecipe(BOOT, new Object[] {
				"x x", "x x", 'x', glowIngot
		});

		// Crafting the glow leggings
		ItemStack LEGGINGS = new ItemStack(glowLegging, 1);
		GameRegistry.addRecipe(LEGGINGS, new Object[] {
				"xxx", "x x", "x x", 'x', glowIngot
		});

		// Crafting the glow chestplate
		ItemStack CHESTPLATE = new ItemStack(glowChestplate, 1);
		GameRegistry.addRecipe(CHESTPLATE, new Object[] {
				"x x", "xyx", "xxx", 'x', glowIngot, 'y', diamondFusedNetherStar
		});

		// Crafting the glow helmet
		ItemStack HELMET = new ItemStack(glowHelmet, 1);
		GameRegistry.addRecipe(HELMET, new Object[] {
				"xxx", "x x", 'x', glowIngot
		});
	}

	private void addFurnaceRecipes() {
		// USE: args(use what block/item from id, (get what block/item from id, how much), how much xp should the player be rewarded.
		GameRegistry.addSmelting(glowOre, new ItemStack(glowDust, 1), 100f);
	}

	/*
	 * private void pulverizeRecipes() { // Code performing glowOre into 2*glowDust via Thermal Expansion // Pulverizer. NBTTagCompound toSend = new NBTTagCompound(); toSend.setInteger("energy", 1000); toSend.setCompoundTag("input", new NBTTagCompound()); toSend.setCompoundTag("primaryOutput", new
	 * NBTTagCompound());
	 * 
	 * ItemStack inputStack = new ItemStack(glowOre, 1); inputStack.writeToNBT(toSend.getCompoundTag("input"));
	 * 
	 * ItemStack outputStack = new ItemStack(glowDust, 2); outputStack.writeToNBT(toSend.getCompoundTag("primaryOutput")); FMLInterModComms.sendMessage("ThermalExpansion", "PulverizerRecipe", toSend);
	 * 
	 * }
	 */

	private void registerTileEntities() {
		GameRegistry.registerTileEntity(TileEntityGlowFurnace.class, "tileEntityGlowFurnace");
	}

	private void registerGuiHandler() {
		if (guiHandler != null) NetworkRegistry.INSTANCE.registerGuiHandler(this, guiHandler);
		else {
			guiHandler = new GuiHandlerGlowFurnace();
			NetworkRegistry.INSTANCE.registerGuiHandler(this, guiHandler);
		}
	}

}
