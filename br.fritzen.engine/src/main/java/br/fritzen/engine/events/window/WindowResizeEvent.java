package br.fritzen.engine.events.window;

import br.fritzen.engine.events.Event;
import br.fritzen.engine.events.EventCategory;
import br.fritzen.engine.events.EventType;

public class WindowResizeEvent extends Event {

	private int width;
	
	private int height;
	
	
	public WindowResizeEvent(int width, int height) {
		this.width = width;
		this.height = height;
	}
	
	
	@Override
	public EventType getEventType() {
		return EventType.WindowResizeEvent;
	}

	
	@Override
	public EventCategory getEventCategory() {
		return EventCategory.Window;
	}


	public int getWidth() {
		return width;
	}


	public int getHeight() {
		return height;
	}

	
	@Override
	public String toString() {
		return String.format("%s : (%d, %d)", this.getName(), this.width, this.height);
	}
	
}
