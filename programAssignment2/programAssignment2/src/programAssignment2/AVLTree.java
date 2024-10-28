package programAssignment2;




public class AVLTree {
	//Node structure
	public static class Node {
		int orderID;//Order ID
		String name;//name of book
		Node left;
		Node right;
		int height;//height of node
		
		//Constructor for creating a new node
		public Node(int orderID,String name) {
			this.height = 1;//new nodes are inserted as leaf nodes with height 1
			this.name = name;
			this.left = null;
			this.right = null;
			this.orderID = orderID;
		}
	}
	Node Root;
	
	//Utility function to get the height of node
	private int height(Node node) {
		if (node == null) {
			return 0;
		}
		else{
			return node.height;	
		}
	}
	
	//update height of the node
	public int updateHeight(Node node) {
		if (node == null) {
			return -1;	
		}
		else {
			node.height = 1+ Math.max(updateHeight(node.left),updateHeight(node.right));
		}
		return node.height;
	}
	
	//right rotation
	private Node rightRotation(Node node) {
		Node y = node.left;
		Node temp = y.right;
		
		//perform rotation
		y.right = node;
		node.left = temp;
		
		//update height
		updateHeight(node);
		updateHeight(y);
		return y;//new root
	}
	
	//left rotate
	private Node leftRotation(Node node) {
		Node y = node.right;
		Node temp = y.left;
		
		//perform rotation
		y.left = node;
		node.right = temp;
		
		//update height
		updateHeight(node);
		updateHeight(y);

		return y;//new root
	}
	
	//balance the tree
	private Node balanceTree(Node root) {
		// Update height of this ancestor node
		updateHeight(root);
		
		// Get balance factor of this node to check if it is unbalanced
		int balancingFactor = calcBF(root);

		if (balancingFactor < -1) {
			if (calcBF(root.right) > 0) {
				root.right = rightRotation(root.right);
			}
			return leftRotation(root);
		}

		if (balancingFactor > 1) {
			if (calcBF(root.left) < 0) {
				root.left = leftRotation(root.left);
			}
			return rightRotation(root);
		}
		return root;
	}
	
	//balance at a node
	private int calcBF(Node node) {
		if(node == null) {
			return 0;
		}
		else {
			return height(node.left) - height(node.right);
		}
	}
	
	private  Node insert(Node root, int orderID, String name) {
		//perform standard insert
		 if (root == null) {
	         return new Node(orderID,name) ;
		 }    
	       else if(orderID < root.orderID) {
			root.left = insert(root.left,orderID,name);
	     }
	       else if(orderID > root.orderID){
			root.right = insert(root.right,orderID,name);
		}else {
			System.out.println("OrderID alredy exists.");
			return root;
		}
		updateHeight(root);
		return balanceTree(root);// Duplicate orderID not allowed
	}
	
	public void insert(int orderID, String name) {
		Root = insert(Root, orderID, name);
	}
	
	//An in-order traversal of the AVL tree prints the orders in ascending order by their order IDs
	 public void InOrder(Node root){
	        if(root == null){
	            return;
	        }
	        InOrder(root.left);
	        System.out.println("(OrderID: " + root.orderID+") "+ "Book Name: "+ root.name );
	        InOrder(root.right);
	 }
	
	public void InOrder() {
		InOrder(Root);
	} 
	
	//Search for a book based on its order number
	public Node search(Node node, int orderID) {
		if(node == null || node.orderID == orderID) {
			return node;
		}

		if(orderID < node.orderID) {
			return search(node.left,orderID);
		}
		return search(node.right,orderID);
	}
	
	//Find Oldest Order (Minimum Node)
	public Node findMin(Node node) {
		Node current = node;
		// Loop down to find the leftmost leaf
		while(current.left != null) {
			current = current.left;
		}
		return current;
	}
	
	//Find Latest Order (Maximum Node)
	public Node findMax(Node node) {
		Node current = node;
		// Loop down to find the rightmost leaf
		while(current.right != null) {
			current = current.right;
		}
		return current;
	}
	
	private Node remove(Node node, int orderID) {
		// Standard remove
		if(node == null) {
			System.out.println("The Order doesn't exist.");
			return node;
		}
		// If the key to be deleted is smaller than the root's key, it lies in left subtree
		if (orderID < node.orderID) {
			node.left = remove(node.left,orderID);
		}
		// If the key to be deleted is greater than the root's key, it lies in right subtree
		else if(orderID > node.orderID) {
			node.right = remove(node.right,orderID);
		}
		// If the key is same as root's key, this is the node to be deleted
		else {
			// Node with only one child or no child
			if(node.left == null || node.right == null) {
				Node temp;
				if(node.left != null) {
					temp = node.left;
				}else {
					temp = node.right;
					System.out.println("OrderID is not valid.");
				}
				// No child case
				if(temp == null) {
					temp = node;
					node = null;
				}else {
					//1 child
					node = temp;
					System.out.println("Tree is empty.");
				}
			}else {
				// Node with two children: Get the inorder successor (smallest in the right subtree)
				Node temp = findMin(node.right);
				// Copy the inorder successor's data to this node
				node.orderID = temp.orderID;
				node.name = temp.name;
				// Delete the inorder successor
				node.right = remove(node.right,temp.orderID);
			}
		}
		 // If the tree had only one node then return
		if(node == null) {
			return node;
		}
		
		//Update height of current node
		updateHeight(node);
		
		//Balance the tree
		return balanceTree(node);
		
		}
		
		public void remove(int orderID) {
			Root = remove(Root,orderID);
		}
		
		//Get height of the tree
		public int getHeightTree() {
			return height(Root);
		}
		
		//Count nodes after every operation
		 public int getNodeCount(Node node) {
		        if (node == null) {
		        	return 0;
		        }
		        return 1 + getNodeCount(node.left) + getNodeCount(node.right);
		 }
		 
		 public int getNodeCount() {
			 return getNodeCount(Root);
		 }

		
			
}