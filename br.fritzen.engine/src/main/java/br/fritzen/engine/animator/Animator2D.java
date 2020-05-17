package br.fritzen.engine.animator;

import org.joml.Vector2f;

import br.fritzen.engine.renderer.Renderer2D;
import br.fritzen.engine.renderer.SubTexture2D;
import br.fritzen.engine.renderer.Texture2D;

public class Animator2D {

	private String name;
	
	private Texture2D spriteSheet;
	
	private Vector2f spriteSize;
	
	private Vector2f[] listOfCoords;
	
	private float timePerFrame;	
	
	private int currentCoord;
	
	private float amountTime;
	
	private SubTexture2D currentTexture;
	
	
	public Animator2D(String name, Texture2D spriteSheet, Vector2f spriteSize, Vector2f[] listOfCoords, float timePerFrame) {
		
		this.name = name;
		this.spriteSheet = spriteSheet;
		this.spriteSize = spriteSize;
		this.listOfCoords = listOfCoords;
		this.timePerFrame = timePerFrame;
		
		
		this.currentCoord = 0;
		this.amountTime = 0;
		
		this.currentTexture = SubTexture2D.CreateFromCoords(this.spriteSheet, listOfCoords[0], spriteSize);
		
	}


	public void draw(Vector2f position, Vector2f size) {
		draw(position, size, 1, 1);
	}
	
	private Vector2f tmpSize = new Vector2f();
	public void draw(Vector2f position, Vector2f size, float directionX, float directionY) {
		Renderer2D.drawQuad(position, tmpSize.set(size).mul(directionX, directionY), this.currentTexture);
	}
	
		
	public void update(float deltatime) {
		
		amountTime += deltatime;
		
		if (amountTime >= timePerFrame) {
			currentCoord++;
			
			if (currentCoord == listOfCoords.length) {
				currentCoord = 0;
			}
			
			this.currentTexture.updateCoords(listOfCoords[currentCoord], spriteSize);
			
			amountTime = 0;
		}
		
	}
}


