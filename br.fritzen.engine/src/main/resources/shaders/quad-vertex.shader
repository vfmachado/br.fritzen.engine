//SIMPLES VERTEX SHADER
#version 330

layout(location = 0) in vec3 position;
layout(location = 1) in vec2 texCoord;

out vec2 v_texCoord;

uniform mat4 u_ViewProjection;
uniform mat4 u_Model;


void main() {

	gl_Position = u_ViewProjection * u_Model * vec4(position, 1.0);
	
	v_texCoord = texCoord;
	
}