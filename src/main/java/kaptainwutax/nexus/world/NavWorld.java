package kaptainwutax.nexus.world;

import kaptainwutax.nexus.utility.math.Pos;

import java.util.HashMap;

public class NavWorld {

    public HashMap<Long, NavChunk> CHUNKS = new HashMap<>();

    public int getBlockNav(Pos pos) {
        NavChunk chunk = this.getChunk(pos.x >> 4, pos.z >> 4, false);
        if(chunk == null)return NavType.NULL;

        return chunk.STORAGE[pos.x & 16][pos.y][pos.z & 16];
    }

    public void setBlockNav(Pos pos, int navType) {
        NavChunk chunk = this.getChunk(pos.x >> 4, pos.z >> 4, true);
        chunk.STORAGE[pos.x & 16][pos.y][pos.z & 16] = navType;
    }

    public NavChunk getChunk(int chunkX, int chunkZ, boolean create) {
        long hash = chunkX | (long)chunkZ << 22;
        NavChunk chunk = CHUNKS.get(hash);

        if(create && chunk == null) {
            return CHUNKS.put(hash, new NavChunk());
        }

        return chunk;
    }

    public void unloadChunk(int chunkX, int chunkZ) {
        CHUNKS.remove(chunkX | (long)chunkZ << 22);
    }

}
