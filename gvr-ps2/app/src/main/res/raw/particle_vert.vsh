precision mediump float;
attribute vec4 a_position;
attribute vec3 a_normal;
attribute vec2 a_texcoord;
uniform mat4 u_mvp;
uniform float u_velocity;
uniform float u_time;

varying float is_deltaT_negative;

void main() {

    float deltaTime = u_time - a_texcoord.x;

    if ( deltaTime < 0.0f ) {
        is_deltaT_negative = 1.0f;
    }
    else {
        is_deltaT_negative = 0.0f;
    }

    vec4 pos = a_position + vec4(0, (a_normal.y*deltaTime), -3.0f, 0);
    gl_PointSize = 50.0f;
	gl_Position = u_mvp * pos;
}

