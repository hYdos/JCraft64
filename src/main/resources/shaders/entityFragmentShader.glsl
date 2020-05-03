#version 140

in vec2 pass_textureCoords;
in vec4 pass_colour;

out vec4 out_Color;

uniform sampler2D textureSampler;

void main(void){
	vec4 textureColour = texture(textureSampler, pass_textureCoords);
	if(textureColour.a == 0){
		discard;
	}

	if(textureColour.r == 0, textureColour.b == 0, textureColour.g == 0){
		out_Color = pass_colour;
	}else{
		out_Color = textureColour;
	}

}
