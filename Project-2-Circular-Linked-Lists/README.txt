# Circular Linked List – Josephus Problem Simulation

This project implements a **generic circular linked list** in Java and uses it to solve a variation of the **Josephus problem**.

The Josephus problem is a classic algorithmic puzzle:  
A group of soldiers stand in a circle. Starting at a specific point, you count off soldiers until you reach a certain number `k`. The `k`th soldier is removed from the circle, and counting resumes from the next soldier. This continues until only a certain number of soldiers remain.

## Features
- **Generic Circular Linked List** – Can store any type of object, not just integers.
- **Custom Add & Remove** – Supports inserting and removing nodes from any position (front, middle, end).
- **Iterator Support** – Allows circular iteration over the list using Java’s `Iterator` interface.
- **String Representation** – Prints the list in the format: `item1 ==> item2 ==> item3 ...`
- **Josephus Problem Simulation** – User specifies:
  - Number of soldiers (`n`)
  - Count number (`k`)
  - Program simulates eliminations until two soldiers remain.

## How It Works
- The list is **circular** – the last node points back to the head node, making it easy to loop continuously.
- The `ListIterator` class:
  - Keeps track of the current node.
  - Wraps around to the head when reaching the tail.
  - Supports removal of the last returned node.
- Josephus simulation steps:
  1. Populate the list with soldier numbers `1` to `n`.
  2. Use the iterator to count `(k-1)` soldiers.
  3. Remove the `k`th soldier.
  4. Continue until only two soldiers remain.

## Example Run
```
Enter Number of Soldiers
7
Enter Number of Counts
3
Soldier 3 was eliminated.
1 ==> 2 ==> 4 ==> 5 ==> 6 ==> 7
Soldier 6 was eliminated.
1 ==> 2 ==> 4 ==> 5 ==> 7
Soldier 2 was eliminated.
1 ==> 4 ==> 5 ==> 7
Soldier 7 was eliminated.
1 ==> 4 ==> 5
Soldier 5 was eliminated.
1 ==> 4
The soldiers that survived: 1 ==> 4
```

## Requirements
- Java 8 or newer

## How to Compile & Run
1. Save the file as `CircularLinkedList.java` inside a folder named `circularlinkedlist`.
2. Compile:
   ```bash
   javac circularlinkedlist/CircularLinkedList.java
   ```
3. Run:
   ```bash
   java circularlinkedlist.CircularLinkedList
   ```
