package br.fritzen.engine.scenegraph;

import org.joml.Vector3f;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
public abstract class Light extends GameObject {
	
	public enum LightType {
		Directional,
		Point,
		Spot
	}
	
	@Getter
	@Setter
	private Vector3f ambientColor;

	@Getter
	@Setter
	private Vector3f diffuseColor;

	@Getter
	@Setter
	private Vector3f specularColor;

	
	@Override
	protected GameObjectType getType() {
		return GameObjectType.LIGHT;
	}
	
	abstract LightType getLightType();
	
	public static class DirectionalLight extends Light {

		@Getter
		@Setter
		private Vector3f direction;
		
		/**
		 * Similar to blender default lamp in Sun Mode
		 */
		private static DirectionalLight nullInstance = new DirectionalLight(
				new Vector3f(0.5f), 
				new Vector3f(1.0f), 
				new Vector3f(2f), 
				new Vector3f(4f, 5.9f, -1f));
		
		
		public DirectionalLight(Vector3f mainColor, Vector3f direction) {
			this(mainColor, mainColor, mainColor, direction);
		}
		
		
		public DirectionalLight(Vector3f ambientColor, Vector3f diffuseColor, Vector3f specularColor, Vector3f direction) {
			super(ambientColor, diffuseColor, specularColor);
			this.direction = direction;
		}
		
		
		/**
		 * No light at all.
		 * @return light with all components being 0.
		 */
		public static DirectionalLight getEmpty() {
			return nullInstance;
		}


		@Override
		LightType getLightType() {
			return LightType.Directional;
		}
		
	}
	

}
