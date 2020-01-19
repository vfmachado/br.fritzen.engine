package br.fritzen.engine.renderer;

import org.joml.Matrix4f;
import org.joml.Vector3f;

import br.fritzen.engine.components.Camera;
import br.fritzen.engine.components.Mesh;
import br.fritzen.engine.core.EngineState;
import br.fritzen.engine.gameobject.Light.DirectionalLight;
import br.fritzen.engine.renderer.Buffer.VertexArray;
import br.fritzen.engine.renderer.shader.Shader;
import br.fritzen.engine.renderer.shader.ShaderUniform;

public abstract class Renderer {

	
	private static SceneData sceneData = new SceneData();
	
	private static Renderer3DStorage sData = new Renderer3DStorage();
	
	
	public  static void init() {
		
		RenderCommand.init();
		
		Renderer2D.init();
	}
	
	
	public static void beginScene(Camera camera) {
		beginScene(camera, DirectionalLight.getEmpty());
	}
	
	public static void beginScene(Camera camera, DirectionalLight directionalLight) {
	
		//copy values from camera
		sceneData.viewMatrix = camera.getView();
		sceneData.projectionMatrix = camera.getProjection();
		sceneData.viewProjectionMatrix = camera.getViewProjection();
		sceneData.cameraPosition = camera.getPosition();
		
		
		sData.getShader().bind();
		
		sData.getShader().setInt(ShaderUniform.light_model, EngineState.LIGHT_MODEL);
		
		sData.getShader().setMat4(ShaderUniform.viewProjection, sceneData.viewProjectionMatrix);
		sData.getShader().setMat4(ShaderUniform.view, sceneData.viewMatrix);
		sData.getShader().setMat4(ShaderUniform.projection, sceneData.projectionMatrix);
		
		sData.getShader().setFloat3(ShaderUniform.cameraPosition, sceneData.cameraPosition);
		
		//BIND DIRECTIONAL LIGHT
		//directionalLight.bind(sData.getShader()); //maybe?
		
		sData.getShader().setFloat3(ShaderUniform.directionalLight_direction, directionalLight.getDirection());
		sData.getShader().setFloat3(ShaderUniform.directionalLight_ambient, directionalLight.getAmbientColor());
		sData.getShader().setFloat3(ShaderUniform.directionalLight_diffuse, directionalLight.getDiffuseColor());
		sData.getShader().setFloat3(ShaderUniform.directionalLight_specular, directionalLight.getSpecularColor());
		
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
		
		shader.bind();
		
		shader.setMat4(ShaderUniform.viewProjection, sceneData.viewProjectionMatrix);
		shader.setMat4(ShaderUniform.view, sceneData.viewMatrix);
		shader.setMat4(ShaderUniform.projection, sceneData.projectionMatrix);
		
		shader.setFloat3(ShaderUniform.cameraPosition, sceneData.cameraPosition);
		
		shader.setMat4(ShaderUniform.model, transform);
		
		vertexArray.bind();
		
		RendererAPI.get().drawIndexed(vertexArray);
		
	}
	
	
	public static void render(Mesh mesh, Matrix4f transform, Material material) {
	
		sData.getShader().setMat4(ShaderUniform.model, transform);
		
		//MATERIAL
		sData.getShader().setFloat4(ShaderUniform.color, material.getDiffuseColor());
		
		sData.getShader().setFloat4(ShaderUniform.material_AmbientColor, material.getAmbientColor());
		sData.getShader().setFloat4(ShaderUniform.material_DiffuseColor, material.getDiffuseColor());
		sData.getShader().setFloat4(ShaderUniform.material_SpecularColor, material.getSpecularColor());
		
		material.getDiffuseTexture().bind(0);
		//sData.getShader().setInt(ShaderUniform.texture, 0);
		sData.getShader().setInt(ShaderUniform.material_DiffuseTexture, 0);
		
		sData.getShader().setFloat(ShaderUniform.material_Shininess, material.getShininess());
		
		sData.getShader().setFloat4(ShaderUniform.materialSpecColor, material.getSpecularColor());
		
		mesh.getVertexArray().bind();
		RendererAPI.get().drawIndexed(mesh.getVertexArray());
				
	}
		
}
