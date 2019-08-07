package kaptainwutax.nexus.path;

import net.minecraft.util.math.BlockPos;

public class Node {

    public Node parent;

    public double distance;
    public double heuristic;
    public double totalCost;

    private BlockPos pos;

    public Node(BlockPos pos) {
        this.pos = pos;
    }

    @Override
    public boolean equals(Object obj) {
        return pos.equals(obj);
    }

    public BlockPos getPos() {
        return this.pos;
    }

}
