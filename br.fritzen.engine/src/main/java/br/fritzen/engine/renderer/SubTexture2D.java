package br.fritzen.engine.renderer;

import org.joml.Vector2f;

import lombok.Getter;

public class SubTexture2D {

	@Getter
	private Vector2f[] texCoords = new Vector2f[4];
	
	@Getter
	private Texture texture;
	
	public SubTexture2D(Texture texture, Vector2f min, Vector2f max) {
		
		this.texture = texture;
		
		texCoords[0] = new Vector2f(min.x, min.y);
		texCoords[1] = new Vector2f(max.x, min.y);
		texCoords[2] = new Vector2f(max.x, max.y);
		texCoords[3] = new Vector2f(min.x, max.y);
		
	}
	
	
	public static SubTexture2D CreateFromCoords(Texture texture, Vector2f coords, Vector2f spriteSize) {
		
		Vector2f min = new Vector2f(coords.x * spriteSize.x / texture.getWidth(), coords.y * spriteSize.y / texture.getHeight());
		Vector2f max = new Vector2f((coords.x + 1) * spriteSize.x / texture.getWidth(), (coords.y + 1) * spriteSize.y / texture.getHeight());
		return new SubTexture2D(texture, min, max);
	}
	
	
	public void updateCoords(Vector2f coords, Vector2f spriteSize) {
		
		Vector2f min = new Vector2f(coords.x * spriteSize.x / texture.getWidth(), coords.y * spriteSize.y / texture.getHeight());
		Vector2f max = new Vector2f((coords.x + 1) * spriteSize.x / texture.getWidth(), (coords.y + 1) * spriteSize.y / texture.getHeight());
		
		texCoords[0] = new Vector2f(min.x, min.y);
		texCoords[1] = new Vector2f(max.x, min.y);
		texCoords[2] = new Vector2f(max.x, max.y);
		texCoords[3] = new Vector2f(min.x, max.y);
		
	}
}
