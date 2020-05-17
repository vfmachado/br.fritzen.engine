package core;

import br.fritzen.engine.utils.Pair;

public class StateGraph {

	private StateNode currentNode;
	
	
	public void setInitial(StateNode initial) {
	
		this.currentNode = initial;
		
	}
	
	
	public void update(float deltatime) {
		
		System.out.println("Updating ... " + currentNode);
		
	}


	public StateNode getCurrentNode() {
		return this.currentNode;
	}

	
	public String listPaths() {
		return this.currentNode.getPaths().toString();
	}


	public void trigger(String trigger) {
		
		for (Pair<StateNode, String> node : this.currentNode.getPaths()) {
			if (node.getValue().equals(trigger)) {
				
				this.currentNode = node.getKey();
				this.currentNode.onTrigger();
				
				break;
			}
		}
		
	}
}
