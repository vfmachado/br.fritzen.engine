package br.fritzen.pong;

import java.util.ArrayList;
import java.util.List;

import org.joml.Matrix4f;
import org.joml.Vector3f;

import br.fritzen.engine.platform.opengl.OpenGLShader;
import br.fritzen.engine.platform.opengl.OpenGLShaderType;
import br.fritzen.engine.platform.opengl.OpenGLTexture2D;
import br.fritzen.engine.platform.opengl.VertexBufferLayout;
import br.fritzen.engine.renderer.Buffer.IndexBuffer;
import br.fritzen.engine.renderer.Buffer.VertexArray;
import br.fritzen.engine.renderer.Buffer.VertexBuffer;
import br.fritzen.engine.renderer.Renderer;
import br.fritzen.engine.renderer.Texture;
import br.fritzen.engine.renderer.shader.Shader;
import br.fritzen.engine.renderer.shader.ShaderUniform;
import br.fritzen.engine.utils.EngineBuffers;
import br.fritzen.engine.utils.Pair;

public class Ball {

	private Shader shader;
	
	private VertexArray vao;
	private IndexBuffer ibo;
	
	private Matrix4f transform;
	
	private Texture texture;
	
	private Vector3f position;
	private Vector3f direction;
	
	private float speed = 100f;
	
	public Ball() {
	
		List<Pair<String, OpenGLShaderType>> shaders = new ArrayList<Pair<String, OpenGLShaderType>>();
		shaders.add(new Pair<String, OpenGLShaderType>("shaders/vertex-mvp.shader", OpenGLShaderType.VERTEX));
		shaders.add(new Pair<String, OpenGLShaderType>("shaders/fragment.shader", OpenGLShaderType.FRAGMENT));
		this.shader = new OpenGLShader(shaders);
		
		this.transform = new Matrix4f();
		
		float[] positions = {
				  -0.1f, -0.1f, 0, 0, 0,  //0
	               0.1f, -0.1f, 0, 1, 0,	//1
	              -0.1f,  0.1f, 0, 0, 1,	//2
	               0.1f,  0.1f, 0, 1, 1	//3
	            };
		
		
		
		this.vao = VertexArray.create();
		//this.vao = new OpenGLVertexArray();
		//this.vao.addInterleavedVBO(vbo, layouts);
		
		VertexBuffer vbo = VertexBuffer.create(EngineBuffers.createFloatBuffer(positions), positions.length * 4);
		
		List<VertexBufferLayout> layouts = new ArrayList<VertexBufferLayout>();
		layouts.add(new VertexBufferLayout(3));
		layouts.add(new VertexBufferLayout(2));
		
		this.vao.addInterleavedVBO(vbo, layouts);
		
		//this.vao.addVB(vbo, 0, 3);
		
		
		int[] indices = { 0, 1, 2, 1, 3, 2};
		
		ibo = IndexBuffer.create(EngineBuffers.createIntBuffer(indices), indices.length);
		this.vao.setIB(ibo);
		//ibo.unbind();
		
		texture = new OpenGLTexture2D("textures/ball.png");
		
		position = new Vector3f();
		direction = new Vector3f(0.5f, 0.3f, 0);
		
	}
	
	Vector3f tmpDir = new Vector3f();
	public void update(float deltatime) {
		//System.out.println(deltatime);
	
		if (position.x <= 0f || position.x > 100f) {
			direction.mul(-1, 1, 1);
			if (position.x < 0f) {
				position.x = 0f;
			} else {
				position.x = 100f;
			}
			//position.add(direction.mul(deltatime/100f, tmpDir));
			//System.out.println("Inverter X");
		}
		
		if (position.y <= 0f || position.y >= 100f) {
			
			direction.mul(1, -1, 1);
			if (position.y < 0f) {
				position.y = 0f;
			} else {
				position.y = 100f;
			}
			//position.add(direction.mul(deltatime/100f, tmpDir));
			//System.out.println("Inverter Y");
		}
			
		position.add(direction.mul(deltatime * speed, tmpDir));
		//System.out.println(position);
		
		transform.identity().translate(position).scale(10, 10*1.777f,  1);
		
		
		
		//System.out.println(position);
		
		
	}
	
	public void draw() {
		
		this.shader.bind();
		
		//this.shader.updateUniform(ShaderUniform.color, 1.0f, 0.2f, 0.2f);
		
		this.texture.bind();
		this.shader.setInt(ShaderUniform.texture, 0);
		
		
		Renderer.submit(this.shader, this.vao, this.transform);
	}
	
}
