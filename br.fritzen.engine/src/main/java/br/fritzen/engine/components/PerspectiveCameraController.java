package br.fritzen.engine.components;

import org.joml.Vector3f;

import br.fritzen.engine.core.EngineState;
import br.fritzen.engine.core.input.Input;
import lombok.Getter;

public class PerspectiveCameraController {

	private Vector3f tmpVec = new Vector3f(); 
	
	@Getter
	private PerspectiveCamera camera;

	public PerspectiveCameraController() {

		this.camera = new PerspectiveCamera(90f, 16f / 9f, 0.1f, 100f);
		this.camera.setPosition(new Vector3f(0, 0, 0));

	}

	public void onUpdate(float deltatime) {

		tmpVec.set(0);
		
		if (Input.isKeyPressed(Input.KEY_W)) {

			this.camera.getPosition()
					.add(this.camera.getRotation().transformUnitPositiveZ(tmpVec).mul(-deltatime));
			this.camera.recalculateViewProjection();

		} else if (Input.isKeyPressed(Input.KEY_S)) {

			this.camera.getPosition()
					.add(this.camera.getRotation().transformUnitPositiveZ(tmpVec).mul(deltatime));
			this.camera.recalculateViewProjection();

		}

		if (Input.isKeyPressed(Input.KEY_A)) {

			this.camera.getPosition()
					.add(this.camera.getRotation().transformUnitPositiveX(tmpVec).mul(-deltatime));
			this.camera.recalculateViewProjection();

		} else if (Input.isKeyPressed(Input.KEY_D)) {

			this.camera.getPosition()
					.add(this.camera.getRotation().transformUnitPositiveX(tmpVec).mul(deltatime));
			this.camera.recalculateViewProjection();

		}

		if (Input.isKeyPressed(Input.KEY_R)) {

			this.camera.getPosition()
					.add(this.camera.getRotation().transformUnitPositiveY(tmpVec).mul(deltatime));
			this.camera.recalculateViewProjection();

		} else if (Input.isKeyPressed(Input.KEY_F)) {

			this.camera.getPosition()
					.add(this.camera.getRotation().transformUnitPositiveY(tmpVec).mul(-deltatime));
			this.camera.recalculateViewProjection();

		}

		
		if (Input.isKeyPressed(Input.KEY_Q)) {

			this.camera.getRotation().rotateLocalY((float)Math.toRadians(45 * deltatime));
			this.camera.recalculateViewProjection();

		} else if (Input.isKeyPressed(Input.KEY_E)) {

			
			this.camera.recalculateViewProjection();

		}

		
	}

}
