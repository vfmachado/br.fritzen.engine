package br.fritzen.engine.utils;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;

import br.fritzen.engine.renderer.Renderer2DStorage.QuadVertex;

public class EngineBuffers {

	public static IntBuffer createIntBuffer(int[] values) {
		IntBuffer intBuffer = BufferUtils.createIntBuffer(values.length);
		intBuffer.put(values);
		intBuffer.flip();
		return intBuffer;
	}
	
	
	public static FloatBuffer createFloatBuffer(float[] values) {
		FloatBuffer floatBuffer = BufferUtils.createFloatBuffer(values.length);
		floatBuffer.put(values);
		floatBuffer.flip();
		return floatBuffer;
	}


	public static FloatBuffer updateFloatBuffer(FloatBuffer floatBuffer, QuadVertex[] quadVertexBuffer, int quadIndex) {
		
		
		for (int i = 0; i < quadIndex; i++) {
		
			floatBuffer.put(quadVertexBuffer[i].position.x);
			floatBuffer.put(quadVertexBuffer[i].position.y);
			floatBuffer.put(quadVertexBuffer[i].position.z);
			
			floatBuffer.put(quadVertexBuffer[i].texCoord.x);
			floatBuffer.put(quadVertexBuffer[i].texCoord.y);
			
			floatBuffer.put(quadVertexBuffer[i].color.x);
			floatBuffer.put(quadVertexBuffer[i].color.y);
			floatBuffer.put(quadVertexBuffer[i].color.z);
			floatBuffer.put(quadVertexBuffer[i].color.w);
			
			floatBuffer.put(quadVertexBuffer[i].texIndex);
			floatBuffer.put(quadVertexBuffer[i].tillingFactor);
		}
		
		floatBuffer.rewind();
		return floatBuffer;
	}
	

	
	/*
	public static FloatBuffer createFloatBuffer(List<Vertex> vertices) {
		FloatBuffer floatBuffer = BufferUtils.createFloatBuffer(vertices.size() * Vertex.SIZE);
		
		for (Vertex v :  vertices) {

			floatBuffer.put(v.getPosition().x);
			floatBuffer.put(v.getPosition().y);
			floatBuffer.put(v.getPosition().z);

			floatBuffer.put(v.getTexture().x);
			floatBuffer.put(v.getTexture().y);

			floatBuffer.put(v.getNormal().x);
			floatBuffer.put(v.getNormal().y);
			floatBuffer.put(v.getNormal().z);

			floatBuffer.put(v.getTangent().x);
			floatBuffer.put(v.getTangent().y);
			floatBuffer.put(v.getTangent().z);

		}
		
		floatBuffer.flip();
		return floatBuffer;
	}
	*/
}
