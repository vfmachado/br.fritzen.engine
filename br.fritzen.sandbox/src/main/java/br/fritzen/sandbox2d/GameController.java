package br.fritzen.sandbox2d;

import br.fritzen.engine.core.input.Input;
import br.fritzen.engine.events.Event;
import br.fritzen.engine.events.EventType;
import br.fritzen.engine.events.key.KeyEvent;


public class GameController {

	public enum Direction {
		RIGHT, LEFT, UP, DOWN;
	}
	
	private Direction currentDirection;
	
	
	public GameController(Direction startDirection) {
		this.currentDirection = startDirection;
	}
	
	
	public Direction getLastDirection() {
		return this.currentDirection;
	}
	
	
	public void onEvent(Event e) {

		if (e.getEventType() == EventType.KeyReleasedEvent) {
			KeyEvent evt = (KeyEvent) e;
			if (evt.getKeyCode() == Input.KEY_UP) {
				this.currentDirection = Direction.UP;
			} else if (evt.getKeyCode() == Input.KEY_DOWN) {
				this.currentDirection = Direction.DOWN;
			} else if (evt.getKeyCode() == Input.KEY_LEFT) {
				this.currentDirection = Direction.LEFT;
			} else if (evt.getKeyCode() == Input.KEY_RIGHT) {
				this.currentDirection = Direction.RIGHT;
			}
		}
		
	}
	
}
