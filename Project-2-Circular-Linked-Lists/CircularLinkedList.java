package circularlinkedlist;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class CircularLinkedList<E> implements Iterable<E> {
    Node<E> head;
    Node<E> tail;
    int size;

    // Constructor
    public CircularLinkedList() {
        head = null;
        tail = null;
        size = 0;
    }

    public int size() {
        return size;
    }

    // Return Node<E> found at the specified index
    private Node<E> getNode(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index out of bounds.");
        }
        Node<E> curr = head;
        for (int i = 0; i < index; i++) {
            curr = curr.next;
        }
        return curr;
    }

    // Add a node to the end of the list
    public boolean add(E item) {
        this.add(size, item);
        return true;
    }

    // Add a node at a specific index
    public void add(int index, E item) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index out of bounds.");
        }
        Node<E> adding = new Node<>(item);

        // Adding to empty list
        if (size == 0) {
            head = adding;
            tail = adding;
            tail.next = head;
            size = 1;
        }
        // Adding to front
        else if (index == 0) {
            adding.next = head;
            head = adding;
            tail.next = head;
            size++;
        }
        // Adding to end
        else if (index == size) {
            Node<E> prevTail = tail;
            tail = adding;
            tail.next = head;
            prevTail.next = tail;
            size++;
        }
        // Adding in middle
        else {
            Node<E> before = getNode(index - 1);
            adding.next = before.next;
            before.next = adding;
            size++;
        }
    }

    // Remove a node
    public E remove(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index out of bounds");
        }
        E itemRemove = null;

        // Removing first element
        if (index == 0) {
            itemRemove = head.item;
            head = head.next;
            tail.next = head;
        }
        // Removing from middle
        else if (index > 0 && index < size - 1) {
            Node<E> before = getNode(index - 1);
            itemRemove = before.next.item;
            before.next = before.next.next;
        }
        // Removing last element
        else if (index == size - 1) {
            itemRemove = tail.item;
            Node<E> newTail = getNode(index - 1);
            newTail.next = head;
            tail = newTail;
        }
        size--;
        return itemRemove;
    }

    // Stringify the list
    public String toString() {
        if (size == 0) {
            return "Empty List";
        }
        StringBuilder result = new StringBuilder();
        Node<E> curr = head;
        int count = 0;
        while (count < size) {
            result.append(curr.item);
            curr = curr.next;
            count++;
            if (curr != head) {
                result.append(" ==> ");
            }
        }
        return result.toString();
    }

    public Iterator<E> iterator() {
        return new ListIterator();
    }

    // Iterator class
    private class ListIterator implements Iterator<E> {
        Node<E> nextItem;
        Node<E> prev;
        int index;

        @SuppressWarnings("unchecked")
        public ListIterator() {
            nextItem = (Node<E>) head;
            index = 0;
        }

        public boolean hasNext() {
            return nextItem != null;
        }

        public E next() {
            if (index < size - 1) {
                prev = nextItem;
                E currItem = nextItem.item;
                nextItem = nextItem.next;
                index++;
                return currItem;
            } else {
                prev = nextItem;
                nextItem = head;
                index = 0;
                return prev.item;
            }
        }

        public void remove() {
            if (head == null || prev == null) {
                return;
            }
            if (index == 0) {
                CircularLinkedList.this.remove(size - 1);
            } else {
                CircularLinkedList.this.remove(index - 1);
                index--;
            }
        }
    }

    // Node class
    private static class Node<E> {
        E item;
        Node<E> next;

        public Node(E item) {
            this.item = item;
        }
    }

    // Main method - Josephus problem simulation
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter Number of Soldiers");
        int n = scanner.nextInt();

        System.out.println("Enter Number of Counts");
        int k = scanner.nextInt();

        CircularLinkedList<Integer> soldiers = new CircularLinkedList<>();
        for (int i = 1; i <= n; i++) {
            soldiers.add(i);
        }

        Iterator<Integer> soldierCircle = soldiers.iterator();
        while (soldiers.size() > 2) {
            for (int i = 0; i < (k - 1); i++) {
                if (!soldierCircle.hasNext()) {
                    soldierCircle = soldiers.iterator();
                }
                soldierCircle.next();
            }
            int eliminated = soldierCircle.next();
            System.out.println("Soldier " + eliminated + " was eliminated.");
            soldierCircle.remove();
            System.out.println(soldiers);
        }
        System.out.println("The soldiers that survived: " + soldiers);
    }
}
