package kaptainwutax.nexus.render;

import kaptainwutax.nexus.utility.Color;
import net.minecraft.client.util.math.Vector4f;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;

public class Cube extends Cuboid {

    public Cube() {
        this(BlockPos.ORIGIN, new Color(1.0f, 1.0f, 1.0f));
    }

    public Cube(BlockPos pos) {
        this(pos, new Color(1.0f, 1.0f, 1.0f));
    }

    public Cube(BlockPos pos, Color color) {
        super(toVec3d(pos), new Vec3d(1.0D, 1.0D, 1.0D), color);
    }

}
