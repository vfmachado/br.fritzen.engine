package br.fritzen.engine.renderer.shader;

public class ShaderUniform {

	//SimpleShaderValues
	public static final String position = "position";
	
	public static final String color = "color";
	public static final String hasTexture = "hasTexture";
	public static final String hasColor = "hasColor";
	public static final String tillingFactor = "textureRepeats";
	
	
	//MVP
	public static final String view = "u_View";
	public static final String projection = "u_Projection";
	public static final String viewProjection = "u_ViewProjection";
	public static final String model = "u_Model";
	
	//simpletexture
	public static final String texture = "u_Texture";
	
	//Materials
	public static final String material_DiffuseTexture = "u_Material.diffuseTexture";
	public static final String material_NormalMapTexture = "u_Material.normalMapTexture";
	public static final String material_SpecularMapTexture = "u_Material.specMapTexture";
	public static final String material_HasDiffuseTexture = "u_Material.hasDiffuseTexture";
	
	public static final String material_AmbientColor = "u_Material.ambientColor";
	public static final String material_DiffuseColor = "u_Material.diffuseColor";
	public static final String material_SpecularColor = "u_Material.specularColor";
	public static final String material_SpecularExponent = "u_Material.specularExponent";
	
	public static final String material_Shininess = "u_Material.shininess";
	public static final String shininess = "u_shininess";
	
	public static final String materialSpecColor = "materialSpecColor";
	
	//LIGHTS
	public static final String light_NumberOfDirectional = "u_NumberOfDirectionalLights";
	public static final String light_NumberOfSpot = "u_numSpotLight";
	public static final String light_NumberOfPoint = "u_numPointLight";
	
	//CAMERA
	public static final String cameraPosition = "u_CameraPosition";
	
	public static final String wireframeEnable = "u_WireframeEnabled"; 
	public static final String wireframeColor = "u_WireframeColor";
	public static final String lightsEnable = "u_LightsEnabled"; 
	
	public static final String light_model = "LIGHT_MODEL";
	
	//DIRECTIONAL LIGHT
	//directionalLight = "u_DirectionalLight";
	public static final String directionalLight_direction = "u_DirectionalLight.direction";
	public static final String 	directionalLight_ambient = "u_DirectionalLight.light.ambientColor";
	public static final String directionalLight_diffuse = "u_DirectionalLight.light.diffuseColor";
	public static final String directionalLight_specular = "u_DirectionalLight.light.specularColor";
	
	public static final String skybox_Texture = "u_mainTexture";
	
	public static String dirLightDirection(int index) {
		return "u_DirectionalLight["+index+"].direction";
	}
	
	public static String dirLightAmbientColor(int index) {
		return "u_DirectionalLight["+index+"].light.ambientColor";
	}
	
	public static String dirLightDiffuseColor(int index) {
		return "u_DirectionalLight["+index+"].light.diffuseColor";
	}
	
	public static String dirLightSpecularColor(int index) {
		return "u_DirectionalLight["+index+"].light.specularColor";
	}
	
	public ShaderUniform() {
	
		
	}
	
	
	/*
	public String getDirLight(int index) {
		return "u_DirectionalLight[" + index + "]."  
	}
	
	public static class u_DirLight {
		
		public String direction = "direction";
		
		
	}
	
	/*
	color("color"),
	hasTexture("hasTexture"),
	hasColor("hasColor"),
	tillingFactor("textureRepeats"),
	
	
	//MVP
	view("u_View"),
	projection("u_Projection"),
	viewProjection("u_ViewProjection"),
	model("u_Model"),
	
	//simpletexture
	texture("u_Texture"),
	
	//Materials
	material_DiffuseTexture("u_Material.diffuseTexture"),
	material_NormalMapTexture("u_Material.normalMapTexture"),
	material_SpecularMapTexture("u_Material.specMapTexture"),
	material_HasDiffuseTexture("u_Material.hasDiffuseTexture"),
	
	material_AmbientColor("u_Material.ambientColor"),
	material_DiffuseColor("u_Material.diffuseColor"),
	material_SpecularColor("u_Material.specularColor"),
	material_SpecularExponent("u_Material.specularExponent"),
	
	material_Shininess("u_Material.shininess"),
	shininess("u_shininess"),
	
	materialSpecColor("materialSpecColor"),
	
	//LIGHTS
	light_NumberOfDirectional("u_numDirectionalLight"),
	light_NumberOfSpot("u_numSpotLight"),
	light_NumberOfPoint("u_numPointLight"),
	
	//CAMERA
	cameraPosition("u_CameraPosition"),
	
	wireframeEnable("u_WireframeEnabled"), 
	wireframeColor("u_WireframeColor"),
	lightsEnable("u_LightsEnabled"), 
	
	light_model("LIGHT_MODEL"),
	
	//DIRECTIONAL LIGHT
	//directionalLight("u_DirectionalLight"),
	directionalLight_direction("u_DirectionalLight.direction"),
	directionalLight_ambient("u_DirectionalLight.light.ambientColor"),
	directionalLight_diffuse("u_DirectionalLight.light.diffuseColor"),
	directionalLight_specular("u_DirectionalLight.light.specularColor"),
	
	skybox_Texture("u_mainTexture");
	
	
	private String text;
	
	private ShaderUniform(String text) {
		this.text = text;
	}
	
	public String getText() {
		return this.text;
	}
	
	*/
	
}
