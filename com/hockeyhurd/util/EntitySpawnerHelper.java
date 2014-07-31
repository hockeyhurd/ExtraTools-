package com.hockeyhurd.util;

import java.util.HashMap;

public class EntitySpawnerHelper {

	private HashMap<String, Integer> mapping = new HashMap<String, Integer>();
	
	public EntitySpawnerHelper() {
	}
	
	public void init() {
		add("Creeper", 50);
		add("Skeleton", 51);
		add("Spider", 52);
		add("Giant", 53);
		add("Zombie", 54);
		add("Slime", 55);
		add("Ghast", 56);
		add("PigZombie", 57);
		add("Enderman", 58);
		add("CaveSpider", 59);
		add("Silverfish", 60);
		add("Blaze", 61);
		add("LavaSlime", 62);
		add("WitherBoss", 64);
		add("Bat", 65);
		add("Witch", 66);
		add("Pig", 90);
		add("Sheep", 91);
		add("Cow", 92);
		add("Chicken", 93);
		add("Squid", 94);
		add("Wolf", 95);
		add("MushroomCow", 96);
		add("SnowMan", 97);
		add("Ozelot", 98);
		add("VillagerGolem", 99);
		add("EntityHorse", 100);
		add("Villager", 120);
	}
	
	private void add(String name, int id) {
		this.mapping.put(name, id);
	}
	
	public int getMappedID(String name) {
		if (name != null && !name.equals("") && this.mapping.containsKey(name)) return this.mapping.get(name);
		else {
			System.err.println("Error! Mapping does not contain the entity: " + name);
			return this.mapping.get("Pig");
		}
	}
	
}
