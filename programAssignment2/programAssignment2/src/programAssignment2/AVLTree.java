package programAssignment2;
import java.util.Scanner;
//Node structure
class Node {
	int orderID;
	String bookName;
	int height;
	Node left;
	Node right;
	
	//Constructor for creating a new node
	Node(int orderID, String bookName){
		this.orderID = orderID;
		this.bookName = bookName;
		this.height = 1;
	}
}
class AVLTree {
	Node root;
	
	//get height of the node
	int nodeHeight(Node n) {
		if(n == null)
			return 0;
		return n.height;
	}
	
	//get current node count
	int countNodes(Node n) {
	    if (n == null)
	        return 0;
	    return 1 + countNodes(n.left) + countNodes(n.right);
	}
	
	//update height of the node
	void updateHeight(Node n) {
		n.height = 1+ Math.max(nodeHeight(n.left), nodeHeight(n.right));
	}
	
	//balance of the node
	int balance(Node n) {
		if(n == null) {
			return 0;
		}
		return (nodeHeight(n.left) - nodeHeight(n.right));
	}
	
	//left rotate
	Node leftRotate(Node x) {
		Node y = x.right;
		Node T2 = y.left;
		
		//perform rotation
		y.left = x;
		x.right = T2;
		
		//update height
		updateHeight(x);
		updateHeight(y);
		
		return y; //new root
	}
	
	//right rotation
	Node rightRotate(Node x) {
		Node y = x.left;
		Node T2 = y.right;
		
		//perform rotation
		y.right = x;
		x.left = T2;
		
		//update height
		updateHeight(y);
		updateHeight(x);
		
		return x; //new root
	}
	
	//balance the tree
	Node balanceTree(Node n) {
		updateHeight(n);
		
		int balance = balance(n);
		
		if(balance > 1) {
			//right
			if(balance(n.right) < 0) {
				//right left
				n.right = rightRotate(n.right);
				return leftRotate(n);
			}
		else //RR
			return leftRotate(n);	
		}
		if(balance < -1) {
			//left
			if(balance(n.left) > 0) {
				//left right
				n.left = leftRotate(n.left);
				return rightRotate(n);
			}
		else //LL
			return rightRotate(n);	
		}
		return n;
	}
	
	//Insert
	Node insertNode(Node n, int key, String bookName) {
		//perform standard insert
		if(n == null) {
			return new Node(key, bookName);
		}
		else if(key < n.orderID) {
			n.left = insertNode(n.left, key, bookName);
		}
		else {
			n.right = insertNode(n.right, key, bookName);
		}
		return balanceTree(n);// Duplicate orderID not allowed
	}
	
	//return the next left child
	Node mostLeftChild(Node n) {
		if(n.left != null)
			return mostLeftChild(n.left);
		else
			return n;
	}
	
	//remove
	Node removeNode(Node n, int key) {
	    //standard remove
		if (n == null) {
	        return n;
	    } 
		// If the key to be deleted is smaller than the root's key, it lies in left subtree
		else if (n.orderID > key) {
	        n.left = removeNode(n.left, key);
	    } 
		// If the key to be deleted is greater than the root's key, it lies in right subtree
		else if (n.orderID < key) {
	        n.right = removeNode(n.right, key);
	    } 
		// If the key is same as root's key, this is the node to be deleted
		else {
			// Node with only one child or no child
	        if (n.left == null || n.right == null) {
	            n = (n.left == null) ? n.right : n.left;
	        } else {
	            Node mostLeftChild = mostLeftChild(n.right);
	            n.orderID = mostLeftChild.orderID;
	            n.bookName = mostLeftChild.bookName;
	            n.right = removeNode(n.right, n.orderID);
	        }
	    }
	    if (n != null) {
	        n = balanceTree(n);
	    }
	    return n;
	}
	
	//find book based on its order ID
	Node findNode(int key) {
	    Node current = root;
	    while (current != null) {
	        if (current.orderID == key) {
	            break;
	        }
	        current = current.orderID < key ? current.right : current.left;
	    }
	    return current;
	}    
	
	// Utility function for search of node
	int search(int key)
    {
        if(findNode(key) == null)
            return 0;
        else
            return 1;
    }
	
	 // Utility function for insertion of node
    void add(int key, String bookName)
    {
        if (findNode(key) == null)
        {
            root = insertNode(root , key, bookName);
            System.out.println("Insertion successful");
        }

        else
            System.out.println("\nKey with the entered value already exists in the tree");
    }

    // Utility function for deletion of node
    void delete(int key)
    {
        if (findNode(key) != null)
        {
            root = removeNode(root , key);
            System.out.println("\nDeletion successful ");
        }

        else
            System.out.println("\nNo node with entered value found in tree");
    }

    //An in-order traversal of the AVL tree prints the orders in ascending order by their order IDs.
    void InOrder(Node root)
    {
        if(root == null)
        {
            System.out.println("\nNo nodes in the tree");
            return;
        }

        if(root.left != null)
            InOrder(root.left);
        System.out.print(root.orderID + " ");
        if(root.right != null)
            InOrder(root.right);

    }

    void PreOrder(Node root)
    {
        if(root == null)
        {
            System.out.println("No nodes in the tree");
            return;
        }

        System.out.print(root.bookName + " ");
        if(root.left != null)
            PreOrder(root.left);
        if(root.right != null)
            PreOrder(root.right);

    }

    void PostOrder(Node key)
    {
        if(key == null)
        {
            System.out.println("No nodes in the tree");
            return;
        }


        if(key.left != null)
            PostOrder(key.left);
        if(key.right != null)
            PostOrder(key.right);
        System.out.print(key.bookName + " ");

    }
}