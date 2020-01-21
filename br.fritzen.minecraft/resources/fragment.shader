#version 330

layout(location = 0) out vec4 fragColor;

in vec2 v_texCoord;
in vec3 v_normal;
in vec3 v_fragPos;

uniform float textureRepeats = 1;



uniform sampler2D u_texture;


void main() {

	vec4 textureColor = texture(u_texture, vec2(v_texCoord.x * 0.125, 1 - v_texCoord.y * 0.125));
	
	if (textureColor.w < 0.3)
		discard;

	
	fragColor = textureColor;

}