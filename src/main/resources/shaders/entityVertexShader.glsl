#version 150


in vec4 position;
in vec2 textureCoords;

out vec2 pass_textureCoords;
out vec3 surfaceNormal;
out vec3 toLightVector[5];
out vec3 toCameraVector;
out float visibility;

uniform mat4 transformationMatrix;
uniform mat4 projectionMatrix;
uniform mat4 viewMatrix;
uniform vec3 lightPosition[5];

const vec4 plane = vec4(0, -1, 0, 15);

uniform float useFakeLighting;

const float density = 0.0025;
const float gradient = 10.0;

void main(void){
	pass_textureCoords = textureCoords;
	gl_Position = position;


}
