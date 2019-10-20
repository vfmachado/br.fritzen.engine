//SIMPLES VERTEX SHADER
#version 330

layout(location = 0) in vec3 position;

out vec3 colorByPos;

uniform mat4 u_ViewProjection;
uniform mat4 u_Model;

void main() {

	gl_Position = u_Model * u_ViewProjection * vec4(position, 1.0);
	
	colorByPos = position;
	
}