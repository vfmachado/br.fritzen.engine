package br.fritzen.engine.components;

import java.util.ArrayList;
import java.util.List;

import org.joml.Matrix4f;

import br.fritzen.engine.Application;
import br.fritzen.engine.core.layers.Layer;
import br.fritzen.engine.events.Event;
import br.fritzen.engine.platform.opengl.OpenGLShader;
import br.fritzen.engine.platform.opengl.OpenGLShaderType;
import br.fritzen.engine.renderer.Buffer.IndexBuffer;
import br.fritzen.engine.renderer.Buffer.VertexArray;
import br.fritzen.engine.renderer.Buffer.VertexBuffer;
import br.fritzen.engine.renderer.Renderer;
import br.fritzen.engine.renderer.shader.Shader;
import br.fritzen.engine.utils.EngineBuffers;
import br.fritzen.engine.utils.Pair;

public class OrthographicCameraTest {

	public static class MainLayer extends Layer {

		private OrthographicCameraController camera;
		
		private VertexArray vao;
		
		private Matrix4f identity;
		
		private Shader shader;
		
		
		public MainLayer() {
			super("MAIN LAYER");
			
			List<Pair<String, OpenGLShaderType>> shaders = new ArrayList<Pair<String, OpenGLShaderType>>();
			shaders.add(new Pair<String, OpenGLShaderType>("shaders/simple/vertex-mvp.shader", OpenGLShaderType.VERTEX));
			shaders.add(new Pair<String, OpenGLShaderType>("shaders/simple/fragment-color.shader", OpenGLShaderType.FRAGMENT));
			this.shader = new OpenGLShader(shaders);
			
			
			
			camera = new OrthographicCameraController(1280f/720f, true);
			
			float[] positions = {
					  -0.25f, -0.25f, 0,  //0
		               0.25f, -0.25f, 0,	//1
		              -0.25f,  0.25f, 0,	//2
		               0.25f,  0.25f, 0	//3
		            };
			
			this.vao = VertexArray.create();
			
			VertexBuffer vbo = VertexBuffer.create(EngineBuffers.createFloatBuffer(positions), positions.length * 4);
			
			this.vao.addVB(vbo, 0, 3);
			
			int[] indices = { 0, 1, 2, 1, 3, 2};
			
			IndexBuffer ibo = IndexBuffer.create(EngineBuffers.createIntBuffer(indices), indices.length);
			this.vao.setIB(ibo);
			
			
			this.identity = new Matrix4f();
		}
		
		@Override
		public void onUpdate(float deltatime) {
			
			camera.onUpdate(deltatime);
		
		}
		
		@Override
		public void onRender() {
			
			Renderer.beginScene(this.camera.getCamera());
		
			this.shader.bind();
						
			Renderer.submit(shader, this.vao, this.identity);
			
			Renderer.endScene();
		}
		
		@Override
		public void onOvent(Event e) {
			camera.onEvent(e);
		}
		
	}
	
	
	
	public static void main(String[] args) {

		Application app = Application.create("Camera Test", 1280, 720);
		
		app.addLayer(new MainLayer());
		
		app.run();
		
		
	}

	
	
	
}
