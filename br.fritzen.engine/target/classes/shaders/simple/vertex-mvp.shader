//SIMPLES VERTEX SHADER
#version 330

layout(location = 0) in vec3 position;


uniform mat4 u_ViewProjection;
uniform mat4 u_Model;

void main() {

	gl_Position = u_ViewProjection * u_Model * vec4(position, 1.0);
	
}