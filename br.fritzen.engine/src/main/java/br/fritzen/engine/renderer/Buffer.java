package br.fritzen.engine.renderer;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.List;

import br.fritzen.engine.core.EngineLog;
import br.fritzen.engine.platform.opengl.OpenGLIndexBuffer;
import br.fritzen.engine.platform.opengl.OpenGLVertexArray;
import br.fritzen.engine.platform.opengl.OpenGLVertexBuffer;
import br.fritzen.engine.platform.opengl.VertexBufferLayout;

public interface Buffer {

	
public interface VertexArray {
		
		public static VertexArray create() {
			
			switch (Renderer.SELECTED_API) {
			case NONE:
				EngineLog.severe("None API selected");
				break;
			case OPENGL:
				return new OpenGLVertexArray();
				
			case VULKAN:
				EngineLog.severe("Not supported yet");
				break;
			}
			
			EngineLog.severe("Invalid Renderer API.");
			System.exit(1);
			return null;
		}
		
		public void bind();
		
		public void unbind();
		
		public void addVB(VertexBuffer vb, int attribArray, int size);
		
		public void addVB(VertexBuffer vb, int attribArray, VertexBufferLayout layout);
		
		public void addInterleavedVBO(VertexBuffer vb, List<VertexBufferLayout> layouts);
		
		public void setIB(IndexBuffer ib);
		
		public IndexBuffer getIB();
	}
	
	
	public interface VertexBuffer {
		
		public static VertexBuffer create(FloatBuffer data, int size) {
			
			switch (Renderer.SELECTED_API) {
			case NONE:
				EngineLog.severe("None API selected");
				break;
			case OPENGL:
				return new OpenGLVertexBuffer(data, size);
				
			case VULKAN:
				EngineLog.severe("Not supported yet");
				break;
			}
			
			EngineLog.severe("Invalid Renderer API.");
			System.exit(1);
			return null;
		}
		
		public void bind();
		
		public void unbind();
		
	}
	
	
	public interface IndexBuffer {
		
		public static IndexBuffer create(IntBuffer indices, int count) {

			switch (Renderer.SELECTED_API) {
			case NONE:
				EngineLog.severe("None API selected");
				break;
			case OPENGL:
				return new OpenGLIndexBuffer(indices, count);
				
			case VULKAN:
				EngineLog.severe("Not supported yet");
				break;
			}
			
			EngineLog.severe("Invalid Renderer API.");
			System.exit(1);
			return null;
		
		}
		
		public void bind();
		
		public void unbind();
		
		public void setOffset(int offset);

		public int getOffset();
		
		public int getCount();
		
	}
}
