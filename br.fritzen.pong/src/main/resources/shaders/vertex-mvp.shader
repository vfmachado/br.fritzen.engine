//SIMPLES VERTEX SHADER
#version 330

layout(location = 0) in vec3 position;
layout(location = 1) in vec2 textures;

out vec2 texCoord;

uniform mat4 u_ViewProjection;
uniform mat4 u_Model;

void main() {

	texCoord = textures;

	gl_Position = u_Model * u_ViewProjection * vec4(position, 1.0);
	
}