package br.fritzen.tetris;

import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;

import br.fritzen.engine.components.OrthographicCamera;
import br.fritzen.engine.core.input.Input;
import br.fritzen.engine.core.layers.Layer;
import br.fritzen.engine.events.Event;
import br.fritzen.engine.events.EventType;
import br.fritzen.engine.events.key.KeyEvent;
import br.fritzen.engine.renderer.RenderCommand;
import br.fritzen.engine.renderer.Renderer2D;
import br.fritzen.engine.renderer.Texture2D;

public class BoardLayer extends Layer {

	public static class Defines {
		public static final int WIDTH = 10;
		public static final int HEIGHT = 20;
		
	}
	
	private Vector2f blockSize = new Vector2f(30, 30);
	private Vector3f tempPos = new Vector3f();
	private Vector4f emptyColor = new Vector4f(0.8f, 0.8f, 0.8f, 1);
	private Vector4f fixedColor = new Vector4f(0.2f, 0.4f, 0.8f, 1);
	private Vector4f pieceColor = new Vector4f(0.2f, 0.8f, 0.3f, 1);
	private Vector4f castPieceColor = new Vector4f(0.2f, 0.8f, 0.3f, 0.4f);
	private Texture2D blockTexture = Texture2D.create("textures/block.png");
	
	private Board board;
	private Piece piece;
	private Piece tempPiece;
	private Piece castPiece;
	
	private OrthographicCamera camera;;
	
	public BoardLayer() {
		super("Board Layer");
		
		camera = new OrthographicCamera(0, 300, 600, 0);
	
		board = new Board(Defines.HEIGHT, Defines.WIDTH);
		piece = new Piece();
		castPiece = new Piece();
		tempPiece = new Piece();
		
		RenderCommand.clearColor(0,  0,  0,  1);
	}

	private float time;
	
	@Override
	public void onUpdate(float deltatime) {
		super.onUpdate(deltatime);
		
		piece.clone(castPiece);
		
		while (board.pieceFit(castPiece, 0, 1)) {
			castPiece.increaseY();
		}
		
		//move board down
		time += deltatime;
		if (time >= 0.25f) {
		
			//update everything
			if (board.pieceFit(piece, 0, 1)) {
				piece.increaseY();
			} else {
				//fix on board
				board.fixPiece(piece);
				
				//generates a new Piece
				piece = new Piece();
				
			}
			time = 0;
			
			board.removeCompleteLines();
		}
	}
	
	
	@Override
	public void onEvent(Event e) {
		
		if (e.getEventType() == EventType.KeyPressedEvent) {
			KeyEvent evt = (KeyEvent) e;
			if (evt.getKeyCode() == Input.KEY_W) {

				piece.clone(tempPiece);
				
				tempPiece.rotateThis();
				
				//if fits, update piece state
				if (board.pieceFit(tempPiece, 0, 0)) {
					piece.setState(tempPiece.getState());
				}
				
				
			} else if (evt.getKeyCode() == Input.KEY_A) {
				
				if (board.pieceFit(piece, -1, 0)) {
					piece.decreaseX();
				}
				
			} else if (evt.getKeyCode() == Input.KEY_D) {
				if (board.pieceFit(piece, 1, 0)) {
					piece.increaseX();
				}
			} else if (evt.getKeyCode() == Input.KEY_S) {
				if (board.pieceFit(piece, 0, 1)) {
					piece.increaseY();
				} else {
					board.fixPiece(piece);
					piece = new Piece();
				}
				
			}
			
			
			if (evt.getKeyCode() == Input.KEY_SPACE) {
				
				while (board.pieceFit(piece, 0, 1)) {
					piece.increaseY();
				}
				
			}
			
		}
		
		
	}
	
	
	@Override
	public void onRender() {
		
		RenderCommand.clear();
		
		Renderer2D.beginScene(camera);
		
		//Renderer2D.drawQuad(tmpPos, blockSize, blockTexture, quadColor);
		
		int[][] B = board.getBoard();
		
		tempPos.z = 0.0f;
		for (int i = 0; i < B.length; i++) {
			for (int j = 0; j < B[i].length; j++) {
				
				tempPos.x = j * 30;
				tempPos.y = i * 30;
				
				if (B[i][j] == 0) {
					Renderer2D.drawQuad(tempPos, blockSize, blockTexture, emptyColor);
				}
				else {
					Renderer2D.drawQuad(tempPos, blockSize, blockTexture, fixedColor);
				}
			}
		}
		
		//current piece
		int[][] P = piece.getState();
		tempPos.z = 0.1f;
		for (int i = 0; i < P.length; i++) {
			for (int j = 0; j < P[i].length; j++) {
				tempPos.x = (j+piece.getCurrentX()) * 30;
				tempPos.y = (i+piece.getCurrentY()) * 30;
				
				if (P[i][j] == 1) {
					Renderer2D.drawQuad(tempPos, blockSize, blockTexture, pieceColor);
				}
			}
		}
		
		//cast piece
		P = castPiece.getState();
		tempPos.z = 0.1f;
		for (int i = 0; i < P.length; i++) {
			for (int j = 0; j < P[i].length; j++) {
				tempPos.x = (j+castPiece.getCurrentX()) * 30;
				tempPos.y = (i+castPiece.getCurrentY()) * 30;
				
				if (P[i][j] == 1) {
					Renderer2D.drawQuad(tempPos, blockSize, blockTexture, castPieceColor);
				}
			}
		}
		
		Renderer2D.endScene();
	}
	
}
