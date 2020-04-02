package br.fritzen.engine.scenegraph;

import java.util.ArrayList;
import java.util.List;

import br.fritzen.engine.components.Camera;
import br.fritzen.engine.components.Skybox;
import lombok.Getter;
import lombok.Setter;

public class Scene {

	@Getter
	@Setter
	private Camera camera;
	
	@Getter
	private GameObject rootGameObject;
	
	@Getter
	@Setter
	private Skybox skybox;
	
	
	@Getter
	@Setter
	private List<Light> lights;
	
	
	public Scene(Camera camera) {
	
		this.camera = camera;
		
		this.lights = new ArrayList<Light>();
		
		this.skybox = null;
		
		rootGameObject = new GameObject() {
			
			@Override
			protected GameObjectType getType() {
				return GameObjectType.SIMPLE;
			}
		};
		
		rootGameObject.setSceneReferene(this);
		
		
	}
	
	
	public void add(GameObject gameObject) {
		
		gameObject.setSceneReferene(this);
		
		rootGameObject.addChild(gameObject);
		
	}
	
	
	public void onUpdate(float deltatime) {
		
		//camera update is dealt by user
		if (skybox != null)
			skybox.onUpdate(deltatime);
		
		//process all objects on the scene
		recursiveUpdate(this.rootGameObject, deltatime);
		
	}
	
	
	private void recursiveUpdate(GameObject parent, float deltatime) {
		
		parent.onUpdate(deltatime);
		
		for (GameObject go : parent.getChildren()) {
			recursiveUpdate(go, deltatime);
		}
		
	}
	
	
	
}
