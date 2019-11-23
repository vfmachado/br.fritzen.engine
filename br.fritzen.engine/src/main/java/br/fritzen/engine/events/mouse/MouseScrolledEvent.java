package br.fritzen.engine.events.mouse;

import br.fritzen.engine.events.EventType;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class MouseScrolledEvent extends MouseEvent {

	@Getter
	private float xOffset;
	
	@Getter
	private float yOffset;
	
	
	@Override
	public EventType getEventType() {
		return EventType.MouseScrolledEvent;
	}

}
