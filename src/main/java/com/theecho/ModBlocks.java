package com.theecho;

import com.theecho.block.EchoMirrorBlock;
import com.theecho.block.EchoOriginalBlock;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.MapColor;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public final class ModBlocks {
    public static final Block ECHO_MIRROR = register("echo_mirror", new EchoMirrorBlock(
            AbstractBlock.Settings.create()
                    .mapColor(MapColor.LAPIS)
                    .strength(1.5f, 6.0f)
                    .pistonBehavior(PistonBehavior.DESTROY)
    ));

    public static final Block ECHO_ORIGINAL = register("echo_original", new EchoOriginalBlock(
            AbstractBlock.Settings.create()
                    .mapColor(MapColor.BLACK)
                    .strength(5.0f, 1200.0f)
                    .pistonBehavior(PistonBehavior.BLOCK)
    ));

    private static Block register(String path, Block block) {
        Registry.register(Registries.BLOCK, TheEcho.id(path), block);
        Registry.register(Registries.ITEM, TheEcho.id(path), new BlockItem(block, new Item.Settings()));
        return block;
    }

    public static void register() {
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.FUNCTIONAL).register(entries -> {
            entries.add(ECHO_MIRROR);
        });
    }
}
