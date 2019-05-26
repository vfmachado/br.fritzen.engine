package br.fritzen.engine.renderer.shader;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.joml.Vector4f;

public abstract class Shader {
		
	public abstract void bind();
	
	public abstract void unbind();
	
	public abstract void updateUniform(ShaderUniform uniform, float x, float y, float z);
	
	public abstract void updateUniform(ShaderUniform uniform, int value);
	
	public abstract void updateUniform(ShaderUniform uniform, Matrix4f mat);
	
	public abstract void updateUniform(ShaderUniform uniform, Vector4f vec);
	
	public abstract void updateUniform(ShaderUniform uniform, Vector3f vec);
	
	public abstract void updateUniform(ShaderUniform uniform, float value);

}
