package br.fritzen.sandbox2d;

public class Piece {

	int blocks[][];
	int pivot = 2;
	
	int currentX;
	int currentY;
	
	
	public Piece(int x, int y) {
	
		this.currentX = x;
		this.currentY = y;
		
		blocks = new int[4][4];
		
		blocks[1][1] = 1;
		blocks[1][2] = 1;
		blocks[2][1] = 1;
		blocks[2][2] = 1;
		
	}
	
	
	
	public void rotate() {
		
	}
	
	
	public int[][] getState() {
		return blocks;
	}
}

