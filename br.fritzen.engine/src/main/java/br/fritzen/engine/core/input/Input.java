package br.fritzen.engine.core.input;

import org.joml.Vector2f;

public abstract class Input {

	protected static Input instance;
	
	
	public static boolean isKeyPressed(int keycode) {
		return instance.isKeyPressedImpl(keycode);
	}
	
	
	public static boolean isMouseButton(int button) {
		return instance.isMouseButtonImpl(button);
	}
	
	
	public static Vector2f getMousePos() {
		return instance.getMousePosImpl();
	}
	
	
	public abstract boolean isKeyPressedImpl(int keycode);
	
	public abstract boolean isMouseButtonImpl(int button);
	
	public abstract Vector2f getMousePosImpl();
	
}
