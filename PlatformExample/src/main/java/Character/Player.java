package Character;

import org.joml.Vector2f;

import br.fritzen.engine.animator.Animator2D;
import br.fritzen.engine.core.input.Input;
import br.fritzen.engine.events.Event;
import br.fritzen.engine.events.EventCategory;
import br.fritzen.engine.events.EventType;
import br.fritzen.engine.events.key.KeyEvent;
import br.fritzen.engine.events.key.KeyPressedEvent;
import br.fritzen.engine.renderer.Texture2D;
import core.StateGraph;
import core.StateNode;


public class Player {

	private StateGraph stateGraph;
	
	private Animator2D running;
	private Animator2D iddle;
	private Animator2D attacking;
	private Animator2D jumping;
	
	private Texture2D textureA = Texture2D.create("textures/tiles/adventurerSheet.png");
	
	private Vector2f position;
	private Vector2f size;
	
	private float direction = 1;
	private float speed = 0f;
	private float defaultSpeed = 8f;
	
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
		attacking = new Animator2D("Attacking", textureA, new Vector2f(50, 37), listOfCoordsAttacking, 0.1f);
		
		
		Vector2f[] listOfCoordsJumping = new Vector2f[10];
		for (int i = 0; i < 10; i++) {
			listOfCoordsJumping[i] = new Vector2f(i % 7, 13 - (i / 7));
		}
		jumping = new Animator2D("Jumping", textureA, new Vector2f(50, 37), listOfCoordsJumping, 0.15f);
		
		
		AttackA attackingState = new AttackA("AttackA", attacking);
		
		StateNode iddleState = new StateNode("IDDLE", iddle) {
			@Override
			public void onUpdate(float deltatime) {
				this.getAnimation().update(deltatime);
			}
		};
		
		StateNode runningState = new StateNode("RUNNING", running) {
			
			@Override
			public void onTrigger() {
				super.onTrigger();			
			}
		
			@Override
			public void onUpdate(float deltatime) {
				this.getAnimation().update(deltatime);
				
				if (Input.isKeyPressed(Input.KEY_LEFT)) {
					direction = -1f;
					speed = defaultSpeed;
				}
				else if (Input.isKeyPressed(Input.KEY_RIGHT)) {
					direction = 1;
					speed = defaultSpeed;
				}
				else { 
					stateGraph.trigger("ARROW RELEASED");
					speed = 0;
				}
			}
		};

		StateNode jumpingState = new StateNode("JUMPING", jumping) {
			
			float maxUpTime = 1.0f;
			float totalUpTime = 0;
			float initialY;
			float falling = 1;
			float jumpForce;
			
			boolean available = true;
			boolean fallingControl = false;
			
			@Override
			public void onTrigger() {
				super.onTrigger();
				
				initialY = position.y;
				totalUpTime = 0;
				jumpForce = 30;
				fallingControl = false;
				
				this.getAnimation().restart();
				
				if (!available) {
					stateGraph.setInitial(stateGraph.getLastNode());
					//stateGraph.getCurrentNode().onTrigger();
				}
				else
					available = false;
			}
		
			@Override
			public void onUpdate(float deltatime) {
				
				this.getAnimation().update(deltatime);
				
				//allow change the direction in the middle of jump
				if (Input.isKeyPressed(Input.KEY_LEFT)) {
					direction = -1f;
					speed = defaultSpeed + 0;
				}
				else if (Input.isKeyPressed(Input.KEY_RIGHT)) {
					direction = 1;
					speed = defaultSpeed + 0;
				}
				else { 
					stateGraph.trigger("ARROW RELEASED");
					speed = 0;
				}
				
							
				if (Input.isKeyPressed(Input.KEY_UP) && totalUpTime < maxUpTime && !fallingControl) {
					
					totalUpTime += deltatime;
					position.y += jumpForce  * deltatime;
					falling = 1;
					jumpForce *= 0.9f;
					
				} else {
					
					//FALLING
					fallingControl = true;
					
					float deltaY= (speed + falling) * deltatime;
					falling++;
					
					if (deltaY < position.y - initialY) {
						position.y  -= deltaY;
						
					//END JUMP
					} else {
						position.y = initialY;
						available = true;
						stateGraph.finishState();
					}
				}
				
			}
		};

		stateGraph = new StateGraph();
		stateGraph.setInitial(iddleState);

		//map of relations on state graph
		stateGraph.addPath(iddleState, runningState, "ARROW LR");
		stateGraph.addPath(runningState, iddleState, "ARROW RELEASED");
		
		stateGraph.addPath(iddleState, jumpingState, "UP");
		stateGraph.addPath(runningState, jumpingState, "UP");
		
		stateGraph.addPath(jumpingState, iddleState, "FINISH JUMP");
		
		//map attack
		stateGraph.addPath(iddleState, attackingState, "ATTACK");
		stateGraph.addPath(runningState, attackingState, "ATTACK");
		
	}
	
	
	public void update(float deltatime) {
		
		this.stateGraph.getCurrentNode().onUpdate(deltatime);
		position.x += direction * speed * deltatime;
	}

	
	public void draw() {
		//this.currentAnimator.draw(position, size, direction, 1);
		this.stateGraph.getCurrentNode().getAnimation().draw(position, size, direction, 1);
	}

	
	public void onEvent(Event e) {
		
		if (e.getEventCategory() == EventCategory.Keyboard) {
			
			KeyEvent ke = (KeyEvent) e;
			
			if (ke.getEventType() == EventType.KeyPressedEvent) {
				
				if (ke.getKeyCode() == Input.KEY_LEFT || ke.getKeyCode() == Input.KEY_RIGHT)
					this.stateGraph.trigger("ARROW LR");
				
				if (ke.getKeyCode() == Input.KEY_UP && ((KeyPressedEvent)ke).getRepeatCount() == 0) {
					this.stateGraph.trigger("UP");
				}
				
				if (ke.getKeyCode() == Input.KEY_SPACE && ((KeyPressedEvent)ke).getRepeatCount() == 0) {
					this.stateGraph.trigger("ATTACK");
				}
			}
		}
		
	}
	
}
