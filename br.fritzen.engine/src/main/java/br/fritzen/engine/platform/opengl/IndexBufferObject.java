package br.fritzen.engine.platform.opengl;

import java.nio.IntBuffer;
import java.util.logging.Logger;

import org.lwjgl.opengl.GL15;

public class IndexBufferObject {

	private final static Logger LOG = Logger.getLogger(IndexBufferObject.class.getName());
	
	private int ibo;
	
	private int count;
	
	private int offset;
	
	
	public IndexBufferObject(IntBuffer indexBuffer) {
		
		this.ibo = GL15.glGenBuffers();
		this.setData(indexBuffer);
		
		if (this.ibo == 0) {
			LOG.warning("Problem creating IBO");
		}
		
	}
	
	
	public void bind() {
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, this.ibo);
	}
	
	
	public void unbind() {
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);
	}
	
	
	public void setData(IntBuffer indexBuffer) {
		this.bind();
		GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, indexBuffer, GL15.GL_STATIC_DRAW);
		this.count = indexBuffer.capacity();
	}


	public int getOffset() {
		return offset;
	}


	public void setOffset(int offset) {
		this.offset = offset;
	}


	public int getCount() {
		return count;
	}
	
	
	
}
