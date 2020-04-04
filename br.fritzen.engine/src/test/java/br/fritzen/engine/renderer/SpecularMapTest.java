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

public class SpecularMapTest extends Layer {

	private PerspectiveCameraController cameraController;
	
	private DirectionalLight dirLight;
	
	private Scene scene;
	
	
	public SpecularMapTest() {
		
		super("MainLayer");
		
		this.cameraController = new PerspectiveCameraController(0, 2, 5);

		//TODO ADD TO SCENE
		this.dirLight = new DirectionalLight(
				new Vector3f(0.4f), 	//ambient
				new Vector3f(1), 		//diffuse
				new Vector3f(1), 		//specular
				new Vector3f(-1, -1, -1));	//direction
		
		scene = new Scene(cameraController.getCamera());
		
		Model planeModel = new Model("models/obj/cube.obj");
		
		GameObject planeFlat = new GameObject();
		
		planeFlat.addComponent(new MeshRenderer() {
			
			private List<Pair<Mesh, Material>> meshMaterial = new ArrayList<Pair<Mesh, Material>>();
			
			{
				Material material = new Material();
				material.setDiffuseTexture(Texture2D.create("textures/container.png"));
				meshMaterial.add(new Pair<Mesh, Material>(planeModel.getMeshes().get(0).getKey(), material));
			}
			
			@Override
			public List<Pair<Mesh, Material>> getMeshMaterial() {
				return meshMaterial;
			}
		});
		
		
		GameObject planeWithSpecular = new GameObject();
		
		planeWithSpecular.addComponent(new MeshRenderer() {
			
			private List<Pair<Mesh, Material>> meshMaterial = new ArrayList<Pair<Mesh, Material>>();
			
			{
				Material material = new Material();
				material.setDiffuseTexture(Texture2D.create("textures/container.png"));
				material.setSpecularMapTexture(Texture2D.create("textures/container_specular.png"));
				
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
		
		planeWithSpecular.getTransform()
			.translate(2, 0, 0)
			.rotate((float) Math.toRadians(90), EngineState.Y_AXIS)
			.rotate((float) Math.toRadians(90), EngineState.Z_AXIS)
			;
		
		scene.add(planeFlat);
		scene.add(planeWithSpecular);
		
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
		
		app.addLayer(new SpecularMapTest());
		
		app.run();
		
	}

}
