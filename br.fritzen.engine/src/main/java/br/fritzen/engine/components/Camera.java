package br.fritzen.engine.components;

import org.joml.Matrix4f;
import org.joml.Vector3f;

import lombok.Getter;

public abstract class Camera {

	@Getter
	protected Vector3f position;
	
	@Getter
	protected Matrix4f view;
	
	@Getter
	protected Matrix4f projection;
	
	@Getter
	protected Matrix4f viewProjection;
	
	
	public Camera() {
		
		this.view = new Matrix4f();
		this.projection = new Matrix4f();
		this.viewProjection = new Matrix4f();
		this.position = new Vector3f();
		
		System.out.println("Camera init");
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


	public abstract void recalculateViewProjection();
	
}
