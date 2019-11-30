package br.fritzen.engine.renderer;

import org.joml.Matrix4f;

import br.fritzen.engine.components.Camera;
import br.fritzen.engine.components.Mesh;
import br.fritzen.engine.renderer.Buffer.VertexArray;
import br.fritzen.engine.renderer.shader.Shader;
import br.fritzen.engine.renderer.shader.ShaderUniform;

public abstract class Renderer {

	
	private static SceneData sceneData = new SceneData();
	
	private static Renderer3DStorage sData = new Renderer3DStorage();
	
	
	public  static void init() {
		
		RenderCommand.init();
		
		Renderer2D.init();
	}
	
	
	public static void beginScene(Camera camera) {
	
		sceneData.viewMatrix = camera.getView();
		sceneData.projectionMatrix = camera.getProjection();
		sceneData.viewProjectionMatrix = camera.getViewProjection();
		
	}
	
	
	public static void endScene() {
	
	
	}
	
	
	private static class SceneData {
		
		public Matrix4f viewMatrix;
		public Matrix4f projectionMatrix;
		public Matrix4f viewProjectionMatrix;
		
	}
	
	
	public static void submit(Shader shader, VertexArray vertexArray, Matrix4f transform) {
		
		shader.bind();
		
		shader.setMat4(ShaderUniform.viewProjection, sceneData.viewProjectionMatrix);
		shader.setMat4(ShaderUniform.view, sceneData.viewMatrix);
		shader.setMat4(ShaderUniform.projection, sceneData.projectionMatrix);
		
		shader.setMat4(ShaderUniform.model, transform);
		
		vertexArray.bind();
		
		RendererAPI.get().drawIndexed(vertexArray);
		
	}
	
	
	public static void render(Mesh mesh, Matrix4f transform, Material material) {
	
		sData.getShader().bind();
		
		sData.getShader().setMat4(ShaderUniform.viewProjection, sceneData.viewProjectionMatrix);
		sData.getShader().setMat4(ShaderUniform.view, sceneData.viewMatrix);
		sData.getShader().setMat4(ShaderUniform.projection, sceneData.projectionMatrix);
		
		sData.getShader().setMat4(ShaderUniform.model, transform);
		
		//MATERIAL
		sData.getShader().setFloat4(ShaderUniform.color, material.getDiffuseColor());
		
		material.getDiffuseTexture().bind(0);
		sData.getShader().setInt(ShaderUniform.texture, 0);
		
		mesh.getVertexArray().bind();
		RendererAPI.get().drawIndexed(mesh.getVertexArray());
				
	}
		
}
