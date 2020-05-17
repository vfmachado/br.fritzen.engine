package core;

import java.util.ArrayList;
import java.util.List;

import br.fritzen.engine.utils.Pair;

public class StateNode {

	private String name;
	
	private List<Pair<StateNode, String>> paths;
	
	
	public StateNode(String name) {
		this.name = name;
		
		paths = new ArrayList<Pair<StateNode,String>>();
	}
	
	public void onTrigger() {
		System.out.println("TRIGGERING ::: " + name);
	}
	
	public void addPath(StateNode midA, String trigger) {
		paths.add(new Pair<StateNode, String>(midA, trigger));
	}

	
	@Override
	public String toString() {
		return this.name;
	}


	public List<Pair<StateNode, String>> getPaths() {
		return this.paths;
	}
}
