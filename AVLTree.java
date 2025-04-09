import java.util.Iterator;
import java.util.Stack;

public class AVLTree<T extends Comparable<T>> implements Iterable<T> {
    
    //properties of the node itself, created in inner class, can access outer variables
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

    //returns height of the tree
    public int height() {
        if (root == null) {
            return 0;
        } else {
            return root.height;
        }
    }

    //returns number of nodes in the tree
    public int size() {
        return nodeCount;
    }

    //returns true if the tree is empty and false otherwise
    public boolean isEmpty() {
        return nodeCount == 0;
    }

    //
    public boolean contains(T value) {
        //inputs root along with value needing to be searched for so it starts searching recursively from the root to leaf 
        return contains(root, value);
    }

    //recursive function to search for the value in the tree
    private boolean contains(Node node, T value) {
        //base case: if a leaf is reached and value is still not found, it is not in the tree
        if (node == null) {
            return false;
        }
        
        //compare to function returns -1 if the value is less than the node's data, 0 if equal, and 1 if greater
        int cmp = value.compareTo(node.data);

        //if value is less than node's data, then search left subtree recursively 
        if (cmp < 0) {
            return contains(node.left, value);
        } 
        //if value is greater than node's data, then search right subtree recursively 
        else if (cmp > 0) {
            return contains(node.right, value);
        } 
        //if value is equal to node's data, then return true
        else {
            return true;
        }
    }

    public boolean insert(T value) {
        //check if the value is null, if so throw an exception
        if (value == null) {
            throw new IllegalArgumentException("Cannot insert null value");
        }
        //if tree does not contain the value, then insert it into tree and increase the size of the tree by 1
        if (!contains(value)) {
            root = insert(root, value);
            nodeCount++;
            return true;
        } 
        
        //if the value is already in the tree, return false 
        else {
            return false;
        }
    }

    private Node insert(Node node, T value) {
        //base case: if the node is null, then create a new node with the value to insert and return it 
        if (node == null) {
            return new Node(value);
        }

        //compare to function returns -1 if the value is less than the node's data, 0 if equal, and 1 if greater
        int cmp = value.compareTo(node.data);

        //if value is less than node's data, then insert it into the left subtree recursively
        if (cmp < 0) {
            node.left = insert(node.left, value);
        } 
        //if value is greater than node's data, then insert it into the right subtree recursively
        else {
            node.right = insert(node.right, value);
        }

        //output the recalculated height and balanced factor of the node
        update(node);
        return balance(node);
    }

    private void update(Node node) {
        //STEP 1: calculate the height of the left and right subtrees of the node
        int leftNodeHeight;
        //if left child doesn't exist, set height of left subtree as -1 
        if (node.left == null) {
            leftNodeHeight = -1;
        } 
        //if left child does exist, set height of left subtree as the height of that child 
        else {
            leftNodeHeight = node.left.height;
        }

        int rightNodeHeight;
        //if right child doesn't exist, set height of right subtree as -1 
        if (node.right == null) {
            rightNodeHeight = -1;
        } 
        //if right child does exist, set height of right subtree as the height of that child 
        else {
            rightNodeHeight = node.right.height;
        }

        //STEP 2: calculate the height and bf of the node 
        //set the height of the node as the max of the left and right subtree heights + 1
        node.height = 1 + Math.max(leftNodeHeight, rightNodeHeight);
        //set the balance factor of the node as the difference between the right and left subtree heights
        node.bf = rightNodeHeight - leftNodeHeight;
    }

    private Node balance(Node node) {
        //if node is left heavy
        if (node.bf == -2) {
            //imbalence is on the left (same) side, do single right rotation 
            if (node.left.bf <= 0) {
                return leftLeftCase(node);
            } 
            //imbalence left child is right heavy, do double rotations
            else {
                return leftRightCase(node);
            }
        } 
        //if node is right heavy 
        else if (node.bf == 2) {
            //imbalence is on the right (same) side, do single left rotation 
            if (node.right.bf >= 0) {
                return rightRightCase(node);
            } 
            //imbalence right child is left heavy, do double rotations
            else {
                return rightLeftCase(node);
            }
        }
        //if the node is balanced, return it as it is
        return node;
    }

    //perform a right rotation for leftleftcase
    private Node leftLeftCase(Node node) {
        return rightRotation(node);
    }

