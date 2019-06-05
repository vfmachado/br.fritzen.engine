package br.fritzen.engine.platform.opengl;

import org.lwjgl.opengl.GL11;

public class VertexBufferLayout {

	private int size;
	
	private int type;
	
	private boolean normalized;
	
		
	public VertexBufferLayout(int size) {
		//this(size, GL11.GL_FLOAT);
		this(size, 4);
	}
	
	
	public VertexBufferLayout(int size, int type) {
		this(size, type, false);
	}
	
	
	public VertexBufferLayout(int size, int type, boolean normalized) {
	
		this.size = size;
		this.type = type;
		this.normalized = normalized;
	
	}


	public int getSize() {
		return size;
	}


	public int getType() {
		return type;
	}


	public boolean isNormalized() {
		return normalized;
	}
	
	
	
}
