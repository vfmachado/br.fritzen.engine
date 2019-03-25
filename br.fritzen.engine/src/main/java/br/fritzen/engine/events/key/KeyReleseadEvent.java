package br.fritzen.engine.events.key;

import br.fritzen.engine.events.EventType;

public class KeyReleseadEvent extends KeyEvent {


	public KeyReleseadEvent(int keycode) {
		this.keycode = keycode;
	}
	
	
	@Override
	public EventType getEventType() {
		return EventType.KeyReleased;
	}
	
	
}