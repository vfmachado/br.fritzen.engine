package br.fritzen.engine.components;

import org.joml.AxisAngle4f;
import org.joml.Quaternionf;
import org.joml.Vector3f;

import br.fritzen.engine.Application;
import br.fritzen.engine.core.EngineState;
import br.fritzen.engine.core.input.Input;
import br.fritzen.engine.events.Event;
import br.fritzen.engine.events.EventType;
import br.fritzen.engine.events.mouse.MouseMovedEvent;
import lombok.Getter;
import lombok.Setter;

public class PerspectiveCameraController {

	private float currentMouseX = 0;

	private float currentMouseY = 0;

	@Setter
	private float speed = 1;

	@Getter
	private PerspectiveCamera camera;


	public PerspectiveCameraController(float posX, float posY, float posZ) {

		this.camera = new PerspectiveCamera(90f, 16f / 9f, 0.1f, 1000f);
		this.camera.setPosition(new Vector3f(posX, posY, posZ));

		this.currentMouseX = Application.getWindow().getWidth() / 2;
		this.currentMouseY = Application.getWindow().getHeight() / 2;

	}


	public void onUpdate(float deltatime) {

		if (Input.isKeyPressed(Input.KEY_W)) {

			this.camera.getPosition().add(getForward().mul(0.05f * this.speed));
			this.camera.recalculateViewProjection();

		} else if (Input.isKeyPressed(Input.KEY_S)) {

			this.camera.getPosition().add(getBack().mul(0.05f * this.speed));
			this.camera.recalculateViewProjection();

		}

		if (Input.isKeyPressed(Input.KEY_A)) {

			this.camera.getPosition().add(getLeft().mul(0.05f * this.speed));
			this.camera.recalculateViewProjection();

		} else if (Input.isKeyPressed(Input.KEY_D)) {

			this.camera.getPosition().add(getRight().mul(0.05f * this.speed));
			this.camera.recalculateViewProjection();

		}

		if (Input.isKeyPressed(Input.KEY_R)) {

			this.camera.getPosition().add(getUp().mul(0.05f * this.speed));
			this.camera.recalculateViewProjection();

		} else if (Input.isKeyPressed(Input.KEY_F)) {

			this.camera.getPosition().add(getDown().mul(0.05f * this.speed));
			this.camera.recalculateViewProjection();

		}

	}


	public void onEvent(Event e) {

		if (e.getEventType() == EventType.MouseMovedEvent) {

			if (!Input.isMouseButton(Input.MOUSE_BUTTON_2)) {
				Application.getWindow().enableMouse();
				return;
			}

			float deltaX = this.currentMouseX - ((MouseMovedEvent) e).getPosX();
			float deltaY = this.currentMouseY - ((MouseMovedEvent) e).getPosY();

			if (deltaX != 0 || deltaY != 0) {

				this.rotate(EngineState.Y_AXIS, deltaX * 0.001f);
				this.rotate(getRight(), deltaY * 0.001f);
				this.camera.recalculateViewProjection();
			}

			Application.getWindow().setCursorPos(this.currentMouseX, this.currentMouseY);
			Application.getWindow().disableMouse();
		}

	}

	private Vector3f auxDirection = new Vector3f();

	private AxisAngle4f axisRotation = new AxisAngle4f();

	private Quaternionf axisRotationQ = new Quaternionf();


	private Vector3f getForward() {
		return auxDirection.set(EngineState.Z_AXIS).mul(-1).rotate(this.camera.getRotation());
	}


	private Vector3f getBack() {
		return auxDirection.set(EngineState.Z_AXIS).rotate(this.camera.getRotation());
	}


	private Vector3f getRight() {
		return auxDirection.set(EngineState.X_AXIS).rotate(this.camera.getRotation());
	}


	private Vector3f getLeft() {
		return auxDirection.set(EngineState.X_AXIS).mul(-1).rotate(this.camera.getRotation());
	}


	private Vector3f getUp() {
		return auxDirection.set(EngineState.Y_AXIS).rotate(this.camera.getRotation());
	}


	private Vector3f getDown() {
		return auxDirection.set(EngineState.Y_AXIS).mul(-1).rotate(this.camera.getRotation());
	}


	private void rotate(Vector3f axis, float angle) {
		axisRotation.set(angle, axis).get(this.axisRotationQ).mul(this.camera.getRotation(), this.camera.getRotation());
	}



}
