package kaptainwutax.nexus.path;

import kaptainwutax.nexus.PathRenderer;
import kaptainwutax.nexus.init.Agents;
import kaptainwutax.nexus.init.Speeds;
import kaptainwutax.nexus.path.agent.Agent;
import kaptainwutax.nexus.utility.Line;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.client.util.math.Vector4f;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.math.BlockPos;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Pathfinding {

    private static ClientWorld world;
    public static BlockPos start;
    public static BlockPos end;

    private Set<Node> OPEN = new HashSet<Node>();
    private Set<Node> CLOSED = new HashSet<Node>();

    private List<Node> PATH = new ArrayList<Node>();

    public static Thread thread = null;

    public static void tick() {
        world = MinecraftClient.getInstance().world;
        if(thread != null && thread.isAlive())return;

        thread = new Thread(() -> {
            Pathfinding pf = new Pathfinding();
            //BlockPos spawn = world.getSpawnPos();
            //pf.findPath(spawn, world.getTopPosition(Heightmap.Type.WORLD_SURFACE, spawn.add(60, 0, 60)));
            pf.findPath();

            PathRenderer.LINES.clear();

            for(Node n: pf.PATH) {
                if(n.parent == null)continue;
                Line line = new Line();
                line.pos1 = toVec3f(n.getPos());
                line.pos2 = toVec3f(n.parent.getPos());

                line.color = new Vector4f(0f, 1f, 1f, 1);

                if(n.agent != null) {
                    line.color = n.agent.getRenderColor();
                }

                PathRenderer.LINES.add(line);
            }
        });

        thread.start();
    }

    private static Vector3f toVec3f(BlockPos pos) {
        return new Vector3f(pos.getX() + 0.5f, pos.getY() + 0.5f, pos.getZ() + 0.5f);
    }

    public void findPath() {
        if(start != null && end != null)this.findPath(start.add(0, 0, 0), end.add(0, 0, 0));
    }

    public void findPath(BlockPos start, BlockPos end) {
        Node TARGET = new Node(end, null);
        Node START = new Node(start, null);
        OPEN.add(START);

        while(!OPEN.isEmpty()) {
            Node currentNode = null;

            for(Node node: OPEN) {
                if(currentNode == null || node.totalCost < currentNode.totalCost)currentNode = node;
            }

            OPEN.remove(currentNode);
            CLOSED.add(currentNode);

            if(currentNode.parent != null) {
                Line line = new Line();
                line.pos1 = toVec3f(currentNode.getPos());
                line.pos2 = toVec3f(currentNode.parent.getPos());

                line.color = new Vector4f(1.0f, 1.0f, 1.0f, 1.0f);

                if(currentNode.agent != null) {
                    line.color = currentNode.agent.getRenderColor();
                }

                PathRenderer.LINES.add(line);
            }

            if(currentNode.getPos().equals(TARGET.getPos())) {
                PATH.add(currentNode);

                while(currentNode.parent != null) {
                    PATH.add(currentNode.parent);
                    currentNode = currentNode.parent;
                }

                return;
            }

            for(Agent agent: Agents.AGENTS) {
                for(Node child: agent.getNodes(world, currentNode)) {
                    double pathCost = child.pathCost;
                    double totalCost = pathCost + Math.sqrt(TARGET.getPos().getSquaredDistance(child.getPos())) * Speeds.SPRINT_JUMP;

                    if(CLOSED.contains(child))continue;

                    Node existingNode;

                    if(!OPEN.contains(child)) {
                        OPEN.add(child);
                        child.parent = currentNode;
                        child.totalCost = totalCost;
                    } else if((existingNode = getNodeWithPos(OPEN, child.getPos())) != null && totalCost < existingNode.totalCost) {
                        existingNode.parent = currentNode;
                        existingNode.pathCost = pathCost;
                        existingNode.agent = child.agent;
                        existingNode.totalCost = existingNode.pathCost + Math.sqrt(TARGET.getPos().getSquaredDistance(existingNode.getPos())) * Speeds.SPRINT_JUMP;
                    }
                }
            }

        }

    }

    private Node getNodeWithPos(Set<Node> set, BlockPos pos) {
        for(Node n: set) {
            if(n.getPos().getX() == pos.getX() &&
                    n.getPos().getY() == pos.getY() &&
                    n.getPos().getZ() == pos.getZ())return n;
        }

        return null;
    }

}
