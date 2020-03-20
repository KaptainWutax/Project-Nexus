package kaptainwutax.nexus.render;

import kaptainwutax.nexus.path.Node;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

public class NodeDisplay extends Line {

	protected Cuboid cuboid;
	protected BlockPos pos;

	public NodeDisplay(Node node) {
		super(Renderer.toCenteredVec3d(node.parent.pos), Renderer.toCenteredVec3d(node.pos), node.agent.getRenderColor());

		this.pos = node.pos;

		this.cuboid = new Cuboid(
				Renderer.toCenteredVec3d(this.pos).add(-0.1D, -0.1D, -0.1D),
				new Vec3d(0.2D, 0.2D, 0.2D), node.agent.getRenderColor()
		);
	}

	@Override
	public void render() {
		super.render();
		this.cuboid.render();
	}

	@Override
	public BlockPos getPos() {
		return this.pos;
	}

}
