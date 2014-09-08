package com.hockeyhurd.mod;

import static com.hockeyhurd.mod.ExtraTools.*;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;

import com.hockeyhurd.entity.tileentity.TileEntityGlowChest;
import com.hockeyhurd.entity.tileentity.TileEntityGlowFurnace;
import com.hockeyhurd.entity.tileentity.TileEntityGlowPulverizer;
import com.hockeyhurd.gui.GuiHandler;
import com.hockeyhurd.handler.*;
import com.hockeyhurd.util.PulverizeRecipes;

import cpw.mods.fml.common.event.FMLInterModComms;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;

public class CommonProxy {

	public boolean updateFlag = false;
	
	public CommonProxy() {
	}

	public void registerRenderInformation() {
	}

	public void init() {
		registerEventHandlers();
		registerWorldgen();
		registerBlocks();
		registerItems();
		addOreDict();
		addFuelRegister();
		addCraftingRecipes();
		addFurnaceRecipes();
		if (ModsLoadedHelper.te4Loaded) pulverizeRecipes();
		if (ModsLoadedHelper.ic2Loaded) maceratorRecipes();
		registerTileEntities();
		registerGuiHandler();
		registerRegisters();
	}

	protected void registerEventHandlers() {
		MinecraftForge.EVENT_BUS.register(new EventHookContainer());
		PacketHandler.init();
	}

	protected void registerWorldgen() {
		GameRegistry.registerWorldGenerator(worldgenGlowOre, 1);
	}

	protected void registerBlocks() {
		// Ores
		GameRegistry.registerBlock(glowOre, "GlowOre");
		GameRegistry.registerBlock(glowOreNether, "GlowOreNether");
		GameRegistry.registerBlock(fermiteOre, "FermiteOre");
		GameRegistry.registerBlock(tanzaniteOre, "TanzaniteOre");
		
		// Other
		GameRegistry.registerBlock(glowRock, "GlowRock");
		GameRegistry.registerBlock(glowTorch, "GlowTorchOn");
		GameRegistry.registerBlock(glowIngotBlock, "GlowIngotBlock");
		GameRegistry.registerBlock(glowFurnaceOff, "GlowFurnaceOff");
		GameRegistry.registerBlock(glowFurnaceOn, "GlowFurnaceOn");
		GameRegistry.registerBlock(glowPulverizerOff, "GlowPulverizerOff");
		GameRegistry.registerBlock(glowPulverizerOn, "GlowPulverizerOn");
		GameRegistry.registerBlock(glowChest, "GlowChest");
		GameRegistry.registerBlock(extraSmoothStone, "ExtraSmoothStone");
		GameRegistry.registerBlock(stoneBricksDefault, "StoneBricksDefault");
		GameRegistry.registerBlock(stoneBricksWide, "StoneBricksWide");
		GameRegistry.registerBlock(stoneBricksRed, "stoneBricksRed");
		GameRegistry.registerBlock(stoneBricksBlue, "stoneBricksBlue");
		GameRegistry.registerBlock(stoneBricksGreen, "stoneBricksGreen");
		GameRegistry.registerBlock(stoneBricksPurple, "stoneBricksPurple");
		GameRegistry.registerBlock(safeGlass, "SafeGlass");
		GameRegistry.registerBlock(glowPressurePlate, "GlowPressurePlate");
	}

