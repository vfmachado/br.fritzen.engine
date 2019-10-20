package br.fritzen.engine.components;

import org.joml.Matrix4f;

public class OrthographicCamera extends Camera {

	private float rotation;
	
	private Matrix4f tmpTransform;
	
	
	public OrthographicCamera() {
		this(-1, 1, -1, 1);
	}
	
	
	public OrthographicCamera(float left, float right, float bottom, float top) {
		
		super();
		
		this.projection.ortho(left, right, bottom, top, -1.0f, 1.0f);
		
		this.tmpTransform = new Matrix4f();
		
		recalculateViewProjection();
		
	}
	
	
	public void setProjection(float left, float right, float bottom, float top) {
		
		this.projection.ortho(left, right, bottom, top, -1.0f, 1.0f);
		recalculateViewProjection();
		
	}
	
	
	@Override
	public void recalculateViewProjection() {
		
		tmpTransform.identity();
		tmpTransform.translate(this.position).rotate((float)Math.toRadians(this.rotation), 0, 0, 1);
		tmpTransform.invert();
		
		this.view.set(tmpTransform);
		
		this.viewProjection.set(projection).mul(view);
		
	}

	
	public float getRotation() {
		return rotation;
	}


	public void setRotation(float rotation) {
		this.rotation = rotation;
		recalculateViewProjection();
	}

	
	public void addRotation(float amount) {
		this.rotation += amount;
		recalculateViewProjection();
	}
	
	
}
