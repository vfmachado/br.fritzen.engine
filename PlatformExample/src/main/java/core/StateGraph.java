package core;

import br.fritzen.engine.utils.Pair;
import lombok.Getter;

public class StateGraph {

	private StateNode currentNode;
	
	@Getter
	private StateNode lastNode;
	
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
			
				this.lastNode = this.currentNode;
				this.currentNode = node.getKey();
				this.currentNode.onTrigger();
				
				break;
			}
		}
		
	}


	public void finishState() {
		this.setInitial(this.getLastNode());
		this.getCurrentNode().onTrigger();
	}


	public void addPath(StateNode stateA, StateNode stateB, String trigger) {
		
		stateA.setGraphReference(this);
		stateB.setGraphReference(this);
		
		stateA.addPath(stateB, trigger);
		
	}
}
