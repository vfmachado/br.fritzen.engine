package br.fritzen.engine.platform.opengl;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL30;
import org.lwjgl.stb.STBImage;

import br.fritzen.engine.core.EngineLog;
import br.fritzen.engine.core.EngineState;
import br.fritzen.engine.renderer.Texture;
import br.fritzen.engine.renderer.TextureFormat;
import br.fritzen.engine.utils.EngineFiles;

public class Texture2DGL extends Texture {

	private int id;
	
	private String filename;
	
	private int width;
	
	private int height;
	
	
	public Texture2DGL(String filename) {
		
		this.filename = filename;
		this.id = GL11.glGenTextures();
		
		try {
			this.loadTexture(filename);
		} catch (IOException e) {
			EngineLog.warning("Não carregou a textura: " + filename);
		}
	}
	
	
	@Override
	public void cleanup() {
		GL11.glDeleteTextures(this.id);
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

	
	private void loadTexture(String filename) throws IOException {
		
		IntBuffer width = BufferUtils.createIntBuffer(1);
		IntBuffer height = BufferUtils.createIntBuffer(1);
		IntBuffer channels_b = BufferUtils.createIntBuffer(1);
		
		STBImage.stbi_set_flip_vertically_on_load(true);
		
		File file = new File(EngineFiles.class.getClassLoader().getResource(filename).getFile());
				
		ByteBuffer imgBuffer = STBImage.stbi_load(file.getAbsolutePath(), width, height, channels_b, STBImage.STBI_rgb_alpha);
				
		if (EngineState.DEBUG_TEXTURE) {
			EngineLog.info("Texture loaded: \n\tFile: " + file.getAbsolutePath() + "\n\tWidth: " + width.get(0) + "\n\tHeight: " + height.get(0) + "\n\tChannels: " + channels_b.get(0));
		}
		
		this.width = width.get(0);
		this.height = height.get(0);
		
		int channels = channels_b.get(0);
		
		int internalFormat = 0, dataFormat = 0;
		if (channels == 4) {
			
			internalFormat = GL11.GL_RGBA8;
			dataFormat = GL11.GL_RGBA;
			
		} else if (channels == 3) {
			
			internalFormat = GL11.GL_RGB8;
			dataFormat = GL11.GL_RGB;
			
		}
		
		
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, this.id);
		
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL12.GL_CLAMP_TO_EDGE);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL12.GL_CLAMP_TO_EDGE);
		
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
	
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
