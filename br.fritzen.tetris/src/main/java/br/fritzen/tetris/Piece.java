package br.fritzen.tetris;

import java.util.Random;

import br.fritzen.tetris.BoardLayer.Defines;
import lombok.Getter;
import lombok.Setter;

public class  Piece {
	
	@Getter
	@Setter
	private int [][] state = new int[4][4];
	
	@Getter
	private int currentX;
	
	@Getter
	private int currentY;
	
	
	public Piece() {

		
		int [][] state = generateRandomState();
		
		Random r = new Random();
		int numberOfRotations = r.nextInt(3);
		
		for (int i = 0; i < numberOfRotations; i++) {
			rotateThis();
		}
		
		this.currentX = Defines.WIDTH/2 -2;
		this.currentY = 0;
		
		this.state = state;
		
		
	}
	
	
	private int[][] generateRandomState() {
		
		Random r = new Random();
		
		switch(r.nextInt(7)) {
		case 0:
			return new int[][] {
				{0, 0, 1, 0},
				{0, 1, 1, 1},
				{0, 0, 0, 0},
				{0, 0, 0, 0}
			};
		case 1:
			return new int[][] {
				{0, 0, 0, 0},
				{0, 1, 1, 0},
				{0, 1, 1, 0},
				{0, 0, 0, 0}
			};
		case 2:
			return new int[][] {
				{0, 0, 0, 0},
				{0, 0, 1, 1},
				{0, 1, 1, 0},
				{0, 0, 0, 0}
			};
		case 3:
			return new int[][] {
				{0, 0, 0, 0},
				{0, 1, 1, 0},
				{0, 0, 1, 1},
				{0, 0, 0, 0}
			};
		case 4:
			return new int[][] {
				{0, 0, 1, 1},
				{0, 0, 1, 0},
				{0, 0, 1, 0},
				{0, 0, 0, 0}
			};
		case 5:
			return new int[][] {
				{0, 1, 1, 0},
				{0, 0, 1, 0},
				{0, 0, 1, 0},
				{0, 0, 0, 0}
			};
			
		case 6:
			return new int[][] {
				{0, 0, 1, 0},
				{0, 0, 1, 0},
				{0, 0, 1, 0},
				{0, 0, 1, 0}
			};
		
		}
		
		return null;
	}
	
	
	public void clone(Piece into) {
		
		into.state = this.state;
		into.currentX = this.currentX;
		into.currentY = this.currentY;
		
	}

	
	public int[][] rotate(int mat[][]) {
		
		int copy[][] = new int[4][4];
		
		for (int i = 0; i < 4; i++) {
			
			for (int j = 0; j < 4; j++) {
				
				//if (i + 1 == 4) continue;
				copy[i][j] = mat[3 -j][(i + 1) % 4];
				
			}
			
		}
		
		return copy;
	}	
	
	
	public void rotateThis() {
		
		this.state = this.rotate(this.state);
	
	}

	
	public void decreaseX() {
		this.currentX--;
	}
	
	
	public void increaseX() {
		this.currentX++;
	}
	
	
	public void increaseY() {
		this.currentY++;
	}
	
}
