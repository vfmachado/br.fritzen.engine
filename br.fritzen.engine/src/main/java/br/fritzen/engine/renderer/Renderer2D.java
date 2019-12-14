package br.fritzen.engine.renderer;

import java.nio.ByteBuffer;

import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;
import org.lwjgl.BufferUtils;

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
	
	private static Texture2D blankTexture = Texture2D.create(1, 1);
	
	public static void init() {
		
		ByteBuffer textureData = BufferUtils.createByteBuffer(16);
		
		textureData.put((byte) 255);
		textureData.put((byte) 255);
		textureData.put((byte) 255);
		textureData.put((byte) 255);
		
		textureData.flip();
		
		blankTexture.setData(textureData, 1);
		
	}
	
	
	public static void beginScene(Camera camera) {
	
		sData.getShader().bind();
		
		sceneData.viewMatrix = camera.getView();
		sceneData.projectionMatrix = camera.getProjection();
		sceneData.viewProjectionMatrix = camera.getViewProjection();

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
				
		tmpTransform.identity().translate(pos.x, pos.y, pos.z).scale(size.x, size.y, 1);
		
		sData.getShader().setMat4(ShaderUniform.model, tmpTransform);
		
		blankTexture.bind();
		
		sData.getShader().setInt(ShaderUniform.texture, 0);
		
		sData.getShader().setFloat4(ShaderUniform.color, color);
		
		RendererAPI.get().drawIndexed(sData.getVAO());
		
	}
	
	
	public static void drawQuad(Vector2f pos, Vector2f size, Texture2D texture) {
		
		Renderer2D.drawQuad(tmpVec3.set(pos, 0), size, texture);		
	}
	
	
	public static void drawQuad(Vector3f pos, Vector2f size, Texture2D texture) {

		tmpTransform.identity().translate(pos.x, pos.y, pos.z).scale(size.x, size.y, 1);
		
		sData.getShader().setMat4(ShaderUniform.model, tmpTransform);
		
		sData.getShader().setFloat4(ShaderUniform.color, 1, 1, 1, 1);
		
		texture.bind();
		
		sData.getShader().setInt(ShaderUniform.texture, 0);
		
		RendererAPI.get().drawIndexed(sData.getVAO());
	}
		
	
	public static void drawQuad(Vector2f pos, Vector2f size, Texture2D texture, Vector4f color) {
		Renderer2D.drawQuad(tmpVec3.set(pos, 0), size, texture, color);		
	}
	
	
	public static void drawQuad(Vector3f pos, Vector2f size, Texture2D texture, Vector4f color) {

		tmpTransform.identity().translate(pos.x, pos.y, pos.z).scale(size.x, size.y, 1);
		
		sData.getShader().setMat4(ShaderUniform.model, tmpTransform);
		
		sData.getShader().setFloat4(ShaderUniform.color, color);
		
		texture.bind();
		
		sData.getShader().setInt(ShaderUniform.texture, 0);
		
		RendererAPI.get().drawIndexed(sData.getVAO());
	}
	
	
	public static void drawRotatedQuad(Vector2f pos, Vector2f size, float angle, Vector4f color) {
		drawRotatedQuad(tmpVec3.set(pos, 0), size, angle, color);
	}
	
	
	public static void drawRotatedQuad(Vector3f pos, Vector2f size, float angle, Vector4f color) {
		
		tmpTransform.identity()
			.translate(pos.x, pos.y, pos.z)
			.rotate((float) Math.toRadians(angle), 0, 0, 1)
			.scale(size.x, size.y, 1);
		
		sData.getShader().setMat4(ShaderUniform.model, tmpTransform);
		
		blankTexture.bind();
		
		sData.getShader().setInt(ShaderUniform.texture, 0);
		
		sData.getShader().setFloat4(ShaderUniform.color, color);
		
		RendererAPI.get().drawIndexed(sData.getVAO());
		
		
	}
	
	
	public static void drawRotatedQuad(Vector2f pos, Vector2f size, float angle, Texture2D texture) {
		
		Renderer2D.drawRotatedQuad(tmpVec3.set(pos, 0), size, angle, texture);		
	}
	
	
	public static void drawRotatedQuad(Vector3f pos, Vector2f size, float angle, Texture2D texture) {

		tmpTransform.identity()
			.translate(pos.x, pos.y, pos.z)
			.rotate((float) Math.toRadians(angle), 0, 0, 1)
			.scale(size.x, size.y, 1);
	
		sData.getShader().setMat4(ShaderUniform.model, tmpTransform);
		
		sData.getShader().setFloat4(ShaderUniform.color, 1, 1, 1, 1);
		
		texture.bind();
		
		sData.getShader().setInt(ShaderUniform.texture, 0);
		
		RendererAPI.get().drawIndexed(sData.getVAO());
	}
	

	public static void drawRotatedQuad(Vector2f pos, Vector2f size, float angle, Texture2D texture, Vector4f color) {
		Renderer2D.drawRotatedQuad(tmpVec3.set(pos, 0), size, angle, texture, color);		
	}
	
	
	public static void drawRotatedQuad(Vector3f pos, Vector2f size, float angle, Texture2D texture, Vector4f color) {

		tmpTransform.identity()
			.translate(pos.x, pos.y, pos.z)
			.rotate((float) Math.toRadians(angle), 0, 0, 1)
			.scale(size.x, size.y, 1);

		sData.getShader().setMat4(ShaderUniform.model, tmpTransform);
		
		sData.getShader().setFloat4(ShaderUniform.color, color);
		
		texture.bind();
		
		sData.getShader().setInt(ShaderUniform.texture, 0);
		
		RendererAPI.get().drawIndexed(sData.getVAO());
	}
	

	
	public static void setTillingFactor(float repeats) {
		sData.getShader().setFloat(ShaderUniform.tillingFactor, repeats);
	}
	
	
	private static Matrix4f tmpTransform = new Matrix4f();
	private static Vector3f tmpVec3 = new Vector3f();

	
}
