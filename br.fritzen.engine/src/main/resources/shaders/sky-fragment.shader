#version 330

layout(location = 0) out vec4 fragColor;

in vec2 v_texCoord;

uniform sampler2D u_mainTexture;

void main() {

    fragColor = texture(u_mainTexture, v_texCoord); 
/*
	vec4 textureColor = texture(u_mainTexture, v_texCoord);
	
	if (textureColor.w < 0.2)
		discard;


	vec3 linearColor = textureColor.rgb;
	vec3 gamma = vec3(1.0/2.2);
	
	fragColor = vec4(pow(linearColor, gamma), textureColor.a);
*/	
}