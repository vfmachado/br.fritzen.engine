package br.fritzen.engine.renderer.shader;

public enum ShaderUniform {

	//SimpleShaderValues
	position("position"),
	color("color"),
	
	//MVP
	view("u_View"),
	projection("u_Projection"),
	model("u_Model"),
	
	//Materials
	material_DiffuseTexture("u_Material.diffuseTexture"),
	material_HasDiffuseTexture("u_Material.hasDiffuseTexture"),
	
	material_AmbientColor("u_Material.ambientColor"),
	material_DiffuseColor("u_Material.diffuseColor"),
	material_SpecularColor("u_Material.specularColor"),
	material_SpecularExponent("u_Material.specularExponent"),
	
	//LIGHTS
	light_NumberOfDirectional("u_numDirectionalLight"),
	light_NumberOfSpot("u_numSpotLight"),
	light_NumberOfPoint("u_numPointLight"),
	
	//CAMERA
	cameraPosition("u_CameraPosition"),
	
	wireframeEnable("u_WireframeEnabled"), 
	wireframeColor("u_WireframeColor"),
	lightsEnable("u_LightsEnabled")
	;

	
	private String text;
	
	private ShaderUniform(String text) {
		this.text = text;
	}
	
	public String getText() {
		return this.text;
	}
}