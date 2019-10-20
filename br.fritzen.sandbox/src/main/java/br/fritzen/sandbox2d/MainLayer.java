package br.fritzen.sandbox2d;

import br.fritzen.engine.components.OrthographicCameraController;
import br.fritzen.engine.core.layers.Layer;
import br.fritzen.engine.events.Event;
import br.fritzen.engine.renderer.RenderCommand;
import br.fritzen.engine.renderer.Renderer;


public class MainLayer extends Layer {

	private OrthographicCameraController cameraController;
	
	public MainLayer() {
		
		super("MainLayer");
		
		cameraController = new OrthographicCameraController(1280f/720f);
		
		Renderer.get().clearColor(0,  0,  0,  1);
	}

	
	@Override
	public void onUpdate(float deltatime) {
		
		cameraController.onUpdate(deltatime);
	}
	
	
	@Override
	public void onRender() {
	
		//System.out.println("Mouse at: " + Input.getMousePos());
	
		//make this static
		RenderCommand.clear();
		
		Renderer.beginScene(cameraController.getCamera());

		//Hazel::Renderer2D::DrawQuad({ 0.0f, 0.0f }, { 1.0f, 1.0f }, { 0.8f, 0.2f, 0.3f, 1.0f });
		
		
		Renderer.endScene();
	}
	
	@Override
	public void onOvent(Event e) {
		
		cameraController.onEvent(e);
		
	}
	

}
