package br.fritzen.engine.platform.opengl;

import java.nio.FloatBuffer;
import java.util.logging.Logger;

import org.lwjgl.opengl.GL15;

public class VertexBufferObject {

	private final static Logger LOG = Logger.getLogger(VertexBufferObject.class.getName());
	
	private int vbo;
	
	
	public VertexBufferObject() {
		
		this.vbo = GL15.glGenBuffers();
		
		if (this.vbo == 0) {
			LOG.warning("Problem creating VBO");
		}
		
	}
	
	
	public VertexBufferObject(FloatBuffer data) {
		this();
		this.setData(data);
	}
	
	
	public void bind() {
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, this.vbo);
	}
	
	
	public void unbind() {
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
	}
	
	
	public void setData(FloatBuffer data) {
		this.bind();
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, data, GL15.GL_STATIC_DRAW);
	}
}
