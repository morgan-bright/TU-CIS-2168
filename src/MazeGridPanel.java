import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Stack;

public class MazeGridPanel extends JPanel {
	private int rows;
	private int cols;
	private Cell[][] maze;

	// Extra credit
	public void generateMazeByDFS() {
		boolean[][] visited;
		Stack<Cell> stack = new Stack<>();
		Cell start = maze[0][0];
		stack.push(start);




	}

	public void solveMaze() {
		Stack<Cell> stack = new Stack<>();
		Cell start = maze[0][0];
		start.setBackground(Color.GREEN);
		Cell finish = maze[rows - 1][cols - 1];
		finish.setBackground(Color.RED);
		stack.push(start);




		int colorInt = 0;

		// Implement your algorithm here

		while(!stack.empty()){ //continues until stack is empty

			Cell location = stack.peek(); //check current location
			if (location.getBackground() == Color.RED){ //if the background color of the current location is red, we've found the end
				break;
			}

			//checks north, south, east and west for a place we have not yet visited. If it finds one it pushes the location and changes the color to magenta
			if((!location.northWall) && !visited(location.row-1, location.col)){
				stack.push(maze[location.row-1][location.col]);
				location.setBackground(Color.MAGENTA);
			}else if(!location.southWall && !visited(location.row+1, location.col)){
				stack.push(maze[location.row+1][location.col]);
				location.setBackground(Color.MAGENTA);
			}else if(!location.eastWall && !visited(location.row, location.col+1)){
				stack.push(maze[location.row][location.col+1]);
				location.setBackground(Color.MAGENTA);
			}else if(!location.westWall && !visited(location.row, location.col-1)){
				stack.push(maze[location.row][location.col-1]);
				location.setBackground(Color.MAGENTA);
			}else{ //if we are at a dead end it is marked as a dead end and popped off the stack
				if (colorInt > 2){
					colorInt = 0;
				}

				if(colorInt == 0){
					location.setBackground(Color.GREEN);
				}else if(colorInt == 1){
					location.setBackground(Color.CYAN);
				}else if(colorInt == 2){
					location.setBackground(Color.BLUE);
				}
				colorInt++;
				stack.pop();
			}
		}
	}


	public boolean visited(int row, int col) {
		Cell c = maze[row][col];
		Color status = c.getBackground();
		if (status.equals(Color.WHITE) || status.equals(Color.RED)) {
			return false;
		}
		return true;
	}

	public void generateMaze() {
		for (int row = 0; row < rows; row++) {
			for (int col = 0; col < cols; col++) {

				if (row == 0 && col == 0) {
					continue;
				} else if (row == 0) {
					maze[row][col].westWall = false;
					maze[row][col - 1].eastWall = false;
				} else if (col == 0) {
					maze[row][col].northWall = false;
					maze[row - 1][col].southWall = false;
				} else {
					boolean north = Math.random() < 0.5;
					if (north) {
						maze[row][col].northWall = false;
						maze[row - 1][col].southWall = false;
					} else {
						maze[row][col].westWall = false;
						maze[row][col - 1].eastWall = false;
					}
					maze[row][col].repaint();
				}
			}
		}

		this.repaint();
	}

	public MazeGridPanel(int rows, int cols) {
		this.setPreferredSize(new Dimension(800, 800));
		this.rows = rows;
		this.cols = cols;
		this.setLayout(new GridLayout(rows, cols));
		this.maze = new Cell[rows][cols];
		for (int row = 0; row < rows; row++) {
			for (int col = 0; col < cols; col++) {
				maze[row][col] = new Cell(row, col);
				this.add(maze[row][col]);
			}
		}

		this.generateMaze();
		this.solveMaze();
	}
}
