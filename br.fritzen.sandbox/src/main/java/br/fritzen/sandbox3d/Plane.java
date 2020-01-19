package br.fritzen.sandbox3d;

import org.joml.Matrix4f;

import br.fritzen.engine.components.Mesh;
import br.fritzen.engine.components.Model;
import br.fritzen.engine.renderer.Material;
import br.fritzen.engine.renderer.Texture2D;
import lombok.Getter;

public class Plane {

	@Getter
	private Material material;

	private Model mesh;

	@Getter
	private Matrix4f transform;


	public Plane() {

		this.mesh = new Model("models/plane.obj");
		this.material = new Material();
		this.transform = new Matrix4f();

		this.material.setDiffuseTexture(Texture2D.create("textures/wood-floor.png"));
		this.material.setShininess(32f);
	}


	public Mesh getMesh() {
		return this.mesh.getMeshes().get(0).getKey();
	}

}
