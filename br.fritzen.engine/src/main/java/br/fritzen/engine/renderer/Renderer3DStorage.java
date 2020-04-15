package br.fritzen.engine.renderer;

import java.util.ArrayList;
import java.util.List;

import br.fritzen.engine.core.EngineLog;
import br.fritzen.engine.platform.opengl.OpenGLShader;
import br.fritzen.engine.platform.opengl.OpenGLShaderType;
import br.fritzen.engine.renderer.shader.Shader;
import br.fritzen.engine.renderer.shadow.ShadowMap;
import br.fritzen.engine.utils.Pair;
import lombok.Getter;
	
/**
 * Static Class to get all necessary data for render  
 * 
 * @author fritz
 *
 */
public class Renderer3DStorage {

	@Getter
	private Shader mainShader;
	
	@Getter
	private Shader skyboxShader;
	
	@Getter
	private Shader terrainShader;
	
	@Getter
	private Shader depthShader;
	
	@Getter
	private ShadowMap shadowMap;
	
	
	public Renderer3DStorage() {
		
		List<Pair<String, OpenGLShaderType>> shaders = new ArrayList<Pair<String, OpenGLShaderType>>();
		shaders.add(new Pair<String, OpenGLShaderType>("shaders/default-vertex.shader", OpenGLShaderType.VERTEX));
		shaders.add(new Pair<String, OpenGLShaderType>("shaders/default-fragment.shader", OpenGLShaderType.FRAGMENT));
		this.mainShader = new OpenGLShader(shaders);
		
		List<Pair<String, OpenGLShaderType>> skyshaders = new ArrayList<Pair<String, OpenGLShaderType>>();
		skyshaders.add(new Pair<String, OpenGLShaderType>("shaders/sky-vertex.shader", OpenGLShaderType.VERTEX));
		skyshaders.add(new Pair<String, OpenGLShaderType>("shaders/sky-fragment.shader", OpenGLShaderType.FRAGMENT));
		this.skyboxShader = new OpenGLShader(skyshaders);
		
		List<Pair<String, OpenGLShaderType>> terrainshaders = new ArrayList<Pair<String, OpenGLShaderType>>();
		terrainshaders.add(new Pair<String, OpenGLShaderType>("shaders/terrain/vertex.shader", OpenGLShaderType.VERTEX));
		terrainshaders.add(new Pair<String, OpenGLShaderType>("shaders/terrain/fragment.shader", OpenGLShaderType.FRAGMENT));
		this.terrainShader = new OpenGLShader(terrainshaders);
		
		
		List<Pair<String, OpenGLShaderType>> depthShaders = new ArrayList<Pair<String, OpenGLShaderType>>();
		depthShaders.add(new Pair<String, OpenGLShaderType>("shaders/depth/vertex.shader", OpenGLShaderType.VERTEX));
		depthShaders.add(new Pair<String, OpenGLShaderType>("shaders/depth/fragment.shader", OpenGLShaderType.FRAGMENT));
		this.depthShader = new OpenGLShader(depthShaders);
		
		
		try {
			this.shadowMap = new ShadowMap();
			EngineLog.info("Shadow Map initialized");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
		
	

}
