import java.util.*;
import java.lang.Math.*;

class MineSweeper{

    /* This method initializes all of the important variables within this program.
	* (see comments within method). It then loops through all of the games states
	* within a while loop while the game isnt lost or won. Takes no input
	*/
	public static void main(String[] args) {
		//game states. when either of these are true the game is finished
		boolean gameOver = false, gameWon = false;
		//A 2D array that contains the bomb locations
		int[][] grid = new int[8][8];
		//A 2D array that contains the uncovered state of a cell and the amount of adjacent bombs
		// (0 isnt clear, 1 is clear, anything above 1 is the amount of bombs -1 (ex 4 = 3 bombs and cleared))
		int[][] gridUncovered  = new int[8][8];
		//A 1D array that contains some user entered co-ordinates
		int[] ioUser = new int[2];
		//Initializes the game grid
		initializeFullGrid(grid);
		//Draws the grid
		drawFullGrid(grid, gridUncovered, gameOver, gameWon);

		//Checks to see if the games finished yet.
		while(!gameOver && !gameWon){
			takeInput(ioUser);
			gameOver = revealGridCell(ioUser[0],ioUser[1],grid,gridUncovered);
			gameWon = checkGameWon(grid, gridUncovered);
			drawFullGrid(grid, gridUncovered, gameOver, gameWon);
		}
	}

    /* Initializes the grid with ten bombs at different places
	* on the map. If there is already a bomb at the specified
	* co-ordinate it tries again. It takes a 2D array as an
	* input called grid. Grid holds the position of the bombs on
	* the map.
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

    /* This method checks if all of the clearable cells are cleared.
	* If all cells are cleared it returns true. If else, it returns
	* false. It takes two 2D arrays as input. grid and gridUncovered.
	* It uses a for loop to check through all the cells in the grid.
	*/
	public static boolean checkGameWon(int[][] grid, int[][] gridUncovered){
		int runningTotal = 0;
		int reqWinTotal = 54; //8 x 8 elements. 10 bombs. 64 - 10 = 54 which means the total cells needed is 54
		for(int posY = 0; posY < 8; posY++){
			for(int posX = 0; posX < 8; posX++){
				if(grid[posX][posY] != -1 && gridUncovered[posX][posY] >= 1){
					runningTotal++;
				}
			}
		}

		if(runningTotal == reqWinTotal){
			System.out.println("Congrats! You Won!");
			return true;
		}else {
			return false;
		}
	}

    /* This method checks the cells surrounding the user specified cell to ensure
	* there are no bombs present. it takes in two ints: row, col. and two 2D arrays:
	* grid and gridUncovered. It uses the row and col ints to check the surrounding values.
	* A method called bombSurround is then called which adds up the number of surroudning bombs
	* this method does not need to return anything as the arrays edited are objects
	* and because of this dont need to be explicitly returned.
	*/
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

    /* This method is very similar to checkSurround as it uses the same for loop structure
	* but this time counts the surrounding bombs. For every bomb adjacent to the passed row, col
	* value within grid a number is added to gridUncovered. this method does not need to
	* return anything as the arrays edited are objects and because of this dont need to
	* be explicitly returned.
	*/
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

    /* this method calls checkSurround() which sets the appropriate values within gridUncovered
	* so that drawFullGrid() can properly draw the grid. Additionally this returns a boolean values
	* that determines if the game is over. If a user enters a cell address that contains a bomb it
	* returns true which sets gameOver to true in the main method thus ending the game. It takes in
	* the int values row and col and the 2D arrays grid and gridUncovered and passes them to checkSurround()
	*/
	public static boolean revealGridCell(int row, int col, int[][] grid, int[][] gridUncovered){
		checkSurround(row, col, grid, gridUncovered);
		if(grid[row][col] == -1){
			System.out.println("Kaboom! Game Over!");
			return true;
		} else {
			return false;
		}
	}

    /* This method draws the actual game grid. It uses a for loop to parse through all the values
     * and print there representative symbol. all values are printed as ". " intially because none
	* of the values are uncovered. When a value is uncovered if there are no adjacent bombs a "  "
	* is printed. If there are bombs adjacent to the parsed value it prints the number of bombs
	* (see checkSurround(), bombSurround(), and revealGridCell() for how this is set.) WHen the game
	* is finsihed win or lose the bombs are printed as "B ". This method doesnt need to return anyhting
	* as it simply prints the graph. It takes two 2D arrays as input: grid and gridUncovered. Theses are used to parse
	* through the values. It also takes two booleans: gameOver and gameWon. These values are used to determine if the
	* game is finished
	*/
	public static void drawFullGrid(int[][] grid, int[][] revealGridCell, boolean gameOver, boolean gameWon){
		System.out.println("  | 0 1 2 3 4 5 6 7");
		System.out.println("___________________");
		for(int i = 0; i < 8; i++){
			System.out.print(i + " | ");
			for(int z = 0; z < 8; z++){
				if( revealGridCell[i][z] == 0) {
					if(grid[i][z] == -1 && (gameOver || gameWon)) {
						System.out.print("B ");
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

    /* Asks the user for a row and a column to check for bombs
	* the method takes in an array ioCoords that is initialized
	* in the main and sets it so that it contains a valid co ordinate.
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

    /* Makes sure that the user entered value is a valid
	* integer value. It creates a Scanner object and uses a
	* try / catch test along with the Scanner method nextInt()
	* to check that the entered value is valid. If it is not valid
	* the method returns a -1. This ensures that it is caught as not valid
	* by the checkDomainRange() method.
	*/
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

	/* takes in a domain or range number and insures it is in
	 * the correct range. As far as this program is concerned there are
	 * no elements outside 0 - 7: if a number greater/less than that is passed
	 * the method returns false.
	 */
	 public static boolean checkDomainRange(int domRan){
		 if(domRan < 0 || domRan > 7){
			 return false;
		 }
		 else{
			 return true;
		 }
	 }
}
