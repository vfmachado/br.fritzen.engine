package br.fritzen.engine.events.mouse;

import br.fritzen.engine.events.EventType;

public class MouseButtonReleasedEvent extends MouseEvent {

	private int button;
	
	public MouseButtonReleasedEvent(int button) {
		this.button = button;
	}
	
	@Override
	public EventType getEventType() {
		return EventType.MouseButtonReleasedEvent;
	}

	
	public int getButton() {
		return this.button;
	}
	
	
}
