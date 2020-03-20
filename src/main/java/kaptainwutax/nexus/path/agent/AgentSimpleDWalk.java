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

public class AgentSimpleDWalk extends Agent<ClientPlayerEntity> {

	protected static Color COLOR = new Color(0.0f, 1.0f, 1.0F);

	@Override
	public Set<Node> getNextNodes(FastWorld world, Node currentNode) {
		Set<Node> children = new HashSet<>();

		for(Direction d1: Direction.Type.HORIZONTAL) {
			for(Direction d2: Direction.Type.HORIZONTAL) {
				if(d1.getAxis() == d2.getAxis())continue;
				if(!this.canStandAt(world, currentNode.pos.offset(d1), 2))continue;
				if(!this.canStandAt(world, currentNode.pos.offset(d2), 2))continue;

				BlockPos pos = currentNode.pos.offset(d1).offset(d2);

				if(this.canStandAt(world, pos)) {
					children.add(new Node(currentNode, this, pos, FastMath.SQRT_2));
				}
			}
		}

		return children;
	}

	@Override
	public PathResult pathToNode(ClientPlayerEntity entity, Node targetNode) {
		return PathResult.COMPLETED;
	}

	@Override
	public Color getRenderColor() {
		return COLOR;
	}

	public boolean canStandAt(FastWorld world, BlockPos pos) {
		if(!Nodes.STEP_ON_BLOCKS.contains(world.getBlockState(pos.down()).getBlock()))return false;
		if(!Nodes.GO_THROUGH_BLOCKS.contains(world.getBlockState(pos).getBlock()))return false;
		if(!Nodes.GO_THROUGH_BLOCKS.contains(world.getBlockState(pos.up()).getBlock()))return false;
		return true;
	}

	private boolean canStandAt(FastWorld world, BlockPos pos, int spaces) {
		for(int i = 0; i < spaces; i++) {
			if(!Nodes.GO_THROUGH_BLOCKS.contains(world.getBlockState(pos.up(i)).getBlock()))return false;
		}

		return true;
	}

}
