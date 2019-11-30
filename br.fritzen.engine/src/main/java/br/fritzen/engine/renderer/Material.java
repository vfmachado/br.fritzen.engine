package br.fritzen.engine.renderer;

import org.joml.Vector4f;

import lombok.Data;

@Data
public class Material {

	private Vector4f diffuseColor;
	
	private Texture2D diffuseTexture;
	
}
