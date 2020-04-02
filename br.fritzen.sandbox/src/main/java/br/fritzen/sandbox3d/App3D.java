package br.fritzen.sandbox3d;



import org.joml.Vector3f;

import br.fritzen.engine.Application;
import br.fritzen.engine.components.Model;
import br.fritzen.engine.components.PerspectiveCameraController;
import br.fritzen.engine.components.Skybox;
import br.fritzen.engine.core.layers.Layer;
import br.fritzen.engine.events.Event;
import br.fritzen.engine.renderer.Material;
import br.fritzen.engine.renderer.Renderer;
import br.fritzen.engine.renderer.Texture2D;
import br.fritzen.engine.scenegraph.Light.DirectionalLight;
import br.fritzen.engine.terrain.FlatTerrain;

public class App3D {

	public static class Scene3D extends Layer {

		private PerspectiveCameraController camera;

		private BlenderMonkey monkey;
		
		private Plane plane;
		
		private DirectionalLight dirLight;

		private Skybox skybox;
		
		private FlatTerrain terrain;
		
		//OBJECTIVE: private Scene scene;
		
		
		public Scene3D() {

			super("Main 3D Layer");

			this.camera = new PerspectiveCameraController(0, 2, 5);

			this.monkey = new BlenderMonkey();
			this.plane = new Plane();
			
			this.dirLight = new DirectionalLight(
					new Vector3f(0.4f), 	//ambient
					new Vector3f(1), 		//diffuse
					new Vector3f(1), 		//specular
					new Vector3f(-1, -1, -1));	//direction
			
			this.plane.getTransform().translate(0, -1f, 0).scale(5);
			
			this.skybox = new Skybox(
					new Model("src/main/resources/skybox/skybox.obj").getMeshes().get(0).getKey(),
					Texture2D.create("skybox/skybox.png"), 
					50);
			
			Material terrainMaterial = new Material();
					
			terrainMaterial.setDiffuseTexture(Texture2D.create("textures/grass.jpg"));
			terrainMaterial.setShininess(32f);
			
			this.terrain = new FlatTerrain(0, 0, terrainMaterial);
			
			//OBJECTIVE:
			/*
			
			this.scene.setCamera(this.camera);
			this.scene.setSky(this.skybox);
			this.scene.add(this.monkey);
			this.scene.add(this.dirLight);
			this.scene.add(this.terrain);
			 
			
			 */
		}


		@Override
		public void onUpdate(float deltatime) {

			camera.onUpdate(deltatime);	//PROBABLY GO TO SCENE PROCESS
			
			//OBJECTIVE: this.scene.onUpdate(deltatime);			
			
		}


		@Override
		public void onRender() {

			//OBJECTIVE: Renderer.beginScene(scene);	//render all things on scene
			
			//allows user to render more things. apart from scene
			
			Renderer.beginScene(this.camera.getCamera(), this.dirLight, this.skybox);
			
			//Renderer.render(plane.getMesh(), plane.getTransform(), plane.getMaterial());
			
			Renderer.render(monkey.getMesh(), monkey.getTransform(), monkey.getMaterial());

			Renderer.render(terrain.getMesh(), monkey.getTransform(), terrain.getMaterial());
			
			Renderer.endScene();
		}


		@Override
		public void onEvent(Event e) {
			
			this.camera.onEvent(e);
			
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
