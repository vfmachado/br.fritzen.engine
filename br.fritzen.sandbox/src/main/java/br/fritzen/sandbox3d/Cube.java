package br.fritzen.sandbox3d;

import org.joml.Matrix4f;

import br.fritzen.engine.components.Mesh;
import br.fritzen.engine.components.Model;
import br.fritzen.engine.renderer.Material;
import lombok.Getter;

public class Cube {

	@Getter
	private Material material;
	
	private Model mesh;
	
	@Getter
	private Matrix4f transform;
	
	public Cube() {
		this.mesh = new Model("models/cube.obj");
		this.material = new Material();
		this.transform = new Matrix4f();
	}
	
	public Mesh getMesh() {
		return this.mesh.getMeshes().get(0).getKey();
	}
	
}
