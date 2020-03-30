package br.fritzen.engine.renderer;

import org.joml.Vector4f;

import lombok.Data;

@Data
public class Material {


	public static final Material blank = new Material();

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
	
	
	public Material(Vector4f ambient, Vector4f diffuse, Vector4f specular, float shininess) {
		
		this.ambientColor = ambient;
		this.diffuseColor = diffuse;
		this.specularColor = specular;
		
		this.diffuseTexture = Texture2D.createBlank();
		
		this.shininess = shininess;
		
		
	}
	
	
	//BRASS FROM http://www.it.hiof.no/~borres/j3d/explain/light/p-materials.html
	public static final Material BRASS = new Material(
			new Vector4f(0.329412f, 0.223529f, 0.027451f, 1.0f), 
			new Vector4f(0.780392f, 0.568627f, 0.113725f, 1.0f), 
			new Vector4f(0.992157f, 0.941176f, 0.807843f, 1.0f),
			27.8974f);

}
