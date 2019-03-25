package br.fritzen.engine.events.key;

import br.fritzen.engine.events.Event;
import br.fritzen.engine.events.EventCategory;

public abstract class KeyEvent extends Event {

	protected int keycode;
	
	
	@Override
	public EventCategory getEventCategory() {
		return EventCategory.Keyboard;
	}

	
	public int getKeyCode() {
		return this.keycode;
	}
	
	
	@Override
	public String getName() {
		return getEventType().toString() + ": " + this.keycode;
	}
}
