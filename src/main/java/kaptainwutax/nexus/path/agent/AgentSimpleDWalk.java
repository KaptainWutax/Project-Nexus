package kaptainwutax.nexus.path.agent;

import kaptainwutax.nexus.init.Nodes;
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

public class AgentSimpleDWalk extends Agent<ClientPlayerEntity> {

	protected static Color COLOR = new Color(0.0f, 1.0f, 1.0F);

	@Override
	public void addNextNodes(FastWorld world, Node currentNode, Set<Node> children) {
		Profiler.GLOBAL.start("Agent | Diagonal Walk");

		for(Direction d1: Direction.Type.HORIZONTAL) {
			for(Direction d2: Direction.Type.HORIZONTAL) {
				if(d1.getAxis() == d2.getAxis())continue;
				BlockPos pos = currentNode.pos.offset(d1).offset(d2);
				if(!this.canStandAt(world, pos))continue;
				if(!this.canStandAt(world, currentNode.pos.offset(d1), 2))continue;
				if(!this.canStandAt(world, currentNode.pos.offset(d2), 2))continue;
				children.add(new Node(currentNode, this, pos, FastMath.SQRT_2));
			}
		}

		Profiler.GLOBAL.end();
	}

	@Override
	public PathResult pathToNode(ClientPlayerEntity player, Node fromNode, Node toNode) {
		if(PathHelper.Position.isStandingOn(player, toNode.pos.down(), 0.6D)) {
			return PathResult.COMPLETED;
		} else if(!PathHelper.Position.isHoldingOn(player, fromNode.pos.down(), 0.6D)
				|| !Nodes.GO_THROUGH_BLOCKS.contains(player.world.getBlockState(toNode.pos).getBlock())) {
			return PathResult.ERRORED;
		}

		PathHelper.Rotation.lookAtTarget(player, new Vec3d(toNode.pos.getX() + 0.5D, 0.0D, toNode.pos.getZ() + 0.5D), true);

		KeyHelper.setKeyPressed(MinecraftClient.getInstance().options.keyForward, true);
		KeyHelper.setKeyPressed(MinecraftClient.getInstance().options.keySprint, true);
		return PathResult.IN_PROGRESS;
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
