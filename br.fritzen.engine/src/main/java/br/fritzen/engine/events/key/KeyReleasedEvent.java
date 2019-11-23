package br.fritzen.engine.events.key;

import br.fritzen.engine.events.EventType;

public class KeyReleasedEvent extends KeyEvent {


	public KeyReleasedEvent(int keyCode) {
		this.keyCode = keyCode;
	}
	
	
	@Override
	public EventType getEventType() {
		return EventType.KeyReleasedEvent;
	}
	
	
}