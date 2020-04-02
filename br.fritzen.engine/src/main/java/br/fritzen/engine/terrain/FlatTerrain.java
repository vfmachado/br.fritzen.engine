package br.fritzen.engine.terrain;

import br.fritzen.engine.components.Mesh;
import br.fritzen.engine.renderer.Buffer;
import br.fritzen.engine.renderer.Buffer.IndexBuffer;
import br.fritzen.engine.renderer.Buffer.VertexArray;
import br.fritzen.engine.renderer.Buffer.VertexBuffer;
import br.fritzen.engine.renderer.Material;
import br.fritzen.engine.scenegraph.GameObjectType;
import br.fritzen.engine.utils.EngineBuffers;
import lombok.Getter;

public class FlatTerrain extends Terrain {

	private static final float SIZE = 10;

	private static final int VERTEX_COUNT = 128;

	@Getter
	private float x, z;

	@Getter
	private Buffer.VertexArray vertexArray;

	@Getter
	private Material material;

	@Getter
	private Mesh mesh;

	
	public FlatTerrain(int gridX, int gridZ, Material material) {

		this.material = material;
		this.x = gridX * SIZE;
		this.z = gridZ * SIZE;

		vertexArray = generate();

		this.mesh = new Mesh();
		this.mesh.setVertexArray(vertexArray);
		
	}


	private Buffer.VertexArray generate() {

		int count = VERTEX_COUNT * VERTEX_COUNT;
		float[] vertices = new float[count * 3];
		float[] normals = new float[count * 3];
		float[] textureCoords = new float[count * 2];
		int[] indices = new int[6 * (VERTEX_COUNT - 1) * (VERTEX_COUNT - 1)];
		int vertexPointer = 0;
		
		for (int i = 0; i < VERTEX_COUNT; i++) {
			
			for (int j = 0; j < VERTEX_COUNT; j++) {
				vertices[vertexPointer * 3] = (float) j / ((float) VERTEX_COUNT - 1) * SIZE;
				vertices[vertexPointer * 3 + 1] = 0;
				vertices[vertexPointer * 3 + 2] = (float) i / ((float) VERTEX_COUNT - 1) * SIZE;
				
				textureCoords[vertexPointer * 2] = (float) j / ((float) VERTEX_COUNT - 1);
				textureCoords[vertexPointer * 2 + 1] = (float) i / ((float) VERTEX_COUNT - 1);
				
				normals[vertexPointer * 3] = 0;
				normals[vertexPointer * 3 + 1] = 1;
				normals[vertexPointer * 3 + 2] = 0;
				
				vertexPointer++;
			}
		}
		
		int pointer = 0;
		
		for (int gz = 0; gz < VERTEX_COUNT - 1; gz++) {
		
			for (int gx = 0; gx < VERTEX_COUNT - 1; gx++) {
			
				int topLeft = (gz * VERTEX_COUNT) + gx;
				int topRight = topLeft + 1;
				int bottomLeft = ((gz + 1) * VERTEX_COUNT) + gx;
				int bottomRight = bottomLeft + 1;
				indices[pointer++] = topLeft;
				indices[pointer++] = bottomLeft;
				indices[pointer++] = topRight;
				indices[pointer++] = topRight;
				indices[pointer++] = bottomLeft;
				indices[pointer++] = bottomRight;
			}
		}

		VertexArray vao = VertexArray.create();

		VertexBuffer vbVertice = VertexBuffer.create(EngineBuffers.createFloatBuffer(vertices), vertices.length * 4);
		VertexBuffer vbTexture = VertexBuffer.create(EngineBuffers.createFloatBuffer(textureCoords), textureCoords.length * 4);
		VertexBuffer vbNormal = VertexBuffer.create(EngineBuffers.createFloatBuffer(normals), normals.length * 4);
		
		
		vao.addVB(vbVertice, 0, 3);
		vao.addVB(vbTexture, 1, 2);
		vao.addVB(vbNormal, 2, 3);

		IndexBuffer ib = IndexBuffer.create(EngineBuffers.createIntBuffer(indices), indices.length);
		
		vao.setIB(ib);

		return vao;
	}


}
