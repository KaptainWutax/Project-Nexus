package kaptainwutax.nexus.mixin;

import kaptainwutax.nexus.path.Pathfinding;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.ChunkSection;
import net.minecraft.world.chunk.WorldChunk;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(WorldChunk.class)
public abstract class MixinWorldChunk {

    @Shadow @Final private World world;
    @Shadow @Final private ChunkPos pos;
    @Shadow @Final private ChunkSection[] sections;
    @Shadow @Final public static ChunkSection EMPTY_SECTION;

    @Inject(at = @At("HEAD"), method = "setBlockState")
    public void setBlockState(BlockPos pos, BlockState state, boolean flag, CallbackInfoReturnable cir) {
        if(!this.world.isClient)return;

        if(state.getBlock() == Blocks.DIAMOND_BLOCK) {
            Pathfinding.start = pos.up();
            System.out.println("Set starting point.");
        } else if(state.getBlock() == Blocks.GOLD_BLOCK) {
            Pathfinding.end = pos.up();
            System.out.println("Set ending point.");
        }

        return;
    }

}
