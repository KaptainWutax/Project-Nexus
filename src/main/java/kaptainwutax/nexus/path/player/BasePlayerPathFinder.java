package kaptainwutax.nexus.path.player;

import kaptainwutax.nexus.path.Node;
import kaptainwutax.nexus.path.PathFinder;
import kaptainwutax.nexus.path.agent.*;
import kaptainwutax.nexus.world.chunk.FastWorld;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.math.BlockPos;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class BasePlayerPathFinder extends PathFinder {

	protected static Set<Agent<?>> AGENTS = new HashSet<>();
	private BlockPos start;
	private BlockPos end;

	private boolean isLookingForPath = false;

	private List<Node> path;
	private int currentNodeId = 0;

	public BasePlayerPathFinder(FastWorld world) {
		super(world, AGENTS);
	}

	@Override
	public void update() {
		if(this.path == null) {
			if(this.start == null || this.end == null || this.isLookingForPath)return;

			System.out.println("Finding path from " + this.start + " to " + this.end + ".");

			THREAD_POOL.submit(() -> {
				this.path = this.findPath(this.start, this.end);
				this.isLookingForPath = false;
				this.currentNodeId = 0;

				MinecraftClient.getInstance().player.updatePosition(
						this.path.get(0).pos.getX() + 0.5F,
						this.path.get(0).pos.getY() + 0.5F,
						this.path.get(0).pos.getZ() + 0.5F
				);
			});

			this.isLookingForPath = true;
			return;
		}

		if(this.currentNodeId + 1 == this.path.size()) {
			this.path = null;
			this.currentNodeId = 0;
			return;
		}

		Node currentNode = this.path.get(this.currentNodeId);
		Agent.PathResult result = currentNode.agent.pathToNode(MinecraftClient.getInstance().player, this.path.get(this.currentNodeId + 1));

		if(result == Agent.PathResult.COMPLETED) {
			this.currentNodeId++;
		} else if(result == Agent.PathResult.ERRORED) {
			this.path = null;
			this.currentNodeId = 0;
		}
	}

	public boolean setStart(BlockPos start) {
		if(start.equals(this.start))return false;
		this.start = start;
		return true;
	}

	public boolean setEnd(BlockPos end) {
		if(end.equals(this.end))return false;
		this.end = end;
		return true;
	}

	public void reset() {
		this.start = null;
		this.end = null;
		this.path = null;
	}

	static {
		AGENTS.add(new AgentSimpleWalk());
		AGENTS.add(new AgentSimpleDWalk());
		AGENTS.add(new AgentStepUp());
		AGENTS.add(new AgentFall());
		AGENTS.add(new AgentPlaceBlock());
	}

}
