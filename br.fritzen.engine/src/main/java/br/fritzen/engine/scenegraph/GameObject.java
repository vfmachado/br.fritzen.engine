package br.fritzen.engine.scenegraph;

import java.util.ArrayList;
import java.util.List;

import org.joml.Matrix4f;

import br.fritzen.engine.components.GameComponent;
import br.fritzen.engine.components.GameComponentType;
import br.fritzen.engine.events.Event;
import lombok.Getter;
import lombok.Setter;

public abstract class GameObject {

	@Getter
	@Setter
	private String name;
	
	@Getter
	@Setter
	private Matrix4f transform = new Matrix4f();
	
	@Getter
	private List<GameObject> children = new ArrayList<GameObject>();
	
	@Getter
	@Setter
	private Scene sceneReferene = null;
	
	@Getter
	@Setter
	private GameObject parent = null;
	
	@Getter
	private List<GameComponent> components = new ArrayList<GameComponent>();
	
	
	public void addChild(GameObject gameObject) {
		
		gameObject.setParent(this);
		gameObject.setSceneReferene(this.sceneReferene);
		
		this.children.add(gameObject);
	}
	
	
	public void addComponent(GameComponent component) {
		this.components.add(component);
	}
	
	
	public GameComponent getComponent(GameComponentType type) {
		
		for (GameComponent component : components) {
			if (component.getComponentType() == type) {
				return component;
			}
		}
		
		return null;
	}
	
	
	protected abstract GameObjectType getType();
	
	
	protected void onUpdate(float deltatime) {}
	
	
	protected void onEvent(Event e) {} 
	
	
	protected void onRender() {}
	
}
