package br.fritzen.engine.renderer;

import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;

import br.fritzen.engine.components.Camera;
import br.fritzen.engine.renderer.shader.ShaderUniform;

public abstract class Renderer2D {

	
	private static class SceneData {
		
		public Matrix4f viewMatrix;
		public Matrix4f projectionMatrix;
		public Matrix4f viewProjectionMatrix;
		
	}
	
	private static SceneData sceneData = new SceneData();
		
	private static Renderer2DStorage sData = new Renderer2DStorage();
	
	public  static void init() {
		
	}
	
	
	public static void beginScene(Camera camera) {
	
		sData.getShader().bind();
		
		sceneData.viewMatrix = camera.getViewMatrix();
		sceneData.projectionMatrix = camera.getProjectionMatrix();
		sceneData.viewProjectionMatrix = camera.getViewProjectionMatrix();

		sData.getShader().setMat4(ShaderUniform.viewProjection, sceneData.viewProjectionMatrix);
		sData.getShader().setMat4(ShaderUniform.view, sceneData.viewMatrix);
		sData.getShader().setMat4(ShaderUniform.projection, sceneData.projectionMatrix);
		
	}
	
	
	
	public static void endScene() {
	
	
	}
	
	
	public static void drawQuad(Vector2f pos, Vector2f size, Vector4f color) {
		
		Renderer2D.drawQuad(tmpVec3.set(pos, 0), size, color);		
		
	}
	
	
	public static void drawQuad(Vector3f pos, Vector2f size, Vector4f color) {
		
		tmpTransform.identity().translate(pos.x, pos.y, 0).scale(size.x, size.y, 1);
		
		sData.getShader().setMat4(ShaderUniform.model, tmpTransform);
		
		sData.getShader().setFloat4(ShaderUniform.color, color);
		
		RendererAPI.get().drawIndexed(sData.getVAO());
		
	}
	
	
	private static Matrix4f tmpTransform = new Matrix4f();
	private static Vector3f tmpVec3 = new Vector3f();
	
}
