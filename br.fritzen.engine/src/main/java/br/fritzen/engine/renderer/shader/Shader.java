package br.fritzen.engine.renderer.shader;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.joml.Vector4f;

public abstract class Shader {
		
	public abstract void bind();
	
	public abstract void unbind();
	
	public abstract int getProgram();
	
	public abstract void setInt(String uniform, int value);
	
	public abstract void setFloat(String uniform, float value);

	public abstract void setFloat3(String uniform, float x, float y, float z);
	
	public abstract void setFloat3(String uniform, Vector3f vec);
	
	public abstract void setFloat4(String uniform, float x, float y, float z, float w);
	
	public abstract void setFloat4(String uniform, Vector4f vec);
	
	public abstract void setMat4(String uniform, Matrix4f mat);
	
}
