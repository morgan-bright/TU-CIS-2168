# Maze Grid Panel – Maze Generation and Solving in Java Swing

This project implements a simple maze generator and solver in Java using Swing.  
It displays the maze in a grid of `Cell` objects and can solve it using a stack-based depth-first search (DFS) algorithm.

---

## Features

- **Maze Rendering** – Each `Cell` has four possible walls (north, south, east, west) and draws them accordingly.
- **Random Maze Generation** – Carves passages between cells using a basic randomized algorithm.
- **Maze Solving** – Uses a stack and depth-first search to find a path from the start to the finish.
- **Color Coding**:
  - **Green** – Start cell
  - **Red** – Finish cell
  - **Magenta** – Current path
  - **Green / Cyan / Blue** – Dead-end backtracking colors

---

## Classes

### `Cell`
- Extends `JPanel` to draw individual maze cells.
- Stores wall information (`northWall`, `southWall`, `eastWall`, `westWall`) and coordinates (`row`, `col`).
- Overrides `paintComponent` to draw walls.

### `MazeGridPanel`
- Extends `JPanel` and holds a 2D array of `Cell` objects.
- Generates the maze using a randomized wall-removal algorithm.
- Solves the maze using DFS and a stack to keep track of the path.

---

## Example Maze Solving Logic

1. Push the starting cell `(0,0)` onto the stack.
2. Check each direction (north, south, east, west) for an unvisited cell without a wall between.
3. Move to that cell, mark it as visited, and push it onto the stack.
4. If no unvisited neighbors exist, backtrack by popping the stack.
5. Stop when the finish cell `(rows-1, cols-1)` is reached.

---

## Requirements

- Java 8 or newer
- Swing (included with standard Java)

---

## How to Compile & Run

1. Save `Cell.java` and `MazeGridPanel.java` in the same directory.
2. Compile: javac Cell.java MazeGridPanel.java
3. Run using your own main class or a simple Swing frame:
```java
import javax.swing.*;

public class MazeApp {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Maze");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new MazeGridPanel(10, 10)); // 10x10 maze
        frame.pack();
        frame.setVisible(true);
    }
}
4. Compile and run:
  javac MazeApp.java
  java MazeApp

