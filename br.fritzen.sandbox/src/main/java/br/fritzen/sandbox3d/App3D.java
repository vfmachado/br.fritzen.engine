package br.fritzen.sandbox3d;

import org.joml.Vector3f;

import br.fritzen.engine.Application;
import br.fritzen.engine.components.PerspectiveCameraController;
import br.fritzen.engine.core.layers.Layer;
import br.fritzen.engine.events.Event;
import br.fritzen.engine.gameobject.Light.DirectionalLight;
import br.fritzen.engine.renderer.Renderer;

public class App3D {

	public static class Scene3D extends Layer {

		private PerspectiveCameraController camera;

		private BlenderMonkey monkey;
		
		private Plane plane;
		
		private DirectionalLight dirLight;

		
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
			
			this.plane.getTransform().translate(0, -1f, 0).scale(3);
		}


		@Override
		public void onUpdate(float deltatime) {

			camera.onUpdate(deltatime);

		}


		@Override
		public void onRender() {

			Renderer.beginScene(this.camera.getCamera(), this.dirLight);
			
			Renderer.render(plane.getMesh(), plane.getTransform(), plane.getMaterial());
			
			Renderer.render(monkey.getMesh(), monkey.getTransform(), monkey.getMaterial());

			Renderer.endScene();
		}


		@Override
		public void onEvent(Event e) {
			this.camera.onEvent(e);
		}
	}


	public static void main(String[] args) {
		Application app = Application.create("Sandbox 3D", 1280, 720);
		app.addLayer(new Scene3D());
		app.run();
	}

}
