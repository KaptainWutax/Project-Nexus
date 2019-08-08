package kaptainwutax.nexus.init;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.util.registry.Registry;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Nodes {

    public static Set<Block> GO_THROUGH_BLOCKS = new HashSet<Block>();
    public static Set<Block> STEP_ON_BLOCKS = new HashSet<Block>();

    static {
        for(Block block: Registry.BLOCK) {
            if(block.canMobSpawnInside()) {
                GO_THROUGH_BLOCKS.add(block);
            } else if(block.getDefaultState().getMaterial().isSolid()) {
                STEP_ON_BLOCKS.add(block);
            }
        }
    }

}
