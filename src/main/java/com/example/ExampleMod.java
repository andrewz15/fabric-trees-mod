package com.example;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.player.AttackBlockCallback;
import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.fabricmc.fabric.api.resource.v1.DataResourceStore;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;

import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExampleMod implements ModInitializer {
	public static final String MOD_ID = "modid";

	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.

		LOGGER.info("Hello Fabric world!");
/*
		UseBlockCallback.EVENT.register((player, world, hand, hitResult) -> {
            BlockPos p = player.getOnPos();
			world.setBlock(p, Blocks.COBBLESTONE.defaultBlockState(), 3);
			return InteractionResult.PASS;
		});
*/

		//AttackBlockCallback.EVENT.register((player, level, hand, pos, direction) -> {
		UseBlockCallback.EVENT.register((player, world, hand, hitResult) -> {
			BlockPos p0 = player.getOnPos();
			/*
			for (int i = 0; i < 2; ++i) {
				for (int j = 0; j < 26; ++j) {
					for (int k = 0; k < 2; ++k) {
						BlockPos p = p0.offset(i + 1, j, k + 1);
						world.setBlock(p, Blocks.SPRUCE_LOG .defaultBlockState(), 3);
					}
				}
			}
			*/
			generateTreeTrunk(world, p0, 40, 10, 5);
			return InteractionResult.PASS;
		});
	}

	void generateTreeTrunk(Level world, BlockPos treePosition, int height, int baseWidth, int topWidth) {
		int prevWidth = 0;
		int offset = 0;
		for (int i = height; i > 0; --i) {
			int delta = (int)((double)(baseWidth - topWidth) * i / height) / 2;
			if (prevWidth == 0) {
				prevWidth = delta;
			} else {
				offset += prevWidth - delta;
			}
			prevWidth = delta;
			int levelWidth = topWidth + delta * 2;
			for (int j = 0; j < levelWidth; ++j) {
				for (int k = 0; k < levelWidth; ++k) {
					BlockPos p = treePosition.offset(j + offset + 4, height - i, k + offset + 4);
					world.setBlock(p, Blocks.SPRUCE_WOOD .defaultBlockState(), 3);

				}
			}
		}
	}
}