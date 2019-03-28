package br.fritzen.engine.events.mouse;

import br.fritzen.engine.events.Event;
import br.fritzen.engine.events.EventCategory;

public abstract class MouseEvent extends Event {

	
	@Override
	public EventCategory getEventCategory() {
		return EventCategory.Mouse;
	}
	
	
	public String toString() {
		return this.getEventCategory() + ":  " + this.getEventType();
	}
	
	public static final int 
		BUTTON_1 = 0,
		BUTTON_2 = 1,
		BUTTON_3 = 2,
		BUTTON_4 = 3,
		BUTTON_5 = 4,
		BUTTON_6 = 5,
		BUTTON_7 = 6,
		BUTTON_8 = 7,
		BUTTON_LAST = BUTTON_8,
		BUTTON_LEFT = BUTTON_1,
		BUTTON_RIGHT = BUTTON_2,
		BUTTON_MIDDLE = BUTTON_3
		;
	
}
