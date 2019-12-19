package br.fritzen.engine.components;

import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

import org.joml.Vector4f;
import org.lwjgl.PointerBuffer;
import org.lwjgl.assimp.AIColor4D;
import org.lwjgl.assimp.AIFace;
import org.lwjgl.assimp.AIMaterial;
import org.lwjgl.assimp.AIMesh;
import org.lwjgl.assimp.AIScene;
import org.lwjgl.assimp.AIString;
import org.lwjgl.assimp.AIVector3D;
import org.lwjgl.assimp.Assimp;

import br.fritzen.engine.core.EngineLog;
import br.fritzen.engine.renderer.Buffer.IndexBuffer;
import br.fritzen.engine.renderer.Buffer.VertexArray;
import br.fritzen.engine.renderer.Buffer.VertexBuffer;
import br.fritzen.engine.renderer.Material;
import br.fritzen.engine.renderer.Texture2D;
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
		this.materials = new ArrayList<Material>();
		
		AIScene aiScene = Assimp.aiImportFile("src/main/resources/" + filename, 
				Assimp.aiProcess_JoinIdenticalVertices | 
				Assimp.aiProcess_Triangulate | 
				Assimp.aiProcess_FixInfacingNormals |
				//Assimp.aiProcess_GenUVCoords |
				//Assimp.aiProcess_GenSmoothNormals |
				Assimp.aiProcess_CalcTangentSpace);
		
		if (aiScene == null) {
		    //throw new Exception("Error loading model");
			EngineLog.severe("Error loading model with Assimp");
			System.exit(0);
		}
		
		
		PointerBuffer aiMaterials = aiScene.mMaterials();
        
        for (int i = 0; i < aiScene.mNumMaterials(); ++i) {
            
        	AIMaterial aiMaterial = AIMaterial.create(aiMaterials.get(i));
        	
        	Material material = new Material();
        	
        	Vector4f ambientColor = this.extractColor(aiMaterial, Assimp.AI_MATKEY_COLOR_AMBIENT);
            material.setAmbientColor(ambientColor);
        	
            Vector4f diffuseColor = this.extractColor(aiMaterial, Assimp.AI_MATKEY_COLOR_DIFFUSE);
            material.setAmbientColor(diffuseColor);
            
            Vector4f specularColor = this.extractColor(aiMaterial, Assimp.AI_MATKEY_COLOR_SPECULAR);
            material.setAmbientColor(specularColor);
        	
            AIString text = AIString.create();
            Assimp.aiGetMaterialTexture(aiMaterial, Assimp.aiTextureType_DIFFUSE, 0, text, (IntBuffer) null, null, null, null, null, null);
    		
            System.out.println("Texture with assimp: " + text.dataString());
            
            if (!text.dataString().equals("")) {
	            String currentPath = filename.substring(0, filename.lastIndexOf("/")+1);
	            System.out.println(currentPath);
	            
	            Texture2D texture = Texture2D.create(currentPath + text.dataString());
	            
	            material.setDiffuseTexture(texture);
            }
        	materials.add(material);
        	
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
				System.out.println("\tProcessing vertices: " + aiVertices.remaining());
				VertexBuffer vbVertice = createVB(aiVertices, 3);
				vao.addVB(vbVertice, 0, 3);
			}
			
			AIVector3D.Buffer aiTextures = aiMesh.mTextureCoords(0);
			if (aiTextures != null) {
				System.out.println("\tProcessing textures: " + aiTextures.remaining());
				VertexBuffer vbTexture = createVB(aiTextures, 2);
				vao.addVB(vbTexture, 1, 2);
			}
			
			AIVector3D.Buffer aiNormals = aiMesh.mNormals();
			if (aiNormals != null) {
				System.out.println("\tProcessing normals: " + aiNormals.remaining());
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
	
	
	public Vector4f extractColor(AIMaterial aiMaterial, String assimpIdentifier) {
		
		AIColor4D mColor = AIColor4D.create();
        if (Assimp.aiGetMaterialColor(aiMaterial, assimpIdentifier,
                Assimp.aiTextureType_NONE, 0, mColor) != 0) {
            throw new IllegalStateException(Assimp.aiGetErrorString());
        }
        
        return new Vector4f(mColor.r(), mColor.g(), mColor.b(), mColor.a());
        
	}
}
