package br.fritzen.engine.events.mouse;

import br.fritzen.engine.events.EventType;

public class MouseMovedEvent extends MouseEvent {

	private float posX;
	
	private float posY;
	
	
	public MouseMovedEvent(float x, float y) {
		this.posX = x;
		this.posY = y;
		
	}
	

	@Override
	public EventType getEventType() {
		return EventType.MouseMovedEvent;
	}

	
	public float getPosX() {
		return posX;
	}

	
	public float getPosY() {
		return posY;
	}
	

}
