package br.fritzen.engine.components;

import org.joml.Matrix4f;
import org.joml.Quaternionf;

import br.fritzen.engine.Application;
import lombok.Getter;

public class PerspectiveCamera extends Camera {

	@Getter
	private Quaternionf rotation;
	
	private Matrix4f tmpTransform;
	
	
	public PerspectiveCamera() {
		this(60f, (float) Application.getWindow().getWidth() / (float) Application.getWindow().getHeight(), 0.1f, 1000f);
	}
	
	/**
	 * 
	 * @param fov in degrees
	 * @param aspect
	 * @param zNear
	 * @param zFar
	 */
	public PerspectiveCamera(float fov, float aspect, float zNear, float zFar) {
		
		super();
		this.tmpTransform = new Matrix4f();
		this.rotation = new Quaternionf();
		
		setProjection(fov, aspect, zNear, zFar);
		
	}
	
	
	public void setProjection(float fov, float aspect, float zNear, float zFar) {
		
		this.projection.identity();
		this.projection.perspective((float)Math.toRadians(fov), aspect, zNear, zFar);
		recalculateViewProjection();
		
	}
	
	
	@Override
	public void recalculateViewProjection() {

		tmpTransform.identity();
		tmpTransform.rotate(rotation).translate(this.position).invert();
		
		this.view.set(tmpTransform);
		
		this.viewProjection.set(projection).mul(view);
		
	}

	
	public void setRotation(Quaternionf rotation) {
		this.rotation = rotation;
		recalculateViewProjection();
	}

	
}
