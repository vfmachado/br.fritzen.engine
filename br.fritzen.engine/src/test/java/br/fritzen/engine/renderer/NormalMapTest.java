package br.fritzen.engine.renderer;

import java.util.ArrayList;
import java.util.List;

import org.joml.Vector3f;

import br.fritzen.engine.Application;
import br.fritzen.engine.components.Mesh;
import br.fritzen.engine.components.MeshRenderer;
import br.fritzen.engine.components.Model;
import br.fritzen.engine.components.PerspectiveCameraController;
import br.fritzen.engine.core.EngineState;
import br.fritzen.engine.core.layers.Layer;
import br.fritzen.engine.events.Event;
import br.fritzen.engine.scenegraph.GameObject;
import br.fritzen.engine.scenegraph.Light.DirectionalLight;
import br.fritzen.engine.scenegraph.Scene;
import br.fritzen.engine.utils.Pair;

public class NormalMapTest extends Layer {

	private PerspectiveCameraController cameraController;
	
	private DirectionalLight dirLight;
	
	private Scene scene;
	
	
	public NormalMapTest() {
		
		super("MainLayer");
		
		this.cameraController = new PerspectiveCameraController(0, 2, 5);

		//TODO ADD TO SCENE
		this.dirLight = new DirectionalLight(
				new Vector3f(0.4f), 	//ambient
				new Vector3f(1), 		//diffuse
				new Vector3f(1), 		//specular
				new Vector3f(-1, -1, -1));	//direction
		
		scene = new Scene(cameraController.getCamera());
		
		Model planeModel = new Model("models/obj/plane.obj");
		
		GameObject planeFlat = new GameObject();
		
		planeFlat.addComponent(new MeshRenderer() {
			
			private List<Pair<Mesh, Material>> meshMaterial = new ArrayList<Pair<Mesh, Material>>();
			
			{
				Material material = new Material();
				material.setDiffuseTexture(Texture2D.create("textures/bricks.jpg"));
				meshMaterial.add(new Pair<Mesh, Material>(planeModel.getMeshes().get(0).getKey(), material));
			}
			
			@Override
			public List<Pair<Mesh, Material>> getMeshMaterial() {
				return meshMaterial;
			}
		});
		
		
		GameObject planeWithNormal = new GameObject();
		
		planeWithNormal.addComponent(new MeshRenderer() {
			
			private List<Pair<Mesh, Material>> meshMaterial = new ArrayList<Pair<Mesh, Material>>();
			
			{
				Material material = new Material();
				material.setDiffuseTexture(Texture2D.create("textures/bricks.jpg"));
				material.setNormalMapTexture(Texture2D.create("textures/bricksNormal.png"));
				
				meshMaterial.add(new Pair<Mesh, Material>(planeModel.getMeshes().get(0).getKey(), material));
			}
			
			@Override
			public List<Pair<Mesh, Material>> getMeshMaterial() {
				return meshMaterial;
			}
		});
		
		
		planeFlat.getTransform()
			.translate(-2, 0, 0)
			.rotate((float) Math.toRadians(90), EngineState.Y_AXIS)
			.rotate((float) Math.toRadians(90), EngineState.Z_AXIS)
			;
		
		planeWithNormal.getTransform()
			.translate(2, 0, 0)
			.rotate((float) Math.toRadians(90), EngineState.Y_AXIS)
			.rotate((float) Math.toRadians(90), EngineState.Z_AXIS)
			;
		
		scene.add(planeFlat);
		scene.add(planeWithNormal);
		
	}
	
	
	@Override
	public void onUpdate(float deltatime) {
		cameraController.onUpdate(deltatime);
	}


	@Override
	public void onRender() {

		Renderer.beginScene(scene);	
		Renderer.endScene();
	}
	
	@Override
	public void onEvent(Event e) {
		this.cameraController.onEvent(e);
		
	}
	
	
	public static void main(String[] args) {
		
		Application app = Application.create("NormalMap Test", 1280, 720);
		
		app.addLayer(new NormalMapTest());
		
		app.run();
		
	}

}
