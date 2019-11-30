package br.fritzen.engine.components;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.joml.Vector4f;

import br.fritzen.engine.Application;
import br.fritzen.engine.core.EngineState;
import br.fritzen.engine.core.layers.Layer;
import br.fritzen.engine.renderer.Material;
import br.fritzen.engine.renderer.Renderer;
import br.fritzen.engine.renderer.Texture2D;
import imgui.imgui.demo.showExampleApp.MyDocument;

public class MeshLoaderTest {

	public static class RotationMeshLayer extends Layer {

		private PerspectiveCamera camera;
		
		private Matrix4f transform;
		
		private Mesh cubeMesh;
		
		private Material material;
		
		
		public RotationMeshLayer() {
			super("MAIN LAYER");
			
			camera = new PerspectiveCamera();
			
			this.cubeMesh = new Mesh("models/obj/cube.obj");
			
			this.transform = new Matrix4f();
			
			this.material = new Material();
			this.material.setDiffuseColor(new Vector4f(1, 1, 1, 1));
			this.material.setDiffuseTexture(Texture2D.create("textures/default.png"));
			
			this.camera.setPosition(new Vector3f(0, 0, 1f));
		}
		
		float angle = 0;
		
		@Override
		public void onUpdate(float deltatime) {
			
			transform.rotate((float)Math.toRadians(50f * deltatime), EngineState.Y_AXIS);
			
		}
		
		@Override
		public void onRender() {
			
			Renderer.beginScene(this.camera);
		
			Renderer.render(this.cubeMesh, this.transform, this.material);
			
			Renderer.endScene();
		}
		
	}
	
	
	
	public static void main(String[] args) {

		Application app = Application.create("Camera Test", 1280, 720);
		
		app.addLayer(new RotationMeshLayer());
		
		app.run();
		
	}

	
}
