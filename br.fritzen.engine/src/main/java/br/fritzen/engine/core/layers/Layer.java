package br.fritzen.engine.core.layers;

import br.fritzen.engine.events.Event;

public abstract class Layer {

	private String name;
	
	
	public Layer(String name) {
		this.name = name;
	}
	
	
	public String getName() {
		return this.name;
	}
	
	
	public abstract void onAttach();
	
	public abstract void onDetach();
	
	public abstract void onUpdate();
	
	public abstract void onOvent(Event e);
	
	public abstract void onImGuiRender();
	
	
}
