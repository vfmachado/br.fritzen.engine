//SIMPLES VERTEX SHADER
#version 330

layout(location = 0) in vec3 position;
layout(location = 1) in vec2 texCoord;
layout(location = 2) in vec4 color;
layout(location = 3) in float texIndex;
layout(location = 4) in float tillingFactor;

out vec2 v_texCoord;
out vec4 v_color;
out float v_texIndex;
out float v_tillingFactor;

uniform mat4 u_ViewProjection;
//uniform mat4 u_Model;


void main() {

	//gl_Position = u_ViewProjection * u_Model * vec4(position, 1.0);
	gl_Position = u_ViewProjection * vec4(position, 1.0);
	
	v_texCoord = texCoord;
	v_color = color;
	v_texIndex = texIndex;
	v_tillingFactor = tillingFactor;
}