package br.fritzen.engine.events;

public abstract class Event {

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
	
	public void setHandled(boolean handled) {
		this.handled = handled;;
	}
	
	public boolean isHandled() {
		return this.handled;
	}
	
	
}
