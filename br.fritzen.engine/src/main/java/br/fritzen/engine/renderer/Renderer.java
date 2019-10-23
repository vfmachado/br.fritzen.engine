package br.fritzen.engine.renderer;

import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector4f;

import br.fritzen.engine.components.Camera;
import br.fritzen.engine.renderer.Buffer.VertexArray;
import br.fritzen.engine.renderer.shader.QuadModel;
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
	

	private static Matrix4f tmpTransform = new Matrix4f();
	public static void drawQuad(Vector2f pos, Vector2f size, Vector4f color) {
		
		QuadModel.getShader().bind();
		
		QuadModel.getShader().updateUniform(ShaderUniform.viewProjection, sceneData.viewProjectionMatrix);
		
		tmpTransform.identity().scale(size.x, size.y, 1).translate(pos.x, pos.y, 0);
		
		QuadModel.getShader().updateUniform(ShaderUniform.model, tmpTransform);
		
		QuadModel.getShader().updateUniform(ShaderUniform.color, color);
		
		QuadModel.getVAO().bind();
		RendererAPI.get().drawIndexed(QuadModel.getVAO());
		
	}
	
}
