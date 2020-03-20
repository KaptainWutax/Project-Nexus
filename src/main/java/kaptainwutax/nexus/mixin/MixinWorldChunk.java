package kaptainwutax.nexus.mixin;

import kaptainwutax.nexus.Nexus;
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

    @Inject(at = @At("HEAD"), method = "setBlockState")
    public void setBlockState(BlockPos pos, BlockState state, boolean flag, CallbackInfoReturnable<Boolean> cir) {
        if(!this.world.isClient)return;

        Nexus.getInstance().world.setBlockState(pos, state);

        if(state.getBlock() == Blocks.DIAMOND_BLOCK) {
            if(Nexus.getInstance().pathFinder.setStart(pos.up())) {
                System.out.println("Set starting point.");
            }
        } else if(state.getBlock() == Blocks.GOLD_BLOCK) {
            if(Nexus.getInstance().pathFinder.setEnd(pos.up())) {
                System.out.println("Set ending point.");
            }
        }
    }

}
