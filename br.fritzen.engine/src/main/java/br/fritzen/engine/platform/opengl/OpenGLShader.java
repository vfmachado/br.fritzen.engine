package br.fritzen.engine.platform.opengl;

import java.io.IOException;
import java.nio.FloatBuffer;
import java.util.HashMap;
import java.util.List;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.joml.Vector4f;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL20;

import br.fritzen.engine.core.EngineLog;
import br.fritzen.engine.core.EngineState;
import br.fritzen.engine.renderer.shader.Shader;
import br.fritzen.engine.renderer.shader.ShaderUniform;
import br.fritzen.engine.utils.EngineFiles;
import br.fritzen.engine.utils.Pair;

public class OpenGLShader extends Shader {

	private int program;
	
	private HashMap<String, Integer> uniformsLocation;
	
	
	
	public OpenGLShader(List<Pair<String, OpenGLShaderType>> shaders) {
	
		uniformsLocation = new HashMap<String, Integer>();
		
		this.program = GL20.glCreateProgram();
		
		for (Pair<String, OpenGLShaderType> shaderDescription : shaders) {
			this.addShader(shaderDescription.getKey(), shaderDescription.getValue());
		}
		
		GL20.glLinkProgram(this.program);
		if (GL20.glGetProgrami(this.program, GL20.GL_LINK_STATUS) == 0) {
			EngineLog.warning("ERROR on link program: " + GL20.glGetShaderInfoLog(this.program));
			System.exit(0);
		}
		
		GL20.glValidateProgram(this.program);
		if (GL20.glGetProgrami(this.program, GL20.GL_VALIDATE_STATUS) == 0) {
			EngineLog.warning("ERROR on validate program: " + GL20.glGetShaderInfoLog(this.program));
			System.exit(0);
		}
		
	}

	
	@Override
	public void bind() {
		GL20.glUseProgram(this.program);
	}

	
	@Override
	public void unbind() {
		GL20.glUseProgram(0);
	}
	
	
	private void addShader(String filename, OpenGLShaderType shaderType) {
		
		try {
			
			String text = EngineFiles.loadTextFile(filename);
			
			int shader = GL20.glCreateShader(shaderType.getShaderCode());
			if (shader == 0) {
				EngineLog.warning("Shader creation failure");
			}
			
			GL20.glShaderSource(shader, text);
			GL20.glCompileShader(shader);
			
			if (GL20.glGetShaderi(shader, GL20.GL_COMPILE_STATUS) == 0) {
				EngineLog.warning("Shader can't be compiled! Error:\n" + GL20.glGetShaderInfoLog(shader));
			}
			
			GL20.glAttachShader(this.program, shader);
			
		} catch (IOException e) {
			EngineLog.warning("Error opening/reading the file: " + filename);
			e.printStackTrace();
		}
		
	}
	

	public int getUniformLocation(String name) {
		
		if (uniformsLocation.containsKey(name)) {
			return uniformsLocation.get(name);
		}
		
		int location = GL20.glGetUniformLocation(this.program, name);
		
		if (location == 0xFFFFFFFF) {
			if (EngineState.DEBUG_SHADER_WARNING)
				EngineLog.warning("O uniform " + name + " não pode ser encontrado. Verifique se está sendo utilizado");
			return -1;
		}
		
		uniformsLocation.put(name, location);
		return location;
	}
	
	
	
	public void setInt(ShaderUniform uniform, int value) {
		GL20.glUniform1i(getUniformLocation(uniform.getText()), value);	
	}

	
	private FloatBuffer tmpBuffer = BufferUtils.createFloatBuffer(16);
	public void setMat4(ShaderUniform uniform, Matrix4f mat) {
		GL20.glUniformMatrix4fv(getUniformLocation(uniform.getText()), false, mat.get(tmpBuffer));	
	}

	
	public void setFloat3(ShaderUniform uniform, float x, float y, float z) {
		GL20.glUniform3f(getUniformLocation(uniform.getText()), x, y, z);	
	}
	
	
	public void setFloat3(ShaderUniform uniform, Vector3f vec) {
			GL20.glUniform3f(getUniformLocation(uniform.getText()), vec.x, vec.y, vec.z);
	}
	
	
	public void setFloat4(ShaderUniform uniform, float x, float y, float z, float w) {
		GL20.glUniform4f(getUniformLocation(uniform.getText()), x, y, z, w);
	}
	
	public void setFloat4(ShaderUniform uniform, Vector4f vec) {
		GL20.glUniform4f(getUniformLocation(uniform.getText()), vec.x, vec.y, vec.z, vec.w);	
	}


	
	
	
	public void setFloat(ShaderUniform uniform, float value) {
		GL20.glUniform1f(getUniformLocation(uniform.getText()), value);
	}
	
}
