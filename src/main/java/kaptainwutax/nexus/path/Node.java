package kaptainwutax.nexus.path;

import kaptainwutax.nexus.path.agent.Agent;
import kaptainwutax.nexus.render.Line;
import kaptainwutax.nexus.render.NodeDisplay;
import kaptainwutax.nexus.render.Renderer;
import net.minecraft.util.math.BlockPos;

public class Node implements Comparable<Node> {

    public final BlockPos pos;
    public final Agent agent;

    public Node parent;

    public double pathCost;
    public double heuristic;
    public double totalCost;

    public Node(BlockPos pos, Agent<?> agent) {
        this.pos = pos;
        this.agent = agent;
    }

    public Node(Node parent, Agent<?> agent, BlockPos pos, double pathCostAddend) {
        this(pos, agent);
        this.parent = parent;
        this.pathCost = this.parent.pathCost + pathCostAddend;
    }

    public Renderer getRenderer() {
        if(this.parent == null)return Line.NO_LINE;
        return new NodeDisplay(this);
    }

    @Override
    public int compareTo(Node o) {
        if(this.totalCost == o.totalCost)return 0;
        return this.totalCost > o.totalCost ? 1 : -1;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == this)return true;
        if(obj == null)return false;
        if(obj.getClass() != this.getClass())return false;
        Node node = (Node)obj;
        return this.pos.equals(node.pos);
    }

    @Override
    public int hashCode() {
        return this.pos.hashCode();
    }

    @Override
    public String toString() {
        return "Node{" +
                "pathCost=" + this.pathCost +
                ", heuristic=" + this.heuristic +
                ", totalCost=" + this.totalCost +
                '}';
    }

}
