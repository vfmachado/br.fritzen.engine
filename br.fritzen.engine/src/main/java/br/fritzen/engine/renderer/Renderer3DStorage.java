package br.fritzen.engine.renderer;

import java.util.ArrayList;
import java.util.List;

import br.fritzen.engine.platform.opengl.OpenGLShader;
import br.fritzen.engine.platform.opengl.OpenGLShaderType;
import br.fritzen.engine.renderer.shader.Shader;
import br.fritzen.engine.utils.Pair;
	
/**
 * Static Class to get all necessary data for render  
 * 
 * @author fritz
 *
 */
public class Renderer3DStorage {

	private Shader shader;
	
	public Renderer3DStorage() {
		
		List<Pair<String, OpenGLShaderType>> shaders = new ArrayList<Pair<String, OpenGLShaderType>>();
		shaders.add(new Pair<String, OpenGLShaderType>("shaders/default-vertex.shader", OpenGLShaderType.VERTEX));
		shaders.add(new Pair<String, OpenGLShaderType>("shaders/default-fragment.shader", OpenGLShaderType.FRAGMENT));
		this.shader = new OpenGLShader(shaders);
		
	}
		
	
	public Shader getShader() {
		return this.shader;
	}

}
