package br.fritzen.sandbox3d;



import java.util.List;

import org.joml.Matrix4f;
import org.joml.Vector3f;

import br.fritzen.engine.Application;
import br.fritzen.engine.components.Mesh;
import br.fritzen.engine.components.MeshRenderer;
import br.fritzen.engine.components.Model;
import br.fritzen.engine.components.PerspectiveCameraController;
import br.fritzen.engine.components.Skybox;
import br.fritzen.engine.core.layers.Layer;
import br.fritzen.engine.events.Event;
import br.fritzen.engine.renderer.Material;
import br.fritzen.engine.renderer.Renderer;
import br.fritzen.engine.renderer.Texture2D;
import br.fritzen.engine.scenegraph.Light.DirectionalLight;
import br.fritzen.engine.scenegraph.GameObject;
import br.fritzen.engine.scenegraph.GameObjectType;
import br.fritzen.engine.scenegraph.Scene;
import br.fritzen.engine.terrain.FlatTerrain;
import br.fritzen.engine.utils.Pair;

public class App3D {

	public static class Scene3D extends Layer {

		private PerspectiveCameraController cameraController;

		private BlenderMonkey monkey;
		
		private Plane plane;
		
		private DirectionalLight dirLight;

		private Skybox skybox;
		
		private FlatTerrain terrain;
		
		private Model model; 
		
		private Matrix4f transform;
		
		private Matrix4f terrainTransform;
		
		private Scene scene;
		
		
		public Scene3D() {

			super("Main 3D Layer");

			this.cameraController = new PerspectiveCameraController(0, 2, 5);

			this.monkey = new BlenderMonkey();
			this.plane = new Plane();
			
			this.dirLight = new DirectionalLight(
					new Vector3f(0.4f), 	//ambient
					new Vector3f(1), 		//diffuse
					new Vector3f(1), 		//specular
					new Vector3f(-1, -1, -1));	//direction
			
			this.plane.getTransform().translate(0, -1f, 0).scale(5);
			
			this.skybox = new Skybox(
					new Model("src/main/resources/skybox/sky3.obj").getMeshes().get(0).getKey(),
					Texture2D.create("skybox/yellow_field_2k.hdr"), 
					50);
			
			
			
			
			Material terrainMaterial = new Material();
					
			terrainMaterial.setDiffuseTexture(Texture2D.create("textures/grass_DIF.jpg"));
			terrainMaterial.setShininess(32f);
			
			this.terrain = new FlatTerrain(-5, -5, terrainMaterial);
			this.terrainTransform = new Matrix4f().translate(-10, 0, -10).scale(10);
			
			
			
			//model = new Model("models/plants/Bush_01/Bush_01.obj");
			//model = new Model("models/plants/Grass_01/grassmodel.obj");
			//model = new Model("models/plants/Palm_01/Palma 001.obj");
			//model = new Model("models/plants/Plant_01/billboardmodel.obj");
			//model = new Model("models/plants/Tree_01/tree01.obj");
			model = new Model("models/plants/Tree_02/tree02.obj");
			
			transform = new Matrix4f();
			
			
			
			this.scene = new Scene(cameraController.getCamera());
			
			this.scene.setSkybox(this.skybox);
			
			
			
			GameObject tree = new GameObject();
			
			tree.addComponent(new MeshRenderer() {
				@Override
				public List<Pair<Mesh, Material>> getMeshMaterial() {
					return model.getMeshMaterialList();
				}
			});
			
			this.scene.add(tree);
			
			//this.scene.add(this.monkey);
			//this.scene.add(this.dirLight);
			//this.scene.add(this.terrain);
			
		}


		@Override
		public void onUpdate(float deltatime) {

			cameraController.onUpdate(deltatime);	//PROBABLY GO TO SCENE PROCESS
			
			this.scene.onUpdate(deltatime);			
			
		}


		@Override
		public void onRender() {

			Renderer.beginScene(scene);	//render all things on scene
			
			//allows user to render more things. apart from scene
			
			//Renderer.beginScene(this.cameraController.getCamera(), this.dirLight, this.skybox);
			
			Renderer.render(model, transform);
			
			//Renderer.render(plane.getMesh(), plane.getTransform(), plane.getMaterial());
			
			//Renderer.render(monkey.getMesh(), monkey.getTransform(), monkey.getMaterial());

			//Renderer.render(terrain.getMesh(), terrainTransform, terrain.getMaterial());
			
			Renderer.endScene();
		}


		@Override
		public void onEvent(Event e) {
			
			this.cameraController.onEvent(e);
			
			//OBJECTIVE: this.sceneOnEvent(e);
			
			//EngineLog.info(e.toString());
			
		}
	}


	public static void main(String[] args) {
		Application app = Application.create("Sandbox 3D", 1280, 720);
		app.addLayer(new Scene3D());
		app.run();
	}

}
