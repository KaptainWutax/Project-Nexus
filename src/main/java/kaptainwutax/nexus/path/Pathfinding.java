package kaptainwutax.nexus.path;

import net.minecraft.util.math.BlockPos;

import java.util.ArrayList;
import java.util.List;

public class Pathfinding {

    public static void findPath(BlockPos start, BlockPos end) {
        Node TARGET = new Node(end);

        List<Node> OPEN = new ArrayList<Node>();
        List<Node> CLOSED = new ArrayList<Node>();

        OPEN.add(new Node(start));

        while(!OPEN.isEmpty()) {
            Node currentNode = OPEN.get(0);

            for(Node node: OPEN) {
                if(node.totalCost < currentNode.totalCost)currentNode = node;
            }

            OPEN.remove(currentNode);
            CLOSED.add(currentNode);

            if(currentNode.equals(TARGET)) {
                System.out.println("Found path!");
                return;
            }

            for(int x = -1; x <= 1; x++) {
                for(int y = -1; y <= 1; y++) {
                    for(int z = -1; z <= 1; z++) {
                        Node child = new Node(currentNode.getPos().add(x, y, z));
                        if(CLOSED.contains(child))continue;

                        child.distance = currentNode.distance + currentNode.getPos().getSquaredDistance(child.getPos());
                        child.heuristic = TARGET.getPos().getSquaredDistance(child.getPos());
                        child.totalCost = child.distance + child.heuristic;

                        //if(OPEN.contains(child))
                    }
                }
            }
        }
    }

}
