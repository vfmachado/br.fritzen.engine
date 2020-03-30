package br.fritzen.engine.components;

import org.joml.Matrix4f;
import org.joml.Vector4f;

import br.fritzen.engine.renderer.Material;
import br.fritzen.engine.renderer.Texture2D;
import lombok.Getter;

public class Skybox {

	@Getter
	private Texture2D texture;
	
	@Getter
	private Matrix4f transform;
	
	@Getter
	private Mesh mesh;
	
	@Getter
	private Material material;
	
	public Skybox(Mesh mesh, Texture2D texture, float size) {
		
		this.texture = texture;
		this.mesh = mesh;
		this.transform = new Matrix4f().scale(size);
		this.material = new Material(new Vector4f(1), new Vector4f(1), new Vector4f(0), 0);
	}
	
	
}
