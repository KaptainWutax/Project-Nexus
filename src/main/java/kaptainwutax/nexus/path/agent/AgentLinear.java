package kaptainwutax.nexus.path.agent;

import kaptainwutax.nexus.init.Nodes;
import kaptainwutax.nexus.path.Node;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public class AgentLinear extends Agent {

    private Direction direction;

    public AgentLinear(Direction direction) {
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
        if(!Nodes.GO_THROUGH_BLOCKS.contains(world.getBlockState(pos).getBlock()))return false;
        if(!Nodes.GO_THROUGH_BLOCKS.contains(world.getBlockState(pos.up()).getBlock()))return false;
        if(!Nodes.STEP_ON_BLOCKS.contains(world.getBlockState(pos.down()).getBlock()))return false;
        return true;
    }

}
