package br.fritzen.engine.components;

import br.fritzen.engine.renderer.Buffer.VertexArray;
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
	
	
}
