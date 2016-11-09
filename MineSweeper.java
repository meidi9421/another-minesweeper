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


	// TEST IMPLEMENTATION ONLY
	public static boolean revealGridCell(int row, int col, int[][] grid, int[][] gridUncovered){
		//Open Spaces and create the appropriate labelling 
		if(grid[row][col] == 0){
			grid[row][col] = 1;

		}

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
				if(grid[i][z] == 0 && revealGridCell[i][z] == 0) {
					System.out.print( ". ");
				} else if(grid[i][z] == 0 && revealGridCell[i][z] == 1) {
					System.out.print("  ");
				} else if((grid[i][z] == 0 && revealGridCell[i][z] > 1){
					System.our.print((revealGridCell[i][z] - 1) + " ");
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
			Scanner userIn = new Scanner(System.in);
			System.out.print("Select a cell. Row value (a digit between 0 and 7): ");
			int rowVal = userIn.nextInt();
			System.out.print("Select a cell. Column value (a digit between 0 and 7): ");
			int colVal = userIn.nextInt();
			if(checkDomainRange(rowVal) && checkDomainRange(colVal)){
				ioCoords[0] = rowVal;
				ioCoords[1] = colVal;
				properInput = true;
			}
		}


	}
}
