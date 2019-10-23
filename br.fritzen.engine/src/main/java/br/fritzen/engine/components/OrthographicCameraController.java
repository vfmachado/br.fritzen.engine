package br.fritzen.engine.components;

import org.joml.Vector3f;

import br.fritzen.engine.core.input.Input;
import br.fritzen.engine.events.Event;
import br.fritzen.engine.events.EventDispatcher;
import br.fritzen.engine.events.key.KeyEvent;
import br.fritzen.engine.events.mouse.MouseScrolledEvent;
import br.fritzen.engine.events.window.WindowResizeEvent;

public class OrthographicCameraController {

	private OrthographicCamera camera;

	private float aspectRatio;

	private float zoomLevel = 1.0f;

	private boolean rotation;

	private Vector3f cameraPosition = new Vector3f(0.0f, 0.0f, 0.0f);

	private float cameraRotation = 0.0f;

	private float cameraTranslationSpeed = 0.1f, cameraRotationSpeed = 180f;

	
	public OrthographicCameraController(float aspectRatio) {

		this(aspectRatio, false);
	}

	
	public OrthographicCameraController(float aspectRatio, boolean rotation) {

		this.aspectRatio = aspectRatio;
		this.camera = new OrthographicCamera(-aspectRatio * zoomLevel, aspectRatio * zoomLevel, -zoomLevel, zoomLevel);
		this.rotation = rotation;

	}

	
	public OrthographicCamera getCamera() {
		return camera;
	}


	public void onUpdate(float deltatime) {
		
		if (Input.isKeyPressed(KeyEvent.KEY_A)) {
			
			cameraPosition.x -= (float) Math.cos(Math.toRadians(cameraRotation)) * cameraTranslationSpeed * deltatime;
			cameraPosition.y -= (float) Math.sin(Math.toRadians(cameraRotation)) * cameraTranslationSpeed * deltatime;
		
			
		} else if (Input.isKeyPressed(KeyEvent.KEY_D)) {
		
			cameraPosition.x += (float) Math.cos(Math.toRadians(cameraRotation)) * cameraTranslationSpeed * deltatime;
			cameraPosition.y += (float) Math.sin(Math.toRadians(cameraRotation)) * cameraTranslationSpeed * deltatime;
		
		} 
		
		if (Input.isKeyPressed(KeyEvent.KEY_W)) {
			
			cameraPosition.x += (float) - Math.sin(Math.toRadians(cameraRotation)) * cameraTranslationSpeed * deltatime;
			cameraPosition.y += (float) Math.cos(Math.toRadians(cameraRotation)) * cameraTranslationSpeed * deltatime;
		
		} else if (Input.isKeyPressed(KeyEvent.KEY_S)) {
		
			cameraPosition.x -= (float) - Math.sin(Math.toRadians(cameraRotation)) * cameraTranslationSpeed * deltatime;
			cameraPosition.y -= (float) Math.cos(Math.toRadians(cameraRotation)) * cameraTranslationSpeed * deltatime;
		
		} 

		if (this.rotation)
		{
			if (Input.isKeyPressed(KeyEvent.KEY_Q))
				cameraRotation += cameraRotationSpeed * deltatime;
			if (Input.isKeyPressed(KeyEvent.KEY_E))
				cameraRotation -= cameraRotationSpeed * deltatime;

			if (cameraRotation > 180.0f)
				cameraRotation -= 360.0f;
			else if (cameraRotation <= -180.0f)
				cameraRotation += 360.0f;

			camera.setRotation(cameraRotation);
		}

		this.camera.setPosition(cameraPosition);

		cameraTranslationSpeed = zoomLevel;

	}
	
	
	private EventDispatcher dispatcher = new EventDispatcher();
	
	public void onEvent(Event e) {

		dispatcher.setEvent(e);
		dispatcher.dispatch(this::onMouseScrolledEvent, MouseScrolledEvent.class);
		dispatcher.dispatch(this::onWindowResizeEvent, WindowResizeEvent.class);
	}

	
	private boolean onMouseScrolledEvent(Event e) {
		
		MouseScrolledEvent evt = (MouseScrolledEvent) e;
		
		zoomLevel -= evt.getYOffset() * 0.25f;
		zoomLevel = Math.max(zoomLevel, 0.25f);
		camera.setProjection(-aspectRatio * zoomLevel, aspectRatio * zoomLevel, -zoomLevel, zoomLevel);
		return false;
		
	}
	
	
	private boolean onWindowResizeEvent(Event e) {
		
		WindowResizeEvent evt = (WindowResizeEvent) e;
		
		aspectRatio = (float)evt.getWidth() / (float)evt.getHeight();
		camera.setProjection(-aspectRatio * zoomLevel, aspectRatio * zoomLevel, -zoomLevel, zoomLevel);
				
		return false;
	}
		
}
