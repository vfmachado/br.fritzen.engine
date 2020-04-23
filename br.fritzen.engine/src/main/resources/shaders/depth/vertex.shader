#version 330

layout(location = 0) in vec3 position;
layout(location = 1) in vec2 texture;
layout(location = 2) in vec3 normal;
layout(location = 3) in vec3 tangent;


uniform mat4 u_Model;
uniform mat4 u_LightViewMatrix;
uniform mat4 u_OrthoProjectionMatrix;
uniform mat4 lightViewProj;

void main() {
    
    //gl_Position =  u_OrthoProjectionMatrix * u_LightViewMatrix * u_Model * vec4(position, 1.0f);
    gl_Position =  lightViewProj * u_Model * vec4(position, 1.0f);
    
}