package kaptainwutax.nexus.path.agent;

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

        BlockPos pos = currentNode.getPos().offset(this.direction);
        if(!this.isValidPos(world, pos))return nodes;

        Node node = new Node(pos, this);
        node.pathCost = currentNode.pathCost + 1;
        nodes.add(node);

        return nodes;
    }

    private boolean isValidPos(World world, BlockPos pos) {
        if(!world.getBlockState(pos).isAir())return false;
        if(!world.getBlockState(pos.up()).isAir())return false;
        if(!world.getBlockState(pos.up().up()).isAir())return false;
        if(world.getBlockState(pos.down()).isAir())return false;
        return true;
    }

}
