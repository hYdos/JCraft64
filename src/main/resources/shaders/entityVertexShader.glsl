#version 150


in vec4 position;
in vec2 textureCoords;
in vec4 colour;

out vec2 pass_textureCoords;
out vec4 pass_colour;

void main(void){
	pass_colour = colour;
	pass_textureCoords = textureCoords;
	gl_Position = position;


}
