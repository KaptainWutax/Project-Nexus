package kaptainwutax.nexus.path;

import kaptainwutax.nexus.PathRenderer;
import kaptainwutax.nexus.path.agent.Agent;
import kaptainwutax.nexus.profiler.Profiler;
import kaptainwutax.nexus.utility.FastMath;
import kaptainwutax.nexus.world.chunk.FastWorld;
import net.minecraft.util.math.BlockPos;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public abstract class PathFinder {

    protected ExecutorService THREAD_POOL = Executors.newFixedThreadPool(1);

    private FastWorld world;
    protected Set<Agent<?>> agents = new HashSet<>();

    private PathAlgo pathAlgo = PathAlgo.A_STAR;

    public PathFinder(FastWorld world) {
        this.world = world;
    }

    public PathFinder setPathAlgo(PathAlgo pathAlgo) {
        this.pathAlgo = pathAlgo;
        return this;
    }

    public abstract void update();

    public List<Node> findPath(BlockPos start, BlockPos end) {
        this.setPathAlgo(PathAlgo.GREEDY);
        PathRenderer.RENDERERS.clear();
        Profiler.GLOBAL.clear();

        Profiler.GLOBAL.start("initialization");

        Node targetNode = new Node(end, null);
        Node startNode = new Node(start, null);

        PriorityQueue<Node> openNodes = new PriorityQueue<>();
        Map<BlockPos, Node> mappedOpenNodes = new HashMap<>();

        Set<Node> closedNodes = new HashSet<>();
        List<Node> finalPath = new ArrayList<>();
        Set<Node> children = new HashSet<>();

        openNodes.add(startNode);
        mappedOpenNodes.put(startNode.pos, startNode);

        Profiler.GLOBAL.end();

        for(int travelledNodes = 0; !openNodes.isEmpty(); travelledNodes++) {
            Profiler.GLOBAL.start("polling node");
            //Grabs the node with the lowest pathCost.
            Node currentNode = openNodes.poll();

            if(travelledNodes % 4000 == 0)PathRenderer.RENDERERS.clear();
            PathRenderer.RENDERERS.add(currentNode.getRenderer());

            Profiler.GLOBAL.swap("target check");

            //If the end node is reached, build the path and return.
            if(currentNode.pos.equals(targetNode.pos)) {
                PathRenderer.RENDERERS.clear();
                finalPath.add(currentNode);

                while(currentNode.parent != null) {
                    finalPath.add(currentNode.parent);
                    PathRenderer.RENDERERS.add(currentNode.getRenderer());
                    currentNode = currentNode.parent;
               }

                Collections.reverse(finalPath);
                Profiler.GLOBAL.end();
                Profiler.GLOBAL.printReport();
                return finalPath;
            }


            Profiler.GLOBAL.swap("closing node");
            mappedOpenNodes.remove(currentNode.pos);
            closedNodes.add(currentNode);
            Profiler.GLOBAL.end();

            children.clear();

            for(Agent<?> agent: this.agents) {
                agent.addNextNodes(this.world, currentNode, children);
            }

            for(Node childNode: children) {
                Profiler.GLOBAL.start("cost calculation");
                childNode.pathCost = this.pathAlgo.getPathCost(childNode.pathCost);
                childNode.heuristic = FastMath.distance(childNode.pos, targetNode.pos);
                childNode.heuristic = this.pathAlgo.getHeuristic(childNode.heuristic);
                childNode.totalCost = childNode.pathCost + childNode.heuristic;
                Profiler.GLOBAL.end();

                Node existingNode = mappedOpenNodes.get(childNode.pos);

                if(existingNode == null || childNode.totalCost < existingNode.totalCost) {
                    Profiler.GLOBAL.start("opening node");

                    if(!closedNodes.contains(childNode)) {
                        openNodes.add(childNode);
                    }

                    mappedOpenNodes.put(childNode.pos, childNode);
                    Profiler.GLOBAL.end();
                }
            }
        }

        return finalPath;
    }

}
