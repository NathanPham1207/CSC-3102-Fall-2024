package programAssignment2;
import java.util.ArrayList;

public class AVLTree {

    /* Members of the NODE CLASS posses the following attributes:
        * Order ID (type: integer): Book order identifier
        * Name (type: string): Title of the book
        * Left (type: reference): References the child node to the LEFT of the current node
        * Right (type: reference): References the child node to the RIGHT of the current node
        * Height (type: integer): The number of edges on the longest path from the current node to its farthest leaf */
    public static class Node {
        int orderID;
        String name;
        Node left;
        Node right;
        int height;

        /* NODE CONSTRUCTOR
         * Initializes attributes for new members of Node class
         * New nodes are specified with a name and an order ID
         * New nodes are inserted as leaf nodes with a height of 1 */
        public Node(int orderID,String name) {
            this.height = 1;
            this.name = name;
            this.left = null;
            this.right = null;
            this.orderID = orderID;
        }
    }

    /* ROOT NODE (of the AVL Tree)
    * The topmost node from which other nodes are attached */
    Node Root;

    /* GET HEIGHT
     * Returns the height of a node
     * "node" is the node whose height is to be returned
     * If the node is null, the function returns 0 */
    private int height(Node node) {
        if (node == null) {
            return 0;
        }
        else{
            return node.height;
        }
    }

    /* UPDATE HEIGHT
     * Updates the height of the specified node based on its children's heights
     * Returns the updated height */
    private int updateHeight(Node node) {
        /* If the node is null, returns -1 to represent an empty subtree,
         * ensuring that the recursive function to terminate at leaf nodes */
        if (node == null) {
            return -1;
        }
        /* Recursively calculates the height of the left and right subtrees of the current node
         * Sets the node’s height as 1 plus the maximum of these subtree heights
         * Returns the updated height of the current node */
        else {
            node.height = 1+ Math.max(updateHeight(node.left),updateHeight(node.right));
        }
        return node.height;
    }

    /* RIGHT ROTATION
     * Restructures a node’s left-heavy subtree to restore balance in the AVL tree
     * This rotation is used when the left child’s height is greater than the right’s by more than one */
    private Node rightRotation(Node node) {
        /* Sets the left child (y) as the new root of this subtree */
        Node y = node.left;

        /* Temporarily stores y's right subtree
         * Temp become the left subtree of the original node) */
        Node temp = y.right;

        /* PERFORM THE ROTATION
         * Reattaches the original node as the right child of y */
        y.right = node;

        /* Updates the left reference of the original node to y’s right subtree */
        node.left = temp;

        /* Update heights */
        updateHeight(node);
        updateHeight(y);

        /* y will become the new root of the subtree once rotated */
        return y;
    }

    /* LEFT ROTATION
     * Restructures a node’s right-heavy subtree to restore balance in the AVL tree
     * This rotation is used when the right child’s height is greater than the left’s by more than one */
    private Node leftRotation(Node node) {
        /* Sets the right child (y) as the new root of this subtree */
        Node y = node.right;

        /* Temporarily stores y's left subtree
         * Temp becomes the right subtree of the original node */
        Node temp = y.left;

        /* PERFORM THE ROTATION
         * Reattaches the original node as the left child of y */
        y.left = node;

        /* Updates the right reference of the original node to y’s left subtree */
        node.right = temp;

        /* Update heights */
        updateHeight(node);
        updateHeight(y);

        /* y will become the new root of the subtree once rotated */
        return y;
    }

    /* BALANCE TREE
     * Calculates the balance factor (left subtree - right subtree) of a given node
     * If the balance factor is greater than one, performs necessary rotations to balance the tree
     * Returns the new root of the balanced (sub)tree */
    private Node balanceTree(Node root) {
        /* Updates the height of the current node */
        updateHeight(root);
        /* Calculates the balance factor of the current node */
        int balancingFactor = calcBF(root);

        /* RIGHT-HEAVY CASE
         * Checks the balance factor of the current node
         * If the balance factor is < -1, there is a right-heavy imbalance */
        if (balancingFactor < -1) {
            /* Checks if the right subtree is left-heavy
             * If true, this indicates a right-left imbalance */
            if (calcBF(root.right) > 0) {
                /* Fixes the imbalance by performing a right rotation on the right subtree */
                root.right = rightRotation(root.right);
            }
            /* Performs a left rotation on the given root
             * to correct either a right-right imbalance
             * or to complete the right-left rotation sequence */
            return leftRotation(root);
        }

        /* LEFT-HEAVY CASE
         * Checks the balance factor of the current node
         * If the balance factor is > 1, there is a left-heavy imbalance */
        if (balancingFactor > 1) {
            /* Checks if the left subtree is right-heavy
             * If true, this indicates a left-right imbalance */
            if (calcBF(root.left) < 0) {
                /* Fixes the imbalance by performing a left rotation on the left subtree */
                root.left = leftRotation(root.left);
            }
            /* Performs a right rotation on the given root
             * to correct either a left-left imbalance
             * or to complete the left-right rotation sequence */
            return rightRotation(root);
        }
        return root;
    }

