package br.fritzen.engine.renderer;

import org.joml.Matrix4f;
import org.joml.Vector3f;

import br.fritzen.engine.components.Camera;
import br.fritzen.engine.components.GameComponent;
import br.fritzen.engine.components.GameComponentType;
import br.fritzen.engine.components.Mesh;
import br.fritzen.engine.components.MeshRenderer;
import br.fritzen.engine.components.Model;
import br.fritzen.engine.components.Skybox;
import br.fritzen.engine.core.EngineLog;
import br.fritzen.engine.core.EngineState;
import br.fritzen.engine.renderer.Buffer.VertexArray;
import br.fritzen.engine.renderer.shader.Shader;
import br.fritzen.engine.renderer.shader.ShaderUniform;
import br.fritzen.engine.scenegraph.GameObject;
import br.fritzen.engine.scenegraph.Light.DirectionalLight;
import br.fritzen.engine.scenegraph.Scene;
import br.fritzen.engine.utils.Pair;

public abstract class Renderer {

	private static SceneData sceneData = new SceneData();

	private static Renderer3DStorage sData = new Renderer3DStorage();

	private static Shader currentShader;
	
	
	public static void init() {

		RenderCommand.init();

		Renderer2D.init();
	}


	public static void beginScene(Camera camera) {
		beginScene(camera, DirectionalLight.getEmpty(), null);
	}


	public static void beginScene(Camera camera, DirectionalLight directionalLight) {

		beginScene(camera, directionalLight, null);
	}

	
	public static void beginScene(Shader shader, Camera camera) {

		sceneData.viewMatrix = camera.getView();
		sceneData.projectionMatrix = camera.getProjection();
		sceneData.viewProjectionMatrix = camera.getViewProjection();
		sceneData.cameraPosition = camera.getPosition();

		shader.bind();
	}

	
	/**
	 * Use Engine Renderer Storage and Default Shaders
	 * @param scene
	 */
	public static void beginScene(Scene scene) {
		
		if (scene.getLights().isEmpty()) {
			Renderer.beginScene(scene.getCamera(), DirectionalLight.getEmpty(), scene.getSkybox());
		}
		
		//Renderer all models on the scene
		recursiveRenderer(scene.getRootGameObject());
	}
	
	
	private static GameComponent currentComponent;
	private static void recursiveRenderer(GameObject parent) {
		
		//EngineLog.info("Redering " + parent.getName());
		
		//RENDER CURRENT PARENT
		if ( (currentComponent = parent.getComponent(GameComponentType.MESH_RENDERER)) != null) {
//			/EngineLog.info("... has a MESH_RENDERER");
			for (Pair<Mesh, Material> m : ((MeshRenderer) currentComponent).getMeshMaterial()) {
				Renderer.render(m.getKey(), parent.getTransform(), m.getValue());
			}
			
		}
		
		
		//RECALL FOR CHILDREN
		for (GameObject go : parent.getChildren()) {
			recursiveRenderer(go);
		}
	}
	
	
	public static void beginScene(Camera camera, DirectionalLight directionalLight, Skybox skybox) {

		// copy values from camera
		sceneData.viewMatrix = camera.getView();
		sceneData.projectionMatrix = camera.getProjection();
		sceneData.viewProjectionMatrix = camera.getViewProjection();
		sceneData.cameraPosition = camera.getPosition();

		// BIND SKYBOX and DRAW IT - different shader
		if (skybox != null) {

			currentShader = sData.getSkyboxShader();
			currentShader.bind();

			skybox.getTexture().bind(0);
			currentShader.setInt(ShaderUniform.skybox_Texture, 0);

			skybox.getTransform().setTranslation(sceneData.cameraPosition);
			currentShader.setMat4(ShaderUniform.model, skybox.getTransform());

			currentShader.setMat4(ShaderUniform.viewProjection, sceneData.viewProjectionMatrix);
			currentShader.setFloat3(ShaderUniform.cameraPosition, sceneData.cameraPosition);

			RendererAPI.get().drawIndexed(skybox.getMesh().getVertexArray());
		}

		sData.getMainShader().bind();

		sData.getMainShader().setInt(ShaderUniform.light_model, EngineState.LIGHT_MODEL);

		sData.getMainShader().setMat4(ShaderUniform.viewProjection, sceneData.viewProjectionMatrix);
		sData.getMainShader().setMat4(ShaderUniform.view, sceneData.viewMatrix);
		sData.getMainShader().setMat4(ShaderUniform.projection, sceneData.projectionMatrix);

		sData.getMainShader().setFloat3(ShaderUniform.cameraPosition, sceneData.cameraPosition);

		// BIND DIRECTIONAL LIGHT
		// directionalLight.bind(sData.getShader()); //maybe?

		sData.getMainShader().setFloat3(ShaderUniform.directionalLight_direction, directionalLight.getDirection());
		sData.getMainShader().setFloat3(ShaderUniform.directionalLight_ambient, directionalLight.getAmbientColor());
		sData.getMainShader().setFloat3(ShaderUniform.directionalLight_diffuse, directionalLight.getDiffuseColor());
		sData.getMainShader().setFloat3(ShaderUniform.directionalLight_specular, directionalLight.getSpecularColor());

	}


