package com.dawnfall.engine.handle;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.GridPoint3;
import com.badlogic.gdx.math.Vector3;
import com.dawnfall.engine.entity.Player;
import com.dawnfall.engine.gen.Blocks.BlockRegister;
import com.dawnfall.engine.gen.World.World;
import com.dawnfall.engine.util.BlockPos;

import static com.badlogic.gdx.math.MathUtils.floor;

public final class RayCast {
    private static final float LENGTH = 8.0f;
    private static final float STEPS = 0.05f;
    private static final RayInfo info = new RayInfo();
    private static final Vector3 pos = new Vector3();
    private static final Vector3 nor = new Vector3();
    private static final GridPoint3 offset = new GridPoint3();

    public static RayInfo ray(Player player) {
        final World world = player.world;
        final Camera cam = player.getCamera();
        nor.set(cam.direction).nor().scl(STEPS);

        final Vector3 camPos = cam.position;
        offset.set(floor(camPos.x), floor(camPos.y), floor(camPos.z));
        pos.set(camPos).sub(offset.x, offset.y, offset.z);

        int x  = 0, y  = 0, z  = 0; // Current integer position.
        int lx = 0, ly = 0, lz = 0; // Last integer position.

        for (float i = 0; i < LENGTH; i += STEPS) {
            pos.add(nor);
            if ((x = floor(pos.x)) == lx && (y = floor(pos.y)) == ly && (z = floor(pos.z)) == lz) continue;

            if (world.getBlock(x+offset.x, y+offset.y, z+offset.z) == BlockRegister.AIR) {
                lx = x; ly = y; lz = z;
                continue;
            }

            info.in.set(x, y, z).add(offset);
            info.out.set(lx, ly, lz).add(offset);
            return info;
        }

        return null;
    }

    public static class RayInfo {
        /** Outside the block. */
        public final BlockPos out = new BlockPos();
        /** Inside the block. */
        public final BlockPos in  = new BlockPos();
    }
}
