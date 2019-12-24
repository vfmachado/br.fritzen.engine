package br.fritzen.engine.renderer;

import org.joml.Vector4f;

import lombok.Data;

@Data
public class Material {


	private Vector4f ambientColor;
	
	private Vector4f diffuseColor;
	
	private Vector4f specularColor;
	
	private Texture2D diffuseTexture;
	
	private float shininess;
	
	public Material() {
		
		this.ambientColor = new Vector4f(1);
		this.diffuseColor = new Vector4f(1);
		this.specularColor = new Vector4f(1);
		
		this.diffuseTexture = Texture2D.createBlank();
		
		this.shininess = 1f;
		
	}
	
}
