package kaptainwutax.nexus.path;

import net.minecraft.util.Pair;

public class RotInfo extends Pair<Float, Float> {

	public RotInfo(float yaw, float pitch) {
		super(yaw, pitch);
	}

	public float getYaw() {
		return this.getLeft();
	}

	public float getPitch() {
		return this.getRight();
	}

	public RotInfo withYaw(float yaw) {
		return new RotInfo(yaw, this.getPitch());
	}

	public RotInfo withPitch(float pitch) {
		return new RotInfo(this.getYaw(), pitch);
	}

}
