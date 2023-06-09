package com.dawnfall.engine.util.math;

import com.badlogic.gdx.math.Plane;
import com.badlogic.gdx.math.Vector3;
import com.dawnfall.engine.Client.MainRenderer;
import com.dawnfall.engine.gen.Chunk;

/** Fast frustum utilities. */
public class FrustumUtil {
    public static boolean pointInFrustum (final Plane[] planes, Vector3 point) {
        for (int i = 2; i < planes.length; i++) {
            if (planes[i].testPoint(point) == Plane.PlaneSide.Back) return false;
        }
        return true;
    }

    /** the six clipping planes, near, far, left, right, top, bottom **/
    public static boolean frustumBounds(final Plane[] planes, Chunk chunk) {
        final float x = ((int)chunk.chunkCoordinates.x<<4)+8;

        final float z = ((int)chunk.chunkCoordinates.y<<4)+8;
        for (Plane plane : planes) {
            if (testBounds(plane, x, MainRenderer.player.getPlayerPosY(), z) != Plane.PlaneSide.Back) {
                continue;
            }
            return false;
        }

        return true;
    }

    private static Plane.PlaneSide testBounds(final Plane plane, final float x, final float y, final float z) {
        // Compute the projection interval radius of b onto L(t) = b.c + t * p.n
        final float radius = 8f * Math.abs(plane.normal.x) +
                8f * Math.abs(plane.normal.y) +
                8f * Math.abs(plane.normal.z);

        // Compute distance of box center from plane
        final float dist = plane.normal.dot(x, y, z) + plane.d;

        // Intersection occurs when plane distance falls within [-r,+r] interval
        if (dist > radius) {
            return Plane.PlaneSide.Front;
        } else if (dist < -radius) {
            return Plane.PlaneSide.Back;
        }

        return Plane.PlaneSide.OnPlane;
    }
}
