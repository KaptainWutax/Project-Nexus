package kaptainwutax.nexus.path;

import kaptainwutax.nexus.PathRenderer;
import kaptainwutax.nexus.utility.Line;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.client.util.math.Vector4f;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Heightmap;

import java.util.ArrayList;
import java.util.List;

public class Pathfinding {

    private static ClientWorld world;

    private static List<Node> OPEN = new ArrayList<Node>();
    private static List<Node> CLOSED = new ArrayList<Node>();

    private static List<Node> PATH = new ArrayList<Node>();

    public static void tick() {
        world = MinecraftClient.getInstance().world;
        if(world.getTimeOfDay() % 20 != 0)return;

        BlockPos spawn = world.getSpawnPos();

        System.out.print("Pathfinding...");
        findPath(spawn, world.getTopPosition(Heightmap.Type.WORLD_SURFACE, spawn.add(60, 0, 60)));
        System.out.println("done!");

        PathRenderer.LINES.clear();

        for(Node n: PATH) {
            if(n.parent == null)continue;
            Line line = new Line();
            line.pos1 = toVec3f(n.getPos());
            line.pos2 = toVec3f(n.parent.getPos());
            line.color = new Vector4f(0.5f, 0.5f, 0.5f, 1);
            PathRenderer.LINES.add(line);
        }
    }

    private static Vector3f toVec3f(BlockPos pos) {
        return new Vector3f(pos.getX() + 0.5f, pos.getY() + 0.5f, pos.getZ() + 0.5f);
    }


    public static void findPath(BlockPos start, BlockPos end) {
        OPEN.clear();
        CLOSED.clear();
        PATH.clear();

        Node TARGET = new Node(end);
        Node START = new Node(start);
        OPEN.add(START);

        while(!OPEN.isEmpty()) {
            Node currentNode = OPEN.get(0);

            for(Node node: OPEN) {
                if(node.totalCost < currentNode.totalCost)currentNode = node;
            }

            OPEN.remove(currentNode);

            if(currentNode.equals(TARGET)) {
                System.out.println("Found a path!");
                PATH.add(currentNode);

                while(currentNode.parent != null) {
                    PATH.add(currentNode.parent);
                    currentNode = currentNode.parent;
                }

                return;
            }

            for(int x = -1; x <= 1; x++) {
                for(int y = -1; y <= 1; y++) {
                    for(int z = -1; z <= 1; z++) {
                        Node child = new Node(currentNode.getPos().add(x, y, z));

                        double distance = currentNode.distance + currentNode.getPos().getSquaredDistance(child.getPos());

                        if(child.equals(currentNode)) {
                            continue;
                        } else if(!world.getBlockState(child.getPos()).isAir() || world.getBlockState(child.getPos().down()).isAir()) {
                            continue;
                        }

                        if(CLOSED.contains(child))continue;

                        if(!OPEN.contains(child)) {
                            OPEN.add(child);
                            child.parent = currentNode;
                            child.distance = distance;
                            child.totalCost = child.distance + TARGET.getPos().getSquaredDistance(child.getPos());
                        } else if(distance < OPEN.get(OPEN.indexOf(child)).distance) {
                            Node node = OPEN.get(OPEN.indexOf(child));
                            node.parent = currentNode;
                            node.distance = distance;
                            node.totalCost = node.distance + TARGET.getPos().getSquaredDistance(node.getPos());
                        }


                    }
                }
            }

            CLOSED.add(currentNode);
        }

        System.out.println("No path possible.");
    }

}
