import java.util.Iterator;
import java.util.Stack;

public class AVLTree<T extends Comparable<T>> implements Iterable<T> {

    public class Node {
        T data;
        Node left, right;
        public int bf, height;

        public Node(T data) {
            this.data = data;
            this.left = null;
            this.right = null;
            this.bf = 0;
            this.height = 0;
        }
    }

    private Node root;
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

    public boolean isEmpty() {
        return nodeCount == 0;
    }

    public boolean contains(T value) {
        return contains(root, value);
    }

    private boolean contains(Node node, T value) {
        if (node == null) {
            return false;
        }

        int cmp = value.compareTo(node.data);

        if (cmp < 0) {
            return contains(node.left, value);
        } else if (cmp > 0) {
            return contains(node.right, value);
        } else {
            return true;
        }
    }

    public boolean insert(T value) {
        if (value == null) {
            throw new IllegalArgumentException("Cannot insert null value");
        }

        if (!contains(value)) {
            root = insert(root, value);
            nodeCount++;
            return true;
        } else {
            return false;
        }
    }

    private Node insert(Node node, T value) {
        if (node == null) {
            return new Node(value);
        }

        int cmp = value.compareTo(node.data);

        if (cmp < 0) {
            node.left = insert(node.left, value);
        } else {
            node.right = insert(node.right, value);
        }

        update(node);
        return balance(node);
    }

    private void update(Node node) {
        int leftNodeHeight;
        if (node.left == null) {
            leftNodeHeight = -1;
        } else {
            leftNodeHeight = node.left.height;
        }

        int rightNodeHeight;
        if (node.right == null) {
            rightNodeHeight = -1;
        } else {
            rightNodeHeight = node.right.height;
        }

        node.height = 1 + Math.max(leftNodeHeight, rightNodeHeight);
        node.bf = rightNodeHeight - leftNodeHeight;
    }

    private Node balance(Node node) {
        if (node.bf == -2) {
            if (node.left.bf <= 0) {
                return leftLeftCase(node);
            } else {
                return leftRightCase(node);
            }
        } else if (node.bf == 2) {
            if (node.right.bf >= 0) {
                return rightRightCase(node);
            } else {
                return rightLeftCase(node);
            }
        }
        return node;
    }

    private Node leftLeftCase(Node node) {
        return rightRotation(node);
    }

    private Node leftRightCase(Node node) {
        node.left = leftRotation(node.left);
        return leftLeftCase(node);
    }

    private Node rightRightCase(Node node) {
        return leftRotation(node);
    }

    private Node rightLeftCase(Node node) {
        node.right = rightRotation(node.right);
        return rightRightCase(node);
    }

    private Node leftRotation(Node node) {
        Node newParent = node.right;
        node.right = newParent.left;
        newParent.left = node;
        update(node);
        update(newParent);
        return newParent;
    }

    private Node rightRotation(Node node) {
        Node newParent = node.left;
        node.left = newParent.right;
        newParent.right = node;
        update(node);
        update(newParent);
        return newParent;
    }

    public boolean remove(T element) {
        if (element == null) {
            throw new IllegalArgumentException("Cannot remove null value");
        }
        if (contains(root, element)) {
            root = remove(root, element);
            nodeCount--;
            return true;
        }
        return false;
    }

    private Node remove(Node node, T element) {
        if (node == null) {
            return null;
        }
        int cmp = element.compareTo(node.data);
        if (cmp < 0) {
            node.left = remove(node.left, element);
        } else if (cmp > 0) {
            node.right = remove(node.right, element);
        } else {
            if (node.left == null) {
                return node.right;
            } else if (node.right == null) {
                return node.left;
            } else {
                if (node.left.height > node.right.height) {
                    T successorValue = findMax(node.left);
                    node.data = successorValue;
                    node.left = remove(node.left, successorValue);
                } else {
                    T successorValue = findMin(node.right);
                    node.data = successorValue;
                    node.right = remove(node.right, successorValue);
                }
            }
        }
        update(node);
        return balance(node);
    }

    private T findMin(Node node) {
        while (node.left != null) {
            node = node.left;
        }
        return node.data;
    }

    private T findMax(Node node) {
        while (node.right != null) {
            node = node.right;
        }
        return node.data;
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
