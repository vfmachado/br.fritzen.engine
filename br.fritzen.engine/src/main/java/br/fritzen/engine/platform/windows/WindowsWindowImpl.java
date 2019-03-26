package br.fritzen.engine.platform.windows;

import static org.lwjgl.system.MemoryUtil.NULL;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL;

import br.fritzen.engine.core.EngineLog;
import br.fritzen.engine.core.EngineState;
import br.fritzen.engine.window.Window;

public class WindowsWindowImpl extends Window {

	
	private long handler;
	
	private boolean vsync;
			
	
	public WindowsWindowImpl(int width, int height, String title) {
		super(width, height, title);
	}


	@Override
	public void cleanUp() {
		
		// Free the window callbacks and destroy the window
		//GLFW.glfwFreeCallbacks(this.handler);
		GLFW.glfwDestroyWindow(this.handler);

		// Terminate GLFW and free the error callback
		GLFW.glfwTerminate();
		GLFW.glfwSetErrorCallback(null).free();
	}
	
		
	@Override
	protected void init() {
		
		GLFWErrorCallback.createPrint(System.err).set();
		
		if (!GLFW.glfwInit()) {
			throw new IllegalStateException("Unable to initialize GLFW");
		} else {
			EngineLog.info("GLFW initialized!");
		}
		
		// Configure GLFW
		GLFW.glfwDefaultWindowHints(); // optional, the current window hints are already the default
		GLFW.glfwWindowHint(GLFW.GLFW_VISIBLE, GLFW.GLFW_FALSE); // the window will stay hidden after creation
		GLFW.glfwWindowHint(GLFW.GLFW_RESIZABLE, GLFW.GLFW_TRUE); // the window will be resizable

		GLFW.glfwWindowHint(GLFW.GLFW_SAMPLES, EngineState.MSAA_SAMPLES);


		EngineLog.info("Creating window " + this.title + " (" + this.width + ", " + this.height + ")");
		handler = GLFW.glfwCreateWindow(this.width, this.height, this.title, NULL, NULL);
		
		if (handler == NULL ) {
			throw new RuntimeException("Failed to create the GLFW window");
		} else { 
			EngineLog.info("Window created with no errors");
		}
		
		GLFW.glfwMakeContextCurrent(handler);
		
		// Enable v-sync according to EngineState
		vsync = EngineState.VSync;
		this.setVSync(vsync);
		
		GL.createCapabilities();

		GLFW.glfwShowWindow(handler);
	}
	
	
	public void setVSync(boolean enabled) {
		
		this.vsync = enabled;
		if (enabled)
			GLFW.glfwSwapInterval(1);
		else
			GLFW.glfwSwapInterval(0);

	}
	
	
	public boolean isVSync() {
		return this.vsync;
	}
	
	
	@Override
	public void onUpdate() {
		
		GLFW.glfwSwapBuffers(this.handler);
		GLFW.glfwPollEvents();
		
	}
	
	
	@Override
	public long getNativeWindow() {
		return this.handler;
	}
	

}
