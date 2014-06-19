package com.hockeyhurd.main;

import java.util.logging.Level;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.EnumArmorMaterial;
import net.minecraft.item.EnumToolMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.launchwrapper.LogWrapper;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.EnumHelper;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;

import com.hockeyhurd.block.BlockGlowRock;
import com.hockeyhurd.block.BlockGlowTorch;
import com.hockeyhurd.block.ores.BlockGlowOre;
import com.hockeyhurd.creativetab.MyCreativeTab;
import com.hockeyhurd.handler.DefaultIDHandler;
import com.hockeyhurd.item.ItemDiamondFusedNetherStar;
import com.hockeyhurd.item.ItemGlowDust;
import com.hockeyhurd.item.ItemGlowIngot;
import com.hockeyhurd.item.ItemNetherSoulCollector;
import com.hockeyhurd.item.armor.ArmorSetGlow;
import com.hockeyhurd.item.tool.GlowShovelUnbreakble;
import com.hockeyhurd.item.tool.ItemGlowAxe;
import com.hockeyhurd.item.tool.ItemGlowHoe;
import com.hockeyhurd.item.tool.ItemGlowPickaxe;
import com.hockeyhurd.item.tool.ItemGlowSword;
import com.hockeyhurd.worldgen.OreGlowWorldgen;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLInterModComms;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;

@Mod(modid = "ExtraTools+", name = "ExtraTools+", version = "v0.1")
@NetworkMod(clientSideRequired = true, serverSideRequired = false)
public class ExtraTools {

	@SidedProxy(clientSide = "com.hockeyhurd.client.ClientProxy", serverSide = "com.hockeyhurd.main.CommonProxy")
	public static CommonProxy proxy;

	@Instance("ExtraTools+")
	public static ExtraTools instance;

	public static String modPrefix = "extratools:";

	// Blocks
	public static Block glowRock = new BlockGlowRock(DefaultIDHandler.getNextAvailableID(), Material.glass);
	public static Block glowTorch = new BlockGlowTorch(DefaultIDHandler.getNextAvailableID());

	// Ores
	public static Block glowOre = new BlockGlowOre(DefaultIDHandler.getNextAvailableID(), Material.rock);

	// World generation.
	public static OreGlowWorldgen worldgenGlowOre = new OreGlowWorldgen();

	// Items
	public static Item glowDust = new ItemGlowDust(DefaultIDHandler.getNextAvailableID());
	public static Item glowIngot = new ItemGlowIngot(DefaultIDHandler.getNextAvailableID());
	public static Item diamondFusedNetherStar = new ItemDiamondFusedNetherStar(DefaultIDHandler.getNextAvailableID());
	public static Item netherSoulCollector = new ItemNetherSoulCollector(DefaultIDHandler.getNextAvailableID(), false);

	// Tool materials.
	public static EnumToolMaterial toolGlow = EnumHelper.addToolMaterial("GLOW", 3, 2000, 10.0f, 5.0f, 30);
	public static EnumToolMaterial toolGlowUnbreakable = EnumHelper.addToolMaterial("GLOWUNBREAKING", 3, -1, 10.0f, 5.0f, 30);

	// Tool sets
	public static Item glowPickaxeUnbreakable = new ItemGlowPickaxe(DefaultIDHandler.getNextAvailableID(), toolGlowUnbreakable);
	public static Item glowHoeUnbreakable = new ItemGlowHoe(DefaultIDHandler.getNextAvailableID(), toolGlowUnbreakable);
	public static Item glowSwordUnbreakable = new ItemGlowSword(DefaultIDHandler.getNextAvailableID(), toolGlowUnbreakable);
	public static Item glowAxeUnbreakable = new ItemGlowAxe(DefaultIDHandler.getNextAvailableID(), toolGlowUnbreakable);
	public static Item glowShovelUnbreakable = new GlowShovelUnbreakble(DefaultIDHandler.getNextAvailableID(), toolGlowUnbreakable);

	// Armor materials.
	public static EnumArmorMaterial glowArmorMat = EnumHelper.addArmorMaterial("GLOWARMOR", 100, new int[] {
			3, 8, 6, 3
	}, 25);

	// Armor sets.
	public static Item glowHelmet = new ArmorSetGlow(DefaultIDHandler.getNextAvailableID(), glowArmorMat, 0, 0, "Glow").setUnlocalizedName("GlowHelm");
	public static Item glowChestplate = new ArmorSetGlow(DefaultIDHandler.getNextAvailableID(), glowArmorMat, 0, 1, "Glow").setUnlocalizedName("GlowChestplate");
	public static Item glowLegging = new ArmorSetGlow(DefaultIDHandler.getNextAvailableID(), glowArmorMat, 0, 2, "Glow").setUnlocalizedName("GlowLeggings");
	public static Item glowBoot = new ArmorSetGlow(DefaultIDHandler.getNextAvailableID(), glowArmorMat, 0, 3, "Glow").setUnlocalizedName("GlowBoots");

	// Creative Tabs
	public static CreativeTabs myCreativeTab = new MyCreativeTab(CreativeTabs.getNextID(), "ExtraTools+");

	@EventHandler
	public void load(FMLInitializationEvent event) {
		proxy.registerRenderInformation();
	}

	public ExtraTools() {
		init();

		LogWrapper.log(Level.INFO, "ExtraTools+ loaded succesfully!");
	}

	// Handlers all init of blocks, items, etc.
	private void init() {

		registerEventHandlers();
		registerWorldgen();
		registerBlocks();
		addOreDict();
		addLocalizedNames();
		addCraftingRecipes();
		addFurnaceRecipes();
		pulverizeRecipes();
		registerTileEntities();
	}

	private void registerEventHandlers() {
		MinecraftForge.EVENT_BUS.register(new EventHookContainer());
	}

