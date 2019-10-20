package br.fritzen.engine.components;

import java.util.ArrayList;
import java.util.List;

import org.joml.Matrix4f;
import org.joml.Vector3f;

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

public class PerspectiveCameraTest {

	public static class MainLayer extends Layer {

		private PerspectiveCamera camera;
		
		private VertexArray vao;
		
		private Matrix4f transform;
		
		private Shader shader;
		
		
		public MainLayer() {
			super("MAIN LAYER");
			
			List<Pair<String, OpenGLShaderType>> shaders = new ArrayList<Pair<String, OpenGLShaderType>>();
			shaders.add(new Pair<String, OpenGLShaderType>("shaders/simple/vertex-mvp.shader", OpenGLShaderType.VERTEX));
			shaders.add(new Pair<String, OpenGLShaderType>("shaders/simple/fragment-color.shader", OpenGLShaderType.FRAGMENT));
			this.shader = new OpenGLShader(shaders);
			
			
			
			camera = new PerspectiveCamera();
			
			float[] positions = {
					  -0.5f, -0.5f, 0,  //0
		               0.5f, -0.5f, 0,	//1
		              -0.5f,  0.5f, 0,	//2
		               0.5f,  0.5f, 0 	//3
		            };
			
			this.vao = VertexArray.create();
			
			VertexBuffer vbo = VertexBuffer.create(EngineBuffers.createFloatBuffer(positions), positions.length * 4);
			
			this.vao.addVB(vbo, 0, 3);
			
			int[] indices = { 0, 1, 2, 1, 3, 2};
			
			IndexBuffer ibo = IndexBuffer.create(EngineBuffers.createIntBuffer(indices), indices.length);
			this.vao.setIB(ibo);
			
			
			this.transform = new Matrix4f();
			transform.rotate((float) Math.toRadians(45), 0, 1, 0);
			
			this.camera.setPosition(new Vector3f(0, 0, 0.5f));
		}
		
		float angle = 0;
		float dir = 1;
		
		@Override
		public void onUpdate(float deltatime) {
			
		//	camera.onUpdate(deltatime);
			
			if (angle >= 90) {
				dir = -1;
			} else if (angle <= 0) {
				dir = 1;
			}

			angle += dir * (float) Math.toRadians(5 * deltatime);
			
			
			transform.identity();
			transform.rotate(angle, 0, 1, 0);
			
		
		}
		
		@Override
		public void onRender() {
			
			Renderer.BeginScene(this.camera);
		
			this.shader.bind();
						
			
			Renderer.submit(shader, this.vao, this.transform);
			
			Renderer.EndScene();
		}
		
		@Override
		public void onOvent(Event e) {
		//	camera.onEvent(e);
		}
		
	}
	
	
	
	public static void main(String[] args) {

		Application app = Application.create("Camera Test", 1280, 720);
		
		app.addLayer(new MainLayer());
		
		app.run();
		
		
	}

	
	
	
}
