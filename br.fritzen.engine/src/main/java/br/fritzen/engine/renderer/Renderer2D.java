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
	
	private static int drawCalls;
	
	private static Vector3f[] quadVertexPositions;
	
	public static void init() {
		
		ByteBuffer textureData = BufferUtils.createByteBuffer(16);
		
		textureData.put((byte) 255);
		textureData.put((byte) 255);
		textureData.put((byte) 255);
		textureData.put((byte) 255);
		
		textureData.flip();
		
		blankTexture.setData(textureData, 1);
	
		quadVertexPositions = new Vector3f[4];
		quadVertexPositions[0] = new Vector3f(-0.5f, -0.5f, 0.0f);
		quadVertexPositions[1] = new Vector3f(0.5f, -0.5f, 0.0f);
		quadVertexPositions[2] = new Vector3f(0.5f,  0.5f, 0.0f);
		quadVertexPositions[3] = new Vector3f(-0.5f,  0.5f, 0.0f);
				
	}
	
	
	public static void beginScene(Camera camera) {
	
		drawCalls = 0;
		//System.out.println("STARTING SCENE");
		
		sData.getShader().bind();
		
		sceneData.viewMatrix = camera.getView();
		sceneData.projectionMatrix = camera.getProjection();
		sceneData.viewProjectionMatrix = camera.getViewProjection();

		sData.getShader().setMat4(ShaderUniform.viewProjection, sceneData.viewProjectionMatrix);
		//sData.getShader().setMat4(ShaderUniform.view, sceneData.viewMatrix);
		//sData.getShader().setMat4(ShaderUniform.projection, sceneData.projectionMatrix);
		
		sData.startQuadPointer();
		
		sData.getTextureSlot()[0] = blankTexture.getRendererId();
		sData.setTextureSlotIndex(1);
		
		
		for (int i = 0; i < Renderer2DStorage.MAX_TEXTURE_SLOTS; i++) {
			sData.getShader().setInt("u_texture[" + i + "]", i);
		}
		
	}
	
	
	
	public static void endScene() {
		
		flush();
		//System.out.println("DRAW CALLS: " + drawCalls);
		drawCalls = 0;
	}
	
	
	public static void flush() {
		
		//bind textures
		for (int i = 0; i <= sData.getTextureSlotIndex(); i++) {
			RendererAPI.get().bindTexture(i, sData.getTextureSlot()[i]);
		}
		
		sData.updateData();
		RendererAPI.get().drawIndexed(sData.getVertexArray(), sData.getQuadCount());
		drawCalls++;
		sData.startQuadPointer();
	}
	
	
	public static void flushAndReset() {
		
		flush();
		sData.setTextureSlotIndex(1);
		
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
		
	}
	
	
	public static void drawQuad(Vector2f position, Vector2f size, Texture2D texture) {
		Renderer2D.drawQuad(tmpVec3.set(position, 0), size, texture);		
	}
	
	
	public static void drawQuad(Vector3f position, Vector2f size, Texture2D texture) {
		Renderer2D.drawQuad(position, size, texture, Colors.WHITE);	
	}
		
	
	public static void drawQuad(Vector2f position, Vector2f size, Texture2D texture, Vector4f color) {
		Renderer2D.drawQuad(tmpVec3.set(position, 0), size, texture, color);		
	}
	
	
	public static void drawQuad(Vector3f position, Vector2f size, Texture2D texture, Vector4f color) {

		float textureIndex = -1;
		for (int i = 0; i < sData.getTextureSlotIndex(); i++) {
			
			if (sData.getTextureSlot()[i] == texture.getRendererId()) {
				textureIndex = (float) i;
				break;
			}
		}

		if (textureIndex == -1)
		{
			if (sData.getTextureSlotIndex() >= Renderer2DStorage.MAX_TEXTURE_SLOTS)
				flushAndReset();

			textureIndex = (float)sData.getTextureSlotIndex();
			sData.getTextureSlot()[sData.getTextureSlotIndex()] = texture.getRendererId();
			
			sData.setTextureSlotIndex(sData.getTextureSlotIndex()+1);
		}
		
		//BOTTOM - LEFT
		sData.getQuadPointer().position.set(position);
		sData.getQuadPointer().color.set(color);
		sData.getQuadPointer().texCoord.set(0, 0);
		sData.getQuadPointer().texIndex = textureIndex;
		sData.increaseQuadPointer();
		
		//BOTTOM - RIGHT
		sData.getQuadPointer().position.set(position.x + size.x, position.y, position.z);
		sData.getQuadPointer().color.set(color);
		sData.getQuadPointer().texCoord.set(1, 0);
		sData.getQuadPointer().texIndex = textureIndex;
		sData.increaseQuadPointer();
		
		//UP - RIGHT
		sData.getQuadPointer().position.set(position.x + size.x, position.y + size.y, position.z);
		sData.getQuadPointer().color.set(color);
		sData.getQuadPointer().texCoord.set(1, 1);
		sData.getQuadPointer().texIndex = textureIndex;
		sData.increaseQuadPointer();
		
		//UP - LEFT
		sData.getQuadPointer().position.set(position.x, position.y + size.y, position.z);
		sData.getQuadPointer().color.set(color);
		sData.getQuadPointer().texCoord.set(0, 1);
		sData.getQuadPointer().texIndex = textureIndex;
		sData.increaseQuadPointer();
		
		
		if (sData.mustFlush())	flush();

	}
	
	
	public static void drawQuad(Vector2f position, Vector2f size, SubTexture2D subtexture) {
		Renderer2D.drawQuad(tmpVec3.set(position, 0), size, subtexture, Colors.WHITE);
	}
	
	private static Matrix4f tmpTrans = new Matrix4f();
	private static Vector3f tmpVec = new Vector3f();
	public static void drawQuad(Vector3f position, Vector2f size, SubTexture2D subtexture, Vector4f color) {

		float textureIndex = -1;
		for (int i = 0; i < sData.getTextureSlotIndex(); i++) {
			
			if (sData.getTextureSlot()[i] == subtexture.getTexture().getRendererId()) {
				textureIndex = (float) i;
				break;
			}
		}

		if (textureIndex == -1)
		{
			if (sData.getTextureSlotIndex() >= Renderer2DStorage.MAX_TEXTURE_SLOTS)
				flushAndReset();

			textureIndex = (float)sData.getTextureSlotIndex();
			sData.getTextureSlot()[sData.getTextureSlotIndex()] = subtexture.getTexture().getRendererId();
			
			sData.setTextureSlotIndex(sData.getTextureSlotIndex()+1);
		}
		
		tmpTrans.identity().translate(position).scale(size.x, size.y, 1);
		
		for (int i = 0; i < 4; i++) {
			sData.getQuadPointer().position.set(tmpVec.set(quadVertexPositions[i]).mulPosition(tmpTrans));
			sData.getQuadPointer().color.set(color);
			sData.getQuadPointer().texCoord.set(subtexture.getTexCoords()[i]);
			sData.getQuadPointer().texIndex = textureIndex;
			sData.increaseQuadPointer();
		}
				
		if (sData.mustFlush())	flush();

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
