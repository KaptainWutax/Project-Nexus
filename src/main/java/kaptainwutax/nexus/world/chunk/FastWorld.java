package kaptainwutax.nexus.world.chunk;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.network.packet.s2c.play.ChunkDataS2CPacket;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkSection;

import java.util.HashMap;
import java.util.Map;

public class FastWorld {

	protected int renderDistance = 52;
	protected Map<Long, FastChunk> chunks = new HashMap<>();

	public void tick(BlockPos playerPosition) {
		ChunkPos chunkPos = new ChunkPos(playerPosition);
		this.chunks.entrySet().removeIf(e -> new ChunkPos(e.getKey()).method_24022(chunkPos) > this.renderDistance);
	}

	public BlockState getBlockState(BlockPos pos) {
		FastChunk fastChunk = this.chunks.get(ChunkPos.toLong(pos.getX() >> 4, pos.getZ() >> 4));
		if(fastChunk == null)return Blocks.AIR.getDefaultState();
		return fastChunk.getBlockState(pos);
	}

	public void setBlockState(BlockPos pos, BlockState state) {
		FastChunk fastChunk = this.chunks.get(ChunkPos.toLong(pos.getX() >> 4, pos.getZ() >> 4));
		if(fastChunk == null)return;
		fastChunk.setBlockState(pos, state);
	}

	public void onChunkData(World world, ChunkDataS2CPacket packet) {
		if(!packet.isFullChunk())return;

		FastChunk fastChunk = new FastChunk();
		Chunk chunk = world.getChunk(packet.getX(), packet.getZ());
		if(chunk == null)return;

		ChunkSection[] sections = chunk.getSectionArray();

		for(int i = 0; i < sections.length; i++) {
			ChunkSection section = sections[i];
			if(section == null)continue;

			for(int x = 0; x < 16; x++) {
				for(int y = 0; y < 16; y++) {
					for(int z = 0; z < 16; z++) {
						fastChunk.setBlockState(new BlockPos(x, y + i * 16, z), section.getBlockState(x, y, z));
					}
				}
			}
		}

		this.chunks.put(ChunkPos.toLong(packet.getX(), packet.getZ()), fastChunk);
	}

	public void clear() {
		this.chunks.clear();
	}

}
