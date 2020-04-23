package br.fritzen.engine.renderer.shadow;

import java.nio.ByteBuffer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL12.*;
import static org.lwjgl.opengl.GL13.*;
import static org.lwjgl.opengl.GL14.*;
import static org.lwjgl.opengl.GL30.*;

import br.fritzen.engine.renderer.Texture2D;
import br.fritzen.engine.renderer.TextureFormat;

public class ShadowTexture extends Texture2D {

	private int id;
	
	private int width;
	
	private int height;
	
	
	public ShadowTexture(int width, int height, int pixelFormat) throws Exception {
	    
		this.id = glGenTextures();
	    this.width = width;
	    this.height = height;
	    
	    glBindTexture(GL_TEXTURE_2D, this.id);
	    glTexImage2D(GL_TEXTURE_2D, 0, GL_DEPTH_COMPONENT16, this.width, this.height, 0, pixelFormat, GL_FLOAT, 0);
	    //glTexImage2D(GL_TEXTURE_2D, 0, GL_RGB, this.width, this.height, 0, GL_RGB, GL_UNSIGNED_BYTE, 0);
	    
	    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
	    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
	    
	    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE);
	    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE);
	    
	    //float borderColor[] = { 1.0f, 1.0f, 1.0f, 1.0f };
	    //glTexParameterfv(GL_TEXTURE_2D, GL_TEXTURE_BORDER_COLOR, borderColor);  
	}

	
	public int getId() {
		return id;
	}


	public void cleanup() {
		glDeleteTextures(this.id);
	}


	@Override
	public String getName() {
		return "Shadow Map texture";
	}


	@Override
	public int getWidth() {
		return this.width;
	}


	@Override
	public int getHeight() {
		return this.height;
	}


	@Override
	public TextureFormat getFormat() {
		return null;
	}


	@Override
	public void setData(ByteBuffer data, int size) {
		
	}


	@Override
	public void bind() {
		
	}


	@Override
	public void bind(int slot) {
		glActiveTexture(GL_TEXTURE0 + slot);	
		glBindTexture(GL_TEXTURE_2D, this.id);
	}


	@Override
	public void unbind() {
		glActiveTexture(GL_TEXTURE0);
		glBindTexture(GL_TEXTURE_2D, this.id);
	}


	@Override
	public int getRendererId() {
		return this.id;
	}


	@Override
	public int getPlatformFormat(TextureFormat format) {
		return 0;
	}


	@Override
	public void bindAsRenderTarget() {
		
	}
	
	
}
