package Character;

import org.joml.Vector2f;

import br.fritzen.engine.animator.Animator2D;
import core.StateNode;

public class AttackA extends StateNode {

	float time = 0;
	
	public AttackA(String name, Animator2D animation) {
		super(name, animation);		
	}

	
	@Override
	public void onTrigger() {
		super.onTrigger();
		
		time = 0;
		this.getAnimation().restart();
	}
	
	
	@Override
	public void onUpdate(float deltatime) {
		super.onUpdate(deltatime);
		this.getAnimation().update(deltatime);
				
		time += deltatime;
		if (time > 0.5f)
			onEnd();
		
	}
	
}
