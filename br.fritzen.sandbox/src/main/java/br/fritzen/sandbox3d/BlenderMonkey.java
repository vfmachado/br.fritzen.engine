package br.fritzen.sandbox3d;

import org.joml.Matrix4f;
import org.joml.Vector4f;

import br.fritzen.engine.components.Mesh;
import br.fritzen.engine.components.Model;
import br.fritzen.engine.renderer.Material;
import lombok.Getter;

public class BlenderMonkey {

	private Model mesh;

	@Getter
	private Matrix4f transform;


	public BlenderMonkey() {
		this.mesh = new Model("models/monkey.obj");
		this.transform = new Matrix4f();
		
	}


	public Mesh getMesh() {
		return this.mesh.getMeshes().get(0).getKey();
	}

	
	public Material getMaterial() {
		return Material.BRASS;
	}
}
