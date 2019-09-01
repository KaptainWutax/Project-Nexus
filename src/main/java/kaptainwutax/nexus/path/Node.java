package kaptainwutax.nexus.path;

import kaptainwutax.nexus.path.agent.Agent;
import net.minecraft.util.math.BlockPos;

public class Node {

    public Agent agent;
    public Node parent;

    public float pathCost;
    public float totalCost;
    public float heuristic;

    private BlockPos pos;

    public Node(BlockPos pos, Agent agent) {
        this.pos = pos;
        this.agent = agent;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == this)return true;
        if(obj == null)return false;
        if(obj.getClass() != Node.class)return false;
        Node node = (Node)obj;
        return node.pos.equals(this.pos);
    }

    public BlockPos getPos() {
        return this.pos;
    }

    @Override
    public String toString() {
        return "Node [" + pos.getX() + ", " + pos.getY() + ", " + pos.getZ() + "] with " + pathCost + ", " + totalCost;
    }

    @Override
    public int hashCode() {
        return this.pos.hashCode();
    }
}
