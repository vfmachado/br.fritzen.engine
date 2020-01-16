#version 330

layout(location = 0) out vec4 fragColor;

in vec2 v_texCoord;
in vec3 v_normal;
in vec3 v_fragPos;

uniform vec4 color;
uniform vec4 materialSpecColor;

uniform int textureRepeats = 1;

uniform sampler2D u_texture;

//testing
vec3 lightPos = vec3(20, 20, 40);

vec3 ambient = vec3(0.1, 0.1, 0.1);

uniform vec3 cameraPosition;

uniform float u_shininess;

void main() {

	vec4 textureColor = texture(u_texture, v_texCoord * textureRepeats);
	
	if (textureColor.w < 0.2)
		discard;

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
	
	specularCoefficient = pow(max(0.0, dot(surfaceToCamera, reflect(-lightDir, norm))), u_shininess);
	
	vec3 specular = specularCoefficient * materialSpecColor.rgb;
	
	vec3 linearColor = (ambient + diffVector + specular) * textureColor.rgb * color.rgb;
	vec3 gamma = vec3(1.0/2.2);
	
	float alpha = textureColor.a * color.a;
	
	fragColor = vec4(pow(linearColor, gamma), alpha);
	

}