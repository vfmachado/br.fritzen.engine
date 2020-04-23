package br.fritzen.engine.platform.opengl;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL14;
import org.lwjgl.opengl.GL30;
import org.lwjgl.opengl.GL32;
import org.lwjgl.stb.STBImage;

import br.fritzen.engine.core.EngineLog;
import br.fritzen.engine.core.EngineState;
import br.fritzen.engine.renderer.Texture2D;
import br.fritzen.engine.renderer.TextureFormat;
import br.fritzen.engine.utils.EngineFiles;
import lombok.Getter;

public class OpenGLTexture2D extends Texture2D {

	private int id;
	
	private int frameBufferID;
	
	private String filename;
	
	@Getter
	private int width;
	
	@Getter
	private int height;
	
	private int internalFormat;
	
	private int dataFormat;
	
	public OpenGLTexture2D(String filename) {
		
		this.filename = filename;
		this.id = GL11.glGenTextures();
		
		try {
			this.loadTexture(filename);
		} catch (IOException e) {
			EngineLog.warning("Não carregou a textura: " + filename);
		}
	}
	
	
	public OpenGLTexture2D(int width, int height) {
		
		this.width = width;
		this.height = height;
		
		this.internalFormat = GL11.GL_RGBA8;
		this.dataFormat = GL11.GL_RGBA;
				
		this.id = GL11.glGenTextures();
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, this.id);
		
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL12.GL_NEAREST);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL12.GL_NEAREST);
		
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL12.GL_REPEAT);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL12.GL_REPEAT);
		
		
		GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL14.GL_TEXTURE_LOD_BIAS, -0.4f);
		
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
		
	}


	@Override
	public void cleanup() {
		GL11.glDeleteTextures(this.id);
		
		if (frameBufferID != 0)
			GL30.glDeleteFramebuffers(this.frameBufferID);
	}
	
	
	@Override
	public TextureFormat getFormat() {
		// TODO Auto-generated method stub
		return null;
	}

	
	@Override
	public void bind(int slot) {
		GL13.glActiveTexture(GL13.GL_TEXTURE0 + slot);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, this.id);
	}
	
	
	@Override
	public void bind() {
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, this.id);
	}

	
	@Override
	public void bindAsRenderTarget() {
		
		this.bind();
		
		if (frameBufferID == 0) {
			
			//create and bind
			frameBufferID = GL30.glGenFramebuffers();
			GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, frameBufferID);
			
			//GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, this.internalFormat, this.width, this.height, 0,  this.dataFormat, GL11.GL_UNSIGNED_BYTE, 0);
			GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, this.internalFormat, this.width, this.height, 0,  this.dataFormat, GL11.GL_UNSIGNED_BYTE, 0);
			
			//associate to a texture
			GL32.glFramebufferTexture(GL30.GL_FRAMEBUFFER, GL32.GL_COLOR_ATTACHMENT0, this.id, 0);
			
			//render buffer -> it should fix stencil and depth
			int renderbuffer = 0;
			renderbuffer = GL30.glGenRenderbuffers();
			GL30.glBindRenderbuffer(GL30.GL_RENDERBUFFER, renderbuffer);
			GL30.glRenderbufferStorage(GL30.GL_RENDERBUFFER, GL30.GL_DEPTH24_STENCIL8, this.width, this.height);
			GL30.glFramebufferRenderbuffer(GL30.GL_FRAMEBUFFER, GL30.GL_DEPTH_STENCIL_ATTACHMENT, GL30.GL_RENDERBUFFER, renderbuffer);
		}
		
		
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
		GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, frameBufferID);
		GL11.glViewport(0, 0, this.getWidth(), this.getHeight());
				
	}
	
	
	@Override
	public void unbind() {
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
	}

	
	@Override
	public int getRendererId() {
		return this.id;
	}

	
	@Override
	public int getPlatformFormat(TextureFormat format) {
		// TODO Auto-generated method stub
		return 0;
	}

	
	@Override
	public void setData(ByteBuffer data, int size) {
		
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, this.id);
		
		GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, this.internalFormat, this.width, this.height, 0, this.dataFormat, GL11.GL_UNSIGNED_BYTE, data);
		GL30.glGenerateMipmap(GL11.GL_TEXTURE_2D);
		
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
	}
	
	
	private void loadTexture(String filename) throws IOException {
		
		if (EngineState.DEBUG_TEXTURE) {
			EngineLog.info("Loading texture: " + filename);
		}
		
		IntBuffer width = BufferUtils.createIntBuffer(1);
		IntBuffer height = BufferUtils.createIntBuffer(1);
		IntBuffer channels_b = BufferUtils.createIntBuffer(1);
		
		STBImage.stbi_set_flip_vertically_on_load(true);
		
		File file = new File(EngineFiles.class.getClassLoader().getResource(filename).getFile());
				
		ByteBuffer imgBuffer = STBImage.stbi_load(file.getAbsolutePath(), width, height, channels_b, STBImage.STBI_default);
				
		if (EngineState.DEBUG_TEXTURE) {
			EngineLog.info("Texture loaded: \n\tFile: " + file.getAbsolutePath() + "\n\tWidth: " + width.get(0) + "\n\tHeight: " + height.get(0) + "\n\tChannels: " + channels_b.get(0));
		}
		
		this.width = width.get(0);
		this.height = height.get(0);
		
		int channels = channels_b.get(0);
		
		this.internalFormat = 0;
		this.dataFormat = 0;
		
		if (channels == 4) {
			
			internalFormat = GL11.GL_RGBA8;
			dataFormat = GL11.GL_RGBA;
			
		} else if (channels == 3) {
			
			internalFormat = GL11.GL_RGB8;
			dataFormat = GL11.GL_RGB;
			
		}
		
		
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, this.id);
		
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
		
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL12.GL_REPEAT);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL12.GL_REPEAT);
		
		GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, internalFormat, this.width, this.height, 0, dataFormat, GL11.GL_UNSIGNED_BYTE, imgBuffer);
		GL30.glGenerateMipmap(GL11.GL_TEXTURE_2D);
		
		//clear
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
		STBImage.stbi_image_free(imgBuffer);
		
	}


	@Override
	public String getName() {
		return this.filename;
	}
	
}
