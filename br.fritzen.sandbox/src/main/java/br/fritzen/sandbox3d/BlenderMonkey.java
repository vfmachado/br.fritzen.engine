package br.fritzen.sandbox3d;

import org.joml.Matrix4f;
import org.joml.Vector4f;

import br.fritzen.engine.components.Mesh;
import br.fritzen.engine.components.Model;
import br.fritzen.engine.renderer.Material;
import lombok.Getter;

public class BlenderMonkey {

	@Getter
	private Material material;

	private Model mesh;

	@Getter
	private Matrix4f transform;


	public BlenderMonkey() {
		this.mesh = new Model("models/monkey.obj");
		this.material = new Material();
		this.transform = new Matrix4f();
		
		//BRASS FROM http://www.it.hiof.no/~borres/j3d/explain/light/p-materials.html
		this.material.setAmbientColor(new Vector4f(0.329412f, 0.223529f, 0.027451f, 1.0f));
		this.material.setDiffuseColor(new Vector4f(0.780392f, 0.568627f, 0.113725f, 1.0f));
		this.material.setSpecularColor(new Vector4f(0.992157f, 0.941176f, 0.807843f, 1.0f));
		
		this.material.setShininess(27.8974f); //27.8974f / 128 = 0.218f
	}


	public Mesh getMesh() {
		return this.mesh.getMeshes().get(0).getKey();
	}

}
