//SIMPLES VERTEX SHADER
#version 330

layout(location = 0) in vec3 position;
layout(location = 1) in vec2 texture;
layout(location = 2) in vec3 normal;

out vec2 v_texCoord;
out vec3 v_fragPos;
out vec3 v_normal;

out mat4 modelViewMatrix;

uniform mat4 u_ViewProjection;
uniform mat4 u_View;
uniform mat4 u_Projection;
uniform mat4 u_Model;

void main() {

	gl_Position = u_ViewProjection * u_Model * vec4(position, 1.0);
	
	v_texCoord = texture;
	v_fragPos = vec3(u_Model * vec4(position, 1.0));
	
	//https://learnopengl.com/Lighting/Basic-Lighting
	v_normal = mat3(transpose(inverse(u_Model))) * normal;
	
	modelViewMatrix = u_View * u_Model;
}