	public static void endScene() {

	}

	private static class SceneData {

		public Matrix4f viewMatrix;
		public Matrix4f projectionMatrix;
		public Matrix4f viewProjectionMatrix;
		public Vector3f cameraPosition;

	}


	public static void submit(Shader shader, VertexArray vertexArray, Matrix4f transform) {

		// shader.bind();

		shader.setMat4(ShaderUniform.viewProjection, sceneData.viewProjectionMatrix);
		shader.setMat4(ShaderUniform.view, sceneData.viewMatrix);
		shader.setMat4(ShaderUniform.projection, sceneData.projectionMatrix);

		shader.setFloat3(ShaderUniform.cameraPosition, sceneData.cameraPosition);

		shader.setMat4(ShaderUniform.model, transform);

		vertexArray.bind();

		RendererAPI.get().drawIndexed(vertexArray);

	}


	public static void submitInstanced(Shader shader, VertexArray vertexArray, Matrix4f transform, int count) {

		// shader.bind();

		shader.setMat4(ShaderUniform.viewProjection, sceneData.viewProjectionMatrix);
		shader.setMat4(ShaderUniform.view, sceneData.viewMatrix);
		shader.setMat4(ShaderUniform.projection, sceneData.projectionMatrix);

		shader.setFloat3(ShaderUniform.cameraPosition, sceneData.cameraPosition);

		shader.setMat4(ShaderUniform.model, transform);

		RendererAPI.get().drawInstanced(vertexArray, count);

	}


	public static void render(Mesh mesh, Matrix4f transform, Material material) {

		sData.getMainShader().setMat4(ShaderUniform.model, transform);

		// MATERIAL
		sData.getMainShader().setFloat4(ShaderUniform.color, material.getDiffuseColor());

		sData.getMainShader().setFloat4(ShaderUniform.material_AmbientColor, material.getAmbientColor());
		sData.getMainShader().setFloat4(ShaderUniform.material_DiffuseColor, material.getDiffuseColor());
		sData.getMainShader().setFloat4(ShaderUniform.material_SpecularColor, material.getSpecularColor());

		material.getDiffuseTexture().bind(0);
		// sData.getShader().setInt(ShaderUniform.texture, 0);
		sData.getMainShader().setInt(ShaderUniform.material_DiffuseTexture, 0);

		material.getNormalMapTexture().bind(1);
		sData.getMainShader().setInt(ShaderUniform.material_NormalMapTexture, 1);
		
		sData.getMainShader().setFloat(ShaderUniform.material_Shininess, material.getShininess());

		sData.getMainShader().setFloat4(ShaderUniform.materialSpecColor, material.getSpecularColor());

		mesh.getVertexArray().bind(); // RendererAPI mus deal with this
		RendererAPI.get().drawIndexed(mesh.getVertexArray());

	}

	
	/**
	 * Renderer for Models loaded.
	 * @param model : Model object.
	 * @param transform : Matrix4f with the transform 
	 */
	public static void render(Model model, Matrix4f transform) {
		for (Pair<Mesh, Integer> m : model.getMeshes()) {
			Renderer.render(m.getKey(), transform, model.getMaterials().get(m.getValue()));
		}
	}
	
	
	
}
