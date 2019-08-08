package kaptainwutax.nexus.path.agent;

import kaptainwutax.nexus.path.Node;
import net.minecraft.world.World;

import java.util.List;

public abstract class Agent {

    public abstract List<Node> getNodes(World world, Node currentNode);

}
