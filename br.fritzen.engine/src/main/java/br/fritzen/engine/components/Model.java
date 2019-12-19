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
import br.fritzen.engine.renderer.Buffer.IndexBuffer;
import br.fritzen.engine.renderer.Buffer.VertexArray;
import br.fritzen.engine.renderer.Buffer.VertexBuffer;
import br.fritzen.engine.renderer.Material;
import br.fritzen.engine.utils.EngineBuffers;
import br.fritzen.engine.utils.Pair;
import lombok.Getter;

public class Model {

	@Getter
	private List<Pair<Mesh, Integer>> meshes;
	
	@Getter
	private List<Material> materials;
	
	
	/**
	 * Load full model with multiple meshes and materials with assimp
	 * @param filename
	 */
	public Model(String filename) {
		
		this.meshes = new ArrayList<Pair<Mesh, Integer>>();
		
		AIScene aiScene = Assimp.aiImportFile("src/main/resources/" + filename, 
				Assimp.aiProcess_JoinIdenticalVertices | 
				Assimp.aiProcess_Triangulate | 
				Assimp.aiProcess_FixInfacingNormals |
				Assimp.aiProcess_GenUVCoords |
				Assimp.aiProcess_GenNormals |
				Assimp.aiProcess_CalcTangentSpace);
		
		if (aiScene == null) {
		    //throw new Exception("Error loading model");
			EngineLog.severe("Error loading model with Assimp");
			System.exit(0);
		}
		
		
		PointerBuffer aiMeshes = aiScene.mMeshes();
		
		for (int i = 0; i < aiScene.mNumMeshes(); i++) {
		    
			AIMesh aiMesh = AIMesh.create(aiMeshes.get(i));
		    
			//process mesh
			
			Mesh mesh = new Mesh();
			
			String name = aiMesh.mName().dataString();
			mesh.setName(name);
			System.out.println("Processing mesh: " + name);
			VertexArray vao = VertexArray.create();
			
			AIVector3D.Buffer aiVertices = aiMesh.mVertices();
			if (aiVertices != null) {
				System.out.println("\tProcessing vertices");
				VertexBuffer vbVertice = createVB(aiVertices, 3);
				vao.addVB(vbVertice, 0, 3);
			}
			
			AIVector3D.Buffer aiTextures = aiMesh.mTextureCoords(0);
			if (aiTextures != null) {
				System.out.println("\tProcessing textures");
				VertexBuffer vbTexture = createVB(aiTextures, 2);
				vao.addVB(vbTexture, 1, 2);
			}
			
			AIVector3D.Buffer aiNormals = aiMesh.mNormals();
			if (aiNormals != null) {
				System.out.println("\tProcessing normals");
				VertexBuffer vbNormal = createVB(aiNormals, 3);
				vao.addVB(vbNormal, 2, 3);
			}
			
			
			AIFace.Buffer aiFaces = aiMesh.mFaces();
	        IndexBuffer ib = createIB(aiFaces);
	        vao.setIB(ib);
			
			mesh.setVertexArray(vao);
			
			//material reference
			int materialIndex = aiMesh.mMaterialIndex();
			
			this.meshes.add(new Pair<Mesh, Integer>(mesh, materialIndex));
		}
	}
	
	
	public VertexBuffer createVB(AIVector3D.Buffer aiBuffer, int size) {
		
		float[] bufferData = new float[aiBuffer.remaining() * size];
		int index = 0;
	    while (aiBuffer.remaining() > 0) {
	        AIVector3D aiVertex = aiBuffer.get();
	        bufferData[index++] = aiVertex.x();
	        bufferData[index++] = aiVertex.y();
	        if (size == 3) {
	        	bufferData[index++] = aiVertex.z();
	        }
        }
	    
	    return VertexBuffer.create(EngineBuffers.createFloatBuffer(bufferData), bufferData.length * 4);
	    
	}
	

	public IndexBuffer createIB(AIFace.Buffer aiFaces) {
		
		int[] indices = new int[aiFaces.capacity() * 3];
        int index = 0;
	    
        for (int i = 0; i < aiFaces.capacity(); i++) {
            AIFace aiFace = aiFaces.get(i);
            IntBuffer buffer = aiFace.mIndices();
            while (buffer.remaining() > 0) {
                indices[index++] = buffer.get();
            }
        }
	    
        return IndexBuffer.create(EngineBuffers.createIntBuffer(indices), indices.length);
	}
}
