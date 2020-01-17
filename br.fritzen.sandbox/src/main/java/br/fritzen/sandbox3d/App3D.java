package br.fritzen.sandbox3d;

import br.fritzen.engine.Application;
import br.fritzen.engine.components.PerspectiveCameraController;
import br.fritzen.engine.core.EngineState;
import br.fritzen.engine.core.layers.Layer;
import br.fritzen.engine.events.Event;
import br.fritzen.engine.renderer.Renderer;

public class App3D {

	public static class Scene3D extends Layer {

		private PerspectiveCameraController camera;

		private Cube cube;
		private Plane plane;

		public Scene3D() {

			super("Main 3D Layer");

			this.camera = new PerspectiveCameraController(0, 2, 5);

			this.cube = new Cube();
			this.plane = new Plane();
			
			this.cube.getTransform().rotate((float)Math.toRadians(45), EngineState.Y_AXIS);
			
			this.plane.getTransform().translate(0, -0.5f, 0).scale(5);
		}


		@Override
		public void onUpdate(float deltatime) {

			camera.onUpdate(deltatime);

		}


		@Override
		public void onRender() {

			Renderer.beginScene(this.camera.getCamera());
			
			Renderer.render(plane.getMesh(), plane.getTransform(), plane.getMaterial());
			
			Renderer.render(cube.getMesh(), cube.getTransform(), cube.getMaterial());

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
