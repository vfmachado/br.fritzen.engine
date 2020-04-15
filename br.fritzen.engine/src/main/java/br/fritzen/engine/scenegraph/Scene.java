package br.fritzen.engine.scenegraph;

import java.util.ArrayList;
import java.util.List;

import br.fritzen.engine.components.Camera;
import br.fritzen.engine.components.Skybox;
import br.fritzen.engine.scenegraph.Light.DirectionalLight;
import lombok.Getter;
import lombok.Setter;

/**
 * The scene is currently using default directional light in the renderer;
 * 
 * @author fritz
 *
 */
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
	private List<Light> lights;

	@Getter
	private List<DirectionalLight> dirlights;


	public Scene(Camera camera) {

		this.camera = camera;

		this.lights = new ArrayList<Light>();
		this.dirlights = new ArrayList<DirectionalLight>();

		this.skybox = null;

		rootGameObject = new GameObject();

		rootGameObject.setSceneReference(this);

	}


	public void add(GameObject gameObject) {

		updateSceneReference(gameObject);

		rootGameObject.addChild(gameObject);

	}


	public void onUpdate(float deltatime) {

		// camera update is dealt by user
		if (skybox != null)
			skybox.onUpdate(deltatime);

		// process all objects on the scene
		recursiveUpdate(this.rootGameObject, deltatime);

	}


	private void recursiveUpdate(GameObject parent, float deltatime) {

		parent.onUpdate(deltatime);

		for (GameObject go : parent.getChildren()) {
			recursiveUpdate(go, deltatime);
		}

	}


	public void updateSceneReference(GameObject parent) {

		parent.setSceneReference(this);

		for (GameObject go : parent.getChildren()) {
			updateSceneReference(go);
		}

	}


	public List<Light> getLights() {

		return this.lights;

	}


	private void addLights(GameObject parent) {

		if (parent.getType() == GameObjectType.LIGHT) {
			this.lights.add((Light) parent);

			switch (((Light) parent).getLightType()) {
			case Directional:
				this.dirlights.add((DirectionalLight) parent);
				break;
			case Point:
				break;
			case Spot:
				break;
			default:
				break;
			}

		}

		for (GameObject go : parent.getChildren()) {
			addLights(go);
		}

	}


	/**
	 * Call just one time per update / frame
	 */
	public void processLights() {
		
		this.lights.clear();
		this.dirlights.clear();
		
		addLights(this.rootGameObject);
	}
}
