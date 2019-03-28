package br.fritzen.engine.events.window;

import br.fritzen.engine.events.Event;
import br.fritzen.engine.events.EventCategory;
import br.fritzen.engine.events.EventType;

public class WindowCloseEvent extends Event {

	@Override
	public EventType getEventType() {
		return EventType.WindowCloseEvent;
	}

	
	@Override
	public EventCategory getEventCategory() {
		return EventCategory.Window;
	}
	
	
}
