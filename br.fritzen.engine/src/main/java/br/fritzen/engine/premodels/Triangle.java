package br.fritzen.engine.premodels;

import org.lwjgl.opengl.GL11;

import br.fritzen.engine.gameobject.GameObject;
import br.fritzen.engine.platform.opengl.IndexBufferObject;
import br.fritzen.engine.platform.opengl.VertexArrayObject;
import br.fritzen.engine.platform.opengl.VertexBufferObject;
import br.fritzen.engine.renderer.shader.Shader;
import br.fritzen.engine.renderer.shader.ShaderUniform;
import br.fritzen.engine.utils.EngineBuffers;

/**
 * NOT WORKING FOR NOW...
 * @author fritz
 *
 */
public class Triangle extends GameObject {

	private float[] vertices;
	
	private float[] color;
	
	private int[] indices = { 0, 1, 2};
	
	Shader shader;
	VertexArrayObject vao;
	IndexBufferObject ibo;
	
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
		process();
	}
	
	
	public float[] getColor() {
		return this.color;
	}
	
	
	private void process() {
		
		VertexBufferObject vbo = new VertexBufferObject(EngineBuffers.createFloatBuffer(this.vertices));
		this.vao = new VertexArrayObject();
		this.vao.addVBO(vbo, 0, 3);
		ibo = new IndexBufferObject(EngineBuffers.createIntBuffer(indices));
		ibo.unbind();
		
	}
	
	
	public void updateUniforms(Shader shader) {
		this.shader.updateUniform(ShaderUniform.color, color[0], color[1], color[2]);
	}
	
	
	public void draw() {
	
		this.vao.bind();
		this.ibo.bind();
		
		
		
		GL11.glDrawElements(GL11.GL_TRIANGLES, ibo.getCount(), GL11.GL_UNSIGNED_INT, ibo.getOffset());
		//bind vao
		//update color
		//draw
		
		
	}
}
