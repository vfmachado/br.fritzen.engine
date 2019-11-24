package br.fritzen.sandbox2d;

import java.util.Random;

import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;

import br.fritzen.engine.components.OrthographicCameraController;
import br.fritzen.engine.core.input.Input;
import br.fritzen.engine.core.layers.Layer;
import br.fritzen.engine.events.Event;
import br.fritzen.engine.events.EventType;
import br.fritzen.engine.events.key.KeyEvent;
import br.fritzen.engine.renderer.RenderCommand;
import br.fritzen.engine.renderer.Renderer2D;
import br.fritzen.engine.renderer.Texture2D;
import br.fritzen.sandbox2d.GameController.Direction;


public class MainLayer extends Layer {

	private OrthographicCameraController cameraController;
	
	private Vector2f vec2 = new Vector2f();
	
	//private Vector2f quadPosition = new Vector2f(0, 8.75f);
	private Vector2f quadSize = new Vector2f(1.25f, 1.25f);
	private Vector4f quadColor = new Vector4f(0.8f, 0.2f, 0.3f, 1);
	
	private Texture2D blockTexture = Texture2D.create("block.png");
	
	private Vector3f backgroundPosition = new Vector3f(-10.0f, -10.0f, -0.5f);
	private Vector2f backgroundSize = new Vector2f(20f, 20f);
	private Texture2D backgroundTexture = Texture2D.create("textures/default.png");
	
	private Vector4f grayColor = new Vector4f(0.5f);
	
	private int textureRepeats = 2;
	
	private GameController controller;
	
	private int[][] mat = new int[16][8];
	
	private Random r = new Random();
	
	public MainLayer() {
		
		super("MainLayer");
		
		cameraController = new OrthographicCameraController(1280f/720f);
		
		controller = new GameController(Direction.DOWN);
			
		RenderCommand.clearColor(0,  0,  0,  1);
	}

	float time = 0;
	
	@Override
	public void onUpdate(float deltatime) {
		
		cameraController.onUpdate(deltatime);

		time += deltatime;

		if (time > 0.5f) {
			
			updateMatrix();
			
			time = 0;
			/*
			//VERTICAL
			if (controller.getLastDirection() == Direction.DOWN) {
				quadPosition.y -= 1.25f;
			} else if (controller.getLastDirection() == Direction.UP) {
				quadPosition.y += 1.25f;
			}
 			
			if (quadPosition.y < -10) {
				quadPosition.y = 8.75f;
			} else if (quadPosition.y >= 10) {
				quadPosition.y = -10f;
			}
			
			//HORIZONTAL
			if (controller.getLastDirection() == Direction.LEFT) {
				piece.currentX--;
			} else if (controller.getLastDirection() == Direction.RIGHT) {
				piece.currentY++;
			}
 			
			
			if (piece.currentX < -10) {
				piece.currentX = -10;
			} else if (piece.currentX >= 10) {
				piece.currentX = 10;
			}
			*/
			
		}
		
	}
	
	
	@Override
	public void onRender() {
	
		RenderCommand.clear();
		
		
		Renderer2D.beginScene(cameraController.getCamera());

		Renderer2D.setTextureRepeats(textureRepeats);
		Renderer2D.drawQuad(backgroundPosition, backgroundSize, backgroundTexture, grayColor);
		Renderer2D.setTextureRepeats(1);
		
		//Renderer2D.drawQuad(quadPosition, quadSize, quadColor);
		
		for (int i = 0; i < 16; i++) {
			
			for (int j = 0; j < 8; j++) {
		
				if (mat[i][j] == 1) {
					vec2.set(1.25f * j - 5f, 10 -1.25f - 1.25f * i);
					
					Renderer2D.drawQuad(vec2, quadSize, blockTexture, quadColor);
				}
			}
		}
		
		
		Renderer2D.endScene();
		
	}
	
	
	@Override
	public void onOvent(Event e) {
		
		if (e.getEventType() == EventType.KeyReleasedEvent) {
			KeyEvent evt = (KeyEvent) e;
			if (evt.getKeyCode() == Input.KEY_KP_ADD) {
				textureRepeats++;
			} else if (evt.getKeyCode() == Input.KEY_KP_SUBTRACT) {
				textureRepeats--;
			}
		}
		
		cameraController.onEvent(e);
		
		controller.onEvent(e);
		
	}
	
	
	int full = 0;
	
	//Piece piece = new Piece(-1, 2);
	
	private void updateMatrix() {
	
		removeCompleteLines();
		
		
		//boolean down = false;
		/*
		boolean canDown = true;
		int[][] pieceState = piece.getState();
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
			
				
			}
		}
		
		//move piece
		
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				try {
					mat[piece.currentX + i][piece.currentY + j] = pieceState[i][j];
				} catch (ArrayIndexOutOfBoundsException e) {}
			}
		}
		
		
		
		for (int i = 15; i > 0; i--) {
			
			for (int j = 0; j < 8; j++) {
				
				if (mat[i][j] == 0 && mat[i-1][j] == 1) {
					mat[i][j] = 1;
					mat[i-1][j] = 0;
					//down = true;
					
					
				}
			}
		}
		
		/*
		if (!down) {
			mat[0][r.nextInt(8)] = 1;
			full++;
			
		}
		*/
		
		if (full == 16 * 8) {
			mat = new int[16][8];
			full = 0;
		}
	
	}

	
	private void removeCompleteLines() {
		
		boolean complete = true;
		for (int i = 0; i < 8; i++) {
			
			if (mat[15][i] == 0) {
				complete = false;
			}
		}
		
		if (complete) {
			for (int i = 0; i < 8; i++) {
				
				mat[15][i] = 0;
			}
		}


	}
}
