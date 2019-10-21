package br.fritzen.pong;

import br.fritzen.engine.components.OrthographicCamera;
import br.fritzen.engine.core.layers.Layer;
import br.fritzen.engine.events.Event;
import br.fritzen.engine.events.EventType;
import br.fritzen.engine.renderer.RenderCommand;
import br.fritzen.engine.renderer.Renderer;

public class GameLayer extends Layer {

	private Ball ball;
	
	private OrthographicCamera mainCamera;
	
	public GameLayer() {
		
		super("MainLayer");
		
		mainCamera = new OrthographicCamera(-1.7778f, 1.7778f, -1, 1);
		ball = new Ball();
		
		RenderCommand.clearColor(0,  0,  0,  1);
	}

	
	@Override
	public void onAttach() {
		// TODO Auto-generated method stub
		super.onAttach();
	}
	
	@Override
	public void onUpdate(float deltatime) {
		
		ball.update(deltatime);
		
		//this.mainCamera.addRotation(deltatime * 0.05f);
		
		//System.out.println("Mouse at: " + Input.getMousePos());
	}
	
	
	@Override
	public void onRender() {
	
		//System.out.println("Mouse at: " + Input.getMousePos());
	
		Renderer.beginScene(mainCamera);

		
		ball.draw();
		
		
		Renderer.endScene();
	}
	
	@Override
	public void onOvent(Event e) {
		
		if (e.getEventType() == EventType.MouseMovedEvent) {
		//	System.out.println(Input.getMousePos());
		}
		
	}
	
}
