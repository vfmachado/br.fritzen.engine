package br.fritzen.engine.renderer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.joml.Vector2f;
import org.joml.Vector4f;

import br.fritzen.engine.Application;
import br.fritzen.engine.animator.Animator2D;
import br.fritzen.engine.components.OrthographicCameraController;
import br.fritzen.engine.core.layers.Layer;
import br.fritzen.engine.events.Event;


public class Batch2DRendererTextureTest extends Layer {

	private OrthographicCameraController cameraController;
	
	private Vector2f position = new Vector2f();
	
	private Vector2f size = new Vector2f();
	
	private Texture2D textureA = Texture2D.create("textures/tiles/adventurerSheet.png");
	
	
	private Animator2D running;
	private Animator2D iddle;
	private Animator2D attacking;
	
	
	public Batch2DRendererTextureTest() {
		super("Layer");
		
		this.cameraController = new OrthographicCameraController(1280f/720f);
		
		
		Vector2f[] listOfCoordsIddle = new Vector2f[4];
		for (int i = 0; i < 4; i++) {
			listOfCoordsIddle[i] = new Vector2f(i, 15);
		}
		iddle = new Animator2D("Iddle", textureA, new Vector2f(50, 37), listOfCoordsIddle, 0.5f);
		
		
		Vector2f[] listOfCoordsRunning = new Vector2f[6];
		for (int i = 0; i < 6; i++) {
			listOfCoordsRunning[i] = new Vector2f(1 + i, 14);
		}
		running = new Animator2D("Running", textureA, new Vector2f(50, 37), listOfCoordsRunning, 0.25f);
		
		
		Vector2f[] listOfCoordsAttacking = new Vector2f[17];
		for (int i = 0; i < 17; i++) {
			listOfCoordsAttacking[i] = new Vector2f(i % 7, 9 - (i / 7));
		}
		attacking = new Animator2D("Attacking", textureA, new Vector2f(50, 37), listOfCoordsAttacking, 0.15f);
	}

	
	@Override
	public void onUpdate(float deltatime) {
		
		cameraController.onUpdate(deltatime);
		
		//update
		iddle.update(deltatime);
		running.update(deltatime);
		attacking.update(deltatime);
	}
	
	
	@Override
	public void onRender() {
	
		RenderCommand.clear();
		Renderer2D.beginScene(cameraController.getCamera());
		
		//size.set(1.3513f * 2, 1 * 2);
		//Renderer2D.drawQuad(position.set(-16, 6), size, texture1);
		
		size.set(1.3513f * 4, 1 * 4);
		iddle.draw(position.set(-18, 6), size);
		
		size.set(1.3513f * 4, 1 * 4);
		running.draw(position.set(-12, 6), size);
		
		size.set(1.3513f * 4, 1 * 4);
		attacking.draw(position.set(-6, 6), size);
		
		size.set(1.3513f * 20, 1 * 20);
		Renderer2D.drawQuad(new Vector2f(0, -10), size, textureA);
		
		Renderer2D.endScene();
		
	}
	
	
	@Override
	public void onEvent(Event e) {
		cameraController.onEvent(e);
	}
	
	
	public static void main(String[] args) {
		
		Application app = Application.create("Batch Renderer 2D - Textures", 1280, 720);
		app.addLayer(new Batch2DRendererTextureTest());
		app.run();
		
	}
}
