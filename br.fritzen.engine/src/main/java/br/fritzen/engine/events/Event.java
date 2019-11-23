package br.fritzen.engine.events;

import lombok.Getter;
import lombok.Setter;

public abstract class Event {

	@Getter
	@Setter
	private boolean handled = false;
	
	public abstract EventType getEventType();
	
	public abstract EventCategory getEventCategory();
	
	
	public String getName() {
		return getEventType().toString();
	}

	
	@Override
	public String toString() {
		return this.getName();
	}
	
}
