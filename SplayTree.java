import java.util.Iterator;
import java.util.Stack;

public class SplayTree<T extends Comparable<T>> implements Iterable<T> {

    //properties of the node itself, created in inner class, can access outer variables
    public class Node {
        T data;
        Node left, right;

        public Node(T data) {
            this.data = data;
        }
    }

    //properties of the tree itself, root and node count
    private Node root;
    private int nodeCount;

    //returns number of nodes in the tree
    public int size() {
        return nodeCount;
    }

    //returns true if the tree is empty and false otherwise
    public boolean isEmpty() {
        return nodeCount == 0;
    }


    public boolean contains(T value) {
        //rotates as searching, if value found it is new root and if value not found last accessed node near it is root
        root = splay(root, value);
        //if value is found and it is the root then return true, otherwise false
        return root != null && root.data.compareTo(value) == 0;
    }

    public boolean insert(T value) {
        //check if value is null, if so throw exception
        if (value == null) {
            throw new IllegalArgumentException("Cannot insert null value");
        }

        //if tree is empty, create a new node and set it as root
        if (root == null) {
            root = new Node(value);
            nodeCount++;
            return true;
        }

        //searches for value in tree and rotates the tree to bring the value to the root
        root = splay(root, value);
        //ccomparing value to the root node's data 
        int cmp = value.compareTo(root.data);

        //if value is already present in the tree, do not insert it again
        if (cmp == 0) {
            return false;
        }

        //create a new node with value to be inserted
        Node newNode = new Node(value);
        //if value is smaller, go left
        if (cmp < 0) {
            newNode.left = root.left;
            newNode.right = root;
            root.left = null;
        } 
        //if value is larger, go right
        else {
            newNode.right = root.right;
            newNode.left = root;
            root.right = null;
        }

        //set the new node as the root of the tree
        root = newNode;
        nodeCount++;
        return true;
    }

    public boolean remove(T value) {
        //check if value is null, if so throw exception
        if (value == null) {
            throw new IllegalArgumentException("Cannot remove null value");
        }
        //if tree is empty, nothing to remove
        if (root == null) {
            return false;
        }

        //searches for value in tree and rotates the tree to bring the value to the root
        root = splay(root, value);

        //if value is not found, return false
        if (root.data.compareTo(value) != 0) {
            return false;
        }

        //if value is found, it is at root and we need to remove it
        //if there is no left subtree, delete root, set the root to right subtree 
        if (root.left == null) {
            root = root.right;
        } 
        
        //if there is left subtree, save right subtree in temp
        //remove current node (deleting it), move left subtree up
        //splay to bring max node in left subtree to top
        //and attach right subtree to the new root
        else {
            Node temp = root.right;
            root = root.left;
            root = splay(root, value);
            root.right = temp;
        }

        //decrement the node count
        nodeCount--;
        return true;
    }

    private Node splay(Node node, T value) {
        //if node is null, return null
        if (node == null) {
            return null;
        }

        //comparing value to the node's data
        int compare = value.compareTo(node.data);

        //if value is less than node's data, go left
        if (compare < 0) {
            if (node.left == null) {
                return node;
            }

            //check if value is left or right of left child
            int leftCompare = value.compareTo(node.left.data);
            if (leftCompare < 0) {
                //zig zig rotation
                node.left.left = splay(node.left.left, value);
                node = rotateRight(node);
            } else if (leftCompare > 0) {
                //zig zag rotation 
                node.left.right = splay(node.left.right, value);
                if (node.left.right != null) {
                    node.left = rotateLeft(node.left);
                }
            }

            //if left child doesn't exist, return node
            if (node.left == null) {
                return node;
            } 
            //if left child exists, rotate right 
            else {
                return rotateRight(node);
            }

        //value is greater than node's data, go right
        } else if (compare > 0) {
            //if right child doesn't exist, return node
            if (node.right == null) {
                return node;
            }

            //check if value is left or right of right child
            int rightCompare = value.compareTo(node.right.data);
            //zig zag rotation
            if (rightCompare < 0) {
                node.right.left = splay(node.right.left, value);
                if (node.right.left != null) {
                    node.right = rotateRight(node.right);
                }
            } 
            //zig zig rotation
            else if (rightCompare > 0) {
                node.right.right = splay(node.right.right, value);
                node = rotateLeft(node);
            }

            //if right child doesn't exist, return node
            if (node.right == null) {
                return node;
            } 
            //if right child exists, rotate left
            else {
                return rotateLeft(node);
            }

        } 
        //if value is found and at root, return node
        else {
            return node;
        }
    }

    //fixing right heavy imbalence
    private Node rotateLeft(Node node) {
        Node x = node.right;
        node.right = x.left;
        x.left = node;
        return x;
    }

    //fixing left heavy imbalence
    private Node rotateRight(Node node) {
        Node x = node.left;
        node.left = x.right;
        x.right = node;
        return x;
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            //create a stack to hold nodes for inorder traversal
            private Stack<Node> stack = new Stack<>();
            //keeps track of current node in traversal 
            private Node current = root;

            //runs when iterator is created, goes to far left first and pushes each node to stack
            {
                while (current != null) {
                    stack.push(current);
                    current = current.left;
                }
            }

            //checks if there are more nodes to visit in the stack
            @Override
            public boolean hasNext() {
                return !stack.isEmpty();
            }

            @Override
            public T next() {
                //pops top node off stack, 
                Node node = stack.pop();
                T result = node.data;
                //if node has right child, go to leftmost and push all left children to stack
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
