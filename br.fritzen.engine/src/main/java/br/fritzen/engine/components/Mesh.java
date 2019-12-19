package br.fritzen.engine.components;

import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.PointerBuffer;
import org.lwjgl.assimp.AIFace;
import org.lwjgl.assimp.AIMesh;
import org.lwjgl.assimp.AIScene;
import org.lwjgl.assimp.AIVector3D;
import org.lwjgl.assimp.Assimp;

import br.fritzen.engine.core.EngineLog;
import br.fritzen.engine.platform.opengl.VertexBufferLayout;
import br.fritzen.engine.renderer.Buffer.IndexBuffer;
import br.fritzen.engine.renderer.Buffer.VertexArray;
import br.fritzen.engine.renderer.Buffer.VertexBuffer;
import br.fritzen.engine.utils.EngineBuffers;
import lombok.Getter;
import lombok.Setter;

public class Mesh {

	@Getter
	@Setter
	private VertexArray vertexArray;

	@Getter
	@Setter
	private String name;
	
	
	public Mesh() {
		
	}
	
	/**
	 * For tests reasons, it's generating quad with size 1 centered on 0, 0, 0
	 * @param filename
	 */
	/*
	public void loadAssimp(String filename) {
		
		
		AIScene aiScene = Assimp.aiImportFile("src/main/resources/" + filename, 
				Assimp.aiProcess_JoinIdenticalVertices | 
				Assimp.aiProcess_Triangulate | 
				Assimp.aiProcess_FixInfacingNormals | 
				Assimp.aiProcess_CalcTangentSpace);
		
		if (aiScene == null) {
		    //throw new Exception("Error loading model");
			EngineLog.severe("Error loading model with Assimp");
			System.exit(0);
		}
		
		
		int numMeshes = aiScene.mNumMeshes();
		
		System.out.println(numMeshes);
		//PointerBuffer aiMeshes = aiScene.mMeshes();
		
//		for (int i = 0; i < numMeshes; i++) {
//		    AIMesh aiMesh = AIMesh.create(aiMeshes.get(i));
//		    
//		
//		}
//		
	
		
		PointerBuffer aiMeshes = aiScene.mMeshes();
		AIMesh aiMesh = AIMesh.create(aiMeshes.get(0));
		
		System.out.println("Vertices: " + aiMesh.mVertices().remaining());
		System.out.println("Textures: " + aiMesh.mTextureCoords().remaining());
		System.out.println("Normals: " + aiMesh.mNormals().remaining());
		
		System.out.println(aiMesh.mNumFaces());
		
		
		AIVector3D.Buffer aiVertices = aiMesh.mVertices();
		float[] bufferVertices = new float[aiVertices.remaining() * 3];
		int index = 0;
	    while (aiVertices.remaining() > 0) {
	        AIVector3D aiVertex = aiVertices.get();
	        bufferVertices[index++] = aiVertex.x();
	        bufferVertices[index++] = aiVertex.y();
	        bufferVertices[index++] = aiVertex.z();
        }
	    
	    AIVector3D.Buffer textCoords = aiMesh.mTextureCoords(0);
	    float[] bufferTextures = new float[textCoords.remaining() * 2];
		index = 0;
	    while (textCoords.remaining() > 0) {
	        AIVector3D aiVertex = textCoords.get();
	        bufferTextures[index++] = aiVertex.x();
	        bufferTextures[index++] = aiVertex.y();
	       
        }
	    
	    
	    int numFaces = aiMesh.mNumFaces();
	    System.out.println("Num faces: " + numFaces);
	    index = 0;
	    int[] indices = new int[numFaces * 3];
        AIFace.Buffer aiFaces = aiMesh.mFaces();
        System.out.println("AI Faces capacity: " + aiFaces.capacity());
        for (int i = 0; i < numFaces; i++) {
            AIFace aiFace = aiFaces.get(i);
            IntBuffer buffer = aiFace.mIndices();
            while (buffer.remaining() > 0) {
                indices[index++] = buffer.get();
            }
        }
	    
	    this.vertexArray = VertexArray.create();
	    
	    VertexBuffer vboPos = VertexBuffer.create(EngineBuffers.createFloatBuffer(bufferVertices), bufferVertices.length * 4);
	    
	    this.vertexArray.addVB(vboPos, 0, 3);
	    
	    VertexBuffer vboTex = VertexBuffer.create(EngineBuffers.createFloatBuffer(bufferTextures), bufferTextures.length * 4);
	    
	    this.vertexArray.addVB(vboTex, 1, 2);
	    
	    
	    IndexBuffer ibo = IndexBuffer.create(EngineBuffers.createIntBuffer(indices), indices.length);
		this.vertexArray.setIB(ibo);
	}
	*/
	
	
	
}
