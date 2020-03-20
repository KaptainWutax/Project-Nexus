package kaptainwutax.nexus.path.agent;

import kaptainwutax.nexus.init.Nodes;
import kaptainwutax.nexus.init.Speeds;
import kaptainwutax.nexus.path.Node;
import kaptainwutax.nexus.utility.Color;
import kaptainwutax.nexus.world.chunk.FastWorld;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;

import java.util.HashSet;
import java.util.Set;

public class AgentPlaceBlock extends Agent<ClientPlayerEntity> {

	protected static Color COLOR = new Color(0.5F, 0.0F, 0.5F);

	@Override
	public Set<Node> getNextNodes(FastWorld world, Node currentNode) {
		Set<Node> nodes = new HashSet<>();

		BlockPos pos = currentNode.pos;

		if(this.isValidPosUp(world, pos)) {
			//TODO: This is just an estimate. Needs proper testing.
			nodes.add(new Node(currentNode, this, pos.up(), (Speeds.SPRINT_JUMP / Speeds.WALK) * 2.5D));
		}

		for(Direction direction: Direction.Type.HORIZONTAL) {
			BlockPos pos2 = pos.offset(direction);
			if(this.isValidPosSide(world, pos2)) {
				//TODO: This is just an estimate. Needs proper testing.
				nodes.add(new Node(currentNode, this, pos2, (Speeds.SPRINT_JUMP / Speeds.WALK) * 1.8D));
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
		if(!world.getBlockState(pos).getMaterial().isLiquid() && !world.getBlockState(pos).isAir())return false;
		if(!Nodes.GO_THROUGH_BLOCKS.contains(world.getBlockState(pos.up()).getBlock()))return false;
		if(!Nodes.GO_THROUGH_BLOCKS.contains(world.getBlockState(pos.up(2)).getBlock()))return false;
		return true;
	}

	private boolean isValidPosSide(FastWorld world, BlockPos pos) {
		if(!world.getBlockState(pos.down()).getMaterial().isLiquid() && !world.getBlockState(pos.down()).isAir())return false;
		if(!Nodes.GO_THROUGH_BLOCKS.contains(world.getBlockState(pos).getBlock()))return false;
		if(!Nodes.GO_THROUGH_BLOCKS.contains(world.getBlockState(pos.up()).getBlock()))return false;
		return true;
	}

}
