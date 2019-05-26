package br.fritzen.engine.platform.opengl;

import org.lwjgl.opengl.GL20;

public enum OpenGLShaderType {

	VERTEX(GL20.GL_VERTEX_SHADER),
	FRAGMENT(GL20.GL_FRAGMENT_SHADER);
	
	private int shaderCode;
	
	
	private OpenGLShaderType(int shaderCode) {
		this.shaderCode = shaderCode;
	}
	
	
	public int getShaderCode() {
		return this.shaderCode;
	}
}
