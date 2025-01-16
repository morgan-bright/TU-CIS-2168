//Its this one
package circularlinkedlist;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Scanner;


public class CircularLinkedList<E> implements Iterable<E> {
    Node<E> head;
    Node<E> tail;
    int size;


    // Implement a constructor
    public CircularLinkedList() {
        head = null;
        tail = null;
        size = 0;
    }
    public int size() {
        return size;
    }

    // Return Node<E> found at the specified index
    // Be sure to check for out of bounds cases
    private Node<E> getNode(int index ) {
        Node<E> curr = head;

        //Case: Out of bounds
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index out of bounds.");
        }

        //iterate until we get to desired index
        for (int i = 0; i < index; i++) {
            //advances current node until we get to desired index
            curr = curr.next;
        }
        //returns desired node
        return curr;
    }

    // Add a node to the end of the list
    // HINT: Use the overloaded add method as a helper method
    public boolean add(E item) {
        this.add(size, item);   //overloaded add method
        return true;
    }


    // Cases to handle:
    //      Out of bounds
    //      Adding an element to an empty list
    //      Adding an element to the front
    //      Adding an element to the end
    //      Adding an element in the middle
    // HINT: Remember to keep track of the list's size
    public void add(int index, E item) {
        if(index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index out of bounds.");
        }

        Node<E> adding = new Node<>(item);

        //Case: adding element to empty list
        if (size == 0) {
            head = adding; //empty list, first node is both head and tail
            tail = adding;
            tail.next = head; //node points around to itself
            size = 1; //create size
        }

        //Case: adding element to front
        else if (index == 0) {
            adding.next = head;  // node "adding" points to head
            head = adding;     //memory location that was stored in adding, now stored in head
            tail.next = head; //tail points to head
            size ++;
        }

        //adding element to end
        else if (index == size) {
            Node<E> prevTail = tail;  //create new node prevTail. memory location that was stored in tail, now stored in prevTail
            tail.next = head;         // points tail to head
            tail = adding;            // memory location that was stored in adding, now stored in tail
            prevTail.next = tail;     // prevTail points to tail
            size ++;                  // head -> middle -> tail        head -> middle -> prevTail -> newTail
        }

        //adding element to te middle
        else{
            Node<E> before = getNode(index-1);  //grab a node at the index before location we want to place new node
            adding.next = before.next;   //set the adding pointer to same place as before pointer
            before.next = adding;        //set pointer of previous node to the location of the node we are adding
            size ++;
        }
    }



    // Cases to handle:
    //      Out of bounds
    //      Removing the last element in the list
    //      Removing the first element in the list
    //      Removing the last element
    //      Removing an element from the middle
    // HINT: Remember to keep track of the list's size
    public E remove(int index) {
        //out of bounds handler
        if (index<0 || index >= size){
            throw new IndexOutOfBoundsException("Index out of bounds");
        }
        //initialize varable holding item to remove
        E itemRemove = null;
        //removal from beginning
        if (index == 0) {
            itemRemove = head.item;
            head = head.next; //memory location stored in head.next now stored in head
            tail.next = head; //tail pointer to the new head
        }
        //removal from middle
        if (index > 0 && index < size-1) {   //if if we are not at the beginning and we have not at the last element
            Node<E> before = getNode(index-1);
            itemRemove = before.next.item;
            before.next = before.next.next;
            }
        //removal from end
        if(index == size-1) {
            itemRemove = tail.item;
            Node<E> newTail = getNode(index-1);
            newTail.next = head;
            tail = newTail;

        }
        size --;
        return itemRemove;
    }

    // Stringify your list
    // Cases to handle:
    //      Empty list
    //      Single element list
    //      Multi-element list
    // Use "==>" to delimit each node
    public String toString() {
        StringBuilder result = new StringBuilder();

        if (size == 0) {
            return "Empty List";
        }

        int count = 0;
        Node<E> curr = head;

        //appends result with each item and ==> between each item
        while(count < size){
            result.append(curr.item);
            curr = curr.next;
            count ++;

            if (curr != head) {
                result.append(" ==> ");
            }
        }
        return result.toString();
    }

    public Iterator<E> iterator() {
        return new ListIterator();
    }

    // This class is not static because it needs to reference its parent class
    private class ListIterator implements Iterator<E> {
        Node<E> nextItem;
        Node<E> prev;
        int index;

        @SuppressWarnings("unchecked")
        // Creates a new iterator that starts at the head of the list
        public ListIterator() {
            nextItem = (Node<E>) head;
            index = 0;
        }

        // Returns true if there is a next node
        public boolean hasNext() {
            return nextItem != null;
        }

        // Advances the iterator to the next item
        // Should wrap back around to the head
        public E next() {
            //if(!hasNext()){
           //     throw new NoSuchElementException("No more elements to iterate.");
           // }


            if(index < size -1){
                prev = nextItem;
                E currItem = nextItem.item; //current position is stored in prev
                nextItem = nextItem.next; //nextItem shifts one
                index++;
            }else{
                prev = nextItem;
                nextItem = head;
                index = 0;
            }

            return prev.item;

        }

        // Remove the last node visted by the .next() call
        // For example, if we had just created an iterator,
        // the following calls would remove the item at index 1 (the second person in the ring)
        // next() next() remove()
        // Use CircularLinkedList.this to reference the CircularLinkedList parent class
        public void remove() {
            if (head == null || prev ==null) {
                return;
            }

            if(index == 0) {
                CircularLinkedList.this.remove(size - 1);
            }else {
                CircularLinkedList.this.remove(index - 1);
                index--;
            }

    }}

    // The Node class
    private static class Node<E>{
        E item;
        Node<E> next;

        public Node(E item) {
            this.item = item;
        }

    }

    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);


        System.out.println("Enter Number of Soldiers");
        int n = scanner.nextInt();
        System.out.println("Enter Number of Counts");
        int k = scanner.nextInt();

        //initialize circular linked list
        CircularLinkedList<Integer> soldiers = new CircularLinkedList<>();

        //put n number of soldiers in the list
        for (int i = 1; i<=n; i++){
            soldiers.add(i);
        }
        //initialize iterator
        Iterator<Integer> soldierCircle = soldiers.iterator();


        while (soldiers.size() > 2) {
            //iterate till we get to soldier to kill
            for(int i = 0; i < (k-1); i++){
                if(!soldierCircle.hasNext()){       //if the iterator !hasNext, start from beginning of soldiers circular linked list
                    soldierCircle = soldiers.iterator();
                }
                soldierCircle.next(); //iterate to the next soldier
            }
            int eliminated = soldierCircle.next(); // saves soldier who is to be eliminated to variable
            System.out.println("Soldier " + eliminated + " was eliminated.");
            soldierCircle.remove();         //remove the current node
            System.out.println(soldiers);
        }
        System.out.println("The soldiers that survived: " + soldiers);

        }


    }
