package kaptainwutax.nexus.path.agent;

import kaptainwutax.nexus.init.Nodes;
import kaptainwutax.nexus.init.Speeds;
import kaptainwutax.nexus.path.Node;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public class AgentStep extends Agent {

    private Direction direction;

    public AgentStep(Direction direction) {
        this.direction = direction;
    }

    @Override
    public List<Node> getNodes(World world, Node currentNode) {
        List<Node> nodes = new ArrayList<>();

        BlockPos pos = currentNode.getPos();

        if(isValidPos(world, pos, 2) &&
                isValidPos(world, pos.offset(this.direction).offset(Direction.DOWN), 3)) {
            Node node = new Node(pos.offset(this.direction).offset(Direction.DOWN), this);
            node.pathCost = currentNode.pathCost + Math.sqrt(2) * (Speeds.SPRINT_JUMP / Speeds.STAIR_STEP_DOWN);
            nodes.add(node);
        }

        if(isValidPos(world, pos, 3) &&
                isValidPos(world, pos.offset(this.direction).offset(Direction.UP), 2)) {
            Node node = new Node(pos.offset(this.direction).offset(Direction.UP), this);
            node.pathCost = currentNode.pathCost + Math.sqrt(2) * (Speeds.SPRINT_JUMP / Speeds.STAIR_STEP_UP);
            nodes.add(node);
        }

        return nodes;
    }

    private boolean isValidPos(World world, BlockPos pos, int spaces) {
        if(!Nodes.STEP_ON_BLOCKS.contains(world.getBlockState(pos.down()).getBlock()))return false;

        for(int i = 0; i < spaces; i++) {
            if(!Nodes.GO_THROUGH_BLOCKS.contains(world.getBlockState(pos.up(i)).getBlock()))return false;
        }

        return true;
    }

}
