//SIMPLES VERTEX SHADER
#version 330

layout(location = 0) in vec3 position;

out vec4 flatColor;

uniform mat4 u_ViewProjection;
uniform mat4 u_Model;

uniform vec4 color;

void main() {

	gl_Position = u_Model * u_ViewProjection * vec4(position, 1.0);
	
	flatColor = color;
	
}