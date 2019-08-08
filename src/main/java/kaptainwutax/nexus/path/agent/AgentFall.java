package kaptainwutax.nexus.path.agent;

import kaptainwutax.nexus.init.Nodes;
import kaptainwutax.nexus.init.Speeds;
import kaptainwutax.nexus.path.Node;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public class AgentFall extends Agent {

    private Direction direction;

    public AgentFall(Direction direction) {
        this.direction = direction;
    }

    @Override
    public List<Node> getNodes(World world, Node currentNode) {
        List<Node> nodes = new ArrayList<>();

        BlockPos pos = currentNode.getPos();

        if(isValidPos(world, pos, 2) &&
                isValidPos(world, pos.offset(this.direction).down(), 3)) {

            int depth = 0;
            BlockPos offsetPos = pos.offset(this.direction).down(2);

            while(Nodes.GO_THROUGH_BLOCKS.contains(world.getBlockState(offsetPos).getBlock())) {
                depth++;
                offsetPos = offsetPos.down();
            }

            offsetPos = offsetPos.up();

            Node node = new Node(offsetPos, this);
            node.pathCost = currentNode.pathCost + Math.sqrt(2) * (Speeds.SPRINT_JUMP / Speeds.STAIR_STEP_DOWN) + 0.1d * depth;
            nodes.add(node);
        }

        return nodes;
    }

    private boolean isValidPos(World world, BlockPos pos, int spaces) {
        for(int i = 0; i < spaces; i++) {
            if(!Nodes.GO_THROUGH_BLOCKS.contains(world.getBlockState(pos.up(i)).getBlock()))return false;
        }

        return true;
    }

}
