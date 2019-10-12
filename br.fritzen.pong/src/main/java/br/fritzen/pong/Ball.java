package br.fritzen.pong;

import java.util.ArrayList;
import java.util.List;

import org.joml.Matrix4f;

import br.fritzen.engine.platform.opengl.OpenGLShader;
import br.fritzen.engine.platform.opengl.OpenGLShaderType;
import br.fritzen.engine.renderer.Buffer.IndexBuffer;
import br.fritzen.engine.renderer.Buffer.VertexArray;
import br.fritzen.engine.renderer.Buffer.VertexBuffer;
import br.fritzen.engine.renderer.Renderer;
import br.fritzen.engine.renderer.shader.Shader;
import br.fritzen.engine.renderer.shader.ShaderUniform;
import br.fritzen.engine.utils.EngineBuffers;
import br.fritzen.engine.utils.Pair;

public class Ball {

	Shader shader;
	VertexArray vao;
	IndexBuffer ibo;
	Matrix4f transform;
	
	public Ball() {
	
		List<Pair<String, OpenGLShaderType>> shaders = new ArrayList<Pair<String, OpenGLShaderType>>();
		shaders.add(new Pair<String, OpenGLShaderType>("shaders/simple/vertex.shader", OpenGLShaderType.VERTEX));
		shaders.add(new Pair<String, OpenGLShaderType>("shaders/simple/fragment.shader", OpenGLShaderType.FRAGMENT));
		this.shader = new OpenGLShader(shaders);
		
		this.transform = new Matrix4f();
		
		float[] positions = {
				  -0.2f, -0.2f, 0,  //0
	               0.2f, -0.2f, 0,	//1
	              -0.2f,  0.2f, 0,	//2
	               0.2f,  0.2f, 0,	//3
	            };
		
		VertexBuffer vbo = VertexBuffer.create(EngineBuffers.createFloatBuffer(positions), positions.length * 4);
		
		
		this.vao = VertexArray.create();
		//this.vao = new OpenGLVertexArray();
		//this.vao.addInterleavedVBO(vbo, layouts);
			
		this.vao.addVB(vbo, 0, 3);
		
		int[] indices = { 0, 1, 2, 1, 3, 2};
		
		ibo = IndexBuffer.create(EngineBuffers.createIntBuffer(indices), indices.length);
		this.vao.setIB(ibo);
		//ibo.unbind();
		
	}
	
	
	public void draw() {
		this.shader.bind();
		this.shader.updateUniform(ShaderUniform.color, 1.0f, 0.2f, 0.2f);
		Renderer.get().submit(shader, this.vao, this.transform);
	}
	
}
