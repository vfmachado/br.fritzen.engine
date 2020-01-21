package br.fritzen.minecraft.main;

import java.util.ArrayList;
import java.util.List;

import org.joml.Matrix4f;

import br.fritzen.engine.components.PerspectiveCameraController;
import br.fritzen.engine.core.layers.Layer;
import br.fritzen.engine.events.Event;
import br.fritzen.engine.platform.opengl.OpenGLShader;
import br.fritzen.engine.platform.opengl.OpenGLShaderType;
import br.fritzen.engine.renderer.Renderer;
import br.fritzen.engine.renderer.Texture2D;
import br.fritzen.engine.renderer.shader.Shader;
import br.fritzen.engine.renderer.shader.ShaderUniform;
import br.fritzen.engine.utils.Pair;

public class SceneLayer extends Layer {

	private PerspectiveCameraController camera;

	private Shader sceneShader;

	private Matrix4f transform;

	private Texture2D texture;
	
	
	int mat[][][] = new int[10][10][10];

	public SceneLayer(String name) {
		super(name);

		List<Pair<String, OpenGLShaderType>> shaderSource = new ArrayList<Pair<String, OpenGLShaderType>>();

		shaderSource.add(new Pair<String, OpenGLShaderType>("vertex.shader", OpenGLShaderType.VERTEX));
		shaderSource.add(new Pair<String, OpenGLShaderType>("fragment.shader", OpenGLShaderType.FRAGMENT));
		
		sceneShader = new OpenGLShader(shaderSource);

		camera = new PerspectiveCameraController(0, 0, 5);
		
		transform = new Matrix4f();
		
		texture = Texture2D.create("my_texture_atlas.png");
		
		sceneShader.bind();
		
		
		sceneShader.setInt(ShaderUniform.texture, 0);
		texture.bind(0);
		
		//GL11.glPolygonMode( GL11.GL_FRONT_AND_BACK, GL11.GL_LINE );
		
		
		for (int i = 0; i < mat.length; i++) {
			for (int j = 0; j < mat.length; j++) {
				for (int k = 0; k < mat.length; k++) {
					if (k > i + j)
						mat[i][j][k] = 1; 
				}
			}
		}
		
	}


	@Override
	public void onUpdate(float deltatime) {

		camera.onUpdate(deltatime);

	}


	@Override
	public void onRender() {
		
		Renderer.beginScene(sceneShader, this.camera.getCamera());
		
		
		texture.bind(0);
		sceneShader.setInt(ShaderUniform.texture, 0);
		//Renderer.submit(sceneShader, Cube.getVao(), transform);

		for (int i = 0; i < mat.length; i++) {
			for (int j = 0; j < mat.length; j++) {
				for (int k = 0; k < mat.length; k++) {
					if (mat[i][j][k] == 1) {
						this.transform.identity();
						transform.translate(i, j ,k);
						Renderer.submit(sceneShader, Cube.getVao(), transform);
					}
				}
			}
		}
		
		Renderer.endScene();
	}


	@Override
	public void onEvent(Event e) {
		this.camera.onEvent(e);
	}
}
