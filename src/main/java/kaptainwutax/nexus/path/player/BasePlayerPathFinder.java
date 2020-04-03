package kaptainwutax.nexus.path.player;

import kaptainwutax.nexus.path.KeyHelper;
import kaptainwutax.nexus.path.Node;
import kaptainwutax.nexus.path.PathFinder;
import kaptainwutax.nexus.path.agent.*;
import kaptainwutax.nexus.world.chunk.FastWorld;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.math.BlockPos;

import java.util.List;

public class BasePlayerPathFinder extends PathFinder {

	private BlockPos start;
	private BlockPos end;

	private boolean isLookingForPath = false;

	private List<Node> path;
	private int currentNodeId = 0;
	private boolean firstPath = true;

	public BasePlayerPathFinder(FastWorld world) {
		super(world);
		this.agents.add(new AgentSimpleWalk());
		this.agents.add(new AgentSimpleDWalk());
		this.agents.add(new AgentStepUp());
		this.agents.add(new AgentFall());
		this.agents.add(new AgentPlaceBlock());
	}

	@Override
	public void update() {
		if(this.path == null) {
			if(this.start == null || this.end == null || this.isLookingForPath)return;

			System.out.println("Finding path from " + this.start + " to " + this.end + ".");

			THREAD_POOL.submit(() -> {
				try {
					this.path = this.findPath(this.start, this.end);
					this.isLookingForPath = false;
					this.currentNodeId = 0;

					if(this.firstPath) {
						//MinecraftClient.getInstance().player.updatePosition(this.path.get(0).pos.getX() + 0.5F, this.path.get(0).pos.getY() + 0.5F, this.path.get(0).pos.getZ() + 0.5F);
						this.firstPath = false;
					}
				} catch(Exception e) {
					this.isLookingForPath = false;
					e.printStackTrace();
				}
			});

			this.isLookingForPath = true;
			return;
		}

		KeyHelper.unpressAll();

		if(this.currentNodeId + 1 >= this.path.size()) {
			this.reset();
			return;
		}

		Node fromNode = this.path.get(this.currentNodeId);
		Node toNode = this.path.get(this.currentNodeId + 1);

		Agent.PathResult result =
				Agent.PathResult.COMPLETED;
		//toNode.agent.pathToNode(MinecraftClient.getInstance().player, fromNode, toNode);

		if(result == Agent.PathResult.COMPLETED) {
			this.currentNodeId++;
			this.update();
		} else if(result == Agent.PathResult.ERRORED) {
			this.path = null;
			this.start = MinecraftClient.getInstance().player.getBlockPos();
			this.currentNodeId = 0;
			KeyHelper.unpressAll();
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
		this.currentNodeId = 0;
		this.firstPath = true;
	}

}
