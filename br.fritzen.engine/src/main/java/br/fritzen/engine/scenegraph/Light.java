package br.fritzen.engine.scenegraph;

import org.joml.Matrix4f;
import org.joml.Vector3f;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;


public abstract class Light extends GameObject {
	
	
	public enum LightType {
		Directional,
		Point,
		Spot
	}
	
	
	@Data
	public class ShadowInfo {
		
		private Matrix4f lightProjection;
		
	}
	
		
	public Light(Vector3f ambientColor, Vector3f diffuseColor, Vector3f specularColor) {
		this.ambientColor = ambientColor;
		this.diffuseColor = diffuseColor;
		this.specularColor = specularColor;
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
	
	@Getter
	protected ShadowInfo shadowInfo = null;
	
	
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
			
			this.shadowInfo = new ShadowInfo();
			this.shadowInfo.setLightProjection(new Matrix4f().ortho(-50, 50, -50, 50, -50, 50));
			
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
