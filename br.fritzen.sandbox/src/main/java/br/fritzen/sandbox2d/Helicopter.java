package br.fritzen.sandbox2d;

import java.util.ArrayList;
import java.util.List;

import org.joml.Vector2f;

import br.fritzen.engine.renderer.Renderer2D;
import br.fritzen.engine.renderer.Texture2D;

public class Helicopter {

	private List<Texture2D> textures;
	private Texture2D currentTexture;
	
	private Vector2f position;
	private Vector2f size;
	
	private int textIndex;
	private float time = 0;
	
	public Helicopter() {
		
		position = new Vector2f(-10, 0);
		size = new Vector2f(4, 1.42f);
		
		textures = new ArrayList<Texture2D>();
		
		for (int i = 1; i <=4 ; i++) {
			textures.add(Texture2D.create("heli-" + i + ".png"));
		}
		
		currentTexture = textures.get(0);
		textIndex = 0;
	}
			
	
	public void onUpdate(float deltatime) {
		
		time += deltatime;
		
		if (time >= 0.1f) {
			textIndex = ++textIndex % 4;
			time = 0;
		}
		
		currentTexture = textures.get(textIndex);
		
	}
			
	
	public void onRender() {
		
		Renderer2D.drawQuad(position, size, currentTexture);
		
	}
}
