package kaptainwutax.nexus.path.agent;

import kaptainwutax.nexus.path.Node;
import kaptainwutax.nexus.path.agent.Agent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public class AgentDiagonal extends Agent {

    private Direction direction1;
    private Direction direction2;

    public AgentDiagonal(Direction direction1, Direction direction2) {
        this.direction1 = direction1;
        this.direction2 = direction2;
    }

    @Override
    public List<Node> getNodes(World world, Node currentNode) {
        List<Node> nodes = new ArrayList<>();

        BlockPos pos = currentNode.getPos().offset(this.direction1).offset(this.direction2);
        if(!this.isValidPos(world, pos))return nodes;

        Node node = new Node(pos, this);
        node.pathCost = currentNode.pathCost + 2;
        nodes.add(node);

        return nodes;
    }

    private boolean isValidPos(World world, BlockPos pos) {
        if(!world.getBlockState(pos).isAir())return false;
        if(!world.getBlockState(pos.up()).isAir())return false;
        if(world.getBlockState(pos.down()).isAir())return false;
        if(!world.getBlockState(pos.offset(this.opposite(this.direction1))).isAir())return false;
        if(!world.getBlockState(pos.offset(this.opposite(this.direction2))).isAir())return false;
        if(!world.getBlockState(pos.up().offset(this.opposite(this.direction1))).isAir())return false;
        if(!world.getBlockState(pos.up().offset(this.opposite(this.direction2))).isAir())return false;

        return true;
    }

    public Direction opposite(Direction direction) {
        if(direction == Direction.NORTH) {
            return Direction.SOUTH;
        } else if(direction == Direction.SOUTH) {
            return Direction.NORTH;
        } else if(direction == Direction.EAST) {
            return Direction.WEST;
        } else if(direction == Direction.WEST) {
            return Direction.EAST;
        }

        return null;
    }

}