	private void registerWorldgen() {
		GameRegistry.registerWorldGenerator(worldgenGlowOre);
	}

	private void registerBlocks() {
		GameRegistry.registerBlock(glowRock, "GlowRock");
		GameRegistry.registerBlock(glowOre, "GlowOre");
		GameRegistry.registerBlock(glowTorch, "GlowTorchOn");
	}

	private void addOreDict() {
		OreDictionary.registerOre("oreGlow", glowOre);
		OreDictionary.registerOre("dustGlow", glowDust);
		OreDictionary.registerOre("ingotGlow", glowIngot);
	}

	private void addLocalizedNames() {
		// Blocks
		LanguageRegistry.addName(glowRock, "Glow Rock");
		LanguageRegistry.addName(glowOre, "Ore Glow");
		LanguageRegistry.addName(glowTorch, "Glow Torch");

		// Items
		LanguageRegistry.addName(glowDust, "Glow Dust");
		LanguageRegistry.addName(diamondFusedNetherStar, "Encaptured Soul of The Nether");
		LanguageRegistry.addName(glowIngot, "Glow Ingot");
		LanguageRegistry.addName(netherSoulCollector, "Soul Collector of The Nether");

		// Glow Toolset
		LanguageRegistry.addName(glowPickaxeUnbreakable, "Pickaxe of The Lost Souls");
		LanguageRegistry.addName(glowShovelUnbreakable, "Glow Shovel");
		LanguageRegistry.addName(glowHoeUnbreakable, "Glow Hoe");
		LanguageRegistry.addName(glowAxeUnbreakable, "Glow Axe");
		LanguageRegistry.addName(glowSwordUnbreakable, "Glow Sword");

		// Glow Armor set
		LanguageRegistry.addName(glowHelmet, "Glow Helmet");
		LanguageRegistry.addName(glowChestplate, "Glow Chestplate");
		LanguageRegistry.addName(glowLegging, "Glow Leggings");
		LanguageRegistry.addName(glowBoot, "Glow Boots");
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

		// DiamondNetherStarIngot recipe
		GameRegistry.addRecipe(new ItemStack(diamondFusedNetherStar, 1), new Object[] {
				"xyx", "yzy", "xyx", 'x', Item.diamond, 'y', glowIngot, 'z', Item.netherStar
		});

		// Crafting the NetherSoulCollector
		GameRegistry.addRecipe(new ItemStack(netherSoulCollector, 1), new Object[] {
				"xyx", "yzy", "xyx", 'x', glowIngot, 'y', Item.ingotGold, 'z', diamondFusedNetherStar
		});

		// Craft the pick
		ItemStack pick = new ItemStack(glowPickaxeUnbreakable, 1);
		pick.addEnchantment(Enchantment.efficiency, 5);
		pick.addEnchantment(Enchantment.fortune, 4);

		GameRegistry.addRecipe(new ShapedOreRecipe(pick, new Object[] {
				"yxy", " z ", " z ", 'x', diamondFusedNetherStar, 'y', glowIngot, 'z', STICK
		}));

		// Crafting the sword
		ItemStack SWORD = new ItemStack(glowSwordUnbreakable, 1);
		SWORD.addEnchantment(Enchantment.fireAspect, 1);
		SWORD.addEnchantment(Enchantment.sharpness, 5);
		SWORD.addEnchantment(Enchantment.looting, 4);
		GameRegistry.addRecipe(new ShapedOreRecipe(SWORD, new Object[] {
				" w ", "yxy", " z ", 'w', glowIngot, 'x', diamondFusedNetherStar, 'y', Item.diamond, 'z', STICK
		}));

		// Crafting the axe
		ItemStack AXE = new ItemStack(glowAxeUnbreakable, 1);
		AXE.addEnchantment(Enchantment.efficiency, 5);
		GameRegistry.addRecipe(new ShapedOreRecipe(AXE, new Object[] {
				"wx ", "xy ", " y ", 'w', diamondFusedNetherStar, 'x', glowIngot, 'y', STICK,
		}));

		// Crafting the glowHoe
		ItemStack HOE = new ItemStack(glowHoeUnbreakable, 1);
		GameRegistry.addRecipe(new ShapedOreRecipe(HOE, new Object[] {
				"wx ", "yz ", " z ", 'w', glowIngot, 'x', diamondFusedNetherStar, 'y', Item.diamond, 'z', STICK
		}));

		// Crafting the glow Shovel
		ItemStack SHOVEL = new ItemStack(glowShovelUnbreakable, 1);
		SHOVEL.addEnchantment(Enchantment.efficiency, 5);
		GameRegistry.addRecipe(new ShapedOreRecipe(SHOVEL, new Object[] {
				" x ", " y ", " y ", 'x', diamondFusedNetherStar, 'y', STICK
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
		GameRegistry.addSmelting(glowOre.blockID, new ItemStack(glowDust, 1), 100f);
	}

	private void pulverizeRecipes() {
		// Code performing glowOre into 2*glowDust via Thermal Expansion
		// Pulverizer.
		NBTTagCompound toSend = new NBTTagCompound();
		toSend.setInteger("energy", 1000);
		toSend.setCompoundTag("input", new NBTTagCompound());
		toSend.setCompoundTag("primaryOutput", new NBTTagCompound());

		ItemStack inputStack = new ItemStack(glowOre, 1);
		inputStack.writeToNBT(toSend.getCompoundTag("input"));

		ItemStack outputStack = new ItemStack(glowDust, 2);
		outputStack.writeToNBT(toSend.getCompoundTag("primaryOutput"));
		FMLInterModComms.sendMessage("ThermalExpansion", "PulverizerRecipe", toSend);

	}

	private void registerTileEntities() {

	}

}
