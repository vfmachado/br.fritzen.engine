//SIMPLES VERTEX SHADER
#version 330

layout(location = 0) in vec3 position;
layout(location = 1) in vec2 texture;
layout(location = 2) in vec3 normal;
layout(location = 3) in vec3 tangent;

out vec2 v_texCoord;
out vec3 v_fragPos;
out vec3 v_normal;

out mat3 tbnMatrix;

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
	//v_normal = mat3(transpose(inverse(u_Model))) * normal;
	
	//bennybox	- same result as above
	v_normal = (u_Model * vec4(normal, 0.0)).xyz;
	
	//check normalize cost.
	vec3 n = normalize((u_Model * vec4(normal, 0.0)).xyz);
	vec3 t = normalize((u_Model * vec4(tangent, 0.0)).xyz);
	
	t = normalize(t - dot(t, n) * n);
	
	vec3 biTangent = cross(t, n);
	
	
	tbnMatrix = mat3(t, biTangent, n);
	
	modelViewMatrix = u_View * u_Model;
}