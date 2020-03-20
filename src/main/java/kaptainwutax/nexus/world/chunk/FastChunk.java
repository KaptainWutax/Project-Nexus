package kaptainwutax.nexus.world.chunk;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;

public class FastChunk {

	protected final BlockState[][][] blockStates = new BlockState[16][256][16];

	public BlockState getBlockState(BlockPos pos) {
		if(pos.getY() < 0 || pos.getY() > 255)return Blocks.AIR.getDefaultState();
		BlockState state = this.blockStates[pos.getX() & 15][pos.getY()][pos.getZ() & 15];
		return state == null ? Blocks.AIR.getDefaultState() : state;
	}

	public void setBlockState(BlockPos pos, BlockState state) {
		if(pos.getY() < 0 || pos.getY() > 255)return;
		this.blockStates[pos.getX() & 15][pos.getY()][pos.getZ() & 15] = state;
	}

}
