
public class AVLTree {
	
	public static class Node {
		int orderID;
		String name;
		Node left;
		Node right;
		int height;
		
		public Node(int orderID,String name) {
			this.height = 1;
			this.name = name;
			this.left = null;
			this.right = null;
			this.orderID = orderID;
		}
	}
	Node Root;
	
	private int height(Node node) {
		if (node == null) {
			return 0;
		}
		else{
			return node.height;	
		}
	}

	
	public int updateHeight(Node node) {
		if (node == null) {
			return -1;	
		}
		else {
			node.height = 1+ Math.max(updateHeight(node.left),updateHeight(node.right));
		}
		return node.height;
	}
	
	private Node rightRotation(Node node) {
		Node y = node.left;
		Node temp = y.right;
		y.right = node;
		node.left = temp;
		updateHeight(node);
		updateHeight(y);
		
		return y;
	}
	
	private Node leftRotation(Node node) {
		Node y = node.right;
		Node temp = y.left;
		y.left = node;
		node.right = temp;
		updateHeight(node);
		updateHeight(y);
		
		return y;
	}
	
	private Node balanceTree(Node root) {
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
	
	private int calcBF(Node node) {
		if(node == null) {
			return 0;
		}
		else {
			return height(node.left) - height(node.right);
		}
	}
	
	private  Node insert(Node root, int orderID, String name) {
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
		return balanceTree(root);
	}
	
	public void insert(int orderID, String name) {
		Root = insert(Root, orderID, name);
	}
	
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
	
	public Node search(Node node, int orderID) {
		if(node == null || node.orderID == orderID) {
			return node;
		}
		
		if(orderID < node.orderID) {
			return search(node.left,orderID);
		}
		return search(node.right,orderID);
	}
	
	public Node findMin(Node node) {
		Node current = node;
		while(current.left != null) {
			current = current.left;
		}
		return current;
	}
	
	public Node findMax(Node node) {
		Node current = node;
		while(current.right != null) {
			current = current.right;
		}
		return current;
	}
	
	private Node remove(Node node, int orderID) {
		if(node == null) {
			System.out.println("The Order doesn't exist.");
			return node;
		}
		
		if (orderID < node.orderID) {
			node.left = remove(node.left,orderID);
		}else if(orderID > node.orderID) {
			node.right = remove(node.right,orderID);
		}else {
			if(node.left == null || node.right == null) {
				
				Node temp;
				
				if(node.left != null) {
					temp = node.left;
				}else {
					temp = node.right;
				}
				
				if(temp == null) {
					node = null;
					
				}else {
					node = temp;
				}
				
		}else {
			Node temp = findMin(node.right);
			
			node.orderID = temp.orderID;
			node.name = temp.name;
			node.right = remove(node.right,temp.orderID);
		}
	}
	
	if(node == null) {
		return node;
	}
	
	updateHeight(node);
	return balanceTree(node);
	
	}
	
	public void remove(int orderID) {
		Root = remove(Root,orderID);
	}
	
	public int getHeightTree() {
		return height(Root);
	}

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
	

