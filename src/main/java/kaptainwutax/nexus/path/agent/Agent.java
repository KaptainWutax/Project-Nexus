package kaptainwutax.nexus.path.agent;

import kaptainwutax.nexus.path.Node;
import kaptainwutax.nexus.utility.Color;
import kaptainwutax.nexus.world.chunk.FastWorld;
import net.minecraft.entity.Entity;

import java.util.HashSet;
import java.util.Set;

public abstract class Agent<T extends Entity> {

	public abstract void addNextNodes(FastWorld world, Node currentNode, Set<Node> nodes);

    public final Set<Node> getNextNodes(FastWorld world, Node currentNode) {
    	Set<Node> nodes = new HashSet<>();
    	this.addNextNodes(world, currentNode, nodes);
    	return nodes;
    }

    public abstract PathResult pathToNode(T entity, Node fromNode, Node toNode);

    public abstract Color getRenderColor();

	public enum PathResult {
        IN_PROGRESS, COMPLETED, ERRORED
    }

}
