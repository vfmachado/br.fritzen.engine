#version 330

layout(location = 0) out vec4 fragColor;

in vec2 v_texCoord;
in vec4 v_color;
in float v_texIndex;
in float v_tillingFactor;


uniform sampler2D u_texture[32];


void main() {

	vec4 textureColor = texture(u_texture[int(v_texIndex)], v_texCoord * v_tillingFactor);
	
	if (textureColor.w < 0.3)
		discard;

	
	fragColor = textureColor * v_color;
	
	
}