package core;

import java.util.ArrayList;
import java.util.List;

import br.fritzen.engine.animator.Animator2D;
import br.fritzen.engine.utils.Pair;
import lombok.Getter;
import lombok.Setter;

public class StateNode {

	private String name;
	
	@Getter
	@Setter
	private Animator2D animation;
	
	private List<Pair<StateNode, String>> paths;
	
	
	public StateNode(String name) {
		this(name, null);
	}
	
	
	public StateNode(String name, Animator2D animation) {
		
		this.name = name;
		this.animation = animation;
		
		paths = new ArrayList<Pair<StateNode,String>>();
	}
	
	public void onTrigger() {
		System.out.println("TRIGGERING ::: " + name);
		
	}
	
	public void onUpdate(float deltatime) {
		
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
