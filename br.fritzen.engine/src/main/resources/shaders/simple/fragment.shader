//SIMPLE FRAGMENT SHADER
#version 330

uniform vec3 color;

out vec4 fragColor;

in vec3 colorByPos;

void main() {

	vec3 calculatedColor = colorByPos;

	if (colorByPos.x < 0) {
		calculatedColor.x *= -1;
	}
	if (colorByPos.y < 0) {
		calculatedColor.y *= -1;
	}
	if (colorByPos.z < 0) {
		calculatedColor.z *= -1;
	}

	fragColor = vec4(calculatedColor, 1.0);
	fragColor = vec4(color, 1.0);

}