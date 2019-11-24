package br.fritzen.tetris;

import br.fritzen.engine.Application;

public class Main {

	public static void main(String[] args) {
		
		Application tetrisGame = Application.create("Tetris", 300, 600);
		tetrisGame.addLayer(new BoardLayer());
		tetrisGame.run();
		
	}

}
