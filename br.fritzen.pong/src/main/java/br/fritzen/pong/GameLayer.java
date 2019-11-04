package br.fritzen.pong;

import br.fritzen.engine.components.OrthographicCamera;
import br.fritzen.engine.core.layers.Layer;
import br.fritzen.engine.events.Event;
import br.fritzen.engine.events.EventType;
import br.fritzen.engine.renderer.RenderCommand;
import br.fritzen.engine.renderer.Renderer;
import br.fritzen.engine.renderer.Renderer2D;

public class GameLayer extends Layer {

	private OrthographicCamera mainCamera;
	
	private Ball ball;
	
	private Player player;
		
	
	public GameLayer() {
		
		super("MainLayer");
		
		this.mainCamera = new OrthographicCamera(0, 100, 0, 100);
		this.ball = new Ball(40, 40, 10/1.77f, 10);
		this.player = new Player(0, 40, 2, 20);
		
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
		player.update(deltatime);
		//this.mainCamera.addRotation(deltatime * 0.05f);
		
		//System.out.println("Mouse at: " + Input.getMousePos());
	}
	
	
	@Override
	public void onRender() {
	
		//System.out.println("Mouse at: " + Input.getMousePos());
	
		Renderer2D.beginScene(mainCamera);
		
		this.ball.draw();
		
		this.player.draw();
		
		Renderer2D.endScene();
		
		
		//Renderer2D.beginScene(mainCamera);
		
	}
	
	@Override
	public void onOvent(Event e) {
		
		if (e.getEventType() == EventType.MouseMovedEvent) {
		//	System.out.println(Input.getMousePos());
		}
		
	}
	
}
