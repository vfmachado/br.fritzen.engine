package br.fritzen.engine.core.layers;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class LayerStack implements Iterable<Layer> {

	private List<Layer> layers;
	
	
	public LayerStack() {
	
		this.layers = new ArrayList<Layer>();
		
	}
	
	
	public ListIterator<Layer> begin() {
		return this.layers.listIterator();
	}
	
	
	public ListIterator<Layer> end() {
		return this.layers.listIterator(layers.size());
	}
	
		
	public void pushLayer(Layer layer) {
		this.layers.add(layer);
		layer.onAttach();
	}
	
	
	public void pushOverlay(Layer overlay) {
		this.layers.add(0, overlay);
		overlay.onAttach();
	}
	
	
	public void popLayer(Layer layer) {
		this.layers.remove(layer);
		layer.onDetach();
	}
	
	
	public void popOverlay(Layer overlay) {
		layers.remove(overlay);
		overlay.onDetach();
	}


	@Override
	public Iterator<Layer> iterator() {
		return this.layers.iterator();
	}
	
	
}
