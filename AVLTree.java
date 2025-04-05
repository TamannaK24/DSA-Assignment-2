import java.util.Iterator;

public class AVLTree<T extends Comparable<T>> implements Iterable<T> {
    public class Node<T> {
        T data; 
        Node<T> left, right; 
        public int bf, height; 

        public Node(T data) {
            this.data = data; 
            this.left = null;
            this.right = null; 
            this.bf = 0; 
        }

        public Node root; 
        private int nodeCount; 

        public int height() {
            if (root == null) {
                return 0; 
            } else {
                return root.height; 
            }
        }

        public int size() {
            return nodeCount; 
        }
    }
}