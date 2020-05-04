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
		
	private static final Renderer2DStorage sData = new Renderer2DStorage();
	
	private static final Texture2D blankTexture = Texture2D.create(1, 1);
	
	
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
	
		//System.out.println("STARTING SCENE");
		
		sData.getShader().bind();
		
		sceneData.viewMatrix = camera.getView();
		sceneData.projectionMatrix = camera.getProjection();
		sceneData.viewProjectionMatrix = camera.getViewProjection();

		sData.getShader().setMat4(ShaderUniform.viewProjection, sceneData.viewProjectionMatrix);
		//sData.getShader().setMat4(ShaderUniform.view, sceneData.viewMatrix);
		//sData.getShader().setMat4(ShaderUniform.projection, sceneData.projectionMatrix);
		
		sData.startQuadPointer();
				
	}
	
	
	
	public static void endScene() {
		
		flush();
	
		//System.out.println("END SCENE\n\n");
	}
	
	
	public static void flush() {
		
		//System.out.println("DRAW!!!");
		
		sData.updateData();
		RendererAPI.get().drawIndexed(sData.getVertexArray(), sData.getQuadCount());
		
		sData.startQuadPointer();
	}
	
	
	public static void drawQuad(Vector2f position, Vector2f size, Vector4f color) {
		
		Renderer2D.drawQuad(tmpVec3.set(position, 0), size, color);		
		
	}
	
	
	public static void drawQuad(Vector3f position, Vector2f size, Vector4f color) {

		//BOTTOM - LEFT
		sData.getQuadPointer().position.set(position);
		sData.getQuadPointer().color.set(color);
		sData.getQuadPointer().texCoord.set(0, 0);
		sData.increaseQuadPointer();
		
		//BOTTOM - RIGHT
		sData.getQuadPointer().position.set(position.x + size.x, position.y, position.z);
		sData.getQuadPointer().color.set(color);
		sData.getQuadPointer().texCoord.set(1, 0);
		sData.increaseQuadPointer();
		
		//UP - RIGHT
		sData.getQuadPointer().position.set(position.x + size.x, position.y + size.y, position.z);
		sData.getQuadPointer().color.set(color);
		sData.getQuadPointer().texCoord.set(1, 1);
		sData.increaseQuadPointer();
		
		//UP - LEFT
		sData.getQuadPointer().position.set(position.x, position.y + size.y, position.z);
		sData.getQuadPointer().color.set(color);
		sData.getQuadPointer().texCoord.set(0, 1);
		sData.increaseQuadPointer();
		
		
		if (sData.mustFlush())	flush();
		
		/*
		tmpTransform.identity().translate(position.x, position.y, position.z).scale(size.x, size.y, 1);
		
		sData.getShader().setMat4(ShaderUniform.model, tmpTransform);
		
		blankTexture.bind();
		
		sData.getShader().setInt(ShaderUniform.texture, 0);
		
		sData.getShader().setFloat4(ShaderUniform.color, color);
		
		RendererAPI.get().drawIndexed(sData.getVertexArray());
		*/
	}
	
	
	public static void drawQuad(Vector2f position, Vector2f size, Texture2D texture) {
		
		Renderer2D.drawQuad(tmpVec3.set(position, 0), size, texture);		
	}
	
	
	public static void drawQuad(Vector3f position, Vector2f size, Texture2D texture) {

		tmpTransform.identity().translate(position.x, position.y, position.z).scale(size.x, size.y, 1);
		
		sData.getShader().setMat4(ShaderUniform.model, tmpTransform);
		
		sData.getShader().setFloat4(ShaderUniform.color, 1, 1, 1, 1);
		
		texture.bind();
		
		sData.getShader().setInt(ShaderUniform.texture, 0);
		
		RendererAPI.get().drawIndexed(sData.getVertexArray());
	}
		
	
	public static void drawQuad(Vector2f position, Vector2f size, Texture2D texture, Vector4f color) {
		Renderer2D.drawQuad(tmpVec3.set(position, 0), size, texture, color);		
	}
	
	
	public static void drawQuad(Vector3f position, Vector2f size, Texture2D texture, Vector4f color) {

		tmpTransform.identity().translate(position.x, position.y, position.z).scale(size.x, size.y, 1);
		
		sData.getShader().setMat4(ShaderUniform.model, tmpTransform);
		
		sData.getShader().setFloat4(ShaderUniform.color, color);
		
		texture.bind();
		
		sData.getShader().setInt(ShaderUniform.texture, 0);
		
		RendererAPI.get().drawIndexed(sData.getVertexArray());
	}
	
	
	public static void drawRotatedQuad(Vector2f position, Vector2f size, float angle, Vector4f color) {
		drawRotatedQuad(tmpVec3.set(position, 0), size, angle, color);
	}
	
	
	public static void drawRotatedQuad(Vector3f position, Vector2f size, float angle, Vector4f color) {
		
		tmpTransform.identity()
			.translate(position.x, position.y, position.z)
			.rotate((float) Math.toRadians(angle), 0, 0, 1)
			.scale(size.x, size.y, 1);
		
		sData.getShader().setMat4(ShaderUniform.model, tmpTransform);
		
		blankTexture.bind();
		
		sData.getShader().setInt(ShaderUniform.texture, 0);
		
		sData.getShader().setFloat4(ShaderUniform.color, color);
		
		RendererAPI.get().drawIndexed(sData.getVertexArray());
		
		
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
		
		RendererAPI.get().drawIndexed(sData.getVertexArray());
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
		
		RendererAPI.get().drawIndexed(sData.getVertexArray());
	}
	

	
	public static void setTillingFactor(float repeats) {
		sData.getShader().setFloat(ShaderUniform.tillingFactor, repeats);
	}
	
	
	private static Matrix4f tmpTransform = new Matrix4f();
	private static Vector3f tmpVec3 = new Vector3f();

	
}
