package br.fritzen.sandbox3d;

import org.joml.Matrix4f;
import org.joml.Vector4f;

import br.fritzen.engine.components.Mesh;
import br.fritzen.engine.components.Model;
import br.fritzen.engine.renderer.Material;
import lombok.Getter;


public class LightPosition {

	@Getter
	private Material material;

	private Model mesh;

	@Getter
	private Matrix4f transform;


	public LightPosition() {
		
		this.mesh = new Model("models/cube.obj");
		this.material = new Material();
		this.transform = new Matrix4f();
		
		Vector4f white = new Vector4f(1, 1, 1, 1);
		this.material.setAmbientColor(white);
		this.material.setDiffuseColor(white);
		this.material.setSpecularColor(white);
		this.material.setShininess(10);
		
	}


	public Mesh getMesh() {
		return this.mesh.getMeshes().get(0).getKey();
	}

}
