package br.fritzen.pong;

import br.fritzen.engine.core.input.Input;
import br.fritzen.engine.core.layers.Layer;
import br.fritzen.engine.events.Event;
import br.fritzen.engine.events.EventType;

public class GameLayer extends Layer {

	private Ball ball;
	
	public GameLayer() {
		
		super("MainLayer");
		
		ball = new Ball();
		
	}

	
	@Override
	public void onUpdate() {
		//System.out.println("Mouse at: " + Input.getMousePos());
	}
	
	
	@Override
	public void onRender() {
		//System.out.println("Mouse at: " + Input.getMousePos());
				
		ball.draw();
	}
	
	@Override
	public void onOvent(Event e) {
		
		if (e.getEventType() == EventType.MouseMovedEvent) {
			System.out.println(Input.getMousePos());
		}
		
	}
	
}
