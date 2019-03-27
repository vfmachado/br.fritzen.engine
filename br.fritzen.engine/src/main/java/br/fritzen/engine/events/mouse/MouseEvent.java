package br.fritzen.engine.events.mouse;

import br.fritzen.engine.events.Event;
import br.fritzen.engine.events.EventCategory;

public abstract class MouseEvent extends Event {

	
	@Override
	public EventCategory getEventCategory() {
		return EventCategory.Mouse;
	}
	
}
