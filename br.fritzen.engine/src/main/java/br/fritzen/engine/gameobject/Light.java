package br.fritzen.engine.gameobject;

import org.joml.Vector3f;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
public abstract class Light {
	
	@Getter
	@Setter
	private Vector3f ambientColor;

	@Getter
	@Setter
	private Vector3f diffuseColor;

	@Getter
	@Setter
	private Vector3f specularColor;

	
	public static class DirectionalLight extends Light {

		@Getter
		@Setter
		private Vector3f direction;
		
		private static DirectionalLight nullInstance = new DirectionalLight(new Vector3f(0), new Vector3f(0));
		
		
		public DirectionalLight(Vector3f mainColor, Vector3f direction) {
			this(mainColor, mainColor, mainColor, direction);
		}
		
		
		public DirectionalLight(Vector3f ambientColor, Vector3f diffuseColor, Vector3f specularColor, Vector3f direction) {
			super(ambientColor, diffuseColor, specularColor);
			this.direction = direction;
		}
		
		
		public static DirectionalLight getEmpty() {
			return nullInstance;
		}
		
	}
	

}
