package kaptainwutax.nexus.path.agent;

import kaptainwutax.nexus.init.Nodes;
import kaptainwutax.nexus.init.Speeds;
import kaptainwutax.nexus.path.KeyHelper;
import kaptainwutax.nexus.path.Node;
import kaptainwutax.nexus.path.PathHelper;
import kaptainwutax.nexus.profiler.Profiler;
import kaptainwutax.nexus.utility.Color;
import kaptainwutax.nexus.utility.FastMath;
import kaptainwutax.nexus.world.chunk.FastWorld;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;

import java.util.Set;

public class AgentFall extends Agent<ClientPlayerEntity> {

	protected static Color COLOR = new Color(1.0f, 0.0f, 0.0F);

	@Override
	public void addNextNodes(FastWorld world, Node currentNode, Set<Node> children) {
		Profiler.GLOBAL.start("Agent | Fall");

		for(Direction direction: Direction.Type.HORIZONTAL) {
			BlockPos pos = currentNode.pos.offset(direction).down();
			if(!this.canStandAt(world, pos, 3))continue;

			int depth = 0;
			pos = pos.down();

			while(Nodes.GO_THROUGH_BLOCKS.contains(world.getBlockState(pos).getBlock()) && depth < 10 && pos.getY() > 0) {
				depth++;
				pos = pos.down();
			}

			if(!Nodes.STEP_ON_BLOCKS.contains(world.getBlockState(pos).getBlock()))continue;

			if(world.getBlockState(pos).getMaterial().isLiquid()) {
				pos = pos.down();
			}

			children.add(new Node(currentNode, this, pos.up(), FastMath.SQRT_2 * (Speeds.SPRINT_JUMP / Speeds.STAIR_STEP_DOWN) + 0.05D * depth));
		}

		Profiler.GLOBAL.end();
	}

	@Override
	public PathResult pathToNode(ClientPlayerEntity player, Node fromNode, Node toNode) {
		if(PathHelper.Position.isHoldingOn(player, toNode.pos.down(), 0.6D)) {
			return PathResult.COMPLETED;
		} else if(!PathHelper.Position.isHoldingOn(player, fromNode.pos.down(), 0.6D)
				&& !PathHelper.Position.isHoldingOn(player, toNode.pos.down(), Double.MAX_VALUE)) {
			return PathResult.ERRORED;
		}

		PathHelper.Rotation.lookAtTarget(player, new Vec3d(toNode.pos.getX() + 0.5D, 0, toNode.pos.getZ() + 0.5D), true);

		KeyHelper.setKeyPressed(MinecraftClient.getInstance().options.keyForward, true);
		return PathResult.IN_PROGRESS;
	}

	@Override
	public Color getRenderColor() {
		return COLOR;
	}

	private boolean canStandAt(FastWorld world, BlockPos pos, int spaces) {
		for(int i = 0; i < spaces; i++) {
			if(!Nodes.GO_THROUGH_BLOCKS.contains(world.getBlockState(pos.up(i)).getBlock()))return false;
		}

		return true;
	}

}
