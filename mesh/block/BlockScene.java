package com.dawnfall.engine.mesh.block;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Disposable;
import com.dawnfall.engine.rendering.shaders.ShaderResource;

public class BlockScene implements Disposable {
    public Mesh mesh;
    private final Vector3 scale;
    private ModelBatch batch;
    private ModelInstance instance,instance2;
    private Texture texture;
    private final String texturePath;
    public BlockScene(String texturePath,Vector3 scale){
      this.texturePath = texturePath;
      this.scale = scale;
      createBlock();
    }
    public Mesh createBlock(){
        batch = new ModelBatch();
        instance2 = showViewPortXYZ();
        instance = showViewPortGrid();

        float[] vertices = {
                //left.
                0.0f,0.0f,0.0f,//v0
                0,1,0,
                0,0,
                0.0f,1.0f,0.0f,//v1
                0,1,0,
                0,1,
                1.0f,0.0f,0.0f,//v2
                0,1,0,
                1,0,
                1.0f,1.0f,0.0f,//v3
                0,1,0,
                1,1,
                //right.
                0.0f,0.0f,-1.0f,//v4
                0,-1,0,
                0,0,
                0.0f,1.0f,-1.0f,//v5
                0,-1,0,
                0,1,
                1.0f,0.0f,-1.0f,//v6
                0,-1,0,
                1,0,
                1.0f,1.0f,-1.0f,//v7
                -1,0,0,
                1,1,
                //back.
                0.0f,0.0f,0.0f,//v8
                0,-1,0,
                0,0,
                0.0f,0.0f,-1.0f,//v9
                0,-1,0,
                1,0,
                0.0f,1.0f,-1.0f,//v10
                0,-1,0,
                1,1,
                0.0f,1.0f,0.0f,//v11
                -1,0,0,
                0,1,
                //front.
                1.0f,0.0f,0.0f,//v12
                0,1,0,
                0,0,
                1.0f,0.0f,-1.0f,//v13
                0,1,0,
                1,0,
                1.0f,1.0f,-1.0f,//v14
                0,1,0,
                1,1,
                1.0f,1.0f,0.0f,//v15
                1,0,0,
                0,1,
                //top.
                0.0f,1.0f,0.0f,//v16
                0,1,0,
                0,1,
                1.0f,1.0f,0.0f,//v17
                0,1,0,
                1,1,
                1.0f,1.0f,-1.0f,//v18
                0,1,0,
                1,0,
                0.0f,1.0f,-1.0f,//v19
                1,0,0,
                0,0,
                //bottom.
                0.0f,0.0f,0.0f,//v20
                0,1,0,
                0,1,
                1.0f,0.0f,0.0f,//v21
                0,1,0,
                1,1,
                1.0f,0.0f,-1.0f,//v22
                0,1,0,
                1,0,
                0.0f,0.0f,-1.0f,//v23
                1,0,0,
                0,0,
        };
        short[] indices = {
                //left face.
                0, 2, 3, 0, 3, 1,
                //front face.
                12, 13, 14, 12, 15, 14,
                //right face.
                6, 4, 5, 6, 5, 7,
                //back face.
                8, 9, 10, 8, 11, 10,
                //bottom face.
                20, 21, 22, 20, 23, 22,
                //top face.
                16, 17, 18, 16, 19, 18,
        };
        mesh = new Mesh(true, vertices.length,indices.length,
                VertexAttribute.Position(),VertexAttribute.Normal(),VertexAttribute.TexCoords(0));
        mesh.setVertices(vertices);
        mesh.setIndices(indices);
        texture = new Texture(Gdx.files.internal(texturePath));
        mesh.scale(scale.x,scale.y,scale.z);
        return mesh;
    }
    public ModelInstance showViewPortGrid(){
        Material material = new Material(new ColorAttribute(ColorAttribute.Diffuse,new Color(23434223)));
        int attributes = VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal | VertexAttributes.Usage.ColorPacked;
        Model grid,model;
        ModelBuilder builder = new ModelBuilder();
        model = builder.createXYZCoordinates(16,material,attributes);
        grid = builder.createLineGrid(50,50,16,16,material,attributes);
        return instance = new ModelInstance(grid);
    }
    public ModelInstance showViewPortXYZ(){
        Material material = new Material(new ColorAttribute(ColorAttribute.Diffuse,Color.WHITE));
        int attributes = VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal | VertexAttributes.Usage.ColorPacked;
        Model model;
        ModelBuilder builder = new ModelBuilder();
        model = builder.createXYZCoordinates(50,material,attributes);
        return instance = new ModelInstance(model);
    }
    public void renderBlock(PerspectiveCamera camera, ShaderResource shader){
        shader.renderTextureShader(createBlock(),texturePath,GL20.GL_TRIANGLES);
        batch.begin(camera);
        batch.render(instance2);
        batch.render(instance);
        batch.end();
    }
    @Override
    public void dispose() {
        mesh.dispose();
        instance.model.dispose();
        instance2.model.dispose();
    }
}
