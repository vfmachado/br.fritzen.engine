package Character;

import org.joml.Vector2f;

import br.fritzen.engine.animator.Animator2D;
import br.fritzen.engine.core.input.Input;
import br.fritzen.engine.events.Event;
import br.fritzen.engine.renderer.Texture2D;

public class Player {

	private Animator2D currentAnimator;
	
	private Animator2D running;
	private Animator2D iddle;
	private Animator2D attacking;
	private Animator2D jumping;
	
	private Texture2D textureA = Texture2D.create("textures/tiles/adventurerSheet.png");
	
	private boolean isJumping = false;
	
	private Vector2f position;
	private Vector2f size;
	
	private float direction = 1;
	private float speed = 5f;
	
	public Player(Vector2f position) {
	
		this.position = position;
		this.size = new Vector2f(1.3513f * 4, 1 * 4);
		
		Vector2f[] listOfCoordsIddle = new Vector2f[4];
		for (int i = 0; i < 4; i++) {
			listOfCoordsIddle[i] = new Vector2f(i, 15);
		}
		iddle = new Animator2D("Iddle", textureA, new Vector2f(50, 37), listOfCoordsIddle, 0.3f);
		
		
		Vector2f[] listOfCoordsRunning = new Vector2f[6];
		for (int i = 0; i < 6; i++) {
			listOfCoordsRunning[i] = new Vector2f(1 + i, 14);
		}
		running = new Animator2D("Running", textureA, new Vector2f(50, 37), listOfCoordsRunning, 0.1f);
		
		
		Vector2f[] listOfCoordsAttacking = new Vector2f[17];
		for (int i = 0; i < 17; i++) {
			listOfCoordsAttacking[i] = new Vector2f(i % 7, 9 - (i / 7));
		}
		attacking = new Animator2D("Attacking", textureA, new Vector2f(50, 37), listOfCoordsAttacking, 0.15f);
		
		
		Vector2f[] listOfCoordsJumping = new Vector2f[10];
		for (int i = 0; i < 10; i++) {
			listOfCoordsJumping[i] = new Vector2f(i % 7, 13 - (i / 7));
		}
		jumping = new Animator2D("Jumping", textureA, new Vector2f(50, 37), listOfCoordsJumping, 0.1f);
		
		
		this.currentAnimator = iddle;
		
	}
	
	float totalJump = 1f;
	float jumpingTime = 0;
	public void update(float deltatime) {
		
		this.currentAnimator = iddle;
		
		if (Input.isKeyPressed(Input.KEY_SPACE) || isJumping ) {
			this.currentAnimator = jumping;
			isJumping = true;
			jumpingTime += deltatime;
			
			if (jumpingTime < totalJump/2) {
				position.y += 8 * deltatime;
			} else {
				position.y -= 8 * deltatime;
			}
			
			if (jumpingTime >= totalJump) {
				position.y = -5;	//reseting
				isJumping = false;
				jumpingTime = 0;
			}
		}
		
		if (Input.isKeyPressed(Input.KEY_LEFT)) {
			this.position.x -= speed * deltatime;
			if (!isJumping) this.currentAnimator = running;
			this.direction = -1f;
			
		} else if (Input.isKeyPressed(Input.KEY_RIGHT)) {
			this.position.x += speed * deltatime;
			if (!isJumping) this.currentAnimator = running;
			this.direction = 1f;
			
		}
		this.currentAnimator.update(deltatime);	
	}

	
	public void draw() {
		this.currentAnimator.draw(position, size, direction, 1);
	}

	
	public void onEvent(Event e) {
		
	}
	
}
