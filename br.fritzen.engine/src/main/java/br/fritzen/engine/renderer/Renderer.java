package br.fritzen.engine.renderer;

import org.joml.Matrix4f;

import br.fritzen.engine.components.Camera;
import br.fritzen.engine.platform.opengl.OpenGLRenderer;
import br.fritzen.engine.renderer.Buffer.VertexArray;
import br.fritzen.engine.renderer.shader.Shader;
import br.fritzen.engine.renderer.shader.ShaderUniform;

public abstract class Renderer {

	public enum API {
		NONE, OPENGL, VULKAN;
	}
	
	//TODO - change in runtime
	public static API SELECTED_API = API.OPENGL; 

	
	private static Renderer instance;

	private static SceneData sceneData = new SceneData();
		
	
	public static Renderer get() {
		
		if (instance == null) {
			instance = new OpenGLRenderer();
		}
		
		return instance; 
	}
	
	
	public static void BeginScene(Camera camera) {
	
		sceneData.viewMatrix = camera.getViewMatrix();
		sceneData.projectionMatrix = camera.getProjectionMatrix();
		sceneData.viewProjectionMatrix = camera.getViewProjectionMatrix();

		
	}
	
	
	
	public static void EndScene() {
	
	
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
		
		Renderer.get().drawIndexed(vertexArray);
		
	}
	
	
	public abstract void clear();
	
	public abstract void clearColor(float r, float g, float b, float a);
	
	public abstract void drawIndexed(VertexArray vertexArray);
	
	public abstract void updateViewPort();
	
}
