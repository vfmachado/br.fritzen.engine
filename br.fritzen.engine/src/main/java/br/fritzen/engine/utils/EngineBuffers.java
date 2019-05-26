package br.fritzen.engine.utils;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;

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
