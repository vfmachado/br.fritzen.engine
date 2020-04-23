package br.fritzen.engine.renderer;

import java.nio.ByteBuffer;

public abstract class Texture {

	public abstract String getName();
	
	public abstract int getWidth();
	
	public abstract int getHeight();
	
	public abstract TextureFormat getFormat();
	
	public abstract void setData(ByteBuffer data, int size);
	
	public abstract void bind();
	
	public abstract void bind(int slot);
	
	public abstract void bindAsRenderTarget();
	
	public abstract void unbind();
	
	public abstract int getRendererId();

	public abstract int getPlatformFormat(TextureFormat format);
	
	public abstract void cleanup();
	
}
