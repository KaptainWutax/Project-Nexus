package kaptainwutax.nexus.path.agent;

import kaptainwutax.nexus.init.Nodes;
import kaptainwutax.nexus.init.Speeds;
import kaptainwutax.nexus.path.Node;
import net.minecraft.block.Blocks;
import net.minecraft.client.util.math.Vector4f;
import net.minecraft.entity.EntityContext;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public class AgentPlace extends Agent {

    private static final Direction[] DIRECTIONS = {Direction.NORTH, Direction.SOUTH, Direction.EAST, Direction.WEST};

    @Override
    public List<Node> getNodes(World world, Node currentNode) {
        List<Node> nodes = new ArrayList<>();

        BlockPos pos = currentNode.getPos();

        if(this.isValidPosUp(world, pos)) {
            Node node = new Node(pos.up(), this);
            //TODO: This is just an estimate. Needs proper testing.
            node.pathCost = (float) (currentNode.pathCost + (Speeds.SPRINT_JUMP / Speeds.WALK) * 4.0d);
            nodes.add(node);
        }

        for(Direction direction: DIRECTIONS) {
            if(this.isValidPosSide(world, pos.offset(direction))) {
                Node node = new Node(pos.offset(direction), this);
                //TODO: This is just an estimate. Needs proper testing.
                node.pathCost = (float) (currentNode.pathCost + (Speeds.SPRINT_JUMP / Speeds.WALK) * 3.0d);
                nodes.add(node);
            }
        }

        return nodes;
    }

    private boolean isValidPosUp(World world, BlockPos pos) {
        //if(!Nodes.STEP_ON_BLOCKS.contains(world.getBlockState(pos.down()).getBlock()))return false;
        if(!Nodes.GO_THROUGH_BLOCKS.contains(world.getBlockState(pos).getBlock()))return false;
        if(!Nodes.GO_THROUGH_BLOCKS.contains(world.getBlockState(pos.up()).getBlock()))return false;
        if(!Nodes.GO_THROUGH_BLOCKS.contains(world.getBlockState(pos.up().up()).getBlock()))return false;
        return true;
    }

    private boolean isValidPosSide(World world, BlockPos pos) {
        //TODO: Could also be any other replaceable block.
        if(!world.getBlockState(pos.down()).isAir())return false;
        if(!Nodes.GO_THROUGH_BLOCKS.contains(world.getBlockState(pos).getBlock()))return false;
        if(!Nodes.GO_THROUGH_BLOCKS.contains(world.getBlockState(pos.up()).getBlock()))return false;
        return true;
    }

    @Override
    public Vector4f getRenderColor() {
        return new Vector4f(0.5f, 0.0f, 0.5f, 1.0f);
    }

}
