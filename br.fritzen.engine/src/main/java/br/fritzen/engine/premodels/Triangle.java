package br.fritzen.engine.premodels;

import org.lwjgl.opengl.GL11;

import br.fritzen.engine.gameobject.GameObject;
import br.fritzen.engine.platform.opengl.OpenGLIndexBuffer;
import br.fritzen.engine.platform.opengl.OpenGLVertexArray;
import br.fritzen.engine.platform.opengl.OpenGLVertexBuffer;
import br.fritzen.engine.renderer.shader.Shader;
import br.fritzen.engine.renderer.shader.ShaderUniform;
import br.fritzen.engine.utils.EngineBuffers;

/**
 * NOT WORKING FOR NOW...
 * @author fritz
 *
 */
public class Triangle {

	private float[] vertices;
	
	private float[] color = {0, 0, 0};
	
	private int[] indices = { 0, 1, 2};
	
	OpenGLVertexArray vao;
	OpenGLIndexBuffer ibo;
	
	/**
	 * An array with 3 x 3 float represeting the vertices of the triangle
	 * @param vertices
	 */
	public Triangle(float[] vertices) {
		this.vertices = vertices;
		process();
	}
	
	/**
	 * Color of the triangle
	 * @param color 3 float array representing RGB color
	 */
	public void setColor(float[] color) {
		this.color = color;
	}
	
	
	public float[] getColor() {
		return this.color;
	}
	
	
	private void process() {
		
		OpenGLVertexBuffer vbo = new OpenGLVertexBuffer(EngineBuffers.createFloatBuffer(this.vertices), this.vertices.length * 4);
		//this.vao = new OpenGLVertexArrayObject();
		this.vao = new OpenGLVertexArray();
		this.vao.addVB(vbo, 0, 3);
		ibo = new OpenGLIndexBuffer(EngineBuffers.createIntBuffer(indices), indices.length);
		ibo.unbind();
		
	}
	
	
	public void updateUniforms(Shader shader) {
		shader.updateUniform(ShaderUniform.color, color[0], color[1], color[2]);
	}
	
	
	public void draw(Shader shader) {
	
		shader.bind();
		this.updateUniforms(shader);
		
		this.vao.bind();
		this.ibo.bind();
		
		
		
		GL11.glDrawElements(GL11.GL_TRIANGLES, ibo.getCount(), GL11.GL_UNSIGNED_INT, ibo.getOffset());
		//bind vao
		//update color
		//draw
		
		
	}
}
