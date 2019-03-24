package br.fritzen.engine.events;

public abstract class Event {

	
	private boolean handled = false;
	
	public abstract EventType getEventType();
	
	public abstract String getName();
	
	public abstract EventCategory getEventCategory();
	
	public String toString() {
		return this.getName();
	}
	

}