	protected void registerItems() {
		// Metals and dusts.
		GameRegistry.registerItem(glowDust, "Glow Dust");
		GameRegistry.registerItem(fermiteDust, "Fermite Dust");
		GameRegistry.registerItem(tanzaniteDust, "Tanzanite Dust");
		GameRegistry.registerItem(glowIngot, "GlowIngot");
		GameRegistry.registerItem(fermiteIngot, "FermiteIngot");
		GameRegistry.registerItem(tanzaniteIngot, "TanzaniteIngot");
		
		GameRegistry.registerItem(diamondFusedNetherStar, "DiamondFusedNetherStar");
		GameRegistry.registerItem(netherSoulCollector, "NetherSoulCollector");
		GameRegistry.registerItem(fireryNetherStar, "FireryNetherStar");
		GameRegistry.registerItem(diamondSacrifice, "Sacriment to The Nether");
		GameRegistry.registerItem(glowCoal, "GlowCoal");
		GameRegistry.registerItem(hockeyPuck, "HockeyPuck");
		GameRegistry.registerItem(rubber, "Rubber");
		GameRegistry.registerItem(bottler, "Bottler");

		GameRegistry.registerItem(pulverizedIron, "PulverizedIron");
		GameRegistry.registerItem(pulverizedGold, "PulverizedGold");

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

	protected void addOreDict() {
		OreDictionary.registerOre("oreGlow", glowOre);
		OreDictionary.registerOre("oreGlow", glowOreNether);
		OreDictionary.registerOre("dustGlow", glowDust);
		OreDictionary.registerOre("ingotGlow", glowIngot);
		OreDictionary.registerOre("blockGlow", glowIngotBlock);
		OreDictionary.registerOre("dustIron", pulverizedIron);
		OreDictionary.registerOre("dustGold", pulverizedGold);
		OreDictionary.registerOre("oreGlowCoal", glowCoal);
		OreDictionary.registerOre("itemRubber", rubber);
		OreDictionary.registerOre("stone", extraSmoothStone);
		OreDictionary.registerOre("stoneBricks", stoneBricksDefault);
		OreDictionary.registerOre("stoneBricks", stoneBricksWide);
		OreDictionary.registerOre("stoneBricks", stoneBricksRed);
		OreDictionary.registerOre("stoneBricks", stoneBricksBlue);
		OreDictionary.registerOre("stoneBricks", stoneBricksGreen);
		OreDictionary.registerOre("stoneBricks", stoneBricksPurple);
		OreDictionary.registerOre("blockGlass", safeGlass);
	}

	protected void addFuelRegister() {
		GameRegistry.registerFuelHandler(new FuelHandler());
	}

	protected void addCraftingRecipes() {
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
				"wxw", "xzx", "xyx", 'w', "blockRedstone", 'x', "ingotGlow", 'y', Blocks.furnace, 'z', "oreGlowCoal"
		}));

		// Crafting the glow pulverizer
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(glowPulverizerOff, 1), new Object[] {
				"wxw", "xzx", "xyx", 'w', "blockRedstone", 'x', "ingotGlow", 'y', Blocks.furnace, 'z', Items.diamond_pickaxe
		}));

		// Crafting the Glow Chest
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(glowChest, 1), new Object[] {
				"xyx", "yzy", "xyx", 'x', "stone", 'y', "ingotGlow", 'z', Blocks.chest
		}));

		// Crafting the GlowPressurePlate.
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(glowPressurePlate, 1), new Object[] {
				" x ", "xyx", " x ", 'x', "ingotGlow", 'y', Blocks.stone_pressure_plate
		}));

		// Crafting the ExtraSmoothStone
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(extraSmoothStone, 8), new Object[] {
				"xxx", "xyx", "xxx", 'x', "stone", 'y', "dustGlow"
		}));

		// Crafting the stoneBricks
		GameRegistry.addRecipe(new ItemStack(stoneBricksDefault, 8), new Object[] {
				"xxx", "x x", "xxx", 'x', Blocks.stonebrick
		});

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(stoneBricksDefault, 1), new Object[] {
				"xy", 'x', "stoneBricks", 'y', "dyeWhite"
		}));

		// Crafting the stoneBricks_wide
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(stoneBricksWide, 2), new Object[] {
				"xx", 'x', stoneBricksDefault
		}));

		// Crafting the stoneBricks_red
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(stoneBricksRed, 8), new Object[] {
				"xxx", "xyx", "xxx", 'x', "stoneBricks", 'y', "dyeRed"
		}));

		// Crafting the stoneBricks_blue
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(stoneBricksBlue, 8), new Object[] {
				"xxx", "xyx", "xxx", 'x', "stoneBricks", 'y', "dyeBlue"
		}));

		// Crafting the stoneBricks_green
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(stoneBricksGreen, 8), new Object[] {
				"xxx", "xyx", "xxx", 'x', "stoneBricks", 'y', "dyeGreen"
		}));

		// Crafting the stoneBricks_green
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(stoneBricksPurple, 8), new Object[] {
				"xxx", "xyx", "xxx", 'x', "stoneBricks", 'y', "dyePurple"
		}));

		// Crafting the SafeGlass
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(safeGlass, 8), new Object[] {
				"xxx", "xyx", "xxx", 'x', "blockGlass", 'y', "dustGlow"
		}));

		// Nether Star Firery
		GameRegistry.addRecipe(new ItemStack(fireryNetherStar, 1), new Object[] {
				"xyx", "yzy", "xyx", 'x', Blocks.nether_brick, 'y', glowIngot, 'z', !ch.easyRecipes ? Items.nether_star : Items.diamond
		});

		if (ch.altFireStarRecipe) {
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(fireryNetherStar, 1), new Object[] {
					"xxx", "xyx", "xxx", 'x', diamondSacrifice, 'y', "blockGlow"
			}));
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
		/*
		 * GameRegistry.addRecipe(new ItemStack(diamondSacrifice, 1), new Object[] { "xyx", "yzy", "xyx", 'x', Blocks.nether_brick, 'y', Items.diamond, 'z', Items.blaze_rod });
		 */

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(diamondSacrifice, 1), new Object[] {
				"abc", "def", "hij", 'a', Blocks.nether_brick, 'b', "gemEmerald", 'c', Items.magma_cream, 'd', "gemDiamond", 'e', Items.blaze_rod, 'f', "ingotGold", 'h', Items.ender_eye, 'i', "ingotIron", 'j', Items.ghast_tear
		}));

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

	protected void addFurnaceRecipes() {
		// USE: args(use what block/item from id, (get what block/item from id, how much), how much xp should the player be rewarded.
		GameRegistry.addSmelting(glowOre, new ItemStack(glowDust, 1), 100f);
		GameRegistry.addSmelting(pulverizedIron, new ItemStack(Items.iron_ingot, 1), 100f);
		GameRegistry.addSmelting(pulverizedGold, new ItemStack(Items.gold_ingot, 1), 100f);
	}

	protected void pulverizeRecipes() { // Code performing glowOre into 2*glowDust via Thermal Expansion // Pulverizer.
		NBTTagCompound toSend = new NBTTagCompound();
		toSend.setInteger("energy", 1000);
		toSend.setTag("input", new NBTTagCompound());
		toSend.setTag("primaryOutput", new NBTTagCompound());

		ItemStack inputStack = new ItemStack(glowOre, 1);
		inputStack.writeToNBT(toSend.getCompoundTag("input"));

		ItemStack outputStack = new ItemStack(glowDust, 2);
		outputStack.writeToNBT(toSend.getCompoundTag("primaryOutput"));
		FMLInterModComms.sendMessage("ThermalExpansion", "PulverizerRecipe", toSend);

	}
	
	protected void maceratorRecipes() {
		// Recipes.macerator.addRecipe(, null, new ItemStack(glowDust, 2));
	}

	protected void registerTileEntities() {
		GameRegistry.registerTileEntity(TileEntityGlowFurnace.class, "tileEntityGlowFurnace");
		GameRegistry.registerTileEntity(TileEntityGlowPulverizer.class, "tileEntityGlowPulverizer");
		GameRegistry.registerTileEntity(TileEntityGlowChest.class, "tileEntityGlowChest");
	}

	protected void registerGuiHandler() {
		if (guiHandler != null) NetworkRegistry.INSTANCE.registerGuiHandler(instance, guiHandler);
		else {
			guiHandler = new GuiHandler();
			NetworkRegistry.INSTANCE.registerGuiHandler(instance, guiHandler);
		}
	}

	protected void registerRegisters() {
		PulverizeRecipes.init();
	}
	
	public void updateChecker() {
		UpdateHandler uh = new UpdateHandler();
		uh.check();
		this.updateFlag = uh.getUpToDate();
	}

}
