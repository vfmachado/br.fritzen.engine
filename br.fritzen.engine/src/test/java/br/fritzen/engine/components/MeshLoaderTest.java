package br.fritzen.engine.components;

import org.joml.Matrix4f;
import org.joml.Vector3f;

import br.fritzen.engine.Application;
import br.fritzen.engine.core.EngineState;
import br.fritzen.engine.core.input.Input;
import br.fritzen.engine.core.layers.Layer;
import br.fritzen.engine.events.Event;
import br.fritzen.engine.renderer.Material;
import br.fritzen.engine.renderer.Renderer;
import br.fritzen.engine.scenegraph.Light.DirectionalLight;
import br.fritzen.engine.utils.Pair;

public class MeshLoaderTest {

	public static class RotationMeshLayer extends Layer {

		private PerspectiveCameraController cameraController;
		
		private Matrix4f transform;
		
		private Model model;

		private DirectionalLight dirLight;
		
		
		
		public RotationMeshLayer() {
			super("MAIN LAYER");
			
			cameraController = new PerspectiveCameraController(0, 5, 10);
			cameraController.setSpeed(5);
			
			model = new Model("models/lamborghini/Lamborghini_Aventador.obj");
			
			this.transform = new Matrix4f();
			transform.scale(0.1f);
			
			this.dirLight = new DirectionalLight(
					new Vector3f(0.4f), 	//ambient
					new Vector3f(1), 		//diffuse
					new Vector3f(1), 		//specular
					new Vector3f(-1, -1, -1));	//direction
			
		}
		
		float angle = 0;
		
		@Override	
		public void onUpdate(float deltatime) {
			
			this.cameraController.onUpdate(deltatime);
			
			if (Input.isKeyPressed(Input.KEY_LEFT))
				transform.rotate((float)Math.toRadians(-20f * deltatime), EngineState.Y_AXIS);
			else if (Input.isKeyPressed(Input.KEY_RIGHT))
				transform.rotate((float)Math.toRadians(20f * deltatime), EngineState.Y_AXIS);
			
		}
		
		@Override
		public void onRender() {
			
			Renderer.beginScene(this.cameraController.getCamera(), dirLight);
			
			for (Pair<Mesh, Integer> m : model.getMeshes()) {
				if (!m.getKey().getName().equals("Lamborghini_Aventador:Collider"))
				Renderer.render(m.getKey(), this.transform, model.getMaterials().get(m.getValue()));
			}
			
			Renderer.endScene();
		}
		
		@Override
		public void onEvent(Event e) {
			this.cameraController.onEvent(e);
		}
		
	}
	
	
	
	public static void main(String[] args) {

		Application app = Application.create("Load With Assimp Test", 1280, 720);
		
		app.addLayer(new RotationMeshLayer());
		
		app.run();
		
	}

	
}
