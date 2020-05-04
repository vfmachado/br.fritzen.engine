package br.fritzen.engine.renderer;

import org.joml.Vector2f;
import org.joml.Vector4f;

import br.fritzen.engine.Application;
import br.fritzen.engine.components.OrthographicCameraController;
import br.fritzen.engine.core.layers.Layer;
import br.fritzen.engine.events.Event;


public class Batch2DRendererTest extends Layer {

	private static final float ROWS = 80;
	private static final float COLS = 100;
	private static final float OFFSET = 0.2f;
	private static final float SIZE = 0.15f;
	
	private OrthographicCameraController cameraController;
	
	private Vector2f position = new Vector2f();
	
	private Vector2f size = new Vector2f();
	
	private Vector4f color = new Vector4f();
	
	
	public Batch2DRendererTest() {
		super("Layer");
		
		this.cameraController = new OrthographicCameraController(1280f/720f);
		
	}

	@Override
	public void onUpdate(float deltatime) {
		
		cameraController.onUpdate(deltatime);
				
	}
	
	
	@Override
	public void onRender() {
	
		RenderCommand.clear();
		Renderer2D.beginScene(cameraController.getCamera());
		
		size.set(SIZE, SIZE);
		
		for (float x = -COLS/2; x < COLS/2; x += OFFSET) {
		
			for (float y = -ROWS/2; y < ROWS/2; y += OFFSET) {
				
				position.set(x, y);
				color.set( (COLS/2 + x)/COLS, 0.5f, (ROWS/2 + y)/ROWS, 1);
				Renderer2D.drawQuad(position, size, color);
			}
		}
				
		Renderer2D.endScene();
		
	}
	
	
	@Override
	public void onEvent(Event e) {
		cameraController.onEvent(e);
	}
	
	
	public static void main(String[] args) {
		
		Application app = Application.create("Batch Renderer 2D - QUADS: " + (int) (ROWS/OFFSET * COLS/OFFSET), 1280, 720);
		app.addLayer(new Batch2DRendererTest());
		app.run();
		
	}
}
