//SIMPLES VERTEX SHADER
#version 330

layout(location = 0) in vec3 position;

out vec3 colorByPos;

void main() {

	gl_Position = vec4(position, 1.0);
	
	colorByPos = position;
	
}