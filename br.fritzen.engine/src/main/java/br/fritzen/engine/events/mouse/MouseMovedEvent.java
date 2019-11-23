package br.fritzen.engine.events.mouse;

import br.fritzen.engine.events.EventType;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;


@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class MouseMovedEvent extends MouseEvent {

	@Getter
	private float posX;
	
	@Getter
	private float posY;
	

	@Override
	public EventType getEventType() {
		return EventType.MouseMovedEvent;
	}


}
