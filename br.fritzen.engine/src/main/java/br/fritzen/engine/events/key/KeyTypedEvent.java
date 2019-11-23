package br.fritzen.engine.events.key;

import br.fritzen.engine.events.EventType;

public class KeyTypedEvent extends KeyEvent {

	
	public KeyTypedEvent(int keyCode) {
		this.keyCode = keyCode;
	}
	
	
	@Override
	public EventType getEventType() {
		return EventType.KeyTypedEvent;
	}


}
