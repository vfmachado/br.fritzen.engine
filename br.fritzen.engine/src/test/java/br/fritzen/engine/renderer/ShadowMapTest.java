package br.fritzen.engine.renderer;

import java.util.ArrayList;
import java.util.List;

import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;

import br.fritzen.engine.Application;
import br.fritzen.engine.components.Mesh;
import br.fritzen.engine.components.MeshRenderer;
import br.fritzen.engine.components.Model;
import br.fritzen.engine.components.OrthographicCamera;
import br.fritzen.engine.components.PerspectiveCameraController;
import br.fritzen.engine.core.input.Input;
import br.fritzen.engine.core.layers.Layer;
import br.fritzen.engine.events.Event;
import br.fritzen.engine.scenegraph.GameObject;
import br.fritzen.engine.scenegraph.Light.DirectionalLight;
import br.fritzen.engine.scenegraph.Scene;
import br.fritzen.engine.utils.Pair;

public class ShadowMapTest extends Layer {

	private PerspectiveCameraController cameraController;

	private DirectionalLight dirLight;

	private Scene scene;
	
	private OrthographicCamera orthoCam;

	private Texture2D defaultTexture = Texture2D.create("textures/default.png");

	public ShadowMapTest() {

		super("MainLayer");

		this.orthoCam = new OrthographicCamera(0, 1280, 0, 720);
		
		this.cameraController = new PerspectiveCameraController(0, 2, 5);

		this.dirLight = new DirectionalLight(new Vector3f(0.2f), // ambient
				new Vector3f(0.5f), // diffuse
				new Vector3f(0.8f), // specular
				new Vector3f(-3f, -1f, 2)); // direction

		scene = new Scene(cameraController.getCamera());

		Model planeModel = new Model("models/obj/plane.obj");
		Model cubeModel = new Model("models/obj/cube.obj");

		GameObject planeFlat = new GameObject();
		planeFlat.setName("Plane");

		planeFlat.addComponent(new MeshRenderer() {

			private List<Pair<Mesh, Material>> meshMaterial = new ArrayList<Pair<Mesh, Material>>();

			{
				Material material = new Material();
				material.setDiffuseTexture(defaultTexture);
				material.setSpecularColor(new Vector4f(0.5f, 0.5f, 0.5f, 1));
				material.setShininess(64);
				meshMaterial.add(new Pair<Mesh, Material>(planeModel.getMeshes().get(0).getKey(), material));
			}


			@Override
			public List<Pair<Mesh, Material>> getMeshMaterial() {
				return meshMaterial;
			}
		});

		GameObject cube = new GameObject();
		cube.setName("Cube");

		cube.addComponent(new MeshRenderer() {

			private List<Pair<Mesh, Material>> meshMaterial = new ArrayList<Pair<Mesh, Material>>();

			{
				Material material = new Material();
				meshMaterial.add(new Pair<Mesh, Material>(cubeModel.getMeshes().get(0).getKey(), material));
			}


			@Override
			public List<Pair<Mesh, Material>> getMeshMaterial() {
				return meshMaterial;
			}
		});

		planeFlat.getTransform().scale(10);

		cube.getTransform().scale(2).translate(0, 0.5f, 0);

		scene.add(dirLight);
		scene.add(planeFlat);
		scene.add(cube);

	}


	@Override
	public void onUpdate(float deltatime) {
		cameraController.onUpdate(deltatime);
		
		if (Input.isKeyPressed(Input.KEY_KP_7)) {
			dirLight.getDirection().x += 0.1;
		} else if (Input.isKeyPressed(Input.KEY_KP_4)) {
			dirLight.getDirection().x -= 0.1;
		}
		
		if (Input.isKeyPressed(Input.KEY_KP_8)) {
			dirLight.getDirection().y += 0.1;
		} else if (Input.isKeyPressed(Input.KEY_KP_5)) {
			dirLight.getDirection().y -= 0.1;
		}
		
		if (Input.isKeyPressed(Input.KEY_KP_9)) {
			dirLight.getDirection().z += 0.1;
		} else if (Input.isKeyPressed(Input.KEY_KP_6)) {
			dirLight.getDirection().z -= 0.1;
		}
		
		System.out.println(dirLight.getDirection());
	}


	@Override
	public void onRender() {

		Renderer.beginScene(scene);
		Renderer.endScene();
		
		Renderer2D.beginScene(orthoCam);
		Renderer2D.drawQuad(new Vector2f(100, 100), new Vector2f(200, 200), Renderer.sData.getShadowMap().getDepthMapTexture());
		Renderer2D.drawQuad(new Vector2f(300, 100), new Vector2f(200, 200), defaultTexture);
	}


	@Override
	public void onEvent(Event e) {
		this.cameraController.onEvent(e);

	}


	public static void main(String[] args) {

		Application app = Application.create("Shadow Map Test", 1280, 720);

		app.addLayer(new ShadowMapTest());

		app.run();

	}

}
