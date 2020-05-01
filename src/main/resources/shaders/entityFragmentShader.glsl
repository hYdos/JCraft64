#version 140

in vec2 pass_textureCoords;
in vec3 surfaceNormal;
in vec3 toLightVector[5];
in vec3 toCameraVector;
in float visibility;

out vec4 out_Color;

uniform sampler2D textureSampler;
uniform vec3 lightColour[5];
uniform vec3 attenuation[5];
uniform float shineDamper;
uniform float reflectivity;
uniform vec3 skyColour;
uniform vec3 colour;

void main(void){
	vec4 textureColour = texture(textureSampler, pass_textureCoords);
	out_Color = textureColour;
}
