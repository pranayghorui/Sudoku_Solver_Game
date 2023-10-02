package sudoku_solver_game;

import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;

public class SudokuSolverGame {
	static Scanner sc= new Scanner(System.in);
	public void playGame() throws InputMismatchException {
		// test case 1 : for invalid solution
/*		int sudoku1[][]= {{0,0,8,0,0,0,0,0,0},{4,9,0,1,5,7,0,0,2},{0,0,3,0,0,4,1,9,0},
						  {7,8,5,0,6,0,0,2,0},{0,0,0,0,2,0,0,6,0},{9,6,0,4,0,5,3,0,0},
						  {0,3,0,0,7,2,0,0,4},{0,4,9,0,3,0,0,5,7},{8,2,7,0,0,9,0,1,3}};
		// test case 2 solution exists
		int sudoku2[][]= {{0,0,1,0,0,0,9,0,0},{0,6,7,0,8,0,0,0,3},{4,0,0,5,0,2,0,0,6},
						  {0,1,0,0,0,3,0,0,0},{0,0,0,8,5,7,0,0,0},{0,0,0,9,0,0,0,7,0},
						  {8,0,0,7,0,9,0,0,5},{5,0,0,0,1,0,3,6,0},{0,0,3,0,0,0,2,0,0}};
		// test case 3 solution exists
		int sudoku5[][]= {{0,7,0,0,2,0,0,1,0},{0,0,0,0,0,3,6,7,5},{0,0,4,0,5,0,8,0,0},
						  {6,0,0,1,0,0,0,0,0},{0,0,0,2,0,5,0,9,0},{9,5,0,0,4,0,0,0,0},
						  {7,0,1,0,0,2,0,5,4},{0,3,0,9,0,0,0,0,0},{0,0,0,0,8,0,7,0,1}};
		//test case 4 : for existing solution
		int sudoku3[][]= {{0,3,0,0,0,0,0,6,0},{0,0,7,8,0,0,2,0,0},{0,0,0,2,0,5,1,0,0},
						  {0,0,0,0,0,4,0,0,3},{9,0,0,1,0,0,0,0,2},{4,0,0,3,0,6,0,0,0},
						  {0,0,2,9,1,8,0,0,0},{0,0,6,0,0,2,9,0,0},{0,5,0,0,0,0,0,0,0}};
*/		// user input
		int[][] sudoku=new int [9][9];
		{
			System.out.println("1. Place blank position with 0 and the numbers in the right positions\n2. Use space between each digit"
					+ "\n3. After each row press Enter");
			System.out.println("-------------------------");
			System.out.println("Enter your sudoku problem");				
			for(int i=0;i<9;i++) {
				System.out.println("Enter row "+(i+1));
				int digit = sc.nextInt();
				if(digit>=0 && digit <=9) {			
					for(int j=0; j<9; j++) {
						sudoku[i][j]=sc.nextInt();
					}				
				}
				else {
					throw new InputMismatchException();
				}
			}
			for(int i=0; i<9;i++) {				
				System.out.println("Your submitted problem :\n"+ Arrays.toString(sudoku[i]));
			}
		}
		
		if(SudokuSolve(sudoku,0,0)) {
			printSudoku(sudoku );
		}
		else {
			System.out.println("Solution does not exist");
		}
	}
	
	public static boolean isSafe(int[][]sudoku, int row, int col, int digit) {
		// vertical / column
		for(int i=0; i<9; i++) {
			if(sudoku[i][col]==digit) {
				return false;
			}
		}
		// row / horizontal
		for(int j=0; j<9; j++) {
			if(sudoku[row][j]==digit) {
				return false;
			}
		}
		//every grid check == grid starting row: sr ; grid starting col: sc
		int sr= (row/3)*3;
		int sc= (col/3)*3;
		
		for(int i=sr; i<sr+3; i++) {
			for(int j=sc; j<sc+3; j++) {
				if(sudoku[i][j]==digit) {
					return false;
				}
			}
		}
		return true;
	}
	
	
	public static boolean SudokuSolve(int[][]sudoku, int row, int col) {
		// base case
		if(row==9) {
			return true;
		}	
		// recursion
		int nextRow=row;
		int nextCol=col+1;
		if(col==sudoku.length-1) { // col==8
			nextRow= row+1;
			nextCol=0;
		}
		// if that position filled default value by sudoku skip that go to next
		if(sudoku[row][col]!=0) {
			return SudokuSolve(sudoku,nextRow,nextCol);
		}
		
		for(int digit=1; digit<=9; digit++) {
			if(isSafe(sudoku, row, col, digit)) {
				sudoku[row][col]=digit;
				if(SudokuSolve(sudoku, nextRow,nextCol)) {	// if this return true--> soln exists
					return true;
				}
				// if return false
				sudoku[row][col]=0;	
			}
		}
		return false;
	}
	
	
	public static void printSudoku(int[][]sudoku) {
		for(int row=0; row<sudoku.length; row++) {
			for(int col=0; col<sudoku[row].length; col++) {
				System.out.print(sudoku[row][col]+" ");
				if(col==2 || col==5) {
					System.out.print("|");
				}
			}
			System.out.println();
			if(row==2 || row==5) {
				System.out.println("-------------------");
			}
		}
	}
	
	public static void main(String[]args) {
		SudokuSolverGame game = new SudokuSolverGame();
		String input="";
		int ans = 0;
		do{
			System.out.println("Want to solve your sudoku : press 'Y' \nTo exit : press 'Q'");
			input=sc.nextLine();
			if(input.equalsIgnoreCase("y")) {
				try {
					game.playGame();
				} catch (Exception e) {
					System.out.println("Wrong input. Input should be from 0 to 9"
							+ "\n--Restart game again--");
				}
			}
			else if(input.equalsIgnoreCase("q")) {
				System.out.println("Visit Again");
			}
			else {
				System.out.println("Inavild Choice");
			}
		}while(!(input.equalsIgnoreCase("q")) && !(input.equalsIgnoreCase("y")) && ans!=-1 );
	}
}