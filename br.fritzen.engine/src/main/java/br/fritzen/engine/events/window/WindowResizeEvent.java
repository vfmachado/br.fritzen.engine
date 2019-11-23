package br.fritzen.engine.events.window;

import br.fritzen.engine.events.Event;
import br.fritzen.engine.events.EventCategory;
import br.fritzen.engine.events.EventType;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class WindowResizeEvent extends Event {

	@Getter
	private int width;
	
	@Getter
	private int height;
	

	@Override
	public EventType getEventType() {
		return EventType.WindowResizeEvent;
	}

	
	@Override
	public EventCategory getEventCategory() {
		return EventCategory.Window;
	}


	@Override
	public String toString() {
		return String.format("%s : (%d, %d)", this.getName(), this.width, this.height);
	}
	
}
