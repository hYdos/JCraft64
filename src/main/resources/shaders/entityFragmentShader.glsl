#version 140

in vec2 pass_textureCoords;

out vec4 out_Color;

uniform sampler2D textureSampler;
uniform vec3 lightColour[5];
uniform vec3 attenuation[5];
uniform float shineDamper;
uniform float reflectivity;
uniform vec3 skyColour;
uniform vec3 colour;

void main(void){
	vec4 colour = texture(textureSampler, pass_textureCoords);
	if(colour.a < 0.5){
		discard;
	}
	out_Color = colour;
}
