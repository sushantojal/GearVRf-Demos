precision mediump float;
uniform vec4 u_color;
uniform float u_opacity;
uniform sampler2D tex0;

varying float is_deltaT_negative;

void main() {

    if ( is_deltaT_negative == 1.0f ) {
        discard;
    }
    vec4 color = texture2D(tex0, gl_PointCoord);
    gl_FragColor = vec4(color.r * u_color.r * u_opacity, color.g * u_color.g * u_opacity ,
     color.b * u_color.b * u_opacity, color.a * u_opacity);

}
