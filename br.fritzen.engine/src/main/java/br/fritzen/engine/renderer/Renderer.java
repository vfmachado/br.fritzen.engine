package br.fritzen.engine.renderer;

import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import br.fritzen.engine.Application;
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
import br.fritzen.engine.renderer.shadow.ShadowMap;
import br.fritzen.engine.scenegraph.GameObject;
import br.fritzen.engine.scenegraph.Light.DirectionalLight;
import br.fritzen.engine.scenegraph.Scene;
import br.fritzen.engine.utils.Pair;

public abstract class Renderer {

	private static SceneData sceneData = new SceneData();

	//TODO PUBLIC BECAUSE TESTING SHADOW MAP
	public static Renderer3DStorage sData = new Renderer3DStorage();

	private static Shader currentShader;
	
	public static Texture2D lightViewTexture = Texture2D.create(4096, 4096);
	
	
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

	
	static Matrix4f  lightViewProj = new Matrix4f();
	static Matrix4f lightView = new Matrix4f();
	static Matrix4f orthoProj = new Matrix4f();
	
	public static void drawToTexture(Scene scene) {
		
		scene.processLights();
		
		lightViewTexture.bindAsRenderTarget();
		
		RenderCommand.clear();
		
		sData.getDepthShader().bind();
		
		//sData.getDepthShader().setMat4("u_LightViewMatrix", scene.getCamera().getView());
		//sData.getDepthShader().setMat4("u_OrthoProjectionMatrix", scene.getCamera().getProjection());
				
		//updateLightViewMatrix(lightView, scene.getDirlights().get(0).getDirection(), new Vector3f(0, 0, 0));
		lightView.identity().lookAt(scene.getDirlights().get(0).getDirection().mul(-1, new Vector3f()), new Vector3f(0), EngineState.Y_AXIS);
		sData.getDepthShader().setMat4("u_LightViewMatrix", lightView);
		
		float orthoSize = 15;
		orthoProj.identity().ortho(-orthoSize, orthoSize, -orthoSize, orthoSize, -orthoSize, orthoSize);
		sData.getDepthShader().setMat4("u_OrthoProjectionMatrix", orthoProj);
		
		lightViewProj.identity();
		lightViewProj.set(orthoProj).mul(lightView);
		
		sData.getDepthShader().setMat4("lightViewProj", lightViewProj);
		
		recursiveShadowRenderer(scene.getRootGameObject());
		
	}
	
	/**
	 * Use Engine Renderer Storage and Default Shaders
	 * Objects not included on Scene will NOT cast shadows
	 * @param scene
	 */
	public static void beginScene(Scene scene) {

		RenderCommand.clear();
		scene.processLights();
		
		if (scene.getLights().isEmpty()) {
			//SIMPLE DIRECTIONAL LIGHT
			Renderer.beginScene(scene.getCamera(), DirectionalLight.getEmpty(), scene.getSkybox());
		} else {
			
	        //BIND LIGHTS ON SCENE!!!
			Renderer.beginScene(scene.getCamera(), null, scene.getSkybox());
			
			//EngineLog.info("Number of dir lights: " + scene.getDirlights().size() );
			sData.getMainShader().setInt(ShaderUniform.light_NumberOfDirectional, scene.getDirlights().size());
			
			int index = 0;
			for (DirectionalLight dirLight : scene.getDirlights()) {
				//EngineLog.info(" DIR LIGHT " + index);
				sData.getMainShader().setFloat3(ShaderUniform.dirLightDirection(index), dirLight.getDirection());
				sData.getMainShader().setFloat3(ShaderUniform.dirLightAmbientColor(index), dirLight.getAmbientColor());
				sData.getMainShader().setFloat3(ShaderUniform.dirLightDiffuseColor(index), dirLight.getDiffuseColor());
				sData.getMainShader().setFloat3(ShaderUniform.dirLightSpecularColor(index), dirLight.getSpecularColor());
				index++;
			}
			
		}
		
		//Renderer all models on the scene
		recursiveRenderer(scene.getRootGameObject());
	}
	
	
	private static GameComponent currentComponent;
	private static void recursiveRenderer(GameObject parent) {
		
		if ( (currentComponent = parent.getComponent(GameComponentType.MESH_RENDERER)) != null) {

			for (Pair<Mesh, Material> m : ((MeshRenderer) currentComponent).getMeshMaterial()) {
				Renderer.render(m.getKey(), parent.getTransform(), m.getValue());
			}
			
		}
		
		
		//RECALL FOR CHILDREN
		for (GameObject go : parent.getChildren()) {
			recursiveRenderer(go);
		}
	}
	
	
	
	private static void recursiveShadowRenderer(GameObject parent) {
		
		if ( (currentComponent = parent.getComponent(GameComponentType.MESH_RENDERER)) != null) {
			
			for (Pair<Mesh, Material> m : ((MeshRenderer) currentComponent).getMeshMaterial()) {
				
				//BIND JUST MODEL NEEDED FOR TRANSFORMATION
				sData.getDepthShader().setMat4(ShaderUniform.model, parent.getTransform());
				
				m.getKey().getVertexArray().bind(); // RendererAPI must deal with this
				RendererAPI.get().drawIndexed(m.getKey().getVertexArray());
			}
			
		}
		
		
		//RECALL FOR CHILDREN
		for (GameObject go : parent.getChildren()) {
			recursiveShadowRenderer(go);
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

		//bind shadow texture
		
		/*
		sData.getShadowMap().getDepthMapTexture().bind(3);
		sData.getMainShader().setInt("shadowMap", 3);
		
		sData.getMainShader().setMat4("lightViewProj", lightViewProj);
		*/
		
		lightViewTexture.bind(3);
		sData.getMainShader().setInt("shadowMap", 3);
		
		sData.getMainShader().setMat4("lightViewProj", lightViewProj);
		sData.getMainShader().setMat4("lightView", lightView);
		sData.getMainShader().setMat4("lightProj", orthoProj);
		
		// BIND DIRECTIONAL LIGHT
		// directionalLight.bind(sData.getShader()); //maybe?

		if (directionalLight != null) {
			sData.getMainShader().setInt(ShaderUniform.light_NumberOfDirectional, 1);
			sData.getMainShader().setFloat3(ShaderUniform.dirLightDirection(0), directionalLight.getDirection());
			sData.getMainShader().setFloat3(ShaderUniform.dirLightAmbientColor(0), directionalLight.getAmbientColor());
			sData.getMainShader().setFloat3(ShaderUniform.dirLightDiffuseColor(0), directionalLight.getDiffuseColor());
			sData.getMainShader().setFloat3(ShaderUniform.dirLightSpecularColor(0), directionalLight.getSpecularColor());
		} 
		
		//SOMETHING LIKE
		/*
		for dir lighs : allDirLights
			shaderUniform.getDirLight(0).direction
		 */
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
		sData.getMainShader().setInt(ShaderUniform.material_DiffuseTexture, 0);

		material.getNormalMapTexture().bind(1);
		sData.getMainShader().setInt(ShaderUniform.material_NormalMapTexture, 1);
		
		material.getSpecularMapTexture().bind(2);
		sData.getMainShader().setInt(ShaderUniform.material_SpecularMapTexture, 2);
		
		lightViewTexture.bind(3);
		sData.getMainShader().setInt("shadowMap", 3);
		
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
	
	
	public static void setTillingFactor(float repeats) {
		sData.getMainShader().setFloat(ShaderUniform.tillingFactor, repeats);
	}
	
	
	public static void renderSceneToTexture(Scene scene, Texture2D texture) {
		
	}
	
}
