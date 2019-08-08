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

    //TODO: Fix this shit.
    @Override
    public boolean equals(Object obj) {
        return pos.equals(((Node)obj).getPos());
    }

    public BlockPos getPos() {
        return this.pos;
    }

    @Override
    public String toString() {
        return "Node [" + pos.getX() + ", " + pos.getY() + ", " + pos.getZ() + "] with " + distance + ", " + heuristic + ", " + totalCost;
    }

    @Override
    public int hashCode() {
        return this.pos.hashCode();
    }
}
