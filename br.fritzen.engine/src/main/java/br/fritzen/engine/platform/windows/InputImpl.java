package br.fritzen.engine.platform.windows;

import org.lwjgl.glfw.GLFW;

import br.fritzen.engine.Application;
import br.fritzen.engine.core.input.Input;

public class InputImpl implements Input {

	@Override
	public boolean isKeyPressed(int keycode) {
		
		long windowNative = Application.getInstance().getWindow().getNativeWindow();
		int state = GLFW.glfwGetKey(windowNative, keycode);
		
		if (state == GLFW.GLFW_PRESS || state == GLFW.GLFW_REPEAT)
			return true;
		
		return false;
	}

	
	
}
