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


public class MainLayer extends Layer {

	private OrthographicCameraController cameraController;
	
	private Vector2f quadPosition = new Vector2f(-0.5f, -0.5f);
	private Vector2f quadSize = new Vector2f(1f, 1f);
	private Vector4f quadColor = new Vector4f(0.8f, 0.2f, 0.3f, 1);
	
	private Vector3f backgroundPosition = new Vector3f(-10.0f, -10.0f, -0.5f);
	private Vector2f backgroundSize = new Vector2f(20f, 20f);
	private Texture2D backgroundTexture = Texture2D.create("textures/default.png");
	
	private int textureRepeats = 1;
	
	public MainLayer() {
		
		super("MainLayer");
		
		cameraController = new OrthographicCameraController(1280f/720f);
		
		RenderCommand.clearColor(0,  0,  0,  1);
	}

	
	@Override
	public void onUpdate(float deltatime) {
		
		cameraController.onUpdate(deltatime);

	}
	
	
	@Override
	public void onRender() {
	
		RenderCommand.clear();
		
		
		Renderer2D.beginScene(cameraController.getCamera());

		
		Renderer2D.drawQuad(quadPosition, quadSize, quadColor);
		
		Renderer2D.setTextureRepeats(textureRepeats);
		Renderer2D.drawQuad(backgroundPosition, backgroundSize, backgroundTexture);
		
		Renderer2D.setTextureRepeats(1);
		
		
		Renderer2D.endScene();
		
	}
	
	
	@Override
	public void onOvent(Event e) {
		
		if (e.getEventType() == EventType.KeyReleasedEvent) {
			KeyEvent evt = (KeyEvent) e;
			if (evt.getKeyCode() == Input.KEY_KP_ADD) {
				textureRepeats++;
			} else if (evt.getKeyCode() == Input.KEY_KP_SUBTRACT) {
				textureRepeats--;
			}
		}
		
		cameraController.onEvent(e);
		
	}
	

}
