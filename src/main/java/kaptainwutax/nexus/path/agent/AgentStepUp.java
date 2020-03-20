package kaptainwutax.nexus.path.agent;

import kaptainwutax.nexus.init.Nodes;
import kaptainwutax.nexus.init.Speeds;
import kaptainwutax.nexus.path.Node;
import kaptainwutax.nexus.utility.Color;
import kaptainwutax.nexus.utility.FastMath;
import kaptainwutax.nexus.world.chunk.FastWorld;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;

import java.util.HashSet;
import java.util.Set;

public class AgentStepUp extends Agent<ClientPlayerEntity> {

	protected static Color COLOR = new Color(0.0f, 1.0f, 0.1F);

	@Override
	public Set<Node> getNextNodes(FastWorld world, Node currentNode) {
		Set<Node> nodes = new HashSet<>();

		if(!this.isValidPosUp(world, currentNode.pos))return nodes;

		for(Direction direction: Direction.Type.HORIZONTAL) {
			BlockPos pos = currentNode.pos.offset(direction).up();
			if(this.canStandAt(world, pos, 2)) {
				nodes.add(new Node(currentNode, this, pos, FastMath.SQRT_2 * (Speeds.SPRINT_JUMP / Speeds.STAIR_STEP_UP)));
			}
		}

		return nodes;
	}

	@Override
	public PathResult pathToNode(ClientPlayerEntity entity, Node targetNode) {
		return PathResult.COMPLETED;
	}

	@Override
	public Color getRenderColor() {
		return COLOR;
	}

	private boolean isValidPosUp(FastWorld world, BlockPos pos) {
		if(!Nodes.GO_THROUGH_BLOCKS.contains(world.getBlockState(pos).getBlock()))return false;
		if(!Nodes.GO_THROUGH_BLOCKS.contains(world.getBlockState(pos.up()).getBlock()))return false;
		if(!Nodes.GO_THROUGH_BLOCKS.contains(world.getBlockState(pos.up(2)).getBlock()))return false;
		return true;
	}

	private boolean canStandAt(FastWorld world, BlockPos pos, int spaces) {
		if(!Nodes.STEP_ON_BLOCKS.contains(world.getBlockState(pos.down()).getBlock()))return false;

		for(int i = 0; i < spaces; i++) {
			if(!Nodes.GO_THROUGH_BLOCKS.contains(world.getBlockState(pos.up(i)).getBlock()))return false;
		}

		return true;
	}

}
