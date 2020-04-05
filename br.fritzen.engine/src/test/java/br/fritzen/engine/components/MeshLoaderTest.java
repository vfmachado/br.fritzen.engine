package br.fritzen.engine.components;

import org.joml.Matrix4f;
import org.joml.Vector3f;

import com.jogamp.common.net.asset.Handler;

import br.fritzen.engine.Application;
import br.fritzen.engine.core.EngineState;
import br.fritzen.engine.core.input.Input;
import br.fritzen.engine.core.layers.Layer;
import br.fritzen.engine.events.Event;
import br.fritzen.engine.renderer.Material;
import br.fritzen.engine.renderer.Renderer;
import br.fritzen.engine.renderer.Texture2D;
import br.fritzen.engine.scenegraph.Light.DirectionalLight;
import br.fritzen.engine.utils.Pair;

public class MeshLoaderTest {

	public static class RotationMeshLayer extends Layer {

		private PerspectiveCameraController cameraController;
		
		private Matrix4f transform;
		
		private Model model;

		private DirectionalLight dirLight;
		
		private Material handgun; 
		
		public RotationMeshLayer() {
			super("MAIN LAYER");
			
			cameraController = new PerspectiveCameraController(0, 1, 2);
			//cameraController.setSpeed(5);
			
			//model = new Model("models/lamborghini/Lamborghini_Aventador.obj");
			
			//model = new Model("paladin/Walking.dae");
			model = new Model("arissa/Running.dae");
			
			//model = new Model("models/Tree_02/tree02.obj");
			
			//model = new Model("models/handgun.dae");
			
			handgun = new Material();
			handgun.setDiffuseTexture(Texture2D.create("models/handgun_C.jpg"));
			handgun.setNormalMapTexture(Texture2D.create("models/handgun_N.jpg"));
			handgun.setSpecularMapTexture(Texture2D.create("models/handgun_S.jpg"));
			
			
			
			this.transform = new Matrix4f();
			//.rotate((float)Math.toRadians(-90), EngineState.X_AXIS);
			transform.scale(0.1f);
			
			this.dirLight = new DirectionalLight(
					new Vector3f(0.8f), 	//ambient
					new Vector3f(1f), 		//diffuse
					new Vector3f(1), 		//specular	//5x improve strength
					new Vector3f(-0.5f, -0.2f, -0.6f));	//direction
			
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
			
			
			/*
			for (Pair<Mesh, Integer> m : model.getMeshes()) {
				if (!m.getKey().getName().equals("Cube.005") && 
						!m.getKey().getName().equals("Cube")  && 
						!m.getKey().getName().equals("Plane.002"))
				Renderer.render(m.getKey(), this.transform, handgun);
			}
			*/
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
