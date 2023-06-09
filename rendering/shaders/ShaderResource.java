package com.dawnfall.engine.rendering.shaders;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;

public class ShaderResource {
    private  ShaderProgram shaderProgram;
    private  FileHandle vertex;
    private  FileHandle fragment;
    private  Texture texture;
    private PerspectiveCamera camera;
    private String vertexShader = "attribute vec4 a_position;\n" +
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
    private String fragmentShader = "#ifdef GL_ES\n" +
            "precision mediump float;\n" +
            "#endif\n" +
            "varying vec4 v_color;\n" +
            "varying vec2 v_texCoords;\n" +
            "uniform sampler2D u_texture;\n" +
            "void main() {\n" +
            "gl_FragColor = v_color * texture2D(u_texture, v_texCoords);\n" +
            "}";
    public ShaderResource(PerspectiveCamera camera){
        this.camera = camera;
    }
    public void setVertexShader(String shader){
        vertexShader = shader;
    }
    public void setFragmentShader(String shader){
        fragmentShader = shader;
    }
    public String getVertexShader(){
        return vertexShader;
    }
    public  String getFragmentShader(){
        return fragmentShader;
    }
   public void setVertexFromFile(String path){
        vertex = Gdx.files.internal(path);
    }
    public void setFragmentFromFile(String path){
        fragment = Gdx.files.internal(path);
    }
    public  void compileShader(){
       shaderProgram = new ShaderProgram(vertex,fragment);
    }
   public void renderTextureShader(Mesh mesh,String texturePath, int primitiveType){
        if (texture == null){
            texture = setShaderTexture(texturePath);
        }
        shaderProgram.bind();
        texture.bind();
        shaderProgram.setUniformMatrix("u_projTrans", camera.combined);
        shaderProgram.setUniformi("u_texture", 0);
        mesh.render(shaderProgram, primitiveType);
   }
   public void renderShader(Mesh mesh,int primitiveType){
       shaderProgram.bind();
       shaderProgram.setUniformMatrix("u_projectionViewMatrix", camera.combined);
       mesh.render(shaderProgram, primitiveType);
   }
   private Texture setShaderTexture(String path){
        return new Texture(Gdx.files.internal(path));
   }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    public FileHandle getFragment() {
        return fragment;
    }

    public FileHandle getVertex() {
        return vertex;
    }

    public Texture getTexture() {
        return texture;
    }

    public PerspectiveCamera getCamera() {
        return camera;
    }

    public ShaderProgram getShaderProgram() {
        return shaderProgram;
    }
}
