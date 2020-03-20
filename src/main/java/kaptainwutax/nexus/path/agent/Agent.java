package kaptainwutax.nexus.path.agent;

import kaptainwutax.nexus.path.Node;
import kaptainwutax.nexus.utility.Color;
import kaptainwutax.nexus.world.chunk.FastWorld;
import net.minecraft.entity.Entity;

import java.util.Set;

public abstract class Agent<T extends Entity> {

    public abstract Set<Node> getNextNodes(FastWorld world, Node currentNode);

    public abstract PathResult pathToNode(T entity, Node targetNode);

    public abstract Color getRenderColor();

    public enum PathResult {
        IN_PROGRESS, COMPLETED, ERRORED
    }

}
