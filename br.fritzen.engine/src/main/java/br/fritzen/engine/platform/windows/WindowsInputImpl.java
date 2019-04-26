package br.fritzen.engine.platform.windows;

import org.joml.Vector2f;
import org.lwjgl.glfw.GLFW;

import br.fritzen.engine.Application;
import br.fritzen.engine.core.input.Input;

public class WindowsInputImpl extends Input {
	
	private Vector2f mousePos = new Vector2f();
	
	
	public static void init() {
		Input.instance = new WindowsInputImpl();
	}
	
	
	@Override
	public boolean isKeyPressedImpl(int keycode) {
		
		long windowNative = Application.getWindow().getNativeWindow();
		int state = GLFW.glfwGetKey(windowNative, keycode);
		
		return state == GLFW.GLFW_PRESS || state == GLFW.GLFW_REPEAT;
		
	}


	@Override
	public boolean isMouseButtonImpl(int button) {
		
		long windowNative = Application.getWindow().getNativeWindow();
		int state = GLFW.glfwGetMouseButton(windowNative, button);
		
		return state == GLFW.GLFW_PRESS || state == GLFW.GLFW_REPEAT;
		
	}


	private double x[] = {0}, y[] = {0};
	
	@Override
	public Vector2f getMousePosImpl() {
		
		long windowNative = Application.getWindow().getNativeWindow();
		GLFW.glfwGetCursorPos(windowNative, x, y);
		
		this.mousePos.set((float)x[0], (float)y[0]);
		return this.mousePos;
		
	}

	
	
}
