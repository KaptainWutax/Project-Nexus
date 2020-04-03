package kaptainwutax.nexus.path;

import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.util.Pair;
import net.minecraft.util.math.*;

public class PathHelper {

	public static class Position {

		public static Vec3d getCenterPosition(Entity entity) {
			Box box = entity.getBoundingBox();
			double x = box.x1 + box.getXLength() / 2.0D;
			double y = box.y1;
			double z = box.z1 + box.getZLength() / 2.0D;
			return new Vec3d(x, y, z);
		}

		public static boolean isHoldingOn(Entity entity, BlockPos pos, double height) {
			//TODO: Take into account more complex block shapes.
			BlockState state = entity.getEntityWorld().getBlockState(pos);

			return entity.getBoundingBox().intersects(
					pos.getX(), pos.getY(), pos.getZ(),
					pos.getX() + 1, pos.getY() + 1 + height, pos.getZ() + 1
			);
		}

		public static boolean isStandingOn(Entity entity, BlockPos pos, double height) {
			//TODO: Take into account more complex block shapes.
			BlockState state = entity.getEntityWorld().getBlockState(pos);

			Box box = new Box(
					pos.getX(), pos.getY(), pos.getZ(),
					pos.getX() + 1.0D, pos.getY() + 1.0D + height, pos.getZ() + 1.0D
			);

			return box.contains(getCenterPosition(entity));
		}

	}

	public static class Rotation {

		public static RotInfo getRotationToTarget(Entity entity, Vec3d target) {
			Vec3d eyes = entity.getCameraPosVec(1.0F);
			Vec3d lookVector = target.subtract(eyes).normalize();

			double yaw = Math.atan2(lookVector.getX(), lookVector.getZ()) / Math.PI * -180.0F;
			double pitch = Math.asin(-lookVector.getY()) / (Math.PI * 0.5F) * 90.0F;
			return new RotInfo((float)yaw, (float)pitch);
		}

		public static void lookAtTarget(Entity entity, Vec3d target, boolean yawOnly) {
			RotInfo rotInfo = getRotationToTarget(entity, target);
			entity.yaw = rotInfo.getYaw();
			if(!yawOnly)entity.pitch = rotInfo.getPitch();
		}

		public static void lookAtDirection(Entity entity, Direction direction) {
			entity.yaw = direction.asRotation();
		}

	}

	public static class Facing {

		public static Direction fromVector(Vec3i facing) {
			Vec3i normalized = new Vec3i(
					facing.getX() == 0 ? 0 : (int)Math.signum(facing.getX()),
					facing.getY() == 0 ? 0 : (int)Math.signum(facing.getY()),
					facing.getZ() == 0 ? 0 : (int)Math.signum(facing.getZ())
			);

			return Direction.fromVector(normalized.getX(), normalized.getY(), normalized.getZ());
		}

	}

}
