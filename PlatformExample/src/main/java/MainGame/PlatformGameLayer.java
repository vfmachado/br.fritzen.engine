package MainGame;

import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;

import Character.Player;
import br.fritzen.engine.components.OrthographicCameraController;
import br.fritzen.engine.core.layers.Layer;
import br.fritzen.engine.events.Event;
import br.fritzen.engine.renderer.RenderCommand;
import br.fritzen.engine.renderer.Renderer2D;

public class PlatformGameLayer extends Layer {

	private Player player;
	
	private static final float ROWS = 80;
	private static final float COLS = 100;
	private static final float OFFSET = 0.2f;
	private static final float SIZE = 0.2f;
	
	private OrthographicCameraController cameraController;
	
	private Vector3f position = new Vector3f();
	
	private Vector2f size = new Vector2f();
	
	private Vector4f color = new Vector4f(1);
	
	public PlatformGameLayer(String name) {
		super(name);
		
		this.cameraController = new OrthographicCameraController(1280f/720f);
		
		this.player = new Player(new Vector2f(0, 0));
	}

	
	@Override
	public void onUpdate(float deltatime) {
		
		cameraController.onUpdate(deltatime);
		
		this.player.update(deltatime);
				
	}
	
	
	@Override
	public void onRender() {
	
		RenderCommand.clear();
		Renderer2D.beginScene(cameraController.getCamera());

		size.set(SIZE, SIZE);
		
		for (float x = -COLS/2; x < COLS/2; x += OFFSET) {
		
			for (float y = -ROWS/2; y < ROWS/2; y += OFFSET) {
				
				position.set(x, y, -0.1f);
				color.set( (COLS/2 + x)/COLS, 0.5f, (ROWS/2 + y)/ROWS, 1);
				Renderer2D.drawQuad(position, size, color);
			}
		}

		player.draw();
		
		Renderer2D.endScene();
	}
	
	
	@Override
	public void onEvent(Event e) {
		cameraController.onEvent(e);
	}
}
