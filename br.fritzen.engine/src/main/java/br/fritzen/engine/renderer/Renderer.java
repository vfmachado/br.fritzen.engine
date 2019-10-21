package br.fritzen.engine.renderer;

import org.joml.Matrix4f;

import br.fritzen.engine.components.Camera;
import br.fritzen.engine.renderer.Buffer.VertexArray;
import br.fritzen.engine.renderer.shader.Shader;
import br.fritzen.engine.renderer.shader.ShaderUniform;

public abstract class Renderer {

	
	private static SceneData sceneData = new SceneData();
		
		
	
	public  static void init() {
		RenderCommand.init();
	}
	
	public static void beginScene(Camera camera) {
	
		sceneData.viewMatrix = camera.getViewMatrix();
		sceneData.projectionMatrix = camera.getProjectionMatrix();
		sceneData.viewProjectionMatrix = camera.getViewProjectionMatrix();

		
	}
	
	
	
	public static void endScene() {
	
	
	}
	
	
	private static class SceneData {
		
		public Matrix4f viewMatrix;
		public Matrix4f projectionMatrix;
		public Matrix4f viewProjectionMatrix;
		
	}
	
	
	public static void submit(Shader shader, VertexArray vertexArray, Matrix4f transform) {
		
		//shader.bind();
		shader.updateUniform(ShaderUniform.viewProjection, sceneData.viewProjectionMatrix);
		shader.updateUniform(ShaderUniform.model, transform);
		
		vertexArray.bind();
		
		RendererAPI.get().drawIndexed(vertexArray);
		
	}
	

	
}
