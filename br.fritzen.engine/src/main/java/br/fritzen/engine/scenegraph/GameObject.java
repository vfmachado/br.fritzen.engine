package br.fritzen.engine.scenegraph;

import br.fritzen.engine.events.Event;

public abstract class GameObject {

	
	protected abstract GameObjectType getType();
	
	
	protected void onUpdate(float deltatime) {}
	
	
	protected void onEvent(Event e) {} 
	
	
	protected void onRender() {}
	
}
