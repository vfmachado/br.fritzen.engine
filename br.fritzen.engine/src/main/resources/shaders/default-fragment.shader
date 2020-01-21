#version 330

layout(location = 0) out vec4 fragColor;

in vec2 v_texCoord;
in vec3 v_normal;
in vec3 v_fragPos;

uniform vec4 color;
uniform vec4 materialSpecColor;

uniform int textureRepeats = 1;


uniform vec3 u_CameraPosition;
uniform vec3 cameraDir;

uniform float u_shininess;


uniform int LIGHT_MODEL;


struct Material {
	
	vec4 ambientColor;
	vec4 diffuseColor;
	vec4 specularColor;
	
	sampler2D diffuseTexture;
	
	float specularExponent;
	float shininess;
};

uniform Material u_Material;


struct Light {
	vec3 ambientColor;
	vec3 diffuseColor;
	vec3 specularColor;
};


struct DirectionalLight {
	vec3 direction;
	Light light;
};

uniform DirectionalLight u_DirectionalLight;


void main() {

	vec4 textureColor = texture(u_Material.diffuseTexture, v_texCoord * textureRepeats);
	
	if (textureColor.w < 0.2)
		discard;


	//AMBIENT
	//vec3 ambient = vec3(0.4, 0.4, 0.4);
	vec3 ambient = u_DirectionalLight.light.ambientColor;
	
	//DIFFUSE
	vec3 norm = normalize(v_normal);
	//vec3 lightDir   = normalize(lightPos - v_fragPos); //point light
	vec3 lightDir = normalize(-u_DirectionalLight.direction);	//directional light
	float diff = max(dot(norm, lightDir), 0.0);
	vec3 diffuse = u_DirectionalLight.light.diffuseColor * diff;
	
	
	//SPECULAR
	vec3 specular = vec3(0);
	float spec = 0;
		
	vec3 viewDir    = normalize(u_CameraPosition - v_fragPos);
	
	
	if (LIGHT_MODEL == 0) {
		//LIGHT MODEL 0 => PHONG
		vec3 reflectDir = reflect(-lightDir, norm);
		spec = pow(max(dot(viewDir, reflectDir), 0.0), u_Material.shininess);
		
	//BLINN-PHONG - https://learnopengl.com/Advanced-Lighting/Advanced-Lighting
	} else if (LIGHT_MODEL == 1) {
		//LIGHT MODEL 1 => BLINN-PHONG
		vec3 halfwayDir = normalize(lightDir + viewDir);
		spec = pow(max(dot(norm, halfwayDir), 0.0),  u_Material.shininess);
		
	}
	

	specular = u_DirectionalLight.light.specularColor * spec * 0.5;	//0.5 to decrease the intensity


	
	vec3 linearColor = (
		ambient * u_Material.ambientColor.rgb +
		diffuse * u_Material.diffuseColor.rgb + 
		specular * u_Material.specularColor.rgb
		) *  textureColor.rgb;
	
	
	float alpha = textureColor.a * u_Material.diffuseColor.a;
	fragColor = vec4(linearColor, alpha);

/*
	//diffuse color
	vec3 norm = normalize(v_normal);
	vec3 lightDir = normalize(lightPos - v_fragPos);  
	float diff = max(dot(norm, lightDir), 0.1);
	
	vec3 diffVector = vec3(diff);
	
	
	//specular
	
	vec3 incidenceVector = -lightDir;
	vec3 reflectionVector = reflect(incidenceVector, norm);
	vec3 surfaceToCamera = normalize(cameraPosition - v_fragPos);
	
	float specularCoefficient = 0.0;
	
	specularCoefficient = pow(max(0.0, dot(surfaceToCamera, reflect(-lightDir, norm))), u_Material.shininess);
	
	vec3 specular = specularCoefficient * materialSpecColor.rgb;
	
	vec3 linearColor = (ambient + diffVector + specular) * textureColor.rgb * color.rgb;
	vec3 gamma = vec3(1.0/2.2);
	
	float alpha = textureColor.a * color.a;
	
	fragColor = vec4(pow(linearColor, gamma), alpha);
	

*/

	/*
		//gama correction
	vec3 gamma = vec3(1.0/2.2);
	
	//float alpha = textureColor.a * color.a;
	float alpha = textureColor.a * u_Material.diffuseColor.a;
	
	fragColor = vec4(pow(linearColor, gamma), alpha);
	*/

}