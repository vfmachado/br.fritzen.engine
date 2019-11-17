#version 330

layout(location = 0) out vec4 fragColor;

in vec2 v_texCoord;

uniform vec4 color;

uniform int textureRepeats = 1;

uniform sampler2D u_texture;

void main() {

	fragColor = texture(u_texture, v_texCoord * textureRepeats) * color;
	
}