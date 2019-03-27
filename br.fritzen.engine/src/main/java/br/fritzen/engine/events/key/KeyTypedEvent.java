package br.fritzen.engine.events.key;

import br.fritzen.engine.events.EventType;

public class KeyTypedEvent extends KeyEvent {

	
	public KeyTypedEvent(int keycode) {
		this.keycode = keycode;
	}
	
	
	@Override
	public EventType getEventType() {
		return EventType.KeyTypedEvent;
	}


}
