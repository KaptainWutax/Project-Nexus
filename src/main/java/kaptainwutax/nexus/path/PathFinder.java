package kaptainwutax.nexus.path;

import kaptainwutax.nexus.PathRenderer;
import kaptainwutax.nexus.path.agent.Agent;
import kaptainwutax.nexus.profiler.Profiler;
import kaptainwutax.nexus.render.Line;
import kaptainwutax.nexus.render.Renderer;
import kaptainwutax.nexus.utility.Color;
import kaptainwutax.nexus.utility.FastMath;
import kaptainwutax.nexus.world.chunk.FastWorld;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public abstract class PathFinder {

    protected ExecutorService THREAD_POOL = Executors.newFixedThreadPool(1);

    private FastWorld world;
    private Set<Agent<?>> agentsSet;

    private PathAlgo pathAlgo = PathAlgo.A_STAR;

    private Profiler profiler = new Profiler();

    public PathFinder(FastWorld world, Set<Agent<?>> agents) {
        this.world = world;
        this.agentsSet = agents;
    }

    public PathFinder setPathAlgo(PathAlgo pathAlgo) {
        this.pathAlgo = pathAlgo;
        return this;
    }

    public abstract void update();

    public List<Node> findPath(BlockPos start, BlockPos end) {
        this.setPathAlgo(PathAlgo.A_STAR);
        PathRenderer.RENDERERS.clear();
        this.profiler.clear();

        this.profiler.start("initialization");

        Node targetNode = new Node(end, null);
        Node startNode = new Node(start, null);

        PriorityQueue<Node> openNodes = new PriorityQueue<>();
        Map<BlockPos, Node> mappedOpenNodes = new HashMap<>();

        Set<Node> closedNodes = new HashSet<>();
        List<Node> finalPath = new ArrayList<>();

        openNodes.add(startNode);
        mappedOpenNodes.put(startNode.pos, startNode);

        this.profiler.end();

        for(int travelledNodes = 0; !openNodes.isEmpty(); travelledNodes++) {
            this.profiler.start("polling best node");
            //Grabs the node with the lowest pathCost.
            Node currentNode = openNodes.poll();
            this.profiler.end();

            if(travelledNodes % 4000 == 0)PathRenderer.RENDERERS.clear();
            PathRenderer.RENDERERS.add(currentNode.getRenderer());

            this.profiler.start("target check");

            //If the end node is reached, build the path and return.
            if(currentNode.pos.equals(targetNode.pos)) {
                PathRenderer.RENDERERS.clear();
                finalPath.add(currentNode);

                while(currentNode.parent != null) {
                    finalPath.add(currentNode.parent);
                    PathRenderer.RENDERERS.add(currentNode.getRenderer());
                    currentNode = currentNode.parent;
               }

                this.profiler.end();
                this.profiler.printReport();
                return finalPath;
            }

            this.profiler.end();

            for(Agent<?> agent: this.agentsSet) {
                this.profiler.start("agents");
                Set<Node> children = agent.getNextNodes(this.world, currentNode);
                this.profiler.end();

                for(Node childNode: children) {
                    this.profiler.start("cost calculation");
                    if(closedNodes.contains(childNode)) {
                        this.profiler.end();
                        continue;
                    }

                    childNode.pathCost = this.pathAlgo.getPathCost(childNode.pathCost);
                    childNode.heuristic = FastMath.distance(childNode.pos, targetNode.pos);;
                    childNode.heuristic = this.pathAlgo.getHeuristic(childNode.heuristic);
                    childNode.totalCost = childNode.pathCost + childNode.heuristic;
                    this.profiler.end();

                    Node existingNode = mappedOpenNodes.get(childNode.pos);

                    if(existingNode == null) {
                        this.profiler.start("opening node");
                        openNodes.add(childNode);
                        mappedOpenNodes.put(childNode.pos, childNode);
                        this.profiler.end();
                    } else if(childNode.totalCost < existingNode.totalCost) {
                        this.profiler.start("node swapping");
                        closedNodes.add(existingNode);

                        openNodes.add(childNode);
                        mappedOpenNodes.put(childNode.pos, childNode);
                        this.profiler.end();
                    }
                }
            }

            this.profiler.start("closing node");
            mappedOpenNodes.remove(currentNode.pos);
            closedNodes.add(currentNode);
            this.profiler.end();
        }

        return finalPath;
    }

}
