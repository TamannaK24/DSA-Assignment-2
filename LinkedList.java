import java.util.Iterator;
import java.util.EmptyStackException;

// doubly linked list that allows adding, removal, iteration
public class LinkedList<T> implements Iterable<T> {
    // public internal class for Node of LinkedList where each node stores data
    // seperated because it refers to the individual node and its characteristics
    public class Node<T> {
        T data;
        Node<T> prev;
        Node<T> next;

        public Node(T data) {
            this.data = data;
            this.prev = null;
            this.next = null;
        }
    }

    // refers in overall context of linked list, not individual node
    private Node<T> head;
    private Node<T> tail;
    private int size;

    // initializing head and tail to null and size to 0
    public LinkedList() {
        this.head = null;
        this.tail = null;
        size = 0;
    }

    // adds new element to the front of the list
    public void add(T data) {
        // creates new node of generic type using node class
        Node<T> newNode = new Node<>(data);
        // checks if list empty
        if (head == null) {
            head = newNode;
            tail = newNode;
        }
        // if not empty, adds new node to the front of the list
        else {
            newNode.next = head;
            head.prev = newNode;
            head = newNode;
        }
        size++;
    }

    // removes element from the front of the list
    public T remove() {
        // checks if list empty
        if (head == null) {
            throw new java.util.EmptyStackException();
        }
        // checks if only one node, if so it removes it
        T data = head.data;
        if (head == tail) {
            head = null;
            tail = null;
        }
        // if more than one node, removes the first node
        else {
            head = head.next;
            if (head != null) {
                head.prev = null;
            }
        }
        size--;
        return data;
    }


    public boolean remove(T data) {
        Node<T> current = head;

        while (current != null) {
            //if the data is foudn and it is the only node, remove it leaving the linkedlist empty
            if (current.data.equals(data)) {
                if (current == head && current == tail) {
                    head = null; 
                    tail = null; 
                }
                //else if the data is at the head, remove it 
                else if (current == head) {
                    head = head.next;
                    head.prev = null; 
                }
                //If it's the tail, remove it completely 
                else if (current == tail) {
                    tail = tail.prev; 
                    tail.next = null; 
                }
                //if the data is in the middl eof the list, remove it 
                else {
                    current.prev.next = current.next;
                    current.next.prev = current.prev; 
                }

                size--; 
                return true; 
            }
            //if current note has none of those scenerios, move to next node and repeat until the node you are at is null 
            current = current.next; 
        }
        //if data is not found in list 
        return false; 
    }

    // returns the first element of the list
    public T peek() {
        if (head == null) {
            throw new java.util.EmptyStackException(); 
        }
        return head.data;
    }

    // returns list size
    public int getSize() {
        return size;
    }

    // returns the last node of the list
    public Node<T> getTail() {
        // checks if empty list
        if (tail == null) {
            return null;
        }
        return tail;
    }

    // checks if list is empty
    public boolean isEmpty() {
        return head == null;
    }

    // iterator for the list, overrides from Iterable interface
    // returns an iterator object which is an anoymous internal class
    // hasNext() checks if there is a next element
    // next() returns the next element
    // throws exception if no more elements
    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            private Node<T> current = head;
            private Node<T> previous = null;

            @Override
            public boolean hasNext() {
                return current != null;
            }

            @Override
            public T next() {
                if (!hasNext()) {
                    throw new IllegalArgumentException("No more elements in the list");
                }
                previous = current;
                T data = current.data;
                current = current.next;
                if (current != null) {
                    current.prev = previous;
                }
                return data;
            }
        };
    }
}