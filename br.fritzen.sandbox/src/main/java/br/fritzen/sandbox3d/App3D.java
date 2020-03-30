package br.fritzen.sandbox3d;



import org.joml.Vector3f;

import br.fritzen.engine.Application;
import br.fritzen.engine.components.Model;
import br.fritzen.engine.components.PerspectiveCameraController;
import br.fritzen.engine.components.Skybox;
import br.fritzen.engine.core.EngineLog;
import br.fritzen.engine.core.layers.Layer;
import br.fritzen.engine.events.Event;
import br.fritzen.engine.gameobject.Light.DirectionalLight;
import br.fritzen.engine.renderer.Renderer;
import br.fritzen.engine.renderer.Texture2D;
import br.fritzen.engine.renderer.TextureFormat;

public class App3D {

	public static class Scene3D extends Layer {

		private PerspectiveCameraController camera;

		private BlenderMonkey monkey;
		
		private Plane plane;
		
		private DirectionalLight dirLight;

		private Skybox skybox;
		
		
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
					new Model("models/skybox/skycube.obj").getMeshes().get(0).getKey(),
					Texture2D.create("textures/sunnysky.png"), 
					50);
		}


		@Override
		public void onUpdate(float deltatime) {

			camera.onUpdate(deltatime);

		}


		@Override
		public void onRender() {

			Renderer.beginScene(this.camera.getCamera(), this.dirLight, this.skybox);
			
			Renderer.render(plane.getMesh(), plane.getTransform(), plane.getMaterial());
			
			Renderer.render(monkey.getMesh(), monkey.getTransform(), monkey.getMaterial());

			Renderer.endScene();
		}


		@Override
		public void onEvent(Event e) {
			this.camera.onEvent(e);
			
			//EngineLog.info(e.toString());
			
		}
	}


	public static void main(String[] args) {
		Application app = Application.create("Sandbox 3D", 1280, 720);
		app.addLayer(new Scene3D());
		app.run();
	}

}
