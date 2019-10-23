package br.fritzen.engine.renderer.shader;

import java.util.ArrayList;
import java.util.List;

import br.fritzen.engine.platform.opengl.OpenGLShader;
import br.fritzen.engine.platform.opengl.OpenGLShaderType;
import br.fritzen.engine.renderer.Buffer.IndexBuffer;
import br.fritzen.engine.renderer.Buffer.VertexArray;
import br.fritzen.engine.renderer.Buffer.VertexBuffer;
import br.fritzen.engine.utils.EngineBuffers;
import br.fritzen.engine.utils.Pair;
	
/**
 * Static Class to get all necessary for render a Quad with flat color
 * 
 * @author fritz
 *
 */
public class QuadModel {

	private Shader shader;
	
	private VertexArray vao;
	
	private static QuadModel instance = null;
	
	
	private QuadModel() {
		
		List<Pair<String, OpenGLShaderType>> shaders = new ArrayList<Pair<String, OpenGLShaderType>>();
		shaders.add(new Pair<String, OpenGLShaderType>("shaders/quad-vertex.shader", OpenGLShaderType.VERTEX));
		shaders.add(new Pair<String, OpenGLShaderType>("shaders/quad-fragment.shader", OpenGLShaderType.FRAGMENT));
		this.shader = new OpenGLShader(shaders);
	
		float[] positions = {
				   0.0f, 0.0f, 0,  //0
	               1.0f, 0.0f, 0,	//1
	               0.0f,  1.0f, 0,	//2
	               1.0f,  1.0f, 0	//3
	            };
		
		
		
		this.vao = VertexArray.create();
		
		VertexBuffer vbo = VertexBuffer.create(EngineBuffers.createFloatBuffer(positions), positions.length * 4);
		
		this.vao.addVB(vbo, 0, 3);
				
		int[] indices = { 0, 1, 2, 1, 3, 2};
		
		IndexBuffer ibo = IndexBuffer.create(EngineBuffers.createIntBuffer(indices), indices.length);
		this.vao.setIB(ibo);
		
	}
	

	private static QuadModel get() {
		
		if (instance == null) {
			instance = new QuadModel();
		}
		
		return instance;
	}
	
	
	public static VertexArray getVAO() {
		return get().vao;
	}
	
	
	public static Shader getShader() {
		return get().shader;
	}

}
