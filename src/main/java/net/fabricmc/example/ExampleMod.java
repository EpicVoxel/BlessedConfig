package net.fabricmc.example;

import io.github.coolmineman.BlessedConfig;
import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;

public class ExampleMod implements ModInitializer {
	BlessedConfig config;

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.

		System.out.println("Hello Fabric world!");
		config = new BlessedConfig(new Identifier("modid", "config"))
			.addOption("TestString", "Jeff")
			.addOption("TestInt", "57")
			.addOption("DoubleTest", "6.35")
			.done();

		System.out.println(config.getOption("TestString"));
		System.out.println(Integer.parseInt(config.getOption("TestInt")));
		System.out.println(Double.parseDouble(config.getOption("DoubleTest")));
	}
}
