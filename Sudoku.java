//package cs445.a3;

import java.util.List;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Sudoku {
    static boolean isFullSolution(int[][] board) {
		
		for (int row = 0; row < 9; row++)
		{
			for (int col = 0; col < 9; col++)
			{
				if (board[row][col] == 0)
				{
					return false;
				}
			}			
		}
		
		if (reject(board))
		{
			return false;
		}
		else
		{
			convert(board);
		}
		
        return true;
    }
	
	private static void convert(int[][] board)
	// Converts all filled cells to their positive values
	{
		for (int row = 0; row < 9; row++)
		{
			for (int col = 0; col < 9; col++)
			{
				if (board[row][col] < 0)
				{
					board[row][col] = Math.abs(board[row][col]);
				}
			}
		}
	}

    static boolean reject(int[][] board) {		
		for (int row = 0; row < board.length; row++)
		{
			for (int col = 0; col < board[row].length; col++)
			{	
				boolean rowCheck = checkRow(board, row, col);
				boolean colCheck = checkCol(board, row, col);
				boolean boxCheck = checkBox(board, row, col);
				if (rowCheck || colCheck || boxCheck)
				{
					return true;
				}
			}	
		}
        return false;
    }
	
	private static boolean checkRow(int[][] board, int row, int col)
	// Checks row
   {
	   int num = Math.abs(board[row][col]);
	   
	   if (num == 0)
	   {
		   return false;
	   }
	  
      for (int i = 0; i < 9; i++)
	  {
         if (i != col && Math.abs(board[row][i]) == num)
		 {
            return true; //DUPLICATE FOUND
		 }
	  }
	  return false;  
   }
   
    private static boolean checkCol(int[][] board, int row, int col)
	// Checks column
   {
		int num = Math.abs(board[row][col]);
		
	   if (num == 0)
	   {
		   return false;
	   }
	   
      for (int i = 0; i < 9; i++)
	  {
         if (i != row && Math.abs(board[i][col]) == num )
		 {			 
            return true; //DUPLICATE FOUND
		 }
	  }
      return false;
   }
   
    private static boolean checkBox(int[][] board, int row, int col)
	// Checks 3x3 box
   {
	   int num = Math.abs(board[row][col]);
	   
	   if(num == 0)
	   {
		   return false;
	   }
	   
      int topLeftRow = (row / 3) * 3;
      int topLeftCol = (col / 3) * 3;

      for (int r = 0; r < 3; r++)
	  {
         for (int c = 0; c < 3; c++)
		 {
			if ((topLeftRow + r) != row && (topLeftCol + c) != col && Math.abs(board[topLeftRow+r][topLeftCol+c]) == num)
			{
				return true; //DUPLICATE FOUND
			}
		 }
	  }
      return false;
   }
   
    static int[][] extend(int[][] board) {		
		boolean isSet = false;
		
		int[][] temp = new int[9][9];
		for (int row = 0; row < 9 ; row++)
		{
			for (int col = 0; col < 9; col++)
			{
				temp[row][col] = board[row][col];
				if (temp[row][col] == 0 && !isSet)
				{	
					isSet = true;
					temp[row][col] = -1;
				}
			}
		}
		
		if (isSet)
			return temp;
		else
			return null;
    }

    static int[][] next(int[][] board) {
		
		int[][] temp = new int[9][9];
		for (int row = 8; row >= 0; row--)
		{
			for (int col = 8; col >= 0; col--)
			{
				temp[row][col] = board[row][col];
				if (board[row][col] < 0)
				{
					temp[row][col] = board[row][col] - 1;
					if (temp[row][col] < -9)
					{
						return null;
					}
					finish(temp, board, row, col);
					return temp;
				}
			}
		}
		return null;
    }

	private static void finish (int[][] temp, int[][] board, int row, int col)
	//This method ensures that rest of board is copied to temp 
	{
		for (int x = row; x >= 0; x--)
		{
			for (int y = 8; y >= 0; y--)
			{
				if (temp[x][y] == temp[row][col])
				{
					continue;
				}
				else 
				{
					temp[x][y] = board[x][y];
				}
			}
		}
	}
	
    static void testIsFullSolution() {
		
		int[][] fullSol = new int[][] { //SOLUTION IS COMPLETE AND CORRECT
            {7, 2, 3, 6, 1, 9, 4, 5, 8},
            {1, 9, 8, 4, 5, 3, 2, 7, 6}, 
            {6, 4, 5, 8, 2, 7, 1, 9, 3},
            {9, 5, 4, 3, 8, 2, 7, 6, 1},
            {3, 1, 6, 7, 9, 4, 5, 8, 2},
            {8, 7, 2, 5, 6, 1, 9, 3, 4},
            {5, 8, 1, 2, 7, 6, 3, 4, 9},
            {2, 3, 7, 9, 4, 8, 6, 1, 5},
			{4, 6, 9, 1, 3, 5, 8, 2, 7},
        };
		
		int[][] notFullSol = new int[][] { //SOLUTION IS INCOMPLETE
            {7, 2, 3, 6, 1, 9, 4, 5, 8},
            {1, 9, 8, 4, 5, 3, 2, 7, 6}, 
            {6, 4, 5, 8, 2, 7, 1, 9, 3},
            {9, 5, 4, 3, 8, 2, 7, 6, 1},
            {3, 1, 6, 7, 9, 4, 5, 8, 2},
            {8, 7, 2, 5, 6, 1, 9, 3, 4},
            {5, 8, 1, 2, 7, 6, 3, 4, 9},
            {2, 3, 7, 9, 4, 8, 6, 1, 5},
			{4, 6, 9, 1, 3, 5, 8, 2, 0}, // Empty cell(0) in row
        };
		
		System.err.println("\nThis should evaluate to FULL:\n");
		printBoard(fullSol);
		System.err.println("\nSol'n above is: ");
		if (isFullSolution(fullSol))
		{
			System.err.println("FULL\n");
		}
		else 
		{
			System.err.println("NOT FULL\n"); //SHOULD NOT OCCUR
		}	
		
		System.err.println("This should evaluate to NOT FULL (Empty cell at board[8][8]):\n");
		printBoard(notFullSol);
		System.err.println("\nSol'n above is: ");
        if (isFullSolution(notFullSol)) 
		{
            System.err.println("FULL\n"); //SHOULD NOT OCCUR
        }
		else
		{
			System.err.println("NOT FULL\n");
		}	
    }    

    static void testReject() {
		// TODO: Complete this method
		
		int[][] rejected = new int[][] { //SOLUTION IS INCORRECT
            {7, 2, 3, 6, 1, 9, 4, 5, 8},
            {1, 9, 8, 4, 5, 3, 2, 7, 6},
            {6, 4, 5, 8, 2, 7, 1, 9, 3},
            {9, 5, 4, 3, 8, 2, 7, 6, 1},
            {3, 1, 6, 7, 9, 4, 5, 8, 1}, // Extra '1' at board[4][8]
            {0, 0, 2, 0, 6, 1, 0, 3, 4},
            {0, 8, 1, 0, 0, 0, 0, 4, 0},
            {0, 3, 0, 0, 0, 0, 6, 0, 5},
			{0, 0, 9, 0, 3, 0, 8, 0, 7},
		};
		
		int[][] notRejected = new int[][] { //SOLUTION IS COMPLETE AND CORRECT
            {7, 2, 3, 6, 1, 9, 4, 5, 8},
            {1, 9, 8, 4, 5, 3, 2, 7, 6},
            {6, 4, 5, 8, 2, 7, 1, 9, 3},
            {9, 5, 4, 3, 8, 2, 7, 6, 1},
            {3, 1, 6, 7, 9, 4, 5, 8, 2},
			{8, 7, 2, 5, 6, 1, 9, 3, 4},
            {5, 8, 1, 2, 7, 6, 3, 4, 9},
            {2, 3, 7, 9, 4, 8, 6, 1, 5},
			{4, 6, 9, 1, 3, 5, 8, 2, 7},
        };
		
		System.err.println("This should be rejected (extra '1' at board[4][8]:\n");
		printBoard(rejected);
		System.err.println("\nSol'n above is: ");
		if (reject(rejected))
		{
            System.err.println("REJECTED\n");
        }
		else 
		{
			System.err.println("NOT REJECTED\n"); //SHOULD NOT OCCUR
		}	
		
		System.err.println("This should be NOT be rejected:\n");
		printBoard(notRejected);
		System.err.println("\nSol'n above is: ");
        if (reject(notRejected)) 
		{
            System.err.println("REJECTED\n");
        }
		else
		{
			System.err.println("NOT REJECTED\n"); //SHOULD NOT OCCUR
		}	
    }

    static void testExtend() {
        // TODO: Complete this method
		
		int[][] extendable = new int[][] { //SOLUTION IS INCOMPLETE & EXTENDABLE
            {-1, 2, 3, 6, 0, 9, 4, 0, 8}, // Empty cell at board[0][4] should be set to '-1' in this row
            {1, 0, 0, 4, 0, 3, 0, 7, 0},
            {0, 4, 0, 0, 2, 0, 0, 0, 3},
            {9, 0, 0, 0, 0, 2, 7, 0, 0},
            {3, 0, 6, 0, 9, 0, 0, 8, 0},
            {0, 0, 2, 0, 6, 1, 0, 3, 4},
            {0, 8, 1, 0, 0, 0, 0, 4, 0},
            {0, 3, 0, 0, 0, 0, 6, 0, 5},
			{0, 0, 9, 0, 3, 0, 8, 0, 7},
        };
		

		int[][] notExtendable = new int[][] { //SOLUTION IS COMPLETE, THEREFORE NOT EXTENDABLE
            {7, 2, 3, 6, 1, 9, 4, 5, 8},
            {1, 9, 8, 4, 5, 3, 2, 7, 6},
            {6, 4, 5, 8, 2, 7, 1, 9, 3},
            {9, 5, 4, 3, 8, 2, 7, 6, 1},
            {3, 1, 6, 7, 9, 4, 5, 8, 2},
            {8, 7, 2, 5, 6, 1, 9, 3, 4},
            {5, 8, 1, 2, 7, 6, 3, 4, 9},
            {2, 3, 7, 9, 4, 8, 6, 1, 5},
			{4, 6, 9, 1, 3, 5, 8, 2, 7},
        };
		
		
        System.err.println("\nThis can be extended (empty cell at board[0][3] should be set to '-1'):\n");
			printBoard(extendable);
		System.err.println("\n--------- To ---------\n");
			printBoard(extend(extendable));
        
		System.err.println("\nThis can NOT be extended:\n");
			printBoard(notExtendable);
		System.err.print("\nAbove sol'n should output 'No assignment': ");
            printBoard(extend(notExtendable));
	}
	
    static void testNext() {
        // TODO: Complete this method
		
		int[][] nextable = new int[][] { //SOLUTION CAN BE NEXT'D
            {7, -1, 8, 6, 1, 9, 4, 5, 8}, 
            {1, -1, 0, 4, 0, 3, 0, 7, 0}, // Most recent entry (at board[1][1]) should be set to '-2' in this row
            {0, 4, 0, 0, 2, 0, 0, 0, 3},
            {9, 0, 0, 0, 0, 2, 7, 0, 0},
            {3, 0, 6, 0, 9, 0, 0, 8, 0},
            {0, 0, 2, 0, 6, 1, 0, 3, 4},
            {0, 8, 1, 0, 0, 0, 0, 4, 0},
            {0, 3, 0, 0, 0, 0, 6, 0, 5},
			{0, 0, 9, 0, 3, 0, 8, 0, 7},
        };
		
		int[][] notNextable1 = new int[][] { //SOLUTION CANNOT BE NEXT'D
            {-7, 2, 3, -6, -1, 9, 4, -5, 8},
            {1, -9, -8, 4, -5, 3, -2, 7, -6},
            {-5, 4, -9, 0, 0, 0, 0, 0, 3}, // Most recent entry is -9, board next'd to null 
            {9, 0, 0, 0, 0, 2, 7, 0, 0},
            {3, 0, 6, 0, 9, 0, 0, 8, 0},
            {0, 0, 2, 0, 6, 1, 0, 3, 4},
            {0, 8, 1, 0, 0, 0, 0, 4, 0},
            {0, 3, 0, 0, 0, 0, 6, 0, 5},
			{0, 0, 9, 0, 3, 0, 8, 0, 7},
        };
		
		int[][] notNextable2 = new int[][] { //SOLUTION IS COMPLETE, CANNOT BE NEXT'D
            {7, 2, 3, 6, 1, 9, 4, 5, 8},
            {1, 9, 8, 4, 5, 3, 2, 7, 6},
            {6, 4, 5, 8, 2, 7, 1, 9, 3},
            {9, 5, 4, 3, 8, 2, 7, 6, 1},
            {3, 1, 6, 7, 9, 4, 5, 8, 2},
            {8, 7, 2, 5, 6, 1, 9, 3, 4},
            {5, 8, 1, 2, 7, 6, 3, 4, 9},
            {2, 3, 7, 9, 4, 8, 6, 1, 5}, 
			{4, 6, 9, 1, 3, 5, 8, 2, 7},
		};
		
		System.err.println("\nThis can be next'd (most recent entry @ board[1][1] should be set to '-2':\n");
			printBoard(nextable);
		System.err.println("\n--------- To ---------\n");
			printBoard(next(nextable));
        
		System.err.println("\nThis can NOT be next'd:\n");
            printBoard(notNextable1);
		System.err.print("\nAbove sol'n should output 'No assignment': ");
            printBoard(next(notNextable1));
			
		System.err.println("\nThis also can NOT be next'd:\n");
            printBoard(notNextable2);
		System.err.print("\nAbove sol'n should output 'No assignment': ");
            printBoard(next(notNextable2));
    }

    static void printBoard(int[][] board) {
        if (board == null) {
            System.out.println("No assignment");
            return;
        }
        for (int i = 0; i < 9; i++) {
            if (i == 3 || i == 6) {
                System.out.println("----+-----+----");
            }
            for (int j = 0; j < 9; j++) {
                if (j == 2 || j == 5) {
                    System.out.print(board[i][j] + " | ");
                } else {
                    System.out.print(board[i][j]);
                }
            }
            System.out.print("\n");
        }
    }

    static int[][] readBoard(String filename) {
        List<String> lines = null;
        try {
            lines = Files.readAllLines(Paths.get(filename), Charset.defaultCharset());
        } catch (IOException e) {
            return null;
        }
        int[][] board = new int[9][9];
        int val = 0;
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                try {
                    val = Integer.parseInt(Character.toString(lines.get(i).charAt(j)));
                } catch (Exception e) {
                    val = 0;
                }
                board[i][j] = val;
            }
        }
        return board;
    }

    static int[][] solve(int[][] board) {
        if (reject(board)) return null;
        if (isFullSolution(board)) return board;
        int[][] attempt = extend(board);
		/*System.out.println("**********************");
		printBoard(attempt);
		System.out.println("**********************");*/
        while (attempt != null) {
            int[][] solution = solve(attempt);
            if (solution != null) return solution;
            attempt = next(attempt);	
			/*System.out.println("======================");
			printBoard(attempt);
			System.out.println("======================");*/
		}
        return null;
    }

    public static void main(String[] args) {
        if (args[0].equals("-t")) {
            testIsFullSolution();
            testReject();
            testExtend();
            testNext();
        } else {
            int[][] board = readBoard(args[0]);
            printBoard(board);
            System.out.println("Solution:");
            printBoard(solve(board));
        }
    }
}

