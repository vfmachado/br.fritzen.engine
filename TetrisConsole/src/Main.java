import java.util.Random;
import java.util.Scanner;

public class Main {

	public static class DEFINES {
		
		public static final int WIDTH = 10;
		public static final int HEIGHT = 20;
		
	}
	
	public static class  Piece {
		
		int [][] state = new int[4][4];
		
		int currentX;
		int currentY;
		
		public Piece() {

			
			int [][] state = generateRandomState();
			
			//random rotation
			Random r = new Random();
			int numberOfRotations = r.nextInt(3);
			
			for (int i = 0; i < numberOfRotations; i++) {
				this.state = rotate4x4(this.state);
			}
			
			
			this.currentX = DEFINES.WIDTH/2 -2;
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
		
		public Piece clone() {
			
			Piece p = new Piece();
			
			p.state = this.state;
			p.currentX = this.currentX;
			p.currentY = this.currentY;
			
			return p;
		}
		
	}
	
	
	public static void main(String[] args) {
		
		Scanner in = new Scanner(System.in);
		int [][] board = new int[DEFINES.HEIGHT][DEFINES.WIDTH];
		
		Piece currentPiece = new Piece();
		
		/*
		System.out.println("Initial:\n");
		printAnyMat(currentPiece.state);
		
		System.out.println("\nFirst rotation\n");
		int [][] rotated = rotate4x4(currentPiece.state);
		printAnyMat(rotated);
		
		System.out.println("\nSecond Rotation\n"); 
		rotated = rotate4x4(rotated);
		printAnyMat(rotated);
		
		System.out.println("\nThird Rotation\n"); 
		rotated = rotate4x4(rotated);
		printAnyMat(rotated);
		*/
		
		
		while (true) {
			
			fakeClear();
			
			//check for complete lines and down
			removeCompleteLines(board);
			
			printBoard(board, currentPiece);
			
			System.out.println();
			char control = in.next().toLowerCase().charAt(0);
			
			//check if piece can go down
			switch (control) {
			
			case 'w':
				
				//clone current Piece
				Piece temp = currentPiece.clone();
				
				temp.state = rotate4x4(temp.state);
				
				//if fits, update piece state
				if (pieceFit(board, temp, 0, 0)) {
					currentPiece.state = temp.state;
				}
				
				break;
			
			
			case 's':
				if (pieceFit(board, currentPiece, 0, 1)) {
					currentPiece.currentY++;
				} else {
					//fix on board
					fixPiece(board, currentPiece);
					
					//generates a new Piece
					currentPiece = new Piece();
					
				}
				break;
				
			case 'a':
				if (pieceFit(board, currentPiece, -1, 0)) {
					currentPiece.currentX--;
				}
				break;
				
			case 'd':
				if (pieceFit(board, currentPiece, 1, 0)) {
					currentPiece.currentX++;
				}
				break;
			
			}
			
		}
				
		
	}
	
	
	private static void removeCompleteLines(int[][] board) {
		
		for (int lin = DEFINES.HEIGHT -1; lin >= 0; lin--) {
			
			boolean clear = true;
			
			for (int j = 0; j < DEFINES.WIDTH; j++) {
				if (board[lin][j] == 0)
					clear = false;
			}
			
			if (clear) {
				
				for (int uplines = lin; uplines > 0; uplines--) {
					for (int j = 0; j < DEFINES.WIDTH; j++) {
						board[uplines][j] = board[uplines -1][j];
					}
				}
			}
			
		}
		
	}


	public static void fixPiece(int[][] board, Piece piece) {
		
		
		for (int px = 0; px < 4; px++) {
			
			for (int py = 0; py < 4; py++) {
			
				if (piece.state[py][px] == 1)
				board[piece.currentY + py][piece.currentX + px] = piece.state[py][px];
				
			}
		}
		
	}


	public static boolean pieceFit(int[][] board, Piece piece, int dx, int dy) {
		
		//PIECE STATE
		for (int px = 0; px < 4; px++) {
			
			for (int py = 0; py < 4; py++) {
		
				//piece empty parts
				if (piece.state[py][px] == 0) {
					continue;
				}
				
				if (piece.currentY + py + dy >= DEFINES.HEIGHT) {
					return false;
				}
				
				if (piece.currentX + px + dx < 0 || piece.currentX + px + dx >= DEFINES.WIDTH) {
					return false;
				}
					
				if (board[piece.currentY + py + dy][piece.currentX + px + dx] != 0) {
					return false;
				}
				
				//}
				
			}
		}
		
		return true;
		
	}
	
	public static void fakeClear() {
		for (int i = 0; i < 50; i++)
			System.out.println();
	}
	
	
	public static void printBoard(int [][] mat, Piece piece) {
		
		for (int i = 0; i < mat.length; i++) {
			System.out.print(" # ");
			for (int j = 0; j < mat[i].length; j++) {
				
				//state
				if (i >= piece.currentY && i < piece.currentY + 4
				&& j >= piece.currentX && j < piece.currentX + 4
				&& piece.state[i - piece.currentY][j - piece.currentX] == 1) {
					System.out.print("X "); 
				} else {
					System.out.print(mat[i][j] + " ");
				}
			
			}
			System.out.println("# ");
			
			
		}
		
		for (int j = 0; j < mat[mat.length-1].length +2; j++) {
			
			System.out.print(" #");
		
		}
		
	}
	
	public static int[][] rotate4x4(int mat[][]) {
		
		int copy[][] = new int[4][4];
		
		for (int i = 0; i < 4; i++) {
			
			for (int j = 0; j < 4; j++) {
				
				//if (i + 1 == 4) continue;
				copy[i][j] = mat[3 -j][(i + 1) % 4];
				
			}
			
		}
		
		return copy;
	}
	
	
	public static void printAnyMat(int mat[][]) {
		
		for (int i = 0; i < mat.length; i++) {
			
			for (int j = 0; j < mat[i].length; j++) {
				System.out.print(mat[i][j] + " ");
			}
			System.out.println();
		}
	}
}
