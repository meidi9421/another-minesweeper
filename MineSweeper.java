import java.util.*;
import java.lang.Math.*;

class MineSweeper{

	public static void main(String[] args) {
		boolean gameOver = false;
		int[][] grid = new int[8][8];
		int[][] gridUncovered  = new int[8][8];
		int[] ioUser = new int[2];
		initializeFullGrid(grid);
		drawFullGrid(grid, gridUncovered);
		while(!gameOver){
			takeInput(ioUser);
			gameOver = revealGridCell(ioUser[0],ioUser[1],grid,gridUncovered);
			drawFullGrid(grid, gridUncovered);
		}
	}

    /* Initializes the grid with ten bombs at different places
	* on the map. If there is already a bomb at the specified
	* co-ordinate it tries again.
	*/
	public static void initializeFullGrid(int[][] grid){
		Random randomEights = new Random();
		//-1 is equivelant to a bomb
		for(int i = 0; i < 10; i++){
			Boolean validPlacement = false;
			do{
				int x = randomEights.nextInt(7) + 0;
				int y = randomEights.nextInt(7) + 0;
				if(grid[x][y] != -1){
					grid[x][y] = -1;
					validPlacement = true;
				}
			}
			while(!validPlacement);
		}

	}

	public static void checkSurround (int row, int col, int[][]grid, int[][] gridUncovered){
		for(int posY = -1; posY < 2; posY++){
			for(int posX = -1; posX < 2; posX++){
				try{
					if(grid[row + posX][col + posY] != -1){
						gridUncovered[row + posX][col + posY] = bombSurround(row + posX, col + posY, grid);
					}
				} catch(ArrayIndexOutOfBoundsException e){
					continue;
				}
			}
		}
	}

	public static int bombSurround (int row, int col, int[][]grid){
		int bombsAround = 1;
		for(int posY = -1; posY < 2; posY++){
			for(int posX = -1; posX < 2; posX++){
			try{
				if(grid[row + posX][col + posY] == -1){
					bombsAround++;
				}
			} catch(ArrayIndexOutOfBoundsException e){
				continue;
				}
			}
		}
		return bombsAround;
	}

	// TEST IMPLEMENTATION ONLY
	public static boolean revealGridCell(int row, int col, int[][] grid, int[][] gridUncovered){
		checkSurround(row, col, grid, gridUncovered);
		if(grid[row][col] == -1){
			System.out.println("GAME OVER");
			return true;
		} else {
			return false;
		}
	}

	public static void drawFullGrid(int[][] grid, int[][] revealGridCell){
		System.out.println("  | 0 1 2 3 4 5 6 7");
		System.out.println("___________________");
		for(int i = 0; i < 8; i++){
			System.out.print(i + " | ");
			for(int z = 0; z < 8; z++){
				if( revealGridCell[i][z] == 0) {
					if(grid[i][z] == -1){
						System.out.print("X ");
					}else {
						System.out.print( ". ");
					}

				} else if(grid[i][z] == 0 && revealGridCell[i][z] == 1) {
					System.out.print("  ");
				} else if(grid[i][z] == 0 && revealGridCell[i][z] > 1){
					System.out.print((revealGridCell[i][z] - 1) + " ");
				}
			}
			System.out.println();
		}
	}

    /* takes in a domain or range number and insures it is in
	* the correct range
	*/
	public static boolean checkDomainRange(int domRan){
		if(domRan < 0 || domRan > 7){
			return false;
		}
		else{
			return true;
		}
	}

    /* Gets user input and returns it
	* through the array ioCoords
	*/
	public static void takeInput(int[] ioCoords){
		Boolean properInput = false;
		while(!properInput){

			System.out.print("Select a cell. Row value (a digit between 0 and 7): ");
			int rowVal = checkValidInput();
			System.out.print("Select a cell. Column value (a digit between 0 and 7): ");
			int colVal = checkValidInput();
			if(checkDomainRange(rowVal) && checkDomainRange(colVal)){
				ioCoords[0] = rowVal;
				ioCoords[1] = colVal;
				properInput = true;
			} else {
				System.out.println("One of those is invalid an invalid value, Please try again.");
			}
		}
	}

	public static int checkValidInput(){
		Scanner userIn = new Scanner(System.in);
		int returnVal = 0;
		try{
			returnVal = userIn.nextInt();
		} catch(InputMismatchException e){
			returnVal = -1;
		}
		return returnVal;
	}
}
