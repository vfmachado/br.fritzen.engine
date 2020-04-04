package br.fritzen.engine.platform.opengl;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GLUtil;
import org.lwjgl.system.Callback;
import org.lwjgl.system.Configuration;

import br.fritzen.engine.core.EngineLog;
import br.fritzen.engine.core.EngineState;
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
		
		if (EngineState.DEBUG_OPENGL) {
			Configuration.DEBUG.set(true);
			Callback debugProc = GLUtil.setupDebugMessageCallback();
		}
		
		EngineLog.info("OpenGL Info");
		EngineLog.info("   Vendor: " + GL11.glGetString(GL11.GL_VENDOR));
		EngineLog.info("   Renderer: " + GL11.glGetString(GL11.GL_RENDERER));
		EngineLog.info("   Version:" + GL11.glGetString(GL11.GL_VERSION));
		
		
	}

	
	@Override
	public void swapBuffers() {
		
		GLFW.glfwSwapBuffers(this.windowHandler);
		
	}


	@Override
	public String getVendor() {
		return GL11.glGetString(GL11.GL_VENDOR);
	}


	@Override
	public String getRenderer() {
		return GL11.glGetString(GL11.GL_RENDERER);
	}


	@Override
	public String getVersion() {
		return GL11.glGetString(GL11.GL_VERSION);
	}

	
	
}
