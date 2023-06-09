package com.dawnfall.engine.rendering.shaders;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;

public class ShaderProperties {
    public static ShaderProgram shader;

    public static FileHandle vertexPBR;
    public static FileHandle fragmentPBR;
 final static String vertexShader = "attribute vec4 a_position;\n" +
            "attribute vec4 a_color;\n" +
            "attribute vec2 a_texCoord0;\n" +
            "uniform mat4 u_projTrans;\n" +
            "varying vec4 v_color;\n" +
            "varying vec2 v_texCoords;\n" +
            "void main() {\n" +
            "v_color = vec4(1, 1, 1, 1);\n" +
            "v_texCoords = a_texCoord0;\n" +
            "gl_Position =  u_projTrans * a_position;\n" +
            "}";
 final static   String fragmentShader = "#ifdef GL_ES\n" +
            "precision mediump float;\n" +
            "#endif\n" +
            "varying vec4 v_color;\n" +
            "varying vec2 v_texCoords;\n" +
            "uniform sampler2D u_texture;\n" +
            "void main() {\n" +
            "gl_FragColor = v_color * texture2D(u_texture, v_texCoords);\n" +
            "}";

 public static void compilePBRShader(){
     vertexPBR = Gdx.files.internal("shaders/pbr/pbr.vs.glsl");
     fragmentPBR = Gdx.files.internal("shaders/pbr/pbr.fs.glsl");
 }
    public static void compileShader(){
        shader = new ShaderProgram(Gdx.files.internal("shaders/vertex.glsl").readString()
                ,Gdx.files.internal("shaders/fragment.glsl").readString() );
        if (shader.isCompiled()){
            System.out.println("Shader load complete");
        }else {
            System.out.println("Shader load failed.");
        }
    }
    public static void compileTextureShader(){
        shader = new ShaderProgram(vertexShader,fragmentShader);
        if (shader.isCompiled()){
            Gdx.app.log("Shaders","Shader load complete.");
        }else {
            Gdx.app.error("Shaders","Shader load failed.");
        }
    }
    public static void renderTextureShader(Mesh mesh, PerspectiveCamera camera, Texture texture){
        texture.bind();
        shader.bind();
        shader.setUniformMatrix("u_projTrans",camera.combined);
        shader.setUniformi("u_texture", 0);
        mesh.render(ShaderProperties.shader, GL20.GL_TRIANGLES);
    }
    public static void renderShader(Mesh mesh, PerspectiveCamera camera){
        ShaderProperties.shader.bind();
        ShaderProperties.shader.setUniformMatrix("u_projTrans",camera.combined);
        mesh.render(ShaderProperties.shader, GL20.GL_TRIANGLES);
    }
}
