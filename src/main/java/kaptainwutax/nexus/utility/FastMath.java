package kaptainwutax.nexus.utility;

import net.minecraft.util.math.BlockPos;

public class FastMath {

	public static final double SQRT_2 = Math.sqrt(2);

	//Maximum squared distance to be expected. 32 chunks by world height by 32 chunks.
	private static int MAX_TRIPLE_SUM_OF_SQUARES = (32 * 16) * (32 * 16) + 256 * 256 + (32 * 16) * (32 * 16);
	public static final double[] INTEGER_SQRT_TABLE = new double[MAX_TRIPLE_SUM_OF_SQUARES];

	static {
		for(int i = 0; i < MAX_TRIPLE_SUM_OF_SQUARES; i++) {
			INTEGER_SQRT_TABLE[i] = Math.sqrt(i);
		}
	}

	public static double distance(BlockPos p1, BlockPos p2) {
		int deltaX = p1.getX() - p2.getX();
		int deltaY = p1.getY() - p2.getY();
		int deltaZ = p1.getZ() - p2.getZ();
		int squaredDistance = deltaX * deltaX + deltaY * deltaY + deltaZ * deltaZ;
		if(squaredDistance >= MAX_TRIPLE_SUM_OF_SQUARES)return Math.sqrt(squaredDistance);
		return INTEGER_SQRT_TABLE[squaredDistance];
	}

}
