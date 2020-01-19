package br.fritzen.engine.renderer.shader;

public enum ShaderUniform {

	//SimpleShaderValues
	position("position"),
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
	;

	
	private String text;
	
	private ShaderUniform(String text) {
		this.text = text;
	}
	
	public String getText() {
		return this.text;
	}
}
