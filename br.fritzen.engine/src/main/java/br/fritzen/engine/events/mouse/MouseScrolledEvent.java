package br.fritzen.engine.events.mouse;

import br.fritzen.engine.events.EventType;

public class MouseScrolledEvent extends MouseEvent {

	private float xOffset;
	
	private float yOffset;
	
	
	public MouseScrolledEvent(float xOffset, float yOffset) {
		this.xOffset = xOffset;
		this.yOffset = yOffset;
	}
	
	
	@Override
	public EventType getEventType() {
		return EventType.MouseScrolledEvent;
	}

	
	public float getXOffset() {
		return this.xOffset;
	}
	
	
	public float getYOffset() {
		return this.yOffset;
	}
}
