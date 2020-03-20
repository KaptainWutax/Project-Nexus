package kaptainwutax.nexus.path.agent;

import kaptainwutax.nexus.init.Nodes;
import kaptainwutax.nexus.path.Node;
import kaptainwutax.nexus.utility.Color;
import kaptainwutax.nexus.utility.FastMath;
import kaptainwutax.nexus.world.chunk.FastWorld;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;

import java.util.HashSet;
import java.util.Set;

public class AgentSimpleWalk extends Agent<ClientPlayerEntity> {

	protected static Color COLOR = new Color(0.0F, 1.0F, 1.0F);

	@Override
	public Set<Node> getNextNodes(FastWorld world, Node currentNode) {
		Set<Node> children = new HashSet<>();

		for(Direction direction: Direction.Type.HORIZONTAL) {
			BlockPos pos = currentNode.pos.offset(direction);

			if(this.canStandAt(world, pos)) {
				children.add(new Node(currentNode, this, pos, 1.0D));
			}
		}

		return children;
	}

	public boolean canStandAt(FastWorld world, BlockPos pos) {
		if(!Nodes.STEP_ON_BLOCKS.contains(world.getBlockState(pos.down()).getBlock()))return false;
		if(!Nodes.GO_THROUGH_BLOCKS.contains(world.getBlockState(pos).getBlock()))return false;
		if(!Nodes.GO_THROUGH_BLOCKS.contains(world.getBlockState(pos.up()).getBlock()))return false;
		return true;
	}

	@Override
	public PathResult pathToNode(ClientPlayerEntity entity, Node targetNode) {
		double distance = FastMath.distance(entity.getBlockPos(), targetNode.pos);

		if(distance == 0) {
			return PathResult.COMPLETED;
		} else if(distance != 1) {
			return PathResult.ERRORED;
		}

		//Walk towards the target node's position.
		return PathResult.IN_PROGRESS;
	}

	@Override
	public Color getRenderColor() {
		return COLOR;
	}

}
