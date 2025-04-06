import java.util.Iterator;
import java.util.Stack;

public class SplayTree<T extends Comparable<T>> implements Iterable<T> {

    public class Node {
        T data;
        Node left, right;

        public Node(T data) {
            this.data = data;
        }
    }

    private Node root;
    private int nodeCount;

    public int size() {
        return nodeCount;
    }

    public boolean isEmpty() {
        return nodeCount == 0;
    }

    public boolean contains(T value) {
        root = splay(root, value);
        return root != null && root.data.compareTo(value) == 0;
    }

    public boolean insert(T value) {
        if (value == null) {
            throw new IllegalArgumentException("Cannot insert null value");
        }
        if (root == null) {
            root = new Node(value);
            nodeCount++;
            return true;
        }

        root = splay(root, value);
        int cmp = value.compareTo(root.data);

        if (cmp == 0) {
            return false;
        }

        Node newNode = new Node(value);
        if (cmp < 0) {
            newNode.left = root.left;
            newNode.right = root;
            root.left = null;
        } else {
            newNode.right = root.right;
            newNode.left = root;
            root.right = null;
        }

        root = newNode;
        nodeCount++;
        return true;
    }

    public boolean remove(T value) {
        if (value == null) {
            throw new IllegalArgumentException("Cannot remove null value");
        }
        if (root == null) {
            return false;
        }

        root = splay(root, value);
        if (root.data.compareTo(value) != 0) {
            return false;
        }

        if (root.left == null) {
            root = root.right;
        } else {
            Node temp = root.right;
            root = root.left;
            root = splay(root, value);
            root.right = temp;
        }

        nodeCount--;
        return true;
    }

    private Node splay(Node node, T value) {
        if (node == null) {
            return null;
        }

        int compare = value.compareTo(node.data);
        if (compare < 0) {
            if (node.left == null) {
                return node;
            }

            int leftCompare = value.compareTo(node.left.data);
            if (leftCompare < 0) {
                node.left.left = splay(node.left.left, value);
                node = rotateRight(node);
            } else if (leftCompare > 0) {
                node.left.right = splay(node.left.right, value);
                if (node.left.right != null) {
                    node.left = rotateLeft(node.left);
                }
            }

            if (node.left == null) {
                return node;
            } else {
                return rotateRight(node);
            }

        } else if (compare > 0) {
            if (node.right == null) {
                return node;
            }

            int rightCompare = value.compareTo(node.right.data);
            if (rightCompare < 0) {
                node.right.left = splay(node.right.left, value);
                if (node.right.left != null) {
                    node.right = rotateRight(node.right);
                }
            } else if (rightCompare > 0) {
                node.right.right = splay(node.right.right, value);
                node = rotateLeft(node);
            }

            if (node.right == null) {
                return node;
            } else {
                return rotateLeft(node);
            }

        } else {
            return node;
        }
    }

    private Node rotateLeft(Node node) {
        Node x = node.right;
        node.right = x.left;
        x.left = node;
        return x;
    }

    private Node rotateRight(Node node) {
        Node x = node.left;
        node.left = x.right;
        x.right = node;
        return x;
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            private Stack<Node> stack = new Stack<>();
            private Node current = root;

            {
                while (current != null) {
                    stack.push(current);
                    current = current.left;
                }
            }

            @Override
            public boolean hasNext() {
                return !stack.isEmpty();
            }

            @Override
            public T next() {
                Node node = stack.pop();
                T result = node.data;
                if (node.right != null) {
                    Node temp = node.right;
                    while (temp != null) {
                        stack.push(temp);
                        temp = temp.left;
                    }
                }
                return result;
            }
        };
    }
}
