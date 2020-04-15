package br.fritzen.engine.renderer.shadow;

import java.nio.ByteBuffer;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
import org.lwjgl.opengl.GL13;

import br.fritzen.engine.renderer.Texture2D;
import br.fritzen.engine.renderer.TextureFormat;

public class ShadowTexture extends Texture2D {

	private int id;
	
	private int width;
	
	private int height;
	
	
	public ShadowTexture(int width, int height, int pixelFormat) throws Exception {
	    
		this.id = GL11.glGenTextures();
	    this.width = width;
	    this.height = height;
	    
	    GL11.glBindTexture(GL11.GL_TEXTURE_2D, this.id);
	    GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_DEPTH_COMPONENT, this.width, this.height, 0, pixelFormat, GL11.GL_FLOAT, (ByteBuffer) null);
	    
	    GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);
	    GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
	    
	    GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL12.GL_CLAMP_TO_EDGE);
	    GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL12.GL_CLAMP_TO_EDGE);
	    
	    //float borderColor[] = { 1.0f, 1.0f, 1.0f, 1.0f };
//	    /GL11.glTexParameterfv(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_BORDER_COLOR, borderColor);  
	}

	
	public int getId() {
		return id;
	}


	public void cleanup() {
		GL11.glDeleteTextures(this.id);
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
		GL13.glActiveTexture(GL13.GL_TEXTURE0 + slot);	
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, this.id);
	}


	@Override
	public void unbind() {
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, this.id);
	}


	@Override
	public int getRendererId() {
		return this.id;
	}


	@Override
	public int getPlatformFormat(TextureFormat format) {
		return 0;
	}
	
	
}
