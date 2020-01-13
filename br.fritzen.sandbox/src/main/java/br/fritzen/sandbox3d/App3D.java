package br.fritzen.sandbox3d;

import br.fritzen.engine.Application;
import br.fritzen.engine.components.PerspectiveCameraController;
import br.fritzen.engine.core.EngineState;
import br.fritzen.engine.core.layers.Layer;
import br.fritzen.engine.renderer.Renderer;


public class App3D {

	public static class Scene3D extends Layer {

		private PerspectiveCameraController camera;
		
		private Cube cube;
		
		public Scene3D() {
			
			super("Main 3D Layer");
			
			this.camera = new PerspectiveCameraController();
			
			this.cube = new Cube();
			
		}
		
		
		@Override
		public void onUpdate(float deltatime) {
			
			camera.onUpdate(deltatime);
			
			//cube.getTransform().rotate((float)Math.toRadians(45 * deltatime), EngineState.Y_AXIS);
		}
		
		
		@Override
		public void onRender() {
			
			Renderer.beginScene(this.camera.getCamera());
			
			Renderer.render(cube.getMesh(), cube.getTransform(), cube.getMaterial());
			
			Renderer.endScene();
		}
		
	}
	
	
	public static void main(String[] args) {
		Application app = Application.create("Sandbox 2D", 1280, 720);
		app.addLayer(new Scene3D());
		app.run();
	}

}
