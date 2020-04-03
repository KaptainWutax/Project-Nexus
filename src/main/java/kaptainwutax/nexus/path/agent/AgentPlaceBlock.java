package kaptainwutax.nexus.path.agent;

import kaptainwutax.nexus.init.Nodes;
import kaptainwutax.nexus.init.Speeds;
import kaptainwutax.nexus.path.KeyHelper;
import kaptainwutax.nexus.path.Node;
import kaptainwutax.nexus.path.PathHelper;
import kaptainwutax.nexus.profiler.Profiler;
import kaptainwutax.nexus.utility.Color;
import kaptainwutax.nexus.world.chunk.FastWorld;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;

import java.util.Set;

public class AgentPlaceBlock extends Agent<ClientPlayerEntity> {

	protected static Color COLOR = new Color(0.5F, 0.0F, 0.5F);

	@Override
	public void addNextNodes(FastWorld world, Node currentNode, Set<Node> children) {
		Profiler.GLOBAL.start("Agent | Place Block");

		BlockPos pos = currentNode.pos;

		if(this.isValidPosUp(world, pos)) {
			//TODO: This is just an estimate. Needs proper testing.
			children.add(new Node(currentNode, this, pos.up(), (Speeds.SPRINT_JUMP / Speeds.WALK) * 2.5D));
		}

		for(Direction direction: Direction.Type.HORIZONTAL) {
			BlockPos pos2 = pos.offset(direction);
			if(this.isValidPosSide(world, pos2)) {
				//TODO: This is just an estimate. Needs proper testing.
				children.add(new Node(currentNode, this, pos2, (Speeds.SPRINT_JUMP / Speeds.WALK) * 1.8D));
			}
		}

		Profiler.GLOBAL.end();
	}

	@Override
	public PathResult pathToNode(ClientPlayerEntity player, Node fromNode, Node toNode) {
		boolean isUp = PathHelper.Facing.fromVector(toNode.pos.subtract(fromNode.pos)).getAxis() == Direction.Axis.Y;

		if(PathHelper.Position.isHoldingOn(player, toNode.pos, 0.6D) && player.getY() >= toNode.pos.getY() + (isUp ? 1 : 0)
				&& Nodes.STEP_ON_BLOCKS.contains(player.world.getBlockState(toNode.pos.down()).getBlock())) {
			return PathResult.COMPLETED;
		} else if(!PathHelper.Position.isHoldingOn(player, fromNode.pos, 1.6D)) {
			return PathResult.ERRORED;
		}

		if(isUp) {
			PathHelper.Rotation.lookAtTarget(player, new Vec3d(fromNode.pos.getX() + 0.5D, fromNode.pos.getY(), fromNode.pos.getZ() + 0.5D), false);
			KeyHelper.setKeyPressed(MinecraftClient.getInstance().options.keyJump, true);
			KeyHelper.setKeyPressed(MinecraftClient.getInstance().options.keyForward, false);
		} else {
			Direction direction = PathHelper.Facing.fromVector(fromNode.pos.subtract(toNode.pos));

			double px = player.getX() - player.getBlockPos().getX();
			double pz = player.getZ() - player.getBlockPos().getZ();

			Vec3d aim = new Vec3d(
					fromNode.pos.getX() + px + px * direction.getOffsetX(),
					fromNode.pos.getY() - 7.5D,
					fromNode.pos.getZ() + pz + pz * direction.getOffsetZ()
			);

			PathHelper.Rotation.lookAtTarget(player, aim, false);
			KeyHelper.setKeyPressed(MinecraftClient.getInstance().options.keySneak, true);
			KeyHelper.setKeyPressed(MinecraftClient.getInstance().options.keyBack, true);
		}

		KeyHelper.setKeyPressed(MinecraftClient.getInstance().options.keyUse, true);
		return PathResult.IN_PROGRESS;
	}

	@Override
	public Color getRenderColor() {
		return COLOR;
	}

	private boolean isValidPosUp(FastWorld world, BlockPos pos) {
		if(!world.getBlockState(pos).getMaterial().isLiquid() && !world.getBlockState(pos).isAir())return false;
		if(!Nodes.GO_THROUGH_BLOCKS.contains(world.getBlockState(pos.up()).getBlock()))return false;
		if(!Nodes.GO_THROUGH_BLOCKS.contains(world.getBlockState(pos.up(2)).getBlock()))return false;
		if(!Nodes.GO_THROUGH_BLOCKS.contains(world.getBlockState(pos.up(3)).getBlock()))return false;
		return true;
	}

	private boolean isValidPosSide(FastWorld world, BlockPos pos) {
		if(!world.getBlockState(pos.down()).getMaterial().isLiquid() && !world.getBlockState(pos.down()).isAir())return false;
		if(!Nodes.GO_THROUGH_BLOCKS.contains(world.getBlockState(pos).getBlock()))return false;
		if(!Nodes.GO_THROUGH_BLOCKS.contains(world.getBlockState(pos.up()).getBlock()))return false;
		return true;
	}

}
