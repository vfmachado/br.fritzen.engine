package br.fritzen.engine.platform.opengl;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;

import br.fritzen.engine.core.EngineLog;
import br.fritzen.engine.renderer.GraphicsContext;

public class OpenGLContext extends GraphicsContext {

	
	private long windowHandler;
	
	
	public OpenGLContext(long windowHandler) {
		
		this.windowHandler = windowHandler;
	
	}
	
	
	@Override
	public void init() {
	
		GLFW.glfwMakeContextCurrent(windowHandler);
		GL.createCapabilities();
		
		EngineLog.info("OpenGL Info");
		EngineLog.info("   Vendor: " + GL11.glGetString(GL11.GL_VENDOR));
		EngineLog.info("   Renderer: " + GL11.glGetString(GL11.GL_RENDERER));
		EngineLog.info("   Version:" + GL11.glGetString(GL11.GL_VERSION));
				
	}

	
	@Override
	public void swapBuffers() {
		
		GLFW.glfwSwapBuffers(windowHandler);
		
	}

	
	
}
