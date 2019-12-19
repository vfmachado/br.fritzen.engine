#version 330

layout(location = 0) out vec4 fragColor;

in vec2 v_texCoord;
in vec3 v_normal;
in vec3 v_fragPos;

uniform vec4 color;

uniform int textureRepeats = 1;

uniform sampler2D u_texture;

//testing
vec3 lightPos = vec3(10, 10, 30);

void main() {

	vec4 textureColor = texture(u_texture, v_texCoord * textureRepeats);
	
	if (textureColor.w < 0.2)
		discard;

	vec3 norm = normalize(v_normal);
	vec3 lightDir = normalize(lightPos - v_fragPos);  
	float diff = max(dot(norm, lightDir), 0.1);
	
	vec4 diffVector = vec4(diff, diff, diff, 1.0);
	
	fragColor = textureColor * color * diffVector;

}