package br.fritzen.engine.components;

import java.util.ArrayList;
import java.util.List;

import br.fritzen.engine.platform.opengl.VertexBufferLayout;
import br.fritzen.engine.renderer.Buffer.IndexBuffer;
import br.fritzen.engine.renderer.Buffer.VertexArray;
import br.fritzen.engine.renderer.Buffer.VertexBuffer;
import br.fritzen.engine.utils.EngineBuffers;
import lombok.Getter;

public class Mesh {

	@Getter
	private VertexArray vertexArray;

	/**
	 * For tests reasons, it's generating quad with size 1 centered on 0, 0, 0
	 * @param filename
	 */
	public Mesh(String filename) {
		
		//load with Assimp
		float[] positions = {
				  -0.5f, -0.5f, 0, 0, 0, //0
	               0.5f, -0.5f, 0, 1, 0,	//1
	              -0.5f,  0.5f, 0, 0, 1,	//2
	               0.5f,  0.5f, 0, 1, 1	//3
	            };
		
		this.vertexArray = VertexArray.create();
		
		VertexBuffer vbo = VertexBuffer.create(EngineBuffers.createFloatBuffer(positions), positions.length * 4);
		
		List<VertexBufferLayout> layout = new ArrayList<VertexBufferLayout>();
		layout.add(new VertexBufferLayout(3));
		layout.add(new VertexBufferLayout(2));
		
		this.vertexArray.addInterleavedVBO(vbo, layout);
		
		int[] indices = { 0, 1, 2, 1, 3, 2};
		
		IndexBuffer ibo = IndexBuffer.create(EngineBuffers.createIntBuffer(indices), indices.length);
		this.vertexArray.setIB(ibo);
		
		
	}
	
}
