//SIMPLE FRAGMENT SHADER
#version 330

in vec2 texCoord;

out vec4 fragColor;

uniform sampler2D u_Texture;

void main() {

	//fragColor = vec4(texCoord.x, texCoord.y, 0, 1.0);
	vec4 color = texture(u_Texture, texCoord);
	if (color.w < 0.8) discard;
	
	fragColor = color;

}