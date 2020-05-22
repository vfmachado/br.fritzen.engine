package Character;

import Character.Player.PlayerData;
import br.fritzen.engine.animator.Animator2D;
import core.StateNode;

public class AttackA extends StateNode {

	float time = 0;
	
	PlayerData data;
	
	public AttackA(String name, Animator2D animation, PlayerData data) {
		super(name, animation);
		this.data = data;
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

		data.speed = data.defaultSpeed/4;
		
		time += deltatime;
		if (time > 0.5f)
			onEnd();
		
	}
	
	
	@Override
	public void onEnd() {
		super.onEnd();
		
	}
}
