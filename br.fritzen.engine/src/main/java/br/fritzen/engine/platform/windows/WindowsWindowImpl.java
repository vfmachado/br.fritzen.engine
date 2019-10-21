package br.fritzen.engine.platform.windows;

import static org.lwjgl.system.MemoryUtil.NULL;
import static org.lwjgl.system.MemoryUtil.memUTF8;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWVidMode;

import br.fritzen.engine.core.EngineLog;
import br.fritzen.engine.core.EngineState;
import br.fritzen.engine.events.Event;
import br.fritzen.engine.events.key.KeyPressedEvent;
import br.fritzen.engine.events.key.KeyReleasedEvent;
import br.fritzen.engine.events.key.KeyTypedEvent;
import br.fritzen.engine.events.mouse.MouseButtonPressedEvent;
import br.fritzen.engine.events.mouse.MouseButtonReleasedEvent;
import br.fritzen.engine.events.mouse.MouseMovedEvent;
import br.fritzen.engine.events.mouse.MouseScrolledEvent;
import br.fritzen.engine.events.window.WindowCloseEvent;
import br.fritzen.engine.events.window.WindowResizeEvent;
import br.fritzen.engine.platform.opengl.OpenGLContext;
import br.fritzen.engine.window.Window;

public class WindowsWindowImpl extends Window {

	private long handler;
	
	private boolean vsync;
		
	private long primaryMonitor;
	
	private GLFWVidMode monitorVideoMode;
	
	
	private class WindowedParams {
		int width;
		int height;
		int posx;
		int posy;
	}
	
	private WindowedParams windowedParams;
	
	
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
		
		this.windowMode = WindowMode.WINDOWED;
		
		windowedParams = new WindowedParams();
		windowedParams.width = width;
		windowedParams.height = height;
		windowedParams.posx = 0;
		windowedParams.posy = 20;
		
		if (!GLFW.glfwInit()) {
			
			throw new IllegalStateException("Unable to initialize GLFW");
			
		} else {
			
			EngineLog.info("GLFW initialized!");
		
			GLFW.glfwSetErrorCallback((error, text) -> {
				EngineLog.severe(String.format("GLFW error [0x%X]: %s", error, getDescription(text)));
			});
			
		}
		
		primaryMonitor = GLFW.glfwGetPrimaryMonitor();
		monitorVideoMode = GLFW.glfwGetVideoMode(primaryMonitor);

		EngineLog.info(String.format("Video Mode details: %dx%d - %dHz (RxGxB) %dx%dx%d  ",
				monitorVideoMode.width(),
				monitorVideoMode.height(),
				monitorVideoMode.refreshRate(),
				monitorVideoMode.redBits(),
				monitorVideoMode.greenBits(),
				monitorVideoMode.blueBits()
				)
		);
		
		
		EngineLog.info("Creating window " + this.title + " (" + this.width + ", " + this.height + ")");
		this.handler = createWindow();
		
		this.setWindowMode(WindowMode.WINDOWED);
		
		vsync = EngineState.VSYNC;
		this.setVSync(vsync);
		
		GLFW.glfwShowWindow(this.handler);
		
