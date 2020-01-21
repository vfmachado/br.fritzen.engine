package br.fritzen.sandbox2d;

import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;

import br.fritzen.engine.components.OrthographicCameraController;
import br.fritzen.engine.core.input.Input;
import br.fritzen.engine.core.layers.Layer;
import br.fritzen.engine.events.Event;
import br.fritzen.engine.events.EventType;
import br.fritzen.engine.events.key.KeyEvent;
import br.fritzen.engine.renderer.RenderCommand;
import br.fritzen.engine.renderer.Renderer2D;
import br.fritzen.engine.renderer.Texture2D;
import br.fritzen.sandbox2d.GameController.Direction;


public class MainLayer extends Layer {

	private OrthographicCameraController cameraController;
	
	private Helicopter helicopter = new Helicopter();
	
	private Vector2f quadDir = new Vector2f(0, 1);
	private Vector2f tmp_quadDir = new Vector2f(0, 1);
	
	private Vector2f quadPosition = new Vector2f(-0.5f, -0.5f);
	private Vector2f quadSize = new Vector2f(5f, 5f);
	private Vector4f quadColor = new Vector4f(0.8f, 0.2f, 0.3f, 1);
	
	private Texture2D testTexture = Texture2D.create("textures/grass_side.png");
	
	private Texture2D blockTexture = Texture2D.create("block.png");
	
	private Vector3f backgroundPosition = new Vector3f(0.0f, 0.0f, -0.5f);
	private Vector2f backgroundSize = new Vector2f(20f, 20f);
	private Texture2D backgroundTexture = Texture2D.create("textures/default.png");
	
	private Vector4f grayColor = new Vector4f(0.5f);
	
	private float tillingFactor = 2;
	
	private GameController controller;
	
	
	public MainLayer() {
		
		super("MainLayer");
		
		cameraController = new OrthographicCameraController(1280f/720f, true);
		
		controller = new GameController(Direction.DOWN);
			
		RenderCommand.clearColor(0,  0,  0,  1);
		
	}

	float time = 0;
	float speed = 5;
	float rotationSpeed = 100;
	float angle = 0;
	
	
	
	@Override
	public void onUpdate(float deltatime) {
		
		helicopter.onUpdate(deltatime);
		
		cameraController.onUpdate(deltatime);

		time += deltatime;
		
		quadDir.normalize( deltatime > 0 ? speed * deltatime : 1);
		
		if (Input.isKeyPressed(KeyEvent.KEY_UP)) {
			quadPosition.add(quadDir);
			helicopter.up();
		} else {
			
		}
		
		if (Input.isKeyPressed(KeyEvent.KEY_DOWN)) {
			quadPosition.sub(quadDir);
			helicopter.down();
		}
		
		if (Input.isKeyPressed(KeyEvent.KEY_RIGHT)) {
			angle -= rotationSpeed * deltatime;
	
			//tmp_quadDir.set(quadDir);
			quadDir.x = (float) (tmp_quadDir.x * Math.cos(Math.toRadians(angle)) - tmp_quadDir.y * Math.sin(Math.toRadians(angle)));
			quadDir.y = (float) (tmp_quadDir.x * Math.sin(Math.toRadians(angle)) + tmp_quadDir.y * Math.cos(Math.toRadians(angle)));
			
		}
		
		if (Input.isKeyPressed(KeyEvent.KEY_LEFT)) {
			angle += rotationSpeed * deltatime;
			
			//tmp_quadDir.set(quadDir);
			quadDir.x = (float) (tmp_quadDir.x * Math.cos(Math.toRadians(angle)) - tmp_quadDir.y * Math.sin(Math.toRadians(angle)));
			quadDir.y = (float) (tmp_quadDir.x * Math.sin(Math.toRadians(angle)) + tmp_quadDir.y * Math.cos(Math.toRadians(angle)));
		}
		
		
	}
	
	
	@Override
	public void onRender() {
	
		RenderCommand.clear();
		
		Renderer2D.beginScene(cameraController.getCamera());

		Renderer2D.setTillingFactor(tillingFactor);
		Renderer2D.drawQuad(backgroundPosition, backgroundSize, backgroundTexture, grayColor);
		
		Renderer2D.setTillingFactor(1);
		
		Renderer2D.drawRotatedQuad(quadPosition, quadSize, angle, testTexture);
		
		
		helicopter.onRender();
		
		Renderer2D.endScene();
		
	}
	
	
	@Override
	public void onEvent(Event e) {
		
		if (e.getEventType() == EventType.KeyReleasedEvent) {
			KeyEvent evt = (KeyEvent) e;
			if (evt.getKeyCode() == Input.KEY_EQUAL) {
				tillingFactor += 0.5f;
			} else if (evt.getKeyCode() == Input.KEY_MINUS) {
				tillingFactor -= 0.5f;
			}
		}
		
		cameraController.onEvent(e);
		
		controller.onEvent(e);
		
	}
	
}