    //perform a left rotation and then a right rotation for leftrightcase
    private Node leftRightCase(Node node) {
        node.left = leftRotation(node.left);
        return leftLeftCase(node);
    }

    //perform a left rotation for rightleftcase
    private Node rightRightCase(Node node) {
        return leftRotation(node);
    }

    //perform a right rotation and then a left rotation for rightrightcase
    private Node rightLeftCase(Node node) {
        node.right = rightRotation(node.right);
        return rightRightCase(node);
    }

    /*
    tree is RR heavy 
       A 
        \
         B
          \ 
           C
     */

    private Node leftRotation(Node node) {
        //create new parent = B
        Node newParent = node.right;
        //B's left child is now A's right child 
        node.right = newParent.left;
        //Parent's (B) left child is now A
        newParent.left = node;

        //update the heights and balance factors 
        //return new root
        update(node);
        update(newParent);
        return newParent;
    }

    /*
    tree is LL heavy 
               C
              /
            B  
          /
        A
    */
    private Node rightRotation(Node node) {
        //create new parent = B
        Node newParent = node.left;
        //B's right child is now C's left child
        node.left = newParent.right;
        //B's right child is now C
        newParent.right = node;
        //update the heights and balance factors
        update(node);
        update(newParent);
        //return new root
        return newParent;
    }


    public boolean remove(T element) {
        //if the element is null, throw an exception
        if (element == null) {
            throw new IllegalArgumentException("Cannot remove null value");
        }
        //if the tree contains the element, remove it from the tree and decrease the size of the tree by 1
        if (contains(root, element)) {
            root = remove(root, element);
            nodeCount--;
            return true;
        }
        //if the element is not in the tree, return false
        return false;
    }

    private Node remove(Node node, T element) {
        //base case: if the node is null, return null
        if (node == null) {
            return null;
        }
        //compare to function returns -1 if the value is less than the node's data, 0 if equal, and 1 if greater
        int cmp = element.compareTo(node.data);
        //if the element is less than the node's data, search left subtree recursively
        if (cmp < 0) {
            node.left = remove(node.left, element);
        } 
        //if the element is greater than the node's data, search right subtree recursively
        else if (cmp > 0) {
            node.right = remove(node.right, element);
        } 
        //if the element is equal to the node's data, then remove the node
        else {
            //if node has only a right child, just replace it with the right child
            if (node.left == null) {
                return node.right;
            } 
            //if node has only a left child, just replace it with the left child
            else if (node.right == null) {
                return node.left;
            } 
            //if node has both children
            else {
                //if left subtree is taller, find max value in it
                if (node.left.height > node.right.height) {
                    T successorValue = findMax(node.left);
                    //assign current node data to successor's data
                    node.data = successorValue;
                    //remove the successor from the left subtree recursively
                    node.left = remove(node.left, successorValue);
                } 
                // if right subtree is taller use max value from it 
                else {
                    //find min value in the right subtree
                    T successorValue = findMin(node.right);
                    //assign current node data to successor's data
                    node.data = successorValue;
                    //remove the successor from the right subtree recursively
                    node.right = remove(node.right, successorValue);
                }
            }
        }
        //update the heights and balance factors of the node
        update(node);
        return balance(node);
    }

    //finds the minimum value in the tree by going to the leftmost node
    private T findMin(Node node) {
        while (node.left != null) {
            node = node.left;
        }
        return node.data;
    }

    //finds the maximum value in the tree by going to the rightmost node
    private T findMax(Node node) {
        while (node.right != null) {
            node = node.right;
        }
        return node.data;
    }

    //inorder traversal of tree, left -> root -> right 
    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            //keeps track of the path 
            private Stack<Node> stack = new Stack<>();
            //keeps track of the current node
            private Node current = root;

            //runs when iterator is created, goes left and pushes each node to stack
            {
                while (current != null) {
                    stack.push(current);
                    current = current.left;
                }
            }

            //returns true if there are still nodes in the stack to iterate through
            @Override
            public boolean hasNext() {
                return !stack.isEmpty();
            }

            @Override
            public T next() {
                //pops current node and saves data 
                Node node = stack.pop();
                T result = node.data;

                //if right subtree exists, go right and push all left children to stack
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
