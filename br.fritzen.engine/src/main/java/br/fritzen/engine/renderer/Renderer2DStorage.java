package br.fritzen.engine.renderer;

import java.util.ArrayList;
import java.util.List;

import br.fritzen.engine.platform.opengl.OpenGLShader;
import br.fritzen.engine.platform.opengl.OpenGLShaderType;
import br.fritzen.engine.platform.opengl.VertexBufferLayout;
import br.fritzen.engine.renderer.Buffer.IndexBuffer;
import br.fritzen.engine.renderer.Buffer.VertexArray;
import br.fritzen.engine.renderer.Buffer.VertexBuffer;
import br.fritzen.engine.renderer.shader.Shader;
import br.fritzen.engine.utils.EngineBuffers;
import br.fritzen.engine.utils.Pair;
	
/**
 * Static Class to get all necessary for render a Quad with flat color
 * 
 * @author fritz
 *
 */
public class Renderer2DStorage {

	private Shader shader;
	
	private VertexArray vao;
	
	//private static Renderer2DStorage instance = null;
	
	
	public Renderer2DStorage() {
		
		List<Pair<String, OpenGLShaderType>> shaders = new ArrayList<Pair<String, OpenGLShaderType>>();
		shaders.add(new Pair<String, OpenGLShaderType>("shaders/quad-vertex.shader", OpenGLShaderType.VERTEX));
		shaders.add(new Pair<String, OpenGLShaderType>("shaders/quad-fragment.shader", OpenGLShaderType.FRAGMENT));
		this.shader = new OpenGLShader(shaders);
	
		float[] positions = {
				   0.0f, 0.0f, 0, 0, 0, //0
	               1.0f, 0.0f, 0, 1, 0,	//1
	               0.0f, 1.0f, 0, 0, 1,	//2
	               1.0f, 1.0f, 0, 1, 1	//3
	            };
		
		
		
		this.vao = VertexArray.create();
		
		VertexBuffer vbo = VertexBuffer.create(EngineBuffers.createFloatBuffer(positions), positions.length * 4);
		
		List<VertexBufferLayout> layouts = new ArrayList<VertexBufferLayout>();
		layouts.add(new VertexBufferLayout(3));
		layouts.add(new VertexBufferLayout(2));
		this.vao.addInterleavedVBO(vbo, layouts);
				
		int[] indices = { 0, 1, 2, 1, 3, 2};
		
		IndexBuffer ibo = IndexBuffer.create(EngineBuffers.createIntBuffer(indices), indices.length);
		this.vao.setIB(ibo);
		
	}
	
	/*
	private static Renderer2DStorage get() {
		
		if (instance == null) {
			instance = new Renderer2DStorage();
		}
		
		return instance;
	}
	*/
	
	
	public VertexArray getVAO() {
		return this.vao;
	}
	
	
	public Shader getShader() {
		return this.shader;
	}

}
