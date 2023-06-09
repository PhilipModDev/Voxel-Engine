package com.dawnfall.engine.rendering;


import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Disposable;
import com.dawnfall.engine.handle.RayCast;
import com.dawnfall.engine.util.BlockPos;
import com.dawnfall.engine.util.Camera;

public class BoxRenderer implements Disposable {
	private final Camera cam;
	private final ShapeRenderer shape;
	
	public BoxRenderer(Camera cam) {
		this.cam = cam;
		shape = new ShapeRenderer(32);
		shape.setAutoShapeType(true);
	}
	
	// 10754
	
	public void render(final RayCast.RayInfo ray) {
		if (ray == null) return;
		final BlockPos pos = ray.in;
		shape.setProjectionMatrix(cam.combined);
		shape.begin(ShapeRenderer.ShapeType.Line);
		shape.box(pos.x-0.01f, pos.y-0.01f, pos.z+1.01f, 1.02f, 1.02f, 1.02f);
		shape.end();
	}
	
	@Override
	public void dispose() {
		shape.dispose();
	}
}
