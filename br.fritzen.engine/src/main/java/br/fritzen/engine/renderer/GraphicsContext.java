package br.fritzen.engine.renderer;

public abstract class GraphicsContext {

	public abstract void init();
		
	public abstract void swapBuffers();
	
	public abstract void bindAsRenderTarget();
	
	public abstract String getVendor();
	
	public abstract String getRenderer();
	
	public abstract String getVersion();
}
