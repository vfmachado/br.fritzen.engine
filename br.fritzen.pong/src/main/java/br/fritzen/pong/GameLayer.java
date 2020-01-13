package br.fritzen.pong;

import java.util.Random;

import org.joml.Vector2f;
import org.joml.Vector4f;

import br.fritzen.engine.components.OrthographicCamera;
import br.fritzen.engine.core.input.Input;
import br.fritzen.engine.core.layers.Layer;
import br.fritzen.engine.events.Event;
import br.fritzen.engine.events.EventType;
import br.fritzen.engine.events.key.KeyEvent;
import br.fritzen.engine.renderer.RenderCommand;
import br.fritzen.engine.renderer.Renderer2D;

public class GameLayer extends Layer {

	private OrthographicCamera mainCamera;
	
	private Ball ball;
	
	private Player player;
	
	private int lives;
	private Vector2f tmpPos = new Vector2f();
	private Vector2f liveSize = new Vector2f(5, 5);
	private Vector4f liveColor = new Vector4f(0.8f, 0.2f, 0.2f, 0.7f);
	
	private Random random = new Random();
	
	private boolean running = true;
	
	public GameLayer() {
		
		super("MainLayer");
		
		this.lives = 5;
		this.mainCamera = new OrthographicCamera(0, 177, 0, 100);
		this.ball = new Ball(30, 30, 10, 10);
		this.player = new Player(5, 40, 3, 20);
		
		RenderCommand.clearColor(0,  0,  0,  1);
	}

	
	@Override
	public void onAttach() {
		// TODO Auto-generated method stub
		super.onAttach();
	}
	
	@Override
	public void onUpdate(float deltatime) {
		
		if (running) {
			//ball collides player - invert X direction
			if (ball.position.x <= player.position.x + player.size.x/2) {
				
				if (ball.position.y <= player.position.y + player.size.y && 
					ball.position.y + ball.size.y >= player.position.y) {
					ball.direction.x *= - (random.nextFloat() * (0.4) + 0.8);
				}
			}
			
			ball.update(deltatime);
			player.update(deltatime);
			//this.mainCamera.addRotation(deltatime * 0.05f);
			
			if (ball.position.x <= 1) {
				ball.position.x = 100;
				ball.direction.x *= - 1;
				
				lives--;
			}
		}
		//System.out.println("Mouse at: " + Input.getMousePos());
	}
	
	
	@Override
	public void onRender() {
	
		//System.out.println("Mouse at: " + Input.getMousePos());
	
		Renderer2D.beginScene(mainCamera);
		
		this.ball.draw();
		
		this.player.draw();
		
		for (int i = lives; i > 0; i--) {
			
			tmpPos.set(177 - i * 10, 90);
			Renderer2D.drawQuad(tmpPos, liveSize, liveColor);
			
		}
		
		Renderer2D.endScene();
		
		
		//Renderer2D.beginScene(mainCamera);
		
	}
	
	@Override
	public void onEvent(Event e) {
		
		if (e.getEventType() == EventType.KeyReleasedEvent) {
		
			KeyEvent evt = (KeyEvent) e;
			if (evt.getKeyCode() == Input.KEY_SPACE) {
				running = !running;
			}
			
		}
		
	}
	
}
