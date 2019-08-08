package kaptainwutax.nexus.path.agent;

import kaptainwutax.nexus.init.Nodes;
import kaptainwutax.nexus.init.Speeds;
import kaptainwutax.nexus.path.Node;
import net.minecraft.block.Blocks;
import net.minecraft.client.util.math.Vector4f;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public class AgentLinearSwim extends Agent {

    private Direction direction;

    public AgentLinearSwim(Direction direction) {
        this.direction = direction;
    }

    @Override
    public List<Node> getNodes(World world, Node currentNode) {
        List<Node> nodes = new ArrayList<>();

        BlockPos pos = currentNode.getPos().offset(this.direction);
        if(!this.isValidPos(world, pos))return nodes;

        Node node = new Node(pos, this);
        //TODO: This is just an estimate. Needs proper testing.
        node.pathCost = currentNode.pathCost + (Speeds.SPRINT_JUMP / Speeds.SPRINT);
        nodes.add(node);

        return nodes;
    }

    @Override
    public Vector4f getRenderColor() {
        return new Vector4f(0.0f, 0.0f, 1.0f, 1.0f);
    }

    private boolean isValidPos(World world, BlockPos pos) {
        if(world.getBlockState(pos.down()).getBlock() != Blocks.WATER)return false;
        if(!Nodes.GO_THROUGH_BLOCKS.contains(world.getBlockState(pos).getBlock()))return false;
        if(!Nodes.GO_THROUGH_BLOCKS.contains(world.getBlockState(pos.up()).getBlock()))return false;
        return true;
    }

}
