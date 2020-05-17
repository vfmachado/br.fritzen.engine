package Character;

import org.joml.Vector2f;

import br.fritzen.engine.animator.Animator2D;
import br.fritzen.engine.renderer.Texture2D;

public class Player {

	private Animator2D currentAnimator;
	
	private Animator2D running;
	private Animator2D iddle;
	private Animator2D attacking;
	
	private Texture2D textureA = Texture2D.create("textures/tiles/adventurerSheet.png");
	
	private Vector2f position;
	private Vector2f size;
	
	public Player(Vector2f position) {
	
		this.position = position;
		this.size = new Vector2f(1.3513f * 4, 1 * 4);
		
		Vector2f[] listOfCoordsIddle = new Vector2f[4];
		for (int i = 0; i < 4; i++) {
			listOfCoordsIddle[i] = new Vector2f(i, 15);
		}
		iddle = new Animator2D("Iddle", textureA, new Vector2f(50, 37), listOfCoordsIddle, 0.5f);
		
		
		Vector2f[] listOfCoordsRunning = new Vector2f[6];
		for (int i = 0; i < 6; i++) {
			listOfCoordsRunning[i] = new Vector2f(1 + i, 14);
		}
		running = new Animator2D("Running", textureA, new Vector2f(50, 37), listOfCoordsRunning, 0.25f);
		
		
		Vector2f[] listOfCoordsAttacking = new Vector2f[17];
		for (int i = 0; i < 17; i++) {
			listOfCoordsAttacking[i] = new Vector2f(i % 7, 9 - (i / 7));
		}
		attacking = new Animator2D("Attacking", textureA, new Vector2f(50, 37), listOfCoordsAttacking, 0.15f);
		
		
		this.currentAnimator = iddle;
		
	}
	
	public void update(float deltatime) {
		
		this.currentAnimator.update(deltatime);
		
	}

	public void draw() {
		this.currentAnimator.draw(position, size);
	}

	
	
	
}
