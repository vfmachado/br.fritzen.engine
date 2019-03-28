package br.fritzen.engine.events.mouse;

import br.fritzen.engine.events.EventType;

public class MouseButtonPressedEvent extends MouseEvent {

	private int button;
	
	public MouseButtonPressedEvent(int button) {
		this.button = button;
	}
	
	@Override
	public EventType getEventType() {
		return EventType.MouseButtonPressedEvent;
	}

	
	public int getButton() {
		return this.button;
	}
	
	
}
