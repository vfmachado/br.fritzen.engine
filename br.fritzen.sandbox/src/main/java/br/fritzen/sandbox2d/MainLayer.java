package br.fritzen.sandbox2d;

import org.joml.Vector2f;
import org.joml.Vector4f;

import br.fritzen.engine.components.OrthographicCameraController;
import br.fritzen.engine.core.layers.Layer;
import br.fritzen.engine.events.Event;
import br.fritzen.engine.renderer.RenderCommand;
import br.fritzen.engine.renderer.Renderer;


public class MainLayer extends Layer {

	private OrthographicCameraController cameraController;
	
	private Vector2f quadPosition = new Vector2f(0.0f, 0.0f);
	private Vector2f quadSize = new Vector2f(0.5f, 0.5f);
	private Vector4f quadColor = new Vector4f(1, 0, 1, 1);
	
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
		
		Renderer.beginScene(cameraController.getCamera());

		Renderer.drawQuad(quadPosition, quadSize, quadColor);
		
		Renderer.endScene();
	}
	
	
	@Override
	public void onOvent(Event e) {
		
		cameraController.onEvent(e);
		
	}
	

}
