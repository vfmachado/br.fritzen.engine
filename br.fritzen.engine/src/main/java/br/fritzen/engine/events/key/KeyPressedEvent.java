package br.fritzen.engine.events.key;

import br.fritzen.engine.events.EventType;

public class KeyPressedEvent extends KeyEvent {

	protected int repeatCount;
	
	
	public KeyPressedEvent(int keycode, int repeatCount) {
	
		this.keycode = keycode;
		this.repeatCount = repeatCount;
		
	}
	
	
	@Override
	public EventType getEventType() {
		return EventType.KeyPressedEvent;
	}


	public int getRepeatCount() {
		return this.repeatCount;
	}
	
	
}