		WindowsInputImpl.init();
	}
	
	
	private void setCallBacks(long handler) {
		
		//-----------------------MOUSE EVENTS-----------------------------
		
		GLFW.glfwSetCursorPosCallback(handler, (window, posx, posy) -> {
			
			Event event = new MouseMovedEvent((float)posx, (float)posy);
			this.eventCallback(event);
			
		});
		
		
		GLFW.glfwSetMouseButtonCallback(handler, (window, button, action, mods) -> {
		
				Event event;
				
				switch (action) {
				
					case GLFW.GLFW_PRESS:
						event = new MouseButtonPressedEvent(button);
						this.eventCallback(event);
						break;
					
					case GLFW.GLFW_RELEASE:
						event = new MouseButtonReleasedEvent(button);
						this.eventCallback(event);
						break;
					
				}
		});
		
		
		GLFW.glfwSetScrollCallback(handler, (window, xoffset, yoffset) -> {
			
			Event event = new MouseScrolledEvent((float)xoffset, (float)yoffset);
			this.eventCallback(event);
		
		});
		
		
		//-----------------------KEYBOARD EVENTS-----------------------------
		
		GLFW.glfwSetKeyCallback(handler, (window, key, scancode, action, mods) -> {
			
			Event event;
			switch (action) {
				
				case GLFW.GLFW_PRESS:
					event = new KeyPressedEvent(key, 0);
					this.eventCallback(event);
					break;
				
				case GLFW.GLFW_RELEASE:
					event = new KeyReleasedEvent(key);
					this.eventCallback(event);
					break;
				
				case GLFW.GLFW_REPEAT:
					event = new KeyPressedEvent(key, 1);
					this.eventCallback(event);
					break;
				
			}
			
		});
		
		
		GLFW.glfwSetCharCallback(handler, (window, keycode) -> {
			
			Event event = new KeyTypedEvent(keycode); 
			this.eventCallback(event);
			
		});
		
		
		//-----------------------WINDOW EVENTS-----------------------------
		
		GLFW.glfwSetWindowCloseCallback(handler, (window) -> {
			Event event = new WindowCloseEvent();
			this.eventCallback(event);
		});
				
		
		GLFW.glfwSetWindowSizeCallback(handler, (window, width, height) -> {
			this.setWindowSize(width, height);
			Event event = new WindowResizeEvent(width, height);
			this.eventCallback(event);
			
		});
	}
	
	
	private long createWindow() {
		
		long handler = GLFW.glfwCreateWindow(this.width, this.height, this.title, NULL, NULL);
		
		if (handler == NULL ) {
			throw new RuntimeException("Failed to create the GLFW window");
		} else { 
			EngineLog.info("Window created with no errors. Handler: " + handler);
		}
		
		
		//GLFW.glfwMakeContextCurrent(handler);
		this.context = new OpenGLContext(handler);
		this.context.init();
		
		this.setCallBacks(handler);
		
		return handler;
		
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
		
		GLFW.glfwPollEvents();
		//GLFW.glfwSwapBuffers(this.handler);
		this.context.swapBuffers();
		
	}
	
	
	@Override
	public long getNativeWindow() {
		return this.handler;
	}
	

	private String getDescription(long description) {
        return memUTF8(description);
    }


	@Override
	public void setWindowMode(WindowMode mode) {
		
		if (this.windowMode == mode) {
			return;
		}
				
		// If currently windowed, stash the current size and position of the window
		if (this.windowMode == WindowMode.WINDOWED) {
			
			this.windowedParams.width = this.width;
			this.windowedParams.height = this.height;
			
			int [] x = {0}, y = {0};
			GLFW.glfwGetWindowPos(this.handler, x, y);
			this.windowedParams.posx = x[0];
			this.windowedParams.posy = y[0];
			
		}
		
		this.windowMode = mode;
		
		long monitor = 0;
		
		GLFW.glfwSetWindowAttrib(this.handler, GLFW.GLFW_RESIZABLE, GLFW.GLFW_TRUE); 
		GLFW.glfwSetWindowAttrib(this.handler, GLFW.GLFW_AUTO_ICONIFY, GLFW.GLFW_FALSE);
		
		if (mode == WindowMode.WINDOWED) {
				
			width = windowedParams.width;
			height = windowedParams.height;
			
			GLFW.glfwSetWindowAttrib(this.handler, GLFW.GLFW_FLOATING, GLFW.GLFW_FALSE);
			GLFW.glfwSetWindowAttrib(this.handler, GLFW.GLFW_DECORATED, GLFW.GLFW_TRUE);
			
			
		} else if (mode == WindowMode.BORDERLESS) {
			
			width = windowedParams.width;
			height = windowedParams.height;
		
			GLFW.glfwSetWindowAttrib(this.handler, GLFW.GLFW_FLOATING, GLFW.GLFW_TRUE);
			GLFW.glfwSetWindowAttrib(this.handler, GLFW.GLFW_DECORATED, GLFW.GLFW_FALSE);
			
		} else if (mode == WindowMode.FULL_SCREEN) {
			
			width = monitorVideoMode.width();
			height = monitorVideoMode.height();
			monitor = primaryMonitor;
			
			GLFW.glfwSetWindowSize(this.handler, this.width, this.height);
		}
		
		GLFW.glfwSetWindowMonitor(this.handler, monitor, windowedParams.posx, windowedParams.posy, this.width, this.height, monitorVideoMode.refreshRate());
			
	}


	@Override
	public void setWindowSize(int width, int height) {

		this.width = width;
		this.height = height;
		
		//TODO unknown if here is the best place
		
		GLFW.glfwSetWindowSize(this.handler, this.width, this.height);
			
	}
}