    /* BALANCE A NODE
    * Calculates the balance factor of the specified node
    * Returns 0 if the node is null, indicating an empty or balanced subtree */
    private int calcBF(Node node) {
        /* Checks if the node is null, returning 0 as there is no imbalance */
        if(node == null) {
            return 0;
        }
        /* Calculates the BF by subtracting the height of the right subtree from the height of the left subtree
         * Positive values indicate left-heavy imbalance and negative values indicating right-heavy imbalance */
        else {
            return height(node.left) - height(node.right);
        }
    }

    /* INSERT NODE
    * Requires parameters orderID and name
    * Performs a standard binary search tree insertion
    * Returns the new root of the subtree after insertion and balancing */
    private  Node insert(Node root, int orderID, String name) {
        /* If the current root is null, creates a new node with the given orderID and name */
        if (root == null) {
            return new Node(orderID,name) ;
        }
        /* If the orderID is less than the current node's orderID, recursively inserts into the left subtree */
        else if(orderID < root.orderID) {
            root.left = insert(root.left,orderID,name);
        }
        /* If the orderID is greater than the current node's orderID, recursively inserts into the right subtree */
        else if(orderID > root.orderID){
            root.right = insert(root.right,orderID,name);
        }else {
            /* If the orderID already exists in the tree, prints an error message and returns the current root */
            System.out.println("OrderID " + orderID + " already exists.");
            return root;
        }
        /* Updates the height of the current node */
        updateHeight(root);
        /* Balances the tree and returns the new root of the subtree */
        return balanceTree(root);
    }

    public void insert(int orderID, String name) {
        Root = insert(Root, orderID, name);
    }

    /* IN-ORDER TRAVERSAL
     * visits the left subtree, the current node, and then the right subtree
     * Prints orders as it goes (in ascending order, based on their order IDs) */
    private void InOrder(Node root) throws InterruptedException {
        /* If the current node is null, the function terminates as it has run out of nodes */
        if(root == null){
            return;
        }
        /* Recursively traverses the left subtree */
        InOrder(root.left);
        /* Prints the order ID and book name of the current node */
        System.out.println("(OrderID: " + root.orderID+") "+ "Book Name: "+ root.name );
        Thread.sleep(350);
        /* Recursively traverses the right subtree */
        InOrder(root.right);
    }

    public void InOrder() throws InterruptedException {
        InOrder(Root);
    }

    /* SEARCH BY ORDER NUMBER
     * Find a node based on the OrderID provided */
    public Node search(Node node, int orderID) {
        /* If the current node is null or the order ID matches, return the current node */
        if(node == null || node.orderID == orderID) {
            return node;
        }
        /* If the order ID is less than the current node's order ID, search in the left subtree */
        if(orderID < node.orderID) {
            return search(node.left,orderID);
        }
        /* Otherwise, search in the right subtree */
        return search(node.right,orderID);
    }

    /* FIND MINIMUM NODE (Oldest Order)
     * The function traverses the leftmost path to find the node with the smallest order ID. */
    public Node findMin(Node node) {
        Node current = node;
        /* Loop down to find the leftmost leaf, which represents the minimum node */
        try {
            while(current.left != null) {
                current = current.left;
            }
        }catch(Exception e) {
            System.out.println("Your AVL Tree is null.");
        }
        /* Returns the node containing the minimum order ID */
        return current;
    }

