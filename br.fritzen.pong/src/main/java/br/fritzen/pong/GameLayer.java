package br.fritzen.pong;

import br.fritzen.engine.components.OrthographicCamera;
import br.fritzen.engine.core.input.Input;
import br.fritzen.engine.core.layers.Layer;
import br.fritzen.engine.events.Event;
import br.fritzen.engine.events.EventType;
import br.fritzen.engine.renderer.Renderer;

public class GameLayer extends Layer {

	private Ball ball;
	
	private OrthographicCamera mainCamera;
	
	public GameLayer() {
		
		super("MainLayer");
		
		mainCamera = new OrthographicCamera(-2, 2, -1, 1);
		ball = new Ball();
		
	}

	
	@Override
	public void onUpdate(float deltatime) {
		
		//this.mainCamera.addRotation(deltatime * 0.05f);
		
		//System.out.println("Mouse at: " + Input.getMousePos());
	}
	
	
	@Override
	public void onRender() {
	
		//System.out.println("Mouse at: " + Input.getMousePos());
	
		Renderer.BeginScene(mainCamera);

		
		ball.draw();
		
		
		Renderer.EndScene();
	}
	
	@Override
	public void onOvent(Event e) {
		
		if (e.getEventType() == EventType.MouseMovedEvent) {
		//	System.out.println(Input.getMousePos());
		}
		
	}
	
}
