package br.fritzen.pong;

import org.joml.Vector2f;
import org.joml.Vector4f;

import br.fritzen.engine.core.input.Input;
import br.fritzen.engine.renderer.Renderer2D;

public class Player {

	public Vector2f position;
	
	public Vector2f size;
	
	private Vector4f color;
	
	private float speed;
	
	
	public Player(float x, float y, float sx, float sy) {
	
		this.position = new Vector2f(x, y);
		this.size = new Vector2f(sx, sy);
		this.color = new Vector4f(0.2f, 0.3f, 0.8f, 1.0f);
		
		this.speed = 40f;
	}
	
	
	public void draw() {
		
		Renderer2D.drawQuad(position, size, color);
		
	}


	public void update(float deltatime) {
		
		if (Input.isKeyPressed(Input.KEY_W)) {
			
			this.position.y += speed * deltatime;
			if (this.position.y + this.size.y/2 > 100) {
				this.position.y = 100 - this.size.y/2;
			}
			
		} else if (Input.isKeyPressed(Input.KEY_S)) {
			
			this.position.y -= speed * deltatime;
			if (this.position.y < this.size.y/2) {
				this.position.y = this.size.y/2;
			}
		}
		
	}
	
}
