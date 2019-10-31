#version 330

layout(location = 0) out vec4 fragColor;

in vec2 v_texCoord;

uniform vec4 color;

uniform int hasTexture;
uniform int hasColor;

uniform int textureRepeats = 1;

uniform sampler2D u_texture;

void main() {

	
	if (hasTexture == 1 && hasColor == 0) {
	
		fragColor = texture(u_texture, v_texCoord * textureRepeats);
	
	} else if (hasTexture == 1 && hasColor == 1) {
	
		fragColor = texture(u_texture, v_texCoord * textureRepeats) * color;
	
	} else {
	
		fragColor = color;
		
	}
	
	
}