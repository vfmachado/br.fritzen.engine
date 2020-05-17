package br.fritzen.engine.renderer;

import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.List;

import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;
import org.lwjgl.BufferUtils;

import br.fritzen.engine.platform.opengl.OpenGLShader;
import br.fritzen.engine.platform.opengl.OpenGLShaderType;
import br.fritzen.engine.platform.opengl.VertexBufferLayout;
import br.fritzen.engine.renderer.Buffer.IndexBuffer;
import br.fritzen.engine.renderer.Buffer.VertexArray;
import br.fritzen.engine.renderer.Buffer.VertexBuffer;
import br.fritzen.engine.renderer.shader.Shader;
import br.fritzen.engine.utils.EngineBuffers;
import br.fritzen.engine.utils.Pair;
import lombok.Getter;
import lombok.Setter;

/**
 * Static Class to get all necessary for render a Quad
 * 
 * @author fritz
 *
 */
public class Renderer2DStorage {

	@Getter
	private Shader shader;

	@Getter
	private VertexArray vertexArray;
	
	@Getter 
	private VertexBuffer vertexBuffer;

	// private static Renderer2DStorage instance = null;

	private static final int MAX_QUADS = 1000;

	private static final int MAX_VERTICES = MAX_QUADS * 4;

	private static final int MAX_INDICES = MAX_QUADS * 6;

	public static final int MAX_TEXTURE_SLOTS = 32;
	
	
	private int quadIndexCount = 0;

	@Getter
	private int[] textureSlot = new int[MAX_TEXTURE_SLOTS];
	
	@Getter
	@Setter
	private int textureSlotIndex = 1;
	
	private FloatBuffer floatBuffer = BufferUtils.createFloatBuffer(MAX_VERTICES * QuadVertex.FLOATS);
	
	
	public class QuadVertex {

		public static final int FLOATS = 11;
		public static final int BYTES = 44;
		
		public Vector3f position;
		public Vector2f texCoord;
		public Vector4f color;
		public float texIndex;
		public float tillingFactor;


		public QuadVertex() {
			position = new Vector3f();
			texCoord = new Vector2f();
			color = new Vector4f();
			texIndex = 0.0f;
			tillingFactor = 1.0f;
		}
		
	}

	private QuadVertex[] quadVertexBuffer;

	public QuadVertex getQuadPointer() {
		return quadVertexBuffer[quadIndexCount];
	}
	
	public void startQuadPointer() {
		quadIndexCount = 0;
	}
	
	public void increaseQuadPointer() {
		quadIndexCount++;
	}
	
	public boolean mustFlush() {
		return quadIndexCount >= MAX_VERTICES;
	}

	public Renderer2DStorage() {

		quadVertexBuffer = new QuadVertex[MAX_VERTICES];
		for (int i = 0; i < MAX_VERTICES; i++) {
			quadVertexBuffer[i] = new QuadVertex();
		}
		
		List<Pair<String, OpenGLShaderType>> shaders = new ArrayList<Pair<String, OpenGLShaderType>>();
		shaders.add(new Pair<String, OpenGLShaderType>("shaders/quad-vertex.shader", OpenGLShaderType.VERTEX));
		shaders.add(new Pair<String, OpenGLShaderType>("shaders/quad-fragment.shader", OpenGLShaderType.FRAGMENT));
		this.shader = new OpenGLShader(shaders);

		
		this.vertexArray = VertexArray.create();

		this.vertexBuffer = VertexBuffer.create(MAX_VERTICES * QuadVertex.BYTES); 
																					

		List<VertexBufferLayout> layouts = new ArrayList<VertexBufferLayout>();
		layouts.add(new VertexBufferLayout(3)); // position
		layouts.add(new VertexBufferLayout(2)); // texture
		layouts.add(new VertexBufferLayout(4)); // color
		layouts.add(new VertexBufferLayout(1)); // texIndex
		layouts.add(new VertexBufferLayout(1)); // tillingFactor
		this.vertexArray.addInterleavedVBO(vertexBuffer, layouts);

		int[] indices = new int[MAX_INDICES];

		int offset = 0;
		for (int i = 0; i < MAX_INDICES; i += 6) {
			
			indices[i + 0] = offset + 0;
			indices[i + 1] = offset + 1;
			indices[i + 2] = offset + 2;
			
			indices[i + 3] = offset + 2;
			indices[i + 4] = offset + 3;
			indices[i + 5] = offset + 0;
			
			offset += 4;
		}
		
		
		IndexBuffer ibo = IndexBuffer.create(EngineBuffers.createIntBuffer(indices), indices.length);
		this.vertexArray.setIB(ibo);

	}

	public void updateData() {
		
		this.vertexBuffer.setData(EngineBuffers.updateFloatBuffer(floatBuffer, quadVertexBuffer, quadIndexCount), quadIndexCount);
		
	}

	public int getQuadCount() {
		return quadIndexCount/4 * 6;
	}
	
	/*
	 * private static Renderer2DStorage get() {
	 * 
	 * if (instance == null) { instance = new Renderer2DStorage(); }
	 * 
	 * return instance; }
	 */

}
