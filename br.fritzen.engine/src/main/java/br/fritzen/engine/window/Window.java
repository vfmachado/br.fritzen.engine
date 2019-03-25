package br.fritzen.engine.window;

import static org.lwjgl.system.MemoryUtil.NULL;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL;

import br.fritzen.engine.core.EngineLog;
import br.fritzen.engine.core.EngineState;

public class Window {

	private long handler;

	private int width;
	
	private int height;
	
	private String title;
	
	
	public Window(int width, int height, String title) {
		
		this.width = width;
		this.height = height;
		this.title = title;
		
		init();
		
	}
		
	
	public void cleanUp() {
		
		// Free the window callbacks and destroy the window
		//GLFW.glfwFreeCallbacks(this.handler);
		GLFW.glfwDestroyWindow(this.handler);

		// Terminate GLFW and free the error callback
		GLFW.glfwTerminate();
		GLFW.glfwSetErrorCallback(null).free();
	}
	
		
	private void init() {
		
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
		this.setVSync(EngineState.VSync);
		
		GL.createCapabilities();

		GLFW.glfwShowWindow(handler);
	}
	
	
	private void setVSync(boolean enabled) {
		if (enabled)
			GLFW.glfwSwapInterval(1);
		else
			GLFW.glfwSwapInterval(0);

	}
	
	public void onUpdate() {
		
		GLFW.glfwSwapBuffers(this.handler);
		GLFW.glfwPollEvents();
		
	}
	
	
	public long getHandler() {
		return handler;
	}

	
	public long getNativeWindow() {
		return this.getHandler();
	}
	

	public int getWidth() {
		return this.width;
	}
	
	
	public int getHeight() {
		return this.height;
	}
	
}
