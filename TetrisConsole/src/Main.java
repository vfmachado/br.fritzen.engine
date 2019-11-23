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
		
			int [][] state = {
					{0, 0, 1, 0},
					{0, 0, 1, 0},
					{0, 0, 1, 0},
					{0, 0, 1, 0}
			};
			
			this.currentX = DEFINES.WIDTH/2;
			this.currentY = DEFINES.HEIGHT/2;
			
			this.state = state;
			
		}
		
		
		public int rotate() {
			return 0;
		}
	}
	
	
	public static void main(String[] args) {
		
		Scanner in = new Scanner(System.in);
		int [][] board = new int[DEFINES.HEIGHT][DEFINES.WIDTH];
		
		Piece currentPiece = new Piece();
		
		while (true) {
			
			fakeClear();
			
			printMat(board);
			
			System.out.println();
			char control = in.next().toLowerCase().charAt(0);
			
			//check if piece can go down
			
		}
				
		
	}
	
	
	public boolean pieceFit(int[][] board, Piece piece) {
		
		//PIECE STATE
		for (int px = 0; px < 4; px++) {
			
			for (int py = 0; py < 4; py++) {
		
				//BOUNDARIES
				if (piece.currentX + px >= 0 && piece.currentX + px < DEFINES.WIDTH) {
					
					if (piece.currentY + py >= 0 && piece.currentY + py < DEFINES.HEIGHT) {

						//LOGICS
						
						
						
					}

				}
				
			}
		}
		
		
		
		return true;
		
	}
	
	public static void fakeClear() {
		for (int i = 0; i < 50; i++)
			System.out.println();
	}
	
	
	public static void printMat(int [][] mat) {
		
		for (int i = 0; i < mat.length; i++) {
			System.out.print(" # ");
			for (int j = 0; j < mat[i].length; j++) {
				
				System.out.print(mat[i][j] + " ");
			
			}
			System.out.println("# ");
			
			
		}
		
		for (int j = 0; j < mat[mat.length-1].length +2; j++) {
			
			System.out.print(" #");
		
		}
		
	}
	
	
}
