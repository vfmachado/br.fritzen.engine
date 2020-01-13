package br.fritzen.engine.components;

import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Vector3f;

import br.fritzen.engine.Application;
import lombok.Getter;

public class PerspectiveCamera extends Camera {

	@Getter
	private Quaternionf rotation;
	
	private Matrix4f tmpTransform;
	
	private Vector3f auxPos = new Vector3f();
	
	
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
	
	Quaternionf auxQ = new Quaternionf();
	
	@Override
	public void recalculateViewProjection() {
		tmpTransform.identity();
		
		auxQ.set(rotation).conjugate().get(tmpTransform).translate(this.position.mul(-1, auxPos));
		
		this.view.set(tmpTransform);
		
		this.viewProjection.set(projection).mul(view);
		
	}

	
	public void setRotation(Quaternionf rotation) {
		this.rotation = rotation;
		recalculateViewProjection();
	}

	
}
