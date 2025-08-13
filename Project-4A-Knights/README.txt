# Knight's Tour – Java Implementation

This program solves the **Knight’s Tour** problem on a standard 8×8 chessboard.  
It uses recursion, backtracking, and a move-ordering heuristic to find a path where a knight visits every square exactly once.

---

## What It Does

- Starts from a chosen square (default is `(0, 0)`).
- Tries possible knight moves in a ranked order (fewest onward moves first).
- Uses backtracking if it hits a dead end.
- Prints the completed board if a full tour is found.

---

## How It Works

- **rowMoves / colMoves** – Arrays for the 8 possible knight moves.
- **board** – 2D array tracking visited squares and the move number.
- **solve()** – Recursive method that explores moves until all squares are visited.
- **valid()** – Checks if a square is on the board and unvisited.
- **getOnward()** – Counts how many moves are possible from a square.
- **chooseBestMove()** – Ranks available moves so the algorithm explores the most promising ones first.

This ranking strategy is based on **Warnsdorff’s rule**, which usually reduces backtracking.

---

## Running the Program

You’ll need Java 8 or newer.

Compile and run from the terminal:

```bash
javac Knights.java
java Knights
