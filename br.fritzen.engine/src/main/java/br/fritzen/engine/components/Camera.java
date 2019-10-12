package br.fritzen.engine.components;

import org.joml.Matrix4f;
import org.joml.Vector3f;

public abstract class Camera {

	protected Vector3f position;
	
	protected Matrix4f view;
	
	protected Matrix4f projection;
	
	protected Matrix4f viewProjection;
	
	
	public Camera() {
		
		this.view = new Matrix4f();
		this.projection = new Matrix4f();
		this.viewProjection = new Matrix4f();
		this.position = new Vector3f();
		
		System.out.println("Camera init");
	}
	
	
	public Vector3f getPosition() {
		return position;
	}


	public void setPosition(Vector3f position) {
		this.position = position;
		this.recalculateViewProjection();
	}


	public void setView(Matrix4f view) {
		this.view = view;
		this.recalculateViewProjection();
	}


	public void setProjection(Matrix4f projection) {
		this.projection = projection;
		this.recalculateViewProjection();
	}


	public Matrix4f getViewMatrix() {
		return view;
	}
	
	
	public Matrix4f getProjectionMatrix() {
		return projection;
	}
	
	
	public Matrix4f getViewProjectionMatrix() {
		return viewProjection;
	}
	
	
	public abstract void recalculateViewProjection();
	
}
