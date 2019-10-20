package br.fritzen.engine.components;

import org.joml.Matrix4f;
import org.joml.Quaternionf;

import br.fritzen.engine.Application;

public class PerspectiveCamera extends Camera {

	private Quaternionf rotation;
	
	private Matrix4f tmpTransform;
	
	
	public PerspectiveCamera() {
		this(60f, (float) Application.getWindow().getWidth() / (float) Application.getWindow().getHeight(), 0.01f, 100f);
	}
	
	
	public PerspectiveCamera(float fovy, float aspect, float zNear, float zFar) {
		
		super();
		
		this.projection.perspective(fovy, aspect, zNear, zFar);
		
		this.tmpTransform = new Matrix4f();
		this.rotation = new Quaternionf();
		
		recalculateViewProjection();
		
	}
	
	
	public void setProjection(float fovy, float aspect, float zNear, float zFar) {
		
		this.projection.identity();
		this.projection.perspective(fovy, aspect, zNear, zFar);
		recalculateViewProjection();
		
	}
	
	
	@Override
	public void recalculateViewProjection() {

		tmpTransform.identity();
		tmpTransform.translate(this.position).rotate(rotation);
		tmpTransform.invert();
		
		this.view.set(tmpTransform);
		
		this.viewProjection.set(projection).mul(view);
		
	}

	
	public Quaternionf getRotation() {
		return rotation;
	}


	public void setRotation(Quaternionf rotation) {
		this.rotation = rotation;
		recalculateViewProjection();
	}

	/*
	public void addRotation(Quaternionf amount) {
		this.rotation += amount;
		recalculateViewProjection();
	}
	*/
	
}
