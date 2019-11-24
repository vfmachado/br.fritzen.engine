package br.fritzen.tetris;


import br.fritzen.tetris.BoardLayer.Defines;
import lombok.Getter;

public class Board {

	@Getter
	private int[][] board;
	
	
	public Board(int lines, int columns) {
		this.board = new int[lines][columns];
	}
	
	
	public void removeCompleteLines() {
		
		for (int lin = this.board.length -1; lin >= 0; lin--) {
			
			boolean clear = true;
			
			for (int j = 0; j < this.board[lin].length; j++) {
				if (board[lin][j] == 0)
					clear = false;
			}
			
			if (clear) {
				
				for (int uplines = lin; uplines > 0; uplines--) {
					for (int j = 0; j < this.board[uplines].length; j++) {
						board[uplines][j] = board[uplines -1][j];
					}
				}
			}
			
		}
		
		
	}
	
	
	public void fixPiece(Piece piece) {
		
		for (int px = 0; px < 4; px++) {
			
			for (int py = 0; py < 4; py++) {
			
				if (piece.getState()[py][px] == 1)
				board[piece.getCurrentY() + py][piece.getCurrentX() + px] = piece.getState()[py][px];
				
			}
		}
		
		
	}
	
	
	
	public boolean pieceFit(Piece piece, int dx, int dy) {
		
		//PIECE STATE
		for (int px = 0; px < 4; px++) {
			
			for (int py = 0; py < 4; py++) {
		
				//piece empty parts
				if (piece.getState()[py][px] == 0) {
					continue;
				}
				
				if (piece.getCurrentY() + py + dy >= Defines.HEIGHT) {
					return false;
				}
				
				if (piece.getCurrentX() + px + dx < 0 || piece.getCurrentX() + px + dx >= Defines.WIDTH) {
					return false;
				}
					
				if (board[piece.getCurrentY() + py + dy][piece.getCurrentX() + px + dx] != 0) {
					return false;
				}
				
			}
		}
		
		return true;
		
	}
	
}