    /* FIND MAXIMUM NODE (Most Recent Order)
     * The function traverses the rightmost path to find the node with the largest order ID. */
    public Node findMax(Node node) {
        Node current = node;
        /* Loop down to find the rightmost leaf, which represents the maximum node */
        try {
            while(current.right != null) {
                current = current.right;
            }
        }catch(Exception e) {
            System.out.println("Your AVL Tree is null.");
        }
        /* Returns the node containing the maximum order ID */
        return current;
    }

    /* DELETE NODE
     * Removes a node with the specified order ID from the AVL tree
     * If the node to be deleted has two children, replaces it with its inorder successor
     * Ensures the AVL tree remains balanced after removal */
    private Node remove(Node node, int orderID) {
        /* If the node is null, the given order ID does not exist */
        if(node == null) {
            System.out.println("The Order doesn't exist.");
            return node;
        }
        /* If the key to be deleted is less than the root's key, it lies in left subtree */
        if (orderID < node.orderID) {
            node.left = remove(node.left,orderID);
        }
        /* If the key to be deleted is greater than the root's key, it lies in right subtree */
        else if(orderID > node.orderID) {
            node.right = remove(node.right,orderID);
        }
        /* If the key is same as root's key, the root the node to be deleted */
        else {
            /* Case: Node has one or zero children */
            if(node.left == null || node.right == null) {
                Node temp;
                /* If the node has a left child, assign it to temp
                 * Else assign the right child */
                if(node.left != null) {
                    temp = node.left;
                }else {
                    temp = node.right;
                    System.out.println("OrderID is not valid.");
                }
                /* Case: No children (leaf node) */
                if(temp == null) {
                    temp = node;
                    node = null;
                }else {
                    /* Case: One child, replacing node with its single child */
                    node = temp;
                    System.out.println("Tree is empty.");
                }
            }
            /* Case: Node has two children */
            else {
                /* Finds the inorder successor (smallest node in the right subtree) */
                Node temp = findMin(node.right);
                /* Replaces current node's data with the successor’s data */
                node.orderID = temp.orderID;
                node.name = temp.name;
                /* Deletes the inorder successor */
                node.right = remove(node.right,temp.orderID);
            }
        }
        /* If the tree had only one node, return it */
        if(node == null) {
            return node;
        }

        /* Updates the height of the current node */
        updateHeight(node);

        /* Balances the tree */
        return balanceTree(node);
    }

    /* SEARCH BY BOOK TITLE
     * Searches for nodes in the AVL tree with a given book title.
     * Collects all matching nodes in an array. */
    private ArrayList<Node> searchByTitle(Node node, String name, ArrayList<Node> books) {
        /* Returns the list if the current node is null */
        if(node == null) {
            return books;
        }
        /* Adds the current node to the list if its title matches */
        if(node.name.toLowerCase().equals(name.toLowerCase())){
            books.add(node);
        }

        /* Recursively searches in the left and right subtrees for additional matches */
        searchByTitle(node.left,name,books);
        searchByTitle(node.right,name,books);

        /* Returns the list of nodes that match the specified title */
        return books;
    }

    /* Print the order IDs of all books with a matching title or an error message if no matches are found */
    public void searchByTitle(String name) {
        /* Calls searchByTitle function with the root of the tree and an empty ArrayList to store matches */
        ArrayList<Node> found = searchByTitle(Root,name, new ArrayList<>());
        /* Checks if any nodes with the given title were found */
        if (!found.isEmpty()) {
            System.out.println("\nOrderID found for book \"" + name + "\":");
            /* Iterates through the list of matching nodes and prints their respective order IDs */
            for (int i = 0; i< found.size(); i++) {
                System.out.println("OrderIDs: " + found.get(i).orderID);
            }
        }
        /* Prints a message if no matching nodes were found */
        else {
            System.out.println("No orders were found.");
        }
    }

    /* Removes a node from the AVL tree
     * Updates the root after deletion */
    public void remove(int orderID) {
        Root = remove(Root,orderID);
    }

    /* Retrieves AVL tree height */
    public int getHeightTree() {
        return height(Root);
    }

    /* Count total number of nodes after every operation */
    public int getNodeCount(Node node) {
        if (node == null) {
            return 0;
        }
        return 1 + getNodeCount(node.left) + getNodeCount(node.right);
    }

    /* Returns the total node count for the entire AVL tree */
    public int getNodeCount() {
        return getNodeCount(Root);
    }
}